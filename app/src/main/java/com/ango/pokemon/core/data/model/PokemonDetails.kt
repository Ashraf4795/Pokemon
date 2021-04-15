package com.ango.pokemon.core.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class PokemonDetails(

        val id: Long? = null,

        val name: String? = null,

        val abilities: List<PokemonAbility>? = null,

        @SerializedName("base_experience")
        val baseExperience: Long? = null,

        val forms: List<PairNameUrl>? = null,

        @Expose(deserialize = false)
        val gameIndices: Any? = null,

        val height: Long? = null,

        @Expose(deserialize = false)
        val heldItems: List<Any?>? = null,

        @SerializedName("is_default")
        val isDefault: Boolean? = null,

        @Expose(deserialize = false)
        val locationAreaEncounters: String? = null,

        @SerializedName("moves")
        val moves: List<PokemonMove>? = null,

        @Expose(deserialize = false)
        val order: Long? = null,

        @Expose(deserialize = false)
        val pastTypes: Any? = null,

        @Expose(deserialize = false)
        val species: Any? = null,

        val sprites: Sprites? = null,

        val stats: List<Stat>? = null,

        val types: List<Type>? = null,

        val weight: Long? = null
)














