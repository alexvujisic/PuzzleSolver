import java.util.Arrays;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        Puzzle puzzle = new Puzzle();

        int nodesManhattan = 0;
        int nodesHumming = 0;





            if(puzzle.isSolvable()) {
                puzzle.solvePuzzle("M");
                nodesManhattan += puzzle.getG();
            }

        System.out.println("Manhattan: \nTotal Nodes in random Puzzles: "+nodesManhattan);
        System.out.println("Humming: \nTotal Nodes in random Puzzles: "+nodesHumming);
    }
}