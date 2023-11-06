package org.springframework.samples.senai.cliente;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import jakarta.validation.Valid;

@Controller
class ClienteController {

	private static final String VIEWS_CLIENTE_CREATE_OR_UPDATE_FORM = "clientes/createOrUpdateClienteForm";

	private final ClienteRepository clientes;

	public ClienteController(ClienteRepository clienteService) {
		this.clientes = clienteService;
	}

	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	@ModelAttribute("cliente")
	public Cliente findCliente(@PathVariable(name = "clienteId", required = false) Integer clienteId) {
		return clienteId == null ? new Cliente() : this.clientes.findById(clienteId);
	}

	@GetMapping("/clientes/new")
	public String initCreationForm(Model model) {
		Cliente cliente = new Cliente();
		model.addAttribute("cliente", cliente);
		return VIEWS_CLIENTE_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping("/clientes/new")
	public String processCreationForm(@Valid Cliente cliente, BindingResult result) {
		if (result.hasErrors()) {
			return VIEWS_CLIENTE_CREATE_OR_UPDATE_FORM;
		}

		this.clientes.save(cliente);
		return "redirect:/clientes/" + cliente.getId();
	}

	@GetMapping("/clientes/find")
	public String initFindForm() {
		return "clientes/findClientes";
	}

	@GetMapping("/clientes")
	public String processFindForm(@RequestParam(defaultValue = "1") int page, Cliente cliente, BindingResult result,
			Model model) {
		// permitir solicitação GET sem parâmetros para /clientes para retornar todos os
		// registros
		if (cliente.getLastName() == null) {
			cliente.setLastName(""); // string vazia indica pesquisa mais ampla possível
		}

		// encontrar clientes por sobrenome
		Page<Cliente> clientesResults = findPaginatedForClientesLastName(page, cliente.getLastName());
		if (clientesResults.isEmpty()) {
			// nenhum cliente encontrado
			result.rejectValue("lastName", "notFound", "not found");
			return "clientes/findClientes";
		}

		if (clientesResults.getTotalElements() == 1) {
			// 1 cliente encontrado
			cliente = clientesResults.iterator().next();
			return "redirect:/clientes/" + cliente.getId();
		}

		// vários clientes encontrados
		return addPaginationModel(page, model, clientesResults);
	}

	private String addPaginationModel(int page, Model model, Page<Cliente> paginated) {
		List<Cliente> listClientes = paginated.getContent();
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", paginated.getTotalPages());
		model.addAttribute("totalItems", paginated.getTotalElements());
		model.addAttribute("listClientes", listClientes);
		return "clientes/clientesList";
	}

	private Page<Cliente> findPaginatedForClientesLastName(int page, String sobrenome) {
		int pageSize = 5;
		Pageable pageable = PageRequest.of(page - 1, pageSize);
		return clientes.findByLastName(sobrenome, pageable);
	}

	@GetMapping("/clientes/{clienteId}/edit")
	public String initUpdateClienteForm(@PathVariable("clienteId") int clienteId, Model model) {
		Cliente cliente = this.clientes.findById(clienteId);
		model.addAttribute(cliente);
		return VIEWS_CLIENTE_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping("/clientes/{clienteId}/edit")
	public String processUpdateClienteForm(@Valid Cliente cliente, BindingResult result,
			@PathVariable("clienteId") int clienteId) {
		if (result.hasErrors()) {
			return VIEWS_CLIENTE_CREATE_OR_UPDATE_FORM;
		}

		cliente.setId(clienteId);
		this.clientes.save(cliente);
		return "redirect:/clientes/{clienteId}";
	}

	/**
	 * Handler personalizado para exibir um cliente.
	 * @param clienteId o ID do cliente para exibir
	 * @return um ModelAndView com os atributos do modelo para a view
	 */
	@GetMapping("/clientes/{clienteId}")
	public ModelAndView showCliente(@PathVariable("clienteId") int clienteId) {
		ModelAndView mav = new ModelAndView("clientes/clienteDetails");
		Cliente cliente = this.clientes.findById(clienteId);
		mav.addObject(cliente);
		return mav;
	}

}
