import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Zona_riesgo {
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

    public Zona_riesgo() {
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
    public void entrar_humano(Humano humano,int zona){ //esta función depende del tunel
        if(zona == 1){
            lh1.add(humano);
        }else if(zona == 2){
            lh2.add(humano);
        }else if(zona == 3){
            lh3.add(humano);
        }else{
            lh4.add(humano);
        }
    }
    public void entrar_zombie(Zombie zombie, int zona){
        if(zona == 1){
            lz1.add(zombie);
        }else if(zona == 2){
            lz2.add(zombie);
        }else if(zona == 3){
            lz3.add(zombie);
        }else{
            lz4.add(zombie);
        }
    }
    public void salir_humano(Humano humano, int zona){
        if(zona == 1){
            lh1.remove(humano);
        }else if(zona == 2){
            lh2.remove(humano);
        }else if(zona == 3){
            lh3.remove(humano);
        }else{
            lh4.remove(humano);
        }
    }
    public void salir_zombie(Zombie zombie, int zona){
        if(zona == 1){
            lz1.remove(zombie);
        }else if(zona == 2){
            lz2.remove(zombie);
        }else if(zona == 3){
            lz3.remove(zombie);
        }else{
            lz4.remove(zombie);
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
                        tunel.entrar1_zona_descanso(humano.gethumanoId());
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
                        tunel.entrar2_zona_descanso(humano.gethumanoId());
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
                        tunel.entrar3_zona_descanso(humano.gethumanoId());
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
                        tunel.entrar4_zona_descanso(humano.gethumanoId());
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
}
