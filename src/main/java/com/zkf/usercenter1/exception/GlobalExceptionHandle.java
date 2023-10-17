package com.zkf.usercenter1.exception;

import com.zkf.usercenter1.common.BaseResponse;
import com.zkf.usercenter1.common.ErrorCode;
import com.zkf.usercenter1.common.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器
 *
 * @author zkf
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandle {

    @ExceptionHandler
    public BaseResponse businessExceptionHandle(BusinessException e) {
        log.error("businessException: " + e.getMessage(), e);
        return ResultUtils.error(e.getCode(), e.getMessage(), e.getDescription());
    }

    @ExceptionHandler
    public BaseResponse runtimeExceptionHandle(RuntimeException e) {
        log.error("runtimeError: ", e);
        return ResultUtils.error(ErrorCode.SYSTEM_ERROR, e.getMessage(), "");
    }
}
