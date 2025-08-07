package br.com.alura.screenmatch.repository;

import br.com.alura.screenmatch.model.Episodio;
import br.com.alura.screenmatch.model.Serie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

public interface EpisodioRepository extends JpaRepository<Episodio, Long> {

    List<Episodio> findTop10BySerieOrderByAvaliacaoDesc(Serie serie);


    @Query("SELECT DISTINCT e FROM Episodio e JOIN e.serie s " +
            "WHERE LOWER(s.titulo) LIKE LOWER(CONCAT('%', :titulo, '%')) " +
            "AND e.dataLancamento >= :data")
    List<Episodio> findDistinctBySerieTituloContainingIgnoreCaseAndDataLancamentoGreaterThanEqual(
            @Param("titulo") String titulo, @Param("data") LocalDate data);

    List<Episodio> findBySerie_TituloContainingIgnoreCaseAndDataLancamentoGreaterThanEqual(
            String nomeSerie, LocalDate dataEpisodio);

    List<Episodio> findByTituloContainingIgnoreCase(String trecho);

    // 🔹 Novo método para evitar duplicados
    boolean existsBySerie_IdAndTemporadaAndNumeroEpisodio(Long serieId, int temporada, int numeroEpisodio);

    List<Episodio> findBySerie_Id (Long id);
}

