package br.com.danilo.forum.controller.dto;

import br.com.danilo.forum.modelo.Curso;
import br.com.danilo.forum.modelo.Topico;
import br.com.danilo.forum.repository.CursoRepository;
import br.com.danilo.forum.repository.TopicoRepository;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class TopicoCreateDto {
    @NotNull @NotEmpty
    private String titulo;
    @NotNull @NotEmpty
    private String mensagem;
    @NotNull @NotEmpty
    private String nomeCurso;

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getNomeCurso() {
        return nomeCurso;
    }

    public void setNomeCurso(String nomeCurso) {
        this.nomeCurso = nomeCurso;
    }

    public Topico converter(CursoRepository cursoRepository) {

        Curso curso = cursoRepository.findByNome(nomeCurso);

        return new Topico(titulo, mensagem, curso);
    }
}
