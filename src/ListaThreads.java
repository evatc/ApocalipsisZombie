import javafx.application.Platform;
import javafx.scene.control.TextField;

import java.util.ArrayList;

public class ListaThreads {
    private ArrayList<Humano> listah;
    private ArrayList<Zombie> listaz;
    private TextField txtfield;


    public ListaThreads(TextField tf)
    {
        listah=new ArrayList<Humano>();
        listaz=new ArrayList<Zombie>();
        this.txtfield =tf;
    }

    public synchronized void meterh(Humano t)
    {
        listah.add(t);
        imprimirh();
    }

    public synchronized void sacarh(Humano t)
    {
        listah.remove(t);
        imprimirh();
    }

    public synchronized void imprimirh()
    {
        String contenido="";
        for(int i=0; i<listah.size(); i++)
        {
            contenido=contenido+listah.get(i).gethumanoId()+" ";
        }
        final String textoImprimir = contenido.toString();

        Platform.runLater(() -> {
            txtfield.setText(textoImprimir);
        });

    }
    public synchronized void meterz(Zombie t)
    {
        listaz.add(t);
        imprimirz();
    }

    public synchronized void sacarz(Zombie t)
    {
        listaz.remove(t);
        imprimirz();
    }

    public synchronized void imprimirz()
    {
        String contenido="";
        for(int i=0; i<listaz.size(); i++)
        {
            contenido=contenido+listaz.get(i).getzombieId()+" ";
        }
        final String textoImprimir = contenido.toString();

        Platform.runLater(() -> {
            txtfield.setText(textoImprimir);
        });

    }

    public synchronized int sizeh(){
        return listah.size();
    }

    public synchronized int sizez(){
        return listaz.size();
    }

}

