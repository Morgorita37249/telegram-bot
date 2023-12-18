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


    private static Graph instance = new Graph();

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

    public ArrayList<WayPoint> getWay(WayPoint from, WayPoint to) throws IllegalArgumentException {
        if (from == null || to == null) {
            throw new IllegalArgumentException("Ошибка: Вершина не существует.");
        }

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

                    if (neighbor.ID.equals(to.ID)) {
                        return Way;
                    }
                }
            }
        }
        throw new IllegalArgumentException("No way between " + from.ID + " и " + to.ID);
    }

    public List<String> get_Names(ArrayList<WayPoint> wayPoints) {
        List<String> names = new ArrayList<>();
        for (WayPoint wayPoint : wayPoints) {
            names.add(wayPoint.name);
        }
        return names;
    }

    public String get_StringWay(ArrayList<WayPoint> wayPoints){
        String way = "";
        for (WayPoint wayPoint : wayPoints){
            way+=wayPoint.name+"->";
        }
        return way;
    }

    private Graph() {

        Map<String, String> WayPoints = JSONData.readJsonData();

        for (Map.Entry<String, String> currentEntry : WayPoints.entrySet()) {
            String currentKey = currentEntry.getKey();
            String currentValue = currentEntry.getValue();
            new WayPoint(currentValue, currentKey);


            for (Map.Entry<String, String> entry : WayPoints.entrySet()) {
                String nextKey = entry.getKey();
                String nextValue = entry.getValue();
                new WayPoint(nextValue, nextKey);

                if (currentKey.contains("id5") && nextKey.contains("id5") ||
                        currentKey.contains("id6") && nextKey.contains("id6") ||
                        currentKey.equals("id5L1") && nextKey.equals("id6L1") ||
                        currentKey.equals("id5L2") && nextKey.equals("id6L2") ||
                        currentKey.equals("id5L3") && nextKey.equals("id6L3") ||
                        currentKey.equals("id5L") && nextKey.equals("id6L")) {

                    connectWayPoints(currentKey, nextKey);
                }
            }
        }
    }
}



