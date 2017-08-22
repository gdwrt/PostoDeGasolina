package com.postoGasolina.dao;

import java.io.File;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

import com.postoGasolina.model.Cargo;
import com.postoGasolina.model.ConverterDate;
import com.postoGasolina.model.Endereco;
import com.postoGasolina.model.Licença_autorizacao;
import com.postoGasolina.model.Licença_autorizacao;
import com.postoGasolina.model.Pessoa;
import com.postoGasolina.model.Telefone;
import com.postoGasolina.util.Data;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class LicencaAutorizacaoDao implements InterfaceDao<Licença_autorizacao>{
	private Connection connection;
	private String sql;
	private PreparedStatement statement;
	private ResultSet rs;
	private ObservableList<Licença_autorizacao> listaLA;
	private int idLa = 0;

	public void cadastrar(Licença_autorizacao lA) throws ClassNotFoundException, SQLException {

		// prepara conexão
		connection = ConexaoUtil.getInstance().getConnection();

		sql = "insert into tb_licenca_autorizacao(id_orgao_fk, id_funcionario,descricao,"
				+ " data_inicio, data_expiracao, status, descricao_responsavel_renovacao,"
				+ " informacao_adicional, arquivo_pdf)values(?,?,?,?,?,?,?,?,?)";

		statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		statement.setInt(1, lA.getOrgao().getId_orgao());
		statement.setInt(2, lA.getFuncionario().getId_funcionario());
		statement.setString(3, lA.getDescricao());
		statement.setDate(4, Date.valueOf(lA.getData_inicio()));
		statement.setDate(5, Date.valueOf(lA.getData_expiracao()));
		statement.setString(6, lA.getStatus());
		statement.setString(7, lA.getDescricao_responsavel_renovacao());
		statement.setString(8, lA.getInformacao_adicional());
		statement.setString(9, lA.getArquivo_pdf() != null ? lA.getArquivo_pdf().getPath() : "" );

		statement.execute();

		statement.close();
		connection.close();
	}

	public void alterar(Licença_autorizacao lA) throws SQLException, ClassNotFoundException {

		// prepara conexão
		connection = ConexaoUtil.getInstance().getConnection();

		sql = "update tb_licenca_autorizacao set id_orgao_fk=?, id_funcionario=?,descricao=?,"
				+ " data_inicio=?, data_expiracao=?, status=?, descricao_responsavel_renovacao=?,"
				+ " informacao_adicional=?, arquivo_pdf=? where id_licenca_autorizacao=?";

		statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		statement.setInt(1, lA.getOrgao().getId_orgao());
		statement.setInt(2, lA.getFuncionario().getId_funcionario());
		statement.setString(3, lA.getDescricao());
		statement.setDate(4, Date.valueOf(lA.getData_inicio()));
		statement.setDate(5, Date.valueOf(lA.getData_expiracao()));
		statement.setString(6, lA.getStatus());
		statement.setString(7, lA.getDescricao_responsavel_renovacao());
		statement.setString(8, lA.getInformacao_adicional());
		statement.setString(9, lA.getArquivo_pdf() != null ? lA.getArquivo_pdf().getPath() : "");
		statement.setInt(10, lA.getId_licenca_autorizacao());

		statement.execute();

		statement.close();
		connection.close();

	}

	public void remover(int id) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		connection = ConexaoUtil.getInstance().getConnection();

		sql = "delete from tb_licenca_autorizacao where id_licenca_autorizacao=?";
		statement = connection.prepareStatement(sql);
		statement.setInt(1, id);
		statement.execute();

		connection.close();
		statement.close();

	}

	public ObservableList<Licença_autorizacao> listar() throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub

		listaLA = FXCollections.observableArrayList();

		connection = ConexaoUtil.getInstance().getConnection();

		sql = "select * from tb_licenca_autorizacao";
		statement = connection.prepareStatement(sql);

		rs = statement.executeQuery();

		while (rs.next()) {
			listaLA.add(new Licença_autorizacao(rs.getInt("id_licenca_autorizacao"),
					new OrgaoGovernamentalDao().pesquisarId(rs.getInt("id_orgao_fk")).get(0),
					!new FuncionarioDao().pesquisarId(rs.getInt("id_funcionario")).isEmpty() 
					?new FuncionarioDao().pesquisarId(rs.getInt("id_funcionario")).get(0) : null
					
					, rs.getString("descricao"),
					ConverterDate.toLocalDate(rs.getDate("data_inicio")),
					ConverterDate.toLocalDate(rs.getDate("data_expiracao")), rs.getString("status"),
					rs.getString("descricao_responsavel_renovacao"), rs.getString("informacao_adicional"),
					new File(rs.getString("arquivo_pdf"))));
		}

		connection.close();
		statement.close();
		rs.close();
		return listaLA;
	}

	public void atualizaVStatus(LocalDate data) throws SQLException, ClassNotFoundException {

		connection = ConexaoUtil.getInstance().getConnection();

		sql = "update tb_licenca_autorizacao set status=? where data_expiracao=?";

		statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		statement.setString(1, "Vencido");
		statement.setDate(2, Date.valueOf(data));
		statement.execute();

		connection.close();
		statement.close();
	}
	public void atualizaAStatus(LocalDate data) throws SQLException, ClassNotFoundException {

		connection = ConexaoUtil.getInstance().getConnection();

		sql = "update tb_licenca_autorizacao set status=? where data_expiracao=?";

		statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		statement.setString(1, "Ativo");
		statement.setDate(2, Date.valueOf(data));
		statement.execute();

		connection.close();
		statement.close();
	}
}
