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
    private Lock cerrojo= new ReentrantLock();

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

    public boolean ataque(Zombie zombie, int zona){
        if(zona == 1){
            cerrojo.lock();
            if(buscar_humano(lh1)){
                Random random = new Random();
                int posicion = random.nextInt(lh1.size());
                Humano humano = lh1.get(posicion);
                int probabilidad = random.nextInt(3);
                if(probabilidad == 0){
                    lh1.remove(humano);
                    //String id = humano.getId();
                    humano.setVivo(false);
                    int muertes = zombie.getCont_muertes();
                    zombie.setCont_muertes(muertes + 1);
                    //Zombie zombie1 = new Zombie(id);
                }
            }
        }
        return false; //He puesto esto solo para que no de error
    }
}
