import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import javafx.scene.control.TextField;

public class Tunel {
    private ListaThreads esperanRefugioARiesgo1, dentroTunel1, esperanRiesgoARefugio1;
    private ListaThreads esperanRefugioARiesgo2, dentroTunel2, esperanRiesgoARefugio2;
    private ListaThreads esperanRefugioARiesgo3, dentroTunel3, esperanRiesgoARefugio3;
    private ListaThreads esperanRefugioARiesgo4, dentroTunel4, esperanRiesgoARefugio4;
    private Zona_riesgo zonaRiesgo /*= new Zona_riesgo()*/;
    private int tunel1;
    private int tunel2;
    private int tunel3;
    private int tunel4;
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

    private List<Humano> lzr1 = new ArrayList<>();
    private List<Humano> lzr2 = new ArrayList<>();
    private List<Humano> lzr3 = new ArrayList<>();
    private List<Humano> lzr4 = new ArrayList<>();
    private List<Humano> lr1 = new ArrayList<>();
    private List<Humano> lr2 = new ArrayList<>();
    private List<Humano> lr3 = new ArrayList<>();
    private List<Humano> lr4 = new ArrayList<>();
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

    public Tunel(TextField c11, TextField c21, TextField c31, TextField c12, TextField c22, TextField c32, TextField c13, TextField c23, TextField c33, TextField c14, TextField c24, TextField c34) {
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
        this.tunel1 = 1;
        this.tunel2 = 2;
        this.tunel3 = 3;
        this.tunel4 = 4;
    }

    //public Tunel(javafx.scene.control.TextField txtRefugioARiesgo1, javafx.scene.control.TextField txtTunel1, javafx.scene.control.TextField txtRiesgoARefugio1, javafx.scene.control.TextField txtRefugioARiesgo2, javafx.scene.control.TextField txtTunel2, javafx.scene.control.TextField txtRiesgoARefugio2, javafx.scene.control.TextField txtRefugioARiesgo3, javafx.scene.control.TextField txtTunel3, javafx.scene.control.TextField txtRiesgoARefugio3, javafx.scene.control.TextField txtRefugioARiesgo4, javafx.scene.control.TextField txtTunel4, javafx.scene.control.TextField txtRiesgoARefugio4) {
    //}

    public void entrar_zona_riesgo1(Humano humano) throws InterruptedException {
        cerrojo1.lock();
        try{
            refugioARiesgo1++;
            while(riesgoARefugio1>0 || enTunel1>0){
                refugio1.await();
            }
            esperanRefugioARiesgo1.sacarh(humano);
            refugioARiesgo1--;
            enTunel1++;
            dentroTunel1.meterh(humano);
        }finally {cerrojo1.unlock();
        }
        // Hay dos cerrojo para que mientras un humano este pasando el túnel
        // otros humanos puedan entrar en las zonas de espera
        Thread.sleep(1000); // Atraviesa el túnel
        cerrojo1.lock();
        try{
            dentroTunel1.sacarh(humano);
            enTunel1--;
            zonaRiesgo.entrar_humano(humano,1);
            System.out.println("El humano " + humano.gethumanoId() + " ha entrado en la zona de riesgo 1");
            zona_riesgo1.signal();
            refugio1.signal();
        }catch (Exception e){}
        finally {
            cerrojo1.unlock();
        }
    }
    public void entrar1_zona_descanso(Humano humano) throws InterruptedException {
        cerrojo1.lock();
        try{
            riesgoARefugio1++;
            esperanRiesgoARefugio1.meterh(humano);
            while(enTunel1>0){
                zona_riesgo1.await();
            }
            esperanRiesgoARefugio1.sacarh(humano);
            riesgoARefugio1--;
            enTunel1++;
            dentroTunel1.meterh(humano);
        }finally {cerrojo1.unlock();}
        // Hay dos cerrojo para que mientras un humano este pasando el túnel
        // otros humanos puedan entrar en las zonas de espera
        Thread.sleep(1000); // Atraviesa el túnel
        cerrojo1.lock();
        try{
            dentroTunel1.sacarh(humano);
            enTunel1--;
            System.out.println("El humano " + humano.gethumanoId() + " ha entrado en el refugio");
            zona_riesgo1.signal();
            refugio1.signal();
        }catch (Exception e){}
        finally{
            cerrojo1.unlock();
        }
    }

