package com.example.repository

sealed class Result<T : Any> {

    class Success<T : Any>(val body: T) : Result<T>()

    class Failure<T : Any>(val error: Exception) : Result<T>()
}