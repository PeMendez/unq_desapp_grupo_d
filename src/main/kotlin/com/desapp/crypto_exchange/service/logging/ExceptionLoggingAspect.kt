package com.desapp.crypto_exchange.service.logging

import org.aspectj.lang.annotation.AfterThrowing
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Aspect
@Component
class ExceptionLoggingAspect {
    private val logger = LoggerFactory.getLogger(this.javaClass)
    @Pointcut("execution(* com.desapp.crypto_exchange.controller..*(..))")
    fun controllerMethodPointcut() {
        //should be empty
    }

    @AfterThrowing(pointcut = "controllerMethodPointcut()", throwing = "e")
    fun logExceptionThrowing(e: Throwable){
        logger.error("Exception: ${e.javaClass.simpleName} - Message : ${e.message}")
    }
}