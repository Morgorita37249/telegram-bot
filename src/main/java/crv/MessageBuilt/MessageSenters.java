package crv.MessageBuilt;

import crv.Bot_Core.Bot;
import crv.DataB.DataBase;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

public interface MessageSenters {

    public static DataBase base = DataBase.GetInstance();
    public static Bot telegramBot = Bot.GetInstance();
    void send_Message(Long ChatID, String message);
    //.ReplyKeyboardMarkup get_Keyboard();

    public void execute(Long ChatID, String message);
}
