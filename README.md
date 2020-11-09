# consuming-api-storing-postgres-spring-boot-jdbc
Consuming an API with Java (Spring Boot) without using JPA. The content is then stored in a PostgreSQL database.

Consumindo uma API com Java (Spring Boot) sem usar JPA. O conteúdo é armazenado num banco PostgreSQL. 
A Api utilizada é a TheCatApi, que retorna imagens aleatórias de gatos, bem como suas respectivas dimensões.

Utilização: Somente o api-client deve ser executado.
Importante: Modifique o arquivo connection/ConnectionFactory.java de acordo com a sua própria instalação do PostgresSQL. 
Então crie a tabela catsdb com os seguintes campos: id, url, width, height
