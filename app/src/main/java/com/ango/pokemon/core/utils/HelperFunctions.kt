package com.ango.pokemon.core.utils

import android.content.Context
import android.content.res.ColorStateList
import androidx.appcompat.content.res.AppCompatResources.getColorStateList
import com.ango.pokemon.core.app.pokemonColors

//helper function to resolve color string to color state
/*
* @param color: pokemon species color
* @param isBackground: flag to select color
* if true-> select color without opacity, if false -> select color with opacity
* */
fun resolveColor(context: Context, color: String, isBackground: Boolean): ColorStateList {
    val resolvedColor: Int = if (isBackground) {
        pokemonColors.getValue(color).first
    } else {
        pokemonColors.getValue(color).second
    }
    return getColorStateList(context, resolvedColor)
}