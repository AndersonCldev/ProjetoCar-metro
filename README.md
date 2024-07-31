```markdown
# Carômetro

O **Carômetro** é um aplicativo de gerenciamento de alunos desenvolvido em Java, utilizando a biblioteca Swing para a interface gráfica e JDBC para manipulação de banco de dados. O sistema permite adicionar, editar, buscar e excluir registros de alunos, além de armazenar e visualizar fotos associadas.
![Logo do Projeto](img/LogoPrograma.png)


## Funcionalidades

- **Gerenciamento de Dados dos Alunos**:
  - Adicionar novos alunos com nome e foto.
  - Editar informações de alunos existentes, incluindo atualização de foto.
  - Excluir registros de alunos.
  - Buscar alunos por RA e visualizar seus dados e foto.

- **Interface Gráfica do Usuário (GUI)**:
  - Campo para inserção e busca de RA e nome.
  - Lista de sugestões de nomes para facilitar a busca.
  - Painel para exibição e carregamento de fotos.

- **Verificação de Conexão**:
  - Exibição do status da conexão com o banco de dados.

- **Validações**:
  - Validação dos campos de entrada para garantir dados corretos.

- **Funcionalidades Adicionais**:
  - Botão "Sobre" para informações adicionais sobre o sistema.

## Requisitos

- **Java Development Kit (JDK)**: Versão 8 ou superior.
- **Banco de Dados**: Qualquer banco de dados SQL compatível com JDBC (por exemplo, MySQL, PostgreSQL).
- **Driver JDBC**: Dependente do banco de dados utilizado.

## Instalação

1. **Clone o Repositório**:
   ```bash
   git clone https://github.com/seu-AndersonCldev
/ProjetoCar-metro.git
   cd carometro
   ```

2. **Configuração do Banco de Dados**:
   - Configure o banco de dados e ajuste as configurações de conexão no arquivo `DAO.java` para corresponder às suas credenciais e URL do banco de dados.

Para configurar o banco de dados utilizado pelo Carômetro, siga estes passos:

3. **Criação do Banco de Dados e Tabela**

   Execute os seguintes comandos SQL para criar o banco de dados e a tabela necessária:

   ```sql
   CREATE DATABASE dbcadastroCanditado;
   USE dbcadastroCanditado;

   CREATE TABLE alunos (
       RA INT PRIMARY KEY AUTO_INCREMENT,
       nome VARCHAR(30) NOT NULL,
       foto LONGBLOB NOT NULL
   );
   ```

4. **Exemplos de Consultas SQL**

   - **Visualizar Todos os Alunos**:
     ```sql
     SELECT * FROM alunos;
     ```

   - **Buscar Aluno por RA**:
     ```sql
     SELECT * FROM alunos WHERE RA = 1;
     ```

   - **Buscar Alunos por Nome (Filtragem e Ordenação)**:
     ```sql
     SELECT * FROM alunos WHERE nome LIKE 't%' ORDER BY nome LIMIT 1;
     ```

5. **Observações**

   - **LONGBLOB** é utilizado para armazenar as fotos dos alunos. Certifique-se de que o tipo de dados é compatível com o seu sistema de gerenciamento de banco de dados.
   - Ajuste a configuração de conexão no arquivo `DAO.java` para corresponder às suas credenciais e URL do banco de dados.

Caso haja necessidade de executar comandos adicionais para manutenção ou para realizar outras operações, ajuste conforme necessário para atender às suas necessidades específicas.
```

4. **Compilação e Execução**:
   - Compile o projeto com o comando:
     ```bash
     javac -d bin src/View/Carometro.java src/model/DAO.java src/utils/Validador.java
     ```
   - Execute o aplicativo com o comando:
     ```bash
     java -cp bin View.Carometro
     ```

## Uso

1. **Adicionar Aluno**:
   - Insira o RA e clique em "Buscar". Se o aluno não estiver cadastrado, insira o nome e carregue uma foto. Clique em "Adicionar" para salvar.

2. **Editar Aluno**:
   - Busque o aluno pelo RA e edite as informações conforme necessário. Clique em "Editar" para aplicar as alterações.

3. **Excluir Aluno**:
   - Busque o aluno pelo RA e clique em "Excluir" após confirmação para remover o registro.

4. **Buscar Aluno**:
   - Insira o RA no campo de busca para visualizar as informações e foto do aluno.

5. **Resetar Dados**:
   - Clique em "Resetar" para limpar os campos e restaurar a interface para o estado inicial.

## Contribuições

Contribuições são bem-vindas! Sinta-se à vontade para abrir issues, sugerir melhorias ou enviar pull requests. 

Para contribuir:

1. Faça um fork do repositório.
2. Crie uma branch para sua feature (`git checkout -b minha-nova-feature`).
3. Faça suas alterações e commit (`git commit -am 'Adiciona nova feature'`).
4. Envie sua branch para o repositório remoto (`git push origin minha-nova-feature`).
5. Abra um Pull Request.

## Licença

Este projeto está licenciado sob a [MIT License](LICENSE).


- **GitHub**: [AndersonCldev](https://github.com/seu-usuario)

---

Desenvolvido com 💙 por [AndersonCldev](https://github.com/seu-usuario).
```
