import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.awt.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.util.Duration;

public class ClienteController implements Initializable {
    @FXML
    private Label labelRefugio;
    @FXML
    private Label labelTunel1;
    @FXML
    private Label labelTunel2;
    @FXML
    private Label labelTunel3;
    @FXML
    private Label labelTunel4;
    @FXML
    private Label labelRiesgoH1;
    @FXML
    private Label labelRiesgoH2;
    @FXML
    private Label labelRiesgoH3;
    @FXML
    private Label labelRiesgoH4;
    @FXML
    private Label labelRiesgoZ1;
    @FXML
    private Label labelRiesgoZ2;
    @FXML
    private Label labelRiesgoZ3;
    @FXML
    private Label labelRiesgoZ4;
    @FXML
    private Label labelTop3;
    @FXML
    private TextField txtTop3;
    @FXML
    private Button botonPausar;
    @FXML
    private Button botonReanudar;
    private Timeline timeline;
    private InterfazApocalipsis apocalipsis;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            apocalipsis = (InterfazApocalipsis) Naming.lookup("//127.0.0.1/ObjetoApocalipsis");

            //No se hacer esto de otra manera
            //Actualiza los datos cada medio segundo
            timeline = new Timeline(new KeyFrame(Duration.seconds(0.5), e -> actualizarDatos()));
            timeline.setCycleCount(Animation.INDEFINITE);
            timeline.play();

        } catch (Exception var5) {
            System.out.println("Error en el cliente: " + String.valueOf(var5));
        }
    }
    private void actualizarDatos(){
        try {
            labelRefugio.setText(String.valueOf(apocalipsis.getHumanosRefugio()));

            labelTunel1.setText(String.valueOf(apocalipsis.getHumanosTunel(1)));
            labelTunel2.setText(String.valueOf(apocalipsis.getHumanosTunel(2)));
            labelTunel3.setText(String.valueOf(apocalipsis.getHumanosTunel(3)));
            labelTunel4.setText(String.valueOf(apocalipsis.getHumanosTunel(4)));

            labelRiesgoH1.setText(String.valueOf(apocalipsis.getHumanosZonaRiesgo(1)));
            labelRiesgoH2.setText(String.valueOf(apocalipsis.getHumanosZonaRiesgo(2)));
            labelRiesgoH3.setText(String.valueOf(apocalipsis.getHumanosZonaRiesgo(3)));
            labelRiesgoH4.setText(String.valueOf(apocalipsis.getHumanosZonaRiesgo(4)));

            labelRiesgoZ1.setText(String.valueOf(apocalipsis.getZombiesZonaRiesgo(1)));
            labelRiesgoZ2.setText(String.valueOf(apocalipsis.getZombiesZonaRiesgo(2)));
            labelRiesgoZ3.setText(String.valueOf(apocalipsis.getZombiesZonaRiesgo(3)));
            labelRiesgoZ4.setText(String.valueOf(apocalipsis.getZombiesZonaRiesgo(4)));

            labelTop3.setText(String.valueOf(apocalipsis.getTop3()));


        }catch (RemoteException e){}
    }
    @FXML
    private void pausar(){
        try {
            apocalipsis.pausar();
        }catch (RemoteException e){
            System.out.println("Error al pausar");
        }
    }

    @FXML
    private void reanudar(){
        try {
            apocalipsis.reanudar();
        }catch (RemoteException e){
            System.out.println("Error al pausar");
        }
    }

}
