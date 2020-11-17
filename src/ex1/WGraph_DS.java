package ex1;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Objects;

public class WGraph_DS implements weighted_graph, Serializable {
    private int mc;
    private int size_e;
    private HashMap<Integer, node_info> graph;

    public WGraph_DS() {
        mc = 0;
        size_e = 0;
        graph = new HashMap<Integer, node_info>();
    }

    //copy constrctor
    public WGraph_DS(weighted_graph other) {
        this.mc = other.getMC();
        this.size_e = other.edgeSize();
        HashMap<Integer, node_info> t = new HashMap<Integer, node_info>();
        for (node_info g : other.getV()) {
            t.put(g.getKey(), g);
            g.setTag(Double.POSITIVE_INFINITY);
        }
        this.graph = t;
    }
//
    @Override
    public boolean equals(Object o) {
        boolean flag1=((weighted_graph)o).edgeSize()==this.size_e&&((weighted_graph)o).nodeSize()==this.graph.size();
//        if(flag1==false){
//            return false;
//        }
//        //משקל
//        //שכנים
//        //מפתחות
//        int count=0;
//        Iterator<node_info> it1= this.graph.values().iterator();
//        Iterator<node_info> it2=((weighted_graph)o).getV().iterator();
//        while(it1.hasNext()&&it2.hasNext()){
//            node_info n1=it1.next();
//            node_info n2=it2.next();
//            if(n1.getKey()!=n2.getKey()&&!(n1.getInfo().equals(n2.getInfo()))){
//                return false;
//            }
//            Iterator<node_info> it3=((NodeInfo) n1).getN().values().iterator();
//            Iterator<node_info> it4=((NodeInfo) n2).getN().values().iterator();
//            while (it3.hasNext()&&it4.hasNext()){
//                node_info ni1=it3.next();
//                node_info ni2=it4.next();
//                if(!hasEdge(ni1.getKey(),ni2.getKey())){
//                    return false;
//                }
//            }

  //      }

        return flag1;
    }



        @Override
    public node_info getNode(int key) {

        return this.graph.get(key);
    }

    @Override
    public boolean hasEdge(int node1, int node2) {
        if (!graph.containsKey(node1) || !graph.containsKey(node2)) return false;
        return ((NodeInfo) this.graph.get(node1)).getN().containsKey(node2);
    }

    @Override
    public double getEdge(int node1, int node2) {
        if (!hasEdge(node1, node2)) return -1;
        return ((NodeInfo) this.graph.get(node1)).getw().get(node2);
    }

    @Override
    public void addNode(int key) {
        if (!graph.containsKey(key)) {
            this.graph.put(key, new NodeInfo(key));
            mc++;
        }


    }

    @Override
    public void connect(int node1, int node2, double w) {
        if (graph.get(node1) == null || graph.get(node2) == null || w <= 0 || node1 == node2) return;

        ((NodeInfo) this.graph.get(node1)).getw().put(node2, w);
        ((NodeInfo) this.graph.get(node2)).getw().put(node1, w);
        if (hasEdge(node1, node2)) return;
        ((NodeInfo) this.graph.get(node1)).getN().put(node2, getNode(node2));
        ((NodeInfo) this.graph.get(node2)).getN().put(node1, getNode(node1));
        size_e++;
        mc++;
    }

    @Override
    public Collection<node_info> getV() {
        return this.graph.values();
    }

    @Override
    public Collection<node_info> getV(int node_id) {
        return ((NodeInfo) this.graph.get(node_id)).getN().values();
    }

    @Override
    public node_info removeNode(int key) {
        if (graph.containsKey(key)) {
            node_info t = getNode(key);
            Iterator<node_info> it = getV(key).iterator();
            while (it.hasNext()) {
                node_info temp = it.next();
                ((NodeInfo) temp).getN().remove(key);
                ((NodeInfo) temp).getw().remove(key);
                size_e--;
                mc++;
            }
            graph.remove(key);
            return t;
        } else {

            return null;
        }


    }

    @Override
    public void removeEdge(int node1, int node2) {
        // if(this.graph.get(node1).getw().get(node2)==0);

        ((NodeInfo) graph.get(node1)).getN().remove(node2);
        ((NodeInfo) graph.get(node1)).getw().remove(node2);
        ((NodeInfo) graph.get(node2)).getN().remove(node1);
        ((NodeInfo) graph.get(node2)).getw().remove(node1);
        size_e--;
        mc++;
    }

    @Override
    public int nodeSize() {

        return graph.size();
    }

    @Override
    public int edgeSize() {
        return size_e;
    }
    @Override
    public int getMC() {
        return mc;
    }

    public class NodeInfo implements node_info, Serializable {
        private String info;
        private int key;
        private double tag;
        private HashMap<Integer, node_info> ni;
        private HashMap<Integer, Double> w;

        public NodeInfo(int key) {
            info = "";
            this.key = key;
            tag = Double.POSITIVE_INFINITY;
            ni = new HashMap<Integer, node_info>();
            w = new HashMap<Integer, Double>();
        }

        public NodeInfo(node_info other) {
            this.key = other.getKey();
            this.info = other.getInfo();
            this.tag = other.getTag();
            HashMap<Integer, node_info> t = new HashMap<Integer, node_info>();
            HashMap<Integer, Double> t1 = new HashMap<Integer, Double>();
            for (node_info g : ((NodeInfo) other).getN().values()) {
                t.put(g.getKey(), g);
                t1.put(g.getKey(), ((NodeInfo) other).getw().values().iterator().next());
            }
            this.ni = t;
            this.w = t1;
        }



        @Override
        public int getKey() {
            return key;
        }

        @Override
        public String getInfo() {
            return info;
        }

        @Override
        public void setInfo(String s) {
            this.info = s;
        }

        @Override
        public double getTag() {
            return tag;
        }

        @Override
        public void setTag(double t) {
            tag = t;
        }

        public HashMap<Integer, node_info> getN() {
            return this.ni;
        }

        public HashMap<Integer, Double> getw() {
            return this.w;
        }
    }

}
