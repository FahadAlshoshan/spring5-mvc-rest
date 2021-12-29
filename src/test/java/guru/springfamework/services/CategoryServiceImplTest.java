package guru.springfamework.services;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import guru.springfamework.api.v1.mapper.CategoryMapper;
import guru.springfamework.api.v1.model.CategoryDTO;
import guru.springfamework.domain.Category;
import guru.springfamework.repositories.CategoryRepository;
import java.util.Arrays;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class CategoryServiceImplTest {

  @Mock
  CategoryRepository categoryRepository;
  CategoryService categoryService;

  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);
    categoryService = new CategoryServiceImpl(categoryRepository, CategoryMapper.INSTANCE);
  }

  @Test
  public void testGetAllCategories() {
    List<Category> list = Arrays.asList(new Category(), new Category(), new Category());

    when(categoryRepository.findAll()).thenReturn(list);

    List<CategoryDTO> returnList = categoryService.getAllCategories();

    assertEquals(3, returnList.size());
  }

  @Test
  public void testGetCategoryByName() {
    Category category = new Category();
    category.setName("Test");
    when(categoryRepository.findCategoryByName(anyString())).thenReturn(category);

    CategoryDTO returnCategory = categoryService.getCategoryByName(anyString());

    assertEquals("Test", returnCategory.getName());
  }
}