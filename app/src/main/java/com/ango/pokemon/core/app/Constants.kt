package com.ango.pokemon.core.app

import com.ango.pokemon.R


const val DATABASE_NAME = "pokemon_database_1.0"
const val BASE_POKEMON_URL = "https://pokeapi.co/api/v2/"

/*
 number of pages displayed inside pokemonDetails fragment
 to display statistics and infomation about a pokemon object
*/
const val POKEMON_DETAILS_NUM_PAGES = 2

//for pokemon species with color=null, use this default value
const val DEFAULT_COLOR = "green"

/*
map of colors to set pokemon item background color
based on pokemon.species.color
key -> pokemon species color
value -> pair of the resource color and a duplicate color with opacity
*/
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

//base state keys
const val HP = "hp"
const val ATTACK = "attack"
const val DEFENSE = "defense"
const val SPECIAL_ATTACK = "special-attack"
const val SPECIAL_DEFENSE = "special-defense"
const val SPEED = "speed"

//number of pokemon state full power
const val TOTAL_STATE_FULL_POWER = 600f