    public void entrar_zona_riesgo2(Humano humano) throws InterruptedException {
        cerrojo2.lock();
        try{
            refugioARiesgo2++;
            while(riesgoARefugio2>0 || enTunel2>0){
                refugio2.await();
            }
            esperanRefugioARiesgo2.sacarh(humano);
            refugioARiesgo2--;
            enTunel2++;
            dentroTunel2.meterh(humano);
        }finally {cerrojo2.unlock();
        }
        // Hay dos cerrojo para que mientras un humano este pasando el túnel
        // otros humanos puedan entrar en las zonas de espera
        Thread.sleep(1000); // Atraviesa el túnel
        cerrojo2.lock();
        try{
            dentroTunel2.sacarh(humano);
            enTunel2--;
            zonaRiesgo.entrar_humano(humano,2);
            System.out.println("El humano " + humano.gethumanoId() + " ha entrado en la zona de riesgo 1");
            zona_riesgo2.signal();
            refugio2.signal();
        }catch (Exception e){}
        finally {
            cerrojo2.unlock();
        }
    }
    public void entrar2_zona_descanso(Humano humano) throws InterruptedException {
        cerrojo2.lock();
        try{
            riesgoARefugio2++;
            esperanRiesgoARefugio2.meterh(humano);
            while(enTunel2>0){
                zona_riesgo2.await();
            }
            esperanRiesgoARefugio2.sacarh(humano);
            riesgoARefugio2--;
            enTunel2++;
            dentroTunel2.meterh(humano);
        }finally {cerrojo2.unlock();}
        // Hay dos cerrojo para que mientras un humano este pasando el túnel
        // otros humanos puedan entrar en las zonas de espera
        Thread.sleep(1000); // Atraviesa el túnel
        cerrojo2.lock();
        try{
            dentroTunel2.sacarh(humano);
            enTunel2--;
            System.out.println("El humano " + humano.gethumanoId() + " ha entrado en el refugio");
            zona_riesgo2.signal();
            refugio2.signal();
        }catch (Exception e){}
        finally{
            cerrojo2.unlock();
        }
    }

    public void entrar_zona_riesgo3(Humano humano)throws InterruptedException {
        cerrojo3.lock();
        try{
            refugioARiesgo3++;
            while(riesgoARefugio3>0 || enTunel3>0){
                refugio3.await();
            }
            esperanRefugioARiesgo3.sacarh(humano);
            refugioARiesgo3--;
            enTunel3++;
            dentroTunel3.meterh(humano);
        }finally {cerrojo3.unlock();
        }
        // Hay dos cerrojo para que mientras un humano este pasando el túnel
        // otros humanos puedan entrar en las zonas de espera
        Thread.sleep(1000); // Atraviesa el túnel
        cerrojo3.lock();
        try{
            dentroTunel3.sacarh(humano);
            enTunel3--;
            zonaRiesgo.entrar_humano(humano,3);
            System.out.println("El humano " + humano.gethumanoId() + " ha entrado en la zona de riesgo 1");
            zona_riesgo3.signal();
            refugio3.signal();
        }catch (Exception e){}
        finally {
            cerrojo3.unlock();
        }
    }
    public void entrar3_zona_descanso(Humano humano) throws InterruptedException {
        cerrojo3.lock();
        try{
            riesgoARefugio3++;
            esperanRiesgoARefugio3.meterh(humano);
            while(enTunel3>0){
                zona_riesgo3.await();
            }
            esperanRiesgoARefugio3.sacarh(humano);
            riesgoARefugio3--;
            enTunel3++;
            dentroTunel3.meterh(humano);
        }finally {cerrojo3.unlock();}
        // Hay dos cerrojo para que mientras un humano este pasando el túnel
        // otros humanos puedan entrar en las zonas de espera
        Thread.sleep(1000); // Atraviesa el túnel
        cerrojo3.lock();
        try{
            dentroTunel3.sacarh(humano);
            enTunel3--;
            System.out.println("El humano " + humano.gethumanoId() + " ha entrado en el refugio");
            zona_riesgo3.signal();
            refugio3.signal();
        }catch (Exception e){}
        finally{
            cerrojo3.unlock();
        }
    }

