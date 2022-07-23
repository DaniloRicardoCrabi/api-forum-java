package br.com.danilo.forum.controller;

import br.com.danilo.forum.controller.dto.TopicoDto;
import br.com.danilo.forum.modelo.*;
import br.com.danilo.forum.repository.TopicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.Arrays;
import java.util.List;

@RestController
public class TopicosController {

    @Autowired
    private TopicoRepository topicoRepository;

    @RequestMapping("/topicos")
    public List<TopicoDto> list(String nomeCurso){

        if(nomeCurso == null)
         return TopicoDto.converter(topicoRepository.findAll());
        else {
          return TopicoDto.converter(topicoRepository.findByCurso_Nome(nomeCurso));
        }
    }
}
