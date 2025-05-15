import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.Serializable;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Refugio implements Serializable {
    private  ListaThreads lDescanso, lComedor, lZonaComun;
    private AtomicInteger comida = new AtomicInteger(0);
    private ConcurrentLinkedQueue<Humano> filaComedor = new ConcurrentLinkedQueue<>();
    private ReentrantLock lock = new ReentrantLock();
    private Condition condicionTurno = lock.newCondition();
    private Logs log;
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
        lock.lock();
        try{
            condicionTurno.signalAll();
        }finally {
            lock.unlock();
        }
    }

    public void comer(Humano humano) {
        lock.lock();
        try {
            filaComedor.offer(humano);
            int comidaActual = comida.get();
            while (comidaActual <= 0 || filaComedor.peek() != humano) {
                // Si no hay comida o no es nuestro turno, esperar
                condicionTurno.await();
                comidaActual = comida.get();
            }
            int resto = comida.decrementAndGet();
            actualizarComida();
            Platform.runLater(() -> {
                log.escribir(humano.gethumanoId() + " ha comido. Comida total: " + resto + ".");
            });
            filaComedor.poll(); // Salir de la fila
        }catch (Exception e){
        }finally {lock.unlock();}
        try{
            int tiempoComer = (int)(Math.random()*2000)+3000; // Entre 3 y 5 segundos
            Thread.sleep(tiempoComer);
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
