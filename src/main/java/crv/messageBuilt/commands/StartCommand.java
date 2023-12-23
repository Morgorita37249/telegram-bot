package crv.messageBuilt.commands;

import crv.dataB.DataBase;
import crv.messageBuilt.MessageSenters;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class StartCommand implements MessageSenters {


    // По команде nav1 - запрашиваем номер аудитории, где находимся, сохраняем в состоянии "firstPoint"
    @Override
    public void execute(Long ChatID, String message) {
        DataBase.GetInstance().newUser(ChatID);
        // return "Сообщите, где находитесь (номер аудитории)";
        //send_Message(ChatID,"Сообщите, где находитесь (номер аудитории)");
        send_Message(ChatID,"Tell me where you are (room number)");
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
