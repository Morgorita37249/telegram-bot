package crv;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BotMM extends TelegramLongPollingBot {
    private final String name;
    Map<String, List<String>> UsersData = new HashMap<>(); //have all chat tokens and their state + needed prev data
    public BotMM(String token, String name) {
        super(token);
        this.name = name;
    }

    public static void main(String[] args) throws IOException, TelegramApiException {
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);

        var token = ""; //need to change for file but for now will be empty and need to fill handy
        var name = ""; //same as ^

        telegramBotsApi.registerBot(new BotMM(token, name));
    }
    public void onUpdateReceived(Update update) {
        var message = update.getMessage();
        if (message == null)
            return;

        var chatId = update.getMessage().getChatId();
        if (UsersData.containsKey(chatId)){
            if(message.hasText()) {
                String mess = message.getText();
                var temp = UsersData.get(chatId);
                temp.add(mess);
                //UsersData.put(chatId,temp);
            }
        }
        SendMessage response = new SendMessage();
        response.setChatId(chatId);
        response.setText("I can process only text messages");
        try {
            execute(response);
        } catch (TelegramApiException e) {
            e.printStackTrace();
            //throw new RuntimeException(e);
        }
    }
    public String getBotUsername() {
        return name;
    }
}
