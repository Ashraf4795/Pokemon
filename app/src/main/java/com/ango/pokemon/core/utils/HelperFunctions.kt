package com.ango.pokemon.core.utils

import android.content.Context
import android.content.res.ColorStateList
import androidx.appcompat.content.res.AppCompatResources.getColorStateList
import com.ango.pokemon.core.app.pokemonColors

//check the offset and limit of the next page of pokemon
//are within the pokemon count
fun checkPokemonOffset(offset: Int, limit: Int, count: Int): Boolean {
    return offset + limit <= count
}

fun resolveColor(context: Context, color: String, isBackground: Boolean): ColorStateList {
    val resolvedColor: Int = if (isBackground) {
        pokemonColors.getValue(color).first
    } else {
        pokemonColors.getValue(color).second
    }
    return getColorStateList(context, resolvedColor)
}