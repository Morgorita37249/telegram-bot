package crv.MessageBuilt;

import java.util.*;
import java.util.Iterator;


public class Graph {
    public class WayPoint {
        public String name;
        public String ID;
        public List<WayPoint> edges = new ArrayList<WayPoint>();

        public WayPoint(String name, String ID) {
            this.name = name;
            this.ID = ID;
            Graph.registerWayPoint(this);
        }

        public void connect(WayPoint otherWayPoint) {
            if (this == otherWayPoint) return;
            if (!edges.contains(otherWayPoint)) edges.add(otherWayPoint);
        }
    }

    private final Map<String, WayPoint> WayPoints = new HashMap<>();

    public WayPoint getWayPointByID(String id) {
        return WayPoints.get(id);
    }

    public void connectWayPoints(String id1, String id2) {
        WayPoint wp1 = WayPoints.get(id1);
        WayPoint wp2 = WayPoints.get(id2);
        wp1.connect(wp2);
        wp2.connect(wp1);
    }

    //единственный граф
    private static final Graph instance = new Graph();

    public static Graph getInstance() {
        return instance;
    }


    public static void registerWayPoint(WayPoint wp) {
        instance.WayPoints.put(wp.ID, wp);
    }


    public void newWayPoint(String name) {
        WayPoint wayPoint = new WayPoint(name, "id" + name);
    }


    public WayPoint getWayPointByName(String name) {
        if (instance != null) {
            for (WayPoint wayPoint : instance.WayPoints.values()) {
                if (wayPoint.name.equals(name)) {
                    return wayPoint;
                }
            }
        }
        return null;
    }
    //обход графа поиском в ширину, возвращает список вершин от точки до точки
    public ArrayList<WayPoint> getWay(WayPoint from, WayPoint to) throws IllegalArgumentException {
        if (from == null || to == null) {
            throw new IllegalArgumentException("Ошибка: Вершина не существует.");
        }

        HashMap<WayPoint, WayPoint> steps=new HashMap<WayPoint, WayPoint>();
        LinkedList<WayPoint> queue = new LinkedList<>();
        ArrayList<WayPoint> Way = new ArrayList<>();
        Set<String> visited = new HashSet<>();
        queue.add(from);
        visited.add(from.ID);

        while (!queue.isEmpty()) {
            WayPoint current = queue.poll();
            Way.add(current);

            for (WayPoint neighbor : current.edges) {
                if (!visited.contains(neighbor.ID)) {
                    queue.add(neighbor);
                    visited.add(neighbor.ID);
                    steps.put(neighbor,current);

                    if (neighbor.ID.equals(to.ID)) { //по пройденным шагам размотаем путь обратно
                        ArrayList<WayPoint> trace=new ArrayList<WayPoint>();
                        WayPoint p=neighbor;
                        while(p!=null) {
                            trace.add(p);
                            p=steps.get(p);
                        }
                        Collections.reverse(trace);
                        return trace;
                    }
                }
            }
        }
        throw new IllegalArgumentException("No way between " + from.name + " и " + to.name);
    }

    public List<String> toStringList(List<WayPoint> wayPoints) {
        List<String> result=new ArrayList<String>();
        for (WayPoint wayPoint : wayPoints) {
            result.add(wayPoint.name);
        }
        return result;
    }
    // строчка из всех пройденных точек в пути
    public String get_Names(List<WayPoint> wayPoints) {
        StringBuilder namesBuilder = new StringBuilder();
        for (WayPoint wayPoint : wayPoints) {
            namesBuilder.append(wayPoint.name).append(" ");
        }
        return namesBuilder.toString().trim();
    }

    private Graph() {

    }
    public void ReadGraph() {
        try {
            Map<String, String> WayPoints = JSONData.readJsonData();

            String prev_key="";
            String prev_value="";

            for (Map.Entry<String, String> currentEntry : WayPoints.entrySet()) {
                String currentKey = currentEntry.getKey();
                String currentValue = currentEntry.getValue();
                new WayPoint(currentValue, currentKey);

                if(prev_key.equals("")) {
                } else {

                    if (currentKey.contains("id5") && prev_key.contains("id5") ||
                            currentKey.contains("id6") && prev_key.contains("id6")) {

                        connectWayPoints(currentKey, prev_key);
                    }
                }
                prev_key=currentKey;
                prev_value=currentValue;
            }
            connectWayPoints("idL5","idL6");
            connectWayPoints("id5L1","id6L1");
            connectWayPoints("id5L2","id6L2");
            connectWayPoints("id5L3","id6L3");
            connectWayPoints("id517","id540");
            connectWayPoints("id640","id625");

        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }
}





