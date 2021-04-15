package com.ango.pokemon.core.data.model

import com.google.gson.annotations.Expose

data class PokemonMove(
        val move: PairNameUrl? = null,

        @Expose(deserialize = false)
        val versionGroupDetails: Any? = null
)

