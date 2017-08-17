package com.postoGasolina.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.postoGasolina.model.Cargo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CargoDao implements InterfaceDao<Cargo>{
	
	private Connection connetion;
	private PreparedStatement statement;
	private String sql;
	private ResultSet rs;
	
	
	public void cadastrar(Cargo cargo) throws ClassNotFoundException, SQLException{
		connetion = ConexaoUtil.getInstance().getConnection();
		
		sql = "insert into tb_cargo(nome)values(?)";
		
		statement = connetion.prepareStatement(sql);
		
		statement.setString(1, cargo.getNome());
		
		statement.execute();
		
		connetion.close();
		statement.close();
		
		
		
	}
	public void remover(int id) throws ClassNotFoundException, SQLException{
		connetion = ConexaoUtil.getInstance().getConnection();
		
		sql = "delete from tb_cargo where id_cargo=?";
		
		statement = connetion.prepareStatement(sql);
		
		statement.setInt(1, id);
		statement.execute();
		
		statement.close();
		connetion.close();
		
	}
	public ObservableList<Cargo> listar() throws ClassNotFoundException, SQLException{
		ObservableList<Cargo> lista_cargos = FXCollections.observableArrayList();
		
		connetion = ConexaoUtil.getInstance().getConnection();
		
		sql = "select * from tb_cargo";
		
		statement = connetion.prepareStatement(sql);
		
		rs = statement.executeQuery();
		
		while (rs.next()) {
			lista_cargos.add(new Cargo(rs.getInt("id_cargo"), rs.getString("nome")));
		}
		
		connetion.close();
		statement.close();
		rs.close();
		
		return lista_cargos;
	}
	@Override
	public void alterar(Cargo t) throws SQLException, ClassNotFoundException {
		// TODO Auto-generated method stub
		
	}

}
