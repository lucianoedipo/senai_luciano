# Projeto SENAI

Este é um projeto baseado no Spring PetClinic, adaptado para atender às necessidades específicas do SENAI.

## Executando o SENAI localmente
O SENAI é uma aplicação Spring Boot construída usando o Maven. Você pode construir um arquivo JAR e executá-lo a partir da linha de comando (ele deve funcionar igualmente bem com Java 17 ou superior):

```bash
git clone https://github.com/spring-projects/spring-petclinic.git
cd spring-petclinic
./mvnw package
java -jar target/*.jar


Or you can run it from Maven directly using the Spring Boot Maven plugin. If you do this, it will pick up changes that you make in the project immediately (changes to Java source files require a compile as well - most people use an IDE for this):

```
./mvnw spring-boot:run
```
