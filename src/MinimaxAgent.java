package src;

import java.util.ArrayList;
import java.util.Random;

public class MinimaxAgent {
  int depth;
  int index_Future = 0; //é um indice jogavel
  boolean flag_for_first = true;
  public MinimaxAgent(int depth) {
    this.depth = depth;
  }

  /** Envia um estado de um tabuleiro para avaliar e dar um indice */
  public int getAction(State st, char player) throws CloneNotSupportedException {
     double valorDoNo = max_value(st, depth, player);
    System.out.println(valorDoNo);
    return index_Future;
  }

  /**
   * private static double maxValue(State state) { if(state.isTerminal()){ return
   * state.getUtility(); } return state.getActions().stream() .map(MinimaxTemplate::minValue)
   * .max(Comparator.comparing(Double::valueOf)).get(); }
   *
   * @param st Estado do tabuleiro
   * @param depth recebe a profundidade.
   */
  public double max_value(State st, int depth, char player) throws CloneNotSupportedException {
    ArrayList<Integer> children = new ArrayList<Integer>();
    char maximize_player;

    if(player == 'X') {
        maximize_player ='X';
    }else{
        maximize_player = 'O';
    }

    if (depth == 0) {
      return  st.evaluationFunction();
    } else {

      /** Confirma por colunas onde não se pode jogar */
      /** children é uma lista das casas disponiveis para jogar */
      children = st.getLegalActions();

      double alpha = -10000000;
      double z;

        for (int i = children.size()-1; i >= 0; i--) {

            /** Retorna as possiveis jogadas do adversario */
            State iind = st.generateSuccessor(maximize_player, children.get(i));
            // Teste
            //      System.out.println("Generation Sucessor max fuction");
            //      iind.printBoard();
            // Teste
            z = min_value(iind, depth-1, player);
            // Corte alfa beta
            if (z >= alpha) {
                alpha = z;
                this.index_Future = i;
            }
            System.out.println(this.index_Future);
        }
      // Por cada indice da coluna disponivel gera um tabuleiro e envia
//      for (int i = 0; i < children.size(); i++) {
//
//        /** Retorna as possiveis jogadas do adversario */
//        State iind = st.generateSuccessor(maximize_player, children.get(i));
//        // Teste
//        //      System.out.println("Generation Sucessor max fuction");
//        //      iind.printBoard();
//        // Teste
//        z = min_value(iind, depth, player);
//        // Corte alfa beta
//        if (z >= alpha) {
//          alpha = z;
//          this.index_Future = i;
//        }
//      }
      // System.out.println("index_Future: "+this.index_Future);
        if(flag_for_first){
            Random rand = new Random();
            this.index_Future = rand.nextInt(6);
            this.flag_for_first = false;
        }

            return alpha;
    }
}

  /**
   * private static double minValue(State state) { if(state.isTerminal()){ return
   * state.getUtility(); } return state.getActions().stream() .map(MinimaxTemplate::maxValue)
   * .min(Comparator.comparing(Double::valueOf)).get(); }
   */
  public double min_value(State st, int d, char player) throws CloneNotSupportedException {
    char minimize_player;
    ArrayList<Integer> children = new ArrayList<Integer>();

      if(player == 'X') {
          minimize_player ='O';
      }else{
          minimize_player = 'X';
      }

    if (d == 0) return st.evaluationFunction();
    else {
      children = st.getLegalActions();

      double v = 10000000;
      int x = 0;
      double z;
      for (int i = 0; i < children.size(); i++) {
        z = max_value(st.generateSuccessor(minimize_player, children.get(i)), d - 1, minimize_player);
        if (z <= v) v = z;
      }
      return v;
    }
  }
}
