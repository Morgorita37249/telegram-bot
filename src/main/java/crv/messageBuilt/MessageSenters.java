package crv.messageBuilt;

import crv.bot_Core.Bot;
import crv.dataB.DataBase;

public interface MessageSenters {

    public static DataBase base = DataBase.GetInstance();
    public static Bot telegramBot = Bot.GetInstance();
    void send_Message(Long ChatID, String message);
    //.ReplyKeyboardMarkup get_Keyboard();

    public void execute(Long ChatID, String message);
}
