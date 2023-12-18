package crv.Bot_Core;

import crv.DataB.SQLiteJDBCDriverConnection;
import crv.MessageBuilt.MessageResponce;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

public class Bot extends TelegramLongPollingBot {
    public Secret sec;
    private String name;
    private static Bot instance;
    public static Bot GetInstance(String token, String name){
        if (instance == null) {
            instance = new Bot(token, name);
        }
        return instance;
    }
    public static Bot GetInstance(){
        //should be never used if there is a chance what instance is null
        return instance;
    }
    @Override
    public void onUpdateReceived(Update update) {
        MessageResponce.ProcessUpdate(update);
    }


    private Bot(String token, String name) {
        super(token);
        this.name = name;
        SQLiteJDBCDriverConnection sqlbase = new SQLiteJDBCDriverConnection();
        sqlbase.createNewTable();
    }
    @Override
    public String getBotUsername() {
        return name;
    }


}