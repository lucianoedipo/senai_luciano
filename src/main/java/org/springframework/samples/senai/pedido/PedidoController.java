package org.springframework.samples.senai.pedido;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/pedidos")
public class PedidoController {

	private final PedidoRepository pedidoRepository;

	@Autowired
	public PedidoController(PedidoRepository pedidoRepository) {
		this.pedidoRepository = pedidoRepository;
	}

	@GetMapping
	public ModelAndView listPedidos() {
		Iterable<Pedido> pedidos = pedidoRepository.findAll();
		return new ModelAndView("pedidos/list", "pedidos", pedidos);
	}

	@GetMapping("/new")
	public String initCreationForm(Model model) {
		Pedido pedido = new Pedido();
		model.addAttribute("pedido", pedido);
		return "pedidos/createOrUpdateForm";
	}

	@PostMapping("/new")
	public String processCreationForm(@Valid Pedido pedido, BindingResult result) {
		if (result.hasErrors()) {
			return "pedidos/createOrUpdateForm";
		}
		pedido.setDate(LocalDate.now());
		pedidoRepository.save(pedido);
		return "redirect:/pedidos";
	}

	@GetMapping("/{pedidoId}/edit")
	public String initUpdateForm(@PathVariable("pedidoId") Integer pedidoId, Model model) {
		Pedido pedido = pedidoRepository.findById(pedidoId)
			.orElseThrow(() -> new IllegalArgumentException("Pedido inválido: " + pedidoId));
		model.addAttribute("pedido", pedido);
		return "pedidos/createOrUpdateForm";
	}

	@PostMapping("/{pedidoId}/edit")
	public String processUpdateForm(@Valid Pedido pedido, BindingResult result,
			@PathVariable("pedidoId") Integer pedidoId) {
		if (result.hasErrors()) {
			return "pedidos/createOrUpdateForm";
		}
		pedido.setId(pedidoId);
		pedido.setDate(LocalDate.now());
		pedidoRepository.save(pedido);
		return "redirect:/pedidos";
	}

	@GetMapping("/{pedidoId}")
	public ModelAndView showPedido(@PathVariable("pedidoId") Integer pedidoId) {
		Pedido pedido = pedidoRepository.findById(pedidoId)
			.orElseThrow(() -> new IllegalArgumentException("Pedido inválido: " + pedidoId));
		return new ModelAndView("pedidos/show", "pedido", pedido);
	}

	@GetMapping("/{pedidoId}/delete")
	public String deletePedido(@PathVariable("pedidoId") Integer pedidoId) {
		pedidoRepository.deleteById(pedidoId);
		return "redirect:/pedidos";
	}

}
