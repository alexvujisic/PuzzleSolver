import java.util.Arrays;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        System.out.println("Type in 1: Right 2: Down 3: Left 4: Up");
        Scanner scan = new Scanner(System.in);
        int[] puzzle2 = {1, 0, 3, 4, 2, 6, 7, 5, 8};
        Puzzle puzzle = new Puzzle(puzzle2);
        Puzzle.printPuzzle(puzzle.getPuzzle());

        while(!Puzzle.isSolved(puzzle.getPuzzle())){
            puzzle.setMisplaced();
            puzzle.setManhattanDistance();
            System.out.println("Misplaced: " + puzzle.getMisplaced() + "Manhattan: " + puzzle.getManhattanDistance());
            System.out.println("Type in\n 1: Right\n 2: Down\n 3: Left\n 4: Up\n");
            Puzzle.printPuzzle(puzzle.getPuzzle());
            int direction = scan.nextInt();
            puzzle.makeMove(direction);
        }
        puzzle.setMisplaced();
        puzzle.setManhattanDistance();
        System.out.println("Misplaced: " + puzzle.getMisplaced() + "Manhattan: " + puzzle.getManhattanDistance());
        System.out.println("good job");
        Puzzle.printPuzzle(puzzle.getPuzzle());
    }
}


