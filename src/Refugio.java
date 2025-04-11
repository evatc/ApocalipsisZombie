import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Refugio {
    private int zona_descanso;
    private int comedor;
    private int zona_comun;
    private int comida;
    private Lock cerrojo = new ReentrantLock();

    public Refugio() {
        this.zona_descanso = 1;
        this.comedor = 2;
        this.zona_comun = 3;
        this.comida = 0;
    }

    public void comedor(String id){
        cerrojo.lock();//revisar, semaforos o conditions
        try{
            if(comida != 0){
                comida -= 1;
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
        return comida;
    }

    public void setComida(int comida) {
        this.comida = comida;
    }
}
