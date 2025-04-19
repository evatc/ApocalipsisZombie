import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Refugio {
    private int zona_descanso;
    private int comedor;
    private int zona_comun;
    private IntegerProperty comida = new SimpleIntegerProperty(0);
    private Lock cerrojo = new ReentrantLock();
    private LinkedBlockingQueue<Humano> filaComedor = new LinkedBlockingQueue<>();
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
    //esperar de forma ordenada pero estoyy viendo como ponerlo bien
    public synchronized void dejarComida(){
        int comida = getComida();
        setComida(comida + 2);
        notifyAll();
    }
    public synchronized void comer(Humano humano){
        try{
            filaComedor.put(humano); //Añade al humano a la fila
            while (comida.get() == 0 || filaComedor.peek() != humano) {
                wait();
            }
            comida.set(comida.get() - 1);
            filaComedor.poll(); //Saca la primer humano de la fila
            double tiempo1 = (3 + Math.random()*2)*1000;
            Thread.sleep((long)tiempo1);
            notifyAll();
        }catch(Exception e){}
    }
    public void zona_espera_tunel1(String id){ //este sería con el q se bloquea hasta q llegan tantos hilos y crea la lista que manda a entrar_zona_riesgo

    }
    public void zona_espera_tunel2(String id){ //este sería con el q se bloquea hasta q llegan tantos hilos y crea la lista que manda a entrar_zona_riesgo

    }
    public void zona_espera_tunel3(String id){ //este sería con el q se bloquea hasta q llegan tantos hilos y crea la lista que manda a entrar_zona_riesgo

    }
    public void zona_espera_tunel4(String id){ //este sería con el q se bloquea hasta q llegan tantos hilos y crea la lista que manda a entrar_zona_riesgo

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
