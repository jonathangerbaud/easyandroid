package fr.jonathangerbaud.network

import okhttp3.Response
import retrofit2.HttpException


/**
 * Sealed class of HTTP result
 */

sealed class Result<out T : Any> {
    /**
     * Successful result of request without errors
     */
    class Success<out T : Any>(
            val value: T,
            override val response: Response
                         ) : Result<T>(), ResponseResult {
        override fun toString() = "Result.Success{value=$value, response=$response}"
    }

    /**
     * HTTP error
     */
    class Error(
            override val exception: HttpException,
            override val response: Response
               ) : Result<Nothing>(), ErrorResult, ResponseResult {
        override fun toString() = "Result.Error{exception=$exception}"
    }

    /**
     * Network exception occurred talking to the server or when an unexpected
     * exception occurred creating the request or processing the response
     */
    class Exception(
            override val exception: Throwable
                   ) : Result<Nothing>(), ErrorResult {
        override fun toString() = "Result.Exception{$exception}"
    }

}

/**
 * Interface for [Result] classes with [okhttp3.Response]: [Result.Success] and [Result.Error]
 */
interface ResponseResult {
    val response: Response
}

/**
 * Interface for [Result] classes that contains [Throwable]: [Result.Error] and [Result.Exception]
 */
interface ErrorResult {
    val exception: Throwable
}

/**
 * Returns [Result.Success.value] or `null`
 */
fun <T : Any> Result<T>.getOrNull(): T? =
        (this as? Result.Success)?.value

/**
 * Returns [Result.Success.value] or [default]
 */
fun <T : Any> Result<T>.getOrDefault(default: T): T =
        getOrNull() ?: default

/**
 * Returns [Result.Success.value] or throw [throwable] or [ErrorResult.exception]
 */
fun <T : Any> Result<T>.getOrThrow(throwable: Throwable? = null): T {
    return when (this) {
        is Result.Success -> value
        is Result.Error -> throw throwable ?: exception
        is Result.Exception -> throw throwable ?: exception
    }
}


/*sealed class Result<out T : Any>

data class Success<out T : Any>(val data: T) : Result<T>()

data class Failure(val error: Throwable?) : Result<Nothing>()*/