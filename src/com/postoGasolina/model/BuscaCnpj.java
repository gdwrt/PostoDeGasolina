package com.postoGasolina.model;

import java.io.IOException;

import org.jsoup.Jsoup;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import json.classes.JSONObject;

public class BuscaCnpj {

	private JSONObject dados;

	public BuscaCnpj(String cnpj) {
		try {
			String json = Jsoup.connect("https://www.receitaws.com.br/v1/cnpj/" + cnpj.replaceAll("[./-]", ""))
					.ignoreContentType(true).execute().body();
			dados = new JSONObject(json);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getEmail() {
		return dados.getString("email") != null ? dados.getString("email") : "";
	}

	public String getTAtividadePrincipal() {
		String dado = new JSONObject(dados.getJSONArray("atividade_principal").get(0).toString()).getString("text");
		return dado != null ? dado : "";
	}

	public String getDataSituacao() {
		return dados.getString("data_situacao") != null ? dados.getString("data_situacao") : "";
	}

	public String getComplemento() {
		return dados.getString("complemento") != null ? dados.getString("complemento") : "";
	}

	public String getNome() {
		return dados.getString("nome") != null ? dados.getString("nome") : "";
	}

	public String getUf() {
		return dados.getString("uf") != null ? dados.getString("uf") : "";
	}

	public ObservableList<String> getListaTelefones() {
		ObservableList<String> lista_telefones = FXCollections.observableArrayList();
		String[] telefones = dados.getString("telefone").split("/");
		for (String telefone : telefones) {
			lista_telefones.add(telefone);
		}
		return lista_telefones;
	}

	public String getFantasia() {
		return dados.getString("fantasia") != null ? dados.getString("fantasia") : "";
	}

	public ObservableList<String> getListaSociosAdministradores() {
		ObservableList<String> lista_socios = FXCollections.observableArrayList();
		for (int i = 0; i < dados.getJSONArray("qsa").length(); i++) {
			lista_socios.add(dados.getJSONArray("qsa").get(i).toString().replaceAll("[\"qual\\{\\}]", "")
					.replaceAll("[\\,:]", " "));
		}
		return lista_socios;

	}

	public String getSituacao() {
		return dados.getString("situacao") != null ? dados.getString("situacao") : "";
	}

	public String getBairro() {
		return dados.getString("bairro") != null ? dados.getString("bairro") : "";
	}

	public String getEndereco() {
		return dados.getString("logradouro") != null ? dados.getString("logradouro") : "";
	}

	public String getNumero() {
		return dados.getString("numero") != null ? dados.getString("numero") : "";
	}

	public String getCep() {
		return dados.getString("cep") != null ? dados.getString("cep") : "";
	}

	public String getCidade() {
		return dados.getString("municipio") != null ? dados.getString("municipio") : "";
	}

	public String getDataAbertura() {
		return dados.getString("abertura") != null ? dados.getString("abertura") : "";
	}

	public String getNaturezaJuridica() {
		return dados.getString("natureza_juridica") != null ? dados.getString("natureza_juridica") : "";
	}

	public String getCnpj() {
		return dados.getString("cnpj") != null ? dados.getString("cnpj") : "";
	}

	public String getUltimaAtualizacao() {
		return dados.getString("ultima_atualizacao") != null ? dados.getString("ultima_atualizacao") : "";
	}

	public String getStatus() {
		return dados.getString("status") != null ? dados.getString("status") : "";
	}

	public String getTipo() {
		return dados.getString("tipo") != null ? dados.getString("tipo") : "";
	}

	public String getEfr() {
		return dados.getString("efr") != null ? dados.getString("efr") : "";
	}

	public String getMotivoSituacao() {
		return dados.getString("motivo_situacao") != null ? dados.getString("motivo_situacao") : "";
	}

	public String getDataSituacaoEspecial() {
		return dados.getString("situacao_especial") != null ? dados.getString("situacao_especial") : "";
	}

	public String getCapitalSocial() {
		return dados.getString("capital_social") != null ? dados.getString("capital_social") : "";
	}

	public String getExtra() {
		return dados.getString("extra") != null ? dados.getString("extra") : "";
	}

	public ObservableList<String> getAtividadesSegundarias() {
		ObservableList<String> lista_socios = FXCollections.observableArrayList();
		for (int i = 0; i < dados.getJSONArray("atividades_secundarias").length(); i++) {
			lista_socios.add(dados.getJSONArray("atividades_secundarias").get(i).toString());
		}
		return lista_socios;

	}

	public static void main(String[] args) {
		System.out.println(new BuscaCnpj("60.628.922/0001-70").getNome());
	}

	/* 
	 *	Gson gson = new Gson();
	 *	Person johnDoe = gson.fromJson(json,Person.class); json p/ obj java
	 *	Car audi = new Car("Audi", "A4", 1.8); 
	 * 	Gson gson = new Gson(); 
	 * 	String json = gson.toJson(audi);  obj java p/ json
	 */
}
