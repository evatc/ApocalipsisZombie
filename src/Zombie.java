import java.util.Scanner;

public class Zombie extends Thread{
    private String id;
    private int cont_muertes = 0;
    private Zona_riesgo zonaRiesgo;
    private boolean convertido = false;
    private int n_zonaRiesgo;
    private Logs log;
    private ListaThreads muertesZombies;
    public Zombie(String id, Zona_riesgo zonaRiesgo, Logs log){
        this.id = id;
        this.zonaRiesgo = zonaRiesgo;
        this.log = log;
    }

    public void run(){
        log.escribir("Zombie " + this.id + " iniciado.");
        //muertesZombies.meterTop(this);

        while (true) {
            // Si un humano se convierte en zombie tiene que empezar en la zona en la que murió
            if (convertido){
                convertido = false;
            }
            else{
                n_zonaRiesgo = (int) (Math.random() * 4) + 1;
            }
            zonaRiesgo.entrar_zombie(this,n_zonaRiesgo);
            zonaRiesgo.ataque(this,n_zonaRiesgo);
            try{
                sleep((int)(1000*Math.random() + 2000)); //Entre 2 y 3 segundos
            }catch(Exception e){}
            zonaRiesgo.salir_zombie(this,n_zonaRiesgo);
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

    public void setConvertido(boolean convertido) {
        this.convertido = convertido;
    }

    public void setN_zonaRiesgo(int n_zonaRiesgo) {
        this.n_zonaRiesgo = n_zonaRiesgo;
    }

    public ListaThreads getMuertesZombies() {
        return muertesZombies;
    }

    public void setMuertesZombies(ListaThreads muertesZombies) {
        this.muertesZombies = muertesZombies;
    }
}
