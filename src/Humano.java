import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class Humano extends Thread{
    private String id;
    private Refugio refugio;
    private Tunel tunel;
    private Zona_riesgo zonaRiesgo;
    private boolean herido = false;
    private boolean vivo = true;

    public Humano(String id, Refugio refugio, Tunel tunel, Zona_riesgo zonaRiesgo){
        this.id = id;
        this.refugio = refugio;
        this.tunel = tunel;
        this.zonaRiesgo = zonaRiesgo;
    }

    public void run(){
        System.out.println("Humano " + this.id + " iniciado");
        while (vivo) {
            System.out.println("Humano " + this.id + " entrando a zona común");
            refugio.getlZonaComun().meterh(this);
            double tiempo = (1 + Math.random())*1000;
            System.out.println("El humano " + this.id + " está en el área común");
            try{
                Thread.sleep((long) tiempo);
            }catch(Exception e){}
            int n_tunel = (int) (Math.random()*4)+1;
            System.out.println("El humano " + this.id + " ha elegido el tunel " + n_tunel);
            refugio.getlZonaComun().sacarh(this);
            if(n_tunel == 1){
                tunel.zona_espera_tunel1(this);
            }else if(n_tunel == 2){
                tunel.zona_espera_tunel2(this);
            }else if(n_tunel == 3){
                tunel.zona_espera_tunel3(this);
            }else{
                tunel.zona_espera_tunel4(this);
            }
            double tiempo1 = (3 + Math.random()*2)*1000; // ns si ponerlo aquí o dentro de un método
            try{
                Thread.sleep((long) tiempo1);
            }catch(Exception e){}
            if(vivo){//si no le ha matado un zombie sigue con la rutina
                if(!herido){ //si no ha sido atacado por un zombie pasa por los túneles(herido se va directamente a los tuneles sin pasar por aqui)
                    zonaRiesgo.salir_humano(this,n_tunel);
                    if(n_tunel == 1){
                        tunel.entrar1_zona_descanso(this);
                    }else if(n_tunel == 2){
                        tunel.entrar2_zona_descanso(this);
                    }else if(n_tunel == 3){
                        tunel.entrar3_zona_descanso(this);
                    }else{
                        tunel.entrar4_zona_descanso(this);
                    }
                    refugio.getlDescanso().meterh(this);
                    System.out.println("El humano " + this.id + " ha recolectado 2 piezas de comida");
                    refugio.dejarComida(this);
                }
                double tiempo2 = (2 + Math.random()*2)*1000;
                System.out.println("El humano " + this.id + " está en el área de descanso");
                try{
                    Thread.sleep((long) tiempo2);
                }catch(Exception e){}
                refugio.getlDescanso().sacarh(this);
                refugio.getlComedor().meterh(this);
                System.out.println("El humano " + this.id + " está en el comedor");
                refugio.comer(this);
                refugio.getlComedor().sacarh(this);
                if(herido){
                    refugio.getlDescanso().meterh(this);
                    System.out.println("El humano " + this.id + " ha vuelto al área de descanso para curar sus heridas");
                    double tiempo3 = (3 + Math.random()*2)*1000; // ns si ponerlo aquí o dentro de un método
                    try{
                        Thread.sleep((long) tiempo1);
                    }catch(Exception e){}
                    refugio.getlDescanso().sacarh(this);
                }
            }

        }
        System.out.println("Humano " + this.id + " terminado");


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

}
