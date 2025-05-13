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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

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
    private TextField txtTop3;
    private InterfazApocalipsis apocalipsis;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            apocalipsis = (InterfazApocalipsis) Naming.lookup("//127.0.0.1/Objetopocalipsis");
            while (true){
                actualizarDatos();
            }

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


        }catch (RemoteException e){}
        }

}
