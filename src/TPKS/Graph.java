package TPKS;


import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JPanel;

/**
 *
 * @author Anna
 */
public class Graph extends JPanel {

    private ArrayList<Vertex> vertexes = new ArrayList<>();
    private ArrayList<EdgeComponent> edges = new ArrayList<>();
private HashMap<Vertex, Integer> map = new HashMap<>();
    /**
     * Constructor
     *
     * @param vertexes array of vertexes
     * @param edges array of edges
     */
    public Graph(ArrayList<Vertex> vertexes, ArrayList<EdgeComponent> edges) {
        this.vertexes = vertexes;
        this.edges = edges;
        for (int i = 0; i < vertexes.size(); i++) {
            this.add(vertexes.get(i));
        }
        for (int i = 0; i < edges.size(); i++) {
            this.add(edges.get(i));
        }
        repaint();
    }

    /**
     * Constructor
     *
     * @param structures array of structures in algorithm
     * @throws Exception
     */
    public Graph(ArrayList<AlgoStructure> structures) throws Exception {
        int c = 1;
         map = new HashMap<>();
        for (int i = 0; i < structures.size(); i++) {
            if (!structures.get(i).isX()) {
                vertexes.add(new Vertex(structures.get(i), c));
                c++;
            }
        }
        for (int i = 0; i < vertexes.size(); i++) {
            for (int j = 0; j < vertexes.size(); j++) {
                ArrayList<String> way = new ArrayList<>();
                vertexes.get(i).getAlgostructure().way(vertexes.get(j).getAlgostructure(), way, "");
                if (!way.isEmpty()) {
                    for (int k = 0; k < way.size(); k++) {
                        edges.add(new EdgeComponent(this, vertexes.get(i), vertexes.get(j), way.get(k)));
                        vertexes.get(i).addComponent(edges.get(edges.size() - 1));
                        vertexes.get(j).addComponent(edges.get(edges.size() - 1));
                    }
                }
            }
        }
        for (int i = 0; i < edges.size(); i++) {
            Vertex from = edges.get(i).getFrom();
            Vertex to = edges.get(i).getTo();
            EdgeComponent edge = edges.get(i);
            if (map.containsKey(from) && map.containsKey(to)) {
                if (!isAdjacentCode(map.get(to), map.get(from))) {
                    //       int code = getAddCode(map.get(to), map.get(from));
                    ArrayList<Integer> list = new ArrayList<>();
                    getCode(map.get(to), map.get(from), list, map);
                    for (int j = 0; j < list.size(); j++) {
                        Vertex v = new Vertex(vertexes.size() + 1);
                        vertexes.add(v);
                        map.put(v, list.get(j));
                        edge.setTo(v);
                        edges.add(new EdgeComponent(this, v, to, "-"));
                        edge = edges.get(edges.size() - 1);
                    }
                }
            } else if (map.containsKey(to)) {
                map.put(from, getFreeAdjacentCode(map.get(to), map));
            } else if (map.containsKey(from)) {
                map.put(to, getFreeAdjacentCode(map.get(from), map));
            } else if (!map.containsKey(to) && !map.containsKey(from)) {
                int code = 0;
                while (map.containsValue(code)) {
                    code++;
                }
                map.put(from, code);
                map.put(to, getFreeAdjacentCode(code, map));
            } else {
                throw new Exception();
            }
        }
        int max = 0;
        for (int i = 0; i < vertexes.size(); i++) {
            if (map.get(vertexes.get(i)) > max) {
                max = map.get(vertexes.get(i));
            }
        }
        int r = 0;
        while (max > 0) {
            r++;
            max = max >> 1;
        }
        r--;
        for (int i = 0; i < vertexes.size(); i++) {
            vertexes.get(i).setCode(map.get(vertexes.get(i)), r);
        }
        for (int i = 0; i < vertexes.size(); i++) {
            this.add(vertexes.get(i));
        }
        for (int i = 0; i < edges.size(); i++) {
            this.add(edges.get(i));
        }
        repaint();
    }
//getters and setters

    /**
     *
     * @return
     */
    public ArrayList<Vertex> getVertexes() {
        return vertexes;
    }

    /**
     *
     * @return
     */
    public ArrayList<EdgeComponent> getEdges() {
        return edges;
    }

    /**
     * Get new code, adjacent to c
     *
     * @param c given code
     * @param code hashmap of codes and vertexes
     * @return
     */
    private int getFreeAdjacentCode(int c, HashMap<Vertex, Integer> code) {
        int t = 1;
        for (int i = 0; i < 31; i++) {
            if (!code.containsValue(c & t)) {
                return c & t;
            }
            if (!code.containsValue(c | t)) {
                return c | t;
            }
            t = t << 1;
        }
        return -1;
    }

    /**
     * Get new code, adjacent to code1 and code2
     *
     * @param code1 code 1
     * @param code2 code 2
     * @param list list of codes, which needs to be pathed
     * @param map hashmap of codes and vertexes
     */
    public static void getCode(int code1, int code2, ArrayList<Integer> list, HashMap<Vertex, Integer> map) {
        if (isAdjacentCode(code1, code2)) {
            return;
        }
        int c1 = code1;
        int c2 = code2;
        int i = 0;
        while (true) {
            if (((c1 & 1) != (c2 & 1)) || ((c1 == 0) && (c2 == 0))) {
                int t = 1;
                t = t << i;
                if (!map.containsValue(code1 ^ t)) {
                    code1 = code1 ^ t;
                    if (isAdjacentCode(code1, code2)) {
                        list.add(code1);
                        return;
                    }
                    getCode(code1, code2, list, map);
                    list.add(code1);
                    return;
                }
            }
            c1 = c1 >> 1;
            c2 = c2 >> 1;
            i++;
        }
    }

    /**
     * Check if code is adjacent
     *
     * @param c first code
     * @param c1 second code
     * @return true - adjacent, false - not
     */
    private static boolean isAdjacentCode(int c, int c1) {
        if (c == 0 && c1 == 0) {
            return true;
        }
        int r;
        if (c > c1) {
            r = c ^ c1;
        } else {
            r = c1 ^ c;
        }
        int t = 1;
        boolean correct = false;
        for (int i = 0; i < 31; i++) {
            if ((t & r) > 0) {
                if (!correct) {
                    correct = true;
                } else {
                    return false;
                }
            }
            t = t << 1;
        }
        return true;
    }

    public HashMap<Vertex, Integer> getMap() {
        return map;
    }
    
}
