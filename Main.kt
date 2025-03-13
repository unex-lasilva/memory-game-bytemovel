import kotlin.random.Random

data class Card(val value: String, val color: String, var isRevealed: Boolean = false)

//cosntruror MG
class MemoryGame(private val boardSize: Int) {
    private val board = Array(boardSize) { Array<Card?>(boardSize) { null } }
    private val colors = listOf("Vermelho", "Azul", "Amarelo", "Preto")

    init {
        initBoard()
    }

    private fun initBoard() {
        val cards = mutableListOf<Card>()
        val totalPairs = boardSize * boardSize / 2

        // Distrib pairs
        val redBluePairs = totalPairs / 2
        val yellowPairs = totalPairs - redBluePairs - 1
        val blackPair = 1

        repeat(redBluePairs) {
            cards.add(Card("V${it + 1}", "Vermelho"))
            cards.add(Card("V${it + 1}", "Vermelho"))
            cards.add(Card("A${it + 1}", "Azul"))
            cards.add(Card("A${it + 1}", "Azul"))
        }

        // Embaralhar
        cards.shuffle()

        var index = 0
        for (i in 0 until boardSize) {
            for (j in 0 until boardSize) {
                board[i][j] = cards[index]
                index++
            }
        }
    }
}

fun main() {

}