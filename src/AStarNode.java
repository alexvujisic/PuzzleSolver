public class AStarNode {
    private Puzzle puzzle;
    private int g;  // each move cost 1
    private int heuristic;  // Heuristic estimate
    private int f;  // Total cost (f = g + h)

    public AStarNode(Puzzle puzzle) {
        this.puzzle = puzzle;
    }

    public Puzzle getPuzzle() {
        return puzzle;
    }

    public void setG(int g) {
        this.g = g;
    }

    public int getG() {
        return g;
    }

    public void setHeuristic(int h) {
        this.heuristic = h;
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
}
