import javafx.application.Platform;

import java.rmi.RemoteException;
import java.rmi.server.RMIClientSocketFactory;
import java.rmi.server.RMIServerSocketFactory;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ObjetoApocalipsis extends UnicastRemoteObject implements InterfazApocalipsis {
    private Refugio refugio;
    private Tunel tunel;
    private Zona_riesgo zonaRiesgo;
    private boolean pausado;

    public ObjetoApocalipsis(Refugio refugio, Tunel tunel, Zona_riesgo zonaRiesgo) throws RemoteException {
        this.refugio = refugio;
        this.tunel = tunel;
        this.zonaRiesgo = zonaRiesgo;
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
    public void pausar() throws RemoteException {
        pausado = true;
        System.out.println("Apiicalipsis pausado");
    }
    @Override
    public synchronized void reanudar() throws RemoteException {
        pausado = false;
        notifyAll();
        System.out.println("Apiicalipsis reanudado");
    }

    @Override
    public boolean estaPausado() throws RemoteException {
        return pausado;
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
