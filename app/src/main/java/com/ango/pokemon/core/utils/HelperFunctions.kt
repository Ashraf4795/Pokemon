package com.ango.pokemon.core.utils

//extract id from pokemon>result>url
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


//check the offset and limit of the next page of pokemon
//are within the pokemon count
fun checkPokemonOffset(offset: Int, limit: Int, count: Int): Boolean {
    return offset + limit <= count
}
