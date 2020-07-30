package cn.it.aspect;



import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@Aspect
@Component
public class LogAspect {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());



    @Pointcut("execution(* cn.it.controller.*.*(..))")
    public void log() {
    }


    @Before("log()")
    public void befor(JoinPoint joinPoint) {
ServletRequestAttributes attributes =(ServletRequestAttributes)RequestContextHolder.getRequestAttributes();

       HttpServletRequest request=attributes.getRequest();
        String url=request.getRequestURL().toString();//获取请求的url
        String ip    =request.getRemoteAddr();//获取请求的ip地址
        String classmethod =joinPoint.getSignature().getDeclaringTypeName()+ "." +joinPoint.getSignature().getName();
        Object[] args=joinPoint.getArgs();
        RequestLog requestLog=new RequestLog(url,ip,classmethod,args);


        logger.info("Request :{}",requestLog);

    }

   /* @After("log()")
    public void after(JoinPoint joinPoint) {
        logger.info("after........................................");

    }*/

    @AfterReturning(returning = "result",pointcut = "log()")
    public   void  AfterReturning (Object result){




        logger.info("Request :{}",result);

    }


    private  class RequestLog{

        private String url;
        private String ip;
        private String classmethod;
        private Object[] args;

        public RequestLog(String url, String ip, String classmethod, Object[] args) {
            this.url = url;
            this.ip = ip;
            this.classmethod = classmethod;
            this.args = args;
        }

        @Override
        public String toString() {
            return "RequestLog{" +
                    "url='" + url + '\'' +
                    ", ip='" + ip + '\'' +
                    ", classmethod='" + classmethod + '\'' +
                    ", args=" + Arrays.toString(args) +
                    '}';
        }
    }

}