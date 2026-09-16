package com.github.IkJuLim.accommodation_booking.exception.handler;

import com.github.IkJuLim.accommodation_booking.exception.code.BaseErrorCode;

public class UserExceptionHandler extends GeneralExceptionHandler {
    public UserExceptionHandler(BaseErrorCode errorCode) {
        super(errorCode);
    }
}
