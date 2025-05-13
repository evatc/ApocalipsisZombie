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


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        /*try {
            InterfazApocalipsis apocalipsis = (InterfazApocalipsis) Naming.lookup("//127.0.0.1/Objetopocalipsis");
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (NotBoundException e) {
            throw new RuntimeException(e);
        }*/
    }
}
