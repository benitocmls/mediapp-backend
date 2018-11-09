package com.mitocode.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.mitocode.exception.ModeloNotFoundException;
import com.mitocode.model.Signos;
import com.mitocode.service.ISignosService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/signos")
@Api(value = "Servicio REST para los Signos")
public class SignosController {
	@Autowired
	private ISignosService service;
	
	@ApiOperation("Rertorna una lista de signos")
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Signos>> listar(){
		List<Signos> signo = new ArrayList<>();
		signo = service.listar();
		return new ResponseEntity<List<Signos>>(signo, HttpStatus.OK);
	}
	
	@GetMapping(value="/pageable", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Page<Signos>> listarPageable(Pageable pageable){
		Page<Signos> signos;
		signos = service.listarPageable(pageable);
		return new ResponseEntity<Page<Signos>>(signos, HttpStatus.OK);
	}
	
	@GetMapping(value = "/{id}")
	public Resource<Signos> listarId(@PathVariable("id") Integer id) {
		Signos sig = service.listarId(id);
		if (sig == null) {
			throw new ModeloNotFoundException("ID: " + id);
		}
		
		Resource<Signos> resource = new Resource<Signos>(sig);
		ControllerLinkBuilder linkTo = linkTo(methodOn(this.getClass()).listarId(id));
		resource.add(linkTo.withRel("Signos-resource"));
		
		return resource;
	}
	
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> registrar(@Valid @RequestBody Signos signos){
		Signos sig = new Signos();
		
		sig = service.registrar(signos);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(sig.getIdSignos()).toUri();
		System.out.println("RitmoRespiratorio "+sig.getIdSignos());
		return ResponseEntity.created(location).build();
		
	}
	
	@PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> actualizar(@Valid @RequestBody Signos signos) {	
		System.out.println("RitmoRespiratorio actualizar "+signos.getFecha());
		service.modificar(signos);
		
		return new ResponseEntity<Object>(HttpStatus.OK);
	}
	
	@DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public void eliminar(@PathVariable Integer id) {
		Signos sig = service.listarId(id);
		if (sig == null) {
			throw new ModeloNotFoundException("ID: " + id);
		} else {
			service.eliminar(id);
		}
	}
}
