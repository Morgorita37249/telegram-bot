package crv.MessageBuilt;

import crv.DataB.DataBase;
import crv.MessageBuilt.Commands.*;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.HashMap;
import java.util.Map;

import static crv.MessageBuilt.MessageSenters.telegramBot;

public class MessageResponce {












    //private String FinalMessage="Oops,something wrong"; //НЕЛЬЗЯ использовать внешние переменные. Если идёт обработка, то результат нужно вернут!!!
    private static final DataBase base=DataBase.GetInstance();

    // private Map<Integer, String> CabinetDecode= Map.of(0,"501",1,"502",2,"503",3,"504",4,"505",5,"506",6,"507",7,"508",8,"509",9,"510",10,"511",11,"512",12,"513",13,"514",14,"515",15,"516",16,"517",17,"518",18,"519",19,"520",20,"521",21,"522",22,"523",23,"524",24,"525",25,"526",26,"527",27,"528",28,"529",29,"530",30,"531",31,"532",32,"533",33,"534",34,"535",35,"536",36,"537",37,"538",38,"539",39,"540");


    // Как должна проходить обработка сообщения

    // по команде nav2 - запрашиваем номер аудитории, куда надо попасть, сохраняем в состоянии "LastPoint", обращаемся к классу Graph за инструкциями

    public static void send_Message(Long ChatID, String message) {
        SendMessage sendText = new SendMessage();
        sendText.setChatId(ChatID);
        sendText.setText(message);
        try {
            telegramBot.execute(sendText);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    public static void ProcessUpdate (Update update) {
        Message mes;
        long UserID=update.getMessage().getChatId();
        String Message=update.getMessage().getText();
        MessageSenters toExecute=processor.commandList.get(Message);
        if(toExecute==null) //команду не нашли, поэтому в зависимости от состояния отрабатываем сообщение
            toExecute=processor.commandList.get(base.getDialogState(UserID));
        if(toExecute==null) send_Message(UserID,"Command not recognized");
            //return "Команда не распознана";
       toExecute.execute(UserID,Message);

    }

    public static MessageResponce processor=new MessageResponce();
    private final Map<String,MessageSenters> commandList=new HashMap<String,MessageSenters>();
    public MessageResponce() {
        //конструктор процессора команд
        commandList.put("/start",new Nav1Command());
        commandList.put("/nav1",new Nav1Command());
        commandList.put("/status",new StatusCommand());
        commandList.put("Waiting for first waypoint",new StoreFirstWayPoint());
        commandList.put("Waiting for last waypoint",new StoreLastWayPoint());
        // TODO: Добавить команды: Добавить ориентир, Удалить ориентир, Связать ориентиры, Вывести граф целиком.
    }

}
