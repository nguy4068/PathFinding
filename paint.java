import javax.swing.*;
import java.awt.*;

public class paint extends JPanel {
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        MyGridLayout.grid.repaint(g);
    }
}
