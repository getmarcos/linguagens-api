package br.com.alura.linguagens.api;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LinguagemController {

    @Autowired
    private LinguagemRepository repositorio;

    @GetMapping("/linguagens")
    @ResponseStatus(HttpStatus.OK)
    public List<Linguagem> obterLinguagens() {
        Sort.sort(Linguagem.class);
        Sort sort = Sort.by("votos").descending();
        List<Linguagem> linguagems = repositorio.findAll(sort);
        for (int i = 0; i < linguagems.size(); i++) {
            linguagems.get(i).setRanking(i + 1);
        }
        return linguagems;
    }

    @GetMapping("/linguagens/{id}")
    public Linguagem linguagemId(@PathVariable String id) {
        return repositorio.findById(id).get();
    }

    @PostMapping("/linguagens")
    @ResponseStatus(HttpStatus.CREATED)
    public Linguagem inserirLinguagen(@RequestBody Linguagem linguagem) {
        return repositorio.save(linguagem);
    }

    @DeleteMapping("linguagens/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletarLinguagem(@PathVariable String id) {
        repositorio.deleteById(id);
    }

    @PutMapping("linguagens/{id}")
    public Linguagem atualizarLinguagem(@PathVariable String id, @RequestBody Linguagem linguagem) {
        Linguagem linguagemAtual = repositorio.findById(id).get();
        BeanUtils.copyProperties(linguagem, linguagemAtual, "id");
        return repositorio.save(linguagemAtual);
    }

    @PatchMapping("/linguagens/{id}")
    public Linguagem votaLinguagem(@PathVariable("id") String id) {
        Linguagem linguagem = repositorio.findById(id).get();
        int voto = linguagem.getVotos();
        linguagem.setVotos(voto + 1);
        return repositorio.save(linguagem);
    }
}