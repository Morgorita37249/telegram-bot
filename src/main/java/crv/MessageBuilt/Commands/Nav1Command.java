package crv.MessageBuilt.Commands;

import crv.MessageBuilt.MessageSenters;

public class Nav1Command implements MessageSenters {


    // По команде nav1 - запрашиваем номер аудитории, где находимся, сохраняем в состоянии "firstPoint"
    @Override
    public void execute(Long ChatID, String message) {
        base.setTag(ChatID,"State","Waiting for first waypoint");

        // return "Сообщите, где находитесь (номер аудитории)";
    }
    @Override
    public void send_Message(Long ChatID, String message) {

    }
}
