package com.ango.pokemon.core.extenstion

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners

/*
    image view extension fun, to load image inside it
    @param url: image url to be loaded
    @param corner radius: if the loaded image need to be rounded
*/
fun ImageView.loadImage(url: String, cornerRadius: Int) {
    Glide.with(this.context)
            .load(url)
            .centerCrop() //center pokemonImage into image view
            .transform(RoundedCorners(cornerRadius)) // with corner edges
            .into(this)
}