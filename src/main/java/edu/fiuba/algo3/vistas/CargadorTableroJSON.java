package edu.fiuba.algo3.vistas;

import edu.fiuba.algo3.modelo.tablero.Hexagono;
import edu.fiuba.algo3.modelo.tablero.Produccion;
import edu.fiuba.algo3.modelo.tablero.tiposHexagono.*;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Parser que carga la información del tablero desde un archivo JSON.
 * Proporciona dos listas: una de hexágonos (19) y otra de producciones (18).
 * Ambas listas están shuffleadas (barajadas) antes de ser retornadas.
 * 
 * Usa parsing manual sin dependencias externas.
 */
public class CargadorTableroJSON {
    
    public static class TableroData {
        public ArrayList<Hexagono> hexagonos;
        public ArrayList<Produccion> producciones;
        
        public TableroData(ArrayList<Hexagono> hexagonos, ArrayList<Produccion> producciones) {
            this.hexagonos = hexagonos;
            this.producciones = producciones;
        }

        public ArrayList<Hexagono> obtenerHexagonos() {
            return this.hexagonos;
        }

        public ArrayList<Produccion> obtenerProducciones() {
            return this.producciones;
        }
    }
    
    /**
     * Carga la información del tablero desde el archivo JSON.
     * Valida que haya exactamente 19 hexágonos y 18 producciones.
     * Después de cargar, baraja ambas listas usando Collections.shuffle().
     * 
     * @return TableroData con listas de hexágonos y producciones shuffleadas
     */
    public static TableroData cargarTableroDesdeJSON() {
        try {
            // Obtener el archivo JSON desde los recursos
            InputStream inputStream = CargadorTableroJSON.class.getResourceAsStream("/json/tablero.json");
            if (inputStream == null) {
                throw new RuntimeException("No se pudo encontrar el archivo tablero.json en los recursos");
            }
            
            // Leer el archivo completo
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
            StringBuilder jsonContent = new StringBuilder();
            String linea;
            while ((linea = reader.readLine()) != null) {
                jsonContent.append(linea);
            }
            reader.close();
            
            String json = jsonContent.toString();
            
            // Extraer hexágonos
            ArrayList<Hexagono> hexagonos = parsearHexagonos(json);
            
            // Extraer producciones
            ArrayList<Produccion> producciones = parsearProducciones(json);
            
            // Validar cantidades
            if (hexagonos.size() != 19 || producciones.size() != 18) {
                throw new RuntimeException("Error: Se esperaban 19 hexágonos y 18 producciones, pero se encontraron " 
                    + hexagonos.size() + " hexágonos y " + producciones.size() + " producciones");
            }
            
            // Shuffle (barajar) las listas
            Collections.shuffle(hexagonos);
            Collections.shuffle(producciones);
            
            return new TableroData(hexagonos, producciones);
            
        } catch (Exception e) {
            throw new RuntimeException("Error al cargar el tablero desde JSON: " + e.getMessage(), e);
        }
    }
    
    /**
     * Parsea el array de hexágonos del JSON usando regex
     */
    private static ArrayList<Hexagono> parsearHexagonos(String json) {
        ArrayList<Hexagono> hexagonos = new ArrayList<>();
        
        // Encontrar la sección "hexagonos" del JSON
        Pattern pattern = Pattern.compile("\"hexagonos\"\\s*:\\s*\\[(.*?)\\]", Pattern.DOTALL);
        Matcher matcher = pattern.matcher(json);
        
        if (matcher.find()) {
            String hexagonosSection = matcher.group(1);
            
            // Encontrar todos los objetos {"tipo": "..."}
            Pattern objPattern = Pattern.compile("\"tipo\"\\s*:\\s*\"([^\"]+)\"");
            Matcher objMatcher = objPattern.matcher(hexagonosSection);
            
            while (objMatcher.find()) {
                String tipo = objMatcher.group(1);
                Hexagono hexagono = crearHexagonoPorTipo(tipo);
                hexagonos.add(hexagono);
            }
        }
        
        return hexagonos;
    }
    
    /**
     * Parsea el array de producciones del JSON usando regex
     */
    private static ArrayList<Produccion> parsearProducciones(String json) {
        ArrayList<Produccion> producciones = new ArrayList<>();
        
        // Encontrar la sección "producciones" del JSON
        Pattern pattern = Pattern.compile("\"producciones\"\\s*:\\s*\\[(.*?)\\]", Pattern.DOTALL);
        Matcher matcher = pattern.matcher(json);
        
        if (matcher.find()) {
            String produccionesSection = matcher.group(1);
            
            // Encontrar todos los objetos {"numero": ...}
            Pattern objPattern = Pattern.compile("\"numero\"\\s*:\\s*(\\d+)");
            Matcher objMatcher = objPattern.matcher(produccionesSection);
            
            while (objMatcher.find()) {
                int numero = Integer.parseInt(objMatcher.group(1));
                Produccion produccion = new Produccion(numero);
                producciones.add(produccion);
            }
        }
        
        return producciones;
    }

    
    /**
     * Crea un hexágono del tipo especificado
     */
    private static Hexagono crearHexagonoPorTipo(String tipo) {
        return switch (tipo) {
            case "Desierto" -> new Desierto();
            case "Campo" -> new Campo();
            case "Bosque" -> new Bosque();
            case "Pastizal" -> new Pastizal();
            case "Colina" -> new Colina();
            case "Montana" -> new Montana();
            default -> throw new RuntimeException("Tipo de hexágono desconocido: " + tipo);
        };
    }
}
