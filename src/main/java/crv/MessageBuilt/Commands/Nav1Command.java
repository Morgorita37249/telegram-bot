package crv.MessageBuilt.Commands;

import crv.MessageBuilt.MessageSenters;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class Nav1Command implements MessageSenters {


    // По команде nav1 - запрашиваем номер аудитории, где находимся, сохраняем в состоянии "firstPoint"
    @Override
    public void execute(Long ChatID, String message) {
        base.setTag(ChatID,"State","Waiting for first waypoint");

        // return "Сообщите, где находитесь (номер аудитории)";
        send_Message(ChatID,"Tell us where you are (audience number)");
    }
    @Override
    public void send_Message(Long ChatID, String message) {
        SendMessage textMessage = new SendMessage();
        textMessage.setText(message);
        textMessage.setChatId(ChatID);
        try {
            telegramBot.execute(textMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}
