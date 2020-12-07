import java.util.*;


public class Main8P {
    public static void main(String[] args){
        Main8P ai = new Main8P();
        System.out.println("Please select a puzzle generation. Possible options: 1 = Random Puzzle. 2 = User defined puzzle.");
        Scanner c = new Scanner(System.in);
        int choice = c.nextInt();
        if(choice == 1){
            ai.randomPuzzle();
        }else if(choice == 2){
            ai.userDefinedPuzzle();
        }else{
            System.err.println("The choice you selected does not exist, Please restart and try again.");
        }
    }
    ASearch aSearch = new ASearch();

    public void randomPuzzle(){
        Map<Integer,ArrayList<SearchData>> runtimeData = new TreeMap<>();

        System.out.println("Enter random input search iterations. Number must be greater than 0.");
        Scanner c = new Scanner(System.in);
        int iterations = c.nextInt();
        PuzzleGen puzzle = new PuzzleGen();
        for(int i = 0; i < iterations; i++){
            Integer [] initialState = {0, 1, 2, 3, 4, 5, 6, 7, 8};
            puzzle.createPuzzle(initialState,true);
            SearchData compute = solve(puzzle.getInitialStateNode());
            if(!runtimeData.containsKey(compute.depth)){
                runtimeData.put(compute.depth, new ArrayList<>());
            }
            runtimeData.get(compute.depth).add(compute);
        }
        System.out.println("--------------------------Averages over "+ iterations + " iterations-------------------------------------");

        System.out.println("d  | Total Cases | Search Cost H1 | Total Time H1 | Search Cost H2 | Total Time H2");
        runtimeData.entrySet().stream().forEach((entry) -> {
            int h1AvgCost=0,h1AvgTime=0,h2AvgCost=0,h2AvgTime=0,total=entry.getValue().size();
            for(int i = 0; i < entry.getValue().size(); ++i){
                SearchData data = entry.getValue().get(i);
                h1AvgCost += data.searchCostH1;
                h1AvgTime += data.totalTimeH1;
                h2AvgCost += data.searchCostH2;
                h2AvgTime += data.totalTimeH2;
            }
            System.out.println("----------------------------------------------------------------------------------");
            System.out.println(entry.getKey() + " | " + total + " | " + (h1AvgCost/total) + " | " + (h1AvgTime/total)
                    + " ms | " + (h2AvgCost/total) + " | " + (h2AvgTime/total) + " ms");
        });
        System.out.println("Times puzzle was unsolvable, then remade: " +puzzle.getNumFailed());



    }

    public void userDefinedPuzzle(){
        PuzzleGen puzzle = new PuzzleGen();
        Integer [] initialState = {0,0,0,0,0,0,0,0,0};
        puzzle.createPuzzle(initialState,false);
        SearchData compute = solve(puzzle.getInitialStateNode());
        System.out.println("d  | Total Cases | Search Cost H1 | Total Time H1 | Search Cost H2 | Total Time H2");
        System.out.println(compute.depth + " | " + 1 + " | " + compute.searchCostH1 + " | " + compute.totalTimeH1 + " | " + compute.searchCostH2 + " | " + compute.totalTimeH2);
    }




    private SearchData solve(StateNode init){
        System.out.println(init);
        System.out.println("--------------- Starting A* Search using Heuristic 1 -------------");
        long start1 = System.currentTimeMillis();
        StateNode goalNode1 = aSearch.runAStar(init, true, true);
        long end1 = System.currentTimeMillis();
        long total1 = end1 - start1;
        System.out.println("-------------------- Heuristic 1 Finished, Starting Heuristic 2 ------------------------");
        long start2 = System.currentTimeMillis();
        StateNode goalNode2 = aSearch.runAStar(init, false, true);
        long end2 = System.currentTimeMillis();
        long total2 = end2 - start2;
        System.out.println("-------------------- Finished Heuristic 2 ------------------------");
        System.out.println("Solved Using H1\nDepth: " + goalNode1.getCost()
                + " - Search Cost: " + goalNode1.getSearchCost()
                + " - Fringe Size: " + goalNode1.getFringeSize()
                + " - Explored Set Size: " + goalNode1.getExploredSize()
                + " - Total Time: " + total1 + " ms");
        System.out.println("Solved Using H2\nDepth: " + goalNode2.getCost()
                + " - Search Cost: " + goalNode2.getSearchCost()
                + " - Fringe Size: " + goalNode2.getFringeSize()
                + " - Explored Set Size: " + goalNode2.getExploredSize()
                + " - Total Time: " + total2 + " ms"+"\n");
        if(goalNode1.getCost() != goalNode2.getCost()){
            System.out.println(goalNode1.getCost() + " != " + goalNode2.getCost());
            System.out.println("The depths calculated from the heursitics are not the same! Exiting!");
            System.exit(0);
        }
        return new SearchData(goalNode1.getCost(),goalNode1.getSearchCost(),total1,goalNode2.getSearchCost(), total2);
    }
    private class SearchData{
        public int depth, searchCostH1, searchCostH2;
        public long totalTimeH1, totalTimeH2;
        public SearchData(int d, int scH1, long ttH1, int scH2, long ttH2){
            depth = d;
            searchCostH1 = scH1;
            searchCostH2 = scH2;
            totalTimeH1 = ttH1;
            totalTimeH2 = ttH2;
        }
    }
}
