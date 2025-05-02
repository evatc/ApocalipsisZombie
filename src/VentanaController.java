import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.CountDownLatch;

import static java.lang.Thread.sleep;

public class VentanaController implements Initializable {
    @FXML
    private Label labelComida;
    /*@FXML
    private ListView<String> descanso;
    @FXML
    private ListView<String> zonaComun;
    @FXML
    private ListView<String> comedor;*/
    @FXML
    private TextField txtDescanso;
    @FXML
    private TextField txtComedor;
    @FXML
    private TextField txtZonaComun;
    @FXML
    private TextField txtRefugioARiesgo1;
    @FXML
    private TextField txtTunel1;
    @FXML
    private TextField txtRiesgoARefugio1;
    @FXML
    private TextField txtRefugioARiesgo2;
    @FXML
    private TextField txtTunel2;
    @FXML
    private TextField txtRiesgoARefugio2;
    @FXML
    private TextField txtRefugioARiesgo3;
    @FXML
    private TextField txtTunel3;
    @FXML
    private TextField txtRiesgoARefugio3;
    @FXML
    private TextField txtRefugioARiesgo4;
    @FXML
    private TextField txtTunel4;
    @FXML
    private TextField txtRiesgoARefugio4;
    @FXML
    private TextField txtRiesgoHumanos1;
    @FXML
    private TextField txtRiesgoZombies1;
    @FXML
    private TextField txtRiesgoHumanos2;
    @FXML
    private TextField txtRiesgoZombies2;
    @FXML
    private TextField txtRiesgoHumanos3;
    @FXML
    private TextField txtRiesgoZombies3;
    @FXML
    private TextField txtRiesgoHumanos4;
    @FXML
    private TextField txtRiesgoZombies4;


    /* @Override
     public void initialize(URL url, ResourceBundle resourceBundle) {
         Refugio refugio = new Refugio(txtDescanso, txtComedor, txtZonaComun);
         labelComida.textProperty().bind(refugio.comidaProperty().asString());

         //Instanciar elementos comunes
         Tunel tunel = new Tunel(txtRefugioARiesgo1, txtTunel1, txtRiesgoARefugio1,
                 txtRefugioARiesgo2, txtTunel2, txtRiesgoARefugio2,
                 txtRefugioARiesgo3, txtTunel3, txtRiesgoARefugio3,
                 txtRefugioARiesgo4, txtTunel4, txtRiesgoARefugio4);
         Zona_riesgo zonaRiesgo = new Zona_riesgo(txtRiesgoHumanos1, txtRiesgoHumanos2, txtRiesgoHumanos3, txtRiesgoHumanos4, txtRiesgoZombies1, txtRiesgoZombies2, txtRiesgoZombies3, txtRiesgoZombies4);

         tunel.setZonaRiesgo(zonaRiesgo);
         zonaRiesgo.setTunel(tunel);


         //Istanciar el zombie
         Zombie zombie = new Zombie("Z0000", zonaRiesgo);
         zombie.start();
         //Instanciar los humanos de forma escalonada
         for (int i = 1; i <= 10000; i++) {
             try {
                 String humanoid = String.format("H%04d", i);
                 Humano humano = new Humano(humanoid, refugio, tunel, zonaRiesgo);
                 humano.start();
                 sleep((int) (1500 * Math.random() + 500));
             } catch (InterruptedException e) {
                 System.out.println("Error al crear los humanos");
             }
         }
         // Crear hilo para inicialización
         /*new Thread(() -> {
             // Instanciar el zombie
             Zombie zombie = new Zombie("Z0000", zonaRiesgo);
             zombie.start();

             // Instanciar los humanos
             for (int i = 1; i <= 10000; i++) {
                 try {
                     String humanoid = String.format("H%04d", i);
                     Humano humano = new Humano(humanoid, refugio, tunel, zonaRiesgo);
                     humano.start();
                     Thread.sleep((int)(1500 * Math.random() + 500));
                 } catch (InterruptedException e) {
                     System.out.println("Error al crear los humanos");
                 }
             }
         }).start();
     }*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Inicializando controlador..."); // Verifica que esto aparece
        // Verifica que el labelComida no sea null
        if (labelComida == null) {
            System.out.println("ERROR: labelComida es null");
            return;
        }

        // Mueve la lógica pesada a un hilo separado
        new Thread(() -> {
            System.out.println("Iniciando simulación...");

            Refugio refugio = new Refugio(txtDescanso, txtComedor, txtZonaComun);
            Platform.runLater(() -> {
                labelComida.textProperty().bind(refugio.comidaProperty().asString());
            });

            Tunel tunel = new Tunel(txtRefugioARiesgo1, txtTunel1, txtRiesgoARefugio1,
                    txtRefugioARiesgo2, txtTunel2, txtRiesgoARefugio2,
                    txtRefugioARiesgo3, txtTunel3, txtRiesgoARefugio3,
                    txtRefugioARiesgo4, txtTunel4, txtRiesgoARefugio4);
            /*CountDownLatch tiempo_ataque = new CountDownLatch(1);
            CountDownLatch tiempo_ataque2 = new CountDownLatch(1);
            CountDownLatch tiempo_ataque3 = new CountDownLatch(1);
            CountDownLatch tiempo_ataque4 = new CountDownLatch(1);*/
            Zona_riesgo zonaRiesgo = new Zona_riesgo(txtRiesgoHumanos1, txtRiesgoHumanos2, txtRiesgoHumanos3, txtRiesgoHumanos4, txtRiesgoZombies1, txtRiesgoZombies2, txtRiesgoZombies3, txtRiesgoZombies4/*, tiempo_ataque, tiempo_ataque2, tiempo_ataque3, tiempo_ataque4*/);

            tunel.setZonaRiesgo(zonaRiesgo);
            zonaRiesgo.setTunel(tunel);

            // Zombie
            Zombie zombie = new Zombie("Z0000", zonaRiesgo);
            zombie.start();
            System.out.println("Zombie iniciado: " + zombie.getName());

            // Humanos
            for (int i = 1; i <= 10000; i++) { // Reduce a 10 para pruebas
                try {
                    String humanoid = String.format("H%04d", i);
                    Humano humano = new Humano(humanoid, refugio, tunel, zonaRiesgo/*,tiempo_ataque,tiempo_ataque2,tiempo_ataque3,tiempo_ataque4*/);
                    humano.start();
                    System.out.println("Humano iniciado: " + humanoid);
                    Thread.sleep((int)(500 * Math.random() + 200)); // Reduce el tiempo
                } catch (InterruptedException e) {
                    System.out.println("Error al crear humanos: " + e.getMessage());
                }
            }
        }).start();
    }

}
