package com.postoGasolina.dao;

import java.sql.SQLException;

import javafx.collections.ObservableList;

public interface IMetodos {
	void cadastrar(Object objeto) throws ClassNotFoundException, SQLException;
	void alterar(Object objeto) throws SQLException, ClassNotFoundException;
	void Remover(int id) throws ClassNotFoundException, SQLException;
	ObservableList<?>  listar() throws ClassNotFoundException, SQLException;
	
}
