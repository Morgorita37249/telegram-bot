package crv.messageBuilt.commands;

import crv.messageBuilt.Graph;
import crv.messageBuilt.ImageMaker;
import crv.messageBuilt.MessageSenters;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;
import java.util.ArrayList;

public class StoreLastWayPoint implements MessageSenters {

    @Override
    public void execute(Long ChatID, String message) {
        // К нам пришло сообщение, где мы ждём конечную точку
        // мы его сейчас должны найти в графе.
        // Если не нашли - ругаемся и ждём снова.
        Graph.WayPoint wp=Graph.getInstance().getWayPointByName(message);
        if(wp==null) {
            send_Message(ChatID, "I dont know such place, repeat plz");
        } else {
            base.setTag(ChatID, "LastPoint", wp.ID); // TODO: переписать класс database, чтобы хранить не только string, но и wayPoint. А в пределе - любой объект.

            base.setTag(ChatID, "State", "new");
            Graph.WayPoint first = Graph.getInstance().getWayPointByID(base.getTag(ChatID, "FirstPoint"));
            try {
                ArrayList<Graph.WayPoint> Path = Graph.getInstance().getWay(first, wp);
                send_Message(ChatID, Graph.getInstance().get_Names(Path));
                ImageMaker iMaker = new ImageMaker();
                File outputImage=iMaker.makeMap(Graph.getInstance().toStringList(Path), ChatID);
                send_Image(ChatID, outputImage);
            } catch(Exception e) {
                send_Message(ChatID,e.getMessage());
            }
        }
    }

    @Override
    public void send_Message(Long ChatID, String message) {
        SendMessage sendText = new SendMessage();
        sendText.setText(message);
        sendText.setChatId(ChatID);
        try {
            telegramBot.execute(sendText);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    public void send_Image(Long ChatID, File output){
        SendPhoto sendImage = new SendPhoto();
        sendImage.setChatId(ChatID);

        sendImage.setPhoto(new InputFile(output));
        try {
            telegramBot.execute(sendImage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}

