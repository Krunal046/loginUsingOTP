package com.example.demo.utility

enum class Status {
    SUCCESS,
    ERROR,
    LOADING,
    FAILED
}

data class Resource<out T>(val status: Status, val data: T?, val message: String?) {

    companion object {

        fun <T> success(data: T?): Resource<T> {
            return Resource(Status.SUCCESS, data, null)
        }

        fun <T> error(error: String, data: T?): Resource<T> {
            return Resource(Status.ERROR, data, error)
        }

        fun <T> failed(error: String, data: T?): Resource<T> {
            return Resource(Status.FAILED, data, error)
        }

        fun <T> loading(data: T?): Resource<T> {
            return Resource(Status.LOADING, data, null)
        }
    }
}

interface onApiResponse<T> {
    fun onSuccess(body: T?)
    fun onError(error: Throwable?)
    fun onLoading(msg: String)
    fun onFailed(msg: String)
}
