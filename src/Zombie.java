public class Zombie extends Thread{
    private int id;
    private int cont_muertes = 0;
    private Zona_riesgo zonaRiesgo;
    public Zombie(int id, Zona_riesgo zonaRiesgo){
        this.id = id;
        this.zonaRiesgo = zonaRiesgo;
    }

    public void run(){
        while(true) {
            int n_zonariesgo = (int) (Math.random() * 4) + 1;
            boolean atacar = zonaRiesgo.ataque();
            if (atacar){
                cont_muertes ++;
            }
            try{
                sleep((int)(1000*Math.random() + 2000));
            }catch(Exception e){}
        }
    }
}
