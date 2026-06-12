package com.tap.ap2restapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// @SpringBootApplication é a anotação principal do Spring Boot. 
// Ela faz 3 coisas automaticamente:
// 1. @Configuration: Diz que essa classe pode conter configurações do projeto.
// 2. @EnableAutoConfiguration: Pede pro Spring Boot adivinhar e configurar o banco H2, o Tomcat (servidor web) e outras coisas automaticamente.
// 3. @ComponentScan: Diz para o Spring procurar outros pacotes e classes (como os Controllers e Models) para rodar junto com a aplicação.
@SpringBootApplication
public class Ap2restapiApplication {

	// Este é o método "main". Assim como em qualquer programa Java convencional, 
	// este é o ponto de entrada. É a primeira coisa que o computador executa quando você dá o Play.
	public static void main(String[] args) {
		// Essa linha chama a classe SpringApplication e liga o motor do Spring Boot.
		// Ela inicia o servidor Tomcat na porta 8080, cria a conexão com o banco em memória
		// e deixa a API rodando no ar aguardando requisições.
		SpringApplication.run(Ap2restapiApplication.class, args);
	}

}
