import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        /*System.out.println("Starting Pattern:");
        Puzzle.printPuzzle(puzzle.getPuzzle());


        System.out.println("is solvable: " + puzzle.isSolvable());
        Puzzle.solveAStar(puzzle, "H");*/

        Puzzle puzzle = new Puzzle();
        String heuristic = "M";
        int totalNodes = 0;
        for(int i = 0; i < 100; i++){
            int[] puzzlePattern = generatePuzzle();
            System.out.println(Arrays.toString(puzzlePattern));
            //int[] puzzlePattern = {3, 0, 4, 5, 8, 7, 6, 2, 1};
            puzzle.setPuzzle(puzzlePattern);
            puzzle.setMisplaced();
            puzzle.setManhattanDistance();
            if(puzzle.isSolvable()){
                totalNodes += Puzzle.solveAStar(puzzle, heuristic);
            }

        }

        System.out.println("Total Nodes: " + totalNodes);

    }


    //function to generate random pattern for puzzle
    public static int[] generatePuzzle(){
        int[] solvedPuzzle = {1, 2, 3, 4, 5, 6, 7, 8, 0};
        int[] randomPattern = Arrays.copyOf(solvedPuzzle, solvedPuzzle.length);

        Random random = new Random();
        for (int i = 0; i < randomPattern.length; i++) {
            int index = random.nextInt(i + 1);
            int temp = randomPattern[i];
            randomPattern[i] = randomPattern[index];
            randomPattern[index] = temp;
        }
        return randomPattern;
    }
}


