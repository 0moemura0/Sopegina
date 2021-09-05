package com.omoemurao.sopegina

sealed class Resource<T>(
    val data: T? = null,
    val message: String? = null
) {

    private var onSuccess: ((T) -> Unit)? = null
    private var onLoading: ((T?) -> Unit)? = null
    private var onError: ((String, T?) -> Unit)? = null

    class Success<T>(data: T) : Resource<T>(data)
    class Loading<T>(data: T? = null) : Resource<T>(data)
    class Error<T>(message: String, data: T? = null) : Resource<T>(data, message)

    fun handle(op: Resource<T>.() -> Unit) {
        op(this)
        when (this) {
            is Loading -> {
                onLoading?.let { it(data) }
            }
            is Success -> {
                onSuccess?.let { it(data!!) }
            }
            is Error -> {
                onError?.let { it(message!!, data) }
            }
        }
    }

    fun success(onSuccess: (T) -> Unit) {
        this.onSuccess = onSuccess
    }

    fun loading(onLoading: (T?) -> Unit) {
        this.onLoading = onLoading
    }

    fun error(onError: (String, T?) -> Unit) {
        this.onError = onError
    }
}