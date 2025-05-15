
import java.rmi.RemoteException;

public class Zombie extends Thread{
    private String id;
    private int cont_muertes = 0;
    private Zona_riesgo zonaRiesgo;
    private boolean convertido = false;
    private int n_zonaRiesgo;
    private Logs log;
    private InterfazApocalipsis apocalipsis;

    public Zombie(String id, Zona_riesgo zonaRiesgo, Logs log, InterfazApocalipsis apocalipsis){
        this.id = id;
        this.zonaRiesgo = zonaRiesgo;
        this.log = log;
        this.apocalipsis = apocalipsis;
    }

    public void run(){
        log.escribir("Zombie " + this.id + " iniciado.");
        zonaRiesgo.getTopMuertes().add(this);

        while (true) {
            try {
                apocalipsis.esperarSiPausado();

                // Si un humano se convierte en zombie tiene que empezar en la zona en la que murió
                if (convertido){
                    convertido = false;
                }
                else{
                    n_zonaRiesgo = (int) (Math.random() * 4) + 1;
                }
                apocalipsis.esperarSiPausado();
                zonaRiesgo.entrar_zombie(this,n_zonaRiesgo);
                apocalipsis.esperarSiPausado();
                zonaRiesgo.ataque(this,n_zonaRiesgo);
                apocalipsis.esperarSiPausado();
                try{
                    sleep((int)(1000*Math.random() + 2000)); //Entre 2 y 3 segundos
                    apocalipsis.esperarSiPausado();
                }catch(Exception e){}
                apocalipsis.esperarSiPausado();
                zonaRiesgo.salir_zombie(this,n_zonaRiesgo);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
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


}
