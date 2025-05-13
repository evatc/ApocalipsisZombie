import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.rmi.Naming;

public class Cliente {
    public static  void main(String args[]){
        try{
            InterfazApocalipsis inter = (InterfazApocalipsis) Naming.lookup("//127.0.0.1/ObjetoApocalipsis");
            inter.lanzarVentana();
        }catch(Exception e){}
    }


}
