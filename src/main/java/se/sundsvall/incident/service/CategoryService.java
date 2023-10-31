package se.sundsvall.incident.service;

import static org.zalando.problem.Status.NOT_FOUND;
import static se.sundsvall.incident.service.utils.PatchUtil.setPropertyIfNonNull;

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

	private final CategoryRepository categoryRepository;
	private final Mapper mapper;

	public CategoryService(final CategoryRepository categoryRepository, final Mapper mapper) {
		this.categoryRepository = categoryRepository;
		this.mapper = mapper;
	}

	public CategoryDTO fetchCategoryById(final Integer id) {
		var categoryEntity = categoryRepository.findById(id)
			.orElseThrow(() -> Problem.valueOf(NOT_FOUND, "Category with id: " + id + " not found"));
		return mapper.toCategoryDto(categoryEntity);
	}

	public List<CategoryDTO> fetchAllCategories() {
		return categoryRepository.findAll().stream()
			.map(mapper::toCategoryDto)
			.toList();
	}

	public CategoryDTO createCategory(final CategoryPost categoryPost) {
		var entity = categoryRepository.save(mapper.toCategoryEntity(categoryPost));
		return mapper.toCategoryDto(entity);
	}

	public void deleteCategoryById(final Integer id) {
		if (!categoryRepository.existsById(id)) {
			throw Problem.valueOf(NOT_FOUND, "Category with id: " + id + " not found");
		}
		categoryRepository.deleteById(id);
	}

	public CategoryDTO patchCategory(final Integer id, final CategoryPatch patch) {
		var categoryEntity = categoryRepository.findById(id)
			.orElseThrow(() -> Problem.valueOf(NOT_FOUND, "Category with id: " + id + " not found"));
		var patchedEntity = categoryRepository.save(patchEntity(categoryEntity, patch));
		return mapper.toCategoryDto(patchedEntity);
	}

	CategoryEntity patchEntity(final CategoryEntity entity, final CategoryPatch patch) {
		setPropertyIfNonNull(entity::setTitle, patch.title());
		setPropertyIfNonNull(entity::setLabel, patch.label());
		setPropertyIfNonNull(entity::setForwardTo, patch.forwardTo());
		return entity;
	}

}
