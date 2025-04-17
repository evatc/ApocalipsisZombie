import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Refugio {
    private int zona_descanso;
    private int comedor;
    private int zona_comun;
    private IntegerProperty comida = new SimpleIntegerProperty(0);
    private Lock cerrojo = new ReentrantLock();
    private ObservableList<String> ldescanso = FXCollections.observableArrayList();
    private ObservableList<String> lcomedor = FXCollections.observableArrayList();
    private ObservableList<String> lzonaComun = FXCollections.observableArrayList();


    public Refugio() {
        this.zona_descanso = 1;
        this.comedor = 2;
        this.zona_comun = 3;
        this.comida.set(0);
    }

    public void comedor(String id){
        cerrojo.lock();//revisar, semaforos o conditions
        try{
            if(comida.get() != 0){
                comida.set(comida.get() - 1);
                double tiempo1 = (3 + Math.random()*2)*1000;
                Thread.sleep((long)tiempo1);
            }
        }catch(Exception e){}
        finally {
            cerrojo.unlock();
        }

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
