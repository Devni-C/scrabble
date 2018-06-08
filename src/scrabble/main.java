/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scrabble;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Ivo Carbajo
 */
public class main {

    public static void main(String[] args) {

        // LEE LAS PALABRAS DEL FICHERO Y LAS PASA A UNA ARRAY
        Scanner sc;
        sc = new Scanner(System.in);
        
        String nomFitxer = "./words.txt";
        String[] paraules={"",""};
        String palabra;

        try {
            paraules = llegirParaules(nomFitxer);
            System.out.println("Palabras leidas correctamente...");
        } catch (FileNotFoundException e) {
            System.out.println("Fichero no encontrado...");
        }

        int menuOption = -1;

        System.out.println("vols jugar sol (1) o contra l'ordinador (2)?");

        menuOption = sc.nextInt();

        System.out.println();

        int total = 0;
        for (int i = 0; i < 10; i++) {
            char [] letras = generarArrayLletres();
            for (int j = 0; j < letras.length; j++) {
                System.out.print(letras[j] + " ");
            }
            if(menuOption == 1){
                int scoore = 0;
                do {
                    palabra = sc.nextLine();
                } while (!(estaEnDiccionari(paraules, palabra)) || !(paraulaValida(palabra, letras)));
                scoore = calcularPuntuacio(palabra);
                total += scoore;
                System.out.println("puntuacion: " + scoore + " total: " + total);
            }else if (menuOption == 2){
                int scoore = 0;
                do {
                    palabra = sc.next();
                } while (!(estaEnDiccionari(paraules, palabra)) || !(paraulaValida(palabra, letras)));
                scoore = calcularPuntuacio(palabra);
                total += scoore;
                System.out.println("puntuacion: " + scoore + " total: " + total);
            }
        }

        
        
    }

    public static String[] llegirParaules(String path) throws FileNotFoundException {

        Scanner lectura = new Scanner(new File(path));
        //variable auxiliar, dinàmica
        ArrayList<String> llista = new ArrayList();
        System.out.println("Carregant diccionari de paraules...");
        while (lectura.hasNext()) {
            //assegurem que treballem amb majúscules
            String s = lectura.next().toUpperCase();
            //guardo dins la llista
            llista.add(s);
        }
        //convertir ArrayList a array d'String
        return llista.toArray(new String[0]);
    }

    public static char[] generarArrayLletres() {
        char[] letra = new char[7];
        
        String letraString="";
        
        String vocales = "aeiou";
        String consonantes = "qwrtypsdfghjklzxcvbnm";

        int vocalcont = 0;
        int indice = 0;

        for (int i = 0; i < letra.length; i++) {
            int alconsonantes = (int) (Math.random() * consonantes.length());

            letraString+=consonantes.charAt(alconsonantes);

            indice++;

            while (vocalcont != 3) {
                int alvocales = (int) (Math.random() * vocales.length());

                letraString+= vocales.charAt(alvocales);

                indice++;
                vocalcont++;
            }

        }
        letraString=letraString.toUpperCase();
        for (int i = 0; i < letra.length; i++) {
            letra[i]=letraString.charAt(i);
        }
        return letra;
    }

    public static boolean estaEnDiccionari(String[] array, String paraula) {
        for (int i = 0; i < array.length; i++) {
            if(paraula.equalsIgnoreCase(array[i]))return true;
        }

        return false;
    }

    public static int calcularPuntuacio(String paraula) {
        char[] punto1 = {'E', 'A', 'I', 'O', 'N', 'R', 'T', 'L', 'S', 'U'};
        char[] punto2 = {'D', 'G'};
        char[] punto3 = {'B', 'C', 'M', 'P'};
        char[] punto4 = {'F', 'H', 'V', 'W', 'Y'};
        char[] punto5= {'K'};
        char[] punto8 = {'J', 'X'};
        char[] punto10 = {'Q', 'Z'};
        
        char[][] puntos={punto1,punto2,punto3,punto4,punto5,punto8,punto10};
        
        int puntosTotales = 0;

        for (int i = 0; i < paraula.length(); i++) {
            for (int j = 0; j < puntos.length; j++) {
                for (int k = 0; k < puntos[j].length; k++) {
                    if (paraula.charAt(i) == puntos[j][k]) {
                        if (j==0) {
                            //suma 1
                            puntosTotales+=1;
                        }else if (j==1) {
                            //suma 2
                            puntosTotales+=2;
                        }else if (j==2) {
                            //suma 3
                            puntosTotales+=3;
                        }else if (j==3) {
                            //suma 4
                            puntosTotales+=4;
                        }else if (j==4) {
                            //suma 5
                            puntosTotales+=5;
                        }else if (j==5) {
                            //suma 8
                            puntosTotales+=8;
                        }else if (j==6) {
                            //suma 10
                            puntosTotales+=10;
                        }
                    }
                }
            }
        }
         
                

        return puntosTotales;
    }
    public static boolean paraulaValida(String paraula, char[] array){
        boolean result = false;
        
        char[] paraulaArray = paraula.toUpperCase().toCharArray();
        char[] arrayLocal = array;
        
        for (int i = 0; i < paraulaArray.length; i++) {
            boolean trobat = false;
            for (int j = 0; j < arrayLocal.length && !trobat; j++) {
                if(paraulaArray[i] == arrayLocal[j]){
                        trobat = true;
                        arrayLocal[j] = '\u0000';
                }
            }
            if (trobat)result=true;else result = false;
        }
        
        return result;
    }

    public static String millorParaula(String[] arrayParaules, char[] arrayLletres){
        int i=0;
        while (!(paraulaValida(arrayParaules[i], arrayLletres))) {
            i++;
        }
        int millorPuntuacio = calcularPuntuacio(arrayParaules[i]);
        String millorParaula = arrayParaules[i];
        for (i=0; i < arrayParaules.length; i++) {
            if (paraulaValida(arrayParaules[i], arrayLletres)) {
                int auxPuntuacio = calcularPuntuacio(arrayParaules[i]);
                if (auxPuntuacio > millorPuntuacio) {
                    millorPuntuacio = auxPuntuacio;
                    millorParaula = arrayParaules[i];
                }
            }
        }
        return millorParaula;
    }

}
