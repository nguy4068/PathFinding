import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

public class MyGridLayout implements MouseListener, MouseMotionListener, KeyListener,ActionListener {
    public static MyGridLayout grid;
    JFrame frame;
    paint p;
    public Point[][] listPoint;
    private int rows;
    private int cols;
    public int width;
    public int height;
    private final int WIDTH = 700;
    private final int HEIGHT = 600;
    private boolean hadStart = false;
    private boolean hadEnd = false;
    private int xStart;
    private int yStart;
    private int xEnd;
    private int yEnd;
    private Button b;
    Point onclickPoint;
    int xOnClick;
    int yOnclick;
    boolean unSet = false;
    Label message;
    /**
     * Constructor initializing a grid of points
     */
    public MyGridLayout(int rows,int cols){
        listPoint = new Point[rows][cols];
        this.rows = rows; //number of rows of the grid
        this.cols = cols; //number of columns of the grid
        this.width =  (WIDTH-200)/rows;// width of a square in the grid
        this.height = (WIDTH-200)/rows;// height of a square in the grid (in this case width = height)
        int y = 20;// y coordinate of the left corner of the grid
        for (int i = 0; i <rows;i++){
            int x = 20;// x coordinate of the left corner of the grid
            for (int j = 0; j < rows; j++){
                listPoint[i][j] = new Point(x,y,i,j);
                x = x + this.width;
            }
            y = y + this.width;
        }
        //Initializing the message
        message = new Label("Welcome to solve maze GUI");
        message.setBounds(250,0,200,20);
        message.setBackground(Color.lightGray);
        //Initializing solve maze button(Breadth-first search)
        Button b3 = new Button("Breadth");
        b3.setBounds(570,180,80,30);
        b3.addActionListener(this);
        b3.setActionCommand("Breadth-first search");
        b3.setBackground(Color.orange);
        //Initializing solve maze button (A*)
        Button b2 = new Button("A*");
        b2.setBounds(570,140,80,30);
        b2.addActionListener(this);
        b2.setActionCommand("A*");
        b2.setBackground(Color.green);
        //Initializing the make maze button
        Button b1 = new Button("GenMaze");
        b1.setActionCommand("Generate");
        b1.addActionListener(this);
        b1.setBounds(570,100,80,30);
        b1.setBackground(Color.cyan);
        // Initializing the reset button
        p = new paint();
        p.setBounds(20,20,510,510);
        b = new Button("Reset");
        b.addActionListener(this);
        b.addKeyListener(this);
        b.setActionCommand("Reset");
        b.setBounds(570,40,50,30);
        b.setBackground(Color.pink);
        // Initializing the frame
        frame = new JFrame();
        frame.setSize(WIDTH,HEIGHT);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setLayout(new LayoutManager() {
            @Override
            public void addLayoutComponent(String s, Component component) {

            }

            @Override
            public void removeLayoutComponent(Component component) {

            }

            @Override
            public Dimension preferredLayoutSize(Container container) {
                return null;
            }

            @Override
            public Dimension minimumLayoutSize(Container container) {
                return null;
            }

            @Override
            public void layoutContainer(Container container) {

            }
        });
        frame.add(p);
        frame.add(b);
        frame.add(b1);
        frame.add(b2);
        frame.add(b3);
        frame.add(message);
        p.addMouseListener(this);
        p.addMouseMotionListener(this);
        frame.addKeyListener(this);
    }
    public Point[][] getListPoint(){
        return listPoint;
    }

