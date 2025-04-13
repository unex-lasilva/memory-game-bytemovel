package com.example.myapplication.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.myapplication.model.Player

@Composable
fun PlayerInfo(
    player: Player,
    isCurrentPlayer: Boolean,
    modifier: Modifier = Modifier
) {
    // Animação quando os pontos mudam
    var lastScore by remember { mutableStateOf(player.score) }
    val scale by animateFloatAsState(
        targetValue = if (player.score != lastScore) 1.2f else 1f,
        animationSpec = tween(durationMillis = 200),
        label = "scoreAnimation"
    )

    LaunchedEffect(player.score) {
        if (player.score != lastScore) {
            lastScore = player.score
        }
    }

    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = if (isCurrentPlayer) Color.LightGray.copy(alpha = 0.5f) else Color.White
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = if (isCurrentPlayer) 8.dp else 4.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Nome do jogador
            Text(
                text = player.name,
                fontWeight = FontWeight.Bold,
                color = when (player.color) {
                    "Vermelho" -> Color.Red
                    "Azul" -> Color.Blue
                    "Amarelo" -> Color(0xFFFFA000) // Amarelo mais escuro
                    "Preto" -> Color.Black
                    else -> Color.DarkGray
                }
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Pontuação com animação
            Text(
                text = "${player.score} pts",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.scale(scale),
                color = if (player.score > lastScore) Color(0xFF388E3C) else Color.Black
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Cor do jogador
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .background(
                        color = when (player.color) {
                            "Vermelho" -> Color.Red
                            "Azul" -> Color.Blue
                            "Amarelo" -> Color.Yellow
                            "Preto" -> Color.Black
                            else -> Color.Gray
                        },
                        shape = MaterialTheme.shapes.small
                    )
            )
        }
    }
}