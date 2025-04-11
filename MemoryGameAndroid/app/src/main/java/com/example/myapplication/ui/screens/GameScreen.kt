package com.example.myapplication.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun GameScreen(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "Memory Game ByteMovel",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            ScoreDisplay(name = "Jogador 1", score = 2, isCurrent = true, color = "Vermelho")
            Text(
                text = "VS",
                fontSize = 20.sp,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
            ScoreDisplay(name = "Jogador 2", score = 1, isCurrent = false, color = "Azul")
        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(4),
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(16) {
                // Placeholder para CardView
                Text(
                    text = "?",
                    modifier = Modifier
                        .aspectRatio(1f)
                        .background(Color.Gray)
                        .padding(4.dp),
                    fontSize = 24.sp,
                    color = Color.White,
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )
            }
        }
    }
}

@Composable
private fun ScoreDisplay(
    name: String,
    score: Int,
    isCurrent: Boolean,
    color: String
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(8.dp)
    ) {
        Text(
            text = name,
            fontWeight = FontWeight.Bold,
            color = if (isCurrent) Color.Green else Color.Black
        )
        Text(
            text = "$score pts",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = when (color) {
                "Vermelho" -> Color.Red
                "Azul" -> Color.Blue
                else -> Color.Black
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GameScreenPreview() {
    GameScreen()
}