package com.docencia.ficheros;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class CsvServiceImpl implements CsvService {

    @Override
    public List<String> leerLineasCsv(Path ruta) {
        List<String> lista = new ArrayList<>();

        if (!Files.exists(ruta)) throw new  IllegalArgumentException();

        try (BufferedReader reader = Files.newBufferedReader(ruta, StandardCharsets.UTF_8)) {
            String linea;
            while((linea = reader.readLine()) != null) {
                lista.add(linea);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(lista);
        return lista;
    }

    @Override
    public List<String[]> leerRegistrosCsv(Path ruta) {
        List<String[]> resultado = new ArrayList<>();
        List<String> lista = new ArrayList<>(leerLineasCsv(ruta));
        for (String linea : lista) {
            String[] registro = linea.split(",");
            resultado.add(registro);
        }
        return resultado;
    }

    @Override
    public void escribirLineasCsv(Path ruta, List<String> lineas) {
        try (BufferedWriter writer = Files.newBufferedWriter(ruta, StandardCharsets.UTF_8)) {
            for (String linea : lineas) {
                writer.write(linea);
                writer.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Integer contarRegistrosCsv(Path ruta) {
        return leerLineasCsv(ruta).size();
    }

    @Override
    public List<String[]> filtrarRegistrosPorValor(Path ruta, Integer columna, String valor) {
        List<String[]> registros = new ArrayList<>(leerRegistrosCsv(ruta));
        List<String[]> resultado = new ArrayList<>();
        for (String[] registro : registros) {
            if (registro.length-1 < columna) {
                throw new IllegalArgumentException();
            }
            if (registro[columna].equals(valor)) {
                resultado.add(registro);
            }
        }
        return resultado;
    }

    @Override
    public Map<String, Integer> contarFrecuenciaColumna(Path ruta, Integer columna) {
        Map<String, Integer> resultado = new HashMap<>();
        List<String[]> registros = leerRegistrosCsv(ruta);
        for (String[] registro : registros) {
            if(!resultado.containsKey(registro[columna])){
                resultado.put(registro[columna], 1);
            } else {
                resultado.put(registro[columna], resultado.get(registro[columna]) + 1);
            }
        }
        return resultado;
    }
    
}
