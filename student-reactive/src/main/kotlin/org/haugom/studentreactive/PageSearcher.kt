package org.haugom.studentreactive

interface PageSearcher {
    fun searchPageFor(url: String, term: String): Result
}