    /**
     * repaint function of JPanel, in charge of animation part
     * @param g: parameter passed by evoking paintComponent of the JPanel
     */
    public void repaint(Graphics g){
        //Loop through every squares in the grid to check for the color and paint the square correspondingly
        for (int i = 0; i < listPoint.length;i++){
            for (int j = 0; j < listPoint.length; j++) {
                if (listPoint[i][j].getColor().equals("white")) {
                    g.drawRect(listPoint[i][j].getxCoord(), listPoint[i][j].getyCoord(), this.width, this.height);
                } else if (listPoint[i][j].getColor().equals("green")) {
                    g.setColor(Color.green);
                    g.fillRect(listPoint[i][j].getxCoord(), listPoint[i][j].getyCoord(), this.width, this.height);
                    g.setColor(Color.black);
                } else if (listPoint[i][j].getColor().equals("blue")) {
                    g.setColor(Color.blue);
                    g.fillRect(listPoint[i][j].getxCoord(), listPoint[i][j].getyCoord(), this.width, this.height);
                    g.setColor(Color.black);
                } else if (listPoint[i][j].getColor().equals("black")) {
                    g.fillRect(listPoint[i][j].getxCoord(), listPoint[i][j].getyCoord(), this.width, this.height);
                } else if (listPoint[i][j].getColor().equals("cyan")){
                    g.setColor(Color.CYAN);
                    g.fillRect(listPoint[i][j].getxCoord(), listPoint[i][j].getyCoord(), this.width, this.height);
                    g.setColor(Color.BLACK);
                } else if (listPoint[i][j].getColor().equals("grey")){
                    g.setColor(Color.LIGHT_GRAY);
                    g.fillRect(listPoint[i][j].getxCoord(), listPoint[i][j].getyCoord(), this.width, this.height);
                    g.setColor(Color.BLACK);
                } else if (listPoint[i][j].getColor().equals("yellow")){
                    g.setColor(Color.yellow);
                    g.fillRect(listPoint[i][j].getxCoord(), listPoint[i][j].getyCoord(), this.width, this.height);
                    g.setColor(Color.BLACK);
                } else if (listPoint[i][j].getColor().equals("purple")){
                    g.setColor(Color.MAGENTA);
                    g.fillRect(listPoint[i][j].getxCoord(), listPoint[i][j].getyCoord(), this.width, this.height);
                    g.setColor(Color.BLACK);
                }
                g.drawRect(listPoint[i][j].getxCoord(), listPoint[i][j].getyCoord(), this.width, this.height);
            }
        }
    }
    public void makeMaze(){
        Random r = new Random();
        for (int i = 0; i < rows;i++){
            for (int j = 0; j < cols; j++){
                int num = r.nextInt(2);
                if (num == 0) {
                    listPoint[i][j].setReachable(true);
                    listPoint[i][j].setColor("white");
                }else if (num == 1){
                    listPoint[i][j].setReachable(false);
                    listPoint[i][j].setColor("black");
                }
            }
        }
        p.repaint();


    }

    /**
     * function in charge of finding neighbors surrounding a point
     * @param x: the x coordinate of point
     * @param y: the y coordinate of point
     * @return a list of neighbor surrounding that point
     */
    public ArrayList<Point> makeNeighbors(int x, int y){
        ArrayList<Point> neighbors = new ArrayList<>();
        int[][] preCheck = new int[][]{{x-1,y},{x+1,y},{x,y-1},{x,y+1}};
        for (int i = 0; i < preCheck.length;i++){
            if (check2(preCheck[i][0],preCheck[i][1])){
                int X = preCheck[i][0];
                int Y = preCheck[i][1];
                neighbors.add(listPoint[X][Y]);
            }
        }
        return neighbors;

    }

    /**
     * solve maze by breadth-first search algorithm (version using a list of point to track the path)
     * @throws InterruptedException
     */
    public void solveMaze() throws InterruptedException {
        Q1Gen<ArrayList<Point>> q = new Q1Gen<>();
        ArrayList<Point> startArray = new ArrayList<>();
        startArray.add(listPoint[xStart][yStart]);
        System.out.println("Hi");
        q.add(startArray);
        listPoint[xStart][yStart].setVisited(true);
        int x = 0;
        int y = 0;
        ArrayList<Point> reachable; // initialize array to store coordinates of
        // reachable neighbors of a specific cell
        ArrayList<Point> path = new ArrayList<>();
        Point finalPoint;
        while (!q.isEmpty()) {
            path = q.remove();// remove the first path in the queue
            finalPoint = path.get(path.size() - 1);
            x = finalPoint.rows;// x coordinate of that cell
            y = finalPoint.cols;// y coordinate of that cell
            if (x != xEnd || y != yEnd) {// when we have not arrived the final cell
                reachable = reachableNeighbors(x, y);// make the list of reachable neighbors of that cell
                for (int i = 0; i < reachable.size(); i++) {
                    //add all of the reachable neighbors into the queue
                    ArrayList<Point> newPath = new ArrayList<>(path);
                    int xCoord = reachable.get(i).rows;
                    int yCoord = reachable.get(i).cols;
                    if (xCoord == xEnd && yCoord == yEnd){
                        path.add(listPoint[xCoord][yCoord]);
                        q = new Q1Gen<>();
                    }
                    newPath.add(listPoint[xCoord][yCoord]);
                    listPoint[xCoord][yCoord].setColor("purple");
                    q.add(newPath);
                    p.paintImmediately(p.getVisibleRect());
                    Thread.sleep(50);
                    listPoint[xCoord][yCoord].setColor("yellow");
                }

            } else {//when we arrived the final cell of the maze
                //maze has been solved
                break;
            }
        }

        for (int i = 0; i < path.size();i++){
            int X = path.get(i).rows;
            int Y = path.get(i).cols;
            listPoint[X][Y].setColor("cyan");
        }
        listPoint[xStart][yStart].setColor("green");
        listPoint[xEnd][yEnd].setColor("blue");
        p.paintImmediately(p.getVisibleRect());


    }

