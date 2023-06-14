package com.sarac.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.keycloak.adapters.springsecurity.account.SimpleKeycloakAccount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

//    Logger logger= LoggerFactory.getLogger(LoggingAspect.class); || @slf4j annotation works

    private String getUserName(){

        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        SimpleKeycloakAccount userDetails= (SimpleKeycloakAccount) authentication.getDetails();
        return userDetails.getKeycloakSecurityContext().getToken().getPreferredUsername();
    }

    @Pointcut("execution(* com.sarac.controller.ProjectController.*(..))|| execution(* com.sarac.controller.TaskController.*(..))")
    public void anyProjectAndTaskControllerPC(){}

    @Before("anyProjectAndTaskControllerPC()")
    public void boforeAnyProjectAndTaskControllerAdvice(JoinPoint joinPoint){
        log.info("Before-> Method:{}, User:{}",
                joinPoint.getSignature().toShortString(),getUserName());
    }

    @AfterReturning(pointcut = "anyProjectAndTaskControllerPC()",returning="results")
    public void afterReturningAnyProjectAndTaskControllerAdvice(JoinPoint joinPoint,Object results){
        log.info("After Returning -> Method:{}, User:{} , Results:{}",
                joinPoint.getSignature().toShortString(),getUserName(),
                results.toString());
    }

    @AfterThrowing(pointcut = "anyProjectAndTaskControllerPC()",throwing="exception")
    public void afterReturningAnyProjectAndTaskControllerThrowing(JoinPoint joinPoint,Exception exception){
        log.info("After Returning -> Method:{}, User:{} , Results:{}",
                joinPoint.getSignature().toShortString(),getUserName(),
                exception.getMessage());
    }
}
