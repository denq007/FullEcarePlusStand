package com.t_systems.ecare.eCare.exeptionhanler;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExeptionHandler extends DefaultHandlerExceptionResolver{

    public static final String ERROR_ACCESS_PAGE = "errorAccessPage";
    public static final String MESSAGE = "message";

    @ExceptionHandler(RuntimeException.class)
    public ModelAndView handleException(HttpServletRequest request, Exception ex) {
        ModelAndView model = new ModelAndView(ERROR_ACCESS_PAGE);
        model.addObject(MESSAGE, "Check the correctness of the entered data");
        return model;
    }
    @ExceptionHandler(NoHandlerFoundException.class)
    public String handleNoHandlerFoundException(NoHandlerFoundException ex) {
        return ERROR_ACCESS_PAGE;
    }

    @ExceptionHandler(NumberFormatException.class)
    public ModelAndView handleNumberFormatException(HttpServletRequest req,NumberFormatException ex)
    {
        ModelAndView mav = new ModelAndView();
        mav.addObject(MESSAGE, "Check the correctness of the entered data");
        mav.addObject("url", req.getRequestURL());
        mav.setViewName(ERROR_ACCESS_PAGE);
        return mav;
    }

    @ExceptionHandler(value = Exception.class)
    public ModelAndView defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {
        if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null)
            throw e;
        ModelAndView mav = new ModelAndView();
        mav.addObject(MESSAGE, "We are already working on fixing this error");
        mav.addObject("url", req.getRequestURL());
        mav.setViewName(ERROR_ACCESS_PAGE);
        return mav;
    }
}

