package com.project.ity.domain.common.logging;

import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

@Component("loggingAspect")
@Aspect
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    public void logBefore(JoinPoint joinPoint) {
        logger.debug("===============================================logBefore()");
    }

    public void logAfter(JoinPoint joinPoint) {
        logger.debug("===============================================logAfter()");
    }

    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        Object thisObj 		= joinPoint.getTarget();
        String className 	= thisObj.getClass().getName();
        long currentTime 	= System.currentTimeMillis();


        Map<String, ?> paramsMap  = null;
        StringBuilder paramsBuffer = new StringBuilder();

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        for (Object obj : joinPoint.getArgs()) {

            paramsMap = getParameterClassLoad(obj);

            if(paramsMap.isEmpty())
                continue;

            for (Map.Entry<String, ?> entry : paramsMap.entrySet()) {
                if(!paramsBuffer.isEmpty()) paramsBuffer.append(", ");
                paramsBuffer.append("{").append(entry.getKey()).append(":");
                paramsBuffer.append(entry.getValue()).append("}");
            }
        }
        /* ------------------------------------------------------------------------------------*/

        String requestUrl 	  = request.getRequestURI();

        if(logger.isDebugEnabled()){
            logger.debug("=================================================");
            logger.debug(">>>>>>>>> LOGGING START >>>>>>>>>>");
            logger.debug("[REQUEST URL]:" +requestUrl);
            logger.debug("[class]:" + className);
            logger.debug("[method]:" + joinPoint.getSignature().getName());
            logger.debug("[params]:" + paramsBuffer.toString());

        }

        Object returnObj = joinPoint.proceed();
        String executeTime = String.valueOf(System.currentTimeMillis()-currentTime);
        if(logger.isDebugEnabled()){
            logger.debug("[class]:" + className);
            logger.debug("[method]:" + joinPoint.getSignature().getName());
            logger.debug("[execute time]: {}ms", executeTime);
            logger.debug(">>>>>>>>>> LOGGING END >>>>>>>>>>");
            logger.debug("=================================================");
        }

        return returnObj;
    }

    @SuppressWarnings("unchecked")
    public static Map<String, Object> getParameterClassLoad(Object obj) throws Exception{
        if (obj == null) {
            return new HashMap<>();
        }

        Map<String, Object> map = new HashMap<>();

        if (obj instanceof String || obj instanceof Integer) {
            map.put("value", obj);
        }
        else if (obj instanceof Map) {
            map.putAll((Map<String, Object>) obj);
        }
        else {
            Class<?> paramClass = obj.getClass();
            Field[] fields = paramClass.getDeclaredFields();

            for (Field field : fields) {
                String fieldName = field.getName();
                String methodName = "get" + capitalize(fieldName);

                Method method = paramClass.getMethod(methodName);
                Object value = method.invoke(obj);
                if (value != null) {
                    map.put(fieldName, value);
                }
            }
        }

        return map;
    }

    private static String capitalize(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}
