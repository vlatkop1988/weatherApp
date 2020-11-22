package com.vlatko.domain

interface IErrorParser {
    fun parse(error: Throwable) : String?
}