package crv.DataB;

import java.util.HashMap;
import java.util.Map;

// база чатов, хранит
public class DataBase {

    public static class TAGSCOLLECTION {
        public static final String STATE="State";
    }
    SQLiteJDBCDriverConnection sqlbase = new SQLiteJDBCDriverConnection();
    // Хранилище состояний чатов - хешмап чат ид -> массив состояний, описанных тегами (хранилище значний имя-> значение)
    public Map<Long, HashMap<String,String>> UsersData = new HashMap<>();
    // Singleton
    private static DataBase instance;
    private DataBase(){
        sqlbase.createNewTable();
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
        //return UsersData.get(UserId).get("state"); // TODO: переписать обращение по строке на обращение к константе класса TAGSCCOLLECTION
        String result = sqlbase.selectTag(UserId,"State");
        if(result=="NotFound"){
            newUser(UserId);
            return "new";
        }
        else {
            return result;
        }
    }

    // создание нового чата, создание для него нового дескриптора с состоянием "новый"
    public void newUser(Long UserId){
        setTag(UserId,"State","new");
    }

    // получение значения тега чата по имени. Переписать на обращение к константе класса tagscollection
    public String getTag(Long UserID, String Tag){
        String result = sqlbase.selectTag(UserID,Tag);
        if(result=="NotFound"){
            newUser(UserID);
            return "Error";
        }
        else {
            return result;
        }
    }

    // изменение записи состояния чата по тегу. Тоже переписать
    public void setTag(Long UserID, String strTag, String strNewValue){
        sqlbase.insertTag(UserID,strTag,strNewValue);
    }

    public void uploadData(){

    }
    public void downloadData(){}
}
