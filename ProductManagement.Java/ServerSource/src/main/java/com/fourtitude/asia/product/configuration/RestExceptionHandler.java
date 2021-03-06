package com.fourtitude.asia.product.configuration;

import com.fourtitude.asia.product.configuration.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.MethodNotAllowedException;
import org.springframework.web.server.UnsupportedMediaTypeStatusException;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class RestExceptionHandler {

    /**
     * handle when data not found error
     * @param e
     * @param req
     * @return
     */
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException.class)
    public ErrorResponse handlerEntityNotFoundException(EntityNotFoundException e, HttpServletRequest req) {
        log.error("===================== Handler EntityNotFoundException =====================");
        e.printStackTrace();
        return new ErrorResponse(HttpStatus.NOT_FOUND, e.getMessage(), null);
    }

    /**
     * handle when RuntimeException
     * @param e
     * @param req
     * @return
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(RuntimeException.class)
    public ErrorResponse handlerRuntimeException(RuntimeException e, HttpServletRequest req) {
        log.error("===================== Handler RuntimeException =====================");
        e.printStackTrace();
        return new ErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage(), null);
    }

    /**
     * handle when not allowed http request method
     * @param e
     * @param req
     * @return
     */
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(MethodNotAllowedException.class)
    public ErrorResponse handlerMethodNotAllowedException(MethodNotAllowedException e, HttpServletRequest req) {
        log.error("===================== Handler MethodNotAllowedException =====================");
        e.printStackTrace();
        return new ErrorResponse(HttpStatus.METHOD_NOT_ALLOWED, e.getMessage(), null);
    }

    /**
     * handle when not supported Media Type
     * @param e
     * @param req
     * @return
     */
    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    @ExceptionHandler(UnsupportedMediaTypeStatusException.class)
    public ErrorResponse handlerUnsupportedMediaTypeStatusException(UnsupportedMediaTypeStatusException e,
                                                                    HttpServletRequest req) {
        log.error("===================== Handler UnsupportedMediaTypeStatusException =====================");
        e.printStackTrace();
        return new ErrorResponse(HttpStatus.UNSUPPORTED_MEDIA_TYPE, e.getMessage(), null);
    }

    /**
     * handle bad request - parameter validation
     * @param e
     * @param req
     * @return
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponse handlerMethodArgumentNotValidException(MethodArgumentNotValidException e,
                                                                HttpServletRequest req) {
        log.error("===================== Handler MethodArgumentNotValidException =====================");
        e.printStackTrace();
        return getErrorResponseByBindingResult(e.getBindingResult(), HttpStatus.BAD_REQUEST, "???????????? ?????? ?????? ????????????.");
    }

    /**
     * other...
     * @param e
     * @param req
     * @return
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(Exception.class)
    public ErrorResponse handlerException(Exception e, HttpServletRequest req) {
        log.error("===================== Handler Exception =====================");
        e.printStackTrace();
        return new ErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage(), null);
    }

    private ErrorResponse getErrorResponseByBindingResult(BindingResult bindingResult,
                                                          HttpStatus httpStatus,
                                                          String message) {
        //BindingResult ????????? ????????? ???????????? validation ????????? ????????????. -> ???? -> ???????????? validation??? ?????? ??? ????????????.
        List<String> errorDetails = bindingResult.getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());
        return new ErrorResponse(httpStatus, message, errorDetails);
    }

}
