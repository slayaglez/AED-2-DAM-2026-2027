package ies.puerto;

import com.sun.net.httpserver.Headers;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

public class LectorRecursos extends FicheroImpl{
    public static void main(String[] args) {

        Path path = Path.of("src/main/resources", "archivo.txt");
        String ruta = path.toAbsolutePath().toString();
        System.out.println("Ruta del fichero: "+ruta);
        File file = new File(ruta);

        if(file.exists()){
            System.out.println("El fichero existe");
        } else {
            System.out.println("El fichero no existe");
        }

        try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            System.out.println(reader.readLine());
            String linea = reader.readLine();
            List<String> lista = Arrays.asList(linea.split(","));
            System.out.println(lista.get(0));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            Reader in = new FileReader(path.toAbsolutePath().toString());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        CSVFormat csv = CSVFormat.DEFAULT.builder()
                .setHeader()
                .setSkipHeaderRecord(true)
                .build();

        //Iterable<CSVRecord> records = csvFormat.parse(in);
    }
}