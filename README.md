# Pedido-SpringBoot

## Requirementos

Para construir e executar o aplicativo que você precisa:

- JDK 11
- MAVEN 3(Opcional)

## Executando o aplicativo localmente

### Maven

Para rodar vá na pasta raiz do projeto e execute o comando:

```
mvn spring-boot:run
```

### IDE STS

Executar o método principal na classe com.edimar.loja.PedidoApplication.java no seu IDE.

## Executando os testes - POSTMAN

O arquivo com os endpoints testados está na pasta "postman" na raiz do projeto, com o nome de "***postman_collection.json***"

## Executando os testes - Unitários

### MAVEN

Para executar os testes e compilar o projeto deve executar o comando "***mvn test***"

### IDE STS

Executar as classes “ClienteControllerTest.java”, “ItemPedidoControllerTest.java”, “PedidoControllerTest.java” e “ProdutoControllerTest.java”.

## Copyright
Released under the Apache License 2.0.
