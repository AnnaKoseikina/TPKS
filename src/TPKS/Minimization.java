/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package TPKS;

import java.util.ArrayList;

/**
 *
 * @author Anna
 */
public class Minimization {

    /**
     * Method for checking if arraylist contains char[]
     *
     * @param value char []
     * @param result arraylist
     * @return true or false
     */
    private static boolean contains(char[] value, ArrayList<char[]> result) {
        for (char[] c : result) {
            if (equals(c, value)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Equals
     *
     * @param c1
     * @param c2
     * @return
     */
    private static boolean equals(char[] c1, char[] c2) {
        for (int i = 0; i < c1.length; i++) {
            if (c1[i] != c2[i]) {
                return false;
            }
        }
        return true;
    }

    /**
     * Method for minimization
     *
     * @param ddnf ddnf form of function
     * @param mini minimization
     * @param glued array, showing if anything was glued
     * @return
     */
    public static boolean minimize(ArrayList<char[]> ddnf, ArrayList<char[]> mini, boolean[] glued) {
        for (int i = 0; i < ddnf.size(); i++) {
            for (int k = i + 1; k < ddnf.size(); k++) {
                int count = 0;
                int index = -1;
                for (int j = 0; j < ddnf.get(i).length; j++) {

                    if (ddnf.get(i)[j] != ddnf.get(k)[j]) {
                        count++;
                        index = j;
                    }
                }
                if (count == 1) {
                    glued[i] = true;
                    glued[k] = true;
                    char[] b = new char[ddnf.get(i).length];
                    System.arraycopy(ddnf.get(i), 0, b, 0, b.length);
                    b[index] = 'X';
                    if (!contains(b, mini)) {
                        mini.add(b);
                    }

                }
            }
            if (!glued[i]) {
                if (!contains(ddnf.get(i), mini)) {
                    mini.add(ddnf.get(i));
                }
            }

        }
        for (int i = 0; i < glued.length; i++) {
            if (glued[i]) {
                return true;
            }
        }
        return false;

    }
}
