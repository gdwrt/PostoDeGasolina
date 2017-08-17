package com.postoGasolina.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.postoGasolina.model.Unidade_medida;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class UnidadeMedidaDao implements InterfaceDao<Unidade_medida>{

	private Connection connetion;
	private PreparedStatement statement;
	private String sql;
	private ResultSet rs;
	
	
	public void cadastrar(Unidade_medida unidadeMedida) throws ClassNotFoundException, SQLException{ 
		connetion = ConexaoUtil.getInstance().getConnection();
		
		sql = "insert into tb_unidade_medida(nome)values(?)";
		
		statement = connetion.prepareStatement(sql);
		
		statement.setString(1, unidadeMedida.getNome());
		
		statement.execute();
		
		connetion.close();
		statement.close();
		
		
		
	}
	public void remover(int id) throws ClassNotFoundException, SQLException{
		connetion = ConexaoUtil.getInstance().getConnection();
		
		sql = "delete from tb_unidade_medida where id_unidade_medida=?";
		
		statement = connetion.prepareStatement(sql);
		
		statement.setInt(1, id);
		statement.execute();
		
		statement.close();
		connetion.close();
		
	}
	public ObservableList<Unidade_medida> listar() throws ClassNotFoundException, SQLException{
		ObservableList<Unidade_medida> lista_unidade_medida = FXCollections.observableArrayList();
		
		connetion = ConexaoUtil.getInstance().getConnection();
		
		sql = "select * from tb_unidade_medida";
		
		statement = connetion.prepareStatement(sql);
		
		rs = statement.executeQuery();
		
		while (rs.next()) {
			lista_unidade_medida.add(new Unidade_medida(rs.getInt("id_unidade_medida"), rs.getString("nome")));
		}
		
		connetion.close();
		statement.close();
		rs.close();
		
		return lista_unidade_medida;
	}
	@Override
	public void alterar(Unidade_medida t) throws SQLException, ClassNotFoundException {
		// TODO Auto-generated method stub
		
	}


}
