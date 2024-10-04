package com.core.network.exception

import retrofit2.HttpException

class ExceptionTranslate {

    companion object {
        fun mapToDomainException(exception: Throwable): Exception {
            return when (exception) {
                is HttpException -> fromHttpExceptionToDomain(exception)
                else -> UnexpectedException(exception)
            }
        }

        private fun fromHttpExceptionToDomain(httpException: HttpException): Exception {
            return when (httpException.code()) {
                401 -> UnauthorizedException()
                404 -> NotFoundException()
                500 -> ServerException()
                else -> TechnicalException(httpException.code())
            }
        }
    }
}
