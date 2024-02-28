import java.util.Arrays;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        //Puzzle puzzle = new Puzzle(new int[]{1, 2, 3, 4, 6, 0, 7, 5, 8}, 0);
        //Puzzle puzzle = new Puzzle(new int[]{5, 2, 0, 4, 6, 7, 1, 8, 3}, 0);

        PuzzleStatistics hammingStatistics = new PuzzleStatistics("H");
        PuzzleStatistics manhattanStatistics = new PuzzleStatistics("M");

        int numberOfPuzzles = 4;

        for(int i = 0; i < numberOfPuzzles; i++){
            Puzzle puzzle = new Puzzle();
            puzzle.printPuzzle();

            // Solving with Hamming
            PuzzleStatistics hammingIteration = puzzle.solvePuzzle("H");
            hammingStatistics.addToStatistic(hammingIteration.getTotalExpandedNodes(), hammingIteration.getDepth(), hammingIteration.getComputationTimeMillis());

            // Solving with Manhattan
            PuzzleStatistics manhattanIteration = puzzle.solvePuzzle("M");
            manhattanStatistics.addToStatistic(manhattanIteration.getTotalExpandedNodes(), manhattanIteration.getDepth(), manhattanIteration.getComputationTimeMillis());
        }
        System.out.println("Total Number of Puzzles solved: " + numberOfPuzzles + "\n");
        manhattanStatistics.printStatistics(numberOfPuzzles);
        System.out.println("\n");
        hammingStatistics.printStatistics(numberOfPuzzles);
    }
}