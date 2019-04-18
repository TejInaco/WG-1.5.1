
import java.util.Scanner;

public class Connect4 {


    public static void main(String[] args) throws CloneNotSupportedException {
        int depth_player1 = 0;
        int depth_player2 = 0;

        do {
            System.out.println("Enter the depth for agent 1 [1-oo]:");
            Scanner in = new Scanner(System.in);
            //Leitura do terminal
            depth_player1 = in.nextInt();

            Scanner in2 = new Scanner(System.in);
            System.out.println("Enter the depth for agent 2 [1-oo]:");
            depth_player2 = in2.nextInt();

        } while (validateInputDepth(depth_player1, depth_player2));


        MinimaxAgent agent_1_X = new MinimaxAgent(depth_player1);
        MinimaxAgent agent_2_0 = new MinimaxAgent(depth_player2);

        State estado_tabuleiro = new State(Macros.ROW, Macros.COL);

        while (true) {
            int action = agent_1_X.getAction(estado_tabuleiro);
            //jogada do minimax
            estado_tabuleiro = estado_tabuleiro.generateSuccessor('O', action);
            estado_tabuleiro.printBoard();

            //check if O won? for Agetn input
            if (estado_tabuleiro.isGoal('O'))
                break;

//            continuePlay();
//FOR user input
//            Scanner in = new Scanner(System.in);
//            int mymove = in.nextInt();
//            estado_tabuleiro = estado_tabuleiro.generateSuccessor('X', mymove);

//            //Codigo para o agente 2
            int action2 = agent_2_0.getAction2(estado_tabuleiro);
            estado_tabuleiro = estado_tabuleiro.generateSuccessor('X', action2);
            estado_tabuleiro.printBoard();

            //teste prints - BEGIN
        //    estado_tabuleiro.nlinhas3('X');
       //     estado_tabuleiro.nlinhas2('O');
            //teste prints - END

            //teste prints -BEGIN
//            System.out.println( "Player 1" );
//            estado_tabuleiro.nlinhas3('X');
     //       estado_tabuleiro.nlinhas2('X');
            //teste prints -END


            if (estado_tabuleiro.isGoal('X'))
                break;
            //pause
            continuePlay();
        }


    }

    /**
     * Validar inteiros recebidos no terminal
     */
    static boolean validateInputDepth(int depth_um, int depth_dois) {
        if (depth_um > 0 && depth_dois > 0) {
            return false;
        }
        System.out.println(" -- Repeat Input Values -- ");
        return true;
    }

    static void continuePlay() {
        System.out.println(" -- Enter 1 to continue - 0 to exit -- ");
        Scanner in = new Scanner(System.in);
        if (in.nextInt() == 0) {
            System.exit(0);
        }
    }
}