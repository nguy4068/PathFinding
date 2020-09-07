public class Node {
    private int rows;
    private int cols;
    private int x;
    private int y;
    private boolean reachable;
    private boolean isClosed;
    private Node priorNode;
    public Node(int rows, int cols, int x, int y){
        this.rows = rows;
        this.cols = cols;
        this.x = x;
        this.y = y;
        reachable = true;
        isClosed = false;
        priorNode = null;
    }
    public void setReachable(boolean state){
        reachable = state;
    }
    public void setClosed(){
        isClosed = true;
    }
    public void setPriorNode(Node n){
        priorNode = n;
    }
    public int getX(){
        return this.x;
    }
    public int getY(){
        return this.y;
    }
    public int getRows(){
        return rows;
    }
    public int getCols(){
        return cols;
    }

}
