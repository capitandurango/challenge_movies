package com.capps.themoviedb.data.network

/**
 * Enumeration that keeps the succes or error message.
 */
sealed class APIResponse <out T: Any> {
    data class Success<out T : Any>(val value: T) : APIResponse<T>()
    data class Error(val message: String, val cause: Exception? = null, val code: Int? = null) : APIResponse<Nothing>()
}