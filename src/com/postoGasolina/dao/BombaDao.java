package com.postoGasolina.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.postoGasolina.model.Bico;
import com.postoGasolina.model.Bomba;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class BombaDao implements InterfaceDao<Bomba>{ 
	private Connection connetion;
	private PreparedStatement statement;
	private String sql;
	private ResultSet rs;
	private ResultSet rs2;
	private int idBomba;

	public void cadastrar(Bomba bomba) throws ClassNotFoundException, SQLException {
		connetion = ConexaoUtil.getInstance().getConnection();

		sql = "insert into tb_bomba(descricao)values(?)";

		statement = connetion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

		statement.setString(1, bomba.getDescricao());
		statement.execute();

		rs = statement.getGeneratedKeys();
		if (rs.next()) {
			idBomba = rs.getInt(1);
		}

		bomba.getLista_bicos().forEach(bico -> {
			
			try {
				sql = "insert into tb_bico(id_bomba_fk, descricao)values(?,?)";
				statement = connetion.prepareStatement(sql);
				statement.setInt(1, idBomba);
				statement.setString(2, bico.getDescricao());
				statement.execute();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		});

		connetion.close();
		statement.close();
		rs.close();

	}

	public void alterar(Bomba bomba) throws ClassNotFoundException, SQLException {
		connetion = ConexaoUtil.getInstance().getConnection();

		sql = "update tb_bomba set descricao=? where id_bomba=?";

		statement = connetion.prepareStatement(sql);

		statement.setString(1, bomba.getDescricao());
		statement.setInt(2, bomba.getId_bomba());
		statement.execute();

		connetion.close();
		statement.close();

	}

	public void remover(int id) throws ClassNotFoundException, SQLException {
		connetion = ConexaoUtil.getInstance().getConnection();

		sql = "delete from tb_bico where id_bomba_fk=?";
		statement = connetion.prepareStatement(sql);
		statement.setInt(1, id);
		statement.execute();

		sql = "delete from tb_bomba where id_bomba=?";
		statement = connetion.prepareStatement(sql);
		statement.setInt(1, id);
		statement.execute();

		statement.close();
		connetion.close();

	}

	public ObservableList<Bomba> listar() throws ClassNotFoundException, SQLException {
		ObservableList<Bomba> lista_bombas = FXCollections.observableArrayList();

		connetion = ConexaoUtil.getInstance().getConnection();

		sql = "select * from tb_bomba";

		statement = connetion.prepareStatement(sql);

		rs = statement.executeQuery();

		while (rs.next()) {

			ObservableList<Bico> lista_bicos = FXCollections.observableArrayList();
			sql = "select * from tb_bico where id_bomba_fk=?";

			statement = connetion.prepareStatement(sql);
			statement.setInt(1, rs.getInt("id_bomba"));
			rs2 = statement.executeQuery();
			while (rs2.next()) {
				lista_bicos.add(new Bico(rs2.getInt("id_bico"), rs2.getInt("id_bomba_fk"), rs2.getString("descricao")));
			}
			lista_bombas.add(new Bomba(rs.getInt("id_bomba"), rs.getString("descricao"), lista_bicos));

			rs2.close();
		}

		connetion.close();
		statement.close();
		rs.close();

		return lista_bombas;
	}

	public void cadastrarBico(Bico bico) throws SQLException, ClassNotFoundException {
		connetion = ConexaoUtil.getInstance().getConnection();

		sql = "insert into tb_bico(id_bomba_fk,descricao)values(?,?)";

		statement = connetion.prepareStatement(sql);
		statement.setInt(1, bico.getId_bomba());
		statement.setString(2, bico.getDescricao());
		statement.execute();

		connetion.close();
		statement.close();
	}

	public void excluirBico(int id) throws SQLException, ClassNotFoundException {
		connetion = ConexaoUtil.getInstance().getConnection();
		sql = "delete from tb_bico where id_bico=?";
		statement = connetion.prepareStatement(sql);
		statement.setInt(1, id);
		statement.execute();
		
		connetion.close();
		statement.close();
	}
}
