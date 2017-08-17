package com.postoGasolina.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.postoGasolina.model.Bico;
import com.postoGasolina.model.Bomba;
import com.postoGasolina.model.Combustivel;
import com.postoGasolina.model.Tipo_combustivel;
import com.postoGasolina.model.Unidade_medida;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CombustiveisDao implements InterfaceDao<Combustivel> {
	private Connection connection;
	private String sql;
	private PreparedStatement statement;
	private ResultSet rs;
	private ResultSet rs2;
	private ResultSet rs3;
	private ResultSet rs4;
	private ResultSet rs5;
	private ObservableList<Combustivel> listaCombustiveis;
	private int id_combustivel;
	private int id_tipoCombustivel;

	@Override
	public void cadastrar(Combustivel Combustivel) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
	
			// prepara conexão
			connection = ConexaoUtil.getInstance().getConnection();

			// ADICIONA PESSOA
			sql = "insert into tb_combustivel(id_tipo_combustivel_fk, id_bomba_fk, id_bico_fk, descricao, preco_venda)values(?,?,?,?,?)";

			// chama a conexão e retorna id
			statement = connection.prepareStatement(sql);
			statement.setInt(1, Combustivel.getTipoCombustivel().getId_tipo_combustivel());
			statement.setInt(2, Combustivel.getBomba().getId_bomba());
			statement.setInt(3, Combustivel.getBico().getId_bico());
			statement.setString(4, Combustivel.getDescricao());
			statement.setBigDecimal(5, Combustivel.getPreco_venda());
			statement.execute();

			statement.close();
			connection.close();
	}

	@Override
	public void alterar(Combustivel Combustivel) throws SQLException, ClassNotFoundException {

			// prepara conexão
			connection = ConexaoUtil.getInstance().getConnection();

			// ADICIONA PESSOA
			sql = "update tb_combustivel set id_tipo_combustivel_fk=?, id_bomba_fk=?, id_bico_fk=?, descricao=?, preco_venda=? where id_combustivel=?";

			// chama a conexão e retorna id
			statement = connection.prepareStatement(sql);
			statement.setInt(1, Combustivel.getTipoCombustivel().getId_tipo_combustivel());
			statement.setInt(2, Combustivel.getBomba().getId_bomba());
			statement.setInt(3, Combustivel.getBico().getId_bico());
			statement.setString(4, Combustivel.getDescricao());
			statement.setBigDecimal(5, Combustivel.getPreco_venda());
			statement.setInt(6, Combustivel.getId_combustivel());
			statement.execute();

			statement.close();
			connection.close();

	}

	@Override
	public void remover(int id) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		connection = ConexaoUtil.getInstance().getConnection();

		sql = "delete from tb_combustivel where id_combustivel=?";

		statement = connection.prepareStatement(sql);
		statement.setInt(1, id);
		statement.execute();

		connection.close();
		statement.close();

	}

	@Override
	public ObservableList<Combustivel> listar() throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub

		listaCombustiveis = FXCollections.observableArrayList();

		connection = ConexaoUtil.getInstance().getConnection();

		// combustivel
		sql = "SELECT * FROM tb_combustivel combustivel inner join tb_tipo_combustivel tipo on(combustivel.id_tipo_combustivel_fk = tipo.id_tipo_combustivel)";
		statement = connection.prepareStatement(sql);

		rs = statement.executeQuery();
		while (rs.next()) {
			
			sql = "select * from tb_unidade_medida where id_unidade_medida=?";
			statement = connection.prepareStatement(sql);
			statement.setInt(1, rs.getInt("id_unidade_medida_fk"));
			rs2 = statement.executeQuery();
			rs2.next();
			
			sql = "select * from tb_bomba where id_bomba=?";
			statement = connection.prepareStatement(sql);
			statement.setInt(1, rs.getInt("id_bomba_fk"));
			rs3 = statement.executeQuery();
			rs3.next();
			
			sql = "select * from tb_bico where id_bico=?";
			statement = connection.prepareStatement(sql);
			statement.setInt(1, rs.getInt("id_bico_fk"));
			rs4 = statement.executeQuery();
			rs4.next();
			
			sql = "select * from tb_bico where id_bomba_fk=?";
			statement = connection.prepareStatement(sql);
			statement.setInt(1, rs.getInt("id_bomba_fk"));
			rs5 = statement.executeQuery();
			
			ObservableList<Bico> lista_bicos = FXCollections.observableArrayList();
			
			while(rs5.next()){
				lista_bicos.add(new Bico(rs5.getInt("id_bico"), rs5.getInt("id_bomba_fk"), rs5.getString("descricao")));
			}
			
			listaCombustiveis.add(
					new Combustivel(rs.getInt("id_combustivel"),
							
					new Tipo_combustivel(rs.getInt("id_tipo_combustivel"), 
					new Unidade_medida(rs2.getInt("id_unidade_medida"), rs2.getString("nome")),
					rs.getString("nome"), rs.getBigDecimal("quantidade"), rs.getBigDecimal("estoque_maximo"), rs.getBigDecimal("estoque_minimo")),
					
					new Bomba(rs3.getInt("id_bomba"), rs3.getString("descricao"), lista_bicos),
					new Bico(rs4.getInt("id_bico"), rs4.getInt("id_bomba_fk"), rs4.getString("descricao")),
					rs.getString("descricao"), rs.getBigDecimal("preco_venda"))); 
			

			rs2.close();
			rs3.close();
			rs4.close();
			rs5.close();
			
		}
		connection.close();
		statement.close();
		rs.close();

		return listaCombustiveis;
	}

	public ObservableList<Combustivel> pesquisar(int id) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub

		listaCombustiveis = FXCollections.observableArrayList();

		connection = ConexaoUtil.getInstance().getConnection();

		// combustivel
		sql = "SELECT * FROM tb_combustivel combustivel inner join tb_tipo_combustivel tipo on(combustivel.id_tipo_combustivel_fk = tipo.id_tipo_combustivel) where id_combustivel=?";
		statement = connection.prepareStatement(sql);
		statement.setInt(1, id); 

		rs = statement.executeQuery();
		while (rs.next()) {
			
			sql = "select * from tb_unidade_medida where id_unidade_medida=?";
			statement = connection.prepareStatement(sql);
			statement.setInt(1, rs.getInt("id_unidade_medida_fk"));
			rs2 = statement.executeQuery();
			rs2.next();
			
			sql = "select * from tb_bomba where id_bomba=?";
			statement = connection.prepareStatement(sql);
			statement.setInt(1, rs.getInt("id_bomba_fk"));
			rs3 = statement.executeQuery();
			rs3.next();
			
			sql = "select * from tb_bico where id_bico=?";
			statement = connection.prepareStatement(sql);
			statement.setInt(1, rs.getInt("id_bico_fk"));
			rs4 = statement.executeQuery();
			rs4.next();
			
			sql = "select * from tb_bico where id_bomba_fk=?";
			statement = connection.prepareStatement(sql);
			statement.setInt(1, rs.getInt("id_bomba_fk"));
			rs5 = statement.executeQuery();
			
			ObservableList<Bico> lista_bicos = FXCollections.observableArrayList();
			
			while(rs5.next()){
				lista_bicos.add(new Bico(rs5.getInt("id_bico"), rs5.getInt("id_bomba_fk"), rs5.getString("descricao")));
			}
			
			listaCombustiveis.add(
					new Combustivel(rs.getInt("id_combustivel"),
							
					new Tipo_combustivel(rs.getInt("id_tipo_combustivel"), 
					new Unidade_medida(rs2.getInt("id_unidade_medida"), rs2.getString("nome")),
					rs.getString("nome"), rs.getBigDecimal("quantidade"), rs.getBigDecimal("estoque_maximo"), rs.getBigDecimal("estoque_minimo")),
					
					new Bomba(rs3.getInt("id_bomba"), rs3.getString("descricao"), lista_bicos),
					new Bico(rs4.getInt("id_bico"), rs4.getInt("id_bomba_fk"), rs4.getString("descricao")),
					rs.getString("descricao"), rs.getBigDecimal("preco_venda"))); 
			

			rs2.close();
			rs3.close();
			rs4.close();
			rs5.close();
			
		}
		connection.close();
		statement.close();
		rs.close();

		return listaCombustiveis;
	}
}
