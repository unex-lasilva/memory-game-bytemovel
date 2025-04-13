package com.example.myapplication.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MainMenuScreen(
    onStartGame: (Int, String, String) -> Unit,
    modifier: Modifier = Modifier
) {
    var player1Name by remember { mutableStateOf("Jogador 1") }
    var player2Name by remember { mutableStateOf("Jogador 2") }
    var selectedSize by remember { mutableStateOf(4) }

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Memory Game ByteMovel",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        OutlinedTextField(
            value = player1Name,
            onValueChange = { player1Name = it },
            label = { Text("Nome do Jogador 1") },
            modifier = Modifier.padding(bottom = 16.dp)
        )

        OutlinedTextField(
            value = player2Name,
            onValueChange = { player2Name = it },
            label = { Text("Nome do Jogador 2") },
            modifier = Modifier.padding(bottom = 32.dp)
        )

        Text(
            text = "Tamanho do Tabuleiro",
            fontSize = 18.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        val sizes = listOf(4 to "4x4 (Fácil)", 6 to "6x6 (Médio)", 8 to "8x8 (Difícil)")
        sizes.forEach { (size, label) ->
            Button(
                onClick = { selectedSize = size },
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .padding(vertical = 4.dp),
                enabled = selectedSize != size
            ) {
                Text(text = label)
            }
        }

        Button(
            onClick = { onStartGame(selectedSize, player1Name, player2Name) },
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .padding(top = 32.dp)
        ) {
            Text(text = "Iniciar Jogo")
        }
    }
}
