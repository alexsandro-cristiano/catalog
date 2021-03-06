package com.devsuperior.dscatalog.services;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.dscatalog.dto.CategoryDTO;
import com.devsuperior.dscatalog.entities.Category;
import com.devsuperior.dscatalog.repositories.CategoryRepository;
import com.devsuperior.dscatalog.services.exceptions.DatabaseException;
import com.devsuperior.dscatalog.services.exceptions.ResourceNotFoundExceptions;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository repository;

	/*
	 * Buscar todos inutilizado
	 * 
	 * @Transactional(readOnly = true) public List<CategoryDTO> getAll() {
	 * List<Category> list = repository.findAll(); return list.stream().map(element
	 * -> new CategoryDTO(element)).collect(Collectors.toList()); }
	 */

	@Transactional(readOnly = true)
	public Page<CategoryDTO> getAllPage(PageRequest pageRequest) {
		Page<Category> list = repository.findAll(pageRequest);
		return list.map(element -> new CategoryDTO(element));
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
			category.setName(dto.getName());
			category = repository.save(category);
			return new CategoryDTO(category);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundExceptions("Id not found " + id);
		}
	}

	public void delete(Long id) {
		try {
			repository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundExceptions("Id not found " + id);
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Integrity violation");
		}
	}

}
