import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;
class PuzzleGen {
    private StateNode initialStateNode;
    private int numFailed;

    private void setInitialStateNode(StateNode init){
        initialStateNode = init;
    }
    StateNode getInitialStateNode(){
        return initialStateNode;
    }
    private void Failed(){
        numFailed++;
    }
    int getNumFailed(){
        return numFailed;
    }

    /**
     * Checks to see if a puzzle is solvable. A solvable puzzle has a positive and even
     * of inversions. An inversion is when a tile with a greater number on it
     * precedes a tile with a smaller number
     */
    private boolean checkSolvable(Integer[] board){
        int inversions = 0;
        for(int i = 0; i < board.length-1; ++i) {
            for(int j = i+1; j < board.length; ++j) {
                //Make sure not to count the 0 tile
                if(board[i] != 0 && board[j] != 0 && board[i] > board[j])
                    inversions++;
            }
        }
        return inversions%2 == 0;
    }
    void createPuzzle(Integer[] initialStateBoard, boolean isRandom){
        boolean canSolve = false;
        numFailed = 0;
        int emptyPosition = 0;

        while(!canSolve){
            if(isRandom){
                Collections.shuffle(Arrays.asList(initialStateBoard));
            }
            if(!isRandom){
                boolean PuzzleCreated = false;
                String puzzleString = "";
                while(!PuzzleCreated) {
                    System.out.println("Enter your puzzle in the following format, 012345678 or 0 1 2 3 4 5 6 7 8, numbers must be from 0-8 and cannot use the same number more than once");
                    Scanner c = new Scanner(System.in);

                    String s = c.nextLine().replaceAll(" ", "");
                    if (s.length() == 9) {
                        puzzleString = s;
                        PuzzleCreated = true;
                    } else {
                        System.out.println("You must enter only 9 numbers, Try again.");
                    }
                    for (int i = 0; i < puzzleString.length(); i++)
                        for (int j = i + 1; j < puzzleString.length(); j++)
                            if (puzzleString.charAt(i) == puzzleString.charAt(j)) {
                                PuzzleCreated = false;
                                System.out.println("String entered has duplicates, Try again.");
                            }

                                for (int i = 0; i < puzzleString.length(); i++) {
                        Integer val = 0;
                        try {
                            val = Integer.parseInt(Character.toString(puzzleString.charAt(i)));
                        } catch (NumberFormatException e) {
                            System.out.println("The string contained a non-number character, try again");
                            PuzzleCreated = false;
                        }
                        if (val == 0)
                            emptyPosition = i;
                        initialStateBoard[i] = val;
                        if (val < 0) {
                            System.out.println("The number must be inbetween 0 and 8");
                            PuzzleCreated = false;
                        } else if (val > 8) {
                            System.out.println("The number must be inbetween 0 and 8");
                           PuzzleCreated = false;
                        }

                    }
                   if(!checkSolvable(initialStateBoard)){
                       PuzzleCreated = false;
                       System.out.println("This is not a solvable puzzle, Please try another one.");
                   }
                }
            }
            for(int i = 0; i < initialStateBoard.length; ++i){
                Integer tile = initialStateBoard[i];

                if(tile.equals(0)) emptyPosition = i;
                initialStateBoard[i] = tile;
            }
            //Runs the PuzzleGen checkSolvable function, returns true if inversions is even
            canSolve = checkSolvable(initialStateBoard);
            if(!canSolve)
               Failed();
        }
      //  System.out.println("Amount of times failed: " + numFailed);
        System.out.println("Empty Pos: " + emptyPosition);
        setInitialStateNode(new StateNode(initialStateBoard, initialStateBoard,0,"noop",null,emptyPosition));
    }


}