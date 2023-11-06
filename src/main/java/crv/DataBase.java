package crv;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataBase {
    public Map<Long, HashMap<String,String>> UsersData = new HashMap<>();
    private static DataBase instance;
    private DataBase(){}
    public static DataBase GetInstance(){
        if (instance == null) {
            instance = new DataBase();
        }
        return instance;
    }
    public String getDialogState(Long UserId){
        return UsersData.get(UserId).get("state");
    }

    public void newUser(Long UserId){
        HashMap<String,String> temp = new HashMap<>();
        temp.put("state","new");
        UsersData.put(UserId,temp);
    }
    public String getTag(Long UserID, String Tag){
        return UsersData.get(UserID).get(Tag);
    }
    public void changeBase(Long UserID,String strTag,String strNewValue){
        var temp = UsersData.get(UserID);
        temp.put(strTag,strNewValue);
        UsersData.put(UserID,temp);
    }
    public void uploadData(){}
    public void downloadData(){}
}
