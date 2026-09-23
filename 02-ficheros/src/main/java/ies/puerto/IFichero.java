package ies.puerto;

import java.nio.file.Path;

public interface IFichero {
    /**
     * Funcion que verifica la existencia de un Path
     * @param path ruta del archivo
     * @return true / false
     */
    public boolean existe(Path path);
}