package com.postoGasolina.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.postoGasolina.model.Orgao_governamental;
import com.postoGasolina.model.ConverterDate;
import com.postoGasolina.model.Endereco;
import com.postoGasolina.model.Telefone;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class OrgaoGovernamentalDao implements InterfaceDao<Orgao_governamental>{
	private Connection connection;
	private String sql;
	private PreparedStatement statement;
	private ResultSet rs;
	private ObservableList<Orgao_governamental> listaclientes;
	private int idEndereco = 0;
	private int id_orgao = 0;

	public void cadastrar(Orgao_governamental orgao) throws ClassNotFoundException, SQLException {

		// prepara conexão
		connection = ConexaoUtil.getInstance().getConnection();

		// ADICIONA ENDEREÇO
		sql = "insert into tb_endereco(cep, endereco, numero, complemento, bairro, uf, cidade)values(?,?,?,?,?,?,?)";

		statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		statement.setString(1, orgao.getEndereco().getCep());
		statement.setString(2, orgao.getEndereco().getEndereco());
		statement.setString(3, orgao.getEndereco().getNumero());
		statement.setString(4, orgao.getEndereco().getComplemento());
		statement.setString(5, orgao.getEndereco().getBairro());
		statement.setString(6, orgao.getEndereco().getEstado());
		statement.setString(7, orgao.getEndereco().getCidade());

		statement.execute();

		rs = statement.getGeneratedKeys();
		if (rs.next()) {
			idEndereco = rs.getInt(1);
		}

		// ADICIONA CLIENTE
		sql = "insert into tb_orgao(id_endereco_fk, nome, sigla, observacao, cnpj, email)values(?,?,?,?,?,?)";

		statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		statement.setInt(1, idEndereco);
		statement.setString(2, orgao.getNome());
		statement.setString(3, orgao.getSigla());
		statement.setString(4, orgao.getObservacao());
		statement.setString(5, orgao.getCnpj());
		statement.setString(6, orgao.getEmail());

		statement.execute();

		rs = statement.getGeneratedKeys();
		if (rs.next()) {
			id_orgao = rs.getInt(1);
		}

		orgao.getLista_telefones().forEach(telefone -> {

			try {
				sql = "insert into tb_telefone_orgao(id_orgao_fk,telefone)values(?,?)";
				statement = connection.prepareStatement(sql);
				statement.setInt(1, id_orgao);
				statement.setString(2, telefone.getTelefone());
				statement.execute();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});

		statement.close();
		connection.close();
		rs.close();

	}

	public void alterar(Orgao_governamental orgao) throws SQLException, ClassNotFoundException {

		// prepara conexão
		connection = ConexaoUtil.getInstance().getConnection(); 

		// ADICIONA ENDEREÇO
		sql = "update tb_endereco set cep=?, endereco=?, numero=?, complemento=?, bairro=?, uf=?, cidade=? where id_endereco=?";

		statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		statement.setString(1, orgao.getEndereco().getCep());
		statement.setString(2, orgao.getEndereco().getEndereco());
		statement.setString(3, orgao.getEndereco().getNumero());
		statement.setString(4, orgao.getEndereco().getComplemento());
		statement.setString(5, orgao.getEndereco().getBairro());
		statement.setString(6, orgao.getEndereco().getEstado());
		statement.setString(7, orgao.getEndereco().getCidade());
		statement.setInt(8, orgao.getEndereco().getId_endereco());

		statement.execute();

		// ADICIONA CLIENTE
		sql = "update tb_orgao set nome=?, sigla=?, observacao=?, cnpj=?, email=? where id_orgao=?";

		statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		statement.setString(1, orgao.getNome());
		statement.setString(2, orgao.getSigla());
		statement.setString(3, orgao.getObservacao());
		statement.setString(4, orgao.getCnpj());
		statement.setString(5, orgao.getEmail());
		statement.setInt(6, orgao.getId_orgao());
		statement.execute();
		
		connection.close();
		statement.close();

	}

	public void remover(int id) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		connection = ConexaoUtil.getInstance().getConnection();

		
		sql = "delete from tb_orgao where id_orgao=?";
		statement = connection.prepareStatement(sql);
		statement.setInt(1, id);
		statement.execute();
		sql = "delete from tb_endereco where id_endereco in(select id_endereco_fk from tb_orgao where id_orgao=?)";
		statement = connection.prepareStatement(sql);
		statement.setInt(1, id);
		statement.execute();
		sql = "delete from tb_telefone_orgao where id_orgao_fk=?";
		statement = connection.prepareStatement(sql);
		statement.setInt(1, id);
		statement.execute();
	
		connection.close();
		statement.close();
		
	

	}

	public ObservableList<Orgao_governamental> listar() throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub

		listaclientes = FXCollections.observableArrayList();

		connection = ConexaoUtil.getInstance().getConnection();

		// cliente
		sql = "SELECT * FROM tb_orgao orgao inner join tb_endereco endereco on(orgao.id_endereco_fk = endereco.id_endereco)";
		statement = connection.prepareStatement(sql);

		rs = statement.executeQuery();
		while (rs.next()) {
			ObservableList<Telefone> lista_telefones = FXCollections.observableArrayList();
			ResultSet rs2;
			sql = "select * from tb_telefone_orgao where id_orgao_fk=?";
			statement = connection.prepareStatement(sql);
			statement.setInt(1, rs.getInt("id_orgao"));
			rs2 = statement.executeQuery();
			while (rs2.next()) {
				lista_telefones.add(new Telefone(rs2.getInt("id_orgao_fk"), rs2.getString("telefone")));
			}
			listaclientes.add(
					
					new Orgao_governamental(rs.getInt("id_orgao"),
							
							new Endereco(rs.getInt("id_endereco"), rs.getString("cep"), rs.getString("endereco"),
									rs.getString("numero"), rs.getString("complemento"), rs.getString("bairro"),
									rs.getString("cidade"), rs.getString("uf")),
							
							
							rs.getString("nome"), rs.getString("sigla"), rs.getString("observacao"),
							rs.getString("email"), rs.getString("cnpj"), 
							
							lista_telefones));

			rs2.close();
		}
		connection.close();
		statement.close();
		rs.close();

		return listaclientes;
	}
	public ObservableList<Orgao_governamental> pesquisarId(int id) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub

		listaclientes = FXCollections.observableArrayList();

		connection = ConexaoUtil.getInstance().getConnection();

		// cliente
		sql = "SELECT * FROM tb_orgao orgao inner join tb_endereco endereco on(orgao.id_endereco_fk = endereco.id_endereco) where orgao.id_orgao=?";
		statement = connection.prepareStatement(sql);
		statement.setInt(1, id);

		rs = statement.executeQuery();
		while (rs.next()) {
			ObservableList<Telefone> lista_telefones = FXCollections.observableArrayList();
			ResultSet rs2;
			sql = "select * from tb_telefone_orgao where id_orgao_fk=?";
			statement = connection.prepareStatement(sql);
			statement.setInt(1, rs.getInt("id_orgao"));
			rs2 = statement.executeQuery();
			while (rs2.next()) {
				lista_telefones.add(new Telefone(rs2.getInt("id_orgao_fk"), rs2.getString("telefone")));
			}
			listaclientes.add(
					
					new Orgao_governamental(rs.getInt("id_orgao"),
							
							new Endereco(rs.getInt("id_endereco"), rs.getString("cep"), rs.getString("endereco"),
									rs.getString("numero"), rs.getString("complemento"), rs.getString("bairro"),
									rs.getString("cidade"), rs.getString("uf")),
							
							
							rs.getString("nome"), rs.getString("sigla"), rs.getString("observacao"),
							rs.getString("email"), rs.getString("cnpj"), 
							
							lista_telefones));

			rs2.close();
		}
		connection.close();
		statement.close();
		rs.close();

		return listaclientes;
	}

	public void excluirTelefone(Telefone telefone) throws SQLException, ClassNotFoundException {

		sql = "delete from tb_telefone_orgao where ? and telefone = ?";

		connection = ConexaoUtil.getInstance().getConnection();

		statement = connection.prepareStatement(sql);
		statement.setInt(1, telefone.getId_responsavel_telefone());
		statement.setString(2, telefone.getTelefone());
		statement.execute();

		connection.close();
		statement.close();

	}

	public void adicionarTelefone(Telefone telefone) throws ClassNotFoundException, SQLException {
		sql = "insert into tb_telefone_orgao(id_orgao_fk, telefone)values(?,?)";

		connection = ConexaoUtil.getInstance().getConnection();

		statement = connection.prepareStatement(sql);
		statement.setInt(1, telefone.getId_responsavel_telefone());
		statement.setString(2, telefone.getTelefone());
		statement.execute();

		connection.close();
		statement.close();

	}
}
