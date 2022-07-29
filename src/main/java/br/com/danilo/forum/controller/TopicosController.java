package br.com.danilo.forum.controller;

import br.com.danilo.forum.controller.dto.DetalhesTopicoDto;
import br.com.danilo.forum.controller.dto.TopicoCreateDto;
import br.com.danilo.forum.controller.dto.TopicoDto;
import br.com.danilo.forum.controller.dto.TopicoUpdateDto;
import br.com.danilo.forum.modelo.*;
import br.com.danilo.forum.repository.CursoRepository;
import br.com.danilo.forum.repository.TopicoRepository;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;


import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/topicos")
public class TopicosController {

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private CursoRepository cursoRepository;


    @GetMapping()
    public Page<TopicoDto> list(@RequestParam(required = false) String nomeCurso,
                               Pageable paginacao)
    {

        if(nomeCurso == null) {
            Page<Topico> t = this.topicoRepository.findAll(paginacao);
            return TopicoDto.converter(t);
        }
        else {
          return TopicoDto.converter(topicoRepository.findByCurso_Nome(nomeCurso, paginacao));
        }
    }
    @PostMapping()
    @Transactional
    public ResponseEntity<TopicoDto> cadastrar(@RequestBody @Valid TopicoCreateDto novoTopico, UriComponentsBuilder uriBuilder){

        Topico topico =  novoTopico.converter(cursoRepository);
        topicoRepository.save(topico);

        URI uri =  uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();

        return ResponseEntity.created(uri).body(new TopicoDto(topico));

    }
    @GetMapping("/{id}")
    public ResponseEntity<DetalhesTopicoDto> detalhar(@PathVariable Long id) {
       Optional<Topico> topico = topicoRepository.findById(id);

       if(topico.isPresent()) {
           return  ResponseEntity.ok((new DetalhesTopicoDto(topico.get())));
       }

       return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<TopicoDto> atualizar(@PathVariable Long id, @RequestBody @Valid TopicoUpdateDto topicoUpdateDto ) {

        Optional<Topico> topico = topicoRepository.findById(id);

        if(topico.isPresent()){

            topico.get().setMensagem(topicoUpdateDto.getMensagem());
            topico.get().setTitulo(topicoUpdateDto.getTitulo());

            return ResponseEntity.ok(new TopicoDto(topico.get()));
        }

        return ResponseEntity.notFound().build();

    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> remover(@PathVariable Long id) {
        Optional<Topico> optional = topicoRepository.findById(id);

        if(optional.isPresent()){
            topicoRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.notFound().build();
    }
}
