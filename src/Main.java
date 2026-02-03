import javax.imageio.IIOException;
import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        String FAlumnos = "alumnos.txt";
        String FResultados = "resultados.txt";

        Map<String, List<Double>> alumnos = new HashMap();

        //1. Leer el fichero
        try(BufferedReader reader = new BufferedReader(new FileReader(FAlumnos));){
            String line;
            while ((line = reader.readLine())!=null){
                String[] data = line.split(";");

                if(data.length==2){
                    String nombre = data[0];
                    double nota = Double.parseDouble(data[1]);

                    alumnos.putIfAbsent(nombre, new ArrayList<>());
                    alumnos.get(nombre).add(nota);
                }
            }
        }catch (IOException | NumberFormatException e) {
            System.out.println("Error al escribir el alumno");
            return;
        }

        //Nota Media
        Map<String, Double> medias = new HashMap();

        for(String alumno: alumnos.keySet()){
            List<Double> notas = alumnos.get(alumno);
            double suma = 0;
            for (Double nota: notas){
                suma += nota;
            }
            double media = suma / notas.size();
            medias.put(alumno, media);
        }

        //Alumnos aprobados
        Map<String, Double> aprobados = new HashMap();

        for (String alumno : medias.keySet()){
            if (medias.get(alumno) >= 5) {
                aprobados.put(alumno, medias.get(alumno));
            }
        }

        //Ordenar por nota media
        List<Map.Entry<String, Double>> ordenada = new ArrayList<>(aprobados.entrySet());

        Collections.sort(ordenada, new Comparator<Map.Entry<String, Double>>() {
            public int compare(Map.Entry<String, Double> a, Map.Entry<String, Double> b) {
                return Double.compare(b.getValue(), a.getValue());
            }
        });

        Map.Entry<String, Double> mejorAlumno = ordenada.get(0);


        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FResultados))){
            writer.write("Alumnos Aprobados");
            writer.newLine();
            writer.write("------------------");
            writer.newLine();

            for (Map.Entry<String, Double> entry: ordenada){
                writer.write(entry.getKey() + "-->" + String.format("%.2f", entry.getValue()));
                writer.newLine();
            }
            writer.newLine();
            writer.write("Mejor Alumno");
            writer.newLine();
            writer.write("-------------");
            writer.newLine();
            writer.write(mejorAlumno.getKey() + "-->" + String.format("%.2f", mejorAlumno.getValue()));
        }catch (IOException e) {
            System.out.println("Error al escribir el alumno");
        }
        System.out.println("Programa terminado.");
    }
}



