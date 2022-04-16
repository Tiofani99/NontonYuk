package com.tiooooo.core.utils

sealed class Resource<T>(val data: T? = null, val message: String? = null) {
    class Success<T>(data: T) : Resource<T>(data)
    class Loading<T>(data: T? = null) : Resource<T>(data)
    class Error<T>(message: String, data: T? = null) : Resource<T>(data, message)
}

// for next feature (paging)
sealed class BaseResult<out T : Any, out U : Any> {
    class Success<T : Any>(val data: T) : BaseResult<T, Nothing>()
    class Error<U : Any>(val rawResponse: U) : BaseResult<Nothing, U>()
}