package guru.springfamework.api.v1.mapper;

import static org.junit.Assert.assertEquals;

import guru.springfamework.api.v1.model.CategoryDTO;
import guru.springfamework.domain.Category;
import org.junit.Test;

public class CategoryMapperTest {

  CategoryMapper categoryMapper = CategoryMapper.INSTANCE;

  @Test
  public void testCatToCatDTO() {
    Category category = new Category();
    category.setName("Fruits");

    CategoryDTO mappedCategory = categoryMapper.categoryToCategoryDTO(category);

    assertEquals(mappedCategory.getName(), "Fruits");
  }
}