package TPKS;


import java.util.ArrayList;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Anna
 */
public class AlgoStructure {

    private AlgoStructure trueLink;
    private AlgoStructure falseLink;
    private ArrayList<String> y = new ArrayList<String>();
    private ArrayList<String> x = new ArrayList<String>();
    private int trueIndex = -1;
    private int falseIndex = -1;
    private ArrayList<Integer> index = new ArrayList<Integer>();

    /**
     *
     * @param a
     * @param way
     * @param path2
     */
    public void way(AlgoStructure a, ArrayList<String> way, String path2) {
        if (trueLink != a) {
            if (trueLink != null) {
                if (trueLink.isX()) {
                    AlgoStructure t = trueLink;
                    trueLink = null;
                    if (x.isEmpty()) {
                        t.way(a, way, path2);
                    } else {
                        t.way(a, way, path2 + x.toString());
                    }
                    trueLink = t;
                }
            }
        } else {
            if (x.isEmpty()) {
                way.add(path2 + "-");
            } else {
                way.add(path2 + x.toString());
            }
        }

        if (falseLink != a) {
            if (falseLink != null) {
                if (falseLink.isX()) {
                    AlgoStructure t = falseLink;
                    falseLink = null;
                    if (x.isEmpty()) {
                        t.way(a, way, path2);
                    } else {
                        t.way(a, way, path2 + "NOT" + x.toString());
                    }
                    falseLink = t;
                }
            }
        } else {
            way.add(path2 + "NOT" + x.toString());
        }

    }

    /**
     *
     * @param yi
     */
    public void addY(String yi) {
        y.add(yi);
    }

    /**
     *
     * @param paths
     * @param path
     */
    public void searchPath(ArrayList<String> paths, String path) {
        if (isE()) {
            trueLink = null;
            paths.add(path + "E");
            return;
        }
        if (trueLink != null) {
            AlgoStructure t = trueLink;

            if (isOperation()) {
                trueLink = null;
                t.searchPath(paths, path + y.toString() + " ");
            } else if (isX()) {
                trueLink = null;
                t.searchPath(paths, path + x.toString() + " ");
            } else {
                t.searchPath(paths, "B ");
            }
            trueLink = t;
        }
        if (falseLink != null) {
            AlgoStructure t = falseLink;
            falseLink = null;
            t.searchPath(paths, path + "NOT" + x.toString() + " ");
            falseLink = t;
        }
    }

    /**
     *
     * @param xi
     */
    public void addX(String xi) {
        x.add(xi);
    }

    /**
     *
     * @return
     */
    public boolean isB() {
        return y.isEmpty() && x.isEmpty()&&(trueLink!=null||falseLink!=null);
    }

    /**
     *
     * @return
     */
    public boolean isE() {
        return y.isEmpty() && x.isEmpty() && trueLink == null && falseLink == null;
    }

    /**
     *
     * @return
     */
    public boolean isX() {
        return !x.isEmpty();
    }

    /**
     *
     * @param index
     */
    public void addIndex(int index) {
        this.index.add(index);
    }

    /**
     *
     * @return
     */
    public boolean isOperation() {
        return !y.isEmpty();
    }

    /**
     *
     * @return
     */
    public boolean isEmptyBlock() {
        return y.isEmpty() && x.isEmpty();
    }

    /**
     *
     * @return
     */
    public ArrayList<Integer> getIndex() {
        return index;
    }

    /**
     *
     * @param index
     */
    public void setIndex(ArrayList<Integer> index) {
        this.index = index;
    }

    /**
     *
     * @return
     */
    public int getTrueIndex() {
        return trueIndex;
    }

    /**
     *
     * @param trueIndex
     */
    public void setTrueIndex(int trueIndex) {
        this.trueIndex = trueIndex;
    }

    /**
     *
     * @return
     */
    public int getFalseIndex() {
        return falseIndex;
    }

    /**
     *
     * @param falseIndex
     */
    public void setFalseIndex(int falseIndex) {
        this.falseIndex = falseIndex;
    }

    /**
     *
     * @return
     */
    public AlgoStructure getTrueLink() {
        return trueLink;
    }

    /**
     *
     * @return
     */
    public AlgoStructure getFalseLink() {
        return falseLink;
    }

    /**
     *
     * @param falseLink
     */
    public void setFalseLink(AlgoStructure falseLink) {
        this.falseLink = falseLink;
    }

    /**
     *
     * @param trueLink
     */
    public void setTrueLink(AlgoStructure trueLink) {
        this.trueLink = trueLink;
    }

    /**
     *
     * @return
     */
    public ArrayList<String> getY() {
        return y;
    }

    /**
     *
     * @param y
     */
    public void setY(ArrayList<String> y) {
        this.y = y;
    }

    /**
     *
     * @return
     */
    public ArrayList<String> getX() {
        return x;
    }

    /**
     *
     * @param x
     */
    public void setX(ArrayList<String> x) {
        this.x = x;
    }

    /**
     *
     * @param index
     * @return
     */
    public boolean hasIndex(int index) {
        return this.index.contains(index);
    }

    /**
     *
     * @param structures
     */
    public void setLinks(ArrayList<AlgoStructure> structures) {
        if (trueIndex != -1) {
            for (AlgoStructure as : structures) {
                if (as.hasIndex(trueIndex)) {
                    trueIndex = -1;
                    trueLink = as;
                    break;
                }
            }
        }
        if (falseIndex != -1) {
            for (AlgoStructure as : structures) {
                if (as.hasIndex(falseIndex)) {
                    falseLink = as;
                    break;
                }
            }
        }
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        if (trueLink == null && falseLink == null) {
            return "E";
        }
        if (x.isEmpty() && y.isEmpty()) {
            return "B" + trueLink.toString();
        }
        if (falseLink != null) {
            return x.toString() + "T(" + trueLink.toString() + ") F(" + falseLink.toString() + ")";
        } else {
            return y.toString() + trueLink.toString();
        }
    }
}
