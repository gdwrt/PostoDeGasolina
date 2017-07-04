package com.postoGasolina.model.validacoes;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class FormatterSoLetraNumero extends PlainDocument {
	private int quantidadeMaxima;

	public FormatterSoLetraNumero(int maxLenght) {
		super();
		if (maxLenght <= 0)
			throw new IllegalArgumentException("Especifique a quantidade!");
		quantidadeMaxima = maxLenght;
	}

	@Override
	public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
		// se str não for null e o documento não tiver mais que "max"
		// caracteres,
		// insere o novo caracter
		if (str != null && getLength() < quantidadeMaxima)
			super.insertString(offset, str.replaceAll("[^a-z|^A-Z|^0-9|^ áâéíõúçãàêóôüÁÂÉÍÕÚÇÃÀÊÓÔÜ]", ""), attr);
	}
}