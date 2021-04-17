package com.ango.pokemon.core.data.model

import com.google.gson.annotations.SerializedName

data class PokemonAbility(

        val ability: PairNameUrl? = null,

        @SerializedName("is_hidden")
        val isHidden: Boolean? = null,

        val slot: Long? = null
)