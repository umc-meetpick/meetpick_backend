package com.umc.meetpick.common.exception.handler;

import com.umc.meetpick.common.exception.GeneralException;
import com.umc.meetpick.common.response.BaseErrorCode;

public class GeneralHandler extends GeneralException {
    public GeneralHandler(BaseErrorCode code) {
        super(code);
    }
}
