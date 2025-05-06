import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javafx.scene.control.TextField;

import static java.lang.Thread.sleep;

public class Zona_riesgo {
    private ListaThreads riesgoHumanos1, riesgoHumanos2, riesgoHumanos3, riesgoHumanos4,
                            riesgoZombies1, riesgoZombies2, riesgoZombies3, riesgoZombies4;
    private ConcurrentLinkedQueue<Humano> lh1;
    private ConcurrentLinkedQueue<Humano> lh2;
    private ConcurrentLinkedQueue<Humano> lh3;
    private ConcurrentLinkedQueue<Humano> lh4;
    private ConcurrentLinkedQueue<Zombie> lz1;
    private ConcurrentLinkedQueue<Zombie> lz2;
    private ConcurrentLinkedQueue<Zombie> lz3;
    private ConcurrentLinkedQueue<Zombie> lz4;
    private Lock cerrojo1= new ReentrantLock();
    private Lock cerrojo2= new ReentrantLock();
    private Lock cerrojo3= new ReentrantLock();
    private Lock cerrojo4= new ReentrantLock();
    private Tunel tunel;
    private Semaphore lock1;
    private Semaphore lock2;
    private Semaphore lock3;
    private Semaphore lock4;
    private Semaphore tiempo_ataque1;
    private Semaphore tiempo_ataque2;
    private Semaphore tiempo_ataque3;
    private Semaphore tiempo_ataque4;

    public Zona_riesgo(TextField c1, TextField c2, TextField c3, TextField c4,
                       TextField c5, TextField c6, TextField c7, TextField c8,
                       Semaphore tiempo_ataque, Semaphore tiempo_ataque2,
                       Semaphore tiempo_ataque3, Semaphore tiempo_ataque4) {
        riesgoHumanos1 = new ListaThreads(c1);
        riesgoHumanos2 = new ListaThreads(c2);
        riesgoHumanos3 = new ListaThreads(c3);
        riesgoHumanos4 = new ListaThreads(c4);
        riesgoZombies1 = new ListaThreads(c5);
        riesgoZombies2 = new ListaThreads(c6);
        riesgoZombies3 = new ListaThreads(c7);
        riesgoZombies4 = new ListaThreads(c8);
        this.lh1 = new ConcurrentLinkedQueue<>();
        this.lh2 = new ConcurrentLinkedQueue<>();
        this.lh3 = new ConcurrentLinkedQueue<>();
        this.lh4 = new ConcurrentLinkedQueue<>();
        this.lz1 = new ConcurrentLinkedQueue<>();
        this.lz2 = new ConcurrentLinkedQueue<>();
        this.lz3 = new ConcurrentLinkedQueue<>();
        this.lz4 = new ConcurrentLinkedQueue<>();
        this.lock1 = new Semaphore(1);
        this.lock2 = new Semaphore(1);
        this.lock3 = new Semaphore(1);
        this.lock4 = new Semaphore(1);
        this.tiempo_ataque1 = tiempo_ataque;
        this.tiempo_ataque2 = tiempo_ataque2;
        this.tiempo_ataque3 = tiempo_ataque3;
        this.tiempo_ataque4 = tiempo_ataque4;
    }

