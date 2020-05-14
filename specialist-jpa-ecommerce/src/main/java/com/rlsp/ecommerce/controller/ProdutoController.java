package com.rlsp.ecommerce.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.rlsp.ecommerce.model.Produto;
import com.rlsp.ecommerce.repository.ProdutosRepository;
import com.rlsp.ecommerce.service.ProdutoService;

@Controller
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private ProdutosRepository produtos;

    @Autowired
    private ProdutoService service;

    @PostMapping("/{id}/editar")
    public ModelAndView atualizar(@RequestAttribute String tenant,
                                  @PathVariable Integer id,
                                  @RequestParam Map<String, Object> produto,
                                  RedirectAttributes redirectAttributes) {
        service.atualizar(id, tenant, produto);

        redirectAttributes.addFlashAttribute("mensagem", "Atualização feita com sucesso!");

        return new ModelAndView("redirect:/produtos/{id}/editar");
    }

    @GetMapping("/{id}/editar")
    public ModelAndView editar(
            @RequestAttribute String tenant, @PathVariable Integer id) {
        return novo(produtos.buscar(id, tenant));
    }

    @PostMapping("/novo")
    public ModelAndView criar(@RequestAttribute String tenant,
                              Produto produto,
                              BindingResult bindingResult,
                              RedirectAttributes redirectAttributes) {
        Produto atualizado = service.criar(tenant, produto);

        redirectAttributes.addFlashAttribute("mensagem", "Registro criado com sucesso!");

        return new ModelAndView(
                "redirect:/produtos/{id}/editar", "id", atualizado.getId());
    }

    @GetMapping("/novo")
    public ModelAndView novo(Produto produto) {
        ModelAndView mv = new ModelAndView("produtos/produtos-formulario");
        mv.addObject("produto", produto);
        return mv;
    }

    @GetMapping
    public ModelAndView listar(@RequestAttribute String tenant) {
        ModelAndView mv = new ModelAndView("produtos/produtos-lista");
        mv.addObject("produtos", produtos.listar(tenant));
        return mv;
    }
}