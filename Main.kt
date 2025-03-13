import kotlin.random.Random

data class Card(val value: String, val color: String, var isRevealed: Boolean = false)

//cosntruror MG
class MemoryGame(private val boardSize: Int) {
    private val board = Array(boardSize) { Array<Card?>(boardSize) { null } }
    private val colors = listOf("Vermelho", "Azul", "Amarelo", "Preto")

    private fun initBoard() {

    }
}

fun main() {

}