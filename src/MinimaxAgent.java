package src;

import java.util.ArrayList;

public class MinimaxAgent {
  int depth;
  int x = 0;

  public MinimaxAgent(int depth) {
    this.depth = depth;
  }

  /**
   * public static State minimaxDecision(State state) { return state.getActions().stream()
   * .max(Comparator.comparing(MinimaxTemplate::minValue)).get(); }
   */
  public int getAction(State st) throws CloneNotSupportedException {
    double val = max_value(st, depth);
    // return max_value(st, depth);\
    int varp = (int) val; //cast
      return x;
  }

  /**
   * private static double maxValue(State state) { if(state.isTerminal()){ return
   * state.getUtility(); } return state.getActions().stream() .map(MinimaxTemplate::minValue)
   * .max(Comparator.comparing(Double::valueOf)).get(); }
   */
  public double max_value(State st, int d) throws CloneNotSupportedException {
    ArrayList<Integer> children = new ArrayList<Integer>();

    if (d == 0) {
        return st.evaluationFunction();
    } else {

        /**Confirma por colunas onde não se pode jogar*/
        children = st.getLegalActions();

    double v = -10000000;
  double z;
      // double z;
      for (int i = 0; i < children.size(); i++) {

          /**Retorna as possiveis jogadas do adversario*/
     State iind = st.generateSuccessor('O', children.get(i));
     //Teste
     System.out.println("Generation Sucessor max fuction");
     iind.printBoard();
     //Teste
          z = min_value(iind, d);
        //Corte alfa beta
          if (z >= v) {
          v = z;
          this.x = i;
        }
      }
      // System.out.println("x: "+this.x);
      return v;
    }
  }

  /**
   * private static double minValue(State state) { if(state.isTerminal()){ return
   * state.getUtility(); } return state.getActions().stream() .map(MinimaxTemplate::maxValue)
   * .min(Comparator.comparing(Double::valueOf)).get(); }
   */
  public double min_value(State st, int d) throws CloneNotSupportedException {

    ArrayList<Integer> children = new ArrayList<Integer>();
    if (d == 0) return st.evaluationFunction();
    else {
      children = st.getLegalActions();

      double v = 10000000;
      int x = 0;
      double z;
      for (int i = 0; i < children.size(); i++) {
        z = max_value(st.generateSuccessor('X', children.get(i)), d - 1);
        if (z <= v) v = z;
      }
      return v;
    }
  }
}
