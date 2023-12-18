package crv.Bot_Core;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.io.IOException;

public class BotMM {

    public static void main(String[] args) throws IOException, TelegramApiException {
        Graph.getInstance().ReadGraph(); // graph init
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        Secret secret = new Secret();
        var token = secret.token; //need to change for file but for now will be empty and need to fill handy
        var name = secret.token; //same as ^

        telegramBotsApi.registerBot(Bot.GetInstance(token,name));
    }

}
