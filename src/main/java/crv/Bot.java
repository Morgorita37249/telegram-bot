package crv;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.io.IOException;

public class Bot { //extends TelegramLongPollingBot
   /*
        private String ResMessage="something wrong";
        //private final String name;
        public String getBotUsername() {
            Secret secret = new Secret();
            return secret.name;
        }
        /*
        public Bot(){
            Secret secret = new ecret();
            super(secret.token);
            this.name = secret.name;
        }


        public void BotStart(String[] args) throws IOException, TelegramApiException{
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(new Bot());
        }
        public void onUpdateReceived(Update update) {
            var Objmessage = update.getMessage();
            var message = Objmessage.getText();
            if (message != null) {

                var chatId = Objmessage.getChatId();
                var MessgeResp = new MessageResponce(chatId, message);
                SendMessage response = new SendMessage();
                response.setText(MessgeResp.getMessage());
                response.setChatId(chatId);
                try {
                    execute(response);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                    //throw new RuntimeException(e);

                }
            }
        }
*/
}
