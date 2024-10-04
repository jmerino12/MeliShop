package com.core.network.exception

class UnexpectedException(cause: Throwable) : Exception("Unexpected error occurred", cause)
