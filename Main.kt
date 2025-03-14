import kotlin.random.Random

data class Card(val value: String, val color: String, var isRevealed: Boolean = false)
data class Player(val name: String, val color: String, var score: Int = 0)

//cosntruror MG
class MemoryGame(private val boardSize: Int) {
    private val board = Array(boardSize) { Array<Card?>(boardSize) { null } }
    val players = mutableListOf<Player>()
    private val colors = listOf("Vermelho", "Azul", "Amarelo", "Preto")

    init {
        initBoard()
    }

    private fun initBoard() {
        val cards = mutableListOf<Card>()
        val totalPairs = boardSize * boardSize / 2

        // Distrib pairs
        val bluePairs = (totalPairs / 4)
        val redPairs = (totalPairs / 4)
        val yellowPairs = (totalPairs - (redPairs + bluePairs)) - 1
        val blackPair = 1

        // Put card
        repeat(redPairs) {
            cards.add(Card("V${it + 1}", "Vermelho"))
            cards.add(Card("V${it + 1}", "Vermelho"))
        }

        repeat(bluePairs) {
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
                board[i][j] = cards[index].apply {isRevealed = true}//cartas viradas(test)
                index++
            }
        }
    }

    //jogar num loop na fun Play!!!!!!!!
    fun printBoard() {

        println("\nTabul:")
        for (i in 0 until boardSize) {
            for (j in 0 until boardSize) {
                val card = board[i][j]
                if (card?.isRevealed == true) {
                    print("${card.value}(${card.color}) ")
                } else {
                    print("C ")
                }
            }
            println()
        }
    }

    //Função adicionar jogador
    fun addPlayer(name: String, color: String) {
        players.add(Player(name, color))
    }

}







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


    ///
    val game = MemoryGame(boardSize)

    println("Informe o nome do Jogador 1")
    val player1Name = readln()
    println("Informe o nome do Jogador 2")
    val player2Name = readln()

    game.addPlayer(player1Name, "Vermelho")
    game.addPlayer(player2Name, "Azul")

    game.players.forEach { players ->
        println("Nome: ${players.name}, Cor: ${players.color}")
    }

    game.printBoard()
    ///


}