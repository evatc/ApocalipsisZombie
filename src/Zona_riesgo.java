import java.awt.*;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import javafx.scene.control.TextField;

public class Zona_riesgo {
    private ListaThreads riesgoHumanos1, riesgoHumanos2, riesgoHumanos3, riesgoHumanos4,
                            riesgoZombies1, riesgoZombies2, riesgoZombies3, riesgoZombies4;
    private int zona1;
    private int zona2;
    private int zona3;
    private int zona4;
    private List<Humano> lh1;
    private List<Humano> lh2;
    private List<Humano> lh3;
    private List<Humano> lh4;
    private List<Zombie> lz1;
    private List<Zombie> lz2;
    private List<Zombie> lz3;
    private List<Zombie> lz4;
    private Lock cerrojo1= new ReentrantLock();
    private Lock cerrojo2= new ReentrantLock();
    private Lock cerrojo3= new ReentrantLock();
    private Lock cerrojo4= new ReentrantLock();
    private Tunel tunel;

    public Zona_riesgo(TextField c1, TextField c2, TextField c3, TextField c4,
                       TextField c5, TextField c6, TextField c7, TextField c8) {
        riesgoHumanos1 = new ListaThreads(c1);
        riesgoHumanos2 = new ListaThreads(c2);
        riesgoHumanos3 = new ListaThreads(c3);
        riesgoHumanos4 = new ListaThreads(c4);
        riesgoZombies1 = new ListaThreads(c5);
        riesgoZombies2 = new ListaThreads(c6);
        riesgoZombies3 = new ListaThreads(c7);
        riesgoZombies4 = new ListaThreads(c8);
        this.zona1 = 1;
        this.zona2 = 2;
        this.zona3 = 3;
        this.zona4 = 4;
        this.lh1 = new ArrayList<>();
        this.lh2 = new ArrayList<>();
        this.lh3 = new ArrayList<>();
        this.lh4 = new ArrayList<>();
        this.lz1 = new ArrayList<>();
        this.lz2 = new ArrayList<>();
        this.lz3 = new ArrayList<>();
        this.lz4 = new ArrayList<>();
    }

    //public Zona_riesgo(javafx.scene.control.TextField txtRiesgoHumanos1, javafx.scene.control.TextField txtRiesgoHumanos2, javafx.scene.control.TextField txtRiesgoHumanos3, javafx.scene.control.TextField txtRiesgoHumanos4, javafx.scene.control.TextField txtRiesgoZombies1, javafx.scene.control.TextField txtRiesgoZombies2, javafx.scene.control.TextField txtRiesgoZombies3, javafx.scene.control.TextField txtRiesgoZombies4) {
    //}