    public void entrar_humano(Humano humano,int zona){ //esta función depende del tunel
        if(zona == 1){
            try {
               // cerrojo1.lock();
                riesgoHumanos1.meterh(humano);
                lh1.add(humano);
            }finally {
                //cerrojo1.unlock();
            }
        }else if(zona == 2){
            try {
                //cerrojo2.lock();
                riesgoHumanos2.meterh(humano);
                lh2.add(humano);
            }finally {
                //cerrojo2.unlock();
            }
        }else if(zona == 3){
            try {
                //cerrojo3.lock();
                riesgoHumanos3.meterh(humano);
                lh3.add(humano);
            }finally {
                //cerrojo3.unlock();
            }
        }else{
            try {
                //cerrojo4.lock();
                riesgoHumanos4.meterh(humano);
                lh4.add(humano);
            }finally {
                //cerrojo4.unlock();
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
            lh1.remove(humano);
            System.out.println("El humano " + humano.gethumanoId() + " ha salido de la zona de riesgo 1");

        }else if(zona == 2){
            riesgoHumanos2.sacarh(humano);
            lh2.remove(humano);
            System.out.println("El humano " + humano.gethumanoId() + " ha salido de la zona de riesgo 2");

        }else if(zona == 3){
            riesgoHumanos3.sacarh(humano);
            lh3.remove(humano);
            System.out.println("El humano " + humano.gethumanoId() + " ha salido de la zona de riesgo 3");

        }else{
            riesgoHumanos4.sacarh(humano);
            lh4.remove(humano);
            System.out.println("El humano " + humano.gethumanoId() + " ha salido de la zona de riesgo 4");

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

    public void ataque(Zombie zombie, int zona){
        try{
            if (zona == 1){
                cerrojo1.lock();
                try{
                    if (!lh1.isEmpty()){
                        lock1.acquire();
                        Humano humano = seleccionarHumano(lh1);
                        if (humano!=null){
                            humano.setAtaque(true);
                            lock1.release();
                            tiempo_ataque1.acquire();
                            System.out.println("El ataque entre el zombie " + zombie.getzombieId() + " y el humano " + humano.gethumanoId() + " se está produciendo");
                            int tiempoAtaque = (int)(Math.random()*1000)+500; // Entre 0,5 y 1,5 segundos
                            sleep(tiempoAtaque);
                            int probabilidad = (int)(Math.random()*3);
                            if (probabilidad == 0){
                                // Humano muere
                                humano.setVivo(false);
                                lh1.remove(humano);
                                riesgoHumanos1.sacarh(humano);
                                int muertes = zombie.getCont_muertes();
                                zombie.setCont_muertes(muertes+1);
                                System.out.println("El humano " + humano.gethumanoId() + " ha sido matado por el zombie " + zombie.getzombieId() + ".");
                                System.out.println("Número de muertes de " + zombie.getzombieId()+ ": " + zombie.getCont_muertes() + ".");
                                // Convertimos el humano a zombie
                                String humanoId = humano.gethumanoId();
                                String zombieId = "Z" + humanoId.substring(1);
                                Zombie zombie1 = new Zombie(zombieId, this);
                                System.out.println("El humano " + humano.gethumanoId() + " se ha convertido en zombie");
                                zombie1.setConvertido(true);
                                zombie1.setN_zonaRiesgo(1);
                                zombie1.start();
                            } else{
                                // El humano está herido
                                humano.setHerido(true);
                                System.out.println("El humano " + humano.gethumanoId() + " ha sido herido");
                            }
                            humano.setAtaque(false);
                            tiempo_ataque1.release();
                        } else {
                            lock1.release();
                        }
                    }
                } finally {
                    cerrojo1.unlock();
                }
            } else if (zona == 2){
                cerrojo2.lock();
                try{
                    if (!lh2.isEmpty()){
                        lock2.acquire();
                        Humano humano = seleccionarHumano(lh2);
                        if (humano!=null){
                            humano.setAtaque(true);
                            lock2.release();
                            tiempo_ataque2.acquire();
                            System.out.println("El ataque entre el zombie " + zombie.getzombieId() + " y el humano " + humano.gethumanoId() + " se está produciendo");
                            int tiempoAtaque = (int)(Math.random()*1000)+500; // Entre 0,5 y 1,5 segundos
                            sleep(tiempoAtaque);
                            int probabilidad = (int)(Math.random()*3);
                            if (probabilidad == 0){
                                // Humano muere
                                humano.setVivo(false);
                                lh2.remove(humano);
                                riesgoHumanos2.sacarh(humano);
                                int muertes = zombie.getCont_muertes();
                                zombie.setCont_muertes(muertes+1);
                                System.out.println("El humano " + humano.gethumanoId() + " ha sido matado por el zombie " + zombie.getzombieId() + ".");
                                System.out.println("Número de muertes de " + zombie.getzombieId()+ ": " + zombie.getCont_muertes() + ".");
                                // Convertimos el humano a zombie
                                String humanoId = humano.gethumanoId();
                                String zombieId = "Z" + humanoId.substring(1);
                                Zombie zombie1 = new Zombie(zombieId, this);
                                System.out.println("El humano " + humano.gethumanoId() + " se ha convertido en zombie");
                                zombie1.setConvertido(true);
                                zombie1.setN_zonaRiesgo(2);
                                zombie1.start();
                            } else{
                                // El humano está herido
                                humano.setHerido(true);
                                System.out.println("El humano " + humano.gethumanoId() + " ha sido herido");
                            }
                            humano.setAtaque(false);
                            tiempo_ataque2.release();
                        } else {
                            lock2.release();
                        }
                    }
                } finally {
                    cerrojo2.unlock();
                }
            } if (zona == 3){
                cerrojo3.lock();
                try{
                    if (!lh3.isEmpty()){
                        lock3.acquire();
                        Humano humano = seleccionarHumano(lh3);
                        if (humano!=null){
                            humano.setAtaque(true);
                            lock3.release();
                            tiempo_ataque3.acquire();
                            System.out.println("El ataque entre el zombie " + zombie.getzombieId() + " y el humano " + humano.gethumanoId() + " se está produciendo");
                            int tiempoAtaque = (int)(Math.random()*1000)+500; // Entre 0,5 y 1,5 segundos
                            sleep(tiempoAtaque);
                            int probabilidad = (int)(Math.random()*3);
                            if (probabilidad == 0){
                                // Humano muere
                                humano.setVivo(false);
                                lh3.remove(humano);
                                riesgoHumanos3.sacarh(humano);
                                int muertes = zombie.getCont_muertes();
                                zombie.setCont_muertes(muertes+1);
                                System.out.println("El humano " + humano.gethumanoId() + " ha sido matado por el zombie " + zombie.getzombieId() + ".");
                                System.out.println("Número de muertes de " + zombie.getzombieId()+ ": " + zombie.getCont_muertes() + ".");
                                // Convertimos el humano a zombie
                                String humanoId = humano.gethumanoId();
                                String zombieId = "Z" + humanoId.substring(1);
                                Zombie zombie1 = new Zombie(zombieId, this);
                                System.out.println("El humano " + humano.gethumanoId() + " se ha convertido en zombie");
                                zombie1.setConvertido(true);
                                zombie1.setN_zonaRiesgo(3);
                                zombie1.start();
                            } else{
                                // El humano está herido
                                humano.setHerido(true);
                                System.out.println("El humano " + humano.gethumanoId() + " ha sido herido");
                            }
                            humano.setAtaque(false);
                            tiempo_ataque3.release();
                        } else {
                            lock3.release();
                        }
                    }
                } finally {
                    cerrojo3.unlock();
                }
            } if (zona == 4){
                cerrojo4.lock();
                try{
                    if (!lh4.isEmpty()){
                        lock4.acquire();
                        Humano humano = seleccionarHumano(lh4);
                        if (humano!=null){
                            humano.setAtaque(true);
                            lock4.release();
                            tiempo_ataque4.acquire();
                            System.out.println("El ataque entre el zombie " + zombie.getzombieId() + " y el humano " + humano.gethumanoId() + " se está produciendo");
                            int tiempoAtaque = (int)(Math.random()*1000)+500; // Entre 0,5 y 1,5 segundos
                            sleep(tiempoAtaque);
                            int probabilidad = (int)(Math.random()*3);
                            if (probabilidad == 0){
                                // Humano muere
                                humano.setVivo(false);
                                lh4.remove(humano);
                                riesgoHumanos4.sacarh(humano);
                                int muertes = zombie.getCont_muertes();
                                zombie.setCont_muertes(muertes+1);
                                System.out.println("El humano " + humano.gethumanoId() + " ha sido matado por el zombie " + zombie.getzombieId() + ".");
                                System.out.println("Número de muertes de " + zombie.getzombieId()+ ": " + zombie.getCont_muertes() + ".");
                                // Convertimos el humano a zombie
                                String humanoId = humano.gethumanoId();
                                String zombieId = "Z" + humanoId.substring(1);
                                Zombie zombie1 = new Zombie(zombieId, this);
                                System.out.println("El humano " + humano.gethumanoId() + " se ha convertido en zombie");
                                zombie1.setConvertido(true);
                                zombie1.setN_zonaRiesgo(4);
                                zombie1.start();
                            } else{
                                // El humano está herido
                                humano.setHerido(true);
                                System.out.println("El humano " + humano.gethumanoId() + " ha sido herido");
                            }
                            humano.setAtaque(false);
                            tiempo_ataque4.release();
                        } else {
                            lock4.release();
                        }
                    }
                } finally {
                    cerrojo4.unlock();
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
   private Humano seleccionarHumano(ConcurrentLinkedQueue<Humano> cola) {
       if (cola.isEmpty()) {
           return null;
       }
       List<Humano> lista = new ArrayList<>(cola);
       return lista.get(new Random().nextInt(lista.size()));
   }


    public ConcurrentLinkedQueue<Humano> getLh1() {
        return lh1;
    }

    public void setLh1(ConcurrentLinkedQueue<Humano> lh1) {
        this.lh1 = lh1;
    }

    public ConcurrentLinkedQueue<Humano> getLh2() {
        return lh2;
    }

    public void setLh2(ConcurrentLinkedQueue<Humano> lh2) {
        this.lh2 = lh2;
    }

    public ConcurrentLinkedQueue<Humano> getLh3() {
        return lh3;
    }

    public void setLh3(ConcurrentLinkedQueue<Humano> lh3) {
        this.lh3 = lh3;
    }

    public ConcurrentLinkedQueue<Humano> getLh4() {
        return lh4;
    }

    public void setLh4(ConcurrentLinkedQueue<Humano> lh4) {
        this.lh4 = lh4;
    }
    public void setTunel(Tunel tunel) {
        this.tunel = tunel;
    }

    public Tunel getTunel() {
        return tunel;
    }

    public ConcurrentLinkedQueue<Zombie> getLz1() {
        return lz1;
    }

    public ConcurrentLinkedQueue<Zombie> getLz2() {
        return lz2;
    }

    public ConcurrentLinkedQueue<Zombie> getLz3() {
        return lz3;
    }

    public ConcurrentLinkedQueue<Zombie> getLz4() {
        return lz4;
    }

    public Semaphore getLock1() {
        return lock1;
    }

    public Semaphore getLock2() {
        return lock2;
    }

    public Semaphore getLock3() {
        return lock3;
    }

    public Semaphore getLock4() {
        return lock4;
    }

}
