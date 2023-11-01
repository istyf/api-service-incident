package se.sundsvall.incident.service;

import static java.util.Optional.ofNullable;
import static org.zalando.problem.Status.NOT_FOUND;
import static se.sundsvall.incident.service.mapper.Mapper.toCategoryDto;
import static se.sundsvall.incident.service.mapper.Mapper.toCategoryEntity;

import java.util.List;

import org.springframework.stereotype.Service;
import org.zalando.problem.Problem;

import se.sundsvall.incident.api.model.CategoryDTO;
import se.sundsvall.incident.api.model.CategoryPatch;
import se.sundsvall.incident.api.model.CategoryPost;
import se.sundsvall.incident.integration.db.CategoryRepository;
import se.sundsvall.incident.integration.db.entity.CategoryEntity;
import se.sundsvall.incident.service.mapper.Mapper;

@Service
public class CategoryService {

	private static final String CATEGORY_NOT_FOUND = "Category with id: %s not found";
	private final CategoryRepository categoryRepository;

	public CategoryService(final CategoryRepository categoryRepository) {
		this.categoryRepository = categoryRepository;
	}

	public CategoryDTO fetchCategoryById(final Integer id) {
		var categoryEntity = categoryRepository.findById(id)
			.orElseThrow(() -> Problem.valueOf(NOT_FOUND, CATEGORY_NOT_FOUND.formatted(id)));
		return toCategoryDto(categoryEntity);
	}

	public List<CategoryDTO> fetchAllCategories() {
		return categoryRepository.findAll().stream()
			.map(Mapper::toCategoryDto)
			.toList();
	}

	public CategoryDTO createCategory(final CategoryPost categoryPost) {
		var entity = categoryRepository.save(toCategoryEntity(categoryPost));
		return toCategoryDto(entity);
	}

	public void deleteCategoryById(final Integer id) {
		if (!categoryRepository.existsById(id)) {
			throw Problem.valueOf(NOT_FOUND, CATEGORY_NOT_FOUND.formatted(id));
		}
		categoryRepository.deleteById(id);
	}

	public CategoryDTO patchCategory(final Integer id, final CategoryPatch patch) {
		var categoryEntity = categoryRepository.findById(id)
			.orElseThrow(() -> Problem.valueOf(NOT_FOUND, CATEGORY_NOT_FOUND.formatted(id)));
		var patchedEntity = categoryRepository.save(patchEntity(categoryEntity, patch));
		return toCategoryDto(patchedEntity);
	}

	CategoryEntity patchEntity(final CategoryEntity entity, final CategoryPatch patch) {
		ofNullable(patch.title()).ifPresent(entity::setTitle);
		ofNullable(patch.label()).ifPresent(entity::setLabel);
		ofNullable(patch.forwardTo()).ifPresent(entity::setForwardTo);
		return entity;
	}

}
