package crv;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

// ����� - ��������� ����� ����������
public class Graph {
    public class WayPoint {
        public String name;
        public String ID;
        public List<WayPoint> edges=new ArrayList<WayPoint>();
        public WayPoint(String name, String ID) {
            this.name=name;
            this.ID=ID;
            Graph.registerWayPoint(this);
        }
        public void connect(WayPoint otherWayPoint) {
            if (this == otherWayPoint) return;
            if(!edges.contains(otherWayPoint)) edges.add(otherWayPoint);
        }
    }

    private final Map<String,WayPoint> WayPoints = new HashMap<>();

    public WayPoint getWayPointByID(String id) {
        return WayPoints.get(id);
    }

    public void connectWayPoints(String id1, String id2) {
        WayPoint wp1=WayPoints.get(id1);
        WayPoint wp2=WayPoints.get(id2);
        wp1.connect(wp2);
        wp2.connect(wp1);
    }

    // ������������� �� ����� �� �����, �������� �����
    private void readMap() {
        try {
            throw new Exception("not implemented");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // ����������� Singleton
    private static Graph instance = new Graph();
    public static Graph getInstance()  {return instance;};

    // ���������� ��������� ��� ����������� ������������� ����������
    public static void registerWayPoint(WayPoint wp) {instance.WayPoints.put(wp.ID,wp);};

    // ��� ������������� � ���������� �������������� ��� ���������� ����� ����������� ������
    public void newWaypoint(String name) {
        new WayPoint(name,"id"+name);
    }


    public WayPoint getWayPointByName(String message) {
        //���� � ������� ���������� �������� � ������� ������
        // � ��������� ����� ���� ��������, �������, ��������.
        // ����� ������ ���� ���������, ����� ����� ������� �������� ������ ������ �����.
        // ���� ������, �������� �� ������� - ���� � ��������� ����������� ����� ������������, �� ���������� ���.
        // �� ����� ��������, ��� ������� ����� ������ ���������.
        return null;
    }

    // � ���������� ���� �� ������� /nav2 ���������� 2 ����� ����� �������� ���� ��������� ����
    // ���������� ������ ���� "����� 1 (�� �����)" -> "����� 2" -> .... -> "����� N (��� ����)"
    // ������� ���������
    //    from: ID �������� �����
    //	  to:	ID ������� �����
    // ����������� �������� ID ����� ����� ��������� �� ���������

    public String getWay(WayPoint from, WayPoint to) {
        //���������� �������� ������ � ������
        return "�� ��";
    }


    private Graph() {
        //readMap();

        //������� ���������� ����� �����
        new WayPoint("501","id501");
        new WayPoint("502","id502");
        new WayPoint("503","id503");
        connectWayPoints("id501","id502");
        connectWayPoints("id502","id503");
    }
}



