package crv.messageBuilt.commands;

import crv.messageBuilt.Graph;
import crv.messageBuilt.MessageSenters;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class StoreFirstWayPoint implements MessageSenters {

    @Override
    public void execute(Long ChatID, String message) {
        // К нам пришло сообщение, где мы ждём первый ориентир
        // мы его сейчас должны найти в графе.
        // Если не нашли - ругаемся и ждём снова.
        Graph.WayPoint wp=Graph.getInstance().getWayPointByName(message);
        if(wp==null) {
            send_Message(ChatID,"Did not find such a place, repeat please");
        } else {
            base.setTag(ChatID,"FirstPoint",wp.ID); // TODO: переписать класс database, чтобы хранить не только string, но и wayPoint. А в пределе - любой объект.
            base.setTag(ChatID,"State","Waiting for last waypoint");
            send_Message(ChatID, "Tell me where you wish to get");
        }

    }

    @Override
    public void send_Message(Long ChatID, String message) {
        SendMessage sendText = new SendMessage();
        sendText.setChatId(ChatID);
        sendText.setText(message);
        try {
            telegramBot.execute(sendText);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}
