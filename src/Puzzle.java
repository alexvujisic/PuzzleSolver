import javax.naming.InitialContext;
import java.util.*;

public class Puzzle {
    private int[] puzzle;
    private int misplaced;
    private int manhattanDistance;


    public int[] getPuzzle() {
        return puzzle;
    }

    public void setPuzzle(int[] puzzle) {
        this.puzzle = puzzle;
    }

    public static int getBlankPosition(int[] puzzle) {
        for (int i = 0; i < puzzle.length; i++) {
            if (0 == puzzle[i]) {
                return i;
            }
        }
        return 9;
    }

    public Puzzle(int[] puzzle) {
        this.puzzle = puzzle;
    }

    public Puzzle() {
    }

    public static void printPuzzle(int[] puzzle) {
        System.out.println("| " + puzzle[0] + " | " + puzzle[1] + " | " + puzzle[2] + " |\n" +
                "| " + puzzle[3] + " | " + puzzle[4] + " | " + puzzle[5] + " |\n" +
                "| " + puzzle[6] + " | " + puzzle[7] + " | " + puzzle[8] + " |\n");
    }

    //checks if the puzzle is solved
    public boolean isSolved() {
        int[] solvedPuzzle = {1, 2, 3, 4, 5, 6, 7, 8, 0};
        return Arrays.equals(this.puzzle, solvedPuzzle);
    }

    //function to move tiles
    public void makeMove(int direction) {
        //direction: 1 -> right, 2 -> down, 3 -> left, 4 -> up
        //how to replace position in array: right -> +1, down -> +3, left -> -1, up -> -3
        int blankPosition = getBlankPosition(puzzle);
        if (isValidMove(blankPosition, direction)) {
            switch (direction) {
                case 1: //right
                    switchPuzzlePosition(blankPosition, blankPosition + 1);
                    break;
                case 2: //down
                    switchPuzzlePosition(blankPosition, blankPosition + 3);
                    break;
                case 3: //left
                    switchPuzzlePosition(blankPosition, blankPosition - 1);
                    break;
                case 4:
                    switchPuzzlePosition(blankPosition, blankPosition - 3);
                    break;
                default:
                    break;
            }
        } else {
            System.out.println("not possible");
        }

    }

    //checks if the next move is valid/possible
    public static boolean isValidMove(int blankPosition, int direction) {
        int row = blankPosition / 3;
        int col = blankPosition % 3;
        //direction: 1 -> right, 2 -> down, 3 -> left, 4 -> up
        return switch (direction) {
            case 1 -> col < 2; // Check if it's not on the right edge
            case 2 -> row < 2; // Check if it's not on the bottom edge
            case 3 -> col > 0; // Check if it's not on the left edge
            case 4 -> row > 0; // Check if it's not on the top edge
            default -> false;
        };
    }

    //function to switch positions in array to move tiles
    private void switchPuzzlePosition(int from, int to) {
        int temp = this.puzzle[from];
        this.puzzle[from] = this.puzzle[to];
        this.puzzle[to] = temp;
    }

    //calculate how many tiles are misplaced
    public void setMisplaced() {
        int counter = 0;
        for (int i = 0; i < (this.puzzle.length - 1); i++) {
            if (puzzle[i] != (i + 1)) {
                counter++;
            }
        }
        this.misplaced = counter;
    }

    public int getMisplaced() {
        return this.misplaced;
    }

    public int getManhattanDistance() {
        return manhattanDistance;
    }


    //calculates distance of each tile and sets it
    public void setManhattanDistance() {
        int distance = 0;
        for (int i = 0; i < this.puzzle.length; i++) {
            if (this.puzzle[i] != 0) {
                int targetRow = (this.puzzle[i] - 1) / 3;
                int targetCol = (this.puzzle[i] - 1) % 3;
                int currentRow = i / 3;
                int currentCol = i % 3;
                distance += Math.abs(targetRow - currentRow) + Math.abs(targetCol - currentCol);
            }
        }
        this.manhattanDistance = distance;
    }

    //function that checks for solvability returns true if solvable and false if not
    public boolean isSolvable() {
        int inversionCount = countInversions(puzzle);
        return inversionCount % 2 == 0;
    }
    //https://www.geeksforgeeks.org/check-instance-8-puzzle-solvable/
    private static int countInversions(int[] puzzle) {
        int inversions = 0;
        for (int i = 0; i < puzzle.length - 1; i++) {
            for (int j = i + 1; j < puzzle.length; j++) {
                if (puzzle[i] != 0 && puzzle[j] != 0 && puzzle[i] > puzzle[j]) {
                    inversions++;
                }
            }
        }
        return inversions;
    }

    public static int solveAStar(Puzzle puzzle, String heuristic){
        PriorityQueue<AStarNode> openSet = new PriorityQueue<>(Comparator.comparingInt(AStarNode::getF));
        Set<Puzzle> closedSet = new HashSet<>();

        AStarNode initalAStarNode = new AStarNode(puzzle);
        initalAStarNode.setG(0);
        // H -> Hamming , M -> Manhattan
        if(heuristic.equals("H")){
            initalAStarNode.setHeuristic(puzzle.getMisplaced());
        }else if(heuristic.equals("M")){
            initalAStarNode.setHeuristic(puzzle.getManhattanDistance());
        }
        initalAStarNode.setF();

        openSet.add(initalAStarNode);

        while(!openSet.isEmpty()){
            AStarNode current = openSet.poll();

            if(current.getPuzzle().isSolved()){
                System.out.println("Puzzle solved with A*");
                Puzzle.printPuzzle(current.getPuzzle().getPuzzle());
                System.out.println("\nNodes: " + current.getG());
                return current.getF();
            }

            closedSet.add(current.getPuzzle());

            for (int direction = 1; direction <= 4; direction++) {
                if (Puzzle.isValidMove(Puzzle.getBlankPosition(current.getPuzzle().getPuzzle()), direction)) {
                    Puzzle successorPuzzle = new Puzzle(Arrays.copyOf(current.getPuzzle().getPuzzle(), current.getPuzzle().getPuzzle().length));
                    successorPuzzle.makeMove(direction);

                    if (!closedSet.contains(successorPuzzle)) {
                        AStarNode successorNode = new AStarNode(successorPuzzle);
                        int g = current.getG() + 1;
                        successorNode.setG(g);
                        if (heuristic.equals("H")) {
                            successorNode.setHeuristic(successorPuzzle.getMisplaced());
                        } else if (heuristic.equals("M")) {
                            successorNode.setHeuristic(successorPuzzle.getManhattanDistance());
                        }
                        successorNode.setF();

                        if (!openSet.contains(successorNode) || g < successorNode.getG()) {
                            openSet.add(successorNode);
                        }
                    }

                }
                System.out.println("Heuristic value" + current.getF());
            }
        }
        return 0;
    }
}