package com.musalatask.weatherapp.common

sealed class Resource<T>(var data: T? = null, val error: Throwable? = null) {

    class Success<T>(data: T): Resource<T>(data)
    class Error<T>(throwable: Throwable, data: T? = null): Resource<T>(data, throwable)
    class Loading<T>(data: T? = null): Resource<T>(data)
}