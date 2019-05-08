package com.example.rowcounter


import android.graphics.PorterDuff
import android.support.annotation.LayoutRes
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.EditText

fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
}

fun Int?.zeroIfNull(): Int {
    if (this == null) {
        return 0
    } else {
        return this
    }
}

fun EditText.shake() {
    val errorRed = ContextCompat.getColor(this.context, R.color.errorRed)
    this.setHintTextColor(errorRed)
    this.background.mutate().setColorFilter(errorRed, PorterDuff.Mode.SRC_ATOP)
    this.startAnimation(
        AnimationUtils.loadAnimation(this.context, R.anim.shake))
}