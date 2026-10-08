CSVFormat formato = CSVFormat.DEFAULT.builder()
          .setHeader()
          .setSkipHeaderRecord(true)
          .get();

  try (Reader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8);
       CSVParser parser = formato.parse(reader)) {
      for (CSVRecord registro : parser) {
          resultado.add(new Producto(
                  Long.parseLong(registro.get("id")),
                  registro.get("nombre"),
                  Double.parseDouble(registro.get("precio")),
                  Integer.parseInt(registro.get("stock"))));
      }
      return resultado;
  }


protected void writeAll(List<Producto> productos) throws IOException {
        CSVFormat formato = CSVFormat.DEFAULT.builder()
                .setHeader("id", "nombre", "precio")
                .get();

        try (Writer writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8);
             CSVPrinter printer = new CSVPrinter(writer, formato)) {
            for (Producto p : productos) printer.printRecord(p.id(), p.nombre(), p.precio());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
