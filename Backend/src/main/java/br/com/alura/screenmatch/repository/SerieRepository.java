package br.com.alura.screenmatch.repository;

//import br.com.alura.screenmatch.dto.SerieDTO;
import br.com.alura.screenmatch.model.Categoria;
import br.com.alura.screenmatch.model.Episodio;
import br.com.alura.screenmatch.model.Serie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.awt.print.Pageable;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface SerieRepository extends JpaRepository<Serie, Long> {
   Optional<Serie> findByTituloContainingIgnoreCase(String nomeSerie);
   @Query("SELECT s FROM Serie s WHERE LOWER(s.atores) LIKE LOWER(CONCAT('%', :nomeAutor, '%'))")
   List<Serie> buscarSeriesPorAtores(@Param("nomeAutor") String nomeAutor);

   List<Serie> findByGenero(Categoria categoria);
   List<Serie> findByTotalTemporadasLessThanEqualAndAvaliacaoGreaterThanEqual(int temporadaMaxima, double avaliacao);
@Query("SELECT s FROM Serie s WHERE s.totalTemporadas <= :totalTemporadas AND s.avaliacao >= :avaliacao")
   List<Serie> seriesPorTemporadaEAvaliacao(int totalTemporadas, double avaliacao);

   @Query("SELECT e FROM Serie s JOIN s.episodios e WHERE s = :serie AND YEAR(e.dataLancamento) >= :anoLancamento")
   List<Episodio> episodiosPorSerieEAno(Serie serie, int anoLancamento);


   Optional<Serie> findFirstByTituloContainingIgnoreCase (String nomeSerie);

   @Query("SELECT s FROM Serie s " +
           "JOIN s.episodios e " +
           "GROUP BY s " +
           "ORDER BY MAX(e.dataLancamento) DESC")
   List<Serie> lancamentosMaisRecentes();

   @Query("SELECT e FROM Serie s JOIN s.episodios e WHERE s.id = :id AND e.temporada = :numero")
   List<Episodio> obterEpisodiosPorTemporada(Long id, Long numero);

   @Query("SELECT e FROM Serie s JOIN s.episodios e WHERE s = :serie ORDER BY e.avaliacao DESC LIMIT 5")
   List<Episodio> topEpisodiosPorSerie(Serie serie);


 

}


