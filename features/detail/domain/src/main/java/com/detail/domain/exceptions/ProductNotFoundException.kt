package com.detail.domain.exceptions

import androidx.annotation.StringRes
import com.core.common.domain.exceptions.ProductException

class ProductNotFoundException(@StringRes messageResId: Int) : ProductException(messageResId)