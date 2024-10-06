package com.core.common.domain.exceptions

class TechnicalException(val codeException: Int): Exception(codeException.toString())