import java.awt.*;
import java.lang.reflect.Array;
import java.util.Random;
import java.util.ArrayList;
public class MyMaze1{
    Cell[][] maze;

    /**
     * Constructor: initialize the maze of the MyMaze object
     * @param rows: the number of rows the maze will have
     * @param cols: the number of columns the maze will have
     */
    public MyMaze1(int rows, int cols) {
        //Initialize the maze array
        maze = new Cell[rows][cols];
        for (int i = 0; i < rows; i++){
            for(int j = 0; j < cols; j++){
                // initialize each element in the maze to a cell
                maze[i][j] = new Cell(i,j);
            }
        }
    }

    /* TODO: Create a new maze using the algorithm found in the writeup. */

    /**
     * static method: make the maze
     * @param rows: the number of rows the maze has
     * @param cols: the number of columns the maze has
     * @return: a MyMaze object with a completed path for its maze
     */
    public static MyMaze1 makeMaze(int rows, int cols) {
        MyMaze1 app = new MyMaze1(rows,cols);//initialize the maze of MyMaze object
        int[][] neighbors;// initialize array to store the neighbors surrounding a specific cell
        Stack1Gen<int[]> stack = new Stack1Gen<>();//initialize stack to store a pair of coordinates with array
        stack.push(new int[]{0,0});//push the first maze into the stack
        Random r = new Random();
        int pickedCell;// initialize the index of the neighbor which is going
        // to be picked after make a random in the neighbors array
        while (!stack.isEmpty()){
            int[] coordinate = stack.top();//get the first cell out of the stack
            int x = coordinate[0];// the x coordinate of the cell
            int y = coordinate[1];// the y coordinate of the cell
            app.maze[x][y].setVisited(true);//mark that cell as visited
            neighbors = app.makeNeighbors(x,y);// find the unvisited neighbors of that cell
            if (neighbors.length > 0){// if there is still unvisited cell around that cell
                if (neighbors.length == 1){// if there is only one cell in the array, choose that element
                    pickedCell = 0;
                } else {//if there are more than one
                    //choose a random cell in the array
                    pickedCell = r.nextInt(neighbors.length);
                }
                int xCell = neighbors[pickedCell][0];// the x coordinate of the picked cell
                int yCell = neighbors[pickedCell][1];// the y coordinate of the picked cell
                if (xCell == x && yCell < y) {// if the picked neighbor is to the left of that cell
                    //remove the right wall of that neighbor
                    app.maze[xCell][yCell].setRight(false);
                } else if (xCell == x && yCell > y) {// if the picked neighbor is to the right of that cell
                    //remove the right border of that cell
                    app.maze[x][y].setRight(false);
                } else if (yCell == y && xCell < x) {// if the picked neighbor is on the top of that cell
                    // remove the bottom wall of that picked neighbor
                    app.maze[xCell][yCell].setBottom(false);
                } else if (yCell == y && xCell > x) {// if the picked neighbor is under that cell
                    // remove the bottom wall of that cell
                    app.maze[x][y].setBottom(false);
                }
                //push that picked neighbor into the stack
                stack.push(new int[]{xCell, yCell});


            } else{ // When there's no un-visited neighbors
                stack.pop();
            }
        }
        //reset all of the cell to unvisited after creating the maze
        for (int i = 0; i < rows; i++){
            for(int j = 0; j < cols; j++){
                app.maze[i][j].setVisited(false);
            }
        }
        return app;
    }

    /* TODO: Print a representation of the maze to the terminal */

