package com.postoGasolina.model;

import java.util.Properties;
import java.util.Random;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.simplejavamail.mailer.Mailer;
import org.simplejavamail.mailer.config.TransportStrategy;

public class Email {

	public int enviarEmailAniversario(String emailDestinatario, String nome, String mensagem) throws EmailException {

		SimpleEmail email = new SimpleEmail();
		// Utilize o hostname do seu provedor de email
		// alterando hostname...
		email.setHostName("smtp.gmail.com");
		// smtp.gmail.com porta 465 -- padrão gmail
		email.setSmtpPort(465);
		// Email do destinatário
		email.addTo(emailDestinatario.toLowerCase().replaceAll(" ", ""), nome);
		// Email do remetente
		email.setFrom("admin@fsm.com.br", "Fuel Station Manager (Gerenciador de Postos de Combustíveis)");
		// Nome do assunto
		email.setSubject("Aniversário");

		// Adicione a mensagem do email
		email.setMsg(mensagem);
		// Autenticando o servidor para enviar msg
		email.setSSL(true);
		email.setAuthentication("****************", "**********");
		// enviando ..
		email.send();
		return 1;
	}
	public int enviarNovaSenha(String emailDestinatario, String nome, String mensagem) throws EmailException {

		SimpleEmail email = new SimpleEmail();
		// Utilize o hostname do seu provedor de email
		// alterando hostname...
		email.setHostName("smtp.gmail.com");
		// smtp.gmail.com porta 465 -- padrão gmail
		email.setSmtpPort(465);
		// Email do destinatário
		email.addTo(emailDestinatario.toLowerCase().replaceAll(" ", ""), nome);
		// Email do remetente
		email.setFrom("admin@fsm.com.br", "Fuel Station Manager (Gerenciador de Postos de Combustíveis) - ");
		// Nome do assunto
		email.setSubject("Nova senha");
		// Adicione a mensagem do email
		email.setMsg("Nova senha gerada com sucesso : "+mensagem);
		// Autenticando o servidor para enviar msg
		email.setSSL(true);
		email.setAuthentication("****************", "****************");
		// enviando ..
		email.send();
		return 1;
	}

	public void enviarEmail() {

		org.simplejavamail.email.Email email = new org.simplejavamail.email.Email();

		email.setFromAddress("lollypop", "lol.pop@somemail.com");
		email.addRecipient("C.Cane", "ssss", RecipientType.TO);
		email.setText("We should meet up!");
		email.setTextHTML("<b>We should meet up!</b>");
		email.setSubject("hey");

		new Mailer("smtp.gmail.com", 25, "your user", "your password", TransportStrategy.SMTP_TLS).sendMail(email);
		new Mailer("smtp.gmail.com", 587, "your user", "your password", TransportStrategy.SMTP_TLS).sendMail(email);
		new Mailer("smtp.gmail.com", 465, "your user", "your password", TransportStrategy.SMTP_SSL).sendMail(email);
	}

	public String geradorSenhaAutomatico() {
		// adicionamos caracteres para gerar a senha
		String caracteres = "qwertyuioplkjhgfdsazxcvbnmMNBVCXZASDFGHJKLPOIUYTREWQ0123456789";
		// instancia random
		Random aleatorio = new Random();
		// cria a string novaSenha
		String novaSenha = "";
		for (int i = 0; i < 10; ++i) {
			// gera um numero aleatório de acordo com a quantidade maxima da
			// string caracteres
			int gerador = aleatorio.nextInt(caracteres.length());
			// concatena o caractere selecionado na nova senha
			novaSenha += caracteres.charAt(gerador);
		}
		// retorna a nova senha
		return novaSenha;
	}

	public int adicionarNovaSenhaFuncionario(String email, String novaSenha) {
		return 0;

	}
}
