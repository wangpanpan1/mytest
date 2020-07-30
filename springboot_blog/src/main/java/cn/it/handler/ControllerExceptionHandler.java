package cn.it.handler;


import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.MatrixVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice//获取控制层的异常通知
public class ControllerExceptionHandler {

 private final  Logger logger= LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(Exception.class)
    public ModelAndView exceptionHandler(HttpServletRequest request,Exception e) throws Exception {
    ModelAndView mv = new ModelAndView();
    logger.error("Request URL : {},Exception :{} ",request.getRequestURI(),e);

    System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");

//判断
    if(AnnotationUtils.findAnnotation(e.getClass(),ResponseStatus.class)!=null){
       throw  e;

    }


    mv.addObject("url",request.getRequestURI());
    mv.addObject("exception",e);
    mv.setViewName("error/error");
    return mv;
}





}
