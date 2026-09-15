package com.github.IkJuLim.accommodation_booking.exception.handler;

import com.github.IkJuLim.accommodation_booking.exception.code.BaseErrorCode;
import com.github.IkJuLim.accommodation_booking.exception.code.ErrorReasonDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GeneralExceptionHandler extends RuntimeException {

    private BaseErrorCode code;

    public ErrorReasonDTO getErrorReason() {
        return this.code.getReason();
    }

    public ErrorReasonDTO getErrorReasonHttpStatus(){
        return this.code.getReasonHttpStatus();
    }
}