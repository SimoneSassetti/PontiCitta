package it.polito.tdp.radio.db;

import it.polito.tdp.radio.bean.Citta;
import it.polito.tdp.radio.bean.Ponte;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe DAO per l'accesso al database {@code pontiradio}
 * 
 * @author Fulvio
 * 
 */

public class RadioDAO {

	/**
	 * Interroga il database e restituisce la {@link Citta} che ha il campo
	 * {@code idCitta} pari a quello specificato
	 * 
	 * @param idCitta il valore dell'ID Città da ricercare
	 * @return la {@link Citta} cercata, oppure {@code null} se non esiste
	 */
	public Citta getCittaById(int idCitta) {
		
		throw new UnsupportedOperationException("Method not implemented - sorry") ;
		
		//return null;

	}

	/**
	 * Interroga il database e restituisce tutti i dati nella tabella
	 * {@code citta} sotto forma di un {@link ArrayList} di {@link Citta}.
	 * 
	 * @return la {@link ArrayList} di {@link Citta}
	 */

	public List<Citta> getAllCitta() {
		
		final String sql = "SELECT * FROM citta order by city";

		List<Citta> citta = new ArrayList<Citta>();

		try {
			Connection conn = DBConnect.getInstance().getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				Citta c = new Citta(
						rs.getInt("idCitta"),
						rs.getString("call"),
						rs.getString("city")
						);
				citta.add(c);
			}
			return citta;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * Interroga il database e restituisce tutti i dati nella tabella
	 * {@code ponte} sotto forma di un {@link ArrayList} di {@link Ponte}.
	 * 
	 * @return la {@link ArrayList} di {@link Ponte}
	 */

	public List<Ponte> getAllPonte() {
		
		final String sql = "SELECT * FROM ponte";

		List<Ponte> ponti = new ArrayList<Ponte>();

		try {
			Connection conn = DBConnect.getInstance().getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				Ponte p = new Ponte(
						rs.getInt("idPonte"),
						rs.getString("output"),
						rs.getString("call"),
						rs.getDouble("pl"),
						rs.getString("comments")
						);
				ponti.add(p);
			}
			return ponti;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public List<Ponte> getPontiInComune(Citta c1, Citta c2) {
		final String sql = "SELECT p.*\r\n" + 
				"from citta as c1,copertura as cop, ponte as p\r\n" + 
				"where c1.idCitta=cop.idCitta and cop.idPonte=p.idPonte and c1.idCitta=? and p.idPonte in(\r\n" + 
				"SELECT p.idPonte\r\n" + 
				"from citta as c2,copertura as cop, ponte as p\r\n" + 
				"where c2.idCitta=cop.idCitta and cop.idPonte=p.idPonte and c2.idCitta=?)";

		List<Ponte> ponti = new ArrayList<Ponte>();

		try {
			Connection conn = DBConnect.getInstance().getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, c1.getIdCitta());
			st.setInt(2, c2.getIdCitta());
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				Ponte p = new Ponte(
						rs.getInt("idPonte"),
						rs.getString("output"),
						rs.getString("call"),
						rs.getDouble("pl"),
						rs.getString("comments")
						);
				ponti.add(p);
			}
			return ponti;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public List<Ponte> getPontiPerCitta(Citta c1) {
		final String sql = "select p.*\r\n" + 
				"from citta as c, copertura as cop, ponte as p\r\n" + 
				"where c.idCitta=cop.idCitta and cop.idPonte=p.idPonte and c.idCitta=?";

		List<Ponte> ponti = new ArrayList<Ponte>();

		try {
			Connection conn = DBConnect.getInstance().getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, c1.getIdCitta());
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				Ponte p = new Ponte(
						rs.getInt("idPonte"),
						rs.getString("output"),
						rs.getString("call"),
						rs.getDouble("pl"),
						rs.getString("comments")
						);
				ponti.add(p);
			}
			return ponti;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}


}
