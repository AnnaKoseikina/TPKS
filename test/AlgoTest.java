/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import TPKS.Algorithm;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Anna
 */
public class AlgoTest {

    public AlgoTest() {
    }

    @Test
    public void hello() throws Exception {
        Algorithm alg = new Algorithm();
        alg.parseAlgorithm("BY2X1↑1Y1Y3↑3↓1X2↑2Y4Y1↑4↓2Y3Y6↓4↓3Y5E");
        assertEquals("B[Y2][X1]T([Y1][Y3][Y5]E) F([X2]T([Y4][Y1][Y5]E) F([Y3][Y6][Y5]E))", alg.getHead().toString());
        alg.parseAlgorithm("B(Y1Y2)↑1↓1X1↑2Y3(Y3Y2)↓2Y6E");
        int[][] matr = {{0, 1, 0, 0, 0, 0, 0}, {0, 0, 1, 0, 0, 0, 0}, {0, 0, 0, 1, 0, 2, 0}, {0, 0, 0, 0, 1, 0, 0}, {0, 0, 0, 0, 0, 1, 0}, {0, 0, 0, 0, 0, 0, 1}, {0, 0, 0, 0, 0, 0, 0}};
        assertEquals(matr, alg.getMatrix());
        int[][] signals = {
            {1, 1, 0, 0, 0},
            {0, 0, 1, 0, 0},
            {0, 0, 0, 1, 0},
            {0, 1, 0, 1, 0},
            {0, 0, 0, 0, 1}};
        assertEquals(signals, alg.getSignals());
        
    }
}
