package com.example.demo.aspect;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.CodeSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * RestControllerAspect
 *
 * @author Assil Rabia
 */

@Aspect
@Component
@AllArgsConstructor
public class RestControllerAspect {

    private final ObjectMapper mapper;
    private final HttpServletRequest request;

    private static final Logger logger = LoggerFactory.getLogger(RestControllerAspect.class);

    /**
     * Pointcut for within @RestController annotation
     */
    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
    public void restController() {
    }

    /**
     * Pointcut for within @ControllerAdvice annotation
     */
    @Pointcut("within(@org.springframework.web.bind.annotation.ControllerAdvice *)")
    public void globalExceptionHandler() {
    }

    /**
     * Pointcut for all method
     */
    @Pointcut("execution(* *.*(..))")
    public void allMethod() {
    }

    /**
     * logging before Pointcut
     *
     * @param joinPoint JoinPoint
     */
    @Before("restController()")
    public void logMethodBeforeCallApi(JoinPoint joinPoint) {
        String[] parameterNames = ((CodeSignature) joinPoint.getSignature()).getParameterNames();
        Map<String, Object> parameters = new HashMap<>();
        for (int i = 0; i < parameterNames.length; i++) {
            parameters.put(parameterNames[i], joinPoint.getArgs()[i]);
        }

        try {
            logger.info("Enter ==> API: [{}], method: [{}], arguments: {} ",
                    request.getServletPath(), request.getMethod(), mapper.writeValueAsString(parameters));
        } catch (JsonProcessingException e) {
            logger.error("Error while converting", e);
        }
    }

    /**
     * logging after Pointcuts returning
     *
     * @param entity ResponseEntity
     */
    @AfterReturning(pointcut = "restController() || globalExceptionHandler()", returning = "entity")
    public void logMethodAfterCall(ResponseEntity<?> entity) {
        try {
            logger.info("Exit <== API: [{}], method: [{}], retuning: {}",
                    request.getServletPath(), request.getMethod(), mapper.writeValueAsString(entity));
        } catch (JsonProcessingException e) {
            logger.error("Error while converting", e);
        }
    }

    /**
     * logging execution time around Pointcuts
     *
     * @param joinPoint ProceedingJoinPoint
     * @return Object
     * @throws Throwable thrown by joinPoint.proceed()
     */
    @Around("restController() && allMethod() || globalExceptionHandler() && allMethod()")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        final StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Object proceed = joinPoint.proceed();
        stopWatch.stop();

        logger.info("Execution === API: [{}], method: [{}] executed in {} ms",
                request.getServletPath(), request.getMethod(), stopWatch.getTotalTimeMillis());
        return proceed;
    }

}
