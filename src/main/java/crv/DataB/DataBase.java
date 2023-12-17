package crv.DataB;

import java.util.HashMap;
import java.util.Map;

// база чатов, хранит
public class DataBase {

    public static class TAGSCOLLECTION {
        public static final String STATE="state";
    }

    // Хранилище состояний чатов - хешмап чат ид -> массив состояний, описанных тегами (хранилище значний имя-> значение)
    public Map<Long, HashMap<String,String>> UsersData = new HashMap<>();
    // Singleton
    private static DataBase instance;
    private DataBase(){

    }
    public static DataBase GetInstance(){
        if (instance == null) {
            instance = new DataBase();
        }
        return instance;
    }

    // end Singleton

    // прямое обращение к состоянию диалога (по тегу "State")
    public String getDialogState(Long UserId){
        return UsersData.get(UserId).get("state"); // TODO: переписать обращение по строке на обращение к константе класса TAGSCCOLLECTION
    }

    // создание нового чата, создание для него нового дескриптора с состоянием "новый"
    public void newUser(Long UserId){
        HashMap<String,String> newUserData = new HashMap<>();
        newUserData.put(TAGSCOLLECTION.STATE,"new");
        UsersData.put(UserId, newUserData);
    }

    // получение значения тега чата по имени. Переписать на обращение к константе класса tagscollection
    public String getTag(Long UserID, String Tag){
        return UsersData.get(UserID).get(Tag);
    }

    // изменение записи состояния чата по тегу. Тоже переписать
    public void setTag(Long UserID, String strTag, String strNewValue){
        var UserData = UsersData.get(UserID);
        UserData.put(strTag,strNewValue);
        UsersData.put(UserID,UserData);
    }

    public void uploadData(){

    }
    public void downloadData(){}
}
