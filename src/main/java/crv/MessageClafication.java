package crv;

import java.util.List;
import java.util.Map;

public interface MessageClafication  {


  public static String Navigation(Long UserId){
    DataBase base=DataBase.GetInstance();
    base.changeBase(UserId,"state","nav1");
    return "Ok, where you now, type a cabinet number";
  };
  public static String Start(){
    return "Hello, i'm MM bot";
  };
  public static String Credits(){
    return "i was made by Yurii Yugov and Margarita Pushkina";
  };

}
