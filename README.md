# 🎬 ScreenMatch

![ScreenMatch](https://raw.githubusercontent.com/MartnsDev/Screenmatch-Web/main/Screenmatch.png)

O **ScreenMatch** é uma aplicação web para gerenciamento de filmes e séries.  
Permite buscar filmes na **API OMDB**, salvar no banco de dados, marcar como favoritos e avaliar títulos.  
O frontend é feito em **HTML, CSS e JavaScript** consumindo a API do backend.

---

## 📌 Funcionalidades

- Buscar filmes e séries na **API OMDB**  
- Salvar filmes no banco de dados local  
- Salvar Episodios com todas as info
- Ver avaliações de filmes, series e ep. 
- Listar filmes cadastrados no banco  
- Interface web responsiva e interativa  

---

## 🛠️ Tecnologias Utilizadas

- **Frontend:** HTML, CSS, JavaScript  
- **Backend:** Java + Spring Boot  
- **Banco de dados:** MySQL ou outro compatível  
- **API externa:** [OMDB API](http://www.omdbapi.com/)  

---

## ▶️ Como Executar

### 1. Backend

1. Configure o banco de dados e crie um schema (ex: `screenmatch_db`)  
2. Configure variáveis de ambiente ou `application.properties` com credenciais do banco e chave da OMDB API:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/screenmatch_db
spring.datasource.username=root
spring.datasource.password=sua_senha
```
