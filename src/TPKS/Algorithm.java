package TPKS;


import java.util.ArrayList;
import java.util.Stack;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Anna
 */
public class Algorithm {
    
    private String algorithm;
    private ArrayList<AlgoStructure> structures = new ArrayList<>();
    private Stack<Integer> stArrowDown = new Stack<>();
    private int[][] matrix;
    private ArrayList<String> sign = new ArrayList<String>();
    private int[][] signals;
    private ArrayList<String> path = new ArrayList<String>();
    
    /**
     *
     * @return
     */
    public ArrayList<AlgoStructure> getStructures() {
        return structures;
    }
    
    private enum Symbols {
        
        B, E, X, Y, arrowDown
    }
    private Stack<Symbols> st = new Stack<>();
    
    /**
     *
     * @return
     */
    public ArrayList<String> getPaths() {
        structures.get(0).searchPath(path, "");
        return path;
    }
    
    /**
     *
     * @param matrix
     */
    public void setMatrix(int[][] matrix) {
        this.matrix = matrix;
    }
    
    /**
     *
     * @param sign
     */
    public void setSign(ArrayList<String> sign) {
        this.sign = sign;
    }
    
    /**
     *
     * @param signals
     */
    public void setSignals(int[][] signals) {
        this.signals = signals;
    }
    
    /**
     *
     * @param algorithm
     * @throws Exception
     */
    public void parseAlgorithm(String algorithm) throws Exception {
        st.clear();
        sign.clear();
        stArrowDown.clear();
        structures.clear();
        if (algorithm.charAt(0) != 'B') {
            throw new Exception("Invalid begin");
        }
        this.algorithm = algorithm;

        // map.put(map.size(), new AlgoStructure());
        structures.add(new AlgoStructure());
        st.add(Symbols.B);
        for (int i = 1; i < algorithm.length(); i++) {
            switch (algorithm.charAt(i)) {
                case 'Y': {
                    i += handleY(i);
                    break;
                }
                case 'X': {
                    i += handleX(i);
                    break;
                }
                case 'E': {
                    i += handleE(i);
                    break;
                }
                case '(': {
                    i = handleOpBr(i);
                    break;
                }
                case '↑': {
                    i += handleArrowUp(i);
                    break;
                }
                case '↓': {
                    i += handleArrowDown(i);
                    break;
                }
                default: {
                    throw new Exception("unexpected symbol");
                }
            }
        }
        for (AlgoStructure as : structures) {
            as.setLinks(structures);
        }
        matrix = new int[structures.size()][structures.size()];
        for (int i = 0; i < structures.size(); i++) {
            if (structures.get(i).getTrueLink() != null) {
                matrix[i][structures.indexOf(structures.get(i).getTrueLink())] = 1;
            }
            if (structures.get(i).getFalseLink() != null) {
                matrix[i][structures.indexOf(structures.get(i).getFalseLink())] = 2;
            }
        }
        signals = new int[structures.size() - 2][sign.size()];
        for (int i = 1; i < structures.size() - 1; i++) {
            if (!structures.get(i).getY().isEmpty()) {
                for (int j = 0; j < structures.get(i).getY().size(); j++) {
                    signals[i - 1][sign.indexOf(structures.get(i).getY().get(j))] = 1;
                }
            }
            if (!structures.get(i).getX().isEmpty()) {
                for (int j = 0; j < structures.get(i).getX().size(); j++) {
                    signals[i - 1][sign.indexOf(structures.get(i).getX().get(j))] = 1;
                }
            }
        }
        System.out.println();
    }
    
    /**
     *
     * @return
     */
    public boolean check() {
        boolean res = false;
        for (int i = 0; i < matrix.length; i++) {
            res = false;
            if (!structures.get(i).isE()) {
                for (int j = 0; j < matrix.length; j++) {
                    if (matrix[i][j] == 1 || matrix[i][j] == 2) {
                        res = true;
                        break;
                    }
                }
                if (res == false) {
                    return false;
                }
            }
            res = false;
            if (!structures.get(i).isB()) {
                for (int j = 0; j < matrix.length; j++) {
                    if (matrix[j][i] == 1 || matrix[j][i] == 2) {
                        res = true;
                        break;
                    }
                }
                if (res == false) {
                    return false;
                }
            }
            
        }
        
        return res;
    }
    
    /**
     *
     * @return
     */
    public int[][] getMatrix() {
        return matrix;
    }
    
    /**
     *
     * @return
     */
    public ArrayList<String> getSign() {
        ArrayList<String> c = new ArrayList<>();
        if (!sign.contains("B")) {
            c.add("B");
        }
        c.addAll(sign);
        if (!sign.contains("E")) {
            c.add("E");
        }
        return c;
    }
    
    /**
     *
     * @return
     */
    public int[][] getSignals() {
        return signals;
    }
    
