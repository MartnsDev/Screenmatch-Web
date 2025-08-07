package br.com.alura.screenmatch.service;

import br.com.alura.screenmatch.dto.EpisodioDTO;
import br.com.alura.screenmatch.dto.SerieDTO;
import br.com.alura.screenmatch.model.Categoria;
import br.com.alura.screenmatch.model.Episodio;
import br.com.alura.screenmatch.model.Serie;
import br.com.alura.screenmatch.repository.EpisodioRepository;
import br.com.alura.screenmatch.repository.SerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.Normalizer;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Service
public class SerieService {

    @Autowired
    private SerieRepository repositorio;
    @Autowired
    private EpisodioRepository episodioRepositorio;

    public List<SerieDTO> obterTodasAsSeries ( ) {
        return repositorio.findAll()
                .stream()
                .map(s -> new SerieDTO(
                        s.getId(),
                        s.getTitulo(),
                        s.getTotalTemporadas(),
                        s.getAvaliacao(),
                        s.getGenero(),
                        s.getAtores(),
                        s.getPoster(),
                        s.getSinopse()))
                .collect(Collectors.toList());
    }

    public List<SerieDTO> obterTop5 ( ) {
        return repositorio.findAll()
                .stream()
                .sorted(Comparator.comparingDouble(Serie::getAvaliacao).reversed())
                .limit(5)
                .map(s -> new SerieDTO(
                        s.getId(),
                        s.getTitulo(),
                        s.getTotalTemporadas(),
                        s.getAvaliacao(),
                        s.getGenero(),
                        s.getAtores(),
                        s.getPoster(),
                        s.getSinopse()
                ))
                .collect(Collectors.toList());
    }


    private List<SerieDTO> converteDados (List<Serie> series) {
        return series.stream()
                .map(s -> new SerieDTO(
                        s.getId(),
                        s.getTitulo(),
                        s.getTotalTemporadas(),
                        s.getAvaliacao(),
                        s.getGenero(),
                        s.getAtores(),
                        s.getPoster(),
                        s.getSinopse()
                ))
                .collect(Collectors.toList());
    }

    public List<SerieDTO> obterLancamentos ( ) {
        return converteDados(repositorio.lancamentosMaisRecentes());
    }

    public SerieDTO obterPorId (Long id) {
        Optional<Serie> serie = repositorio.findById(id);

        if (serie.isPresent()) {
            Serie s = serie.get();
            return new SerieDTO(
                    s.getId(),
                    s.getTitulo(),
                    s.getTotalTemporadas(),
                    s.getAvaliacao(),
                    s.getGenero(),
                    s.getAtores(),
                    s.getPoster(),
                    s.getSinopse()
            );
        }
        return null;
    }


    public List<EpisodioDTO> obterTodasTemporadas (Long id) {
        return repositorio.findById(id)
                .map(s -> s.getEpisodios().stream()
                        .map(e -> new EpisodioDTO(e.getTemporada(), e.getNumeroEpisodio(), e.getTitulo(), e.getAvaliacao()))
                        .collect(Collectors.toList()))
                .orElse(Collections.emptyList());

    }

    public List<EpisodioDTO> obterTemporadasPorNumero(Long id, Long numero) {
        return repositorio.obterEpisodiosPorTemporada(id, numero)
                .stream()
                .map(e -> new EpisodioDTO(e.getTemporada(), e.getNumeroEpisodio(), e.getTitulo(), e.getAvaliacao()))
                .collect(Collectors.toList());
    }

    public List<SerieDTO> obterSeriesPorGenero(String categoria) {
        Categoria genero = Categoria.fromPortugues(categoria);
        return converteDados(repositorio.findByGenero(genero));
    }



    public List<EpisodioDTO> obterTop5Episodios(Long id) {
        var serie = repositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Série não encontrada"));

        return episodioRepositorio.findTop10BySerieOrderByAvaliacaoDesc(serie)
                .stream()
                .map(e -> new EpisodioDTO(
                        e.getTemporada(),
                        e.getNumeroEpisodio(),
                        e.getTitulo(),
                        e.getAvaliacao()
                ))
                .collect(Collectors.toList());
    }


}