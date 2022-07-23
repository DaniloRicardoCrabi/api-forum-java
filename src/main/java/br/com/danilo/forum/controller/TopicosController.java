package br.com.danilo.forum.controller;

import br.com.danilo.forum.controller.dto.TopicoDto;
import br.com.danilo.forum.modelo.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/topicos")
public class TopicosController {

    @GetMapping()
    public List<TopicoDto> list(){

        Topico topico = new Topico("titulo","mensagem",new Curso("Nome","Categoria"));

        List<Topico> topicos = Arrays.asList(topico,topico);

   return TopicoDto.converter(topicos);
    }
}
