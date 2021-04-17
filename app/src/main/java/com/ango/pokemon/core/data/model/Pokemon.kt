package com.ango.pokemon.core.data.model

data class Pokemon(
    val count: Long? = null,
    val next: String? = null,
    val previous: Any? = null,
    val results: List<Result>? = null
)

data class Result(
    val name: String? = null,
    val url: String? = null
)