    /**
     * print out the maze (either with or without asterisks)
     * @param path: if path is true -> print out the maze with the asterisks after having been solved)
     *              if the path is false -> print out the maze without asterisks
     */
    public void printMaze(boolean path) {
        int rows = maze.length;
        int cols = maze[0].length;
        String result = "";
        //make the implicit upper wall for every cell in row 0
        for (int e = 0; e < cols + 1; e++){
            if ( e < cols){
                result = result + "|---";
            } else {
                result = result + "|" + "\r\n";
            }
        }
        for (int i = 0; i < rows; i ++){//loop through rows
            for (int a = 0; a < 2; a ++) {//make two loops for every rows to make right wall and bottom.
                for (int j = 0; j < cols; j++) {//loops through each cell in that row
                    if (a == 0) {// MAKE THE RIGHT WALL FOR THAT CELL
                        if (i == 0 && j == 0){// the first cell will not have left wall
                            result = result + " ";
                        }
                        if (i != 0 && j == 0) {// all of the cell in column 0 will have left wall
                            result = result + "|";
                        }
                        if(path){// check to see if user want to see the asterisks
                            if (maze[i][j].getVisited()){
                                //print a "*" if the asterisk has visited that cell
                                result = result + " * ";
                            } else {// print nothing if not
                                result = result + "   ";
                            }
                        } else {
                            result = result + "   ";
                        }
                        if (maze[i][j].getRight()) {//if the cell have right wall
                            //print the right wall for that cell if it's not the final cell
                            if (i < rows -1|| j <cols -1) {
                                result = result + "|";
                            } else {// if it is the final cell, make the right wall open
                                result = result + " ";
                            }
                        } else if (!maze[i][j].getRight()) {// if the cell does not have right wall
                            result = result + " ";
                        }
                        if (j == cols - 1) {// if the cell is the final cell of a row,
                            //jump to the next row
                            result = result + "\r\n";
                        }
                    }else if (a == 1){ // MAKE THE BOTTOM WALL FOR THAT CELL
                        if (j == 0) {// add a bar to the begin
                            result = result + "|";
                        }
                        if (maze[i][j].getBottom()) {// if that cell has the bottom wall
                            result = result + "---|";
                        } else if (!maze[i][j].getBottom()) {//if that cell does not have bottom wall
                            result = result + "   |";
                        }
                        if (j == cols - 1) {// if that cell is the final cell of the row, jump to
                            // a new row
                            result = result + "\r\n";
                        }
                    }
                }
            }
        }
        System.out.println(result);
    }

    /* TODO: Solve the maze using the algorithm found in the writeup. */

    /**
     * solve the maze (find any possible path to get out of the maze)
     */
    public void solveMaze() {
        Q1Gen<ArrayList<Cell>> q = new Q1Gen<>();// initialize a queue
        ArrayList<Cell> firstArray = new ArrayList<>();
        firstArray.add(maze[0][0]);
        q.add(firstArray);// push the first cell into the queue
        int x = 0;
        int y = 0;
        int[][] reachable; // initialize array to store coordinates of
        // reachable neighbors of a specific cell
        int[] coordinate; // initialize the array of coordinate for each cell
        ArrayList<Cell> path = new ArrayList<>();
        Cell finalCell;
        while (!q.isEmpty()){
            path = q.remove();// remove the first path in the queue
            finalCell = path.get(path.size() - 1);
            x = finalCell.getX();// x coordinate of that cell
            y = finalCell.getY();// y coordinate of that cell
            maze[x][y].setVisited(true);// mark that cell as visited
            if(x != maze.length - 1 || y!= maze[0].length - 1) {// when we have not arrived the final cell
                reachable = reachableNeighbors(x, y);// make the list of reachable neighbors of that cell
                for (int i = 0; i < reachable.length; i++){
                    //add all of the reachable neighbors into the queue
                    ArrayList<Cell> newPath = new ArrayList<>(path);
                    int xCoord = reachable[i][0];
                    int yCoord = reachable[i][1];
                    newPath.add(maze[xCoord][yCoord]);
                    q.add(newPath);
                }
            }else {//when we arrived the final cell of the maze
                //maze has been solved
                break;
            }
        }
        for (int i = 0; i < maze.length;i++){
            for (int j = 0; j < maze[0].length;j++){
                maze[i][j].setVisited(false);
            }
        }
        String result = "";
        for (int i = 0; i < path.size();i++){
            int X = path.get(i).getX();
            int Y = path.get(i).getY();
            result  = result + X + " " + Y + ";";
            maze[X][Y].setVisited(true);
        }

        System.out.println(path.size());
    }

