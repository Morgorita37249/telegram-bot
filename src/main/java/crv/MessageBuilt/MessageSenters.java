package crv.MessageBuilt;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

public interface MessageSenters {

    void send_Message();
    ReplyKeyboardMarkup get_Keyboard();

    public String execute(Long ChatID, String message);
}
