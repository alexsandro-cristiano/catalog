package com.devsuperior.dscatalog.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.dscatalog.dto.CategoryDTO;
import com.devsuperior.dscatalog.entities.Category;
import com.devsuperior.dscatalog.repositories.CategoryRepository;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository repository;

	@Transactional(readOnly = true)
	public List<CategoryDTO> getAll() {
		List<Category> list = repository.findAll();
		return list.stream().map(element -> new CategoryDTO(element)).collect(Collectors.toList());
		
	}

	@Transactional(readOnly = true)
	public CategoryDTO getById(Long id) {
		Optional<Category> optinalCategory = repository.findById(id);
		Category category = optinalCategory.get();
		
		return new CategoryDTO(category);
	}

}
