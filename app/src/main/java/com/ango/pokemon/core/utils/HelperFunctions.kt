package com.ango.pokemon.core.utils


fun getPokemonId(pokemonResultUrl: String): Long {
    if (pokemonResultUrl.isNotEmpty()) {
        //remote the last '/' then convert id from string to long
        return pokemonResultUrl.dropLast(1)
                .split("/")
                .last()
                .toLong()
    } else {
        // if url is empty return 1 as a placeholder
        return 1
    }
}