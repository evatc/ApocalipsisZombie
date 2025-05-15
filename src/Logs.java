import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logs {
    private File fichero;
    private BufferedWriter escritor;
    private DateTimeFormatter formato;
    private String fecha_hora;

    public Logs(File fichero) throws IOException {
        this.fichero = fichero;
        // Crear el archivo si no existe
        if (!fichero.exists()) {
            fichero.createNewFile();
        }
        abrir(false); // Abrir en modo append
    }

    public void abrir(boolean append) throws IOException {
        // Cerrar escritor si ya estaba abierto
        if (escritor != null) {
            cerrar();
        }

        // Si NO se quiere hacer append (sobrescribir), borrar y crear el archivo de nuevo
        if (!append) {
            if (fichero.exists()) {
                if (!fichero.delete()) {
                    throw new IOException("No se pudo borrar el archivo existente.");
                }
            }
            fichero.createNewFile();
        } else {
            // Crear el archivo si no existe (modo append)
            if (!fichero.exists()) {
                fichero.createNewFile();
            }
        }

        // Abrir escritor con el modo indicado
        escritor = new BufferedWriter(new FileWriter(fichero, append));
    }

    public synchronized void escribir(String contenido) {
        try {
            if (escritor != null) {
                formato = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
                fecha_hora = LocalDateTime.now().format(formato);
                escritor.write("[" + fecha_hora + "] " + contenido);
                escritor.newLine();
                escritor.flush(); // Asegurarse de que los datos se escriben inmediatamente
            } else {
                System.out.println("El archivo no está abierto.");
            }
        } catch (IOException e) {
            System.out.println("Error al escribir: " + e.getMessage());
        }
    }

    public void cerrar() {
        try {
            if (escritor != null) {
                escritor.close();
            }
        } catch (IOException e) {
            System.out.println("Error al cerrar el archivo: " + e.getMessage());
        } finally {
            escritor = null;
        }
    }
}