import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;

public class VentanaController implements Initializable {
    @FXML
    private Label labelComida;
    @FXML
    private ListView<String> descanso;
    @FXML
    private ListView<String> zonaComun;
    @FXML
    private ListView<String> comedor;
    private Refugio refugio;

    public void setRefugio(Refugio refugio) {
        this.refugio = refugio;
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        labelComida.textProperty().bind(refugio.comidaProperty().asString());

        descanso.setItems(refugio.getlDescanso());
        zonaComun.setItems(refugio.getlZonaComun());
        comedor.setItems(refugio.getlComedor());
    }
}
