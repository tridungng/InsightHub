package com.bbyoda.insighthub.shared.kernel

sealed class Result<S, F> {

    data class Success<S, F>(val value: S) : Result<S, F>()
    data class Failure<S, F>(val error: F) : Result<S, F>()

    val isSuccess: Boolean get() = this is Success
    val isFailure: Boolean get() = this is Failure

    inline fun <T> map(transform: (S) -> T): Result<T, F> = when (this) {
        is Success -> Success(transform(value))
        is Failure -> Failure(error)
    }

    inline fun <T> flatMap(transform: (S) -> Result<T, F>): Result<T, F> = when (this) {
        is Success -> transform(value)
        is Failure -> Failure(error)
    }

    inline fun <E> mapError(transform: (F) -> E): Result<S, E> = when (this) {
        is Success -> Success(value)
        is Failure -> Failure(transform(error))
    }

    companion object {
        fun <S, F> success(value: S): Result<S, F> = Success(value)
        fun <S, F> failure(error: F): Result<S, F> = Failure(error)
    }
}
