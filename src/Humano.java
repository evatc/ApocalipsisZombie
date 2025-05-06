import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class Humano extends Thread{
    private String id;
    private Refugio refugio;
    private Tunel tunel;
    private Zona_riesgo zonaRiesgo;
    private boolean herido = false;
    private boolean vivo = true;
    private boolean ataque = false;
    private Semaphore tiempo_ataque1;
    private Semaphore tiempo_ataque2;
    private Semaphore tiempo_ataque3;
    private Semaphore tiempo_ataque4;
    public Humano(){}

    public Humano(String id, Refugio refugio, Tunel tunel, Zona_riesgo zonaRiesgo,
                  Semaphore tiempo_ataque1, Semaphore tiempo_ataque2,
                  Semaphore tiempo_ataque3, Semaphore tiempo_ataque4){
        this.id = id;
        this.refugio = refugio;
        this.tunel = tunel;
        this.zonaRiesgo = zonaRiesgo;
        this.tiempo_ataque1 = tiempo_ataque1;
        this.tiempo_ataque2 = tiempo_ataque2;
        this.tiempo_ataque3 = tiempo_ataque3;
        this.tiempo_ataque4 = tiempo_ataque4;
    }

    public void run(){
        System.out.println("Humano " + this.id + " iniciado.");
        while (vivo) {
            try{
                refugio.getlZonaComun().meterh(this);
                int tiempo = (int)(1000 + Math.random() * 1000); //Entre 1 y 2 segundos
                System.out.println("El humano " + this.id + " está en el área común");
                Thread.sleep(tiempo);
                int n_tunel = (int) (Math.random() * 4) + 1;
                System.out.println("El humano " + this.id + " ha elegido el tunel " + n_tunel);
                refugio.getlZonaComun().sacarh(this);
                if (n_tunel == 1) {
                    tunel.zona_espera_tunel1(this);
                } else if (n_tunel == 2) {
                    tunel.zona_espera_tunel2(this);
                } else if (n_tunel == 3) {
                    tunel.zona_espera_tunel3(this);
                } else {
                    tunel.zona_espera_tunel4(this);
                }
                int tiempoEnZonaRiesgo = (int)((Math.random()*2000)+5000); // Entre 3 y 5 segundos
                Thread.sleep(tiempoEnZonaRiesgo);

                //mira si hay algún zombie buscando victima
                if (n_tunel == 1){
                    try{
                        if (zonaRiesgo.getLock1().tryAcquire(100,TimeUnit.MILLISECONDS)){
                            // Si le están atacando espera a que termine el ataque
                            if (ataque){
                                while (ataque){
                                    tiempo_ataque1.acquire();
                                }
                                tiempo_ataque1.release();
                            }
                        }
                    }catch (InterruptedException e){
                        Thread.currentThread().interrupt();
                    } finally {
                        zonaRiesgo.getLock1().release();
                    }

                } else if (n_tunel == 2){
                    try{
                        if (zonaRiesgo.getLock2().tryAcquire(100,TimeUnit.MILLISECONDS)){
                            // Si le están atacando espera a que termine el ataque
                            if (ataque){
                                while (ataque){
                                    tiempo_ataque2.acquire();
                                }
                                tiempo_ataque2.release();
                            }
                        }
                    }catch (InterruptedException e){
                        Thread.currentThread().interrupt();
                    } finally {
                        zonaRiesgo.getLock2().release();
                    }

                } else if (n_tunel == 3){
                    try{
                        if (zonaRiesgo.getLock3().tryAcquire(100,TimeUnit.MILLISECONDS)){
                            // Si le están atacando espera a que termine el ataque
                            if (ataque){
                                while (ataque){
                                    tiempo_ataque3.acquire();
                                }
                                tiempo_ataque3.release();
                            }
                        }
                    }catch (InterruptedException e){
                        Thread.currentThread().interrupt();
                    } finally {
                        zonaRiesgo.getLock3().release();
                    }

                } else if (n_tunel == 4){
                    try{
                        if (zonaRiesgo.getLock4().tryAcquire(100,TimeUnit.MILLISECONDS)){
                            // Si le están atacando espera a que termine el ataque
                            if (ataque){
                                while (ataque){
                                    tiempo_ataque4.acquire();
                                }
                                tiempo_ataque4.release();
                            }
                        }
                    }catch (InterruptedException e){
                        Thread.currentThread().interrupt();
                    } finally {
                        zonaRiesgo.getLock4().release();
                    }
                }

                if (!vivo) {
                    break; // Terminar el hilo si el humano ha muerto
                }else{//si no le ha matado un zombie sigue con la rutina
                    zonaRiesgo.salir_humano(this,n_tunel);
                    if(n_tunel == 1){
                        zonaRiesgo.getLock1().release();
                        tunel.entrar1_zona_descanso(this);
                    }else if(n_tunel == 2){
                        zonaRiesgo.getLock2().release();
                        tunel.entrar2_zona_descanso(this);
                    }else if(n_tunel == 3){
                        zonaRiesgo.getLock3().release();
                        tunel.entrar3_zona_descanso(this);
                    }else{
                        zonaRiesgo.getLock4().release();
                        tunel.entrar4_zona_descanso(this);
                    }
                    refugio.getlDescanso().meterh(this);
                    if(!herido){ //si no ha sido atacado por un zombie pasa por los túneles(herido se va directamente a los tuneles sin pasar por aqui)
                        //refugio.getlDescanso().meterh(this);
                        System.out.println("El humano " + this.id + " ha recolectado 2 piezas de comida");
                        refugio.dejarComida(this);
                    }
                    int tiempoEnzonaDescanso = (int)(Math.random()*2000)+2000; //Entre 2 y 4 segundos
                    System.out.println("El humano " + this.id + " está en el área de descanso");
                    try{
                        Thread.sleep(tiempoEnzonaDescanso);
                    }catch(InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                    refugio.getlDescanso().sacarh(this);
                    refugio.getlComedor().meterh(this);
                    System.out.println("El humano " + this.id + " está en el comedor");
                    refugio.comer(this);
                    refugio.getlComedor().sacarh(this);
                    if(herido){
                        refugio.getlDescanso().meterh(this);
                        System.out.println("El humano " + this.id + " ha vuelto al área de descanso para curar sus heridas");
                        int tiempoEnzonaDescanso2 = (int)(Math.random()*2000)+3000; // Entre 3 y 5 segundos
                        Thread.sleep(tiempoEnzonaDescanso2);
                        refugio.getlDescanso().sacarh(this);
                        herido = false;
                    }
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        }
        System.out.println("Humano " + this.id + " terminado");
        /*System.out.println("Humano " + this.id + " iniciado");
        while (vivo) {
            try {
                // Zona común
                refugio.getlZonaComun().meterh(this);
                double tiempoComun = (1000 + Math.random() * 1000); // 1-2 segundos
                Thread.sleep((long) tiempoComun);

                // Seleccionar túnel
                int n_tunel = (int) (Math.random() * 4) + 1;
                refugio.getlZonaComun().sacarh(this);

                // Esperar en grupo de 3 para salir
                switch (n_tunel) {
                    case 1: tunel.zona_espera_tunel1(this); break;
                    case 2: tunel.zona_espera_tunel2(this); break;
                    case 3: tunel.zona_espera_tunel3(this); break;
                    case 4: tunel.zona_espera_tunel4(this); break;
                }

                // Tiempo en zona de riesgo (si sigue vivo)
                if (vivo) {
                    double tiempoRiesgo = (3000 + Math.random() * 2000); // 3-5 segundos
                    Thread.sleep((long) tiempoRiesgo);

                    // Intentar regresar
                    if (vivo && !herido) {
                        refugio.dejarComida(this); // Dejar 2 piezas de comida
                    }

                    // Regresar al refugio
                    switch (n_tunel) {
                        case 1: tunel.entrar1_zona_descanso(this); break;
                        case 2: tunel.entrar2_zona_descanso(this); break;
                        case 3: tunel.entrar3_zona_descanso(this); break;
                        case 4: tunel.entrar4_zona_descanso(this); break;
                    }

                    // Descanso
                    refugio.getlDescanso().meterh(this);
                    double tiempoDescanso = (2000 + Math.random() * 2000); // 2-4 segundos
                    Thread.sleep((long) tiempoDescanso);
                    refugio.getlDescanso().sacarh(this);

                    // Comedor
                    refugio.getlComedor().meterh(this);
                    refugio.comer(this);
                    refugio.getlComedor().sacarh(this);

                    // Si está herido, descansar nuevamente
                    if (herido) {
                        refugio.getlDescanso().meterh(this);
                        double tiempoCuracion = (3000 + Math.random() * 2000); // 3-5 segundos
                        Thread.sleep((long) tiempoCuracion);
                        refugio.getlDescanso().sacarh(this);
                        herido = false; // Se cura
                    }
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
        System.out.println("Humano " + this.id + " terminado");*/


    }

    public boolean isHerido() {
        return herido;
    }

    public void setHerido(boolean herido) {
        this.herido = herido;
    }

    public boolean isVivo() {
        return vivo;
    }

    public void setVivo(boolean vivo) {
        this.vivo = vivo;
    }


    public String gethumanoId() {
        return this.id;
    }

    public void setAtaque(boolean ataque){
        this.ataque = ataque;
    }



}
