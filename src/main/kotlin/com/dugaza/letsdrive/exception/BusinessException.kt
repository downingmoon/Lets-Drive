package com.dugaza.letsdrive.exception

import com.dugaza.letsdrive.util.MessageConverter


class BusinessException(
    errorCode: ErrorCode
): RuntimeException() {
    override var message: String = MessageConverter.getMessage(errorCode.code)
    var httpStatus = errorCode.status

}
