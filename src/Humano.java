import java.util.Scanner;

public class Humano extends Thread{
    private String id;
    private Refugio refugio;
    private Tunel tunel;
    private Zona_riesgo zonaRiesgo;
    private boolean herido = false;
    private boolean vivo = true;

    public Humano(String id, Refugio refugio, Tunel tunel, Zona_riesgo zonaRiesgo){
        this.id = id;
        this.refugio = refugio;
        this.zonaRiesgo = zonaRiesgo;
    }

    public void run(){
        Scanner sc = new Scanner(System.in);

        while (vivo) {
            String texto = sc.nextLine();

            if (texto.equals("salir")) {
                break;
            }
            double tiempo = (1 + Math.random())*1000;
            refugio.zona_comun(this.id);
            try{
                Thread.sleep((long) tiempo);
            }catch(Exception e){}
            int n_tunel = (int) (Math.random()*4)+1;
            if(n_tunel == 1){
                refugio.zona_espera_tunel1(this.id);
            }else if(n_tunel == 2){
                refugio.zona_espera_tunel2(this.id);
            }else if(n_tunel == 3){
                refugio.zona_espera_tunel3(this.id);
            }else{
                refugio.zona_espera_tunel4(this.id);
            }
            double tiempo1 = (3 + Math.random()*2)*1000; // ns si ponerlo aquí o dentro de un método
            try{
                Thread.sleep((long) tiempo1);
            }catch(Exception e){}
            //aqui habria que hacer lo del ataque
            if(vivo){//si no le ha matado un zombie sigue con la rutina
                if(!herido){ //si no ha sido atacado por un zombie pasa por los túneles(herido se va directamente a los tuneles sin pasar por aqui)
                    zonaRiesgo.salir_humano(this.id,n_tunel);
                    if(n_tunel == 1){
                        tunel.entrar1_zona_descanso(this.id);
                    }else if(n_tunel == 2){
                        tunel.entrar2_zona_descanso(this.id);
                    }else if(n_tunel == 3){
                        tunel.entrar3_zona_descanso(this.id);
                    }else{
                        tunel.entrar4_zona_descanso(this.id);
                    }
                    int comida = refugio.getComida();
                    refugio.setComida(comida + 2);
                }
                double tiempo2 = (2 + Math.random()*2)*1000;
                try{
                    Thread.sleep((long) tiempo2);
                }catch(Exception e){}
                refugio.comedor(this.id);
                //aqui iria lo de curarse q depende del ataque
                System.out.println("Dijiste: " + texto);
            }

        }

        sc.close();
    }

    public boolean isHerido() {
        return herido;
    }

    public void setHerido(boolean herido) {
        this.herido = herido;
    }

    public boolean isVivo() {
        return vivo;
    }

    public void setVivo(boolean vivo) {
        this.vivo = vivo;
    }

    @Override
    public String getId() {
        return id;
    }
}
