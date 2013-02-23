/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package TPKS;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Anna
 */
public class Table {

    /**
     * Constructor for reading from file
     *
     * @param f file
     * @throws FileNotFoundException
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public Table(File f) throws FileNotFoundException, IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(f);
        ObjectInputStream oin = new ObjectInputStream(fis);
        colums = (String[]) oin.readObject();
        rows = (String[]) oin.readObject();
        tabl = (char[][]) oin.readObject();
        oin.close();
        fis.close();
    }

    /**
     * Constructor
     *
     * @param map hashmap of vertexes and codes
     * @param graf graph of algorithm
     * @param alg algorithm
     * @throws Exception
     */
    public Table(HashMap<Vertex, Integer> map, Graph graf, String alg) throws Exception {
        String[] code = new String[graf.getVertexes().size()];

        int index = 0;

        for (int i = 0; i < graf.getVertexes().size(); i++) {
            code[i] = graf.getVertexes().get(i).getCode();
        }
        int len = code[0].length();
        String[][] grafTable = new String[graf.getEdges().size()][3];
        for (int i = 0; i < graf.getEdges().size(); i++) {
            grafTable[i][0] = "" + graf.getEdges().get(i).getFrom().getIndex();
            grafTable[i][1] = "" + graf.getEdges().get(i).getTo().getIndex();
            grafTable[i][2] = graf.getEdges().get(i).getPath();
        }
        Algorithm a = new Algorithm();
        a.parseAlgorithm(alg);
        ArrayList<String> captions = a.getSign();
        Collections.sort(captions);
        captions.remove(0);
        captions.remove(0);
        tabl = new char[grafTable.length][(len * 4) + captions.size()];
        for (int i = 0; i < tabl.length; i++) {
            char[] mas = code[Integer.parseInt(grafTable[i][0]) - 1].toCharArray();

            System.arraycopy(mas, 0, tabl[i], 0, mas.length);
            index = mas.length;
            mas = code[Integer.parseInt(grafTable[i][1]) - 1].toCharArray();
            System.arraycopy(mas, 0, tabl[i], index, mas.length);
            index += mas.length;
            for (int j = 0; j < captions.size(); j++) {
                if (captions.get(j).contains("X")) {
                    int t = grafTable[i][2].indexOf(captions.get(j));
                    if (t != -1) {
                        if (t != 0) {
                            if (grafTable[i][2].charAt(t - 1) == '[' && t != 1) {
                                t--;
                            }
                            if (grafTable[i][2].charAt(t - 1) == 'T') {
                                tabl[i][j + index] = '0';
                            } else {
                                tabl[i][j + index] = '1';
                            }
                        } else {
                            tabl[i][j + index] = '1';
                        }
                    } else {
                        tabl[i][j + index] = '*';
                    }
                } else {
                    if (graf.getVertexes().get(Integer.parseInt(grafTable[i][0]) - 1).getValue().indexOf(captions.get(j)) != -1) {
                        tabl[i][j + index] = '1';
                    } else {
                        tabl[i][j + index] = '0';
                    }
                }
            }
            index += captions.size();
            for (int j = 0; j < len; j++) {
                tabl[i][j + index] = getJ(tabl[i][j], tabl[i][j + len]);
                index++;
                tabl[i][j + index] = getK(tabl[i][j], tabl[i][j + len]);
            }
        }
        colums = new String[tabl[0].length + 1];
        colums[0] = "";
        int cof = len;
        for (int i = 1; i <= len; i++) {
            colums[i] = "Q" + cof;
            colums[i + len] = "Q" + cof;
            cof--;
        }
        index = len * 2 + 1;
        for (int i = 0; i < captions.size(); i++) {
            colums[index + i] = captions.get(i);
        }
        cof = len;
        index += captions.size();
        rows = new String[tabl.length];

        for (int i = 0; i < len; i++) {
            colums[i + index] = "J" + cof;
            index++;
            colums[i + index] = "K" + cof;
            cof--;
        }
        for (int i = 0; i < tabl.length; i++) {
            rows[i] = "Z" + grafTable[i][0] + " -> Z" + grafTable[i][1];
        }
    }

    /**
     * get J (0 or 1 or *)
     *
     * @param q start state
     * @param qt end state
     * @return function
     */
    private char getJ(char q, char qt) {
        if (q == '0') {
            if (qt == '0') {
                return '0';
            } else {
                return '1';
            }
        } else {
            return '*';
        }
    }

    /**
     * Get K (0 or 1 or *)
     *
     * @param q start state
     * @param qt end state
     * @return function
     */
    private char getK(char q, char qt) {
        if (q == '0') {
            return '*';
        } else {
            if (qt == '0') {
                return '1';
            } else {
                return '0';
            }
        }
    }

    /**
     * Write to file
     *
     * @param f file
     */
    public void write(File f) {
        try {
            FileOutputStream fos = new FileOutputStream(f);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(colums);
            oos.writeObject(rows);
            oos.writeObject(tabl);
            oos.close();
            fos.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /*
     * Getters
     */

    public String[] getColums() {
        return colums;
    }

    public String[] getRows() {
        return rows;
    }

    public char[][] getTabl() {
        return tabl;
    }
    /*
     * Variables
     */
    private String[] colums;
    private String[] rows;
    private char[][] tabl;
}
