package com.postoGasolina.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.postoGasolina.model.Tipo_combustivel;
import com.postoGasolina.model.Unidade_medida;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sun.security.timestamp.Timestamper;

public class TipoCombustivelDao implements InterfaceDao<Tipo_combustivel>{
	
	private Connection connetion;
	private PreparedStatement statement;
	private String sql;
	private ResultSet rs;
	private ResultSet rs2;
	
	
	public void cadastrar(Tipo_combustivel tipo_combustivel) throws ClassNotFoundException, SQLException{
		connetion = ConexaoUtil.getInstance().getConnection();
		
		sql = "insert into tb_tipo_combustivel(id_unidade_medida_fk,nome, quantidade, estoque_maximo, estoque_minimo)values(?,?,?,?,?)";
		
		statement = connetion.prepareStatement(sql);
		
		statement.setInt(1, tipo_combustivel.getUnidade_medida().getId_unidade_medida());
		statement.setString(2, tipo_combustivel.getNome());
		statement.setBigDecimal(3, tipo_combustivel.getQuantidade());
		statement.setBigDecimal(4, tipo_combustivel.getEstoque_maximo());
		statement.setBigDecimal(5, tipo_combustivel.getEstoque_minimo());
		
		statement.execute();
		
		connetion.close();
		statement.close();
		
		
		
	}
	public void alterar(Tipo_combustivel tipo_combustivel) throws ClassNotFoundException, SQLException{
		connetion = ConexaoUtil.getInstance().getConnection();
		
		sql = "update tb_tipo_combustivel set id_unidade_medida_fk=?,nome=?, quantidade=?, estoque_maximo=?, estoque_minimo=? where id_tipo_combustivel=?";
		
		statement = connetion.prepareStatement(sql);
		
		statement.setInt(1, tipo_combustivel.getUnidade_medida().getId_unidade_medida());
		statement.setString(2, tipo_combustivel.getNome());
		statement.setBigDecimal(3, tipo_combustivel.getQuantidade());
		statement.setBigDecimal(4, tipo_combustivel.getEstoque_maximo());
		statement.setBigDecimal(5, tipo_combustivel.getEstoque_minimo());
		statement.setDouble(6, tipo_combustivel.getId_tipo_combustivel());
		
		statement.execute();
		
		connetion.close();
		statement.close();
		
		
		
	}
	public void remover(int id) throws ClassNotFoundException, SQLException{
		connetion = ConexaoUtil.getInstance().getConnection();
		
		sql = "delete from tb_tipo_combustivel where id_tipo_combustivel=?";
		
		statement = connetion.prepareStatement(sql);
		
		statement.setInt(1, id);
		statement.execute();
		
		statement.close();
		connetion.close();
		
	}
	public ObservableList<Tipo_combustivel> listar() throws ClassNotFoundException, SQLException{
		ObservableList<Tipo_combustivel> lista_tipo_combustivel = FXCollections.observableArrayList();
		
		connetion = ConexaoUtil.getInstance().getConnection();
		
		sql = "select * from tb_tipo_combustivel";
		
		statement = connetion.prepareStatement(sql);
		
		rs = statement.executeQuery();
		
		while (rs.next()) {
			
			sql = "select * from tb_unidade_medida where id_unidade_medida=?";
			
			statement = connetion.prepareStatement(sql);
			statement.setInt(1, rs.getInt("id_unidade_medida_fk")); 
			rs2 = statement.executeQuery();
			rs2.next();
			lista_tipo_combustivel.add(new Tipo_combustivel(rs.getInt("id_tipo_combustivel"), 
					new Unidade_medida(rs2.getInt("id_unidade_medida"), rs2.getString("nome")),
					rs.getString("nome"), rs.getBigDecimal("quantidade"), rs.getBigDecimal("estoque_maximo"), rs.getBigDecimal("estoque_minimo")));
			
			rs2.close();
		}
		
		connetion.close();
		statement.close();
		rs.close();
		
		return lista_tipo_combustivel;
	}


}
