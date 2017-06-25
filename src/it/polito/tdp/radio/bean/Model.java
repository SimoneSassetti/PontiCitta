package it.polito.tdp.radio.bean;

import java.util.*;

import it.polito.tdp.radio.db.RadioDAO;

public class Model {
	
	private List<Citta> citta;
	private RadioDAO dao;
	private List<Ponte> ponti;
	
	private Set<Ponte> tuttiPonti;
	private List<Ponte> pontiCitta1;
	private List<Ponte> pontiCitta2;
	private List<Ponte> pontiCitta3;
	
	public Model(){
		dao=new RadioDAO();
	}
	
	public List<Citta> getCitta(){
		if(citta==null){
			citta=dao.getAllCitta();
		}
		return citta;
	}

	public List<Ponte> cercaPonti(Citta c1, Citta c2) {
		ponti=new ArrayList<>();
		ponti=dao.getPontiInComune(c1,c2);
		return ponti;
	}

	public List<Ponte> trovaCopertura(Citta c1, Citta c2, Citta c3) {
		
		pontiCitta1=new ArrayList<Ponte>(dao.getPontiPerCitta(c1));
		pontiCitta2=new ArrayList<Ponte>(dao.getPontiPerCitta(c2));
		pontiCitta3=new ArrayList<Ponte>(dao.getPontiPerCitta(c3));
		List<Ponte> parziale=new ArrayList<>();
		List<Ponte> migliore=new ArrayList<>();
		
		tuttiPonti=new HashSet<>();
		tuttiPonti.addAll(pontiCitta1);
		tuttiPonti.addAll(pontiCitta2);
		tuttiPonti.addAll(pontiCitta3);
		
		recursive(parziale,migliore);
		
		return migliore;
	}

	private void recursive(List<Ponte> parziale, List<Ponte> migliore) {
		
		System.out.println(parziale);
		if(parziale.size()<=3){
			if(presente(parziale)){
				if(parziale.size()<migliore.size() || migliore.isEmpty()){
					migliore.clear();
					migliore.addAll(parziale);
				}
			}
		}
		
		for(Ponte p: tuttiPonti){
			if(parziale.isEmpty() || p.compareTo(parziale.get(parziale.size()-1))>0){
				parziale.add(p);
				recursive(parziale,migliore);
				parziale.remove(p);
			}
		}
	}

	private boolean presente(List<Ponte> parziale) {
		int countA=0;
		int countB=0;
		int countC=0;
		for(Ponte p: parziale){
			if(pontiCitta1.contains(p)){
				countA++;
			}
			if(pontiCitta2.contains(p)){
				countB++;
			}
			if(pontiCitta3.contains(p)){
				countC++;
			}
		}
		if(countA>0 && countB>0 && countC>0){
			return true;
		}
		return false;
	}
}
