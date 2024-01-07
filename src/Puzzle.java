import java.util.*;

public class Puzzle {
    private int[] puzzle;

    private int g;  // each move cost 1
    private int heuristic;  // Heuristic estimate
    private int f;  // Total cost (f = g + h)

    /**
     * Constructor without parameters
     */
    public Puzzle(){
        int[] solvedPuzzle = {1, 2, 3, 4, 5, 6, 7, 8, 0};
        int[] randomPattern = Arrays.copyOf(solvedPuzzle, solvedPuzzle.length);

        Random random = new Random();
        for (int i = 0; i < randomPattern.length; i++) {
            int index = random.nextInt(i + 1);
            int temp = randomPattern[i];
            randomPattern[i] = randomPattern[index];
            randomPattern[index] = temp;
        }
        this.puzzle = randomPattern;
        this.g = 0;
    }

    /**
     * Constructor woth parameters
     * @param puzzle The puzzle
     * @param g G function
     */
    public Puzzle(int[] puzzle, int g){
        this.puzzle = puzzle;
        this.g = g;
    }

    //Returns the position of the blank tile
    public static int getBlankPosition(int[] puzzle) {
        for (int i = 0; i < puzzle.length; i++) {
            if (0 == puzzle[i]) {
                return i;
            }
        }
        return 9;
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

    public void calculateManhattanDistance(){
        //Calculates the Manhattan distance of a puzzle
        int distance = 0;
        for (int i = 0; i < this.puzzle.length; i++) {
            //For each tile in a puzzle
            if (this.puzzle[i] != 0) {
                int targetRow = (this.puzzle[i] - 1) / 3;
                int targetCol = (this.puzzle[i] - 1) % 3;
                int currentRow = i / 3;
                int currentCol = i % 3;
                distance += Math.abs(targetRow - currentRow) + Math.abs(targetCol - currentCol);
            }
        }
        this.heuristic = distance;
    }
    public void calculateHamming(){
        int counter = 0;
        for (int i = 0; i < (this.puzzle.length - 1); i++) {
            if (puzzle[i] != (i + 1)) {
                counter++;
            }
        }
        this.heuristic = counter;
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
    public int[] getPuzzle() {
        return puzzle;
    }
    public void setPuzzle(int[] puzzle) {
        this.puzzle = puzzle;
        this.g = 0;
    }

    /**
     * A* algorithm
     * @param heuristicFunction The heuristic function to be used
     * @return the g value of the solution
     */
    public int solvePuzzle(String heuristicFunction){

        //In here the puzzles and their successor are stored in order of the f function
        PriorityQueue<Puzzle> openSet = new PriorityQueue<>(Comparator.comparingInt(Puzzle::getF));
        //All the nodes that were already visited
        Set<Puzzle> closedSet = new HashSet<>();

        // H -> Hamming , M -> Manhattan
        if(heuristicFunction.equals("H")){
            this.calculateHamming();
            this.setF();
        }else if(heuristicFunction.equals("M")){
            this.calculateManhattanDistance();
            this.setF();
        }
        openSet.add(this);

        while(!openSet.isEmpty()) {
            Puzzle current = openSet.poll();

            if (current.isSolved()) {
                return current.getG();
            }

            closedSet.add(current);

            ArrayList<Puzzle> successorsList = Puzzle.getSuccessors(current, heuristicFunction);

            for (Puzzle successor : successorsList) {
                if (!isPuzzleInSet(openSet, closedSet, successor)) {
                    openSet.add(successor);
                }
            }

        }

        return 0;
    }

    public boolean isPuzzleInSet(PriorityQueue<Puzzle> openSet, Set<Puzzle> closedSet, Puzzle toCompare){
        for (Puzzle p : openSet) {
            if(Arrays.equals(p.getPuzzle(), toCompare.getPuzzle())) {
                return true;
            }
        }
        for (Puzzle p : closedSet) {
            if(Arrays.equals(p.getPuzzle(), toCompare.getPuzzle())) {
                return true;
            }
        }
        return false;
    }
    public static ArrayList<Puzzle> getSuccessors(Puzzle node, String heuristicFunction){
        ArrayList<Puzzle> successors = new ArrayList<Puzzle>();

        for (int direction = 1; direction <= 4; direction++) {
            if (node.isValidMove(node.getBlankPosition(node.getPuzzle()), direction)) {
                Puzzle successorPuzzle = new Puzzle(Arrays.copyOf(node.getPuzzle(), node.getPuzzle().length), node.getG()+1);
                successorPuzzle.makeMove(direction);
                if(heuristicFunction.equals("H")){
                    successorPuzzle.calculateHamming();
                }else if(heuristicFunction.equals("M")){
                    successorPuzzle.calculateManhattanDistance();
                }
                successorPuzzle.setF();
                successors.add(successorPuzzle);
            }
        }

        return successors;
    }

    public void setG(int g) {
        this.g = g;
    }

    public int getG() {
        return g;
    }


    public int getHeuristic() {
        return heuristic;
    }

    public void setF() {
        this.f = this.g + this.heuristic;
    }

    public int getF() {
        return f;
    }

    //checks if the puzzle is solved
    public boolean isSolved() {
        int[] solvedPuzzle = {1, 2, 3, 4, 5, 6, 7, 8, 0};
        return Arrays.equals(this.puzzle, solvedPuzzle);
    }

    public static void printPuzzle(int[] puzzle) {
        System.out.println("| " + puzzle[0] + " | " + puzzle[1] + " | " + puzzle[2] + " |\n" +
                "| " + puzzle[3] + " | " + puzzle[4] + " | " + puzzle[5] + " |\n" +
                "| " + puzzle[6] + " | " + puzzle[7] + " | " + puzzle[8] + " |\n");
    }
}
