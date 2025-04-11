import java.util.Scanner;

public class Zombie extends Thread{
    private String id;
    private int cont_muertes = 0;
    private Zona_riesgo zonaRiesgo;
    public Zombie(String id){
        this.id = id;
    }

    public void run(){
        Scanner sc = new Scanner(System.in);

        while (true) {
            String texto = sc.nextLine();

            if (texto.equals("salir")) {
                break;
            }
            int n_zonariesgo = (int) (Math.random() * 4) + 1;
            zonaRiesgo.entrar_zombie(this,n_zonariesgo);
            //boolean atacar = zonaRiesgo.ataque();
            try{
                sleep((int)(1000*Math.random() + 2000));
            }catch(Exception e){}
            zonaRiesgo.salir_zombie(this,n_zonariesgo);
        }
        sc.close();
    }

    public int getCont_muertes() {
        return cont_muertes;
    }

    public void setCont_muertes(int cont_muertes) {
        this.cont_muertes = cont_muertes;
    }
}
