package ex1;

import org.junit.Before;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.fail;

public class Main {
    //    public static void main(String[] args) {
//        weighted_graph g=new WGraph_DS();
//        weighted_graph_algorithms algo = new WGraph_Algo();
//        g.addNode(0);
////        g.addNode(1);
////        g.addNode(2);
////        g.addNode(3);
//        algo.init(g);
//        algo.save("test.txt");
//        algo.load("test.txt");
//    }
    weighted_graph g= new WGraph_DS();

    @Before
    void BEF() {
        g = new WGraph_DS();
    }

    @Test
    void test1() {
        g.addNode(0);
        if (g.getMC() != 1) {
            fail("fail mc counter");
        }
        g.addNode(1);
        if (g.getMC() != 2) {
            fail("fail mc counter");
        }
        g.addNode(2);
        if (g.getMC() != 3) {
            fail("fail mc counter");
        }
        if (g.edgeSize() != 0) {
            fail("fail edge counter");
        }
        g.connect(0, 1, 7);
        if (g.edgeSize() != 1) {
            fail("fail edge counter");
        }
        g.removeEdge(1, 0);
        if (g.edgeSize() != 0) {
            fail("fail edge counter");
        }
    }

    @Test
    void test2() {
        for(int i=0; i<1000000;i++) {
            g.addNode(i);
        }
        for(int i=0; i<1000000;i+=2) {
            g.connect(i,i+1,15);
        }
        if (g.getMC() != 1500000) {
            fail("fail mc counter");
        }
        g.addNode(1);
        if (g.getMC() != 1500000) {
            fail("fail mc counter");
        }
        g.addNode(2);
        if (g.getMC() != 1500000) {
            fail("fail mc counter");
        }
        if (g.edgeSize() != 500000) {
            fail("fail edge counter");
        }
        g.connect(0, 1, 7);
        if (g.edgeSize() != 500000) {
            fail("fail edge counter");
        }
        g.removeEdge(1, 0);
        if (g.edgeSize() != 499999) {
            fail("fail edge counter");
        }
    }

}
