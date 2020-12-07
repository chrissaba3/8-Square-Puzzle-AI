Project 1: 8-Puzzle

Date: Jul. 19, 2020

Project Description   

The A* search can be used to solve the 8-puzzle problem. As described in the book, 
there are two candidate heuristic functions: 
(1) h1 = the number of misplaced tiles; (2) h2 = the sum of the distances of the tiles from their goal positions.

You are to implement the A* using both heuristics and compare their efficiency in terms of depth of the solution and search costs.   
The following figure provides some data points that you can refer to (you don’t need to report “effective branching factor” in your report). 
To test your program and analyze the efficiency, you should generate random problems (>100 cases) with different solution lengths
 or you can use the inputs that I provided to test your program. Please collect data on the different solution lengths that you have tested, 
with their corresponding search cost (# of nodes generate). A good testing program should test a range of possible cases (2 <= d <= 20). 
Note that the average solution cost for a randomly generated 8-puzzle instance is about 22 steps.
Input requirements : Your program should allow the instructor to either generate a random 8-puzzle problem or enter a specific 8-puzzle 
configuration through the standard input, which contains the configuration for only one puzzle, in the following format:

1 2 4 0 5 6 8 3 7

The goal state is:

0 1 2 3 4 5 6 7 8

Where 0 represents the empty tile.

---------------------------------------------------------------------
How to run:

Go to src folder, included are 4 java files; Main8P.java, StateNode.java, ASearch.java, PuzzleGen.java

The main driver program is in Main8P.java, there you may run the program, and see that the console will ask for input of either 1, or 2
1 input brings you into the random puzzle generator, where you will input the number of iterations to solve x different puzzles.
putting 2 in the previous option will allow your own user input, and can be spaced out like this: 0 1 2 3 4 5 6 7 8, or nonspaced 012345678
The program will tell you to try again if it sees more than 8 digits, non number charactors, digits outside the range 0-8, digits that have
more than one occourance, as well as if the puzzle is solvable or not. If you use the random input, there will be a printout of a table with different depths
as well as a "failure" count which counts how many times a puzzle had to be remade due to it being unsolvable.

A brief description of each java class is as follows.

- Main8P.java: Main driver program, keeps track of statistics and prints most statistics.

- StateNode.java: 
This is the class that considers the physics of the moves, such as swaps, moving up down left and right, next states, expanding current nodes, printing matrix, and creating new nodes.

- ASearch.java:
The A* heuristic graph algorithm that calculates state costs, using 2 different heuristic functions mentioned above.

- PuzzleGen.java:
A puzzle generator that imports user input and allows the input to be checked for solvable, as well as correct formats and imports the arrays into StateNode, 
to be later calculated using the A*Search class.


