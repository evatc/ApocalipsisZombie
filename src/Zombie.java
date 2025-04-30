import java.util.Scanner;

public class Zombie extends Thread{
    private String id;
    private int cont_muertes = 0;
    private Zona_riesgo zonaRiesgo;
    public Zombie(String id, Zona_riesgo zonaRiesgo){
        this.id = id;
        this.zonaRiesgo = zonaRiesgo;
    }

    public void run(){

        while (true) {
            int n_zonariesgo = (int) (Math.random() * 4) + 1;
            zonaRiesgo.entrar_zombie(this,n_zonariesgo);
            zonaRiesgo.ataque(this,n_zonariesgo);
            try{
                sleep((int)(1000*Math.random() + 2000));
            }catch(Exception e){}
            zonaRiesgo.salir_zombie(this,n_zonariesgo);
        }
    }

    public int getCont_muertes() {
        return cont_muertes;
    }

    public void setCont_muertes(int cont_muertes) {
        this.cont_muertes = cont_muertes;
    }

    public String getzombieId() {
        return id;
    }


}
