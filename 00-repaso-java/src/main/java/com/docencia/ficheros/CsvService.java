package com.docencia.ficheros;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;

/** Servicio para practicar lectura y escritura de ficheros CSV. */
public interface CsvService {

    /**
     * Lee todas las lineas de un fichero CSV
     * @param ruta Path ruta del fichero
     * @return lista con cada linea del fichero
     */
    List<String> leerLineasCsv(Path ruta);

    /**
     * Lee los registros de un fichero CSV separando sus campos
     * @param ruta Path ruta del fichero
     * @return lista de registros, cada uno con sus campos
     */
    List<String[]> leerRegistrosCsv(Path ruta);

    /**
     * Escribe lineas en un fichero CSV
     * @param ruta Path ruta del fichero
     * @param lineas List lineas a escribir
     */
    void escribirLineasCsv(Path ruta, List<String> lineas);

    /**
     * Cuenta los registros de un fichero CSV
     * @param ruta Path ruta del fichero
     * @return numero de registros
     */
    Integer contarRegistrosCsv(Path ruta);

    /**
     * Filtra los registros cuyo campo en una columna coincide con un valor
     * @param ruta Path ruta del fichero
     * @param columna Integer indice de la columna
     * @param valor String valor a buscar
     * @return lista de registros que coinciden
     */
    List<String[]> filtrarRegistrosPorValor(Path ruta, Integer columna, String valor);

    /**
     * Cuenta cuantas veces aparece cada valor en una columna
     * @param ruta Path ruta del fichero
     * @param columna Integer indice de la columna
     * @return mapa con cada valor y su numero de apariciones
     */
    Map<String, Integer> contarFrecuenciaColumna(Path ruta, Integer columna);
}