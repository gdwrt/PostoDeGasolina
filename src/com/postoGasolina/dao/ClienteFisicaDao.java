package com.postoGasolina.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sound.midi.Soundbank;

import com.postoGasolina.model.Cliente_Gasto;
import com.postoGasolina.model.Cliente_fisica;
import com.postoGasolina.model.ConverterDate;
import com.postoGasolina.model.Endereco;
import com.postoGasolina.model.Pessoa;
import com.postoGasolina.model.Telefone;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ClienteFisicaDao implements InterfaceDao<Cliente_fisica> {
	private Connection connection;
	private String sql;
	private PreparedStatement statement;
	private ResultSet rs;
	private ResultSet rs2;
	private ResultSet rs3;
	private ObservableList<Cliente_fisica> listaclientes;
	private int idPessoa = 0;
	private int idEndereco = 0;
	private int id_cliente_fisica = 0;

	@Override
	public void cadastrar(Cliente_fisica clienteFisica) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		// Prepara a conexão
		// prepara conexão
		connection = ConexaoUtil.getInstance().getConnection();

		// ADICIONA PESSOA
		sql = "insert into tb_pessoa(nome, data_nascimento, sexo, estado_civil, rg, cpf)values(?,?,?,?,?,?)";

		// chama a conexão e retorna id
		statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		statement.setString(1, clienteFisica.getPessoa().getNome());
		statement.setDate(2, Date.valueOf(clienteFisica.getPessoa().getData_nascimento()));
		statement.setString(3, String.valueOf(clienteFisica.getPessoa().getSexo()));
		statement.setString(4, clienteFisica.getPessoa().getEstado_civil());
		statement.setString(5, clienteFisica.getPessoa().getRg());
		statement.setString(6, clienteFisica.getPessoa().getCpf());
		statement.execute();

		// pegar o id da pessoa inserida
		rs = statement.getGeneratedKeys();
		if (rs.next()) {
			idPessoa = rs.getInt(1);
		}

		// ADICIONA ENDEREÇO
		sql = "insert into tb_endereco(cep, endereco, numero, complemento, bairro, uf, cidade)values(?,?,?,?,?,?,?)";

		statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		statement.setString(1, clienteFisica.getEndereco().getCep());
		statement.setString(2, clienteFisica.getEndereco().getEndereco());
		statement.setString(3, clienteFisica.getEndereco().getNumero());
		statement.setString(4, clienteFisica.getEndereco().getComplemento());
		statement.setString(5, clienteFisica.getEndereco().getBairro());
		statement.setString(6, clienteFisica.getEndereco().getEstado());
		statement.setString(7, clienteFisica.getEndereco().getCidade());

		statement.execute();

		rs = statement.getGeneratedKeys();
		if (rs.next()) {
			idEndereco = rs.getInt(1);
		}

		// ADICIONA CLIENTE
		sql = "insert into tb_cliente_fisica(id_pessoa_fk, id_endereco_fk, pai, mae, email, informacao)values(?,?,?,?,?,?)";

		statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		statement.setInt(1, idPessoa);
		statement.setInt(2, idEndereco);
		statement.setString(3, clienteFisica.getPai());
		statement.setString(4, clienteFisica.getMae());
		statement.setString(5, clienteFisica.getEmail());
		statement.setString(6, clienteFisica.getInformacao());

		statement.execute();

		rs = statement.getGeneratedKeys();
		if (rs.next()) {
			id_cliente_fisica = rs.getInt(1);
		}

		clienteFisica.getListaTelefone().forEach(telefone -> {

			try {
				sql = "insert into tb_telefone_cliente_fisica(id_cliente_fisica_fk,telefone)values(?,?)";
				statement = connection.prepareStatement(sql);
				statement.setInt(1, id_cliente_fisica);
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

	public void cadastrar(String nome, String cpf) throws ClassNotFoundException, SQLException {
		// prepara conexão
		connection = ConexaoUtil.getInstance().getConnection();

		// ADICIONA PESSOA
		sql = "insert into tb_pessoa(nome,cpf)values(?,?)";

		// chama a conexão e retorna id
		statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		statement.setString(1, nome);
		statement.setString(2, cpf);
		statement.execute();

		// pegar o id da pessoa inserida
		rs = statement.getGeneratedKeys();
		if (rs.next()) {
			idPessoa = rs.getInt(1);
		}

		// ADICIONA ENDEREÇO
		sql = "insert into tb_endereco(uf)values(?)";

		statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		statement.setString(1, "PR");

		statement.execute();

		rs = statement.getGeneratedKeys();
		if (rs.next()) {
			idEndereco = rs.getInt(1);
		}

		// ADICIONA CLIENTE
		sql = "insert into tb_cliente_fisica(id_pessoa_fk, id_endereco_fk)values(?,?)";

		statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		statement.setInt(1, idPessoa);
		statement.setInt(2, idEndereco);

		statement.execute();

		rs = statement.getGeneratedKeys();
		if (rs.next()) {
			id_cliente_fisica = rs.getInt(1);
		}

		statement.close();
		connection.close();
		rs.close();
	}

	@Override
	public void alterar(Cliente_fisica clienteFisica) throws SQLException, ClassNotFoundException {

		connection = ConexaoUtil.getInstance().getConnection();

		// Pessoa
		sql = "update tb_pessoa set nome=?, data_nascimento=?, sexo=?, estado_civil=?, rg=?, cpf=? where id_pessoa=?";

		statement = connection.prepareStatement(sql);
		statement.setString(1, clienteFisica.getPessoa().getNome());
		statement.setDate(2, Date.valueOf(clienteFisica.getPessoa().getData_nascimento()));
		statement.setString(3, String.valueOf(clienteFisica.getPessoa().getSexo()));
		statement.setString(4, clienteFisica.getPessoa().getEstado_civil());
		statement.setString(5, clienteFisica.getPessoa().getRg());
		statement.setString(6, clienteFisica.getPessoa().getCpf());
		statement.setInt(7, clienteFisica.getPessoa().getId_pessoa());

		statement.execute();

		// Endereco
		sql = "update tb_endereco set cep=?, endereco=?, numero=?, complemento=?, bairro=?, uf=?, cidade=? where id_endereco=?";

		statement = connection.prepareStatement(sql);
		statement.setString(1, clienteFisica.getEndereco().getCep());
		statement.setString(2, clienteFisica.getEndereco().getEndereco());
		statement.setString(3, clienteFisica.getEndereco().getNumero());
		statement.setString(4, clienteFisica.getEndereco().getComplemento());
		statement.setString(5, clienteFisica.getEndereco().getBairro());
		statement.setString(6, clienteFisica.getEndereco().getEstado());
		statement.setString(7, clienteFisica.getEndereco().getCidade());
		statement.setInt(8, clienteFisica.getEndereco().getId_endereco());

		statement.execute();

		// Cliente
		sql = "update tb_cliente_fisica set pai=?, mae=?, email=?, informacao=? where id_cliente_fisica=?";

		statement = connection.prepareStatement(sql);
		statement.setString(1, clienteFisica.getPai());
		statement.setString(2, clienteFisica.getMae());
		statement.setString(3, clienteFisica.getEmail());
		statement.setString(4, clienteFisica.getInformacao());
		statement.setInt(5, clienteFisica.getId_cliente_fisica());

		statement.execute();

		statement.close();
		connection.close();
		// rs.close();
	}

	@Override
	public void remover(int id) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		connection = ConexaoUtil.getInstance().getConnection();

		sql = "delete from tb_telefone_cliente_fisica where id_cliente_fisica_fk=?";
		statement = connection.prepareStatement(sql);
		statement.setInt(1, id);
		statement.execute();

		sql = "delete from tb_cliente_fisica where id_cliente_fisica=?";
		statement = connection.prepareStatement(sql);
		statement.setInt(1, id);
		statement.execute();

		sql = "delete from tb_pessoa where id_pessoa in(select id_pessoa_fk from tb_cliente_fisica where id_cliente_fisica=?)";
		statement = connection.prepareStatement(sql);
		statement.setInt(1, id);
		statement.execute();
		sql = "delete from tb_endereco where id_endereco in(select id_endereco_fk from tb_cliente_fisica where id_cliente_fisica=?)";
		statement = connection.prepareStatement(sql);
		statement.setInt(1, id);
		statement.execute();

		connection.close();
		statement.close();

	}

	void removerTelefone() {

	}

	@Override
	public ObservableList<Cliente_fisica> listar() throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub

		listaclientes = FXCollections.observableArrayList();

		connection = ConexaoUtil.getInstance().getConnection();

		// cliente
		sql = "SELECT * FROM tb_cliente_fisica cliente inner join tb_pessoa pessoa on(cliente.id_pessoa_fk = pessoa.id_pessoa) inner join tb_endereco endereco on(cliente.id_endereco_fk = endereco.id_endereco)";
		statement = connection.prepareStatement(sql);

		rs = statement.executeQuery();
		while (rs.next()) {

			if (rs.getDate("data_nascimento") == null) {
				listaclientes.add(new Cliente_fisica(rs.getInt("id_cliente_fisica"),
						new Pessoa(rs.getInt("id_pessoa"), rs.getString("nome"), rs.getString("cpf")),
						new Endereco(rs.getInt("id_endereco"), rs.getString("uf"))));
			} else {
				ObservableList<Telefone> lista_telefones = FXCollections.observableArrayList();
				ResultSet rs2;
				sql = "select * from tb_telefone_cliente_fisica where id_cliente_fisica_fk=?";
				statement = connection.prepareStatement(sql);
				statement.setInt(1, rs.getInt("id_cliente_fisica"));
				rs2 = statement.executeQuery();
				while (rs2.next()) {
					lista_telefones.add(new Telefone(rs2.getInt("id_cliente_fisica_fk"), rs2.getString("telefone")));
				}
				listaclientes.add(
						// é um cliente
						new Cliente_fisica(rs.getInt("id_cliente_fisica"),

								// é uma pessoa
								new Pessoa(rs.getInt("id_pessoa"), rs.getString("nome"),
										ConverterDate.toLocalDate(rs.getDate("data_nascimento")),
										Character.valueOf(rs.getString("sexo").charAt(0)), rs.getString("estado_civil"),
										rs.getString("rg"), rs.getString("cpf")),

								// tem um endereco
								new Endereco(rs.getInt("id_endereco"), rs.getString("cep"), rs.getString("endereco"),
										rs.getString("numero"), rs.getString("complemento"), rs.getString("bairro"),
										rs.getString("cidade"), rs.getString("uf")),
								rs.getString("pai"), rs.getString("mae"), rs.getString("email"),
								rs.getString("informacao"),

								// adicionar telefones
								lista_telefones));
				rs2.close();
			}
		}
		connection.close();
		statement.close();
		rs.close();

		return listaclientes;
	}

	public ObservableList<Cliente_fisica> pesquisar(int id) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub

		listaclientes = FXCollections.observableArrayList();

		connection = ConexaoUtil.getInstance().getConnection();

		// cliente
		sql = "SELECT * FROM tb_cliente_fisica cliente inner join tb_pessoa pessoa on(cliente.id_pessoa_fk = pessoa.id_pessoa) inner join tb_endereco endereco on(cliente.id_endereco_fk = endereco.id_endereco) where cliente.id_cliente_fisica=?";
		statement = connection.prepareStatement(sql);
		statement.setInt(1, id);
		rs = statement.executeQuery();
		while (rs.next()) {
			ObservableList<Telefone> lista_telefones = FXCollections.observableArrayList();
			ResultSet rs2;
			sql = "select * from tb_telefone_cliente_fisica where id_cliente_fisica_fk=?";
			statement = connection.prepareStatement(sql);
			statement.setInt(1, rs.getInt("id_cliente_fisica"));
			rs2 = statement.executeQuery();
			while (rs2.next()) {
				lista_telefones.add(new Telefone(rs2.getInt("id_cliente_fisica_fk"), rs2.getString("telefone")));
			}
			listaclientes.add(
					// é um cliente
					new Cliente_fisica(rs.getInt("id_cliente_fisica"),

							// é uma pessoa
							new Pessoa(rs.getInt("id_pessoa"), rs.getString("nome"),
									ConverterDate.toLocalDate(rs.getDate("data_nascimento")),
									Character.valueOf(rs.getString("sexo").charAt(0)), rs.getString("estado_civil"),
									rs.getString("rg"), rs.getString("cpf")),

							// tem um endereco
							new Endereco(rs.getInt("id_endereco"), rs.getString("cep"), rs.getString("endereco"),
									rs.getString("numero"), rs.getString("complemento"), rs.getString("bairro"),
									rs.getString("cidade"), rs.getString("uf")),
							rs.getString("pai"), rs.getString("mae"), rs.getString("email"), rs.getString("informacao"),

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

		sql = "delete from tb_telefone_cliente_fisica where ? and telefone = ?";

		connection = ConexaoUtil.getInstance().getConnection();

		statement = connection.prepareStatement(sql);
		statement.setInt(1, telefone.getId_responsavel_telefone());
		statement.setString(2, telefone.getTelefone());
		statement.execute();

		connection.close();
		statement.close();

	}

	public void adicionarTelefone(Telefone telefone) throws ClassNotFoundException, SQLException {
		sql = "insert into tb_telefone_cliente_fisica(id_cliente_fisica_fk, telefone)values(?,?)";

		connection = ConexaoUtil.getInstance().getConnection();

		statement = connection.prepareStatement(sql);
		statement.setInt(1, telefone.getId_responsavel_telefone());
		statement.setString(2, telefone.getTelefone());
		statement.execute();

		connection.close();
		statement.close();

	}

	public ObservableList<Cliente_Gasto> listarGasto() throws ClassNotFoundException, SQLException {
		connection = ConexaoUtil.getInstance().getConnection();

		ObservableList<Cliente_Gasto> lista_clientes = FXCollections.observableArrayList();

		sql = "SELECT cliente.email, cliente.id_cliente_fisica, pessoa.nome, pessoa.data_nascimento, "
				+ "sum(venda.total_pagar) as gastoTotal FROM tb_pedido_venda venda "
				+ "join tb_cliente_fisica cliente on(cliente.id_cliente_fisica = venda.id_cliente_fisica_fk)"
				+ "join tb_pessoa pessoa on (pessoa.id_pessoa = cliente.id_pessoa_fk)group by pessoa.nome;";

		statement = connection.prepareStatement(sql);
		rs = statement.executeQuery();

		while (rs.next()) {
			System.out.println(rs.getInt("id_cliente_fisica"));

			sql = "SELECT sum(total_pagar) as gastoMensal FROM tb_pedido_venda venda"
					+ "inner join tb_cliente_fisica cliente on(id_cliente_fisica_fk = cliente.id_cliente_fisica)"
					+ "inner join tb_fluxo_caixa caixa on(id_fluxo_caixa_fk = caixa.id_fluxo_caixa)"
					+ "inner join tb_pessoa pessoa on (pessoa.id_pessoa = cliente.id_pessoa_fk)"
					+ "where (MONTH(now()) = MONTH(caixa.data_hora_inicial)) and (cliente.id_cliente_fisica =?)"
					+ "group by pessoa.nome;";

			statement = connection.prepareStatement(sql);
			statement.setInt(1, rs.getInt("id_cliente_fisica"));
			rs2 = statement.executeQuery();

			sql = "SELECT sum(total_pagar) as gastoAnual FROM tb_pedido_venda venda"
					+ "inner join tb_cliente_fisica cliente on(id_cliente_fisica_fk = cliente.id_cliente_fisica)"
					+ "inner join tb_fluxo_caixa caixa on(id_fluxo_caixa_fk = caixa.id_fluxo_caixa)"
					+ "inner join tb_pessoa pessoa on (pessoa.id_pessoa = cliente.id_pessoa_fk)"
					+ "where (year(now()) = year(caixa.data_hora_inicial)) and (cliente.id_cliente_fisica =?)"
					+ "group by pessoa.nome;";

			statement = connection.prepareStatement(sql);
			statement.setInt(1, rs.getInt("id_cliente_fisica"));
			rs3 = statement.executeQuery();

			if (rs2.first() && rs3.first()) {
				lista_clientes.add(new Cliente_Gasto(rs.getInt("id_cliente_fisica"), rs.getString("nome"),
						rs2.getBigDecimal("gastoMensal"), rs3.getBigDecimal("gastoAnual"),
						rs.getBigDecimal("gastoTotal"),
						rs.getDate("data_nascimento") != null ? ConverterDate.toLocalDate(rs.getDate("data_nascimento"))
								: null,
						"cliente_fisica", rs.getString("email")));
			}

		}

		connection.close();
		statement.close();
		rs.close();

		return lista_clientes;

	}

}
