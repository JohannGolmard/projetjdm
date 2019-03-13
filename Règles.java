import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import requeterrezo.Mot;
import requeterrezo.RequeterRezo;
import requeterrezo.RequeterRezoDump;
import requeterrezo.Resultat;

public class Règles {

	String motif,motift;
	ArrayList<String> contraintesPourX = new ArrayList<>();
	ArrayList<String> contraintesPourY = new ArrayList<>();
	ArrayList<String> contraintesXY = new ArrayList<>();
	ArrayList<String> contraintesYX = new ArrayList<>();
	
	public String getMotif() {
		return motif;
	}

	public void setMotif(String motif) {
		this.motif = motif;
	}

	public String getMotift() {
		return motift;
	}

	public void setMotift(String motift) {
		this.motift = motift;
	}


	public ArrayList<String> getContraintesPourX() {
		return contraintesPourX;
	}

	public void setContraintesPourX(ArrayList<String> contraintesPourX) {
		this.contraintesPourX = contraintesPourX;
	}

	public ArrayList<String> getContraintesPourY() {
		return contraintesPourY;
	}

	public void setContraintesPourY(ArrayList<String> contraintesPourY) {
		this.contraintesPourY = contraintesPourY;
	}

	public ArrayList<String> getContraintesXY() {
		return contraintesXY;
	}

	public void setContraintesXY(ArrayList<String> contraintesXY) {
		this.contraintesXY = contraintesXY;
	}

	public Règles(String motif, String motift, ArrayList<String> c) {
		this.motif=motif.substring(1, motif.length())+"$";
		this.motift=motift.substring(1, motif.length());
		for(String l : c) {
			String[] temp = l.split(" ");
			if(temp[0].equals("$y")) {
				String t = l.substring(3);
				this.contraintesPourY.add(t);
			}
			else if(temp[temp.length-1].equals("$y") && temp[0].equals("$x")) {
				this.contraintesXY.add(l);
			}
			else if(temp[temp.length-1].equals("$x") && temp[0].equals("$y")){
				this.contraintesYX.add(l);
			}
			else {
				String t = l.substring(3);
				this.contraintesPourX.add(t);
			}
		}
	}
	public boolean testLine(String mot) {
		Pattern pattern = Pattern.compile(this.motif);
		Matcher matcher = pattern.matcher(mot);
		String temp = mot;
		while(matcher.find()) {
			System.out.println(mot.replaceAll(this.motif, this.motift));
			return this.testConstraint(temp,mot);
		}
		return false;
	}
	public boolean testConstraint(String mot,String motDeriv) {
		
		String req="";
		
		RequeterRezo rezo = new RequeterRezoDump();
		ArrayList<Resultat> rez = rezo.requeteMultiple(mot, req);
		//Mot mot = rez.getMot();
		
		return false;	
	}
	
	public String toString() {
		String res="";
		res+="Motif : "+this.motif+", Motif trouvé : "+this.motift;
		if(this.contraintesPourX.size() > 0) {
			for(String l : this.contraintesPourX) {
				res+="\nContrainte pour x : "+l;
			}
		}
		if(this.contraintesPourY.size() > 0) {
			for(String l : this.contraintesPourY) {
				res+="\nContrainte pour y : "+l;
			}
		}
		if(this.contraintesXY.size() > 0) {
			for(String l : this.contraintesXY) {
				res+="\nContrainte pour x -> y : "+l;
			}
		}
		if(this.contraintesYX.size() > 0) {
			for(String l : this.contraintesYX) {
				res+="\nContrainte pour y -> x : "+l;
			}	
		}
		return res;
	}
	
}
