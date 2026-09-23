package ies.puerto;

import java.io.File;
import java.nio.file.Path;

public class FicheroImpl implements IFichero{

    @Override
    public boolean existe(Path path) {
        if(path == null) {
            return false;
        }

        File file = new  File(path.toString());
        return file.exists();
    }
}