    /**
     * solve maze by breadth-first search algorithm (version setting the previous node for each node so we
     * only need to back track from the end point without memorizing the path)
     * @throws InterruptedException
     */
    public void solveMaze2() throws InterruptedException{
       Q1Gen<Point> listPoint = new Q1Gen<>();
       listPoint.add(this.listPoint[xStart][yStart]);
       this.listPoint[xStart][yStart].visited = true;
       ArrayList<Point> reachableNeighbors = new ArrayList<>();
       Point currentPoint = null;
       while(!listPoint.isEmpty()){
           currentPoint = listPoint.remove();
           int xCoord = currentPoint.rows;
           int yCoord = currentPoint.cols;
           reachableNeighbors = reachableNeighbors(xCoord,yCoord);
           for (int i = 0; i<reachableNeighbors.size(); i++){
               int x = reachableNeighbors.get(i).rows;
               int y = reachableNeighbors.get(i).cols;
               reachableNeighbors.get(i).setLastPoint(xCoord,yCoord);
               reachableNeighbors.get(i).setColor("purple");
               p.paintImmediately(p.getVisibleRect());
               reachableNeighbors.get(i).setColor("yellow");
               if (x == xEnd && y==yEnd){
                   currentPoint = reachableNeighbors.get(i);
                   listPoint = new Q1Gen<>();
                   break;
               }else {
                   listPoint.add(reachableNeighbors.get(i));
               }
           }
       }
       int currentX = currentPoint.rows;
       int currentY = currentPoint.cols;
       if (currentX == xEnd && currentY == yEnd) {
           int xTrace = xEnd;
           int yTrace = yEnd;
           while (xTrace != xStart || yTrace != yStart) {
               int xPrevious = this.listPoint[xTrace][yTrace].lastX;
               int yPrevious = this.listPoint[xTrace][yTrace].lastY;
               this.listPoint[xPrevious][yPrevious].setColor("cyan");
               xTrace = xPrevious;
               yTrace = yPrevious;
           }
           this.listPoint[xStart][yStart].setColor("green");
           this.listPoint[xEnd][yEnd].setColor("blue");
           p.paintImmediately(p.getVisibleRect());
           message.setText("Path found!");
       }else{
           System.out.println("not found");
           message.setText("No path to reach the destination");
       }

    }

