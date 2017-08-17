package com.postoGasolina.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.postoGasolina.model.Fornecedor;
import com.postoGasolina.model.ConverterDate;
import com.postoGasolina.model.Endereco;
import com.postoGasolina.model.Telefone;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class FornecedoresDao implements InterfaceDao<Fornecedor>{
	private Connection connection;
	private String sql;
	private PreparedStatement statement;
	private ResultSet rs;
	private ObservableList<Fornecedor> listaclientes; 
	private int idEndereco = 0;
	private int id_fornecedor = 0; 

	public void cadastrar(Fornecedor fornecedor) throws ClassNotFoundException, SQLException {

		// prepara conexão
		connection = ConexaoUtil.getInstance().getConnection();

		// ADICIONA ENDEREÇO
		sql = "insert into tb_endereco(cep, endereco, numero, complemento, bairro, uf, cidade)values(?,?,?,?,?,?,?)";

		statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		statement.setString(1, fornecedor.getEndereco().getCep());
		statement.setString(2, fornecedor.getEndereco().getEndereco());
		statement.setString(3, fornecedor.getEndereco().getNumero());
		statement.setString(4, fornecedor.getEndereco().getComplemento());
		statement.setString(5, fornecedor.getEndereco().getBairro());
		statement.setString(6, fornecedor.getEndereco().getEstado());
		statement.setString(7, fornecedor.getEndereco().getCidade());

		statement.execute();

		rs = statement.getGeneratedKeys();
		if (rs.next()) {
			idEndereco = rs.getInt(1);
		}

		// ADICIONA Fornecedor
		sql = "insert into tb_fornecedor(id_endereco_fk, razao_social, cnpj, ie, "
				+ "email, site, status, observacoes, data_situacao)values(?,?,?,?,?,?,?,?,?)";

		statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		statement.setInt(1, idEndereco);
		statement.setString(2, fornecedor.getRazao_social());
		statement.setString(3, fornecedor.getCnpj());
		statement.setString(4, fornecedor.getIe());
		statement.setString(5, fornecedor.getEmail());
		statement.setString(6, fornecedor.getSite());
		statement.setString(7, fornecedor.getStatus());
		statement.setString(8, fornecedor.getObservacoes());
		statement.setDate(9, Date.valueOf(fornecedor.getData_situacao()));

		statement.execute();

		rs = statement.getGeneratedKeys();
		if (rs.next()) {
			id_fornecedor = rs.getInt(1);
		}

		fornecedor.getListaTelefone().forEach(telefone -> {

			try {
				sql = "insert into tb_telefone_fornecedor(id_fornecedor_fk,telefone)values(?,?)";
				statement = connection.prepareStatement(sql);
				statement.setInt(1, id_fornecedor);
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

	public void alterar(Fornecedor fornecedor) throws SQLException, ClassNotFoundException {

		// prepara conexão
		connection = ConexaoUtil.getInstance().getConnection();

		// ADICIONA ENDEREÇO
		sql = "update tb_endereco set cep=?, endereco=?, numero=?, complemento=?, bairro=?,"
				+ " uf=?, cidade=? where id_endereco=?";

		statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		statement.setString(1, fornecedor.getEndereco().getCep());
		statement.setString(2, fornecedor.getEndereco().getEndereco());
		statement.setString(3, fornecedor.getEndereco().getNumero());
		statement.setString(4, fornecedor.getEndereco().getComplemento());
		statement.setString(5, fornecedor.getEndereco().getBairro());
		statement.setString(6, fornecedor.getEndereco().getEstado());
		statement.setString(7, fornecedor.getEndereco().getCidade());
		statement.setInt(8, fornecedor.getEndereco().getId_endereco());
		statement.execute();

		// ADICIONA CLIENTE
		sql = "update tb_fornecedor set razao_social=?, cnpj=?, ie=?, "
				+ "email=?, site=?, status=?, observacoes=?, data_situacao=? where id_fornecedor=?";

		statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		statement.setString(1, fornecedor.getRazao_social());
		statement.setString(2, fornecedor.getCnpj());
		statement.setString(3, fornecedor.getIe());
		statement.setString(4, fornecedor.getEmail());
		statement.setString(5, fornecedor.getSite());
		statement.setString(6, fornecedor.getStatus());
		statement.setString(7, fornecedor.getObservacoes());
		statement.setDate(8, Date.valueOf(fornecedor.getData_situacao()));
		statement.setInt(9, fornecedor.getId_fornecedor());
		statement.execute();

		statement.close();
		connection.close();

	}

	public void remover(int id) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		connection = ConexaoUtil.getInstance().getConnection();
		
		sql = "delete from tb_telefone_fornecedor where id_fornecedor_fk=?";
		statement = connection.prepareStatement(sql);
		statement.setInt(1, id);
		statement.execute();
		sql = "delete from tb_fornecedor where id_fornecedor=?";
		statement = connection.prepareStatement(sql);
		statement.setInt(1, id);
		statement.execute();
		sql = "delete from tb_endereco where id_endereco in(select id_endereco_fk from tb_cliente_juridica where id_cliente_juridica=?)";
		statement = connection.prepareStatement(sql);
		statement.setInt(1, id);
		statement.execute();
		
		

		connection.close();
		statement.close();

	}

	public ObservableList<Fornecedor> listar() throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub

		listaclientes = FXCollections.observableArrayList();

		connection = ConexaoUtil.getInstance().getConnection();

		// cliente
		sql = "SELECT * FROM tb_fornecedor fornecedor inner join tb_endereco endereco on(fornecedor.id_endereco_fk = endereco.id_endereco)";
		statement = connection.prepareStatement(sql);

		rs = statement.executeQuery();
		while (rs.next()) {
			ObservableList<Telefone> lista_telefones = FXCollections.observableArrayList();
			ResultSet rs2;
			sql = "select * from tb_telefone_fornecedor where id_fornecedor_fk=?";
			statement = connection.prepareStatement(sql);
			statement.setInt(1, rs.getInt("id_fornecedor"));
			rs2 = statement.executeQuery();
			while (rs2.next()) {
				lista_telefones.add(new Telefone(rs2.getInt("id_fornecedor_fk"), rs2.getString("telefone")));
			}
			listaclientes.add(
					// é um cliente			
					new Fornecedor(rs.getInt("id_fornecedor"),

							// tem um endereco
							new Endereco(rs.getInt("id_endereco"), rs.getString("cep"), rs.getString("endereco"),
									rs.getString("numero"), rs.getString("complemento"), rs.getString("bairro"),
									rs.getString("cidade"), rs.getString("uf")),

							rs.getString("razao_social"), rs.getString("cnpj"), rs.getString("ie"),
							rs.getString("email"), rs.getString("site"),rs.getString("status"),
							rs.getString("observacoes"),ConverterDate.toLocalDate(rs.getDate("data_situacao")),

							// adicionar telefones
							lista_telefones));

			rs2.close();
		}
		connection.close();
		statement.close();
		rs.close();

		return listaclientes;
	}
	
	public ObservableList<Fornecedor> pesquisar(int id) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub

		listaclientes = FXCollections.observableArrayList();

		connection = ConexaoUtil.getInstance().getConnection();

		// cliente
		sql = "SELECT * FROM tb_fornecedor fornecedor inner join tb_endereco endereco on(fornecedor.id_endereco_fk = endereco.id_endereco) where fornecedor.id_fornecedor=?";
		statement = connection.prepareStatement(sql);
		statement.setInt(1, id);

		rs = statement.executeQuery();
		while (rs.next()) {
			ObservableList<Telefone> lista_telefones = FXCollections.observableArrayList();
			ResultSet rs2;
			sql = "select * from tb_telefone_fornecedor where id_fornecedor_fk=?";
			statement = connection.prepareStatement(sql);
			statement.setInt(1, rs.getInt("id_fornecedor"));
			rs2 = statement.executeQuery();
			while (rs2.next()) {
				lista_telefones.add(new Telefone(rs2.getInt("id_fornecedor_fk"), rs2.getString("telefone")));
			}
			listaclientes.add(
					// é um cliente			
					new Fornecedor(rs.getInt("id_fornecedor"),

							// tem um endereco
							new Endereco(rs.getInt("id_endereco"), rs.getString("cep"), rs.getString("endereco"),
									rs.getString("numero"), rs.getString("complemento"), rs.getString("bairro"),
									rs.getString("cidade"), rs.getString("uf")),

							rs.getString("razao_social"), rs.getString("cnpj"), rs.getString("ie"),
							rs.getString("email"), rs.getString("site"),rs.getString("status"),
							rs.getString("observacoes"),ConverterDate.toLocalDate(rs.getDate("data_situacao")),

							// adicionar telefones
							lista_telefones));

			rs2.close();
		}
		connection.close();
		statement.close();
		rs.close();

		return listaclientes;
	}

	public void excluirTelefone(Telefone telefone) throws SQLException, ClassNotFoundException {

		sql = "delete from tb_telefone_fornecedor where ? and telefone = ?";

		connection = ConexaoUtil.getInstance().getConnection();

		statement = connection.prepareStatement(sql);
		statement.setInt(1, telefone.getId_responsavel_telefone());
		statement.setString(2, telefone.getTelefone());
		statement.execute();

		connection.close();
		statement.close();

	}

	public void adicionarTelefone(Telefone telefone) throws ClassNotFoundException, SQLException {
		sql = "insert into tb_telefone_fornecedor(id_fornecedor_fk, telefone)values(?,?)";

		connection = ConexaoUtil.getInstance().getConnection();

		statement = connection.prepareStatement(sql);
		statement.setInt(1, telefone.getId_responsavel_telefone());
		statement.setString(2, telefone.getTelefone());
		statement.execute();

		connection.close();
		statement.close();

	}
}
