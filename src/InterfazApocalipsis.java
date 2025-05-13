import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface InterfazApocalipsis extends Remote {
    int getHumanosRefugio() throws RemoteException;
    int getHumanosTunel(int n_tunel) throws RemoteException;
    int getHumanosZonaRiesgo(int zona) throws RemoteException;
    int getZombiesZonaRiesgo(int zona) throws RemoteException;
    Zombie getTop1() throws RemoteException;
    Zombie getTop2() throws RemoteException;
    Zombie getTop3() throws RemoteException;
    void pausar() throws RemoteException;
    void reanudar() throws RemoteException;

    }
