package org.haugom.studentreactive

interface ResultFactory {
    fun Normal(url: String, term: String, found: Boolean): Result
    fun Exceptionally(url: String, term: String, exception: Exception): Result
}