    public void entrar_humano(Humano humano,int zona){ //esta función depende del tunel
        if(zona == 1){
            try {
                cerrojo1.lock();
                riesgoHumanos1.meterh(humano);
                lh1.add(humano);
                System.out.println("Lista humanos1: " + lh1);
            }finally {
                cerrojo1.unlock();
            }
        }else if(zona == 2){
            try {
                cerrojo2.lock();
                riesgoHumanos2.meterh(humano);
                lh2.add(humano);
                System.out.println("Lista humanos2: " + lh2);
            }finally {
                cerrojo2.unlock();
            }
        }else if(zona == 3){
            try {
                cerrojo3.lock();
                riesgoHumanos3.meterh(humano);
                lh3.add(humano);
                System.out.println("Lista humanos3: " + lh3);
            }finally {
                cerrojo3.unlock();
            }
        }else{
            try {
                cerrojo4.lock();
                riesgoHumanos4.meterh(humano);
                lh4.add(humano);
                System.out.println("Lista humanos4: " + lh4);
            }finally {
                cerrojo4.unlock();
            }
        }
    }
    public void entrar_zombie(Zombie zombie, int zona){
        if(zona == 1){
            riesgoZombies1.meterz(zombie);
            lz1.add(zombie);
            System.out.println("El zombie " + zombie.getzombieId() + " ha entrado a la zona 1");
        }else if(zona == 2){
            riesgoZombies2.meterz(zombie);
            lz2.add(zombie);
            System.out.println("El zombie " + zombie.getzombieId() + " ha entrado a la zona 2");
        }else if(zona == 3){
            riesgoZombies3.meterz(zombie);
            lz3.add(zombie);
            System.out.println("El zombie " + zombie.getzombieId() + " ha entrado a la zona 3");
        }else{
            riesgoZombies4.meterz(zombie);
            lz4.add(zombie);
            System.out.println("El zombie " + zombie.getzombieId() + " ha entrado a la zona 4");
        }
    }
    public void salir_humano(Humano humano, int zona){
        if(zona == 1){
            riesgoHumanos1.sacarh(humano);
            System.out.println("El humano " + humano.gethumanoId() + "ha salido de la zona de riesgo 1");
            lh1.remove(humano);
        }else if(zona == 2){
            riesgoHumanos2.sacarh(humano);
            System.out.println("El humano " + humano.gethumanoId() + "ha salido de la zona de riesgo 2");
            lh2.remove(humano);
        }else if(zona == 3){
            riesgoHumanos3.sacarh(humano);
            System.out.println("El humano " + humano.gethumanoId() + "ha salido de la zona de riesgo 3");
            lh3.remove(humano);
        }else{
            riesgoHumanos4.sacarh(humano);
            System.out.println("El humano " + humano.gethumanoId() + "ha salido de la zona de riesgo 4");
            lh4.remove(humano);
        }
    }
    public void salir_zombie(Zombie zombie, int zona){
        if(zona == 1){
            riesgoZombies1.sacarz(zombie);
            lz1.remove(zombie);
            System.out.println("El zombie " + zombie.getzombieId() + " ha salido de la zona 1");
        }else if(zona == 2){
            riesgoZombies2.sacarz(zombie);
            lz2.remove(zombie);
            System.out.println("El zombie " + zombie.getzombieId() + " ha salido de la zona 2");
        }else if(zona == 3){
            riesgoZombies3.sacarz(zombie);
            lz3.remove(zombie);
            System.out.println("El zombie " + zombie.getzombieId() + " ha salido de la zona 3");
        }else{
            riesgoZombies4.sacarz(zombie);
            lz4.remove(zombie);
            System.out.println("El zombie " + zombie.getzombieId() + " ha salido de la zona 4");
        }
    }
    public boolean buscar_humano(List humanos){
        boolean b = true;
        if(humanos.isEmpty()){
            b = false;
        }
        return b;
    }

