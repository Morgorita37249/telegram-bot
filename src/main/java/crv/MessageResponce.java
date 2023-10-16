package crv;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessageResponce {
    private List<String> Data;
    // private Map<Integer, String> CabinetDecode= Map.of(0,"501",1,"502",2,"503",3,"504",4,"505",5,"506",6,"507",7,"508",8,"509",9,"510",10,"511",11,"512",12,"513",13,"514",14,"515",15,"516",16,"517",17,"518",18,"519",19,"520",20,"521",21,"522",22,"523",23,"524",24,"525",25,"526",26,"527",27,"528",28,"529",29,"530",30,"531",31,"532",32,"533",33,"534",34,"535",35,"536",36,"537",37,"538",38,"539",39,"540");

    public MessageResponce(List Data){
        this.Data=Data;
    }
    public String CreateMsg(){
        String State = Data.get(0);
        if(State == "0"){
            return ("Ok, tell me the closest cabinet");
        }
        return("WIP");
    }
}
