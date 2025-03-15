import kotlin.collections.get
import kotlin.collections.minusAssign
import kotlin.collections.plusAssign
import kotlin.compareTo
import kotlin.random.Random
import kotlin.text.get

data class Card(val value: String, val color: String, var isRevealed: Boolean = false)
data class Player(val name: String, val color: String, var score: Int = 0)

//construror MemoryGame
class MemoryGame(private val boardSize: Int) {
    private val board = Array(boardSize) { Array<Card?>(boardSize) { null } }
    private val players = mutableListOf<Player>()
    private var currentPlayerIndex = 0

    private val colors = listOf("Vermelho", "Azul", "Amarelo", "Preto")

    init {
        initBoard()
    }

    private fun initBoard() {
        val cards = mutableListOf<Card>()
        val totalPairs = boardSize * boardSize / 2

        // Distribuição dos pairs
        val bluePairs = (totalPairs / 4)
        val redPairs = (totalPairs / 4)
        val yellowPairs = (totalPairs - (redPairs + bluePairs)) - 1

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
                board[i][j] = cards[index] //.apply {isRevealed = true}  //Pode usar para testar com as cartas viradas
                index++
            }
        }
    }

    //Função adicionar jogador
    fun addPlayer(name: String, color: String) {
        players.add(Player(name, color))
    }

    //Função play
    fun play() {
        while (true) {
            printBoard()
            val currentPlayer = players[currentPlayerIndex]
            println("Vez de ${currentPlayer.name} (${currentPlayer.color})")

            val (row1, col1) = getCardPosition("Primeira")
            board[row1][col1]?.isRevealed = true
            printBoard() // Mostra o tabuleiro após virar a primeira carta

            val (row2, col2) = getCardPosition("Segunda")
            board[row2][col2]?.isRevealed = true
            printBoard() // Mostra o tabuleiro após virar a segunda carta

            val card1 = board[row1][col1]!!
            val card2 = board[row2][col2]!!

            // Condição para alterar a pontuação
            if (card1.value == card2.value) {
                println("Acertou!")
                when (card1.color) {
                    "Amarelo" -> currentPlayer.score += 1
                    currentPlayer.color -> currentPlayer.score += 5
                    "Preto" -> currentPlayer.score += 50
                    else -> currentPlayer.score += 1
                }
                card1.isRevealed = true
                card2.isRevealed = true
            } else {
                println("Errou!")
                if (card1.color == "Preto" || card2.color == "Preto") {
                    println("${currentPlayer.name} perdeu o 50 pontos")
                    currentPlayer.score -= 50
                }
                if (card1.color == players[1 - currentPlayerIndex].color) {
                    currentPlayer.score -= 2
                }
                if (currentPlayer.score < 0) currentPlayer.score = 0
                currentPlayerIndex = 1 - currentPlayerIndex

                // Esconder as cartas
                card1.isRevealed = false
                card2.isRevealed = false
            }

            // Condição para declarar quem ganhou
            if (checkWin()) {
                val winner = if (players[0].score > players[1].score) {
                    players[0]
                } else {
                    players[1]
                }
                println("Fim do jogo, o vencedor é ${winner.name } com ${winner.score} pontos!")
                break
            }
        }
    }

    // Função para pegar a posição das cartas
    private fun getCardPosition(cardNumber: String): Pair<Int, Int> {
        while (true) {
            println("Digite a linha e a coluna da $cardNumber carta que deseja revelar:")
            print("Linha: ")
            val row = readLine()?.toIntOrNull()?.minus(1) ?: continue
            print("Coluna: ")
            val col = readLine()?.toIntOrNull()?.minus(1) ?: continue

            if (row in 0 until boardSize && col in 0 until boardSize) {
                if (board[row][col]?.isRevealed == true) {
                    println("A carta da posição informada já está virada, por favor, escolha outra posição.")
                } else {
                    return Pair(row, col)
                }
            } else {
                println("Posição da carta inválida, por favor, insira uma posição válida.")
            }
        }
    }

    // Função para mostrar o board
    private fun printBoard() {
        println("\nTabuleiro:")
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
        println("Pontuação:")
        players.forEach { println("${it.name}: ${it.score} pontos") }
    }

    // Função para verificar se todas as cartas estão viradas(fim de jogo)
    private fun checkWin(): Boolean {
        return board.all { row -> row.all { it?.isRevealed == true } }
    }
}

fun main() {
    println("Bem-vindo ao ByteMovel Memory Game!")
    println("Tamanho do tabuleiro:")
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
    val player1Name = readLine() ?: "PARTICIPANTE01"
    println("Informe o nome do Jogador 2")
    val player2Name = readLine() ?: "PARTICIPANTE02"

    game.addPlayer(player1Name, "Vermelho")
    game.addPlayer(player2Name, "Azul")


    game.play()
    ///
}
