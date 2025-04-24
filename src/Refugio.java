import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Refugio {
    private Tunel tunel;
    private int zona_descanso;
    private int comedor;
    private int zona_comun;
    private IntegerProperty comida = new SimpleIntegerProperty(0);
    private Lock cerrojo = new ReentrantLock();
    private ConcurrentLinkedQueue<Humano> filaComedor = new ConcurrentLinkedQueue<>();
    private ObservableList<String> ldescanso = FXCollections.observableArrayList();
    private ObservableList<String> lcomedor = FXCollections.observableArrayList();
    private ObservableList<String> lzonaComun = FXCollections.observableArrayList();

    public Refugio() {
        this.zona_descanso = 1;
        this.comedor = 2;
        this.zona_comun = 3;
        this.comida.set(0);
    }
    //He utilizado una cola porque si no hay comida tienen que
    //esperar de forma ordenada y monitores para la exclusión mutua
    // pero ns si estará bien
    public synchronized void dejarComida(Humano humano){
        int comida = getComida();
        setComida(comida + 2);
        System.out.println(humano.gethumanoId() + " ha dejado 2 comidas. Comida total: " + getComida() + ".");
        notifyAll();
    }
    public synchronized void comer(Humano humano){
        try{

            filaComedor.offer(humano); //Añade al humano a la fila

            while (comida.get() == 0 || filaComedor.peek() != humano) {
                System.out.println(humano.gethumanoId() + " esta esperando en la cola.");
                wait();
            }
            comida.set(comida.get() - 1);
            filaComedor.poll(); //Saca la primer humano de la fila
            System.out.println(humano.gethumanoId() + " ha comido.");
            double tiempo1 = (3 + Math.random()*2)*1000;
            Thread.sleep((long)tiempo1);
            notifyAll();
        }catch(Exception e){}
    }


    public int getComida() {
        return comida.get();
    }

    public void setComida(int comida) {
        this.comida.set(comida);
    }
    public IntegerProperty comidaProperty(){
        return comida;
    }
    public ObservableList<String> getlDescanso(){
        return ldescanso;
    }
    public ObservableList<String> getlComedor(){
        return lcomedor;
    }
    public ObservableList<String> getlZonaComun(){
        return lzonaComun;
    }
}
