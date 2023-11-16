package crv;

import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.HashMap;
import java.util.Map;

public class MessageResponse {
    public interface Command {
        public String execute(Long ChatID, String message);
    }

    public class StartCommand implements Command{
        @Override
        public String execute(Long ChatID, String message) {
            if(!base.UsersData.containsKey(ChatID)){
                base.newUser(ChatID);
            } else base.setTag(ChatID,"State","new");
            return "Hello, let's go!!!!";
        }
    }

    public class Nav1Command implements Command {
        // По команде nav1 - запрашиваем номер аудитории, где находимся, сохраняем в состоянии "firstPoint"
        @Override
        public String execute(Long ChatID, String message) {
            base.setTag(ChatID,"State","Waiting for first waypoint");
            return "Сообщите, где находитесь (номер аудитории)";
        }
    }

    public class StoreFirstWayPoint implements Command {

        @Override
        public String execute(Long ChatID, String message) {
            // к нам пришло сообщение, где мы ждём первый ориентир
            // мы его сейчас должны найти в графе.
            // Если не нашли - ругаемся и ждём снова.
            Graph.WayPoint wp=Graph.getInstance().getWayPointByName(message);
            if(wp==null) return "Не нашёл указанного места в списке, повторите";
            base.setTag(ChatID,"FirstPoint",wp.ID); // TODO: переписать класс database, чтобы хранить не только string, но и wayPoint. А в пределе - любой объект.
            base.setTag(ChatID,"State","Waiting for last point");
            return "Теперь напишите, куда хотите попасть";
        }
    }
    public class StoreLastWayPoint implements Command {

        @Override
        public String execute(Long ChatID, String message) {
            // К нам пришло сообщение, где мы ждём конечную точку
            // мы его сейчас должны найти в графе.
            // Если не нашли - ругаемся и ждём снова.
            Graph.WayPoint wp=Graph.getInstance().getWayPointByName(message);
            if(wp==null) return "Не нашёл указанного места в списке, повторите";
            base.setTag(ChatID,"LastPoint",wp.ID); // TODO: переписать класс database, чтобы хранить не только string, но и wayPoint. А в пределе - любой объект.

            base.setTag(ChatID,"State","new");
            Graph.WayPoint first=Graph.getInstance().getWayPointByID(base.getTag(ChatID,"FirstPoint"));
            return Graph.getInstance().getWay(first,wp);
        }
    }


    //private String FinalMessage="Oops,something wrong"; //НЕЛЬЗЯ использовать внешние переменные. Если идёт обработка, то результат нужно вернут!!!
    private static final DataBase base=DataBase.GetInstance();

    // private Map<Integer, String> CabinetDecode= Map.of(0,"501",1,"502",2,"503",3,"504",4,"505",5,"506",6,"507",7,"508",8,"509",9,"510",10,"511",11,"512",12,"513",13,"514",14,"515",15,"516",16,"517",17,"518",18,"519",19,"520",20,"521",21,"522",22,"523",23,"524",24,"525",25,"526",26,"527",27,"528",28,"529",29,"530",30,"531",31,"532",32,"533",33,"534",34,"535",35,"536",36,"537",37,"538",38,"539",39,"540");


    // Как должна проходить обработка сообщения

    // по команде nav2 - запрашиваем номер аудитории, куда надо попасть, сохраняем в состоянии "LastPoint", обращаемся к классу Graph за инструкциями

    public static String ProcessUpdate (Update update) {
        long UserID=update.getMessage().getChatId();
        String Message=update.getMessage().getText();

        Command toExecute=processor.commandList.get(Message); // Возможны грабли: не тот регистр, лишние пробелы. Учесть
        if(toExecute==null) //команду не нашли, поэтому в зависимости от состояния отрабатываем сообщение
            toExecute=processor.commandList.get(base.getDialogState(UserID));
        if(toExecute==null)
            return "Команда не распознана";
        return toExecute.execute(UserID,Message);

    }

    public static MessageResponse processor=new MessageResponse();
    private final Map<String,Command> commandList=new HashMap<String,Command>();
    public MessageResponse() { //конструктор процессора команд
        commandList.put("/start",new StartCommand());
        commandList.put("/nav1",new Nav1Command());
        commandList.put("Waiting for first waypoint",new StoreFirstWayPoint());
        commandList.put("Waiting for last waypoint",new StoreLastWayPoint());
        // TODO: Добавить команды: Добавить ориентир, Удалить ориентир, Связать ориентиры, Вывести граф целиком.
    }

}