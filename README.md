# Teste técnico back end - Supera Inovação e Tecnologia

## Objetivo 

Construir uma API RESTful para emissao de extrato bancario, com capacidade de filtrar dados de transações a partir do código de número de conta (obrigatório), data inicial, data final e nome do operador.

O dado buscado pelo endpoint é um objeto paginado com informações sobre o tamanho da página, quantidade de elementos totais, total de páginas disponíveis, número da página atual e saldos total e por período.

## Como executar a aplicação 

- Você pode executar a aplicação da maneira que quiser e utilizando a IDE de sua preferência. 
- Caso queira executar a aplicação via linha de comando, execute primeiramente o comando:
```
./mvnw clean package  para linux.
.\mvnw clean package  para windows.
```
- Após isso execute o comando: 
```
  java -jar <...caminhoParaSeuJar>
```

## Como executar os testes unitários 

- Você pode executar usando sua IDE. 
- Caso queira executar a aplicação via linha de comando, vá ate a raiz do projeto e execute:
```
mvn test
```

## Documentação

### Swagger
Para visualizar a documentação da API por meio do Swagger, siga os passos abaixo:
1. Certifique-se de que a aplicação esteja em execução localmente.
2. Acesse a seguinte URL em seu navegador: http://localhost:8080/swagger-ui/index.html

### Manipulando as requisições da API.
#### Para testar o projeto, recomendo usar alguma ferramenta para realizar as requisições, como o Postman ou Insomnia.
Para realizar os dados da requisição, use o verbo HTTP **<font color="blue">GET</font>** e insira a url.

<details>
  <summary>Exemplo</summary>

#### URL
**<font color="blue">GET</font>** `http://localhost:8080/api/transfer/v1/1?page=0&startDate=2022-12-31%2021:00:00&transactionOperatorName=Brockie`

#### Objeto de resposta
```
{
    "pagedTransfers": {
        "links": [
            {
                "rel": "last",
                "href": "http://localhost:8080/api/transfer/v1/1?page=1&startDate=2022-12-31%2021:00:00&endDate=2023-07-18%2015:08:24&transactionOperatorName=Brockie"
            },
            {
                "rel": "next",
                "href": "http://localhost:8080/api/transfer/v1/1?page=1&startDate=2022-12-31%2021:00:00&endDate=2023-07-18%2015:08:24&transactionOperatorName=Brockie"
            }
        ],
        "content": [
            {
                "id": 5,
                "transferDate": "2023-02-18T05:32:40.000+00:00",
                "amount": -998.74,
                "type": "SAQUE",
                "transactionOperatorName": "Brockie",
                "links": []
            }
        ],
        "page": {
            "size": 4,
            "totalElements": 1,
            "totalPages": 1,
            "number": 0
        }
    },
    "totalBalance": 88500.45,
    "periodBalance": -998.74
}
```
</details>


## Recomendação
Esse é o [link do repositorio](https://github.com/YohanDevPs/supera-desafio-front) do front end construída em React, responsável por consumir essa API. Instale e execute ambos os projetos para uma experiência completa.

![Projeto transação bancaria front end](https://github.com/YohanDevPs/supera-desafio-back/assets/87953006/5abcaca6-00de-440d-a4cf-771f6c4c244c)
