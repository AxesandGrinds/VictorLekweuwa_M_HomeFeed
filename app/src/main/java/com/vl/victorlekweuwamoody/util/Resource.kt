package com.vl.victorlekweuwamoody.util

/*
* This sealed class is used to load the data into the flow after the nextwork response returns
* data.
* */
sealed class Resource<T>(
    val data: T? = null,
    val error: Throwable? = null
) {
    class Success<T>(data: T) : Resource<T>(data)
    class Loading<T>(data: T? = null) : Resource<T>(data)
    class Error<T>(throwable: Throwable, data: T? = null) : Resource<T>(data, throwable)
}