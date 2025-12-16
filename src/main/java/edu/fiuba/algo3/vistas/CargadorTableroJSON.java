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


public class CargadorTableroJSON {
    
    public static class TableroData {
        private final ArrayList<Hexagono> hexagonos;
        private final ArrayList<Produccion> producciones;
        
        public TableroData(ArrayList<Hexagono> hexagonos, ArrayList<Produccion> producciones) {
            this.hexagonos = new ArrayList<>(hexagonos);
            this.producciones = new ArrayList<>(producciones);
        }

        public ArrayList<Hexagono> obtenerHexagonos() {
            return new ArrayList<>(this.hexagonos);
        }

        public ArrayList<Produccion> obtenerProducciones() {
            return new ArrayList<>(this.producciones);
        }
    }
    
    public static TableroData cargarTableroDesdeJSON() {
        try {
            InputStream inputStream = CargadorTableroJSON.class.getResourceAsStream("/json/tablero.json");
            if (inputStream == null) {
                throw new RuntimeException("No se pudo encontrar el archivo tablero.json en los recursos");
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
            StringBuilder jsonContent = new StringBuilder();
            String linea;
            while ((linea = reader.readLine()) != null) {
                jsonContent.append(linea);
            }
            reader.close();

            String json = jsonContent.toString();

            ArrayList<Hexagono> hexagonos = parsearHexagonos(json);

            ArrayList<Produccion> producciones = parsearProducciones(json);

            if (hexagonos.size() != 19 || producciones.size() != 18) {
                throw new RuntimeException("Error: Se esperaban 19 hexágonos y 18 producciones, pero se encontraron " 
                    + hexagonos.size() + " hexágonos y " + producciones.size() + " producciones");
            }

            Collections.shuffle(hexagonos);
            Collections.shuffle(producciones);
            
            return new TableroData(hexagonos, producciones);
            
        } catch (Exception e) {
            throw new RuntimeException("Error al cargar el tablero desde JSON: " + e.getMessage(), e);
        }
    }
    
    private static ArrayList<Hexagono> parsearHexagonos(String json) {
        ArrayList<Hexagono> hexagonos = new ArrayList<>();
        
        Pattern pattern = Pattern.compile("\"hexagonos\"\\s*:\\s*\\[(.*?)\\]", Pattern.DOTALL);
        Matcher matcher = pattern.matcher(json);
        
        if (matcher.find()) {
            String hexagonosSection = matcher.group(1);
            
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
    
    private static ArrayList<Produccion> parsearProducciones(String json) {
        ArrayList<Produccion> producciones = new ArrayList<>();
        
        Pattern pattern = Pattern.compile("\"producciones\"\\s*:\\s*\\[(.*?)\\]", Pattern.DOTALL);
        Matcher matcher = pattern.matcher(json);
        
        if (matcher.find()) {
            String produccionesSection = matcher.group(1);
            
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
