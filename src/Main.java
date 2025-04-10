import static java.lang.Thread.sleep;

public class Main {
    public static void main(String[] args) {
        //Instanciar elementos comunes
        Refugio refugio = new Refugio();
        Tunel tunel = new Tunel();
        Zona_riesgo zonaRiesgo = new Zona_riesgo();

        //Istanciar el zombie
        //Zombie zombie = new Zombie("Z0000", refugio, tunel, zonaRiesgo);
        //zombie.start();
        //Instanciar los humanos de forma escalonada
        for (int i = 1; i <= 10000; i++)
        {
            try
            {
                //Hay que ver si el id es mejor que sea string o int
                //String humanoid = String.format("H%04d",i);
                //Humano humano = new Humano(humanoid, refugio, tunel, zonaRiesgo);
                //humano.start();
                sleep((int)(1500*Math.random() + 500));
            }catch(InterruptedException e)
            {
                System.out.println("Error al crear los clientes");
            }
        }
    }
}