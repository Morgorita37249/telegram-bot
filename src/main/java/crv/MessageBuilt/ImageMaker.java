package crv.MessageBuilt;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import java.io.FileReader;


public class ImageMaker {
    private BufferedImage image;
    private List<Point> points;

    public void makeMap(List<String> names, String names2Points_File, String image_File,Long UserID) {
        readImage(image_File);
        readPoints(names2Points_File,names);
        drawArrows(points);
        safe_Image("C:\\Bot"+UserID+".jpeg","jpg" );

    }

    public ImageMaker(){}
    private void readPoints(String file, List<String> names){

    }

    private void readImage(String path) {
        try {
            image = ImageIO.read(new File(path));
        } catch (IOException e) {
            e.printStackTrace();
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
