package com.example.rowcounter.CardTypes

import android.content.res.Resources
import com.example.rowcounter.R

class CardInfo(cardType: Int, cardName: String) {
    val cardType: Int
    var cardName: String

    init {
        this.cardType = cardType
        this.cardName = cardName


    }

}