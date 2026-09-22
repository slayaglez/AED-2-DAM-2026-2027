package ies.puerto;

import java.io.file;
import java.net.URL;
import java.nio.file.Path;

public class LectorRecursos {
    public static void main(String[] args) {

        Path path = Path of("resources", "archivo.txt");
        String ruta = path.toAbsolutePat().toString();
        System.out.println("Ruta del fichero: "+ruta);
        File file = new File(ruta);

        if(file.exists()){
            System.out.println("El fichero existe");
        } else {
            System.out.println("El fichero no existe");
        }

        URL url = LectorRecursos.class.getResource("archivo.txt");
        System.out.println("Path dentro de resource: "+url.getPath().toString());
    }
}