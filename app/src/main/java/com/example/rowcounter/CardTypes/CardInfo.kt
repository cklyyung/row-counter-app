package com.example.rowcounter.CardTypes

open class CardInfo(cardType: Int, cardName: String) {
    val cardType: Int = cardType
    var cardName: String = cardName

}

open class CounterCardInfo(cardType: Int, cardName: String, displayValue: Int) : CardInfo(cardType, cardName) {
    var displayValue: Int = displayValue

}

class SecondaryCounterCardInfo(cardType: Int, cardName: String, displayValue: Int, isLinked: Boolean, repeatLimit: Int)
    : CounterCardInfo(cardType, cardName, displayValue) {
    var isLinked : Boolean = isLinked
    var repeatLimit : Int = repeatLimit

}