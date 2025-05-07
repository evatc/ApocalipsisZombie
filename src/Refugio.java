import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TextField;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Refugio {
    private  ListaThreads lDescanso, lComedor, lZonaComun;
    //private IntegerProperty comida = new SimpleIntegerProperty(0);
    private AtomicInteger comida = new AtomicInteger(0);
    private ConcurrentLinkedQueue<Humano> filaComedor = new ConcurrentLinkedQueue<>();
    private Logs log;

    public Refugio(TextField c1, TextField c2, TextField c3, Logs log) {
        lDescanso = new ListaThreads(c1);
        lComedor = new ListaThreads(c2);
        lZonaComun = new ListaThreads(c3);
        this.comida.set(0);
        this.log = log;
    }
    //He utilizado una cola porque si no hay comida tienen que
    //esperar de forma ordenada y monitores para la exclusión mutua
    // pero ns si estará bien
    /*public synchronized void dejarComida(Humano humano){
        Platform.runLater(()-> {
            int comida = getComida();
            setComida(comida + 2);
            log.escribir(humano.gethumanoId() + " ha dejado 2 comidas. Comida total: " + getComida() + ".");
        });
        notifyAll();

    }
    public synchronized void comer(Humano humano){
        try{
            filaComedor.offer(humano); //Añade al humano a la fila

            while (comida.get() == 0 || filaComedor.peek() != humano) {
                log.escribir(humano.gethumanoId() + " esta esperando en la cola.");
                wait();
            }
            Platform.runLater(()->{
                comida.set(comida.get() - 1);
            });

            filaComedor.poll(); //Saca la primer humano de la fila
            log.escribir(humano.gethumanoId() + " ha comido. Comida total: " + getComida() + ".");
            int tiempoComer = (int)(Math.random()*2000)+3000; //Entre 3 y 5 segundos
            Thread.sleep(tiempoComer);
            notifyAll();

        }catch(Exception e){}
    }*/

    public void dejarComida(Humano humano) {
        int nuevaCantidad = comida.addAndGet(2);
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
                    // Éxito: podemos comer
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

    public int getComida() {
        return comida.get();
    }

    public void setComida(int comida) {
        this.comida.set(comida);
    }
    /*public IntegerProperty comidaProperty(){
        return comida;
    }*/
    public IntegerProperty comidaProperty() {
        IntegerProperty property = new SimpleIntegerProperty();
        property.set(comida.get());
        // Actualizar la propiedad cuando cambie el AtomicInteger
        new Thread(() -> {
            int lastValue = comida.get();
            while (true) {
                int currentValue = comida.get();
                if (currentValue != lastValue) {
                    Platform.runLater(() -> property.set(currentValue));
                    lastValue = currentValue;
                }
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    break;
                }
            }
        }).start();
        return property;
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

}
