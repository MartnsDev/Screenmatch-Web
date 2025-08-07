import getDados from "./getDados.js";

const params = new URLSearchParams(window.location.search);
const serieId = params.get('id');
const listaTemporadas = document.getElementById('temporadas-select');
const fichaSerie = document.getElementById('temporadas-episodios');
const fichaDescricao = document.getElementById('ficha-descricao');
const top5Container = document.querySelector('.top5__container');

top5Container.style.display = 'none';

function carregarTemporadas() {
    getDados(`/series/${serieId}/temporadas/todas`)
        .then(data => {
            const temporadasUnicas = [...new Set(data.map(temporada => temporada.temporada))];
            listaTemporadas.innerHTML = '';

            const optionDefault = document.createElement('option');
            optionDefault.value = '';
            optionDefault.textContent = 'Selecione a temporada';
            listaTemporadas.appendChild(optionDefault);

            temporadasUnicas.forEach(temporada => {
                const option = document.createElement('option');
                option.value = temporada;
                option.textContent = `Temporada ${temporada}`;
                listaTemporadas.appendChild(option);
            });

            const optionTodos = document.createElement('option');
            optionTodos.value = 'todas';
            optionTodos.textContent = 'Todas as temporadas';
            listaTemporadas.appendChild(optionTodos);

            const optionTop5 = document.createElement('option');
            optionTop5.value = 'top5';
            optionTop5.textContent = 'Top 10 episódios';
            listaTemporadas.appendChild(optionTop5);
        })
        .catch(error => {
            console.error('Erro ao obter temporadas:', error);
        });
}

function carregarEpisodios() {
    const valorSelecionado = listaTemporadas.value;

    fichaSerie.innerHTML = '';
    top5Container.innerHTML = '';
    top5Container.style.display = 'none';

    if (!valorSelecionado) {
        return;
    }

    if (valorSelecionado === 'top5') {
        carregarTop5('todas');
        return;
    }

    getDados(`/series/${serieId}/temporadas/${valorSelecionado}`)
        .then(data => {
            if (!data || data.length === 0) {
                fichaSerie.innerHTML = '<p>Nenhum episódio encontrado para essa seleção.</p>';
                return;
            }

            const temporadasUnicas = [...new Set(data.map(ep => ep.temporada))];

            temporadasUnicas.forEach(temporada => {
                const ul = document.createElement('ul');
                ul.className = 'episodios-lista';

                const episodiosTemporadaAtual = data.filter(ep => ep.temporada === temporada);

                const listaHTML = episodiosTemporadaAtual.map(ep => `
                    <li>${ep.numeroEpisodio} - ${ep.titulo}</li>
                `).join('');
                ul.innerHTML = listaHTML;

                const paragrafo = document.createElement('p');
                paragrafo.textContent = `Temporada ${temporada}`;

                fichaSerie.appendChild(paragrafo);
                fichaSerie.appendChild(ul);
            });

            carregarTop5(valorSelecionado);
        })
        .catch(error => {
            console.error('Erro ao obter episódios:', error);
            fichaSerie.innerHTML = '<p>Erro ao carregar episódios.</p>';
        });
}

function carregarInfoSerie() {
    getDados(`/series/${serieId}`)
        .then(data => {
            fichaDescricao.innerHTML = `
                <img src="${data.poster}" alt="${data.titulo}" />
                <div>
                    <h2>${data.titulo}</h2>
                    <div class="descricao-texto">
                        <p><b>Média de avaliações:</b> ${data.avaliacao}</p>
                        <p>${data.sinopse}</p>
                        <p><b>Estrelando:</b> ${data.atores}</p>
                    </div>
                </div>
            `;
        })
        .catch(error => {
            console.error('Erro ao obter informações da série:', error);
        });
}


function carregarTop5(temporada = 'todas') {
    let url = `/series/${serieId}/top5`;
    if (temporada !== 'todas' && temporada !== 'top5') {
        url += `?temporada=${temporada}`;
    }

    getDados(url)
        .then(data => {
            top5Container.innerHTML = '';

            if (!data || data.length === 0) {
                top5Container.innerHTML = '<p>Nenhum episódio encontrado.</p>';
                top5Container.style.display = 'block';
                return;
            }

            data.forEach(ep => {
                const card = document.createElement('div');
                card.classList.add('episodio-card');

                card.innerHTML = `
                    <h4>${ep.numeroEpisodio} - ${ep.titulo}</h4>
                    <p><b>Temporada:</b> ${ep.temporada}</p>
                    <p><b>Avaliação:</b> ${ep.avaliacao}</p>
                `;

                top5Container.appendChild(card);
            });

            top5Container.style.display = 'flex';
        })
        .catch(error => {
            console.error('Erro ao carregar Top 5 episódios:', error);
            top5Container.innerHTML = '<p>Erro ao carregar Top 5 episódios.</p>';
            top5Container.style.display = 'block';
        });
}


listaTemporadas.addEventListener('change', carregarEpisodios);
carregarInfoSerie();
carregarTemporadas();
carregarEpisodios();