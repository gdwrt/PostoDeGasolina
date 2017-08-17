package com.postoGasolina.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.postoGasolina.model.Categoria;
import com.postoGasolina.model.Produto;
import com.postoGasolina.model.Unidade_medida;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ProdutosDao implements InterfaceDao<Produto>{
	private Connection connection;
	private String sql;
	private PreparedStatement statement;
	private ResultSet rs;
	private ResultSet rs2;

	public void cadastrar(Produto produto) throws ClassNotFoundException, SQLException {
		connection = ConexaoUtil.getInstance().getConnection();
		
		sql = "insert into tb_produto(codigo_produto,id_categoria_fk, id_unidade_medida_fk, descricao,estoque_disponivel,preco_venda, estoque_max, estoque_min, nao_controlar_estoque)values(?,?,?,?,?,?,?,?,?)";
		
		statement = connection.prepareStatement(sql);
		statement.setString(1, produto.getCodigo_produto());
		statement.setInt(2, produto.getCategoria().getId_categoria());
		statement.setInt(3, produto.getUnidade_medida().getId_unidade_medida());
		statement.setString(4, produto.getDescricao());
		statement.setBigDecimal(5, produto.getEstoque_disponivel());
		statement.setBigDecimal(6, produto.getPreco_venda());
		statement.setBigDecimal(7, produto.getEstoque_max());
		statement.setBigDecimal(8, produto.getEstoque_min());
		statement.setBoolean(9, produto.isNao_controlar_estoque());
		
		
		statement.execute();
		
		statement.close();
		connection.close();
		

	}


	public void alterar(Produto produto) throws SQLException, ClassNotFoundException {
	connection = ConexaoUtil.getInstance().getConnection();
		
		sql = "update tb_produto set codigo_produto=?,id_categoria_fk=?, id_unidade_medida_fk=?, descricao=?,estoque_disponivel=?,preco_venda=?, estoque_max=?, estoque_min=?, nao_controlar_estoque=? where id_produto=?";
		
		statement = connection.prepareStatement(sql);
		statement.setString(1, produto.getCodigo_produto());
		statement.setInt(2, produto.getCategoria().getId_categoria());
		statement.setInt(3, produto.getUnidade_medida().getId_unidade_medida());
		statement.setString(4, produto.getDescricao());
		statement.setBigDecimal(5, produto.getEstoque_disponivel());
		statement.setBigDecimal(6, produto.getPreco_venda());
		statement.setBigDecimal(7, produto.getEstoque_max());
		statement.setBigDecimal(8, produto.getEstoque_min());
		statement.setBoolean(9, produto.isNao_controlar_estoque());
		statement.setInt(10, produto.getId_produto());
		
		
		statement.execute();
		
		statement.close();
		connection.close();
	}


	public void remover(int id) throws ClassNotFoundException, SQLException {
	connection = ConexaoUtil.getInstance().getConnection();
		
		sql = "delete from tb_produto where id_produto=?";
		statement = connection.prepareStatement(sql);
		statement.setInt(1,id);
		statement.execute();
		
		connection.close();
		statement.close();

	}


	public ObservableList<Produto> listar() throws ClassNotFoundException, SQLException { 
		connection = ConexaoUtil.getInstance().getConnection();
		ObservableList<Produto> lista_produtos = FXCollections.observableArrayList();
		sql= "select * from tb_produto produto inner join tb_categoria categoria on(produto.id_categoria_fk = categoria.id_categoria)";
		statement = connection.prepareStatement(sql);
		rs = statement.executeQuery();
		
		while(rs.next()){
			sql = "select * from tb_unidade_medida where id_unidade_medida=?";
			statement = connection.prepareStatement(sql);
			statement.setInt(1, rs.getInt("id_unidade_medida_fk")); 
			rs2 = statement.executeQuery();
			rs2.next();
			
			lista_produtos.add(new Produto(rs.getInt("id_produto"), rs.getString("codigo_produto"),
					new Categoria(rs.getInt("id_categoria"), rs.getString("nome")), 
					new Unidade_medida(rs2.getInt("id_unidade_medida"), rs2.getString("nome")), 
					rs.getString("descricao"), rs.getBigDecimal("estoque_disponivel"), rs.getBigDecimal("preco_Venda"),
					rs.getBigDecimal("estoque_max"), rs.getBigDecimal("estoque_min"), rs.getBoolean("nao_controlar_estoque")));
			rs2.close();
		}
		connection.close();
		statement.close();
		rs.close();

		return lista_produtos;
	}
	public ObservableList<Produto> pesquisar(int id) throws ClassNotFoundException, SQLException { 
		connection = ConexaoUtil.getInstance().getConnection();
		ObservableList<Produto> lista_produtos = FXCollections.observableArrayList();
		sql= "select * from tb_produto produto inner join tb_categoria categoria on(produto.id_categoria_fk = categoria.id_categoria) where id_produto=?";
		statement = connection.prepareStatement(sql);
		statement.setInt(1, id); 
		rs = statement.executeQuery();
		
		while(rs.next()){
			sql = "select * from tb_unidade_medida where id_unidade_medida=?";
			statement = connection.prepareStatement(sql);
			statement.setInt(1, rs.getInt("id_unidade_medida_fk")); 
			rs2 = statement.executeQuery();
			rs2.next();
			
			lista_produtos.add(new Produto(rs.getInt("id_produto"), rs.getString("codigo_produto"),
					new Categoria(rs.getInt("id_categoria"), rs.getString("nome")), 
					new Unidade_medida(rs2.getInt("id_unidade_medida"), rs2.getString("nome")), 
					rs.getString("descricao"), rs.getBigDecimal("estoque_disponivel"), rs.getBigDecimal("preco_Venda"),
					rs.getBigDecimal("estoque_max"), rs.getBigDecimal("estoque_min"), rs.getBoolean("nao_controlar_estoque")));
			rs2.close();
		}
		connection.close();
		statement.close();
		rs.close();

		return lista_produtos;
	}
}
