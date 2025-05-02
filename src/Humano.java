import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;
import java.util.concurrent.CountDownLatch;

public class Humano extends Thread{
    private String id;
    private Refugio refugio;
    private Tunel tunel;
    private Zona_riesgo zonaRiesgo;
    private boolean herido = false;
    private boolean vivo = true;
    private boolean ataque = false;
    private CountDownLatch tiempo_ataque1;
    private CountDownLatch tiempo_ataque2;
    private CountDownLatch tiempo_ataque3;
    private CountDownLatch tiempo_ataque4;
    public Humano(){}

    public Humano(String id, Refugio refugio, Tunel tunel, Zona_riesgo zonaRiesgo,
                  CountDownLatch tiempo_ataque1, CountDownLatch tiempo_ataque2,
                  CountDownLatch tiempo_ataque3, CountDownLatch tiempo_ataque4){
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
        System.out.println("Humano " + this.id + " iniciado");
        while (vivo) {
            try{
                System.out.println("Humano " + this.id + " entrando a zona común");
                refugio.getlZonaComun().meterh(this);
                double tiempo = (1000 + Math.random() * 1000); //Entre 1 y 2 segundos
                System.out.println("El humano " + this.id + " está en el área común");
                Thread.sleep((long) tiempo);
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
                double tiempo1 = (3 + Math.random()*2)*1000; // ns si ponerlo aquí o dentro de un método
                Thread.sleep((long) tiempo1);
                /*if (ataque){
                    System.out.println("Humano " + this.id + " está siendo atacado");
                    while (ataque){
                        Thread.sleep(1);
                    }
                }
                System.out.println("Humano " + this.id + " ha terminado de ser atacado");*/

                //mira si hay algún zombie buscando victima
                if (n_tunel == 1) {
                    try {
                        zonaRiesgo.getLock1().acquire();
                        if(ataque == true){
                            tiempo_ataque1.await();
                        }
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }else if(n_tunel == 2){
                    try {
                        zonaRiesgo.getLock2().acquire();
                        if(ataque == true){
                            tiempo_ataque2.await();
                        }
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }else if (n_tunel == 3){
                    try {
                        zonaRiesgo.getLock3().acquire();
                        if(ataque == true){
                            tiempo_ataque3.await();
                        }
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }else{
                    try {
                        zonaRiesgo.getLock4().acquire();
                        if(ataque == true){
                            tiempo_ataque4.await();
                        }
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
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
                    if(!herido){ //si no ha sido atacado por un zombie pasa por los túneles(herido se va directamente a los tuneles sin pasar por aqui)
                        refugio.getlDescanso().meterh(this);
                        System.out.println("El humano " + this.id + " ha recolectado 2 piezas de comida");
                        refugio.dejarComida(this);
                    }
                    double tiempo2 = (2 + Math.random()*2)*1000; //Entre 2 y 4 segundos
                    System.out.println("El humano " + this.id + " está en el área de descanso");
                    try{
                        Thread.sleep((long) tiempo2);
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
                        double tiempo3 = (3 + Math.random()*2)*1000; // Entre 3 y 5 segundos
                        Thread.sleep((long) tiempo3);
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
