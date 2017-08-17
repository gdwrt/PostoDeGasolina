package com.postoGasolina.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.postoGasolina.model.Cliente;
import com.postoGasolina.model.Fluxo_caixa;
import com.postoGasolina.model.Item_pedido;
import com.postoGasolina.model.Pedido_compra;
import com.postoGasolina.model.Pedido_venda;
import com.postoGasolina.model.Produto_loja;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CompraDao {

	private Connection connetion;
	private PreparedStatement statement;
	private String sql;
	private ResultSet rs;
	private int idPedido;

	public void finalizar(Pedido_compra pedido) throws ClassNotFoundException, SQLException {
		connetion = ConexaoUtil.getInstance().getConnection();

		sql = "insert into tb_pedido_compra(id_fornecedor_fk,id_fluxo_caixa_fk, nome_responsavel, total_pagar, desconto) values(?,?,?,?,?)";

		statement = connetion.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

		if (pedido.getFornecedor() == null) {
			statement.setNull(1, java.sql.Types.INTEGER);
		} else {
			statement.setInt(1, pedido.getFornecedor().getId_fornecedor());
		}
		statement.setInt(2,  Fluxo_caixa.getId_fluxo_caixa());
		statement.setString(3, pedido.getNome_responsavel());
		statement.setBigDecimal(4, pedido.getTotal_pagar());
		statement.setBigDecimal(5, pedido.getDesconto());

		statement.execute();

		rs = statement.getGeneratedKeys();
		if (rs.next()) {
			idPedido = rs.getInt(1);
		}

		pedido.getItens_pedido().forEach(itemPedido -> {

			try {
				sql = "insert into tb_item_pedido_compra(id_pedido_compra_fk, id_produto_fk,id_combustivel_fk, tipo_produto, preco_unitario, quantidade, total) values(?,?,?,?,?,?,?)";
				statement = connetion.prepareStatement(sql);
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

		connetion.close();
		statement.close();
		rs.close();

	}

	public void atualizarEstoque(ObservableList<Item_pedido> itens_pedido) throws ClassNotFoundException, SQLException {
		connetion = ConexaoUtil.getInstance().getConnection();

		itens_pedido.forEach(item -> {
			try {
				if (item.getProduto_loja().getTipo_produto().equals("combustivel")) {

					sql = "SELECT quantidade, (quantidade+?) as quantidadeAtualizado FROM tb_tipo_combustivel where id_tipo_combustivel =?";
					statement = connetion.prepareStatement(sql);
					statement.setBigDecimal(1, item.getQuantidade());
					statement.setInt(2,
							item.getProduto_loja().getCombustivel().getTipoCombustivel().getId_tipo_combustivel());
					rs = statement.executeQuery();

					if (rs.next()) {
						sql = "update tb_tipo_combustivel set quantidade=? where id_tipo_combustivel = ?";
						statement = connetion.prepareStatement(sql);

						statement.setBigDecimal(1, rs.getBigDecimal("quantidadeAtualizado"));
						statement.setInt(2,
								item.getProduto_loja().getCombustivel().getTipoCombustivel().getId_tipo_combustivel());
						statement.execute();
					}

					statement.close();
					rs.close();
				} else if (item.getTipo_produto().equals("produto")) {
					if (!item.getProduto_loja().getProduto().isNao_controlar_estoque()) {
						sql = "select estoque_disponivel,(estoque_disponivel+?) as estoqueAtualizado from tb_produto  where id_produto =?;";
						statement = connetion.prepareStatement(sql);
						statement.setBigDecimal(1, item.getQuantidade());
						statement.setInt(2, item.getProduto_loja().getProduto().getId_produto());
						rs = statement.executeQuery();
						if (rs.next()) {
							sql = "update tb_produto set estoque_disponivel=? where id_produto=?";
							statement = connetion.prepareStatement(sql);
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

		connetion.close();

	}
	public ObservableList<Pedido_compra> pesquisar(int idCaixa) throws ClassNotFoundException, SQLException{
		ObservableList<Pedido_compra> lista_pedidos = FXCollections.observableArrayList();
		connetion = ConexaoUtil.getInstance().getConnection(); 
		
		sql="SELECT * FROM db_posto_gasolina.tb_pedido_compra where"
				+ " db_posto_gasolina.tb_pedido_compra.id_fluxo_caixa_fk=?";
		
		statement = connetion.prepareStatement(sql);
		statement.setInt(1, idCaixa);
		
		rs = statement.executeQuery();
		 
		while (rs.next()) {
			ObservableList<Item_pedido> itens_pedido = FXCollections.observableArrayList(); 
			ResultSet rs2;
			sql = "select * from tb_item_pedido_compra where id_pedido_compra_fk=?";
			statement = connetion.prepareStatement(sql);
			statement.setInt(1, rs.getInt("id_pedido_compra"));
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
								rs2.getInt("id_pedido_compra_fk"), 
								rs2.getBigDecimal("preco_unitario"), rs2.getBigDecimal("quantidade"),
								rs2.getString("tipo_produto"), rs2.getBigDecimal("total"), 0));
			}
				
			lista_pedidos.add(new Pedido_compra(rs.getInt("id_pedido_compra"), 
					new FornecedoresDao().pesquisar(rs.getInt("id_fornecedor_fk")).get(0),
					new CaixaDao().pesquisar(rs.getInt("id_fluxo_caixa_fk")).get(0),
					rs.getString("nome_responsavel"),
					rs.getBigDecimal("total_pagar"), rs.getBigDecimal("desconto"),
					
					itens_pedido));
			
			
			rs2.close();

		}
		
		statement.close();
		rs.close();
		connetion.close();
		
		
		return lista_pedidos; 
		
	}
}
