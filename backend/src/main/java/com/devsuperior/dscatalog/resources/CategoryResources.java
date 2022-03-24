package com.devsuperior.dscatalog.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devsuperior.dscatalog.dto.CategoryDTO;
import com.devsuperior.dscatalog.services.CategoryService;

@RestController
@RequestMapping(value = "/categories")
public class CategoryResources {

	@Autowired
	private CategoryService service;

	@GetMapping
	public ResponseEntity<List<CategoryDTO>> get() {
		List<CategoryDTO> categoryList = service.getAll();
		return ResponseEntity.ok().body(categoryList);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<CategoryDTO> getById( @PathVariable Long id) {
		CategoryDTO category = service.getById(id);
		return ResponseEntity.ok().body(category);
	}

}
