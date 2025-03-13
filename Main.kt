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

        // Put card
        repeat(redBluePairs) {
            cards.add(Card("V${it + 1}", "Vermelho"))
            cards.add(Card("V${it + 1}", "Vermelho"))
            cards.add(Card("A${it + 1}", "Azul"))
            cards.add(Card("A${it + 1}", "Azul"))
        }
        repeat(yellowPairs) {
            cards.add(Card("Y${it + 1}", "Amarelo"))
            cards.add(Card("Y${it + 1}", "Amarelo"))
        }
        cards.add(Card("P1", "Preto"))
        cards.add(Card("P1", "Preto"))

        // Embaralhar
        cards.shuffle()

        // Board
        var index = 0
        for (i in 0 until boardSize) {
            for (j in 0 until boardSize) {
                board[i][j] = cards[index]
                index++
            }
        }
    }
}

//fun addPlayer



//fun Play

fun main() {
    println{"Tam tab:"}
    println("a. 4x4")
    println("b. 6x6")
    println("c. 8x8")
    println("d. 10x10")
    val option = readln().lowercase()

    val boardSize = when (option) {
        "a" -> 4
        "b" -> 6
        "c" -> 8
        "d" -> 10
        else -> {
            println("Opção inválida. Usando tamanho padrão 4x4.")
            4
        }
    }
}