# Desafio TDS
Desafio composto pela criação de um micro serviço com uma API para encurtamento de URLs seguindo o padrão REST

### Executando a aplicação
Certifique-se de ter o Maven instalado e adicionado ao PATH de seu sistema operacional, assim como o Git.
```
git clone https://github.com/gilbertomoj/url_shortener
cd url_shortener
mvn spring-boot:run
Acesse http://localhost:8080/url
```
### APIs endpoints
GET http://localhost:8080/url/list [lista todas as urls]  
```
// Exemplo de return
[
	{
		"urlId": "dec3474c",
		"urlOrigin": "www.twitter.com",
		"urlAccess": "8hbBBn0cZ",
		"numAccess": 2,
		"lastAccess": "2023-01-10T04:59:06.302+00:00"
	},
	{
		"urlId": "7936be8e",
		"urlOrigin": "www.google.com",
		"urlAccess": "UUMje0oLx",
		"numAccess": 1,
		"lastAccess": "2023-01-10T16:52:36.552+00:00"
	},
    ...
]
```
GET http://localhost:8080/url/short/{codeAccess} [acessa uma url pelo código e redireciona para o link original]

GET http://localhost:8080/url/statistics [acessa o endpoint de estatisticas, com o total de acessos, e a porcentagem de acesso de cada link]
```
// Exemplo de retorno
Total de acessos à API : 21
----------------
Url: http://localhost/url/short/vs0FHlmRe
Número de acessos : 9
% de acessos : 42% 

Url: http://localhost/url/short/UUMje0oLx
Número de acessos : 6
% de acessos : 28% 

Url: http://localhost/url/short/nfdV9YWpx
Número de acessos : 3
% de acessos : 14% 
...
```
GET http://localhost:8080/url/get/{idUrl} [ acessa os detalhes de uma url]
```
// Exemplo
{
	"urlId": "7936be8e",
	"urlOrigin": "www.google.com",
	"urlAccess": "UUMje0oLx",
	"numAccess": 6,
	"lastAccess": "2023-01-11T01:46:56.420+00:00"
}   
```
POST http://localhost:8080/url/create [cadastra uma nova url]
```
{
// Exemplo de body
	"urlOrigin": "www.google.com"
}
```
PUT http://localhost:8080/url/update/ [atualiza os dados de uma url]
```
{
// Exemplo de body
	"urlId": "330b1d69",
	"urlOrigin": "www.varcha"
}
```
DELETE http://localhost:8080/url/delete/{urlId} [remove uma url por ID]

