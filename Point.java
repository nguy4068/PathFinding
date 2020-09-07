public class Point {
    private int xCoord;
    private int yCoord;
    private String color ;
    private boolean reachable;
    public int rows;
    public int cols;
    public boolean visited;
    public int lastX;
    public int lastY;
    public Point lastPoint;
    public double GCost;
    public double HCost;
    public double FCost;
    public Point(int x, int y,int rows, int cols){
        this.xCoord = x;
        this.yCoord = y;
        this.color = "white";
        reachable = true;
        this.rows = rows;
        this.cols = cols;
        this.visited = false;


    }
    public Point(int rows, int cols){
        reachable = true;
        this.rows = rows;
        this.cols = cols;
        this.visited = false;

    }
    public void setColor(String color){
        this.color = color;
    }
    public int getxCoord(){
        return this.xCoord;
    }
    public int getyCoord(){
        return this.yCoord;
    }
    public String getColor(){
        return this.color;
    }
    public boolean getReachable(){
        return this.reachable;
    }
    public void setReachable(boolean state){
        reachable = state;
    }
    public void setVisited(boolean state){
        this.visited = state;
    }
    public void setLastPoint(Point p){
        lastPoint = p;
    }
    public Point getLastPoint(){
        return lastPoint;
    }
    public void setLastPoint(int x, int y){
        this.lastX = x;
        this.lastY = y;
    }
    public int getLastX(){
        return this.lastX;
    }
    public int getLastY(){
        return this.lastY;
    }
    public boolean equals(Point anotherPoint){
        if (anotherPoint.rows == this.rows && anotherPoint.cols == this.cols){
            return true;
        }
        return false;
    }

}
