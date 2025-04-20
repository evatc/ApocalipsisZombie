import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.concurrent.ArrayBlockingQueue;
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
    private LinkedBlockingQueue<Humano> filaComedor = new LinkedBlockingQueue<>();
    private ObservableList<String> ldescanso = FXCollections.observableArrayList();
    private ObservableList<String> lcomedor = FXCollections.observableArrayList();
    private ObservableList<String> lzonaComun = FXCollections.observableArrayList();
    private CyclicBarrier barrera1 = new CyclicBarrier(3);
    private CyclicBarrier barrera2 = new CyclicBarrier(3);
    private CyclicBarrier barrera3 = new CyclicBarrier(3);
    private CyclicBarrier barrera4 = new CyclicBarrier(3);


    public Refugio() {
        this.zona_descanso = 1;
        this.comedor = 2;
        this.zona_comun = 3;
        this.comida.set(0);
    }
    //He utilizado una cola porque si no hay comida tienen que
    //esperar de forma ordenada y monitores para la exclusión mutua
    // pero ns si estará bien
    public synchronized void dejarComida(){
        int comida = getComida();
        setComida(comida + 2);
        notifyAll();
    }
    public synchronized void comer(Humano humano){
        try{
            filaComedor.put(humano); //Añade al humano a la fila
            while (comida.get() == 0 || filaComedor.peek() != humano) {
                System.out.println(humano.gethumanoId() + " esta esperando en la cola.");
                wait();
            }
            System.out.println(humano.gethumanoId() + " ha comido.");
            comida.set(comida.get() - 1);
            filaComedor.poll(); //Saca la primer humano de la fila
            double tiempo1 = (3 + Math.random()*2)*1000;
            Thread.sleep((long)tiempo1);
            notifyAll();
        }catch(Exception e){}
    }
    public void zona_espera_tunel1(Humano humano){
        try{
            System.out.println(humano.gethumanoId() + "espera ha que haya 3 humanos en el túnel 1.");
            barrera1.await();
            tunel.getLr1().add(humano);
            System.out.println("El túnel 1 tiene ya 3 humanos.");
            tunel.entrar_zona_riesgo1(humano.gethumanoId());
            Thread.sleep(1000);//Es el tiempo que tardan en cruzar el túnel pero ns si ponerlo aqui
        }catch (Exception e){}
    }
    public void zona_espera_tunel2(Humano humano){
        try{
            System.out.println(humano.gethumanoId() + "espera ha que haya 3 humanos en el túnel 2.");
            barrera2.await();
            tunel.getLr2().add(humano);
            System.out.println("El túnel 2 tiene ya 3 humanos.");
            tunel.entrar_zona_riesgo2(humano.gethumanoId());
            Thread.sleep(1000);//Es el tiempo que tardan en cruzar el túnel pero ns si ponerlo aqui
        }catch (Exception e){}
    }
    public void zona_espera_tunel3(Humano humano){
        try{
            System.out.println(humano.gethumanoId() + "espera ha que haya 3 humanos en el túnel 3.");
            barrera3.await();
            tunel.getLr3().add(humano);
            System.out.println("El túnel 3 tiene ya 3 humanos.");
            tunel.entrar_zona_riesgo3(humano.gethumanoId());
            Thread.sleep(1000);//Es el tiempo que tardan en cruzar el túnel pero ns si ponerlo aqui
        }catch (Exception e){}
    }
    public void zona_espera_tunel4(Humano humano){
        try{
            System.out.println(humano.gethumanoId() + "espera ha que haya 3 humanos en el túnel 4.");
            barrera1.await();
            tunel.getLr4().add(humano);
            System.out.println("El túnel 4 tiene ya 3 humanos.");
            tunel.entrar_zona_riesgo4(humano.gethumanoId());
            Thread.sleep(1000);//Es el tiempo que tardan en cruzar el túnel pero ns si ponerlo aqui
        }catch (Exception e){}
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