    /**
     * solve maze using A* algorithm
     * @throws InterruptedException
     */
    public void solveMaze1() throws InterruptedException{
        ArrayList<Point> openList = new ArrayList<>();
        ArrayList<Point> closeList = new ArrayList<>();
        Point startPoint = listPoint[xStart][yStart];
        openList.add(startPoint);
        Point currentPoint;
        ArrayList<Point> path = new ArrayList<>();
        while (openList.size() > 0){
            currentPoint = openList.get(0);
            for (int i = 0; i < openList.size();i++) {
                if (openList.get(i).FCost < currentPoint.FCost) {
                    currentPoint = openList.get(i);
                }
            }
            //listPoint[currentPoint.rows][currentPoint.cols].setColor("yellow");
            //p.paintImmediately(p.getVisibleRect());
            //Thread.sleep(50);
            openList.remove(currentPoint);
            closeList.add(currentPoint);
            if (currentPoint.rows == xEnd && currentPoint.cols == yEnd){
                Point tracePoint = currentPoint;
                while (tracePoint.getLastX()!=xStart || tracePoint.getLastY() != yStart){
                    int x = tracePoint.getLastX();
                    int y = tracePoint.getLastY();
                    path.add(listPoint[x][y]);
                    tracePoint = listPoint[x][y];
                }
                path.add(tracePoint);
                break;
            } else {
                ArrayList<Point> neighbors = reachableNeighbors1(currentPoint.rows,currentPoint.cols);
                for (int i = 0; i < neighbors.size(); i++){
                    System.out.println(neighbors.get(i).rows + " " + neighbors.get(i).cols);
                    System.out.println(neighbors.get(i).getReachable());
                    if (inList(closeList,neighbors.get(i)) == -1){
                        if (inList(openList,neighbors.get(i)) != -1){
                            int index = inList(openList,neighbors.get(i));
                            Point point= openList.get(index);
                            double currentGCost = 0;
                            if (checkCorner(point.rows,point.cols,currentPoint.rows,currentPoint.cols)){
                                currentGCost = currentPoint.GCost + Math.sqrt(2);
                            }else{
                                currentGCost = currentPoint.GCost + 1;
                            }
                            if (point.GCost > currentGCost){
                                openList.add(neighbors.get(i));
                                neighbors.get(i).setColor("yellow");
                                p.paintImmediately(p.getVisibleRect());
                                Thread.sleep(10);
                                neighbors.get(i).setColor("purple");
                                neighbors.get(i).setLastPoint(currentPoint.rows,currentPoint.cols);
                                neighbors.get(i).GCost = currentGCost;
                                int deltaX = neighbors.get(i).rows - xEnd;
                                int deltaY = neighbors.get(i).cols - yEnd;
                                neighbors.get(i).HCost = Math.sqrt(deltaX*deltaX + deltaY*deltaY);
                                neighbors.get(i).FCost = neighbors.get(i).GCost + neighbors.get(i).HCost;

                            }
                        } else if (inList(openList,neighbors.get(i)) == -1){
                            neighbors.get(i).GCost = neighbors.get(i).GCost + 1;
                            int deltaX = neighbors.get(i).rows - xEnd;
                            int deltaY = neighbors.get(i).cols - yEnd;
                            neighbors.get(i).HCost = Math.sqrt(deltaX*deltaX + deltaY*deltaY);
                            neighbors.get(i).FCost = neighbors.get(i).GCost + neighbors.get(i).HCost;
                            openList.add(neighbors.get(i));
                            neighbors.get(i).setColor("yellow");
                            p.paintImmediately(p.getVisibleRect());
                            Thread.sleep(10);
                            neighbors.get(i).setColor("purple");
                            neighbors.get(i).setLastPoint(currentPoint.rows,currentPoint.cols);
                        }
                    }
                }
            }
        }
        for (int i = 0 ; i < path.size(); i++){
            int x = path.get(i).rows;
            int y = path.get(i).cols;
            listPoint[x][y].setColor("cyan");
        }
        listPoint[xStart][yStart].setColor("green");
        listPoint[xEnd][yEnd].setColor("blue");
        p.repaint();

    }

    /**
     * function checking whether a point has already existed in a list
     * @param List a list containing points
     * @param neighbor the point needs to be checked
     * @return -1 if the point does not exist or index where the point was found in the list
     */
    public int inList(ArrayList<Point> List,Point neighbor){
        for (int i = 0; i < List.size();i++){
            if (neighbor.equals(List.get(i))){
                return i;
            }
        }
        return -1;
    }
    public boolean checkCorner(int xN, int yN, int xP,int yP){
        if (xN == xP - 1 && yN == yP - 1){
            return true;
        }
        if (xN == xP - 1 && yN == yP + 1){
            return true;
        }
        if (xN == xP + 1 && yN == yP - 1){
            return true;
        }
        if (xN == xP + 1 && yN == yP + 1){
            return true;
        }
        return false;
    }

