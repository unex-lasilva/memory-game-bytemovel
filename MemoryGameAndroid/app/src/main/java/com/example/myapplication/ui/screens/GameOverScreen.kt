package com.example.myapplication.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.example.myapplication.model.Player

@Composable
fun GameOverScreen(
    winner: Player,
    onRestart: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Fim do Jogo!",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "Vencedor: ${winner.name}",
            fontSize = 24.sp,
            modifier = Modifier.padding(vertical = 16.dp)
        )
        Text(
            text = "Pontuação: ${winner.score}",
            fontSize = 20.sp
        )
        Button(
            onClick = onRestart,
            modifier = Modifier.padding(top = 32.dp)
        ) {
            Text("Jogar Novamente")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GameOverScreenPreview() {
    GameOverScreen(
        winner = Player(name = "Jogador", color = "Azul", score = 100),
        onRestart = {}
    )
}