package TPKS;


import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JComponent;

/**
 * Class for vertexes
 *
 * @author Anna
 */
public class Vertex extends JComponent implements MouseMotionListener,
        MouseListener {

    private int size = 50;
    private int index;
    private AlgoStructure algostructure;
    private String value;
    private ArrayList<EdgeComponent> edges = new ArrayList<>();
    private Point p = new Point();
    private String code;

    /**
     * Constructir
     *
     * @param algostructure structure, corresponding for vertex
     * @param index index of vertex
     */
    public Vertex(AlgoStructure algostructure, int index) {
        setBounds(0, 0, size, size);
        if (algostructure.isOperation()) {
            this.value = "z" + index + algostructure.getY().toString();
        } else {
            this.value = "z" + index + "-";
        }
        this.index = index;
        this.algostructure = algostructure;
        Random r = new Random();
        this.setLocation(r.nextInt(200), r.nextInt(200));
        addMouseMotionListener(this);
        addMouseListener(this);
    }

    /**
     * Constructor
     *
     * @param value value of vertex
     * @param code code of vertex
     */
    public Vertex(String value, String code) {
        setBounds(0, 0, size, size);
        this.value = value;
        this.code = code;
        Random r = new Random();
        this.setLocation(r.nextInt(200), r.nextInt(200));
        addMouseMotionListener(this);
        addMouseListener(this);
    }

    /**
     * Constructor
     *
     * @param index index of vertex
     */
    public Vertex(int index) {
        setBounds(0, 0, size, size);
        this.value = "z" + index + "-";
        this.index = index;
        Random r = new Random();
        this.setLocation(r.nextInt(200), r.nextInt(200));
        addMouseMotionListener(this);
        addMouseListener(this);
    }
//gertters and setters

    /**
     *
     * @param size
     */
    public void setSize(int size) {
        this.size = size;
    }

    /**
     *
     * @return
     */
    public int getIndex() {
        return index;
    }

    /**
     *
     * @param index
     */
    public void setIndex(int index) {
        this.index = index;
    }

    /**
     *
     * @return
     */
    public AlgoStructure getAlgostructure() {
        return algostructure;
    }

    /**
     *
     * @param algostructure
     */
    public void setAlgostructure(AlgoStructure algostructure) {
        this.algostructure = algostructure;
    }

    /**
     *
     * @return
     */
    public String getValue() {
        return value;
    }

    /**
     *
     * @param value
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     *
     * @return
     */
    public ArrayList<EdgeComponent> getEdges() {
        return edges;
    }

    /**
     *
     * @param edges
     */
    public void setEdges(ArrayList<EdgeComponent> edges) {
        this.edges = edges;
    }

    /**
     *
     * @return
     */
    public Point getP() {
        return p;
    }

    /**
     *
     * @param p
     */
    public void setP(Point p) {
        this.p = p;
    }

    /**
     *
     * @param code
     * @param r
     */
    public void setCode(int code, int r) {
        this.code = Integer.toBinaryString(code);
        while (this.code.length() <= r) {
            this.code = "0" + this.code;
        }
    }

    /**
     *
     * @return
     */
    public String getCode() {
        return code;
    }

    /**
     * Add new edge to vertex
     *
     * @param edge edge
     */
    public void addComponent(EdgeComponent edge) {
        edges.add(edge);
    }

    /**
     * Paint component on panel
     *
     * @param g
     */
    @Override
    public void paintComponent(Graphics g) {
        g.drawOval(0, 0, size, size);
        g.drawString(value, 10, size / 2);
        g.drawString(code, 0, 15);
        for (int i = 0; i < edges.size(); i++) {
            edges.get(i).repaint();
        }
    }

    /**
     * Movement of mouse
     *
     * @param arg0
     */
    public void mouseDragged(MouseEvent arg0) {
        setLocation((int) (this.getX() + arg0.getXOnScreen() - p.getX()), (int) (this.getY() + arg0.getYOnScreen() - p.getY()));
        p.setLocation(arg0.getLocationOnScreen());

        repaint();
    }

    /**
     * Pressed mouse
     *
     * @param arg0
     */
    public void mousePressed(MouseEvent arg0) {
        p.setLocation(arg0.getLocationOnScreen());
    }

    /**
     * Inherited methods
     *
     * @param me
     */
    @Override
    public void mouseMoved(MouseEvent me) {
    }

    /**
     *
     * @param me
     */
    @Override
    public void mouseReleased(MouseEvent me) {
    }

    /**
     *
     * @param me
     */
    @Override
    public void mouseEntered(MouseEvent me) {
    }

    /**
     *
     * @param me
     */
    @Override
    public void mouseExited(MouseEvent me) {
    }

    /**
     *
     * @param me
     */
    @Override
    public void mouseClicked(MouseEvent me) {
    }
}
