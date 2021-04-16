package com.ango.pokemon.core.data.model

import com.google.gson.annotations.SerializedName

data class PokemonDetails(

        val id: Long? = null,

        val name: String? = null,

        val abilities: List<PokemonAbility>? = null,

        @SerializedName("base_experience")
        val baseExperience: Long? = null,

        val forms: List<PairNameUrl>? = null,

        val height: Long? = null,

        @SerializedName("is_default")
        val isDefault: Boolean? = null,

        @SerializedName("moves")
        val moves: List<PokemonMove>? = null,

        val sprites: Sprites? = null,

        val stats: List<Stat>? = null,

        val types: List<Type>? = null,

        val weight: Long? = null,
)














