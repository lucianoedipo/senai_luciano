package org.springframework.samples.senai.produto;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/produtos")
public class ProdutoController {

	private final ProdutoRepository produtoRepository;

	public ProdutoController(ProdutoRepository produtoRepository) {
		this.produtoRepository = produtoRepository;
	}

	@GetMapping
	public String listProdutos(Model model) {
		model.addAttribute("produtos", produtoRepository.findAll());
		return "produtos/listagem";
	}

	@GetMapping("/{id}")
	public String showProduto(@PathVariable("id") Integer id, Model model) {
		Produto produto = produtoRepository.findById(id).orElse(null);
		if (produto != null) {
			model.addAttribute("produto", produto);
			return "produtos/detalhes";
		}
		else {
			return "error"; // Trate este caso adequadamente em seu código
		}
	}

	@GetMapping("/novo")
	public String novoProdutoForm(Model model) {
		model.addAttribute("produto", new Produto());
		return "produtos/formulario";
	}

	@PostMapping("/novo")
	public String criarProduto(@ModelAttribute Produto produto) {
		produtoRepository.save(produto);
		return "redirect:/produtos";
	}

	@GetMapping("/{id}/editar")
	public String editarProdutoForm(@PathVariable("id") Integer id, Model model) {
		Produto produto = produtoRepository.findById(id).orElse(null);
		if (produto != null) {
			model.addAttribute("produto", produto);
			return "produtos/formulario";
		}
		else {
			return "error"; // Trate este caso adequadamente em seu código
		}
	}

	@PostMapping("/{id}/editar")
	public String atualizarProduto(@PathVariable("id") Integer id, @ModelAttribute Produto produto) {
		Produto existingProduto = produtoRepository.findById(id).orElse(null);
		if (existingProduto != null) {
			existingProduto.setNome(produto.getNome());
			existingProduto.setPreco(produto.getPreco());
			produtoRepository.save(existingProduto);
			return "redirect:/produtos/" + id;
		}
		else {
			return "error"; // Trate este caso adequadamente em seu código
		}
	}

	@PostMapping("/{id}/excluir")
	public String excluirProduto(@PathVariable("id") Integer id) {
		produtoRepository.deleteById(id);
		return "redirect:/produtos";
	}

}