    public void ataque(Zombie zombie, int zona){
        if(zona == 1){
            cerrojo1.lock();
            System.out.println("Lista humanos1: " + lh1);
            System.out.println("Lista humanos2: " + lh2);
            System.out.println("Lista humanos3: " + lh3);
            System.out.println("Lista humanos4: " + lh4);
            System.out.println("Lista zombi1: " + lz1);
            System.out.println("Lista humanos2: " + lz2);
            System.out.println("Lista humanos3: " + lz3);
            System.out.println("Lista humanos4: " + lz4);
            try{
                if(buscar_humano(lh1)){
                    Random random = new Random();
                    int posicion = random.nextInt(lh1.size());
                    Humano humano = lh1.get(posicion);
                    double tiempo2 = (0.5 + Math.random())*1000;
                    System.out.println("El ataque entre el zombie " + zombie.getzombieId() + " y el humano " + humano.gethumanoId() + " se está produciendo");
                    try{
                        Thread.sleep((long) tiempo2);
                    }catch(Exception e){}
                    int probabilidad = random.nextInt(3);
                    if(probabilidad == 0){
                        lh1.remove(humano);
                        humano.setVivo(false);
                        int muertes = zombie.getCont_muertes();
                        zombie.setCont_muertes(muertes + 1);
                        String humanoId = humano.gethumanoId();
                        String zombieId = "Z" + humanoId.substring(1);
                        Zombie zombie1 = new Zombie(zombieId, this);
                        zombie1.start();
                    }else{
                        humano.setHerido(true);
                        tunel.entrar1_zona_descanso(humano);
                        List<Humano> humanos = tunel.getLzr1();
                        humanos.add(humano);
                        tunel.setLzr1(humanos);
                    }
                }
            }catch(Exception e){}
            finally {
                cerrojo1.unlock();
            }
        }else if(zona == 2){
            cerrojo2.lock();
            System.out.println("Lista humanos1: " + lh1);
            System.out.println("Lista humanos2: " + lh2);
            System.out.println("Lista humanos3: " + lh3);
            System.out.println("Lista humanos4: " + lh4);
            try{
                if(buscar_humano(lh2)){
                    Random random = new Random();
                    int posicion = random.nextInt(lh2.size());
                    Humano humano = lh2.get(posicion);
                    double tiempo2 = (0.5 + Math.random())*1000;
                    System.out.println("El ataque entre el zombie " + zombie.getzombieId() + " y el humano " + humano.gethumanoId() + " se está produciendo");
                    try{
                        Thread.sleep((long) tiempo2);
                    }catch(Exception e){}
                    int probabilidad = random.nextInt(3);
                    if(probabilidad == 0){
                        lh2.remove(humano);
                        humano.setVivo(false);
                        int muertes = zombie.getCont_muertes();
                        zombie.setCont_muertes(muertes + 1);
                        String humanoId = humano.gethumanoId();
                        String zombieId = "Z" + humanoId.substring(1);
                        Zombie zombie1 = new Zombie(zombieId, this);
                        zombie1.start();
                    }else{
                        humano.setHerido(true);
                        tunel.entrar2_zona_descanso(humano);
                        List<Humano> humanos = tunel.getLzr2();
                        humanos.add(humano);
                        tunel.setLzr2(humanos);
                    }
                }
            }catch(Exception e){}
            finally {
                cerrojo2.unlock();
            }
        }else if(zona == 3){
            cerrojo3.lock();
            System.out.println("Lista humanos1: " + lh1);
            System.out.println("Lista humanos2: " + lh2);
            System.out.println("Lista humanos3: " + lh3);
            System.out.println("Lista humanos4: " + lh4);
            try{
                if(buscar_humano(lh3)){
                    Random random = new Random();
                    int posicion = random.nextInt(lh3.size());
                    Humano humano = lh3.get(posicion);
                    double tiempo2 = (0.5 + Math.random())*1000;
                    System.out.println("El ataque entre el zombie " + zombie.getzombieId() + " y el humano " + humano.gethumanoId() + " se está produciendo");
                    try{
                        Thread.sleep((long) tiempo2);
                    }catch(Exception e){}
                    int probabilidad = random.nextInt(3);
                    if(probabilidad == 0){
                        lh3.remove(humano);
                        humano.setVivo(false);
                        int muertes = zombie.getCont_muertes();
                        zombie.setCont_muertes(muertes + 1);
                        String humanoId = humano.gethumanoId();
                        String zombieId = "Z" + humanoId.substring(1);
                        Zombie zombie1 = new Zombie(zombieId, this);
                        zombie1.start();
                    }else{
                        humano.setHerido(true);
                        tunel.entrar3_zona_descanso(humano);
                        List<Humano> humanos = tunel.getLzr3();
                        humanos.add(humano);
                        tunel.setLzr3(humanos);
                    }
                }
            }catch(Exception e){}
            finally {
                cerrojo3.unlock();
            }
        }else{
            cerrojo4.lock();
            System.out.println("Lista humanos1: " + lh1);
            System.out.println("Lista humanos2: " + lh2);
            System.out.println("Lista humanos3: " + lh3);
            System.out.println("Lista humanos4: " + lh4);
            try{
                if(buscar_humano(lh4)){
                    Random random = new Random();
                    int posicion = random.nextInt(lh4.size());
                    Humano humano = lh4.get(posicion);
                    double tiempo2 = (0.5 + Math.random())*1000;
                    System.out.println("El ataque entre el zombie " + zombie.getzombieId() + " y el humano " + humano.gethumanoId() + " se está produciendo");
                    try{
                        Thread.sleep((long) tiempo2);
                    }catch(Exception e){}
                    int probabilidad = random.nextInt(3);
                    if(probabilidad == 0){
                        lh4.remove(humano);
                        humano.setVivo(false);
                        int muertes = zombie.getCont_muertes();
                        zombie.setCont_muertes(muertes + 1);
                        String humanoId = humano.gethumanoId();
                        String zombieId = "Z" + humanoId.substring(1);
                        Zombie zombie1 = new Zombie(zombieId, this);
                        zombie1.start();
                    }else{
                        humano.setHerido(true);
                        tunel.entrar4_zona_descanso(humano);
                        List<Humano> humanos = tunel.getLzr4();
                        humanos.add(humano);
                        tunel.setLzr4(humanos);
                    }
                }
            }catch(Exception e){}
            finally {
                cerrojo4.unlock();
            }
        }
    }

    public List<Humano> getLh1() {
        return lh1;
    }

    public void setLh1(List<Humano> lh1) {
        this.lh1 = lh1;
    }

    public List<Humano> getLh2() {
        return lh2;
    }

    public void setLh2(List<Humano> lh2) {
        this.lh2 = lh2;
    }

    public List<Humano> getLh3() {
        return lh3;
    }

    public void setLh3(List<Humano> lh3) {
        this.lh3 = lh3;
    }

    public List<Humano> getLh4() {
        return lh4;
    }

    public void setLh4(List<Humano> lh4) {
        this.lh4 = lh4;
    }
    public void setTunel(Tunel tunel) {
        this.tunel = tunel;
    }

    public Tunel getTunel() {
        return tunel;
    }
}
