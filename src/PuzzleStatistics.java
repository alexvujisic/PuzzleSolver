public class PuzzleStatistics {
    private int totalExpandedNodes;
    private int depth;
    private String heuristic;
    private long computationTimeMillis;

    public int getTotalExpandedNodes() {
        return totalExpandedNodes;
    }

    public int getDepth() {
        return depth;
    }

    public String getHeuristic() {
        return heuristic;
    }

    public long getComputationTimeMillis() {
        return computationTimeMillis;
    }

    public PuzzleStatistics(int totalExpandedNodes, int depth, String heuristic, long computationTimeMillis){
        this.totalExpandedNodes = totalExpandedNodes;
        this.depth = depth;
        if(heuristic.equals("H")){
            this.heuristic = "Hamming";
        }else if(heuristic.equals("M")){
            this.heuristic = "Manhattan";
        }
        this.computationTimeMillis = computationTimeMillis;
    }

    public PuzzleStatistics(String heuristic){
        this.totalExpandedNodes = 0;
        this.depth = 0;
        if(heuristic.equals("H")){
            this.heuristic = "Hamming";
        }else if(heuristic.equals("M")){
            this.heuristic = "Manhattan";
        }
        this.computationTimeMillis = 0;
    }

    public void addToStatistic(int expandedNodes, int depth, long computationTimeMillis){
        this.totalExpandedNodes += expandedNodes;
        this.depth += depth;
        this.computationTimeMillis += computationTimeMillis;
    }

    public void printStatistics(){
        String output = "Heuristic: " + this.heuristic + "\nTotal Number of expanded Nodes: " + this.totalExpandedNodes
                        + "\nTotal Depth: " + this.depth + "\nTotal Computation Time (in ms): " + this.computationTimeMillis + "ms";
        System.out.println(output);
    }
}
