# ???

# Descrição
Compilador da Linguagem `???`.
Projeto desenvolvido para a disciplina `CCM510 - COMPILADORES`.

# Sumario
- [To Do](#To-Do)
- [Tecnologias](#Tecnologias)
- [Estrutura de Arquivos](#Estrutura-de-Arquivos)
- [Como Executar](#Como-Executar)
- [Autores](#Autores)

# To-Do
- [X] Documentação da Linguagem
- [X] Analise Lexica:
    - [X] Reconhecer tokens
- [X] Analise Sintatica:
    - [X] Gramatica
    - [X] Arvore
- [ ] Analise Semantica:
    - [ ] Detectar declarações repetidas ( usar stack ) 
	- [ ] Detectar variaveis recebendo valores errados ( map )
    - [ ] Verificar se a função possui Return
	- [ ] Detectar limite de inteiro e double
- [X] Erros claros:
    - [X] Linha e Coluna
    - [X] Indicar o lexema errado
    - [X] Mensagem clara do erro
- [X] Tradução para linguagem Kotlin:
    - [X] calculadora base 26
    - [X] Tradução Simbolo -> Letra
    - [X] Prefixo ( Evitar variaveis com nome de palavras reservadas )
    - [ ] Arrumar arredondamento de numeros
- [ ] Testes
    - [X] Analise Lexica
    - [X] Analise Sintatica
    - [ ] Analise Semantica
    - [ ] Traducao
    - [ ] Calculadora
- [X] ide:
	- [X] editor de texto
    - [X] highlight
    - [X] vim
    - [X] arvore
    - [X] erro de compilação
    - [X] codigo traduzido

# Tecnologias
- Spring Boot
- Java
- HTML
- CSS
- JavaScript

# Estrutura de Arquivos

# Como executar
## Pré-requisitos
- Java
- Maven
## Clonar repositorio
```
git clone git@github.com:rhy2106/Compilador.git
```
## Configurar a porta do servidor
Altere a porta do servidor no arquivo: `/Compilador/src/main/resources/application.properties`
## Executar Testes
```
mvn test
```
## Executar
Dentro do diretorio `/Compilador`. Execute:
```
mvn spring-boot:run
```
## Acessar
Para acessar entre em
```
http://[IP]:[PORTA]
```
Default: ( na maquina do server )
```
http://localhost:3000
```
# Autores
- **Rafael Hideaki Yara**
- **Mauricio Yudi Kuniyoshi**

