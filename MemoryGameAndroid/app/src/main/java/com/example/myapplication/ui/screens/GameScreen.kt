package com.example.myapplication.ui.screens

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.model.GameState
import com.example.myapplication.model.Player
import com.example.myapplication.ui.components.CardView
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.viewmodel.GameViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun GameScreen(
    state: GameState.Playing,
    onCardClick: (Int, Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Cabeçalho com título e placar
        Text(
            text = "Memory Game ByteMovel",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        // Placar atualizado automaticamente
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            ScoreDisplay(
                player = state.players[0],
                isCurrent = state.currentPlayerIndex == 0
            )
            Text(
                text = "VS",
                fontSize = 20.sp,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
            ScoreDisplay(
                player = state.players[1],
                isCurrent = state.currentPlayerIndex == 1
            )
        }

        // Tabuleiro de cartas
        LazyVerticalGrid(
            columns = GridCells.Fixed(state.board.size),
            modifier = Modifier
                .weight(1f)
                .animateContentSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(state.board.size * state.board.size) { index ->
                val row = index / state.board.size
                val col = index % state.board.size
                val card = state.board[row][col]

                CardView(
                    card = card,
                    onClick = { onCardClick(row, col) },
                    modifier = Modifier
                        .aspectRatio(1f)
                        .padding(4.dp)
                )
            }
        }

        // Verificação automática após selecionar duas cartas
        if (state.firstSelectedCard != null && state.secondSelectedCard != null) {
            LaunchedEffect(state.firstSelectedCard, state.secondSelectedCard) {
                delay(1000)
                onCardClick(-1, -1) // Força a verificação
            }
        }
    }
}

@Composable
private fun ScoreDisplay(
    player: Player,
    isCurrent: Boolean
) {
    var scale by remember { mutableStateOf(1f) }

    LaunchedEffect(player.score) {
        scale = 1.2f
        delay(200)
        scale = 1f
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(8.dp)
            .graphicsLayer(scaleX = scale, scaleY = scale)
    ) {
        Text(
            text = player.name,
            fontWeight = FontWeight.Bold,
            color = if (isCurrent) Color.Green else Color.Black
        )
        Text(
            text = "${player.score} pts",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = when (player.color) {
                "Vermelho" -> Color.Red
                "Azul" -> Color.Blue
                "Amarelo" -> Color.Yellow
                else -> Color.Black
            }
        )
    }
}

@Composable
fun MemoryGameApp(viewModel: GameViewModel = viewModel()) {  // Corrigido: adicionado parênteses
    val gameState by viewModel.gameState.collectAsState()
    val coroutineScope = rememberCoroutineScope()  // Cria um escopo de corrotina

    when (val state = gameState) {
        is GameState.Menu -> MainMenuScreen(
            onStartGame = { size, p1, p2 ->
                viewModel.startGame(size, p1, p2)
            }
        )
        is GameState.Playing -> GameScreen(
            state = state,
            onCardClick = { row, col ->
                // Envolve em coroutineScope para chamar função suspensa
                coroutineScope.launch {
                    viewModel.onCardClick(row, col)
                }
            }
        )
        is GameState.GameOver -> GameOverScreen(
            winner = state.winner,
            onRestart = {
                viewModel.returnToMenu()
            }
        )
    }
}