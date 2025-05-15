import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import javafx.scene.control.TextField;

public class Tunel implements Serializable {
    private ListaThreads esperanRefugioARiesgo1, dentroTunel1, esperanRiesgoARefugio1;
    private ListaThreads esperanRefugioARiesgo2, dentroTunel2, esperanRiesgoARefugio2;
    private ListaThreads esperanRefugioARiesgo3, dentroTunel3, esperanRiesgoARefugio3;
    private ListaThreads esperanRefugioARiesgo4, dentroTunel4, esperanRiesgoARefugio4;
    private Zona_riesgo zonaRiesgo;
    private int enTunel1;
    private int refugioARiesgo1;
    private int riesgoARefugio1;
    private int enTunel2;
    private int refugioARiesgo2;
    private int riesgoARefugio2;
    private int enTunel3;
    private int refugioARiesgo3;
    private int riesgoARefugio3;
    private int enTunel4;
    private int refugioARiesgo4;
    private int riesgoARefugio4;

    private Lock cerrojo1 = new ReentrantLock();
    private Condition refugio1 = cerrojo1.newCondition();
    private Condition zona_riesgo1 = cerrojo1.newCondition();
    private Lock cerrojo2 = new ReentrantLock();
    private Condition refugio2 = cerrojo2.newCondition();
    private Condition zona_riesgo2 = cerrojo2.newCondition();
    private Lock cerrojo3 = new ReentrantLock();
    private Condition refugio3 = cerrojo3.newCondition();
    private Condition zona_riesgo3 = cerrojo3.newCondition();
    private Lock cerrojo4 = new ReentrantLock();
    private Condition refugio4 = cerrojo4.newCondition();
    private Condition zona_riesgo4 = cerrojo4.newCondition();
    private CyclicBarrier barrera1 = new CyclicBarrier(3);
    private CyclicBarrier barrera2 = new CyclicBarrier(3);
    private CyclicBarrier barrera3 = new CyclicBarrier(3);
    private CyclicBarrier barrera4 = new CyclicBarrier(3);
    private Logs log;
    private InterfazApocalipsis apocalipsis;

    public Tunel(TextField c11, TextField c21, TextField c31, TextField c12,
                 TextField c22, TextField c32, TextField c13, TextField c23,
                 TextField c33, TextField c14, TextField c24, TextField c34, Logs log, InterfazApocalipsis apocalipsis) {
        esperanRefugioARiesgo1 = new ListaThreads(c11);
        dentroTunel1 = new ListaThreads(c21);
        esperanRiesgoARefugio1 = new ListaThreads(c31);
        esperanRefugioARiesgo2 = new ListaThreads(c12);
        dentroTunel2 = new ListaThreads(c22);
        esperanRiesgoARefugio2 = new ListaThreads(c32);
        esperanRefugioARiesgo3 = new ListaThreads(c13);
        dentroTunel3 = new ListaThreads(c23);
        esperanRiesgoARefugio3 = new ListaThreads(c33);
        esperanRefugioARiesgo4 = new ListaThreads(c14);
        dentroTunel4 = new ListaThreads(c24);
        esperanRiesgoARefugio4 = new ListaThreads(c34);
        this.log = log;
        this.apocalipsis = apocalipsis;
    }

