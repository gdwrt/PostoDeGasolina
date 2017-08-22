package com.postoGasolina.controller;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.URL;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.ResourceBundle;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXTextField;
import com.postoGasolina.model.Email;
import com.postoGasolina.util.NumeroTextField;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class TelaGerarCupomDescontoController implements Initializable {
	@FXML
	private GridPane gridPaneBottom;

	@FXML
	private JFXButton btnGerarCupom;

	@FXML
	private ImageView imageAdicionar;

	@FXML
	private JFXTextField campoTipoServico;

	private NumeroTextField campoValor = new NumeroTextField(BigDecimal.ZERO,
			NumberFormat.getCurrencyInstance(new Locale("pt", "BR")));

	@FXML
	private JFXTextField campoNome;

	@FXML
	private BorderPane borderPane;

	private JFXSnackbar snackBar;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		campoNome.setText(TelaGerenciarFidelizacaoClientesController.nomeCliente.getText());

		campoValor.getStyleClass().add("format-campo");
		campoValor.setUnFocusColor(Color.GRAY);
		campoValor.setFocusColor(Color.GRAY);
		campoValor.setFont(Font.font("Arial", FontWeight.NORMAL, 16));
		campoValor.setOpacity(0.95);
		campoValor.setFocusTraversable(false);
		gridPaneBottom.add(campoValor, 0, 4);
		gridPaneBottom.setMargin(campoValor, new Insets(0, 30, 0, 30));

		campoNome.getStyleClass().add("format-campo");
		campoNome.setUnFocusColor(Color.GRAY);
		campoNome.setFocusColor(Color.GRAY);
		campoNome.setOpacity(0.95);
		campoNome.setFont(Font.font("Arial", FontWeight.NORMAL, 16));
		campoNome.setFocusTraversable(false);

		campoTipoServico.getStyleClass().add("format-campo");
		campoTipoServico.setUnFocusColor(Color.GRAY);
		campoTipoServico.setFocusColor(Color.GRAY);
		campoTipoServico.setOpacity(0.95);
		campoTipoServico.setFont(Font.font("Arial", FontWeight.NORMAL, 16));
		campoTipoServico.setFocusTraversable(false);

	}

	void btnEnviar(ActionEvent event) {

		new Email().enviarEmail();
	}

	@FXML
	void btnGerarCupom(ActionEvent event) {

		try {

			if (!campoTipoServico.getText().isEmpty() && !campoValor.getNumber().equals(BigDecimal.ZERO)) {

				OutputStream file = new FileOutputStream(new File("CupomDesconto.pdf"));

				Document document = new Document();
				PdfWriter.getInstance(document, file);

				document.open();

				// parágrafo
				Paragraph paragraph = new Paragraph();

				// nova linha
				paragraph.add(new Paragraph(" "));
				paragraph.add(new Paragraph("                    CUPOM DE DESCONTO "
						+ LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"))));
				paragraph.add(new Paragraph(" "));
				document.add(paragraph);

				// cria tabela e carrega registros
				PdfPTable pdfTable = new PdfPTable(3);
				PdfPCell cell1 = new PdfPCell(new Phrase("Cliente"));
				cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
				pdfTable.addCell(cell1);
				cell1 = new PdfPCell(new Phrase("Tipo de serviço"));
				cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
				pdfTable.addCell(cell1);
				cell1 = new PdfPCell(new Phrase("Desconto"));
				cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
				pdfTable.addCell(cell1);

				pdfTable.setHeaderRows(1);

				BigDecimal total = BigDecimal.ZERO;

				pdfTable.addCell(campoNome.getText());
				pdfTable.addCell(campoTipoServico.getText());
				pdfTable.addCell(campoValor.getText());
				pdfTable.addCell("  ");

				document.add(pdfTable);

				document.close();
				file.close();

				Desktop.getDesktop().open(new File("CupomDesconto.pdf"));

				snackBar = new JFXSnackbar(borderPane);
		//		String style = getClass().getResource("/com/postoGasolina/style/SnackBar.css").toExternalForm();
				snackBar.show("Relatório gerado com sucesso", 4000);

				campoTipoServico.setText("");
				campoValor.setNumber(BigDecimal.ZERO);
			} else {
				snackBar = new JFXSnackbar(borderPane);
		//		String style = getClass().getResource("/com/postoGasolina/style/SnackBar.css").toExternalForm();
				snackBar.show("Campos Obrigatórios não informado", 4000);
			}

		} catch (Exception e) {
			e.printStackTrace();
			JFXSnackbar s = new JFXSnackbar(borderPane);
		//	String style = getClass().getResource("/com/postoGasolina/style/SnackBar.css").toExternalForm();
			s.show("Cliente não possui cadastro completo", 4000);
		}

	}

}
