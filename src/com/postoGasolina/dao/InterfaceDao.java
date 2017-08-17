package com.postoGasolina.dao;

import java.sql.SQLException;

import javafx.collections.ObservableList;

public interface InterfaceDao<T> {
	void cadastrar(T t) throws ClassNotFoundException, SQLException;
	void alterar(T t) throws SQLException, ClassNotFoundException;
	void remover(int id) throws ClassNotFoundException, SQLException;
	ObservableList<T>  listar() throws ClassNotFoundException, SQLException;
	
}
