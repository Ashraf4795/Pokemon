package com.ango.pokemon.core.app

import com.ango.pokemon.R

const val DATABASE_NAME = "pokemon_database_1.0"
const val BASE_POKEMON_URL = "https://pokeapi.co/api/v2/"

const val LOAD_LIMIT = 20
const val ALL_POKEMON_LOADED = "All pokemon loaded!"

const val DEFAULT_IMAGE_URL =
    "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/4.png"

const val POKEMON_DETAILS_NUM_PAGES = 3

const val DEFAULT_COLOR = "green"
val pokemonColors = mapOf<String, Pair<Int, Int>>(
    "green" to Pair(R.color.green, R.color.green_opacity),
    "brown" to Pair(R.color.brown, R.color.brown_opacity),
    "gray" to Pair(R.color.gray, R.color.gray_opacity),
    "blue" to Pair(R.color.blue, R.color.blue_opacity),
    "pink" to Pair(R.color.pink, R.color.pink_opacity),
    "purple" to Pair(R.color.purple, R.color.purple_opacity),
    "red" to Pair(R.color.red, R.color.red_opacity),
    "yellow" to Pair(R.color.yellow, R.color.yellow_opacity),
    "white" to Pair(R.color.white, R.color.white),
    "black" to Pair(R.color.black, R.color.black),
)
