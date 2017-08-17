package com.postoGasolina.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.postoGasolina.model.ConverterDate;
import com.postoGasolina.model.Endereco;
import com.postoGasolina.model.Login;
import com.postoGasolina.model.Login;
import com.postoGasolina.model.Telefone;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class PermissoesDao implements InterfaceDao<Login>{

	private Connection connection;
	private String sql;
	private PreparedStatement statement;
	private ResultSet rs;
	private ObservableList<Login> listaLogins;

	public void cadastrar(Login login) throws ClassNotFoundException, SQLException {
		// prepara conexão
		connection = ConexaoUtil.getInstance().getConnection();

		// ADICIONA ENDEREÇO
		sql = "insert into tb_login(id_funcionario_fk, email, senha, g_autorizacao_licenca, "
				+ "g_orgao, g_fornecedores, g_clientes, g_funcionarios, g_fidelizacao, "
				+ "g_compra_produtos, g_venda_produtos, g_combustivel, g_produtos, g_caixa, "
				+ "g_permissoes, informacao_adicional, nivel_acesso)values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

		statement = connection.prepareStatement(sql);
		statement.setInt(1, login.getFuncionario().getId_funcionario());
		statement.setString(2, login.getEmail());
		statement.setString(3, login.getSenha());
		statement.setBoolean(4, login.isG_autorizacao_licenca());
		statement.setBoolean(5, login.isG_orgao());
		statement.setBoolean(6, login.isG_fornecedores());
		statement.setBoolean(7, login.isG_clientes());
		statement.setBoolean(8, login.isG_funcionario());
		statement.setBoolean(9, login.isG_fidelizacao());
		statement.setBoolean(10, login.isG_compra_produtos());
		statement.setBoolean(11, login.isG_venda_produtos());
		statement.setBoolean(12, login.isG_combustivel());
		statement.setBoolean(13, login.isG_produtos());
		statement.setBoolean(14, login.isG_caixa());
		statement.setBoolean(15, login.isG_permissoes());
		statement.setString(16, login.getInformacao_adicional());
		statement.setString(17, login.getNivel_acesso());
		statement.execute();

		statement.close();
		connection.close();

	}

	public void alterar(Login login) throws SQLException, ClassNotFoundException {
		// prepara conexão
		connection = ConexaoUtil.getInstance().getConnection();

		// ADICIONA ENDEREÇO
		sql = "update tb_login set id_funcionario_fk=?, email=?, senha=?, g_autorizacao_licenca=?, "
				+ "g_orgao=?, g_fornecedores=?, g_clientes=?, g_funcionarios=?, g_fidelizacao=?, "
				+ "g_compra_produtos=?, g_venda_produtos=?, g_combustivel=?, g_produtos=?, g_caixa=?, "
				+ "g_permissoes=?, informacao_adicional=?, nivel_acesso=? where id_login =?";

		statement = connection.prepareStatement(sql);
		statement.setInt(1, login.getFuncionario().getId_funcionario());
		statement.setString(2, login.getEmail());
		statement.setString(3, login.getSenha());
		statement.setBoolean(4, login.isG_autorizacao_licenca());
		statement.setBoolean(5, login.isG_orgao());
		statement.setBoolean(6, login.isG_fornecedores());
		statement.setBoolean(7, login.isG_clientes());
		statement.setBoolean(8, login.isG_funcionario());
		statement.setBoolean(9, login.isG_fidelizacao());
		statement.setBoolean(10, login.isG_compra_produtos());
		statement.setBoolean(11, login.isG_venda_produtos());
		statement.setBoolean(12, login.isG_combustivel());
		statement.setBoolean(13, login.isG_produtos());
		statement.setBoolean(14, login.isG_caixa());
		statement.setBoolean(15, login.isG_permissoes());
		statement.setString(16, login.getInformacao_adicional());
		statement.setString(17, login.getNivel_acesso());
		statement.setInt(18, login.getId_login());
		statement.execute();

		statement.close();
		connection.close();

	}

	public void remover(int id) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		connection = ConexaoUtil.getInstance().getConnection();

		sql = "delete from tb_login where id_login=?";
		statement = connection.prepareStatement(sql);
		statement.setInt(1, id);
		statement.execute();

		connection.close();
		statement.close();

	}

	public ObservableList<Login> listar() throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub

		listaLogins = FXCollections.observableArrayList();

		connection = ConexaoUtil.getInstance().getConnection();

		sql = "select * from tb_login";
		statement = connection.prepareStatement(sql);

		rs = statement.executeQuery();
		while (rs.next()) {

			listaLogins.add(new Login(rs.getInt("id_login"),
					!new FuncionarioDao().pesquisarId(rs.getInt("id_funcionario_fk")).isEmpty()
							? new FuncionarioDao().pesquisarId(rs.getInt("id_funcionario_fk")).get(0) : null,
					rs.getString("email"), rs.getString("senha"), rs.getBoolean("g_autorizacao_licenca"),
					rs.getBoolean("g_orgao"), rs.getBoolean("g_fornecedores"), rs.getBoolean("g_clientes"),
					rs.getBoolean("g_funcionarios"), rs.getBoolean("g_fidelizacao"), rs.getBoolean("g_compra_produtos"),
					rs.getBoolean("g_venda_produtos"), rs.getBoolean("g_combustivel"), rs.getBoolean("g_produtos"),
					rs.getBoolean("g_caixa"), rs.getBoolean("g_permissoes"), rs.getString("informacao_adicional"),
					rs.getString("nivel_acesso")));

		}
		connection.close();
		statement.close();
		rs.close();

		return listaLogins;
	}

	public boolean validarLogin(String email, String senha) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		connection = ConexaoUtil.getInstance().getConnection();

		sql = "select * from tb_login where email=? and senha=?";
		statement = connection.prepareStatement(sql);
		statement.setString(1, email);
		statement.setString(2, senha);
		rs = statement.executeQuery();
		if(rs.next()){
			return true;
		}

		connection.close();
		statement.close();
		return false;
	}
	public ObservableList<Login> pesquisar(String email, String senha) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub

		listaLogins = FXCollections.observableArrayList();

		connection = ConexaoUtil.getInstance().getConnection();

		sql = "select * from tb_login where email=? and senha=?";
		statement = connection.prepareStatement(sql);
		statement.setString(1, email);
		statement.setString(2, senha);

		rs = statement.executeQuery();
		while (rs.next()) {

			listaLogins.add(new Login(rs.getInt("id_login"),
					!new FuncionarioDao().pesquisarId(rs.getInt("id_funcionario_fk")).isEmpty()
							? new FuncionarioDao().pesquisarId(rs.getInt("id_funcionario_fk")).get(0) : null,
					rs.getString("email"), rs.getString("senha"), rs.getBoolean("g_autorizacao_licenca"),
					rs.getBoolean("g_orgao"), rs.getBoolean("g_fornecedores"), rs.getBoolean("g_clientes"),
					rs.getBoolean("g_funcionarios"), rs.getBoolean("g_fidelizacao"), rs.getBoolean("g_compra_produtos"),
					rs.getBoolean("g_venda_produtos"), rs.getBoolean("g_combustivel"), rs.getBoolean("g_produtos"),
					rs.getBoolean("g_caixa"), rs.getBoolean("g_permissoes"), rs.getString("informacao_adicional"),
					rs.getString("nivel_acesso")));

		}
		connection.close();
		statement.close();
		rs.close();

		return listaLogins;
	}
	public boolean validarEmail(String email) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		connection = ConexaoUtil.getInstance().getConnection();

		sql = "select * from tb_login where email=?";
		statement = connection.prepareStatement(sql);
		statement.setString(1, email);
		rs = statement.executeQuery();
		if(rs.next()){
			return true;
		}

		connection.close();
		statement.close();
		return false;
	}
	public void AtualizarSenha(String email, String novaSenha) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		connection = ConexaoUtil.getInstance().getConnection();

		sql = "update tb_login set senha=? where email=?";
		statement = connection.prepareStatement(sql);
		statement.setString(1, novaSenha);
		statement.setString(2, email);
		statement.execute();

		connection.close();
		statement.close();
	}
	public ObservableList<Login> pesquisar(String email) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub

		listaLogins = FXCollections.observableArrayList();

		connection = ConexaoUtil.getInstance().getConnection();

		sql = "select * from tb_login where email=?";
		statement = connection.prepareStatement(sql);
		statement.setString(1, email);

		rs = statement.executeQuery();
		while (rs.next()) {

			listaLogins.add(new Login(rs.getInt("id_login"),
					!new FuncionarioDao().pesquisarId(rs.getInt("id_funcionario_fk")).isEmpty()
							? new FuncionarioDao().pesquisarId(rs.getInt("id_funcionario_fk")).get(0) : null,
					rs.getString("email"), rs.getString("senha"), rs.getBoolean("g_autorizacao_licenca"),
					rs.getBoolean("g_orgao"), rs.getBoolean("g_fornecedores"), rs.getBoolean("g_clientes"),
					rs.getBoolean("g_funcionarios"), rs.getBoolean("g_fidelizacao"), rs.getBoolean("g_compra_produtos"),
					rs.getBoolean("g_venda_produtos"), rs.getBoolean("g_combustivel"), rs.getBoolean("g_produtos"),
					rs.getBoolean("g_caixa"), rs.getBoolean("g_permissoes"), rs.getString("informacao_adicional"),
					rs.getString("nivel_acesso")));

		}
		connection.close();
		statement.close();
		rs.close();

		return listaLogins;
	}
	public ObservableList<Login> pesquisar(int id) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub

		listaLogins = FXCollections.observableArrayList();

		connection = ConexaoUtil.getInstance().getConnection();

		sql = "select * from tb_login where id_login=?";
		statement = connection.prepareStatement(sql);
		statement.setInt(1, id);

		rs = statement.executeQuery();
		while (rs.next()) {

			listaLogins.add(new Login(rs.getInt("id_login"),
					!new FuncionarioDao().pesquisarId(rs.getInt("id_funcionario_fk")).isEmpty()
							? new FuncionarioDao().pesquisarId(rs.getInt("id_funcionario_fk")).get(0) : null,
					rs.getString("email"), rs.getString("senha"), rs.getBoolean("g_autorizacao_licenca"),
					rs.getBoolean("g_orgao"), rs.getBoolean("g_fornecedores"), rs.getBoolean("g_clientes"),
					rs.getBoolean("g_funcionarios"), rs.getBoolean("g_fidelizacao"), rs.getBoolean("g_compra_produtos"),
					rs.getBoolean("g_venda_produtos"), rs.getBoolean("g_combustivel"), rs.getBoolean("g_produtos"),
					rs.getBoolean("g_caixa"), rs.getBoolean("g_permissoes"), rs.getString("informacao_adicional"),
					rs.getString("nivel_acesso")));

		}
		connection.close();
		statement.close();
		rs.close();

		return listaLogins;
	}
}
