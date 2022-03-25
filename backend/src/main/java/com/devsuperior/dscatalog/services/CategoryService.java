package com.devsuperior.dscatalog.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.dscatalog.dto.CategoryDTO;
import com.devsuperior.dscatalog.entities.Category;
import com.devsuperior.dscatalog.repositories.CategoryRepository;
import com.devsuperior.dscatalog.services.exceptions.ResourceNotFoundExceptions;

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
		Category category = optinalCategory.orElseThrow(() -> new ResourceNotFoundExceptions("Entity not found"));
		return new CategoryDTO(category);
	}

	@Transactional
	public CategoryDTO insert(CategoryDTO dto) {
		Category category = new Category();
		category.setName(dto.getName());
		category = repository.save(category);
		return new CategoryDTO(category);
	}
	
	@Transactional
	public CategoryDTO update(Long id, CategoryDTO dto) {
		try {
			Category category = repository.getOne(id);
			System.out.println(category.getId());
			category = repository.save(category);
			return new CategoryDTO(category);
		}
		catch (EntityNotFoundException e) {
			throw new ResourceNotFoundExceptions("Id not found " + id);
		}
	}

}
