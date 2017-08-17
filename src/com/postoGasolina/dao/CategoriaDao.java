package com.postoGasolina.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.postoGasolina.model.Categoria;
import com.postoGasolina.model.Categoria;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CategoriaDao implements InterfaceDao<Categoria>{

	private Connection connetion;
	private PreparedStatement statement;
	private String sql;
	private ResultSet rs;
	
	
	public void cadastrar(Categoria unidadeMedida) throws ClassNotFoundException, SQLException{ 
		connetion = ConexaoUtil.getInstance().getConnection();
		
		sql = "insert into tb_categoria(nome)values(?)";
		
		statement = connetion.prepareStatement(sql);
		
		statement.setString(1, unidadeMedida.getNome());
		
		statement.execute();
		
		connetion.close();
		statement.close();
		
		
		
	}
	public void remover(int id) throws ClassNotFoundException, SQLException{
		connetion = ConexaoUtil.getInstance().getConnection();
		
		sql = "delete from tb_categoria where id_categoria=?";
		
		statement = connetion.prepareStatement(sql);
		
		statement.setInt(1, id);
		statement.execute();
		
		statement.close();
		connetion.close();
		
	}
	public ObservableList<Categoria> listar() throws ClassNotFoundException, SQLException{
		ObservableList<Categoria> lista_categoria = FXCollections.observableArrayList(); 
		
		connetion = ConexaoUtil.getInstance().getConnection();
		
		sql = "select * from tb_categoria";
		
		statement = connetion.prepareStatement(sql);
		
		rs = statement.executeQuery();
		
		while (rs.next()) {
			lista_categoria.add(new Categoria(rs.getInt("id_categoria"), rs.getString("nome")));
		}
		
		connetion.close();
		statement.close();
		rs.close();
		
		return lista_categoria;
	}
	@Override
	public void alterar(Categoria t) throws SQLException, ClassNotFoundException {
		// TODO Auto-generated method stub
		
	}


}
