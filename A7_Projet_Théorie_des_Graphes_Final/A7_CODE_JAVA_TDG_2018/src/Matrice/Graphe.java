package Matrice;

import java.io.*;
import java.util.Scanner;


public class Graphe {

    private File txt;
    private BufferedReader graphe;
    String nbarcs;
    String nbsommet;
    Scanner store;
    int nbtotal;
    int nbsom;
    int pre[] = new int[40];
    int suc[] = new int[40];
    int poids[] = new int[40];
    int tabligne[][] = new int[40][40];
    int Ordre = 0;
    int nblinetoremove=0;
    int lineremoved = 0;
    int []lineremoveds = new int [40];
    int nbtour = 0;
    int [] entrees = new int[40];
    String tablines[][] = new String[40][40];
    String colonematrice[][] = new String[40][40];
    String ordrenoeuds="";
    Boolean found = false;
    Boolean Firstcycle = false;
    Boolean oneeentree = false;
    Boolean ordonnance = true;
    Boolean ordonnacefct = false;
    Boolean ordon=false;
    String affichage1;
    String lignematrice [] = new String[40];
    String colonnematrice [] = new String[40];


    Graphe(File graphename) {
        this.txt = graphename;
    }

    public void init() {
        try {
            graphe = new BufferedReader(new FileReader(txt)); // On stock le fichier text dans un Buffer
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void sommetsarc() {

        try {
            nbsommet = graphe.readLine(); // On stock le nombre de sommet dans la variable nbsommet
            nbarcs = graphe.readLine(); // On stock le nombre d'arcs dans la variable nbarcs
            System.out.println("Votre graphe contient " + nbsommet + " sommets et " + nbarcs + " arcs"); // On affiche ses deux variables
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void creationmatrice() {

        store = new Scanner(nbarcs);
        nbtotal = store.nextInt(); // On ne récupère que le int de manière à pouvoir l'utiliser par la suite
        store = new Scanner(nbsommet);
        nbsom = store.nextInt();// On ne récupère que le int de manière à pouvoir l'utiliser par la suite

        for (int i = 0; i < nbtotal; i++) {
            try {
                store = new Scanner(graphe.readLine());
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Votre nombre de sommets et d'arcs est éronné");
            }
            // On stock les différents chiffre du fichier text dans les tableau suivant
            pre[i] = store.nextInt(); // tableau des predecesseurs
            suc[i] = store.nextInt(); // tableau des successeurs
            poids[i] = store.nextInt(); // tableau des valeurs des arcs entre predecesseur et successeur
        }
    }

    public void storage() {

        affichage1 = "-  ";
        for (int i = -1; i < nbsom; i++) {
            // On affiche ici la première ligne de la matrice allant de 0 à n sommets
            if (i == -1) {
                for (int j = 0; j < nbsom; j++) {
                    if (j < 10){affichage1 = affichage1 + j + "  ";}
                    else{affichage1 = affichage1 + j + " ";}
                }
                // Range ici les différents valeurs de poids des arcs dans le tableau à double entrée en fonction des valeurs de predecesseur et de successeur
                // On y range aussi dans un autre tableau à double entrée des F ou des V pour la matrice d'adjacence par la suite
            } else if (i != -1) {
                for (int j = 0; j < nbsom; j++) {
                    found = false;
                    for (int parcour = 0; parcour < nbtotal; parcour++) {
                        if (i == pre[parcour] && j == suc[parcour]) {
                            found = true;
                            tabligne[i][j] = poids[parcour];
                            tablines[i][j] = "V"; // Si un arc existe on rentre V dans le tableau à double entrée
                            colonematrice[j][i] = "V"; // On fait la meme demarche pour les colonnes de la matrice
                        }

                        if (found != true) {
                            tabligne[i][j] = 100; // S'il n'y a pas d'arc entre le predecesseur et le successeur, on y meet un -, 100 en code ASCII
                            tablines[i][j] = "F"; // Si aucun arc existe on rentre un F dans le tableau à double entrée
                            colonematrice[j][i] = "F"; // On fait la meme demarche pour les colonnes de la matrice
                        }
                        }
                    }
                }
            }
        }

    public void affichage(){
        System.out.println("\nMatrice d'incidence\n");
        System.out.println(affichage1);
        int nb=0;
        // On recupère ici chaque valeur rentrer dans le tableau à double entrée précédemment et nous les stockons dans un String réinitialisé à chaque ligne
        for (int i = 0; i < nbsom; i++) {
            String affichag = i + "  ";
            if (i>=10)affichag = i + " ";
            for (int j = 0; j < nbsom; j++) {
                if (tabligne[i][j] == 100)affichag += "-  ";
                else if (tabligne[i][j] < 0 || tabligne[i][j] >= 10) affichag += tabligne[i][j] + " ";
                else affichag += tabligne[i][j] + "  ";
            }
            System.out.println(affichag);
        }

        System.out.println("\nMatrice d'adjacence\n");
        String affichageline2 = "";
        String affichagecolonne2 = "";
        System.out.println(affichage1);
        // On effectue la meme démarche pour la matrice d'adjacence mais cette fois on stocke dans des tableaux colonne et ligne les ensemble de V et F
                for (int u = 0; u < nbsom; u++) {
                    if (u<10)affichageline2 = u + "  ";
                    else affichageline2 = u + " ";
                    if (u<10)affichagecolonne2 = u + "  ";
                    else affichagecolonne2 = u + " ";

                    for (int j = 0; j < nbsom; j++) {
                       affichageline2 += tablines[u][j] + "  ";
                       affichagecolonne2 += colonematrice [u][j] + "  ";
                    }
                    System.out.println(affichageline2);
                    lignematrice[nb] = affichageline2;
                    colonnematrice[nb] = affichagecolonne2;
                    nb++;
                }
    }

    public void circuit() {
        Boolean[] VorF = new Boolean[40];
        String entree = "";
        Boolean circuit = null;
        int nbentree =0;
        String entrypoints = "";
        Boolean finderecherche = false;
        lineremoved = 0;
        nbtour++;

// On cherche ici à savoir si le graphe contient un circuit

        for (int i = 0; i < nbsom; i++) {
            // Si on ne trouve pas de V
            if (colonnematrice[i].indexOf("V") == -1) {
                VorF[i] = false;
                if (nbentree == 0) entree += i;
                else entree += " et " + i;
                nbentree++;
                nblinetoremove ++;
                lineremoved ++;
                lineremoveds[nbtour] = lineremoved;
                //if (lineremoveds[nbtour]>lineremoveds[nbtour -1] && lineremoveds[nbtour] != nbsom+1) {
                //}
                if(entrees[i]!= 10){
                    ordrenoeuds += "\nLe noeud : "+ i + " est d'ordre : "+Ordre;
                    entrypoints += i + " ";
                }
                entrees[i] = 10;
            }
            // Si on trouve un V
            else
                {
                finderecherche = true;
                VorF[i] = true;
                }

                if(colonnematrice[i] == "") {
                    finderecherche = true;
                    nblinetoremove--;
                }
        }

        if (lineremoveds[nbtour] == nbsom -1 && oneeentree == true){
            //System.out.println("\nVotre circuit ne possède qu'une seule sortie");
            ordonnance();// On vérifie sur le graphe ne contenant pas de circuit est bien ordonné
        }

        if(entrypoints!=""){
        System.out.println("\nL'ordre actuel est : "+Ordre);
        System.out.println("La ou les entrées sont/est :"+entrypoints);}
        //System.out.println(lineremoveds[nbtour]);


        if (Firstcycle == false){

        if (nbentree > 1) System.out.println("\nLes entrées de votre graphe sont : " + entree + "\n");
        else if (nbentree == 1) System.out.println("\nL'entrée de votre graphe est : " + entree + "\n");
        oneeentree = true;}

        while (circuit == null) {
            for (int i = 0; i < nbsom; i++) {
                // Si ma ligne contient un V
                if (VorF[i] == true) {

                    for (int j = 0; j < nbsom; j++) {
                        if (VorF[j] == false) {
                            // Je remplace les colonnes ne contennant que des F par ""
                            lignematrice[i] = replaceCharAt(lignematrice[i], (j * 3) + 3, " ");
                            colonnematrice[i] = replaceCharAt(colonnematrice[i], (j * 3) + 3, " ");
                        }

                    }
                }
                //Si ma ligne ne contient pas de V
                else if (VorF[i] == false){
                    lignematrice[i] = "";
                    colonnematrice[i] = "";
                    circuit = true;
                }

                if (nblinetoremove == 0){
                    // On ne peut pu enlever de ligne et qu'il en reste
                    finderecherche =false;
                    circuit = true;
                System.out.println("\nVotre graphe contient un circuit");
                break;}

                if (lineremoved == nbsom){
                    System.out.println("\nVotre graphe ne contient pas de circuit");
                    circuit = false;
                    finderecherche = false;
                    break;}
            }
        }

        Firstcycle = true;

        if (finderecherche == true)
        {
            nblinetoremove = 0;
            Ordre++;
            circuit();}

            if (circuit == false){
            System.out.println(ordrenoeuds);
            }
    if (ordonnacefct == true){
        if (ordonnance == true && ordon == false){
            System.out.println("\nVotre graphe est correctement ordonné");
            ordon =true;}
        else if(ordon == false && ordonnance==false) {
            System.out.println("\nVotre graphe est mal ordonné");
        ordon =true;}
    }
    }

    public void ordonnance(){

        ordonnacefct = true;
        for (int u = 0; u < nbsom; u++) {
            int nbpois = 0;
            int[] poidsline = new int[40];
            for (int j = 0; j < nbsom; j++) {
                if (tablines[u][j].indexOf("V") == -1) {
                } else {
                    poidsline[nbpois] = tabligne[u][j]; // On rentre ici les poids de chaque ligne dans le tableau
                    if (u == 0 && poidsline[nbpois] != 0) {
                        ordonnance = false;
                        //System.out.println("ici1");
                    }
                    nbpois++;
                }
            }

            if (ordonnance != false) {

                for (int i = 1; i < nbpois; i++) {
                    System.out.println(poidsline[i] + " , " + poidsline[i - 1]);
                    if (poidsline[i] == poidsline[i - 1]) {
                        ordonnance = true;
                    }// On vérifie que les poids sont les memes porur chaque ligne
                    else if (poidsline[i] < 0) {
                        ordonnance = false;
                        //System.out.println("ici2");
                    }
                }
            }
        }

    }

    // Fonction permettant de remplacer un caractère d'une ligne à une position donnée par un autre
    public String replaceCharAt(String s, int pos, String c) {
        return s.substring(0,pos) + c + s.substring(pos+1);
    }
}