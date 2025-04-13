package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.model.GameState
import com.example.myapplication.ui.screens.GameOverScreen
import com.example.myapplication.ui.screens.GameScreen
import com.example.myapplication.ui.screens.MainMenuScreen
import com.example.myapplication.ui.theme.MemoryGameTheme
import com.example.myapplication.viewmodel.GameViewModel
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MemoryGameTheme {
                MemoryGameApp()
            }
        }
    }
}

@Composable
fun MemoryGameApp(viewModel: GameViewModel = viewModel()) {
    val gameState by viewModel.gameState.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    when (gameState) {
        is GameState.Menu -> MainMenuScreen(
            onStartGame = { size, p1, p2 ->
                viewModel.startGame(size, p1, p2)
            }
        )
        is GameState.Playing -> {
            val state = gameState as GameState.Playing
            GameScreen(
                state = state,
                onCardClick = { row, col ->
                    coroutineScope.launch {
                        viewModel.onCardClick(row, col)
                    }
                }
            )
        }
        is GameState.GameOver -> {
            val state = gameState as GameState.GameOver
            GameOverScreen(
                winner = state.winner,
                onRestart = { viewModel.returnToMenu() }
            )
        }
    }
}