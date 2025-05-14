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
        return null;
    }



    @Override
    public void pausar() throws RemoteException {

    }

    @Override
    public void reanudar() throws RemoteException {

    }
}
