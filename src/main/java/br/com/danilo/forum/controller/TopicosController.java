package br.com.danilo.forum.controller;

import br.com.danilo.forum.controller.dto.DetalhesTopicoDto;
import br.com.danilo.forum.controller.dto.TopicoCreateDto;
import br.com.danilo.forum.controller.dto.TopicoDto;
import br.com.danilo.forum.modelo.*;
import br.com.danilo.forum.repository.CursoRepository;
import br.com.danilo.forum.repository.TopicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;


import javax.validation.Valid;
import java.net.URI;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/topicos")
public class TopicosController {

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private CursoRepository cursoRepository;


    @GetMapping()
    public List<TopicoDto> list(String nomeCurso){

        if(nomeCurso == null)
         return TopicoDto.converter(topicoRepository.findAll());
        else {
          return TopicoDto.converter(topicoRepository.findByCurso_Nome(nomeCurso));
        }
    }
    @PostMapping()
    public ResponseEntity<TopicoDto> cadastrar(@RequestBody @Valid TopicoCreateDto novoTopico, UriComponentsBuilder uriBuilder){

        Topico topico =  novoTopico.converter(cursoRepository);
        topicoRepository.save(topico);

        URI uri =  uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();

        return ResponseEntity.created(uri).body(new TopicoDto(topico));

    }
    @GetMapping("/{id}")
    public DetalhesTopicoDto detalhar(@PathVariable Long id) {
       Topico topico = topicoRepository.getReferenceById(id);

       return new DetalhesTopicoDto(topico);
    }
}
