import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ResourceBundle;
import java.util.concurrent.Semaphore;


public class VentanaController implements Initializable {
    @FXML
    private Label labelComida;
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Inicializando controlador...");
        //Logs
        File fichero = new File("apocalipsis.txt");
        Logs log;
        try {
            log = new Logs(fichero); // La clase Logs ahora maneja la creación del archivo
        } catch (IOException e) {
            System.out.println("Error al crear logs: " + e.getMessage());
            return;
        }

        new Thread(() -> {
            try {
                System.out.println("Iniciando simulación...");

                //Refugio
                Refugio refugio = new Refugio(txtDescanso, txtComedor, txtZonaComun, log, labelComida);

                //Tunel
                Tunel tunel = new Tunel(txtRefugioARiesgo1, txtTunel1, txtRiesgoARefugio1,
                        txtRefugioARiesgo2, txtTunel2, txtRiesgoARefugio2,txtRefugioARiesgo3,
                        txtTunel3, txtRiesgoARefugio3, txtRefugioARiesgo4, txtTunel4,
                        txtRiesgoARefugio4, log, null);

                //Zona riesgo
                Semaphore tiempo_ataque = new Semaphore(1);
                Semaphore tiempo_ataque2 = new Semaphore(1);
                Semaphore tiempo_ataque3 = new Semaphore(1);
                Semaphore tiempo_ataque4 = new Semaphore(1);
                Zona_riesgo zonaRiesgo = new Zona_riesgo(txtRiesgoHumanos1, txtRiesgoHumanos2, txtRiesgoHumanos3,
                        txtRiesgoHumanos4, txtRiesgoZombies1, txtRiesgoZombies2, txtRiesgoZombies3, txtRiesgoZombies4,
                        tiempo_ataque, tiempo_ataque2, tiempo_ataque3, tiempo_ataque4, log, null);


                ObjetoApocalipsis obj = new ObjetoApocalipsis(refugio,tunel,zonaRiesgo, log);
                Registry registry = LocateRegistry.createRegistry(1099);
                Naming.rebind("//127.0.0.1/ObjetoApocalipsis", obj);
                System.out.println("Servidor RMI listo.");

                tunel.setZonaRiesgo(zonaRiesgo);
                zonaRiesgo.setTunel(tunel);
                zonaRiesgo.setApocalipsis(obj);
                tunel.setApocalipsis(obj);

                // Zombie
                Zombie zombie = new Zombie("Z0000", zonaRiesgo, log, obj);
                zombie.start();


                // Humanos
                for (int i = 1; i <= 10000; i++) {
                    try {
                        obj.esperarSiPausado();
                        String humanoid = String.format("H%04d", i);
                        Humano humano = new Humano(humanoid, refugio, tunel, zonaRiesgo,tiempo_ataque,
                                tiempo_ataque2, tiempo_ataque3,tiempo_ataque4, log, obj);
                        humano.start();
                        obj.esperarSiPausado();
                        Thread.sleep((int)(1500 * Math.random() + 500)); //Entre 0,5 y 2 segundos
                    } catch (InterruptedException e) {
                        System.out.println("Error al crear humanos: " + e.getMessage());
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}