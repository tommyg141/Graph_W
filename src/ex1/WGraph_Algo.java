package ex1;

import java.io.*;
import java.util.*;
import ex1.node_info;
import java.io.Serializable;
public class WGraph_Algo implements weighted_graph_algorithms,Serializable  {
    weighted_graph graph;

    public WGraph_Algo() {
        graph = new WGraph_DS();
    }

    @Override
    public void init(weighted_graph g) {
        this.graph = g;
    }

    @Override
    public weighted_graph getGraph() {
        return graph;
    }

    @Override
    public weighted_graph copy() {
        return new WGraph_DS(graph);
    }

    @Override
    public boolean isConnected() {
        if (graph.nodeSize() == 0 || graph.nodeSize() == 1) return true;
        weighted_graph afterBfs = bfs(graph.getV().iterator().next().getKey());
        for (node_info g : afterBfs.getV()) {
            if (g.getTag() == Double.POSITIVE_INFINITY) return false;
        }
        return true;
    }

    @Override
    public double shortestPathDist(int src, int dest) {
        dijkstra(this.graph.getNode(src));
        // LinkedHashMap<node_info,node_info> parents=dijkstra(this.graph.getNode(src));
        //  List<node_info> ans=new ArrayList<node_info>();
        return this.graph.getNode(dest).getTag();
//        node_info node= this.graph.getNode(dest);
//        while(node!=null){
//            ans.add(node);
//            node = parents.get(node);
//        }
//        return ans.size()-1;
    }

    @Override
    public List<node_info> shortestPath(int src, int dest) {
        LinkedHashMap<node_info, node_info> parents = dijkstra(this.graph.getNode(src));
        List<node_info> ans = new ArrayList<node_info>();

        node_info node = this.graph.getNode(dest);
        while (node != null) {
            ans.add(node);
            node = parents.get(node);
        }
        Collections.reverse(ans);
        return ans;
    }

    private LinkedHashMap<node_info, node_info> dijkstra(node_info src) {
        inittag();
        PriorityQueue<node_info> q = new PriorityQueue<node_info>(new Comparator<node_info>() {
            @Override
            public int compare(node_info o1, node_info o2) {
                return -Double.compare(o1.getTag(), o1.getTag());
            }
        });
        LinkedHashMap<node_info, node_info> parents = new LinkedHashMap<node_info, node_info>();
        src.setTag(0.0);
        q.add(src);
        while (!q.isEmpty()) {
            node_info vertex_u = q.poll();
            Iterator<node_info> it = ((WGraph_DS.NodeInfo)vertex_u).getN().values().iterator();
            while (it.hasNext()) {
                node_info vertex_v = it.next();
                // if(vertex_v.getInfo().equals("")){
                double dis = ((WGraph_DS.NodeInfo) vertex_u).getw().get(vertex_v.getKey()) + vertex_u.getTag();
                if (dis < vertex_v.getTag()) {
                    vertex_v.setTag(dis);
                    parents.put(vertex_v, vertex_u);
                    q.remove(vertex_v);
                    q.add(vertex_v);
                }
            }

        }
        //  vertex_u.setInfo("1");
        //}

        return parents;
    }

    private void inittag() {
        for (node_info i : graph.getV()) {
            i.setTag(Double.POSITIVE_INFINITY);
        }
    }

    @Override
    public boolean save(String file) {
        try {
            FileOutputStream name_file = new FileOutputStream(file);
            ObjectOutputStream out = new ObjectOutputStream(name_file);

            out.writeObject(this.graph);

            out.close();
            name_file.close();

            System.out.println("Object has been serialized");
            return true;
        } catch (IOException ex) {
            System.out.println("IOException is caught");
            return false;
        }

    }

    @Override
    public boolean load(String file) {
        try {
            FileInputStream name_file = new FileInputStream(file);
            ObjectInputStream in = new ObjectInputStream(name_file);
            graph = (weighted_graph) in.readObject();
            in.close();
            name_file.close();
            System.out.println("Object has been deserialized");
            return true;
        } catch (IOException ex) {
            System.out.println("IOException is caught");
            return false;
        } catch (ClassNotFoundException ex) {
            System.out.println("ClassNotFoundException is caught");
            return false;
        }

    }

    //bfs getting copy grph
    //making a queue then set the tag of the src as 0
    //then go i while until the queue is empty
    // sets tags until there no any -1 tags
    private weighted_graph bfs(int src) {
        weighted_graph copyGraph = copy();
        node_info srcNode = copyGraph.getNode(src);
        LinkedList<node_info> queue = new LinkedList<>();
        srcNode.setTag(0);
        queue.push(srcNode);
        while (!queue.isEmpty()) {
            node_info temp = queue.pop();
            for (node_info ni :  ((WGraph_DS.NodeInfo)temp).getN().values()) {
                if (ni.getTag() == Double.POSITIVE_INFINITY) {
                    ni.setTag(temp.getTag() + 1);
                    queue.push(ni);
                }
            }
        }
        return copyGraph;
    }
}
