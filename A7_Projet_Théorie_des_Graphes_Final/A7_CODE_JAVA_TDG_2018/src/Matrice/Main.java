package Matrice;

import java.io.File;
import java.io.FilenameFilter;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.regex.Matcher;

public class Main {


    public static void main(String[] args) {

        int i;
        boolean fin = true;
        String out ="";
        Scanner readtext = new Scanner(System.in);
        Boolean nextstep=false;
        // Ici nous récuperons l'ensemble des noms de fichiers textes contenu dans le répertoire ci-dessous,
        // pour que le programme fonctionne sur votre machine, il vous faudra changer des "path" (lien vers le répertoire)
        System.out.println("Veuillez rentrer la 'path' du répertoire dans lequel sont stockés vos texts\n" +
                "Par exemple : \n" +
                "- sur MAC : /Users/marcbelleperche/Desktop/Text \n" +
                "- sur WINDOWS : C://...\n" +
                "Vous pouvez aussi tout simplement glisser votre dossier dans le terminal, la 'path' apparaitra directement");
        File repertoire = new File(readtext.next());
        File[] files = repertoire.listFiles();
        while (fin == true) {
            nextstep = false;
            System.out.println("Selectionner un texte : \n");
            // On affiche ici tout les noms de fichiers en les associants à un nombre
            for (i = 0; i < files.length; i++) {
                String name = files[i].getName();
                System.out.println(i + 1 + ". " + name);
            }

            System.out.println("Entez votre choix :");
            int numero = readtext.nextInt();
            if (numero != 0) {
                Graphe graphe = new Graphe(files[numero - 1]); // On Créé une instance de graphe avec le nom du fichier text que l'on veut annalyser
                graphe.init(); // fonction d'initialisation
                graphe.sommetsarc(); // fonction récupérants et stockant les sommets et nombre d'arcs
                graphe.creationmatrice(); // Ici on stocke les différentes valeurs du fichier texte dans des tableaux correspondants aux successeurs, predecesseur et le poids associé à l'arc
                graphe.storage();// Ici on trie les différentes valeurs des tableaux précédents dans un tableau double entrée pour créer une matrice
                graphe.affichage();// On affiche ici la matrice ligne par ligne
                graphe.circuit(); // On vérifie sur le graphe contient un circuit
                //graphe.ordonnance(); // On vérifie sur le graphe ne contenant pas de circuit est bien ordonné


            }
            System.out.println("\nSouhaitez vous analysé un nouveau graphe :si oui entrer 'again' sinon entrer 'end'");
            while (nextstep != true) {
                try {
                    out = readtext.next();
                    if (out.matches("end")) {
                        fin = false;
                        nextstep = true;
                    } else if (out.matches("again")) {
                        fin = true;
                        nextstep = true;
                    } else {
                        System.out.println("entrer une commande");
                        nextstep = false;
                    }

                } catch (InputMismatchException e) {}
            }
        }
    }

}


