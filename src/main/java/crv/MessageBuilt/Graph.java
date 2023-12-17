package crv.MessageBuilt;

import java.util.*;

// Класс - хранилище карты ориентиров
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

    // инициализация из файла на диске, написать позже
    private void readMap() {
        try {
            throw new Exception("not implemented");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // конструктор Singleton
    private static Graph instance = new Graph();
    public static Graph getInstance()  {return instance;};

    // Упрощающая процедура для регистрации новосозданных ориентиров
    public static void registerWayPoint(WayPoint wp) {instance.WayPoints.put(wp.ID,wp);};

    // для использования в интерфейсе администратора для заполнения графа посредством команд
    public void newWaypoint(String name) {
        new WayPoint(name,"id"+name);
    }


    public WayPoint getWayPointByName(String message) {
        //Ищем в массиве ориентиров ориентир с похожим именем
        // В сообщении может быть опечатка, пробелы, регистры.
        // Здесь должна быть обработка, чтобы перед поиском оставить только нужный кусок.
        // Либо искать, пробегая по массиву - если в сообщении встречается текст наименования, то возвращать его.
        // НО тогда возможно, что найдётся более одного ориентира.
        return null;
    }
    // Возвращаем строку вида "Точка 1 (вы здесь)" -> "Точка 2" -> .... -> "Точка N (вам сюда)"
    //    from: ID исходной точки
    //	  to:	ID целевой точки

    public String getWay(WayPoint from, WayPoint to) {
	//Используем алгоритм поиска в ширину
	LinkedList<WayPoint> queue = new LinkedList<>();
	HashMap<WayPoint, WayPoint> parentMap = new HashMap<>();

	queue.add(from);
	parentMap.put(from, null);

	while (!queue.isEmpty()) {
		WayPoint current = queue.poll();

		if (current == to) {
			return reconstructPath(parentMap, from, to);
		}

		for (WayPoint neighbor : current.edges) {
			if (!parentMap.containsKey(neighbor)) {
				queue.add(neighbor);
				parentMap.put(neighbor, current);
			}
		}
	}

	return "Путь не найден";
    }

    public String reconstructPath(Map<WayPoint, WayPoint> parentMap, WayPoint start, WayPoint end) {
	LinkedList<WayPoint> path = new LinkedList<>();
	WayPoint current = end;

	while (current != null) {
		path.addFirst(current);
		current = parentMap.get(current);
	}

	StringBuilder result = new StringBuilder("Путь: ");
	for (WayPoint point : path) {
		result.append(point.name).append(" -> ");
	}
	result.delete(result.length() - 4, result.length());

	return result.toString();
    }


    private Graph() {
        //readMap();

        //Дубовое заполнение графа кодом
        new WayPoint("501","id501");
        new WayPoint("502","id502");
        new WayPoint("503","id503");
        connectWayPoints("id501","id502");
        connectWayPoints("id502","id503");
    }
}


