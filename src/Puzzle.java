import java.util.Arrays;

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
        for(int i = 0; i < puzzle.length; i++){
            if(0 == puzzle[i]){
                return i;
            }
        }
        return 9;
    }

    public Puzzle(int[] puzzle) {
        this.puzzle = puzzle;
    }

    public Puzzle(){}

    public static void printPuzzle(int[] puzzle){
        System.out.println("| " + puzzle[0] + " | " + puzzle[1] + " | " + puzzle[2] + " |\n" +
                "| " + puzzle[3] + " | " + puzzle[4] + " | " + puzzle[5] + " |\n" +
                "| " + puzzle[6] + " | " + puzzle[7] + " | " + puzzle[8] + " |\n");
    }

    public static boolean isSolved(int[] puzzle){
        int[] solvedPuzzle =  {1, 2, 3, 4, 5, 6, 7, 8, 0};
        if(Arrays.equals(puzzle, solvedPuzzle)){
            return true;
        }
        return false;
    }


    public void makeMove( int direction){
        //direction: 1 -> right, 2 -> down, 3 -> left, 4 -> up
        //how to replace position in array: right -> +1, down -> +3, left -> -1, up -> -3
        int blankPosition = getBlankPosition(puzzle);
        if(isValidMove(blankPosition, direction)){
            switch (direction) {
                case 1: //right
                    switchPuzzlePosition( blankPosition, blankPosition + 1);
                    break;
                case 2: //down
                    switchPuzzlePosition( blankPosition, blankPosition + 3);
                    break;
                case 3: //left
                    switchPuzzlePosition( blankPosition, blankPosition - 1);
                    break;
                case 4:
                    switchPuzzlePosition( blankPosition, blankPosition - 3);
                    break;
                default:
                    break;
            }
        }
        else{
            System.out.println("not possible");
        }

    }

    public static boolean isValidMove(int blankPosition, int direction) {
        int row = blankPosition / 3;
        int col = blankPosition % 3;
        //direction: 1 -> right, 2 -> down, 3 -> left, 4 -> up
        switch (direction) {
            case 1:
                return col < 2; // Check if it's not on the right edge
            case 2:
                return row < 2; // Check if it's not on the bottom edge
            case 3:
                return col > 0; // Check if it's not on the left edge
            case 4:
                return row > 0; // Check if it's not on the top edge
            default:
                return false;
        }
    }

    private void switchPuzzlePosition(/*int[] puzzle,*/ int from, int to){
        int temp = this.puzzle[from];
        this.puzzle[from] = this.puzzle[to];
        this.puzzle[to] = temp;
    }

    public void setMisplaced(){
        int counter = 0;
        for(int i = 0; i < (this.puzzle.length - 1); i++){
            if(puzzle[i] != (i + 1)){
                counter++;
            }
        }
        this.misplaced = counter;
    }

    public int getMisplaced(){
        return this.misplaced;
    }

    public int getManhattanDistance() {
        return manhattanDistance;
    }


    //funktioniert noch nicht ganz
    public void setManhattanDistance(){
        int distance = 0;
        for (int i = 0; i < this.puzzle.length; i++) {
            if (this.puzzle[i] != 0) {
                int targetRow = this.puzzle[i] / 3;
                int targetCol = this.puzzle[i] % 3;
                int currentRow = i / 3;
                int currentCol = i % 3;
                distance += Math.abs(targetRow - currentRow) + Math.abs(targetCol - currentCol);
            }
        }
        this.manhattanDistance = distance;
    }

}
