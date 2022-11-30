# pessoa
Projeto pessoa com verificação de tipo de pessoa (CPF ou CNPJ)

Na classe PessoaController foram criados os endpoints para busca por id, update, delete, e buscar todas as pessoas, que são desponibilizadas por páginas.

Na classe PessoaService tem as manipulações dos dados e regras, também a classificação quanto ao identificador, se é um cpf ou cnpj.

Os testes para Controller, service e Repository se usou JUnit, o MockMvc, Mockito pra testar as classes verificando tanto caminhos felizes quanto exceções, para testar o repository foi usado o banco de dados em memoria h2 pra simular a inserção e retorno como em um banco real.

O Banco de dados oficial é o mysql, a documentação foi feita usando o swagger 3, foram criadas também validações específicas para garantir o tamanho e tipo da informação, pra receber apenas numeros do tamanho de cpf e cnpj, para evitar verbosidade foi utilizado o Lombok pra automatizar a criação dos constructors, getters e setters.

para acessar a documentação o caminho é localhost:8083/swagger-ui.html e também foi disponibilizado o arquivo da collection do postman, Pessoa.postman_collection.json.
