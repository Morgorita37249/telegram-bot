package crv.MessageBuilt.Commands;

import crv.MessageBuilt.MessageSenters;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


public class StartCommand implements MessageSenters {
        String text;
        @Override
        public void execute(Long ChatID, String message) {
            base.setTag(ChatID,"State","new");
            text="Hey! lest go!!!";
            send_Message(ChatID,text);
        }

        @Override
        public void send_Message(Long ChatID, String text) {
            SendMessage message = new SendMessage();
            message.setChatId(ChatID);
            message.setText(text);
            try {
                telegramBot.execute(message);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        }

    }

