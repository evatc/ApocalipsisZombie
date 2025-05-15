import java.rmi.Remote;
import java.rmi.RemoteException;

public interface InterfazApocalipsis extends Remote {
    int getHumanosRefugio() throws RemoteException;
    int getHumanosTunel(int n_tunel) throws RemoteException;
    int getHumanosZonaRiesgo(int zona) throws RemoteException;
    int getZombiesZonaRiesgo(int zona) throws RemoteException;
    String getTop3() throws RemoteException;
    void pausar() throws RemoteException;
    void reanudar() throws RemoteException;
    void esperarSiPausado() throws RemoteException;

}
