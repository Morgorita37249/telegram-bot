package crv.messageBuilt;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


public class ImageMaker {
    private BufferedImage image;
    private List<Point> points;
    private static final ObjectMapper mapper = new ObjectMapper();

    private final Map<String,Point> pointsMap = new HashMap<>();
    private static final String filePath = "src/main/resources/CoordsMap.json";
    private static final String imagePath = "src/main/resources/карта1.jpg";
    public File makeMap(List<String> names,Long UserID) {
        readImage(imagePath);
        readPoints(names);
        drawArrows(points);

        File outputImage = new File("output.png");
        try {
            ImageIO.write(image, "png", outputImage);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return outputImage;
    }

    public ImageMaker(){}
    private void readPoints(List<String> names) {

        Object obj = null;
        try {
            obj = mapper.readValue(new FileReader("data.json"), Object.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

// Check the type
        JSONArray namesArray;
        if (obj instanceof JSONArray) {
            namesArray = (JSONArray) obj;
        } else {
            throw new RuntimeException("The JSON file does not contain a JSONArray");
        }

        // Get the "names" array from the object

        // Iterate through the array
        for (Object name : namesArray) {
            // Get the name, x, and y values from the object
            String nameText = (String) ((JSONObject) name).get("name");
            int xValue = Integer.parseInt((String) ((JSONObject) name).get("x"));
            int yValue = Integer.parseInt((String) ((JSONObject) name).get("y"));
            pointsMap.put(nameText,new Point(xValue,yValue));
        }
        String lastpoint="";
        for (String name: names) {
            if (pointsMap.containsKey(name)) {
                //огромный костыль который написать было быстрее чем чтобы класс графа давал индексы и исправно писал путь
                if(Objects.equals(name, "stairs near woman toilet")){
                    if(lastpoint.substring(0,1)=="5"){
                        points.add(pointsMap.get("stairs near woman toilet5"));
                        points.add(pointsMap.get("stairs near woman toilet6"));
                        continue;
                    }
                    points.add(pointsMap.get("stairs near woman toilet6"));
                    points.add(pointsMap.get("stairs near woman toilet5"));
                    continue;
                }

                if(Objects.equals(name, "stairs in the middle of the floor")){
                    if(lastpoint.substring(0,1)=="5"){
                        points.add(pointsMap.get("stairs in the middle5"));
                        points.add(pointsMap.get("stairs in the middle6"));
                        continue;
                    }
                    points.add(pointsMap.get("stairs in the middle6"));
                    points.add(pointsMap.get("stairs in the middle5"));
                    continue;
                }
                if(Objects.equals(name, "stairs near lift")){
                    if(lastpoint.substring(0,1)=="5"){
                        points.add(pointsMap.get("stairs near lift5"));
                        points.add(pointsMap.get("stairs near lift6"));
                        continue;
                    }
                    points.add(pointsMap.get("stairs near lift6"));
                    points.add(pointsMap.get("stairs near lift5"));
                    continue;
                }
                if(Objects.equals(name, "Lift on sixth floor")){

                    points.add(pointsMap.get("Lift on sixth floor"));
                    points.add(pointsMap.get("Lift"));
                    continue;
                }
                if(Objects.equals(name, "Lift")){

                    points.add(pointsMap.get("Lift"));
                    points.add(pointsMap.get("Lift on sixth floor"));
                    continue;
                }
                points.add(pointsMap.get(name));
            }
        }
    }
    private void readImage(String path) {
        FileInputStream in = null;
        try {
            in = new FileInputStream(path);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        // Создаем объект ImageIO


        // Считываем изображение в BufferedImage
        try {
            image = ImageIO.read(in);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private void drawArrows(List<Point> points) {
        Graphics2D g2d = image.createGraphics();
        g2d.setColor(Color.RED);
        g2d.setStroke(new BasicStroke(3));

        for (int i = 0; i < points.size() - 1; i++) {
            Point start = points.get(i);
            Point end = points.get(i + 1);
            drawArrow(g2d, start.x, start.y, end.x, end.y);
        }

        g2d.dispose();
    }

    private void drawArrow(Graphics2D g2d, int x1, int y1, int x2, int y2) {
        int ARR_SIZE = 6;

        double dx = x2 - x1, dy = y2 - y1;
        double angle = Math.atan2(dy, dx);
        int len = (int) Math.sqrt(dx*dx + dy*dy);
        AffineTransform at = AffineTransform.getTranslateInstance(x1, y1);
        at.concatenate(AffineTransform.getRotateInstance(angle));
        g2d.transform(at);

        // Draw horizontal arrow starting in (0, 0)
        g2d.drawLine(0, 0, len, 0);
        g2d.fillPolygon(new int[] {len, len-ARR_SIZE, len-ARR_SIZE, len},
                new int[] {0, -ARR_SIZE, ARR_SIZE, 0}, 4);
    }

    private void safe_Image(String filename,String name){
        File output = new File(filename);
        try {
            ImageIO.write(image, name, output);
        } catch (IOException e) {
            System.out.println("Ошибка при создании изображения: " + e.getMessage());
        }

    }

}