    /**
     * helper function 1: make a list of reachable neighbors for a cell to solve the maze
     * @param x: the x coordinate of that cell
     * @param y: the y coordinate of that cell
     * @return: list of all reachable neighbors of that cell
     */
    public int[][] reachableNeighbors(int x, int y){
        int[][] list;// initialize the list to store the coordinates of reachable cells
        int count = 0;
        int x1 = x-1; int y1 = y;// up neighbor's coordinate
        int x2 = x; int y2 = y - 1; // left neighbor's coordinate
        int x3 = x; int y3 = y + 1;// right neighbor's coordinate
        int x4 = x + 1; int y4 = y;// down neighbor's coordinate
        //check to see whether each valid neighbor is reachable
        if (check(x1,y1) && !maze[x1][y1].getBottom()){
            // the up neighbor should not have the bottom wall
            count++;
        }if (check(x2,y2) && !maze[x2][y2].getRight()) {
            // the left neighbor should not have the right wall
            count++;
        }if (check(x3,y3) && !maze[x][y].getRight()) {
            //the chosen cell should not have the right wall
            // if want to get to the right neighbor
            count++;
        }if (check(x4,y4) && !maze[x][y].getBottom()){
            // the chosen cell should not have the bottom wall
            // if want to get to the down neighbor
            count++;
        }
        list = new int[count][2]; // initialize the length of the list
        count = 0;
        //add the reachable neighbors into the list
        if (check(x1,y1) && !maze[x1][y1].getBottom()){
            list[count] = new int[]{x1,y1};
            count++;//increment the index
        }if (check(x2,y2) && !maze[x2][y2].getRight()) {
            list[count] = new int[]{x2,y2};
            count++;
        }if (check(x3,y3) && !maze[x][y].getRight()) {
            list[count] = new int[]{x3,y3};
            count++;
        }if (check(x4,y4) && !maze[x][y].getBottom()){
            list[count] = new int[]{x4,y4};
        }
        return list;

    }

    /**
     * Helper function 2: find all of the valid neighbors surrounding a cell
     * @param x: x coordinate of that cell
     * @param y: y coordinate of that cell
     * @return an array of all possible neighbors surrounding that cell
     */
    public int[][] makeNeighbors(int x, int y){
        // initialize an array which automatically has 4 neighbors
        int[][] neighbors = new int[][]{{x - 1, y}, {x, y - 1},
                {x, y + 1}, {x + 1, y}};
        int size = 0;
        // check to see if the neighbor cell is valid (not out of bound)
        for (int i = 0; i < neighbors.length; i ++) {
            int xCor = neighbors[i][0];
            int yCor = neighbors[i][1];
            if (check(xCor,yCor)) {
                size++;// increment the size
            }
        }
        int[][] newNeighbors = new int[size][2];//initialize the size of the array
        size = 0;
        //put the valid neighbors into the array
        for (int i = 0; i < 4; i ++){
            int xCor = neighbors[i][0];
            int yCor = neighbors[i][1];
            if (check(xCor,yCor)) {
                newNeighbors[size] = new int[]{xCor,yCor};
                size++;
            }
        }
        return newNeighbors;

    }

    /**
     * Helper function 3: check whether the cell is valid
     * @param x: x coordinate of that cell
     * @param y: y coordinate of that cell
     * @return: true if that cell is not out of bound and unvisited
     */
    public boolean check(int x, int y){
        if (x >= 0 && y >=0 && x < maze.length && y < maze[0].length && !maze[x][y].getVisited()){
            return true;
        }
        return false;
    }

    public static void main(String[] args){
        /* Any testing can be put in this main function */
        MyMaze1 a;
        System.out.println("This will print out what 10 mazes look like before and after it has been solved");
        System.out.println();
        for (int i = 0; i < 10; i++) {
            a = makeMaze(5, 20);
            System.out.println("Before solving");
            a.printMaze(false);
            a.solveMaze();
            System.out.println("After solving");
            a.printMaze(true);

        }
    }
}