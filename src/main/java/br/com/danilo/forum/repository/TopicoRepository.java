package br.com.danilo.forum.repository;

import br.com.danilo.forum.modelo.Topico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TopicoRepository extends JpaRepository<Topico, Long> {
    Page<Topico> findByCurso_Nome(String nomeCurso, Pageable paginacao);
    Topico findOneByTitulo(String titulo);

    @Query("Select t from Topico t WHERE t.curso.nome = :nomeCurso")
    Page<Topico> carregarPorNomeDoCurso(@Param("nomeCurso") String nomeCurso, Pageable paginacao);

}
