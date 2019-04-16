package src;

import java.util.Scanner;

public class Connect4 {
    public static void main(String[] args) throws CloneNotSupportedException{

        System.out.println("Enter the depth:");
        Scanner in = new Scanner(System.in);

        //Leitura do terminal
        int depth = in.nextInt();
        if( depth >= 0  && depth <= 7  ){
            System.out.println("Jogada Valida");
        }

        MinimaxAgent mma = new MinimaxAgent(depth);
        State estado_tabuleiro=new State(6,7);

        while(true){
            int action = mma.getAction(estado_tabuleiro);
            //System.out.println("WOWOW");
            //jogada do minimax
            estado_tabuleiro = estado_tabuleiro.generateSuccessor('O', action);
            estado_tabuleiro.printBoard();

            //check if O won? for Agetn input
            if(estado_tabuleiro.isGoal('O'))
                break;

            //jogada do utilizador
            int enemy_move = in.nextInt();
            estado_tabuleiro = estado_tabuleiro.generateSuccessor('X', enemy_move);
            estado_tabuleiro.printBoard();

            //check if X won? for user input
            if(estado_tabuleiro.isGoal('X'))
                break;
            //pause
        }



    }
}
