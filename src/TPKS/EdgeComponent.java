package TPKS;


import java.awt.Graphics;
import java.awt.Point;
import javax.swing.JComponent;
import javax.swing.JPanel;

/**
 * Class for edge
 *
 * @author Anna
 */
public class EdgeComponent extends JComponent {

    private Vertex from;
    private Vertex to;
    private String path;
//getters and setters

    /**
     *
     * @return
     */
    public Vertex getFrom() {
        return from;
    }

    /**
     *
     * @param from
     */
    public void setFrom(Vertex from) {
        this.from = from;
    }

    /**
     *
     * @return
     */
    public Vertex getTo() {
        return to;
    }

    /**
     *
     * @param to
     */
    public void setTo(Vertex to) {
        this.to = to;
    }

    /**
     * Constructor
     *
     * @param panel parent panel
     * @param from from vertex
     * @param to to vertex
     * @param path path between vertexes
     */
    public EdgeComponent(JPanel panel, Vertex from, Vertex to, String path) {
        setSize(1500, 1500);
        this.from = from;
        this.to = to;
        this.path = path;
    }

    /**
     * Inherited method
     *
     * @param x
     * @param y
     * @return
     */
    public boolean contains(int x, int y) {
        return false;
    }

    /**
     * Paint edge on panel
     *
     * @param g
     */
    public void paintComponent(Graphics g) {
        setSize(getParent().getSize());
        Point p1 = new Point(from.getX(), from.getY());
        Point p2 = new Point(to.getX(), to.getY());
        int maxX = 0;
        int minX = 0;
        int maxY = 0;
        int minY = 0;
        if (p1.x > p2.x) {
            maxX = p1.x;
            minX = p2.x;
        } else {
            maxX = p2.x;
            minX = p1.x;
        }
        if (p1.y > p2.y) {
            maxY = p1.y;
            minY = p2.y;
        } else {
            maxY = p2.y;
            minY = p1.y;
        }
        int w = maxX - minX;
        int h = maxY - minY;

        if ((p1.x > p2.x && p1.y > p2.y) || (p1.x < p2.x && p1.y < p2.y)) {
            g.drawArc(minX - w + 25, minY + 25, w * 2, h * 2, 0, 90);
        } else {
            g.drawArc(minX + 25, minY + 25, w * 2, h * 2, 90, 90);
        }
        if (p1.x > p2.x) {
            g.drawString("<" + path, minX + 50, minY + 50);
        } else {
            g.drawString(">" + path, minX + 50, minY + 50);
        }
        getParent().repaint();
    }

    /**
     *
     * @return
     */
    public String getPath() {
        return path;
    }
}
