package com.vlatko.domain

interface ErrorParser {
    fun parse(error: Throwable) : String?
}