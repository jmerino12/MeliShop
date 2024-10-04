package com.core.network.exception

class TechnicalException(val codeException: Int): Exception(codeException.toString())