    public static void solveMaze(Point[][] grid,int xStart, int yStart, int xEnd, int yEnd){
        Q1Gen<ArrayList<Point>> q = new Q1Gen<>();
        ArrayList<Point> startArray = new ArrayList<>();
        startArray.add(grid[xStart][yStart]);
        System.out.println("Hi");
        q.add(startArray);
        grid[xStart][yStart].setVisited(true);
        int x = 0;
        int y = 0;
        ArrayList<Point> reachable; // initialize array to store coordinates of
        // reachable neighbors of a specific cell
        ArrayList<Point> path = new ArrayList<>();
        Point finalPoint;
        System.out.println("Start point is " + xStart + " " + yStart);
        while (!q.isEmpty()){
            path = q.remove();// remove the first path in the queue
            finalPoint = path.get(path.size() - 1);
            x = finalPoint.rows;// x coordinate of that cell
            y = finalPoint.cols;// y coordinate of that cell
            if(x != xEnd || y!= yEnd) {// when we have not arrived the final cell
                reachable = neighbors(x, y,grid);// make the list of reachable neighbors of that cell
                for (int i = 0; i < reachable.size(); i++){
                    //add all of the reachable neighbors into the queue
                    ArrayList<Point> newPath = new ArrayList<>(path);
                    int xCoord = reachable.get(i).rows;
                    int yCoord = reachable.get(i).cols;
                    newPath.add(grid[xCoord][yCoord]);
                    q.add(newPath);
                }
            }else {//when we arrived the final cell of the maze
                //maze has been solved
                break;
            }
        }
        System.out.println("Done");

    }
    public static ArrayList<Point> neighbors(int x, int y, Point[][] listPoint){
        ArrayList<Point> neighbors = new ArrayList<>();
        int upX = x - 1;
        int upY = y;
        int downX = x + 1;
        int downY = y;
        int leftX = x;
        int leftY = y - 1;
        int rightX = x;
        int rightY = y + 1;
        if (check1(upX,upY,listPoint)){
            neighbors.add(listPoint[upX][upY]);
            listPoint[upX][upY].setVisited(true);
        }
        if (check1(downX,downY,listPoint)){
            neighbors.add(listPoint[downX][downY]);
            listPoint[downX][downY].setVisited(true);
        }
        if (check1(rightX,rightY,listPoint)){
            neighbors.add(listPoint[rightX][rightY]);
            listPoint[rightX][rightY].setVisited(true);
        }
        if (check1(leftX,leftY,listPoint)){
            neighbors.add(listPoint[leftX][leftY]);
            listPoint[leftX][leftY].setVisited(true);
        }
        return neighbors;
    }

    public ArrayList<Point> reachableNeighbors(int x, int y){
        ArrayList<Point> neighbors = new ArrayList<>();
        int upX = x - 1;
        int upY = y;
        int downX = x + 1;
        int downY = y;
        int leftX = x;
        int leftY = y - 1;
        int rightX = x;
        int rightY = y + 1;
        if (check(upX,upY)){
            neighbors.add(listPoint[upX][upY]);
            listPoint[upX][upY].setVisited(true);
        }
        if (check(downX,downY)){
            neighbors.add(listPoint[downX][downY]);
            listPoint[downX][downY].setVisited(true);
        }
        if (check(rightX,rightY)){
            neighbors.add(listPoint[rightX][rightY]);
            listPoint[rightX][rightY].setVisited(true);
        }
        if (check(leftX,leftY)){
            neighbors.add(listPoint[leftX][leftY]);
            listPoint[leftX][leftY].setVisited(true);
        }
        return neighbors;
    }
    public ArrayList<Point> reachableNeighbors1(int x, int y){
        ArrayList<Point> neighbors = new ArrayList<>();
        int upX = x - 1;
        int upY = y;
        int downX = x + 1;
        int downY = y;
        int leftX = x;
        int leftY = y - 1;
        int rightX = x;
        int rightY = y + 1;
        int UleftCornerX = x-1;
        int UleftCornerY = y -1;
        int UrightCornerX = x -1 ;
        int UrightCornerY = y + 1;
        int DleftCornerX = x + 1;
        int DleftCornerY = y - 1;
        int DRightCornerX = x + 1;
        int DRightCornerY = y + 1;
        if (check(upX,upY)){
            neighbors.add(listPoint[upX][upY]);

        }
        if (check(downX,downY)){
            neighbors.add(listPoint[downX][downY]);

        }
        if (check(rightX,rightY)){
            neighbors.add(listPoint[rightX][rightY]);
        }
        if (check(leftX,leftY)){
            neighbors.add(listPoint[leftX][leftY]);

        }
        if (check(UleftCornerX,UleftCornerY)){
            neighbors.add(listPoint[UleftCornerX][UleftCornerY]);
        }
        if (check(UrightCornerX,UrightCornerY)){
            neighbors.add(listPoint[UrightCornerX][UrightCornerY]);
        }
        if (check(DleftCornerX,DleftCornerY)){
            neighbors.add(listPoint[DleftCornerX][DleftCornerY]);
        }
        if (check(DRightCornerX,DRightCornerY)){
            neighbors.add(listPoint[DRightCornerX][DRightCornerY]);

        }

        return neighbors;
    }
    public static boolean check1(int x , int y, Point[][] listPoint){
        if (0 <= x && x < listPoint.length && 0 <= y && y < listPoint.length && listPoint[x][y].getReachable() && !listPoint[x][y].visited){
            return true;
        }
        return false;
    }
    public boolean check(int x, int y){
        if (0 <= x && x < rows && 0 <= y && y < cols && listPoint[x][y].getReachable() && !listPoint[x][y].visited){
            return true;
        }
        return false;
    }
    public boolean check2(int x, int y){
        if (0 <= x && x < rows && 0 <= y && y < cols && !listPoint[x][y].visited){
            return true;
        }
        return false;
    }
    public boolean check3(int x, int y){
        if (0 <= x && x < rows && 0 <= y && y < cols && listPoint[x][y].getReachable()){
            return true;
        }
        return false;
    }