    public void entrar_zona_riesgo4(Humano humano)throws InterruptedException {
        cerrojo4.lock();
        try{
            refugioARiesgo4++;
            while(riesgoARefugio4>0 || enTunel4>0){
                refugio4.await();
            }
            esperanRefugioARiesgo4.sacarh(humano);
            refugioARiesgo4--;
            enTunel4++;
            dentroTunel4.meterh(humano);
        }finally {cerrojo4.unlock();
        }
        // Hay dos cerrojo para que mientras un humano este pasando el túnel
        // otros humanos puedan entrar en las zonas de espera
        Thread.sleep(1000); // Atraviesa el túnel
        cerrojo4.lock();
        try{
            dentroTunel4.sacarh(humano);
            enTunel4--;
            zonaRiesgo.entrar_humano(humano,4);
            System.out.println("El humano " + humano.gethumanoId() + " ha entrado en la zona de riesgo 1");
            zona_riesgo4.signal();
            refugio4.signal();
        }catch (Exception e){}
        finally {
            cerrojo4.unlock();
        }
    }
    public void entrar4_zona_descanso(Humano humano) throws InterruptedException {
        cerrojo4.lock();
        try{
            riesgoARefugio4++;
            esperanRiesgoARefugio4.meterh(humano);
            while(enTunel4>0){
                zona_riesgo4.await();
            }
            esperanRiesgoARefugio4.sacarh(humano);
            riesgoARefugio4--;
            enTunel4++;
            dentroTunel4.meterh(humano);
        }finally {cerrojo4.unlock();}
        // Hay dos cerrojo para que mientras un humano este pasando el túnel
        // otros humanos puedan entrar en las zonas de espera
        Thread.sleep(1000); // Atraviesa el túnel
        cerrojo4.lock();
        try{
            dentroTunel4.sacarh(humano);
            enTunel4--;
            System.out.println("El humano " + humano.gethumanoId() + " ha entrado en el refugio");
            zona_riesgo4.signal();
            refugio4.signal();
        }catch (Exception e){}
        finally{
            cerrojo4.unlock();
        }
    }
    public void zona_espera_tunel1(Humano humano){
        try{
            esperanRefugioARiesgo1.meterh(humano);
            System.out.println("El humano " + humano.gethumanoId() + " está esperando en la barrera del túnel 1.");
            barrera1.await();
            System.out.println("El túnel 1 tiene ya 3 humanos.");
            entrar_zona_riesgo1(humano);
        }catch (Exception e){}
    }
    public void zona_espera_tunel2(Humano humano){
        try{
            esperanRefugioARiesgo2.meterh(humano);
            System.out.println("El humano " + humano.gethumanoId() + " está esperando en la barrera del túnel 2.");
            barrera2.await();
            System.out.println("El túnel 2 tiene ya 3 humanos.");
            entrar_zona_riesgo2(humano);
        }catch (Exception e){}
    }
    public void zona_espera_tunel3(Humano humano){
        try{
            esperanRefugioARiesgo3.meterh(humano);
            System.out.println("El humano " + humano.gethumanoId() + " está esperando en la barrera del túnel 3.");
            barrera3.await();
            System.out.println("El túnel 3 tiene ya 3 humanos.");
            entrar_zona_riesgo3(humano);
        }catch (Exception e){}
    }
    public void zona_espera_tunel4(Humano humano){
        try{
            esperanRefugioARiesgo4.meterh(humano);
            System.out.println("El humano " + humano.gethumanoId() + " está esperando en la barrera del túnel 4.");
            barrera4.await();
            System.out.println("El túnel 4 tiene ya 3 humanos.");
            entrar_zona_riesgo4(humano);
        }catch (Exception e){}
    }


    public List<Humano> getLzr1() {
        return lzr1;
    }

    public void setLzr1(List lzr1) {
        this.lzr1 = lzr1;
    }

    public List<Humano> getLzr2() {
        return lzr2;
    }

    public void setLzr2(List lzr2) {
        this.lzr2 = lzr2;
    }

    public List<Humano> getLzr3() {
        return lzr3;
    }

    public void setLzr3(List lzr3) {
        this.lzr3 = lzr3;
    }

    public List<Humano> getLzr4() {
        return lzr4;
    }

    public void setLzr4(List lzr4) {
        this.lzr4 = lzr4;
    }

    public List<Humano> getLr1() {
        return lr1;
    }

    public void setLr1(List lr1) {
        this.lr1 = lr1;
    }

    public List<Humano> getLr2() {
        return lr2;
    }

    public void setLr2(List lr2) {
        this.lr2 = lr2;
    }

    public List<Humano> getLr3() {
        return lr3;
    }

    public void setLr3(List lr3) {
        this.lr3 = lr3;
    }

    public List<Humano> getLr4() {
        return lr4;
    }

    public void setLr4(List lr4) {
        this.lr4 = lr4;
    }

    public void setZonaRiesgo(Zona_riesgo zonaRiesgo) {
        this.zonaRiesgo = zonaRiesgo;
    }

    public Zona_riesgo getZonaRiesgo() {
        return zonaRiesgo;
    }

    public ListaThreads getEsperanRiesgoARefugio1() {
        return esperanRiesgoARefugio1;
    }

    public ListaThreads getEsperanRiesgoARefugio2() {
        return esperanRiesgoARefugio2;
    }

    public ListaThreads getEsperanRiesgoARefugio3() {
        return esperanRiesgoARefugio3;
    }

    public ListaThreads getEsperanRiesgoARefugio4() {
        return esperanRiesgoARefugio4;
    }
}
