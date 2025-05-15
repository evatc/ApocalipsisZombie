import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Servidor extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Servidor.class.getResource("apocalipsis-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Apocalipsis zombie");
        stage.setMinWidth(1215.0);
        stage.setMinHeight(750.0);
        stage.setWidth(1215.0);
        stage.setHeight(750.0);
        stage.setScene(scene);
        stage.show();

    }
    public static void main(String[] args) {
        launch();
    }
}