    public void entrar_zona_riesgo1(Humano humano) throws InterruptedException {
        try {
            apocalipsis.esperarSiPausado();
            cerrojo1.lock();
            try{
                apocalipsis.esperarSiPausado();
                refugioARiesgo1++;
                while(riesgoARefugio1>0 || enTunel1>0){
                    apocalipsis.esperarSiPausado();
                    log.escribir("El humano " + humano.gethumanoId() + " está esperando a poder entrar en la zona de riesgo 1.");
                    refugio1.await();
                    apocalipsis.esperarSiPausado();
                }
                esperanRefugioARiesgo1.sacarh(humano);
                refugioARiesgo1--;
                enTunel1++;
                dentroTunel1.meterh(humano);
            } finally {
                cerrojo1.unlock();
            }
            // Hay dos cerrojo para que mientras un humano este pasando el túnel otros humanos puedan entrar en las zonas de espera
            apocalipsis.esperarSiPausado();
            log.escribir("El humano " + humano.gethumanoId() + " está atravesando el túnel 1.");
            Thread.sleep(1000); // Atraviesa el túnel
            cerrojo1.lock();
            try{
                apocalipsis.esperarSiPausado();
                dentroTunel1.sacarh(humano);
                enTunel1--;
                zonaRiesgo.entrar_humano(humano,1);
                log.escribir("El humano " + humano.gethumanoId() + " ha entrado en la zona de riesgo 1");
                zona_riesgo1.signal();
                refugio1.signal();
            }catch (Exception e){}
            finally {
                cerrojo1.unlock();
            }
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }
    public void entrar1_zona_descanso(Humano humano) throws InterruptedException {
        try {
            apocalipsis.esperarSiPausado();
            cerrojo1.lock();
            try{
                apocalipsis.esperarSiPausado();
                riesgoARefugio1++;
                esperanRiesgoARefugio1.meterh(humano);
                while(enTunel1>0){
                    apocalipsis.esperarSiPausado();
                    log.escribir("El humano " + humano.gethumanoId() + " está esperando a poder entrar en el túnel 1 para ir al refugio.");
                    zona_riesgo1.await();
                    apocalipsis.esperarSiPausado();
                }
                esperanRiesgoARefugio1.sacarh(humano);
                riesgoARefugio1--;
                enTunel1++;
                dentroTunel1.meterh(humano);
            } finally {cerrojo1.unlock();}
            // Hay dos cerrojo para que mientras un humano este pasando el túnel otros humanos puedan entrar en las zonas de espera
            apocalipsis.esperarSiPausado();
            log.escribir("El humano " + humano.gethumanoId() + " está atravesando el túnel 1.");
            Thread.sleep(1000); // Atraviesa el túnel
            cerrojo1.lock();
            try{
                apocalipsis.esperarSiPausado();
                dentroTunel1.sacarh(humano);
                enTunel1--;
                log.escribir("El humano " + humano.gethumanoId() + " ha entrado en el refugio");
                zona_riesgo1.signal();
                refugio1.signal();
            }catch (Exception e){}
            finally{
                cerrojo1.unlock();
            }
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public void entrar_zona_riesgo2(Humano humano) throws InterruptedException {
        try {
            apocalipsis.esperarSiPausado();
            cerrojo2.lock();
            try{
                apocalipsis.esperarSiPausado();
                refugioARiesgo2++;
                while(riesgoARefugio2>0 || enTunel2>0){
                    apocalipsis.esperarSiPausado();
                    log.escribir("El humano " + humano.gethumanoId() + " está esperando a poder entrar en la zona de riesgo 2.");
                    refugio2.await();
                    apocalipsis.esperarSiPausado();
                }
                esperanRefugioARiesgo2.sacarh(humano);
                refugioARiesgo2--;
                enTunel2++;
                dentroTunel2.meterh(humano);
            } finally {
                cerrojo2.unlock();
            }
            // Hay dos cerrojo para que mientras un humano este pasando el túnel otros humanos puedan entrar en las zonas de espera
            apocalipsis.esperarSiPausado();
            log.escribir("El humano " + humano.gethumanoId() + " está atravesando el túnel 2.");
            Thread.sleep(1000); // Atraviesa el túnel
            cerrojo2.lock();
            try{
                apocalipsis.esperarSiPausado();
                dentroTunel2.sacarh(humano);
                enTunel2--;
                zonaRiesgo.entrar_humano(humano,2);
                log.escribir("El humano " + humano.gethumanoId() + " ha entrado en la zona de riesgo 2");
                zona_riesgo2.signal();
                refugio2.signal();
            }catch (Exception e){}
            finally {
                cerrojo2.unlock();
            }
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }
    public void entrar2_zona_descanso(Humano humano) throws InterruptedException {
        try {
            apocalipsis.esperarSiPausado();
            cerrojo2.lock();
            try{
                apocalipsis.esperarSiPausado();
                riesgoARefugio2++;
                esperanRiesgoARefugio2.meterh(humano);
                while(enTunel2>0){
                    apocalipsis.esperarSiPausado();
                    log.escribir("El humano " + humano.gethumanoId() + " está esperando a poder entrar en el túnel 2 para ir al refugio.");
                    zona_riesgo2.await();
                    apocalipsis.esperarSiPausado();
                }
                esperanRiesgoARefugio2.sacarh(humano);
                riesgoARefugio2--;
                enTunel2++;
                dentroTunel2.meterh(humano);
            } finally {cerrojo2.unlock();}
            // Hay dos cerrojo para que mientras un humano este pasando el túnel otros humanos puedan entrar en las zonas de espera
            apocalipsis.esperarSiPausado();
            log.escribir("El humano " + humano.gethumanoId() + " está atravesando el túnel 2.");
            Thread.sleep(1000); // Atraviesa el túnel
            cerrojo2.lock();
            try{
                apocalipsis.esperarSiPausado();
                dentroTunel2.sacarh(humano);
                enTunel2--;
                log.escribir("El humano " + humano.gethumanoId() + " ha entrado en el refugio");
                zona_riesgo2.signal();
                refugio2.signal();
            }catch (Exception e){}
            finally{
                cerrojo2.unlock();
            }
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public void entrar_zona_riesgo3(Humano humano)throws InterruptedException {
        try {
            apocalipsis.esperarSiPausado();
            cerrojo3.lock();
            try{
                apocalipsis.esperarSiPausado();
                refugioARiesgo3++;
                while(riesgoARefugio3>0 || enTunel3>0){
                    apocalipsis.esperarSiPausado();
                    log.escribir("El humano " + humano.gethumanoId() + " está esperando a poder entrar en la zona de riesgo 3.");
                    refugio3.await();
                    apocalipsis.esperarSiPausado();
                }
                esperanRefugioARiesgo3.sacarh(humano);
                refugioARiesgo3--;
                enTunel3++;
                dentroTunel3.meterh(humano);
            } finally {
                cerrojo3.unlock();
            }
            // Hay dos cerrojo para que mientras un humano este pasando el túnel otros humanos puedan entrar en las zonas de espera
            apocalipsis.esperarSiPausado();
            log.escribir("El humano " + humano.gethumanoId() + " está atravesando el túnel 3.");
            Thread.sleep(1000); // Atraviesa el túnel
            cerrojo3.lock();
            try{
                apocalipsis.esperarSiPausado();
                dentroTunel3.sacarh(humano);
                enTunel3--;
                zonaRiesgo.entrar_humano(humano,3);
                log.escribir("El humano " + humano.gethumanoId() + " ha entrado en la zona de riesgo 3");
                zona_riesgo3.signal();
                refugio3.signal();
            }catch (Exception e){}
            finally {
                cerrojo3.unlock();
            }
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }
    public void entrar3_zona_descanso(Humano humano) throws InterruptedException {
        try {
            apocalipsis.esperarSiPausado();
            cerrojo3.lock();
            try{
                apocalipsis.esperarSiPausado();
                riesgoARefugio3++;
                esperanRiesgoARefugio3.meterh(humano);
                while(enTunel3>0){
                    apocalipsis.esperarSiPausado();
                    log.escribir("El humano " + humano.gethumanoId() + " está esperando a poder entrar en el túnel 3 para ir al refugio.");
                    zona_riesgo3.await();
                    apocalipsis.esperarSiPausado();
                }
                esperanRiesgoARefugio3.sacarh(humano);
                riesgoARefugio3--;
                enTunel3++;
                dentroTunel3.meterh(humano);
            } finally {cerrojo3.unlock();}
            // Hay dos cerrojo para que mientras un humano este pasando el túnel otros humanos puedan entrar en las zonas de espera
            apocalipsis.esperarSiPausado();
            log.escribir("El humano " + humano.gethumanoId() + " está atravesando el túnel 3.");
            Thread.sleep(1000); // Atraviesa el túnel
            cerrojo3.lock();
            try{
                apocalipsis.esperarSiPausado();
                dentroTunel3.sacarh(humano);
                enTunel3--;
                log.escribir("El humano " + humano.gethumanoId() + " ha entrado en el refugio");
                zona_riesgo3.signal();
                refugio3.signal();
            }catch (Exception e){}
            finally{
                cerrojo3.unlock();
            }
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public void entrar_zona_riesgo4(Humano humano)throws InterruptedException {
        try {
            apocalipsis.esperarSiPausado();
            cerrojo4.lock();
            try{
                apocalipsis.esperarSiPausado();
                refugioARiesgo4++;
                while(riesgoARefugio4>0 || enTunel4>0){
                    apocalipsis.esperarSiPausado();
                    log.escribir("El humano " + humano.gethumanoId() + " está esperando a poder entrar en la zona de riesgo 4.");
                    refugio4.await();
                    apocalipsis.esperarSiPausado();
                }
                esperanRefugioARiesgo4.sacarh(humano);
                refugioARiesgo4--;
                enTunel4++;
                dentroTunel4.meterh(humano);
            } finally {
                cerrojo4.unlock();
            }
            // Hay dos cerrojo para que mientras un humano este pasando el túnel otros humanos puedan entrar en las zonas de espera
            apocalipsis.esperarSiPausado();
            log.escribir("El humano " + humano.gethumanoId() + " está atravesando el túnel 4.");
            Thread.sleep(1000); // Atraviesa el túnel
            cerrojo4.lock();
            try{
                apocalipsis.esperarSiPausado();
                dentroTunel4.sacarh(humano);
                enTunel4--;
                zonaRiesgo.entrar_humano(humano,4);
                log.escribir("El humano " + humano.gethumanoId() + " ha entrado en la zona de riesgo 4");
                zona_riesgo4.signal();
                refugio4.signal();
            }catch (Exception e){}
            finally {
                cerrojo4.unlock();
            }
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }
    public void entrar4_zona_descanso(Humano humano) throws InterruptedException {
        try {
            apocalipsis.esperarSiPausado();
            cerrojo4.lock();
            try{
                apocalipsis.esperarSiPausado();
                riesgoARefugio4++;
                esperanRiesgoARefugio4.meterh(humano);
                while(enTunel4>0){
                    apocalipsis.esperarSiPausado();
                    log.escribir("El humano " + humano.gethumanoId() + " está esperando a poder entrar en el túnel 4 para ir al refugio.");
                    zona_riesgo4.await();
                    apocalipsis.esperarSiPausado();
                }
                esperanRiesgoARefugio4.sacarh(humano);
                riesgoARefugio4--;
                enTunel4++;
                dentroTunel4.meterh(humano);
            } finally {cerrojo4.unlock();}
            // Hay dos cerrojo para que mientras un humano este pasando el túnel otros humanos puedan entrar en las zonas de espera
            apocalipsis.esperarSiPausado();
            log.escribir("El humano " + humano.gethumanoId() + " está atravesando el túnel 4.");
            Thread.sleep(1000); // Atraviesa el túnel
            cerrojo4.lock();
            try{
                apocalipsis.esperarSiPausado();
                dentroTunel4.sacarh(humano);
                enTunel4--;
                log.escribir("El humano " + humano.gethumanoId() + " ha entrado en el refugio");
                zona_riesgo4.signal();
                refugio4.signal();
            }catch (Exception e){}
            finally{
                cerrojo4.unlock();
            }
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }
    public void zona_espera_tunel1(Humano humano){
        try{
            apocalipsis.esperarSiPausado();
            esperanRefugioARiesgo1.meterh(humano);
            log.escribir("El humano " + humano.gethumanoId() + " está esperando en la barrera del túnel 1.");
            barrera1.await();
            apocalipsis.esperarSiPausado();
            log.escribir("El túnel 1 tiene ya 3 humanos.");
            entrar_zona_riesgo1(humano);
        }catch (Exception e){}
    }
    public void zona_espera_tunel2(Humano humano){
        try{
            apocalipsis.esperarSiPausado();
            esperanRefugioARiesgo2.meterh(humano);
            log.escribir("El humano " + humano.gethumanoId() + " está esperando en la barrera del túnel 2.");
            barrera2.await();
            apocalipsis.esperarSiPausado();
            log.escribir("El túnel 2 tiene ya 3 humanos.");
            entrar_zona_riesgo2(humano);
        }catch (Exception e){}
    }
    public void zona_espera_tunel3(Humano humano){
        try{
            apocalipsis.esperarSiPausado();
            esperanRefugioARiesgo3.meterh(humano);
            log.escribir("El humano " + humano.gethumanoId() + " está esperando en la barrera del túnel 3.");
            barrera3.await();
            apocalipsis.esperarSiPausado();
            log.escribir("El túnel 3 tiene ya 3 humanos.");
            entrar_zona_riesgo3(humano);
        }catch (Exception e){}
    }
    public void zona_espera_tunel4(Humano humano){
        try{
            apocalipsis.esperarSiPausado();
            esperanRefugioARiesgo4.meterh(humano);
            log.escribir("El humano " + humano.gethumanoId() + " está esperando en la barrera del túnel 4.");
            barrera4.await();
            apocalipsis.esperarSiPausado();
            log.escribir("El túnel 4 tiene ya 3 humanos.");
            entrar_zona_riesgo4(humano);
        }catch (Exception e){}
    }

    public void setZonaRiesgo(Zona_riesgo zonaRiesgo) {
        this.zonaRiesgo = zonaRiesgo;
    }

    public int getNumHumanos(int n){
        if (n==1){
            return (esperanRefugioARiesgo1.sizeh() + dentroTunel1.sizeh() + esperanRiesgoARefugio1.sizeh());
        } else if (n==2){
            return (esperanRefugioARiesgo2.sizeh() + dentroTunel2.sizeh() + esperanRiesgoARefugio2.sizeh());
        }
        else if (n==3){
            return (esperanRefugioARiesgo3.sizeh() + dentroTunel3.sizeh() + esperanRiesgoARefugio3.sizeh());
        }
        else {
            return (esperanRefugioARiesgo4.sizeh() + dentroTunel4.sizeh() + esperanRiesgoARefugio4.sizeh());
        }
    }

    public void setApocalipsis(InterfazApocalipsis apocalipsis) {
        this.apocalipsis = apocalipsis;
    }
}
