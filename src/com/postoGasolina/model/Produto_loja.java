package com.postoGasolina.model;

import java.sql.SQLException;

import com.postoGasolina.dao.CombustiveisDao;
import com.postoGasolina.dao.ProdutosDao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Produto_loja {

	private Combustivel combustivel;
	private Produto produto;
	private String tipo_produto;

	public Produto_loja() {

	}

	public Produto_loja(Combustivel combustivel, Produto produto, String tipo_produto) {
		super();
		this.combustivel = combustivel;
		this.produto = produto;
		this.tipo_produto = tipo_produto;
	}

	@Override
	public String toString() {
		if(tipo_produto.equals("combustivel")){
			return combustivel.getId_combustivel() + " - " + combustivel.getTipoCombustivel() + " - "
					+ combustivel.getBomba().getDescricao() + " - " + combustivel.getBico().getDescricao() + " - ";
		} else if(tipo_produto.equals("produto")){
			return produto.getDescricao();
		}
		return "";
	}
	
	public Combustivel getCombustivel() {
		return combustivel;
	}

	public void setCombustivel(Combustivel combustivel) {
		this.combustivel = combustivel;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public String getTipo_produto() {
		return tipo_produto;
	}

	public void setTipo_produto(String tipo_produto) {
		this.tipo_produto = tipo_produto;
	}

	public ObservableList<Produto_loja> listar() {

		ObservableList<Produto_loja> lista_produtos = FXCollections.observableArrayList();

		try {
			new CombustiveisDao().listar().forEach(combustivel -> {
				lista_produtos.add(new Produto_loja(combustivel, null, "combustivel")); 
			});
			new ProdutosDao().listar().forEach(produto ->{
				lista_produtos.add(new Produto_loja(null, produto, "produto"));
			});

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return lista_produtos;
	}
}
