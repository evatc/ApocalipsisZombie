import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Tunel {
    private Zona_riesgo zonaRiesgo;
    private int tunel1;
    private int tunel2;
    private int tunel3;
    private int tunel4;
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

    public Tunel() {
        this.tunel1 = 1;
        this.tunel2 = 2;
        this.tunel3 = 3;
        this.tunel4 = 4;
    }

    public void entrar_zona_riesgo1(String id){
        cerrojo1.lock();
        try{
            while(!lzr1.isEmpty()){
                refugio1.await();
            }
            List<Humano> humanos_entrarzr = zonaRiesgo.getLh1();
            humanos_entrarzr.add(lr1.getFirst());
            zonaRiesgo.setLh1(humanos_entrarzr);
            lr1.removeFirst();
            System.out.println("El humano " + id + " ha entrado en la zona de riesgo 1");
            zona_riesgo1.signal();
        }catch (Exception e){}
        finally {
            cerrojo1.unlock();
        }
    }
    public void entrar1_zona_descanso(String id){
        cerrojo1.lock();
        try{
            while(lzr1.isEmpty()){
                zona_riesgo1.await();
            }
            System.out.println("El humano " + id + " ha entrado en el refugio");
            refugio1.signal();
        }catch (Exception e){}
        finally{
            cerrojo1.unlock();
        }
    }

    public void entrar_zona_riesgo2(String id){
        cerrojo2.lock();
        try{
            while(!lzr2.isEmpty()){
                refugio2.await();
            }
            List<Humano> humanos_entrarzr = zonaRiesgo.getLh2();
            humanos_entrarzr.add(lr2.getFirst());
            zonaRiesgo.setLh2(humanos_entrarzr);
            lr2.removeFirst();
            System.out.println("El humano " + id + " ha entrado en la zona de riesgo 2");
            zona_riesgo2.signal();
        }catch (Exception e){}
        finally {
            cerrojo2.unlock();
        }

    }
    public void entrar2_zona_descanso(String id){
        cerrojo2.lock();
        try{
            while(lzr2.isEmpty()){
                zona_riesgo2.await();
            }
            System.out.println("El humano " + id + " ha entrado en el refugio");
            refugio2.signal();
        }catch (Exception e){}
        finally{
            cerrojo2.unlock();
        }
    }

    public void entrar_zona_riesgo3(String id){
        cerrojo3.lock();
        try{
            while(!lzr3.isEmpty()){
                refugio3.await();
            }
            List<Humano> humanos_entrarzr = zonaRiesgo.getLh3();
            humanos_entrarzr.add(lr3.getFirst());
            zonaRiesgo.setLh3(humanos_entrarzr);
            lr3.removeFirst();
            System.out.println("El humano " + id + " ha entrado en la zona de riesgo 3");
            zona_riesgo3.signal();
        }catch (Exception e){}
        finally {
            cerrojo3.unlock();
        }
    }
    public void entrar3_zona_descanso(String id){
        cerrojo3.lock();
        try{
            while(lzr3.isEmpty()){
                zona_riesgo3.await();
            }
            System.out.println("El humano " + id + " ha entrado en el refugio");
            refugio3.signal();
        }catch (Exception e){}
        finally{
            cerrojo3.unlock();
        }
    }

    public void entrar_zona_riesgo4(String id){
        cerrojo4.lock();
        try{
            while(!lzr4.isEmpty()){
                refugio4.await();
            }
            List<Humano> humanos_entrarzr = zonaRiesgo.getLh4();
            humanos_entrarzr.add(lr4.getFirst());
            zonaRiesgo.setLh4(humanos_entrarzr);
            lr4.removeFirst();
            System.out.println("El humano " + id + " ha entrado en la zona de riesgo 4");
            zona_riesgo4.signal();
        }catch (Exception e){}
        finally {
            cerrojo4.unlock();
        }
    }
    public void entrar4_zona_descanso(String id){
        cerrojo4.lock();
        try{
            while(lzr4.isEmpty()){
                zona_riesgo4.await();
            }
            System.out.println("El humano " + id + " ha entrado en el refugio");
            refugio4.signal();
        }catch (Exception e){}
        finally{
            cerrojo4.unlock();
        }
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
}
