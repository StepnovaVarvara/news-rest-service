package com.example.news_rest_service.aop;

import com.example.news_rest_service.exception.EntityNotFoundException;
import com.example.news_rest_service.exception.InvalidIdException;
import com.example.news_rest_service.model.Comment;
import com.example.news_rest_service.model.News;
import com.example.news_rest_service.repository.CommentRepository;
import com.example.news_rest_service.repository.NewsRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.text.MessageFormat;

@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class CheckAspect {

    private final NewsRepository newsRepository;
    private final CommentRepository commentRepository;
    
    @Before("@annotation(NewsChecker)")
    public void checkUserForNews(JoinPoint joinPoint) {
        String argumentName = joinPoint.getArgs()[0].toString();

        if (argumentName.contains(" ")) {
            argumentName = argumentName.substring(argumentName.indexOf('=') + 1, argumentName.indexOf(','));
        }
        String newsId = argumentName;

        News news = newsRepository.findById(Long.valueOf(newsId))
                .orElseThrow(() -> new EntityNotFoundException(
                        MessageFormat.format("Новость с ID {0} не найдена", newsId)
                ));

        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();

        String userId = request.getParameter("userId");

        if (news.getUser().getId() != Long.valueOf(userId)) {
            throw new InvalidIdException(MessageFormat.format(
                    "Новость с ID {0} создал другой User. " +
                            "Редактирование/удаление новости запрещено!", argumentName));
        }
    }
    @Before("@annotation(CommentChecker)")
    public void checkUserForComment(JoinPoint joinPoint) {
        String argumentName = joinPoint.getArgs()[0].toString();

        if (argumentName.contains(" ")) {
            argumentName = argumentName.substring(argumentName.indexOf('=') + 1, argumentName.indexOf(','));
        }
        String commentId = argumentName;

        Comment comment = commentRepository.findById(Long.valueOf(commentId))
                .orElseThrow(() -> new EntityNotFoundException(
                        MessageFormat.format("Комментарий с ID {0} не найден", commentId)
                ));

        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();

        String userId = request.getParameter("userId");

        if (comment.getUser().getId() != Long.valueOf(userId)) {
            throw new InvalidIdException(MessageFormat.format(
                    "Комментарий с ID {0} создал другой User. " +
                            "Редактирование/удаление комментария запрещено!", argumentName));
        }
    }
}