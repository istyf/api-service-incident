package se.sundsvall.incident.service;

import static java.util.Optional.ofNullable;
import static org.zalando.problem.Status.NOT_FOUND;
import static se.sundsvall.incident.service.mapper.Mapper.toCategoryDto;
import static se.sundsvall.incident.service.mapper.Mapper.toCategoryEntity;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.zalando.problem.Problem;

import se.sundsvall.incident.api.model.Category;
import se.sundsvall.incident.api.model.CategoryPatch;
import se.sundsvall.incident.api.model.CategoryPost;
import se.sundsvall.incident.api.model.ValidCategoryResponse;
import se.sundsvall.incident.api.model.ValidOepCategoryResponse;
import se.sundsvall.incident.integration.db.entity.CategoryEntity;
import se.sundsvall.incident.integration.db.repository.CategoryRepository;
import se.sundsvall.incident.service.mapper.Mapper;

@Service
public class CategoryService {
	private static final String ENTITY_NOT_FOUND = "%s with id: %s not found";
	private static final String CATEGORY = "Category";
	private final CategoryRepository categoryRepository;

	public CategoryService(final CategoryRepository categoryRepository) {
		this.categoryRepository = categoryRepository;
	}

	public Category fetchCategoryById(final Integer id) {
		var category = categoryRepository.findById(id)
			.orElseThrow(() -> Problem.valueOf(NOT_FOUND, ENTITY_NOT_FOUND.formatted(CATEGORY, id)));
		return toCategoryDto(category);
	}

	public List<Category> fetchAllCategories() {
		return categoryRepository.findAll().stream()
			.map(Mapper::toCategoryDto)
			.toList();
	}

	public Category createCategory(final CategoryPost categoryPost) {
		var category = categoryRepository.save(toCategoryEntity(categoryPost));
		return toCategoryDto(category);
	}

	public void deleteCategoryById(final Integer id) {
		if (!categoryRepository.existsById(id)) {
			throw Problem.valueOf(NOT_FOUND, ENTITY_NOT_FOUND.formatted(CATEGORY, id));
		}
		categoryRepository.deleteById(id);
	}

	public Category patchCategory(final Integer id, final CategoryPatch patch) {
		var categoryEntity = categoryRepository.findById(id)
			.orElseThrow(() -> Problem.valueOf(NOT_FOUND, ENTITY_NOT_FOUND.formatted(CATEGORY, id)));
		var category = categoryRepository.save(patchCategory(categoryEntity, patch));
		return toCategoryDto(category);
	}

	public List<ValidCategoryResponse> fetchValidCategories() {
		List<ValidCategoryResponse> validCategories = new ArrayList<>();
		var categories = categoryRepository.findAll();

		for (var category : categories) {
			validCategories.add(ValidCategoryResponse.builder()
				.withCategory(category.getLabel())
				.withCategoryId(category.getCategoryId())
				.build());
		}
		return validCategories;
	}

	public List<ValidOepCategoryResponse> fetchValidOepCategories() {
		List<ValidOepCategoryResponse> validOepCategories = new ArrayList<>();
		var categories = categoryRepository.findAll();

		for (var category : categories) {
			validOepCategories.add(ValidOepCategoryResponse.builder()
				.withKey(String.valueOf(category.getCategoryId()))
				.withValue(category.getLabel())
				.build());
		}
		return validOepCategories;
	}

	CategoryEntity patchCategory(final CategoryEntity entity, final CategoryPatch patch) {
		ofNullable(patch.title()).ifPresent(entity::setTitle);
		ofNullable(patch.label()).ifPresent(entity::setLabel);
		ofNullable(patch.forwardTo()).ifPresent(entity::setForwardTo);
		return entity;
	}
}
