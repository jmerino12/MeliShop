package com.core.common.domain.exceptions

class UnexpectedException(cause: Throwable) : Exception("Unexpected error occurred", cause)
