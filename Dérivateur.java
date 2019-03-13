import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.*;

import requeterrezo.EtatCache;
import requeterrezo.Filtre;
import requeterrezo.Mot;
import requeterrezo.RequeterRezo;
import requeterrezo.RequeterRezoDump;
import requeterrezo.Resultat;
import requeterrezo.Voisin;

public class Dérivateur {

	public static void main(String[] args) throws IOException {		
		
		Pattern pattern;
		Matcher matcher;
		
		Scanner sc = new Scanner(System.in);
		RequeterRezo rezo = new RequeterRezoDump();
		System.out.println("Saisir le mot : ");
		String rqt=sc.nextLine();
		sc.close();
		//System.out.println("Catégorie du mot (Renseigner N pour nom, V pour verbe, ADJ pour adjectif et ADV pour adverbe) : ");
		//String categorie = sc.nextLine();
		
		/*switch (categorie) {
			case "N":
			case "V":
			case "ADJ":
			case "ADV":
			default: 
				System.out.println(" Mauvaise catégorie donnée : Catégorie du mot (Renseigner N pour nom, V pour verbe, ADJ pour adjectif et ADV pour adverbe) : ");
				categorie = sc.nextLine();
		}*/
		
		Resultat resultatRequete = rezo.requete(rqt);
		
		Mot mot = resultatRequete.getMot();

		ArrayList<Voisin> voisins = mot.getRelationsSortantesTypees("r_pos");
		int[] associative= {0,0,0,0}; // respectivement : Nom , Adjectif, Verbe, Adverbe, 
		System.out.println("Un(e) "+rqt+" : ");		
		
		for(Voisin voisin : voisins) {
			System.out.println(voisin.toString().split(":")[0]);
			switch(voisin.toString().split(":")[0]){
				case "Nom":
					associative[0]=1;
					Dérivateur.displayByNom();
					break;
				case "Adj":
					associative[1]=1;
					Dérivateur.displayByAdj();
					break;
				case "Ver":
					associative[2]=1;
					Dérivateur.displayByVer();
					break;
				case "Adv":
					associative[3]=1;
					Dérivateur.displayByAdv();
					break;
				default:
			}
			//System.out.println("\t"+voisin);
		}
		
		mot = resultatRequete.getMot();
		if(mot != null) {
			
		}
		
		/*EtatCache etatCache = resultatRequete.getEtatCache();
		System.out.println("L'état du cache de la requête est : "+etatCache);

		resultatRequete = rezo.requete("pomme de terre", "r_lieu", Filtre.RejeterRelationsEntrantes);
		mot = resultatRequete.getMot();
		if(mot != null) {
			voisins = mot.getRelationsSortantesTypees("r_lieu");
			System.out.println("Une pomme de terre peut se trouver dans les lieux suivants : ");		
			for(Voisin voisin : voisins) {
				System.out.println("\t"+voisin);
			}
		}*/
		ArrayList<Règles> règles = Dérivateur.openCSV();
		
		
	}
	public static void displayByNom() {
		
	}
	
	public static void displayByVer() {
			
		}
	
	public static void displayByAdj() {
		
	}
	
	public static void displayByAdv() {
		
	}
	public static ArrayList<Règles> openCSV() throws IOException {
		File f = new File("");
		String path = f.getAbsolutePath() + File.separator +"relation.csv"; // nom du fichier
		File file = new File(path);
		
		FileReader fr = new FileReader(file);
		BufferedReader br = new BufferedReader(fr);

		ArrayList<Règles> lignes = new ArrayList<>();
		
		for(String line = br.readLine(); line!=null;line=br.readLine()) {
			if(!line.contains("//")) {
				String[] temp = line.split(",");
				ArrayList<String> c = new ArrayList<>();
				for(int index=0;index<temp.length;index++) {
					if(index>4)
						c.add(temp[index]);
				}
				lignes.add(new Règles(temp[2], temp[3], c));
				
				/*ligne.add(line.split(","));
				test = line.split(",");
				espace = test[4].split(" ");
				espace[0] = espace[0].substring(3,espace[0].length());
				espace[2] = espace[2].substring(0, espace[2].length()-3);
				
				String[] motifs = test[2].split(",");
				motifs = motifs[0].split("\n");*/
			}
		}
		System.out.println(lignes.get(0));
		
		br.close();
		fr.close();
		return lignes;
	}

}
