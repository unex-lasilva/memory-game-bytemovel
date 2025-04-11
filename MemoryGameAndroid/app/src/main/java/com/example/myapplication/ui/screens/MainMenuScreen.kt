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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MainMenuScreen(
    modifier: Modifier = Modifier
) {
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
            value = "Jogador 1",
            onValueChange = {},
            label = { Text("Nome do Jogador 1") },
            modifier = Modifier.padding(bottom = 16.dp)
        )

        OutlinedTextField(
            value = "Jogador 2",
            onValueChange = {},
            label = { Text("Nome do Jogador 2") },
            modifier = Modifier.padding(bottom = 32.dp)
        )

        Text(
            text = "Tamanho do Tabuleiro",
            fontSize = 18.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        listOf("4x4", "6x6", "8x8").forEach { label ->
            Button(
                onClick = {},
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .padding(vertical = 4.dp)
            ) {
                Text(text = label)
            }
        }

        Button(
            onClick = {},
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .padding(top = 32.dp)
        ) {
            Text(text = "Iniciar Jogo")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainMenuScreenPreview() {
    MainMenuScreen()
}