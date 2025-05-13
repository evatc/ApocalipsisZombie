import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.Serializable;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Refugio implements Serializable {
    private  ListaThreads lDescanso, lComedor, lZonaComun;

    //private IntegerProperty comida = new SimpleIntegerProperty(0);
    private AtomicInteger comida = new AtomicInteger(0);
    private ConcurrentLinkedQueue<Humano> filaComedor = new ConcurrentLinkedQueue<>();
    private Logs log;
    private VentanaController ventanaController;
    private Label label;


    public Refugio(TextField c1, TextField c2, TextField c3,  Logs log, Label label) {
        lDescanso = new ListaThreads(c1);
        lComedor = new ListaThreads(c2);
        lZonaComun = new ListaThreads(c3);
        this.comida.set(0);
        this.log = log;
        this.label = label;
    }


    public void dejarComida(Humano humano) {
        int nuevaCantidad = comida.addAndGet(2);
        actualizarComida();
        Platform.runLater(() -> {
            log.escribir(humano.gethumanoId() + " ha dejado 2 comidas. Comida total: " + nuevaCantidad + ".");
        });
    }

    public void comer(Humano humano) {
        filaComedor.offer(humano);

        try {
            while (true) {
                int comidaActual = comida.get();

                // Si no hay comida o no es nuestro turno, esperar
                if (comidaActual <= 0 || filaComedor.peek() != humano) {
                    Thread.sleep(100); // Pequeña espera para no saturar la CPU
                    continue;
                }

                // Intentar decrementar la comida
                if (comida.compareAndSet(comidaActual, comidaActual - 1)) {
                    // podemos comer
                    actualizarComida();
                    Platform.runLater(() -> {
                        log.escribir(humano.gethumanoId() + " ha comido. Comida total: " + (comidaActual - 1) + ".");
                    });

                    filaComedor.poll(); // Salir de la fila
                    int tiempoComer = (int)(Math.random()*2000)+3000; // Entre 3 y 5 segundos
                    Thread.sleep(tiempoComer);
                    break;
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    private void actualizarComida(){
        Platform.runLater(() -> {
            label.setText(String.valueOf(getComida()));
        });
    }
    public int getComida() {
        return comida.get();
    }

    public void setComida(int comida) {
        this.comida.set(comida);
    }

    public ListaThreads getlDescanso() {
        return lDescanso;
    }

    public ListaThreads getlComedor() {
        return lComedor;
    }

    public ListaThreads getlZonaComun() {
        return lZonaComun;
    }

    public int getNumHumanos(){
        int total = lDescanso.sizeh() +lComedor.sizeh() + lZonaComun.sizeh();
        return total;
    }

}
