package com.ango.pokemon.core.data.model

import com.google.gson.annotations.SerializedName

data class Species(
        val color: PairNameUrl? = null,

        @SerializedName("egg_groups")
        val eggGroup: List<PairNameUrl>? = null
)