    public static void main(String[] args){
        grid = new MyGridLayout(60,60);
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        int x = mouseEvent.getX();
        int y = mouseEvent.getY();
        int rows = (y-20)/this.width;
        int cols = (x-20)/this.width;
        if (0 <= rows && rows<this.rows && 0 <= cols && cols <= this.cols) {
            if (listPoint[rows][cols].getColor().equals("white") && !hadStart && !hadEnd) {
                listPoint[rows][cols].setColor("green");
                hadStart = true;
                xStart = rows;
                yStart = cols;
            } else if (listPoint[rows][cols].getColor().equals("white") && hadStart && !hadEnd) {
                listPoint[rows][cols].setColor("blue");
                hadEnd = true;
                xEnd = rows;
                yEnd = cols;
            } else if (listPoint[rows][cols].getColor().equals("green")) {
                listPoint[rows][cols].setColor("white");
                hadStart = false;
            } else if (listPoint[rows][cols].getColor().equals("white") && !hadStart && hadEnd) {
                listPoint[rows][cols].setColor("green");
                hadStart = true;
                xStart = rows;
                yStart = cols;
            } else if (listPoint[rows][cols].getColor().equals("blue")) {
                listPoint[rows][cols].setColor("white");
                hadEnd = false;
            }
        }
        p.repaint();

    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {


    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }


    @Override
    public void mouseDragged(MouseEvent mouseEvent) {
        int x = mouseEvent.getX();
        int y = mouseEvent.getY();
        int rows = (y - 20)/this.width;
        int cols = (x- 20)/this.width;
        if (0<= rows && rows <this.rows && 0 <= cols && cols < this.cols) {
            if (!listPoint[rows][cols].getColor().equals("blue") && !listPoint[rows][cols].getColor().equals("green")) {
                listPoint[rows][cols].setColor("black");
                listPoint[rows][cols].setReachable(false);
            }
        }
        p.repaint();
    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent) {
//        int x = mouseEvent.getX();
//        int y = mouseEvent.getY();
//        int rows = (y-20)/this.width;
//        int cols = (x-20)/this.width;
//        if (0<= rows && rows <this.rows && 0 <= cols && cols < this.cols) {
//            if (!unSet) {
//                unSet = true;
//                xOnClick = rows;
//                yOnclick = cols;
//                if (xOnClick != xStart && yOnclick!=yStart) {
//                    listPoint[rows][cols].setColor("yellow");
//                }
//            } else {
//                if (rows!=xStart && cols != yStart) {
//                    listPoint[xOnClick][yOnclick].setColor("white");
//                    xOnClick = rows;
//                    yOnclick = cols;
//                    listPoint[xOnClick][yOnclick].setColor("yellow");
//                }
//            }
//        }
//        p.repaint();
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {

    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {

    }


    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        String event = actionEvent.getActionCommand();
        if (event.equals("Reset")) {
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    listPoint[i][j].setColor("white");
                    listPoint[i][j].setVisited(false);
                    listPoint[i][j].setReachable(true);
                    listPoint[i][j].GCost = 0;
                    listPoint[i][j].HCost = 0;
                    listPoint[i][j].FCost = 0;
                }
            }
            hadEnd = false;
            hadStart = false;
            message.setText("Welcome to solve maze");
            p.repaint();
        }else if (event.equals("Generate")){
            makeMaze();
        }else if (event.equals("A*")){
            try {
                solveMaze1();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }else if (event.equals("Breadth-first search")){
            try {
                solveMaze2();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}