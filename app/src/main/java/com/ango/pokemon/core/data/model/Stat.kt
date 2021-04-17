package com.ango.pokemon.core.data.model

import com.google.gson.annotations.SerializedName

data class Stat(
        @SerializedName("base_stat")
        val baseStat: Long? = null,

        val effort: Long? = null,

        val stat: PairNameUrl? = null
)
