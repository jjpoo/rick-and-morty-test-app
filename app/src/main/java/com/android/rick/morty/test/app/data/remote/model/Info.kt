package com.android.rick.morty.test.app.data.remote.model

data class Info (
    val count: Int,
    val pages: Int,
    val next: String?,
    val prev: String?
)