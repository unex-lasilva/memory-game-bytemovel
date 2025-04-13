package com.example.myapplication.model

sealed class GameState {
    object Menu : GameState()
    data class Playing(
        val board: List<List<Card>>,
        val players: List<Player>,
        val currentPlayerIndex: Int,
        val firstSelectedCard: Pair<Int, Int>?,
        val secondSelectedCard: Pair<Int, Int>?
    ) : GameState()
    data class GameOver(val winner: Player) : GameState()
}