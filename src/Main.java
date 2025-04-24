import java.io.IOException;

import static java.lang.Thread.sleep;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        /*FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("apocalipsis-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Apocalipsis zombie");
        stage.setMinWidth(1215.0);
        stage.setMinHeight(750.0);
        stage.setWidth(1215.0);
        stage.setHeight(750.0);
        stage.setScene(scene);
        stage.show();*/

        //Instanciar elementos comunes
        Refugio refugio = new Refugio();
        Tunel tunel = new Tunel();
        Zona_riesgo zonaRiesgo = new Zona_riesgo();

        //Istanciar el zombie
        Zombie zombie = new Zombie("Z0000", zonaRiesgo);
        zombie.start();
        //Instanciar los humanos de forma escalonada
        for (int i = 1; i <= 10000; i++)
        {
            try
            {
                String humanoid = String.format("H%04d",i);
                Humano humano = new Humano(humanoid, refugio, tunel, zonaRiesgo);
                humano.start();
                sleep((int)(1500*Math.random() + 500));
            }catch(InterruptedException e)
            {
                System.out.println("Error al crear los humanos");
            }
        }
    }
    public static void main(String[] args) {

        launch();
    }
}