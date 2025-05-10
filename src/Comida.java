import javafx.application.Platform;
import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class Comida {
    private AtomicInteger comidah = new AtomicInteger(0);
    private TextField txtfield;
    private ConcurrentLinkedQueue<Humano> filaComedor = new ConcurrentLinkedQueue<>();
    private Logs log;


    public Comida(TextField tf)
    {
        this.txtfield =tf;
    }



    public void dejarComida(Humano humano) {
        int nuevaCantidad = comidah.addAndGet(2);
        imprimirh();

    }

    public void comer(Humano humano) {
        filaComedor.offer(humano);

        try {
            while (true) {
                int comidaActual = comidah.get();

                // Si no hay comida o no es nuestro turno, esperar
                if (comidaActual <= 0 || filaComedor.peek() != humano) {
                    Thread.sleep(100); // Pequeña espera para no saturar la CPU
                    continue;
                }

                // Intentar decrementar la comida
                if (comidah.compareAndSet(comidaActual, comidaActual - 1)) {
                    // Éxito: podemos comer
                    imprimirh();

                    filaComedor.poll(); // Salir de la fila
                    int tiempoComer = (int)(Math.random()*2000)+3000; // Entre 3 y 5 segundos
                    Thread.sleep(tiempoComer);
                    break;
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }


    public void imprimirh()
    {
        final String textoImprimir = String.valueOf(comidah);

        Platform.runLater(() -> {
            txtfield.setText(textoImprimir);
        });

    }

}
