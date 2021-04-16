package com.ango.pokemon.core.extenstion

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners


fun ImageView.loadImage(url: String, cornerRadius: Int) {
    Glide.with(this.context)
            .load(url)
            .centerCrop() //center pokemonImage into image view
            .transform(RoundedCorners(cornerRadius)) // with corner edges
            .into(this)
}