package com.example.myapplication.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.model.Card
import com.example.myapplication.model.GameState
import com.example.myapplication.model.Player
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class GameViewModel : ViewModel() {
    private var boardSize = 4
    private var _board = mutableListOf<MutableList<Card>>()
    val board: List<List<Card>> get() = _board.map { it.toList() }

    private val _gameState = MutableStateFlow<GameState>(GameState.Menu)
    val gameState: StateFlow<GameState> = _gameState.asStateFlow()

    private lateinit var players: List<Player>
    private var currentPlayerIndex = 0

    fun startGame(size: Int, player1Name: String, player2Name: String) {
        boardSize = size
        players = listOf(
            Player(player1Name, "Vermelho"),
            Player(player2Name, "Azul")
        )
        currentPlayerIndex = 0
        initializeBoard()
        _gameState.value = GameState.Playing(
            board = board,
            players = players,
            currentPlayerIndex = currentPlayerIndex,
            firstSelectedCard = null,
            secondSelectedCard = null
        )
    }

    private fun initializeBoard() {
        val cards = mutableListOf<Card>()
        val totalPairs = boardSize * boardSize / 2

        val bluePairs = (totalPairs / 4)
        val redPairs = (totalPairs / 4)
        val yellowPairs = (totalPairs - (redPairs + bluePairs)) - 1

        repeat(redPairs) {
            cards.add(Card("V${it + 1}", "Vermelho"))
            cards.add(Card("V${it + 1}", "Vermelho"))
        }

        repeat(bluePairs) {
            cards.add(Card("A${it + 1}", "Azul"))
            cards.add(Card("A${it + 1}", "Azul"))
        }

        repeat(yellowPairs) {
            cards.add(Card("Y${it + 1}", "Amarelo"))
            cards.add(Card("Y${it + 1}", "Amarelo"))
        }
        cards.add(Card("P1", "Preto"))
        cards.add(Card("P1", "Preto"))

        cards.shuffle()

        _board = List(boardSize) { i ->
            MutableList(boardSize) { j ->
                cards[i * boardSize + j]
            }
        }.toMutableList()
    }

    suspend fun onCardClick(row: Int, col: Int) {
        if (row !in 0 until boardSize || col !in 0 until boardSize) {
            checkMatch() // Chamada para verificar par após delay
            return
        }

        val currentState = _gameState.value as? GameState.Playing ?: return
        val card = _board[row][col]

        when {
            card.isRevealed || card.isMatched -> return

            currentState.firstSelectedCard == null -> {
                _board[row][col] = card.copy(isRevealed = true)
                _gameState.value = currentState.copy(
                    firstSelectedCard = Pair(row, col),
                    board = _board.map { it.toList() }
                )
            }

            currentState.secondSelectedCard == null -> {
                _board[row][col] = card.copy(isRevealed = true)
                val newState = currentState.copy(
                    secondSelectedCard = Pair(row, col),
                    board = _board.map { it.toList() }
                )
                _gameState.value = newState

                delay(1000) // Delay antes de verificar o par
                checkMatch() // Verifica o par e atualiza a pontuação
            }
        }
    }

    private suspend fun checkMatch() {
        val state = _gameState.value as? GameState.Playing ?: return
        val (row1, col1) = state.firstSelectedCard ?: return
        val (row2, col2) = state.secondSelectedCard ?: return

        val card1 = _board[row1][col1]
        val card2 = _board[row2][col2]
        val updatedPlayers = players.toMutableList()
        val currentPlayer = updatedPlayers[currentPlayerIndex]

        if (card1.value == card2.value) { // Par correto
            // Atualiza a pontuação imediatamente
            when (card1.color) {
                "Amarelo" -> currentPlayer.score += 2  // Ajustado para 2 conforme sua descrição
                "Vermelho" -> currentPlayer.score += 5
                "Azul" -> currentPlayer.score += 5
                "Preto" -> currentPlayer.score += 50
                else -> currentPlayer.score += 1
            }
            _board[row1][col1] = card1.copy(isMatched = true, isRevealed = true)
            _board[row2][col2] = card2.copy(isMatched = true, isRevealed = true)
        } else { // Sem par
            if (card1.color == "Preto" || card2.color == "Preto") {
                currentPlayer.score = 0 // Zera os pontos se uma carta preta está envolvida
            } else {
                currentPlayer.score -= 5 // Penalidade por erro
                if (currentPlayer.score < 0) currentPlayer.score = 0
            }
            _board[row1][col1] = card1.copy(isRevealed = false)
            _board[row2][col2] = card2.copy(isRevealed = false)
            currentPlayerIndex = 1 - currentPlayerIndex // Troca o jogador
        }

        // Atualiza o estado do jogo
        updatedPlayers[currentPlayerIndex] = currentPlayer
        val newState = state.copy(
            firstSelectedCard = null,
            secondSelectedCard = null,
            board = _board.map { it.toList() },
            players = updatedPlayers,
            currentPlayerIndex = currentPlayerIndex
        )
        _gameState.value = newState

        // Verifica fim de jogo
        if (_board.all { row -> row.all { it.isMatched } }) {
            _gameState.value = GameState.GameOver(
                winner = players.maxByOrNull { it.score }!!
            )
        }
    }

    fun returnToMenu() {
        _gameState.value = GameState.Menu
    }
}