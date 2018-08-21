package com.jaiswalakshay.aspects;

import com.jaiswalakshay.config.TenantContext;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Aspect
@Component
public class BeforeUserService {

    private static final Logger logger = LoggerFactory.getLogger(BeforeUserService.class);

    @Autowired
    private MultiTenantConnectionProvider multiTenantConnectionProvider;

//    @Around("@annotation(com.jaiswalakshay.annotation.CrossTenancyGet) && execution(* *(..))")
    public Object beforeGetUser(ProceedingJoinPoint joinPoint) throws Throwable {
        logger.info("Before executing method: " + joinPoint.getSignature().toString());
        Object returnObj = joinPoint.proceed();
        logger.info("After executing method: " + joinPoint.getSignature().toString() + " returned Object: " + returnObj.toString());
        return returnObj;
    }

    @Around("@annotation(com.jaiswalakshay.annotation.CrossTenancyGet) && execution(* *(..))")
    public List<Object> beforeGetAllUser(ProceedingJoinPoint joinPoint) throws Throwable {
        logger.info("Before executing method: " + joinPoint.getSignature().toString());
        TenantContext.setTenantId("tenant_1");
        Object returnObj = joinPoint.proceed();
        logger.info("After executing method: " + joinPoint.getSignature().toString() + " returned Object: " + returnObj.toString());
        TenantContext.clear();

        TenantContext.setTenantId("tenant_2");
        Object returnObj1 = joinPoint.proceed();
        logger.info("After executing method: " + joinPoint.getSignature().toString() + " returned Object: " + returnObj.toString());
        TenantContext.clear();

        return Arrays.asList(returnObj,returnObj1);

    }

}
