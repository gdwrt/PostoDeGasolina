package com.postoGasolina.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.postoGasolina.model.Cargo;
import com.postoGasolina.model.ConverterDate;
import com.postoGasolina.model.Endereco;
import com.postoGasolina.model.Funcionario;
import com.postoGasolina.model.Pessoa;
import com.postoGasolina.model.Telefone;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class FuncionarioDao implements InterfaceDao<Funcionario> {
	private Connection connection;
	private String sql;
	private PreparedStatement statement;
	private ResultSet rs;
	private ObservableList<Funcionario> listaFuncionario;
	private int idPessoa = 0;
	private int idEndereco = 0;
	private int id_funcionario = 0;

	@Override
	public void cadastrar(Funcionario funcionario) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub


			// prepara conexão
			connection = ConexaoUtil.getInstance().getConnection();

			// ADICIONA PESSOA
			sql = "insert into tb_pessoa(nome, data_nascimento, sexo, estado_civil, rg, cpf)values(?,?,?,?,?,?)";

			// chama a conexão e retorna id
			statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, funcionario.getPessoa().getNome());
			statement.setDate(2, Date.valueOf(funcionario.getPessoa().getData_nascimento()));
			statement.setString(3, String.valueOf(funcionario.getPessoa().getSexo()));
			statement.setString(4, funcionario.getPessoa().getEstado_civil());
			statement.setString(5, funcionario.getPessoa().getRg());
			statement.setString(6, funcionario.getPessoa().getCpf());
			statement.execute();

			// pegar o id da pessoa inserida
			rs = statement.getGeneratedKeys();
			if (rs.next()) {
				idPessoa = rs.getInt(1);
			}

			// ADICIONA ENDEREÇO
			sql = "insert into tb_endereco(cep, endereco, numero, complemento, bairro, uf, cidade)values(?,?,?,?,?,?,?)";

			statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, funcionario.getEndereco().getCep());
			statement.setString(2, funcionario.getEndereco().getEndereco());
			statement.setString(3, funcionario.getEndereco().getNumero());
			statement.setString(4, funcionario.getEndereco().getComplemento());
			statement.setString(5, funcionario.getEndereco().getBairro());
			statement.setString(6, funcionario.getEndereco().getEstado());
			statement.setString(7, funcionario.getEndereco().getCidade());

			statement.execute();

			rs = statement.getGeneratedKeys();
			if (rs.next()) {
				idEndereco = rs.getInt(1);
			}

			// ADICIONA FUNCIONÁRIO
			sql = "insert into tb_funcionario(id_pessoa_fk, id_endereco_fk, id_cargo_fk, status, email, data_admissao, data_demissao, observacao)values(?,?,?,?,?,?,?,?)";

			statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			statement.setInt(1, idPessoa);
			statement.setInt(2, idEndereco);
			statement.setInt(3, funcionario.getCargo().getId_cargo());
			statement.setString(4, funcionario.getStatus());
			statement.setString(5, funcionario.getEmail());
			statement.setDate(6, Date.valueOf(funcionario.getData_admissao()));
			statement.setDate(7, funcionario.getData_demissao() != null ? Date.valueOf(funcionario.getData_demissao()) : null);
			statement.setString(8, funcionario.getObservacao());

			statement.execute();

			rs = statement.getGeneratedKeys();
			if (rs.next()) {
				id_funcionario = rs.getInt(1);
			}

			funcionario.getLista_telefones().forEach(telefone -> {

				try {
					sql = "insert into tb_telefone_funcionario(id_funcionario_fk,telefone)values(?,?)";
					statement = connection.prepareStatement(sql);
					statement.setInt(1, id_funcionario);
					statement.setString(2, telefone.getTelefone());
					statement.execute();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});
			;

			statement.close();
			connection.close();
			rs.close();
	}

	@Override
	public void alterar(Funcionario funcionario) throws SQLException, ClassNotFoundException {

			connection = ConexaoUtil.getInstance().getConnection();

			// Pessoa
			sql = "update tb_pessoa set nome=?, data_nascimento=?, sexo=?, estado_civil=?, rg=?, cpf=? where id_pessoa=?";

			statement = connection.prepareStatement(sql);
			statement.setString(1, funcionario.getPessoa().getNome());
			statement.setDate(2, Date.valueOf(funcionario.getPessoa().getData_nascimento()));
			statement.setString(3, String.valueOf(funcionario.getPessoa().getSexo()));
			statement.setString(4, funcionario.getPessoa().getEstado_civil());
			statement.setString(5, funcionario.getPessoa().getRg());
			statement.setString(6, funcionario.getPessoa().getCpf());
			statement.setInt(7, funcionario.getPessoa().getId_pessoa());

			statement.execute();

			// Endereco
			sql = "update tb_endereco set cep=?, endereco=?, numero=?, complemento=?, bairro=?, uf=?, cidade=? where id_endereco=?";

			statement = connection.prepareStatement(sql);
			statement.setString(1, funcionario.getEndereco().getCep());
			statement.setString(2, funcionario.getEndereco().getEndereco());
			statement.setString(3, funcionario.getEndereco().getNumero());
			statement.setString(4, funcionario.getEndereco().getComplemento());
			statement.setString(5, funcionario.getEndereco().getBairro());
			statement.setString(6, funcionario.getEndereco().getEstado());
			statement.setString(7, funcionario.getEndereco().getCidade());
			statement.setInt(8, funcionario.getEndereco().getId_endereco());

			statement.execute();

			// ADICIONA FUNCIONÁRIO
			sql = "update tb_funcionario set id_cargo_fk=?, status=?, email=?, data_admissao=?, data_demissao=?, observacao=? where id_funcionario=?";

			statement = connection.prepareStatement(sql);
			statement.setInt(1, funcionario.getCargo().getId_cargo());
			statement.setString(2, funcionario.getStatus());
			statement.setString(3, funcionario.getEmail());
			statement.setDate(4, Date.valueOf(funcionario.getData_admissao()));
			statement.setDate(5, funcionario.getData_demissao() != null ? Date.valueOf(funcionario.getData_demissao()) : null);
			statement.setString(6, funcionario.getObservacao());
			statement.setInt(7, funcionario.getId_funcionario());
			

			statement.execute();

			statement.close();
			connection.close();
			// rs.close();

	}

	@Override
	public void remover(int id) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		connection = ConexaoUtil.getInstance().getConnection();
		
		sql = "delete from tb_funcionario where id_funcionario=?";
		statement = connection.prepareStatement(sql);
		statement.setInt(1, id);
		statement.execute();
		sql = "delete from tb_pessoa where id_pessoa in(select id_pessoa_fk from tb_funcionario where id_funcionario=?)";
		statement = connection.prepareStatement(sql);
		statement.setInt(1, id);
		statement.execute();
		sql = "delete from tb_endereco where id_endereco in(select id_endereco_fk from tb_funcionario where id_funcionario=?)";
		statement = connection.prepareStatement(sql);
		statement.setInt(1, id);
		statement.execute();
		sql = "delete from tb_telefone_funcionario where id_funcionario_fk=?";
		statement = connection.prepareStatement(sql);
		statement.setInt(1, id);
		statement.execute();
		

		connection.close();
		statement.close();

	}

	void removerTelefone() {

	}

	@Override
	public ObservableList<Funcionario> listar() throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub

		listaFuncionario = FXCollections.observableArrayList();

		connection = ConexaoUtil.getInstance().getConnection();

		// FUNCIONARIO
		sql = "SELECT * FROM tb_funcionario funcionario inner join tb_pessoa pessoa on(funcionario.id_pessoa_fk = pessoa.id_pessoa) inner join tb_endereco endereco on(funcionario.id_endereco_fk = endereco.id_endereco) inner join tb_cargo cargo on (funcionario.id_cargo_fk = cargo.id_cargo)";
		statement = connection.prepareStatement(sql);

		rs = statement.executeQuery();
		while (rs.next()) {
			ObservableList<Telefone> lista_telefones = FXCollections.observableArrayList();
			ResultSet rs2;
			sql = "select * from tb_telefone_funcionario where id_funcionario_fk=?";
			statement = connection.prepareStatement(sql);
			statement.setInt(1, rs.getInt("id_funcionario"));
			rs2 = statement.executeQuery();
			while (rs2.next()) {
				lista_telefones.add(new Telefone(rs2.getInt("id_funcionario_fk"), rs2.getString("telefone")));
			}
			rs2.close();
			sql = "select * from tb_cargo where id_cargo=?";
			statement = connection.prepareStatement(sql);
			statement.setInt(1, rs.getInt("id_cargo_fk"));
			rs2 = statement.executeQuery();
			rs2.next();
			listaFuncionario.add(
					// é um Funcionário
					new Funcionario(rs.getInt("id_funcionario"),

							// é uma pessoa
							new Pessoa(rs.getInt("id_pessoa"), rs.getString("nome"),
									ConverterDate.toLocalDate(rs.getDate("data_nascimento")),
									Character.valueOf(rs.getString("sexo").charAt(0)), rs.getString("estado_civil"),
									rs.getString("rg"), rs.getString("cpf")),
							//tem cargo
							
							new Cargo(rs2.getInt("id_cargo"), rs2.getString("nome")),
							// tem um endereco
							new Endereco(rs.getInt("id_endereco"), rs.getString("cep"), rs.getString("endereco"),
									rs.getString("numero"), rs.getString("complemento"), rs.getString("bairro"),
									rs.getString("cidade"), rs.getString("uf")),
							
							
							rs.getString("status"), 
							rs.getString("email"), 
							ConverterDate.toLocalDate(rs.getDate("data_admissao")), 
							rs.getDate("data_demissao") != null ? ConverterDate.toLocalDate(rs.getDate("data_demissao")) : null,
							rs.getString("observacao"),
							// adicionar telefones
							lista_telefones));
			
			rs2.close();
		}
		connection.close();
		statement.close();
		rs.close();
		return listaFuncionario;
	}

	public void excluirTelefone(Telefone telefone) throws SQLException, ClassNotFoundException {

		sql = "delete from tb_telefone_funcionario where ? and telefone = ?";

		connection = ConexaoUtil.getInstance().getConnection();

		statement = connection.prepareStatement(sql);
		statement.setInt(1, telefone.getId_responsavel_telefone());
		statement.setString(2, telefone.getTelefone());
		statement.execute();

		connection.close();
		statement.close();

	}

	public void adicionarTelefone(Telefone telefone) throws ClassNotFoundException, SQLException {
		sql = "insert into tb_telefone_funcionario(id_funcionario_fk, telefone)values(?,?)";

		connection = ConexaoUtil.getInstance().getConnection();

		statement = connection.prepareStatement(sql);
		statement.setInt(1, telefone.getId_responsavel_telefone());
		statement.setString(2, telefone.getTelefone());
		statement.execute();

		connection.close();
		statement.close();

	}
	public ObservableList<Funcionario> pesquisarId(int id) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub

		listaFuncionario = FXCollections.observableArrayList();

		connection = ConexaoUtil.getInstance().getConnection();

		// FUNCIONARIO
		sql = "SELECT * FROM tb_funcionario funcionario inner join tb_pessoa pessoa on(funcionario.id_pessoa_fk = pessoa.id_pessoa) inner join tb_endereco endereco on(funcionario.id_endereco_fk = endereco.id_endereco) inner join tb_cargo cargo on (funcionario.id_cargo_fk = cargo.id_cargo) where id_funcionario=?";
		statement = connection.prepareStatement(sql);
		statement.setInt(1, id); 

		rs = statement.executeQuery();
		while (rs.next()) {
			ObservableList<Telefone> lista_telefones = FXCollections.observableArrayList();
			ResultSet rs2;
			sql = "select * from tb_telefone_funcionario where id_funcionario_fk=?";
			statement = connection.prepareStatement(sql);
			statement.setInt(1, rs.getInt("id_funcionario"));
			rs2 = statement.executeQuery();
			while (rs2.next()) {
				lista_telefones.add(new Telefone(rs2.getInt("id_funcionario_fk"), rs2.getString("telefone")));
			}
			rs2.close();
			sql = "select * from tb_cargo where id_cargo=?";
			statement = connection.prepareStatement(sql);
			statement.setInt(1, rs.getInt("id_cargo_fk"));
			rs2 = statement.executeQuery();
			rs2.next();
			listaFuncionario.add(
					// é um Funcionário
					new Funcionario(rs.getInt("id_funcionario"),

							// é uma pessoa
							new Pessoa(rs.getInt("id_pessoa"), rs.getString("nome"),
									ConverterDate.toLocalDate(rs.getDate("data_nascimento")),
									Character.valueOf(rs.getString("sexo").charAt(0)), rs.getString("estado_civil"),
									rs.getString("rg"), rs.getString("cpf")),
							//tem cargo
							
							new Cargo(rs2.getInt("id_cargo"), rs2.getString("nome")),
							// tem um endereco
							new Endereco(rs.getInt("id_endereco"), rs.getString("cep"), rs.getString("endereco"),
									rs.getString("numero"), rs.getString("complemento"), rs.getString("bairro"),
									rs.getString("cidade"), rs.getString("uf")),
							
							
							rs.getString("status"), 
							rs.getString("email"), 
							ConverterDate.toLocalDate(rs.getDate("data_admissao")), 
							rs.getDate("data_demissao") != null ? ConverterDate.toLocalDate(rs.getDate("data_demissao")) : null,
							rs.getString("observacao"),
							// adicionar telefones
							lista_telefones));
			
			rs2.close();
		}
		connection.close();
		statement.close();
		rs.close();
		return listaFuncionario;
	}

}
