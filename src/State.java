package src;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class State implements Cloneable {
  int rows, cols;
  char[][] board;

  /* basic methods for constructing and proper hashing of State objects */
  public State(int n_rows, int n_cols) {
    this.rows = n_rows;
    this.cols = n_cols;
    this.board = new char[n_rows][n_cols];

    // fill the board up with blanks
    for (int i = 0; i < n_rows; i++) for (int j = 0; j < n_cols; j++) this.board[i][j] = '.';
  }

  public boolean equals(Object obj) {
    // have faith and cast
    State other = (State) obj;
    return Arrays.deepEquals(this.board, other.board);
  }

  public int hashCode() {
    String b = "";
    for (int i = 0; i < board.length; i++) b += String.valueOf(board[0]);
    return b.hashCode();
  }

  public Object clone() throws CloneNotSupportedException {
    State new_state = new State(this.rows, this.cols);
    for (int i = 0; i < this.rows; i++) new_state.board[i] = (char[]) this.board[i].clone();
    return new_state;
  }

  /**
   * Percorre a primeira linha da array, para verificar por colunas cheias
   * 7 posicoes de jogadas possiveis
   * @return list of actions, inteiros das colunas onde a moeda pode cair
   * */
  public ArrayList<Integer> getLegalActions() {
    ArrayList<Integer> actions = new ArrayList<Integer>();
    for (int j = 0; j < this.cols; j++) {
        if (this.board[0][j] == '.') actions.add(j);
    }


    return actions;
  }

  /* returns a State object that is obtained by the agent (parameter)
  performing an action (parameter) on the current state */
  /**
   * @param agent 'X' ou 'O'
   * @param action
   * @return um estado do tabuleiro com uma jogada especificada
   * */
  public State generateSuccessor(char agent, int action) throws CloneNotSupportedException {

    int row;
    for (row = 0;
        row < this.rows && this.board[row][action] != 'X' && this.board[row][action] != 'O';
        row++) ;
    State new_state = (State) this.clone();
    new_state.board[row - 1][action] = agent;

    return new_state;
  }

  /* Print's the current state's board in a nice pretty way */
  public void printBoard() {
    System.out.println(new String(new char[this.cols * 2]).replace('\0', '-'));
    for (int i = 0; i < this.rows; i++) {
      for (int j = 0; j < this.cols; j++) {
        System.out.print(this.board[i][j] + " ");
      }
      System.out.println();
    }
    System.out.println(new String(new char[this.cols * 2]).replace('\0', '-'));
  }

  /**
   * Game won || numero de linhas, colunas ou diagonais com 4 peças semelhantes isto é UMA porque o
   * jogo está ganho Checks rows/columns and diagonals
   */
  public boolean isGoal(char agent) {

    String find = "" + agent + "" + agent + "" + agent + "" + agent;

    // check rows
    for (int i = 0; i < this.rows; i++)
      if (String.valueOf(this.board[i]).contains(find)) return true;

    // check cols
    for (int j = 0; j < this.cols; j++) {
      String col = "";
      for (int i = 0; i < this.rows; i++) col += this.board[i][j];

      if (col.contains(find)) return true;
    }

    // check diagonals
    ArrayList<Point> pos_right = new ArrayList<Point>();
    ArrayList<Point> pos_left = new ArrayList<Point>();

    for (int j = 0; j < this.cols - 4 + 1; j++) pos_right.add(new Point(0, j));
    for (int j = 4 - 1; j < this.cols; j++) pos_left.add(new Point(0, j));
    for (int i = 1; i < this.rows - 4 + 1; i++) {
      pos_right.add(new Point(i, 0));
      pos_left.add(new Point(i, this.cols - 1));
    }

    // check right diagonals
    for (Point p : pos_right) {
      String d = "";
      int x = p.x, y = p.y;
      while (true) {
        if (x >= this.rows || y >= this.cols) break;
        d += this.board[x][y];
        x += 1;
        y += 1;
      }
      if (d.contains(find)) return true;
    }

    // check left diagonals
    for (Point p : pos_left) {
      String d = "";
      int x = p.x, y = p.y;
      while (true) {
        if (y < 0 || x >= this.rows || y >= this.cols) break;
        d += this.board[x][y];
        x += 1;
        y -= 1;
      }
      if (d.contains(find)) return true;
    }
    return false;
  }

  public int central(char agent) {
    // Atribui a cada peca do jogador na casas centrais
    return 0;
  }

  public int nlinhas3(char agent) {

    String findRigth = "" + agent + "" + agent + "" + agent + "."; // 000.
    String findLeft = "." + agent + "" + agent + "" + agent; // .000
    String findLeftCenter = "" + agent + "" + agent + "." + agent; // 00.0
    String findRightCenter = "" + agent + "." + agent + "" + agent; // 0.00

    int counter = 0;

    // check rows
    for (int i = 0; i < this.rows; i++) {
      if (String.valueOf(this.board[i]).contains(findRigth)) {
        counter++;
      }
      if (String.valueOf(this.board[i]).contains(findLeft)) {
        counter++;
      }
      if (String.valueOf(this.board[i]).contains(findLeftCenter)) {
        counter++;
      }
      if (String.valueOf(this.board[i]).contains(findRightCenter)) {
        counter++;
      }
    }

    System.out.println("For das linhas: " + counter);
    int middleCounter = counter;
    // check cols
    for (int j = 0; j < this.cols; j++) {
      String col = "";
      for (int i = 0; i < this.rows; i++) col += this.board[i][j];

      if (col.contains(findRigth)) counter++;
      if (col.contains(findLeft)) counter++;
      if (col.contains(findLeftCenter)) counter++;
      if (col.contains(findLeftCenter)) counter++;
    }
    System.out.println("For colunas: " + (counter - middleCounter));

    int mmCounter = counter;
    // check diagonals
    ArrayList<Point> pos_right = new ArrayList<Point>();
    ArrayList<Point> pos_left = new ArrayList<Point>();

    for (int j = 0; j < this.cols - 4 + 1; j++) pos_right.add(new Point(0, j));
    for (int j = 4 - 1; j < this.cols; j++) pos_left.add(new Point(0, j));
    for (int i = 1; i < this.rows - 4 + 1; i++) {
      pos_right.add(new Point(i, 0));
      pos_left.add(new Point(i, this.cols - 1));
    }

    // check right diagonals
    for (Point p : pos_right) {
      String d = "";
      int x = p.x, y = p.y;

      while (true) {
        try {
          if (x >= this.rows || y >= this.cols) break;
          d += this.board[x][y];
          x += 1;
          y += 1;
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
      if (d.contains(findRigth)) {
        counter++;
      }
      if (d.contains(findLeft)) {
        counter++;
      }
      if (d.contains(findRightCenter)) {
        counter++;
      }
      if (d.contains(findLeftCenter)) {
        counter++;
      }
    }

    // check left diagonals
    for (Point p : pos_left) {
      String d = "";
      int x = p.x, y = p.y;
      while (true) {
        try {
          if (y < 0 || x >= this.rows || y >= this.cols) break;
          d += this.board[x][y];
          x += 1;
          y -= 1;
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
      if (d.contains(findRigth)) {
        counter++;
      }
      if (d.contains(findLeft)) {
        counter++;
      }
      if (d.contains(findRightCenter)) {
        counter++;
      }
      if (d.contains(findLeftCenter)) {
        counter++;
      }
    }
    System.out.println("total counter: " + (counter - mmCounter - middleCounter));

    return counter;
  }

  public int nlinhas2(char agent) {

    String findRigth = "" + agent + "" + agent + "" + "." + "."; // 00..
    String findLeft = "." + "." + "" + agent + "" + agent; // ..00
    String findLeftCenter = "" + agent + "" + "." + "." + agent; // 0..0
    String findRightCenter = "" + "." + agent + agent + "" + "."; // .00.

    int counter = 0;
    // check rows
    for (int i = 0; i < this.rows; i++) {
      if (String.valueOf(this.board[i]).contains(findRigth)) {
        counter++;
      }
      if (String.valueOf(this.board[i]).contains(findLeft)) {
        counter++;
      }
      if (String.valueOf(this.board[i]).contains(findLeftCenter)) {
        counter++;
      }
      if (String.valueOf(this.board[i]).contains(findRightCenter)) {
        counter++;
      }
    }

    System.out.println("For das linhas 2: " + counter);
    int middleCounter = counter;
    // check cols
    for (int j = 0; j < this.cols; j++) {
      String col = "";
      for (int i = 0; i < this.rows; i++) col += this.board[i][j];

      if (col.contains(findRigth)) counter++;
      if (col.contains(findLeft)) counter++;
      if (col.contains(findLeftCenter)) counter++;
      if (col.contains(findLeftCenter)) counter++;
    }
    System.out.println("For colunas 2: " + (counter - middleCounter));

    int mmCounter = counter;
    // check diagonals
    ArrayList<Point> pos_right = new ArrayList<Point>();
    ArrayList<Point> pos_left = new ArrayList<Point>();

    for (int j = 0; j < this.cols - 4 + 1; j++) pos_right.add(new Point(0, j));
    for (int j = 4 - 1; j < this.cols; j++) pos_left.add(new Point(0, j));
    for (int i = 1; i < this.rows - 4 + 1; i++) {
      pos_right.add(new Point(i, 0));
      pos_left.add(new Point(i, this.cols - 1));
    }

    // check right diagonals
    for (Point p : pos_right) {
      String d = "";
      int x = p.x, y = p.y;

      while (true) {
        try {
          if (x >= this.rows || y >= this.cols) break;
          d += this.board[x][y];
          x += 1;
          y += 1;
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
      if (d.contains(findRigth)) {
        counter++;
      }
      if (d.contains(findLeft)) {
        counter++;
      }
      if (d.contains(findRightCenter)) {
        counter++;
      }
      if (d.contains(findLeftCenter)) {
        counter++;
      }
    }

    // check left diagonals
    for (Point p : pos_left) {
      String d = "";
      int x = p.x, y = p.y;
      while (true) {
        try {
          if (y < 0 || x >= this.rows || y >= this.cols) break;
          d += this.board[x][y];
          x += 1;
          y -= 1;
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
      if (d.contains(findRigth)) {
        counter++;
      }
      if (d.contains(findLeft)) {
        counter++;
      }
      if (d.contains(findRightCenter)) {
        counter++;
      }
      if (d.contains(findLeftCenter)) {
        counter++;
      }
    }
    System.out.println("total counter 2: " + (counter - mmCounter - middleCounter));

    return counter;
  }

  // X-1
  // O-2
  public double evaluationFunction() {
    // heuristicas
    //        int agente1 = isGoal('X') - isGoal('O');
    //        int agente2 = 100*agente1 +nlinhas3('X');
    //        int agente3 = 100*agente1 +central('X') - central('O');
    //        int agente4 = 5*agente2 - agente3;

    if (this.isGoal('O')) return 1000.0;
    if (this.isGoal('X')) return -1000.0;

    return 1.0;
  }
}
