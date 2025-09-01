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

🔹 Configuração de CORS

Para permitir que o frontend HTML/JS acesse a API sem bloqueio:

// src/main/java/br/com/screenmatch/config/CorsConfig.java
```
package br.com.screenmatch.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")                 // Permite todas as rotas
                        .allowedOrigins("http://localhost:5500") // URL do frontend
                        .allowedMethods("GET", "POST", "PUT", "DELETE")
                        .allowedHeaders("*")
                        .allowCredentials(true);
            }
        };
    }
}
```

🔹 Substitua "http://localhost:5500" pelo endereço real do seu frontend, se necessário.
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

Build e execução:
```
mvn clean install
java -jar target/screenmatch-api.jar

A API estará disponível em: http://localhost:8080/
```

2. Frontend

Abra a pasta do frontend

Configure a URL da API no JavaScript:

const API_URL = "http://localhost:8080";


Abra o index.html no navegador ou use um servidor local (ex: Live Server do VS Code)

🔹 Estrutura do Projeto

```
ScreenMatch/
│
├── backend/               → código Java/Spring Boot
│   ├── src/
│   └── target/            → build do backend
│
├── frontend/              → HTML, CSS e JS
│   ├── index.html
│   ├── css/
│   └── js/
│
└── README.md
```
🔹 Observações

Certifique-se que o backend esteja rodando antes de abrir o frontend

A chave da OMDB API é obrigatória para buscar filmes

Projeto ideal para estudar full stack Java + Web

👨‍💻 Autor

Matheus Martins

```
Email: mtz.martinss03@gmail.com
```
```
LinkedIn: https://www.linkedin.com/in/matheusmartnsdev/
```
