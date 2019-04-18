
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Random;

public class MinimaxAgent {
    int depth;
    int index_Future = 0; //é um indice jogavel
    static boolean  flag_first = true;
    static boolean  flag_second = true;

    public MinimaxAgent(int depth) {
        this.depth = depth;
    }

    /**
     * Envia um estado de um tabuleiro para avaliar e dar um indice
     */
    public int getAction(State st) throws CloneNotSupportedException {
        double valorDoNo = max_value(st, depth);
        return index_Future;
    }

    public int getAction2(State st) throws CloneNotSupportedException {
        double valorDoNo = max_value_player2(st, depth);
        return index_Future;
    }

    /**
     * private static double maxValue(State state) { if(state.isTerminal()){ return
     * state.getUtility(); } return state.getActions().stream() .map(MinimaxTemplate::minValue)
     * .max(Comparator.comparing(Double::valueOf)).get(); }
     *
     * @param st    Estado do tabuleiro
     * @param depth recebe a profundidade.
     */
    public double max_value(State st, int depth) throws CloneNotSupportedException {
        ArrayList<Integer> children = new ArrayList<Integer>();

        if (depth == 0) {
            return (int) st.evaluationFunction(); //esta devolver o valor calculado, nao um indice
        } else {

            /** Confirma por colunas onde não se pode jogar */
            /** children é uma lista das casas disponiveis para jogar */
            children = st.getLegalActions();
            System.out.println(children.size()+ "----");
            double v = -10000000;
            double z;

            if(flag_first){
                this.flag_first =false;
                Collections.shuffle(children);
            }

            //Por cada indice da coluna disponivel gera um tabuleiro e envia
            for(int i =0; i<children.size();i++) {
                /** Retorna as possiveis jogadas do adversario */
                State iind = st.generateSuccessor('O', children.get(i));
// Teste
//      System.out.println("Generation Sucessor max fuction");
//      iind.printBoard();
// Teste
                z = min_value(iind, depth-1);
                // Corte alfa beta
                if (z >= v) {
                    v = z;
                    this.index_Future = i;
                }
            }
            // System.out.println("index_Future: "+this.index_Future);
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

    public double max_value_player2(State st, int depth) throws CloneNotSupportedException {
        ArrayList<Integer> children = new ArrayList<Integer>();

        if (depth == 0) {
            return (int) st.evaluationFunction2(); //esta devolver o valor calculado, nao um indice
        } else {

            /** Confirma por colunas onde não se pode jogar */
            /** children é uma lista das casas disponiveis para jogar */
            children = st.getLegalActions();
            System.out.println(children.size()+ "----");
            double v = -10000000;
            double z;

            if(flag_second){
                this.flag_second =false;
                Collections.shuffle(children);
            }

            //Por cada indice da coluna disponivel gera um tabuleiro e envia
            for(int i =0; i<children.size();i++) {

                /** Retorna as possiveis jogadas do adversario */
                State stateOne = st.generateSuccessor('X', children.get(i));
// Teste
//      System.out.println("Generation Sucessor max fuction");
//      iind.printBoard();
// Teste
                z = min_value_player2(stateOne, depth-1);
                // Corte alfa beta
                if (z >= v) {
                    v = z;
                    this.index_Future = i;
                }
            }
            // System.out.println("index_Future: "+this.index_Future);

            return v;
        }
    }

    public double min_value_player2(State st, int d) throws CloneNotSupportedException {

        ArrayList<Integer> children = new ArrayList<Integer>();
        if (d == 0) return st.evaluationFunction2();
        else {
            children = st.getLegalActions();

            double v = 10000000;
            int x = 0;
            double z;
            for (int i = 0; i < children.size(); i++) {
                z = max_value_player2(st.generateSuccessor('O', children.get(i)), d - 1);
                if (z <= v) v = z;
            }
            return v;
        }
    }

}
