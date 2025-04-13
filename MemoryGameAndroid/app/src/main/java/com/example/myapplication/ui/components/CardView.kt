package com.example.myapplication.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.model.Card

@Composable
fun CardView(
    card: Card,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val backgroundColor = when (card.color) {
        "Vermelho" -> Color.Red.copy(alpha = 0.8f)
        "Azul" -> Color.Blue.copy(alpha = 0.8f)
        "Amarelo" -> Color.Yellow.copy(alpha = 0.8f)
        "Preto" -> Color.Black.copy(alpha = 0.8f)
        else -> Color.Gray.copy(alpha = 0.8f)
    }

    Box(
        modifier = modifier
            .aspectRatio(1f)
            .border(2.dp, Color.DarkGray)
            .clickable(enabled = !card.isMatched && !card.isRevealed, onClick = onClick)
            .background(if (card.isRevealed || card.isMatched) backgroundColor else Color(0xFF4CAF50)),
        contentAlignment = Alignment.Center
    ) {
        if (card.isRevealed || card.isMatched) {
            Text(
                text = card.value,
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
        } else {
            Text(
                text = "?",
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}