import java.util.*;


class ASearch {
    private final Integer[] goal;

    ASearch() {
        goal = new Integer[]{0,1,2,3,4,5,6,7,8};
    }

    /**
     * Run the A* search on a specific board state using either the misplaced
     * tile or sum of distance heuristic. This is H1 vs H2
     * The lambda function sorts all the step costs.
     * The priority queue will expand children
     * of the smallest cost first and then they will be added to the explored set
     * which should disallow duplicate states. This defines that it is a graph search, and not a tree search.
     */
    StateNode runAStar(StateNode initialState, boolean isH1, boolean print){
        int searchCost = 0;
        HashSet<StateNode> exploredSet = new HashSet<>();
        PriorityQueue<StateNode> fringe;
        if(isH1){
            fringe = new PriorityQueue<>(Comparator.comparingInt((StateNode o) -> (o.getCost() + misplacedTiles(o))));
        }else{
            fringe = new PriorityQueue<>(Comparator.comparingInt((StateNode o) -> (o.getCost() + sumOfDistance(o))));
        }
        fringe.add(initialState);
        while(!fringe.isEmpty()){
            StateNode current = fringe.poll();
            exploredSet.add(current);
            if(print){
                System.out.println(current);
                System.out.println("------ Current Step Cost: " + current.getCost() + " - Search Cost: " + searchCost + "  - Fringe Size: " + fringe.size() + " - Explored Size:  " + exploredSet.size() + " -------");
            }
            if(Arrays.equals(current.getCurrentState(), goal)){
                System.out.println("----------- Goal Found at Search Cost of: " + searchCost + "------------");
                return current;
            }
            ArrayList<StateNode> children = current.expandCurrentNode();
            for (StateNode child : children) {
                if (!exploredSet.contains(child)) {
                    searchCost++;
                    child.setSearchCost(searchCost);
                    child.setExploredSize(exploredSet.size());
                    child.setFringeSize(fringe.size());
                    fringe.add(child);
                }
            }
        }
        return null;
    }
    /**
     * Heuristic function that simply counts the number of misplaced tiles
     * Returns the total amount of misplaced tiles
     * A.K.A H1
     */
    private int misplacedTiles(StateNode node){
        int misplaced = 0;
        for(int i = 0; i < node.getCurrentState().length; ++ i){
            if(node.getCurrentState()[i] != i) misplaced++;
        }
        return misplaced;
    }

    /**
     * Heuristic function for the Manhattan distance, i.e. the sum of the dist.
     * from the tile to the goal.
     * Returns the sum of the distances
     * A.K.A H2
     */
    private int sumOfDistance(StateNode node){
        int sum = 0;
        for(int i = 0; i < node.getCurrentState().length; ++i){
            if(node.getCurrentState()[i] == i) continue;
            if(node.getCurrentState()[i] == 0) continue;
            int row = node.getCurrentState()[i]/3;
            int col = node.getCurrentState()[i]%3;
            int goalRow = i/3;
            int goalCol = i%3;
            sum += Math.abs(col - goalCol) +  Math.abs(row - goalRow);
        }
        return sum;
    }

}