package com.example.myapplication.model

data class Card(
    val value: String,
    val color: String,
    var isRevealed: Boolean = false,
    var isMatched: Boolean = false
)