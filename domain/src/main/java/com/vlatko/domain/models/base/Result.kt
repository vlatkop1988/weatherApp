package com.vlatko.domain.models.base

enum class State { SUCCESS, ERROR }

class Result<R, E>(val response: R? = null, val error: E? = null) {

    val result = if (error == null) State.SUCCESS else State.ERROR

    fun process(success: ((R) -> Unit)?, error: ((E?) -> Unit)?) = when (result) {
        State.SUCCESS -> if (response == null) error?.invoke(this.error) else success?.invoke(response)
        State.ERROR -> error?.invoke(this.error)
    }

    fun process(success: ((R) -> Unit)?) =
        process(success, {})
}