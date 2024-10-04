package com.core.common.domain.exceptions

import androidx.annotation.StringRes

abstract class ProductException(@StringRes val messageResId: Int) : RuntimeException()