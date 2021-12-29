package guru.springframework.controllers.v1;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import guru.springframework.api.v1.model.CategoryDTO;
import guru.springframework.controllers.RestResponseEntityExceptionHandler;
import guru.springframework.services.CategoryService;
import guru.springframework.services.ResourceNotFoundException;
import java.util.Arrays;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

public class CategoryControllerTest {

  @Mock
  CategoryService categoryService;
  @InjectMocks
  CategoryController categoryController;

  MockMvc mockMvc;

  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);

    mockMvc = MockMvcBuilders.standaloneSetup(categoryController)
        .setControllerAdvice(new RestResponseEntityExceptionHandler())
                             .build();
  }

  @Test
  public void testListCategories() throws Exception {
    CategoryDTO categoryDTO1 = new CategoryDTO();
    categoryDTO1.setId(1L);
    categoryDTO1.setName("Test1");

    CategoryDTO categoryDTO2 = new CategoryDTO();
    categoryDTO2.setId(2L);
    categoryDTO2.setName("Test2");

    List<CategoryDTO> list = Arrays.asList(categoryDTO1, categoryDTO2);

    when(categoryService.getAllCategories()).thenReturn(list);

    mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/categories/").accept(MediaType.APPLICATION_JSON)
                                          .contentType(MediaType.APPLICATION_JSON))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.categories", hasSize(2)));
  }

  @Test
  public void testGetCategoryByName() throws Exception {
    CategoryDTO categoryDTO = new CategoryDTO();
    categoryDTO.setId(1L);
    categoryDTO.setName("Test1");



    when(categoryService.getCategoryByName(anyString())).thenReturn(categoryDTO);

    mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/categories/Test1").accept(MediaType.APPLICATION_JSON)
                                          .contentType(MediaType.APPLICATION_JSON))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.name", equalTo("Test1")));
  }
  @Test
  public void testGetCategoryByNameNotFound() throws Exception {
    CategoryDTO categoryDTO = new CategoryDTO();
    categoryDTO.setId(1L);
    categoryDTO.setName("Test1");

    when(categoryService.getCategoryByName(anyString())).thenThrow(ResourceNotFoundException.class);

    mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/categories/Test1").accept(MediaType.APPLICATION_JSON)
               .contentType(MediaType.APPLICATION_JSON))
           .andExpect(status().isNotFound());
  }
}