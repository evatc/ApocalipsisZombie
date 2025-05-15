import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Comparator;

public class ObjetoApocalipsis extends UnicastRemoteObject implements InterfazApocalipsis {
    private Refugio refugio;
    private Tunel tunel;
    private Zona_riesgo zonaRiesgo;
    private boolean pausado;
    private Logs log;

    public ObjetoApocalipsis(Refugio refugio, Tunel tunel, Zona_riesgo zonaRiesgo, Logs log) throws RemoteException {
        this.refugio = refugio;
        this.tunel = tunel;
        this.zonaRiesgo = zonaRiesgo;
        this.log = log;
    }


    @Override
    public int getHumanosRefugio() throws RemoteException {
        return refugio.getNumHumanos();
    }

    @Override
    public int getHumanosTunel(int n_tunel) throws RemoteException {
        return tunel.getNumHumanos(n_tunel);
    }

    @Override
    public int getHumanosZonaRiesgo(int n_zona) throws RemoteException {
        return zonaRiesgo.getNumHumanos(n_zona);
    }

    @Override
    public int getZombiesZonaRiesgo(int n_zona) throws RemoteException {
        return zonaRiesgo.getNumZombies(n_zona);
    }

    @Override
    public String getTop3() throws RemoteException {
        ArrayList<Zombie> muertes = zonaRiesgo.getTopMuertes();
        //Ordena la lista de zombies del que más a matado al que menos
        muertes.sort(Comparator.comparingInt(Zombie::getCont_muertes).reversed());

        String contenido="";
        int zombies;
        if (muertes.size()<3){
            zombies = muertes.size();
        }else {
            zombies = 3;
        }
        for(int i=0; i<zombies; i++)
        {
            String id = muertes.get(i).getzombieId();
            int n_muertes = muertes.get(i).getCont_muertes();
            contenido += id + " - " + n_muertes + " muertes\n";
        }
        final String textoImprimir = contenido.toString();
        return textoImprimir;
    }



    @Override
    public synchronized void pausar() throws RemoteException {
        pausado = true;
        log.escribir("Apocalipsis pausado");
    }
    @Override
    public synchronized void reanudar() throws RemoteException {
        pausado = false;
        notifyAll();
        log.escribir("Apocalipsis reanudado");
    }

    @Override
    public synchronized void esperarSiPausado() throws RemoteException {
        while (pausado){
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