    /**
     *
     * @return
     */
    public AlgoStructure getHead() {
        return structures.get(0);
    }
    
    private int handleY(int i) throws Exception {
        AlgoStructure s = new AlgoStructure();
        String str = getNumber(i + 1);
        if (!sign.contains("Y" + str)) {
            sign.add("Y" + str);
        }
        s.addY("Y" + str);
        if (st.isEmpty()) {
            throw new Exception("");
        } else {
            Symbols ch = st.pop();
            while (ch == Symbols.arrowDown) {
                s.addIndex(stArrowDown.pop());
                if (st.isEmpty()) {
                    structures.add(s);
                    st.push(Symbols.Y);
                    return str.length();
                } else {
                    ch = st.pop();
                }
            }
            
            switch (ch) {
                case B:
                case X:
                case Y: {
                    structures.get(structures.size() - 1).setTrueLink(s);
                    st.push(Symbols.Y);
                    break;
                }
                default: {
                    throw new Exception("");
                }
            }
        }
        structures.add(s);
        return str.length();
    }
    
    private int handleX(int i) throws Exception {
        AlgoStructure s = new AlgoStructure();
        String str = getNumber(i + 1);
        if (!sign.contains("X" + str)) {
            sign.add("X" + str);
        }
        s.addX("X" + str);
        if (st.isEmpty()) {
            throw new Exception("");
        } else {
            Symbols ch = st.pop();
            while (ch == Symbols.arrowDown) {
                s.addIndex(stArrowDown.pop());
                if (st.isEmpty()) {
                    structures.add(s);
                    st.push(Symbols.X);
                    return str.length();
                } else {
                    ch = st.pop();
                }
            }
            
            switch (ch) {
                case B:
                case X:
                case Y: {
                    structures.get(structures.size() - 1).setTrueLink(s);
                    st.push(Symbols.X);
                    break;
                }
                default: {
                    throw new Exception("");
                }
            }
        }
        structures.add(s);
        return str.length();
    }
    
    private int handleE(int i) throws Exception {
        AlgoStructure s = new AlgoStructure();
        if (st.isEmpty()) {
            throw new Exception("");
        } else {
            Symbols ch = st.pop();
            while (ch == Symbols.arrowDown) {
                s.addIndex(stArrowDown.pop());
                if (st.isEmpty()) {
                    structures.add(s);
                    return 0;
                } else {
                    ch = st.pop();
                }
            }
            
            switch (ch) {
                case B:
                case X:
                case Y: {
                    structures.get(structures.size() - 1).setTrueLink(s);
                    st.push(Symbols.E);
                    break;
                }
                default: {
                    throw new Exception("");
                }
            }
        }
        structures.add(s);
        return 0;
    }
    
    private int handleOpBr(int i) throws Exception {
        AlgoStructure s = new AlgoStructure();
        i++;
        if (algorithm.charAt(i) != 'Y') {
            throw new Exception();
        }
        while (algorithm.charAt(i) != ')') {
            String str = getNumber(i + 1);
            if (!sign.contains("Y" + str)) {
                sign.add("Y" + str);
            }
            s.addY("Y" + str);
            i += 1 + str.length();
        }
        // i--;
        if (st.isEmpty()) {
            throw new Exception("");
        } else {
            Symbols ch = st.pop();
            while (ch == Symbols.arrowDown) {
                s.addIndex(stArrowDown.pop());
                if (st.isEmpty()) {
                    structures.add(s);
                    st.push(Symbols.Y);
                    return i;
                } else {
                    ch = st.pop();
                }
            }
            
            switch (ch) {
                case B:
                case X:
                case Y: {
                    structures.get(structures.size() - 1).setTrueLink(s);
                    st.push(Symbols.Y);
                    break;
                }
                default: {
                    throw new Exception("");
                }
            }
        }
        structures.add(s);
        return i;
    }
    
    private int handleArrowUp(int i) throws Exception {
        String str = getNumber(i + 1);
        AlgoStructure s = structures.get(structures.size() - 1);
        if (s.isOperation()) {
            s.setTrueIndex(Integer.parseInt(str));
        } else {
            s.setFalseIndex(Integer.parseInt(str));
        }
        return str.length();
    }
    
    private int handleArrowDown(int i) throws Exception {
        String str = getNumber(i + 1);
        st.push(Symbols.arrowDown);
        stArrowDown.push(Integer.parseInt(str));
        return str.length();
    }
    
    private String getNumber(int i) {
        String buf = "";
        
        for (int j = i; j < algorithm.length(); j++) {
            if ("0123456789".indexOf(algorithm.charAt(j)) != -1) {
                buf = buf + algorithm.charAt(j);
            } else {
                return buf;
            }
        }
        return buf;
    }
}
