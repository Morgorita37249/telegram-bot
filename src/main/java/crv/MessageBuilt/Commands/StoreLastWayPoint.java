package crv.MessageBuilt.Commands;

import crv.MessageBuilt.Graph;
import crv.MessageBuilt.ImageMaker;
import crv.MessageBuilt.MessageSenters;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class StoreLastWayPoint implements MessageSenters {

    @Override
    public void execute(Long ChatID, String message) {
        // К нам пришло сообщение, где мы ждём конечную точку
        // мы его сейчас должны найти в графе.
        // Если не нашли - ругаемся и ждём снова.
        Graph.WayPoint wp=Graph.getInstance().getWayPointByName(message);
        if(wp==null) send_Message(ChatID, "Не нашёл указанного места в списке, повторите");
        base.setTag(ChatID,"LastPoint",wp.ID); // TODO: переписать класс database, чтобы хранить не только string, но и wayPoint. А в пределе - любой объект.

        base.setTag(ChatID,"State","new");
        Graph.WayPoint first=Graph.getInstance().getWayPointByID(base.getTag(ChatID,"FirstPoint"));
        ArrayList<Graph.WayPoint> way=Graph.getInstance().getWay(first,wp);
        String text_Path=Graph.getInstance().get_StringWay(way);
        send_Message(ChatID,text_Path);
        ImageMaker iMaker = new ImageMaker();

        iMaker.makeMap(Graph.getInstance().toStringList(way), "names.json","Base_Map.png",ChatID);
        send_Image(ChatID,"C:\\Bot"+ChatID+".jpeg");
    }

    @Override
    public void send_Message(Long ChatID, String message) {
        SendMessage sendText = new SendMessage();
        sendText.setText(message);
        sendText.setChatId(ChatID);
    }

    public void send_Image(Long ChatID, String imagefile){
        SendPhoto sendImage = new SendPhoto();
        sendImage.setChatId(ChatID);

        sendImage.setPhoto(new InputFile(imagefile));
        try {
            telegramBot.execute(sendImage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}
