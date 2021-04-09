import java.io.BufferedReader
import java.io.InputStreamReader
import kotlin.concurrent.timer

object TicTacToe {
    var game: Game? = null
    var count = 0
    var useInput: String? = null
    private var gameMode = 0
    private var validinput = false

    @JvmStatic
    fun main(args: Array<String>) {
        val minimumGameSize = 1
        val maximumGameSize = 26

        //When program starts, user is met with a welcome message
        //all the text will be as a  constants here
        println("\n\t${Constants.WLCOME_MSG}")
        println("\n\t${Constants.SELECT_GAME_MODE_MSG}")
        println("\n\t    ${Constants.HUMAN_COMPUTER_MSG}")
        println("\n\t    ${Constants.COMPUTER_HUMAN_MSG}")
        useInput = getInput("\n\t${Constants.MODE_PLAY_MSG} ")

        //Keep asking for an answer from the user until we get a 1 or a 2
        gameMode(useInput) //gameMode() is defines below
        println("\n\t${Constants.LARG_GRID_MSG} ")
        useInput = getInput("\n\tPlease enter an integer between $minimumGameSize and $maximumGameSize: ")

        //validate user input for game size
        validinput = false
        //use when instead while statement
        when (!validinput) {
            useInput!!.isNotEmpty() && useInput!!.substring(0, 1)
                .matches("[1-9]".toRegex()) && minimumGameSize <= useInput!!.toInt() && useInput!!.toInt() <= maximumGameSize
            -> validinput = true
            else -> useInput = getInput("\n\tYou must enter a number between $minimumGameSize and $maximumGameSize: ")

        }

        //issue warning for game sizes larger than 15
        //use takeIf instead if normal
        useInput?.takeIf { it.toInt() > 15 }?.let {
            println("\n\t${Constants.WORNING_MSG}")
            getInput("")
        }
        val gameSize: Int = useInput!!.toInt()

        //Create a new Game instance
        game = Game(gameSize)

        //create an array of two players
        val players = arrayOfNulls<Player>(2)

        //set players to AI or Human depending on game mode
        if (gameMode == 1) {
            players[0] = Player(Constants.Human_KEY)
            players[1] = Player(Constants.AI_KEY)
        } else {
            players[0] = Player(Constants.AI_KEY)
            players[1] = Player(Constants.AI_KEY)
        }

        //Draw the blank board initially to show user which columns and rows to choose from
        println(game!!.output())

        //until the game is over, go back and forth between players in players array
        //output the game map to the screen after each move
        while (!game!!.finished) {
            for (player in players) {
                player!!.go()
                println("\n${game!!.output()}")
                count += 1
                if (game!!.finished) {
                    break
                }
            }
        }

        //output an ending message to the game
        if (game!!.draw) {
            println("\n\t${Constants.CAT_GAME_MSG}")
        } else {

            //count variable from earlier is used to decide who went last and therefore won.
            if (count % 2 == 1) {
                println("\n\t${Constants.X_WIN_MSG}")
            } else {
                println("\n\t${Constants.O_WIN_MSG}")
            }
        }
    }

    //encapsulated code for input stream buffer
    fun getInput(prompt: String?): String {
        val stdin = BufferedReader(InputStreamReader(System.`in`))
        print(prompt)
        System.out.flush()
        return try {
            stdin.readLine()
        } catch (e: Exception) {
            "Error: " + e.message
        }
    }

    //validates user input and sets the game mode
    private fun gameMode(mUserInput: String?) {
        var mUserInput = mUserInput
        validinput = false

        when (!validinput) {
            mUserInput?.length == 1 && mUserInput.substring(0, 1).matches("[1-2]".toRegex())
            -> validinput = true
            else ->
                mUserInput = getInput("\n\t${Constants.ENTER_1_OR_2_MSG} ")
        }

        //Set user input to gameMode for use later
        gameMode = mUserInput!!.toInt()
    }
}