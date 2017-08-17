package com.postoGasolina.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.postoGasolina.model.Cliente;
import com.postoGasolina.model.Cliente_fisica;
import com.postoGasolina.model.Fluxo_caixa;
import com.postoGasolina.model.Fluxo_caixa2;
import com.postoGasolina.model.Item_pedido;
import com.postoGasolina.model.Pedido_venda;
import com.postoGasolina.model.Produto_loja;
import com.postoGasolina.model.Telefone;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class VendaDao{

	private Connection connection;
	private PreparedStatement statement;
	private String sql;
	private ResultSet rs;
	private int idPedido;

	public void finalizar(Pedido_venda pedido) throws ClassNotFoundException, SQLException {
		connection = ConexaoUtil.getInstance().getConnection();

		sql = "insert into tb_pedido_venda(id_cliente_fisica_fk,id_cliente_juridica_fk, id_funcionario_fk,id_fluxo_caixa_fk, total_pagar, desconto, forma_pagamento, tipo_cliente) values(?,?,?,?,?,?,?,?)";

		statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

		if (pedido.getCliente() == null) {
			statement.setNull(1, java.sql.Types.INTEGER);
			statement.setNull(2, java.sql.Types.INTEGER);
			statement.setString(7, "Anônimo");
		} else {
			if (pedido.getCliente().getTipoCliente().equals("cliente_fisica"))
				statement.setInt(1, pedido.getCliente().getCliente_fisica().getId_cliente_fisica());
			else
				statement.setNull(1, java.sql.Types.INTEGER);
			if (pedido.getCliente().getTipoCliente().equals("cliente_juridica"))
				statement.setInt(2, pedido.getCliente().getCliente_juridica().getId_cliente_juridica());
			else
				statement.setNull(2, java.sql.Types.INTEGER);

			
		}

		statement.setInt(3, pedido.getFuncionario().getId_funcionario());
		statement.setInt(4, Fluxo_caixa.getId_fluxo_caixa());
		statement.setBigDecimal(5, pedido.getTotal_pagar());
		statement.setBigDecimal(6, pedido.getDesconto());
		statement.setString(7, pedido.getForma_pagamento());
		statement.setString(8, pedido.getCliente() != null ? pedido.getCliente().getTipoCliente() : "anonimo" );

		statement.execute();

		rs = statement.getGeneratedKeys();
		if (rs.next()) {
			idPedido = rs.getInt(1);
		}

		pedido.getItens_pedido().forEach(itemPedido -> {

			try {
				sql = "insert into tb_item_pedido_venda(id_pedido_venda_fk, id_produto_fk,id_combustivel_fk, tipo_produto, preco_unitario, quantidade, total) values(?,?,?,?,?,?,?)";
				statement = connection.prepareStatement(sql);
				statement.setInt(1, idPedido);
				if (itemPedido.getProduto_loja().getProduto() != null)
					statement.setInt(2, itemPedido.getProduto_loja().getProduto().getId_produto());
				else
					statement.setNull(2, java.sql.Types.INTEGER);
				if (itemPedido.getProduto_loja().getCombustivel() != null)
					statement.setInt(3, itemPedido.getProduto_loja().getCombustivel().getId_combustivel());
				else
					statement.setNull(3, java.sql.Types.INTEGER);
				statement.setString(4, itemPedido.getTipo_produto());
				statement.setBigDecimal(5, itemPedido.getPreco_unitario());
				statement.setBigDecimal(6, itemPedido.getQuantidade());
				statement.setBigDecimal(7, itemPedido.getTotal());

				statement.execute();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		});

		// Atualiza estoque
		atualizarEstoque(pedido.getItens_pedido());

		connection.close();
		statement.close();
		rs.close();

	}

	public void atualizarEstoque(ObservableList<Item_pedido> itens_pedido) throws ClassNotFoundException, SQLException {
		connection = ConexaoUtil.getInstance().getConnection();

		itens_pedido.forEach(item -> {
			try {
				if (item.getProduto_loja().getTipo_produto().equals("combustivel")) {

					sql = "SELECT quantidade, (quantidade-?) as quantidadeAtualizado FROM tb_tipo_combustivel where id_tipo_combustivel =?";
					statement = connection.prepareStatement(sql);
					statement.setBigDecimal(1, item.getQuantidade());
					statement.setInt(2,
							item.getProduto_loja().getCombustivel().getTipoCombustivel().getId_tipo_combustivel());
					rs = statement.executeQuery();

					if (rs.next()) {
						sql = "update tb_tipo_combustivel set quantidade=? where id_tipo_combustivel = ?";
						statement = connection.prepareStatement(sql);

						statement.setBigDecimal(1, rs.getBigDecimal("quantidadeAtualizado"));
						statement.setInt(2,
								item.getProduto_loja().getCombustivel().getTipoCombustivel().getId_tipo_combustivel());
						statement.execute();
					}

					statement.close();
					rs.close();
				} else if (item.getTipo_produto().equals("produto")) {
					if (!item.getProduto_loja().getProduto().isNao_controlar_estoque()) {
						sql = "select estoque_disponivel,(estoque_disponivel-?) as estoqueAtualizado from tb_produto  where id_produto =?;";
						statement = connection.prepareStatement(sql);
						statement.setBigDecimal(1, item.getQuantidade());
						statement.setInt(2, item.getProduto_loja().getProduto().getId_produto());
						rs = statement.executeQuery();
						if (rs.next()) {
							sql = "update tb_produto set estoque_disponivel=? where id_produto=?";
							statement = connection.prepareStatement(sql);
							statement.setBigDecimal(1, rs.getBigDecimal("estoqueAtualizado"));
							statement.setInt(2, item.getProduto_loja().getProduto().getId_produto());
							statement.execute();
						}

						statement.close();
						rs.close();
					}
				}

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		});

		connection.close();

	}

	public ObservableList<Pedido_venda> pesquisar(int idCaixa) throws ClassNotFoundException, SQLException{
		ObservableList<Pedido_venda> lista_pedidos = FXCollections.observableArrayList();
		connection = ConexaoUtil.getInstance().getConnection(); 
		
		sql="SELECT * FROM db_posto_gasolina.tb_pedido_venda where"
				+ " db_posto_gasolina.tb_pedido_venda.id_fluxo_caixa_fk=?";
		
		statement = connection.prepareStatement(sql);
		statement.setInt(1, idCaixa);
		
		rs = statement.executeQuery();
		 
		while (rs.next()) {
			ObservableList<Item_pedido> itens_pedido = FXCollections.observableArrayList(); 
			ResultSet rs2;
			sql = "select * from tb_item_pedido_venda where id_pedido_venda_fk=?";
			statement = connection.prepareStatement(sql);
			statement.setInt(1, rs.getInt("id_pedido_venda"));
			rs2 = statement.executeQuery();
			
			while (rs2.next()) {
						itens_pedido.add(new Item_pedido(
								new Produto_loja( 
								new CombustiveisDao().pesquisar(rs2.getInt("id_combustivel_fk")).size() != 0 ?
								new CombustiveisDao().pesquisar(rs2.getInt("id_combustivel_fk")).get(0) :
									null,
								new ProdutosDao().pesquisar(rs2.getInt("id_produto_fk")).size() != 0 ?
								new ProdutosDao().pesquisar(rs2.getInt("id_produto_fk")).get(0) : null,
								rs2.getString("tipo_produto")),
								rs2.getInt("id_pedido_venda_fk"), 
								rs2.getBigDecimal("preco_unitario"), rs2.getBigDecimal("quantidade"),
								rs2.getString("tipo_produto"), rs2.getBigDecimal("total"), 0));
			}
			
			lista_pedidos.add(new Pedido_venda(rs.getInt("id_pedido_venda"),
					new FuncionarioDao().pesquisarId(rs.getInt("id_funcionario_fk")).get(0), 
					new Cliente(new ClienteFisicaDao().pesquisar(rs.getInt("id_cliente_fisica_fk")).size() != 0 ?
							new ClienteFisicaDao().pesquisar(rs.getInt("id_cliente_fisica_fk")).get(0) : null,
							new ClienteJuridicaDao().pesquisar(rs.getInt("id_cliente_juridica_fk")).size() != 0 ?
							new ClienteJuridicaDao().pesquisar(rs.getInt("id_cliente_juridica_fk")).get(0) : null,
							rs.getString("tipo_cliente")),
					new CaixaDao().pesquisar(rs.getInt("id_fluxo_caixa_fk")).get(0),
					rs.getBigDecimal("total_pagar"), rs.getBigDecimal("desconto"), rs.getString("forma_pagamento"),
					
					itens_pedido));
			
			rs2.close();

		}
		
		statement.close();
		rs.close();
		connection.close();
		
		
		return lista_pedidos; 
		
	}

}
