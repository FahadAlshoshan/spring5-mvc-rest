package guru.springfamework.controllers.v1;

import static guru.springfamework.controllers.v1.AbstractRestControllerTest.asJsonString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.services.CustomerService;
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

public class CustomerControllerTest {

  @Mock
  CustomerService customerService;
  @InjectMocks
  CustomerController customerController;
  MockMvc mockMvc;

  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);
    mockMvc = MockMvcBuilders.standaloneSetup(customerController)
                             .setControllerAdvice(new RestResponseEntityExceptionHandler())
                             .build();
  }

  @Test
  public void testGetAllCustomers() throws Exception {
    List<CustomerDTO> customerDTOList = Arrays.asList(new CustomerDTO(), new CustomerDTO());
    when(customerService.findAllCustomers()).thenReturn(customerDTOList);

    mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/customers/").accept(MediaType.APPLICATION_JSON)
                                          .contentType(MediaType.APPLICATION_JSON))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.customers", hasSize(2)));
  }

  @Test
  public void testGetCustomerById() throws Exception {
    CustomerDTO customerDTO = new CustomerDTO();
    customerDTO.setId(1L);
    when(customerService.findCustomerById(anyLong())).thenReturn(customerDTO);

    mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/customers/1").accept(MediaType.APPLICATION_JSON)
                                          .contentType(MediaType.APPLICATION_JSON))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.id", equalTo(1)));
  }

  @Test
  public void testCreateNewCustomer() throws Exception {
    CustomerDTO customerDTO = new CustomerDTO();
    customerDTO.setFirstName("Test");
    customerDTO.setLastName("Test Last");

    CustomerDTO savedCustomerDTO = new CustomerDTO();
    savedCustomerDTO.setFirstName(customerDTO.getFirstName());
    savedCustomerDTO.setLastName(customerDTO.getLastName());
    savedCustomerDTO.setCustomerUrl("/api/v1/customers/1");

    when(customerService.createCustomer(customerDTO)).thenReturn(savedCustomerDTO);

    mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/customers/").accept(MediaType.APPLICATION_JSON)
                                          .contentType(MediaType.APPLICATION_JSON)
                                          .content(asJsonString(customerDTO)))
           .andExpect(status().isCreated())
           .andExpect(jsonPath("$.firstName", equalTo("Test")))
           .andExpect(jsonPath("$.lastName", equalTo("Test Last")))
           .andExpect(jsonPath("$.customerUrl", equalTo("/api/v1/customers/1")));
  }

  @Test
  public void testUpdateCustomer() throws Exception {
    CustomerDTO oldCustomerDTO = new CustomerDTO();
    oldCustomerDTO.setFirstName("Test");
    oldCustomerDTO.setLastName("Test Last");

    CustomerDTO newCustomerDTO = new CustomerDTO();
    newCustomerDTO.setFirstName(oldCustomerDTO.getFirstName());
    newCustomerDTO.setLastName(oldCustomerDTO.getLastName());
    newCustomerDTO.setCustomerUrl("/api/v1/customers/1");

    when(customerService.updateCustomer(any(), anyLong())).thenReturn(newCustomerDTO);

    mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/customers/1").accept(MediaType.APPLICATION_JSON)
                                          .contentType(MediaType.APPLICATION_JSON)
                                          .content(asJsonString(oldCustomerDTO)))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.firstName", equalTo("Test")))
           .andExpect(jsonPath("$.lastName", equalTo("Test Last")))
           .andExpect(jsonPath("$.customerUrl", equalTo("/api/v1/customers/1")));
  }

  @Test
  public void testPatchCustomer() throws Exception {
    CustomerDTO oldCustomerDTO = new CustomerDTO();
    oldCustomerDTO.setFirstName("Test");
    oldCustomerDTO.setLastName("Test Last");

    CustomerDTO newCustomerDTO = new CustomerDTO();
    newCustomerDTO.setFirstName(oldCustomerDTO.getFirstName());
    newCustomerDTO.setLastName("Patch Last");
    newCustomerDTO.setCustomerUrl("/api/v1/customers/1");

    when(customerService.patchCustomer(any(), anyLong())).thenReturn(newCustomerDTO);

    mockMvc.perform(MockMvcRequestBuilders.patch("/api/v1/customers/1").accept(MediaType.APPLICATION_JSON)
                                          .contentType(MediaType.APPLICATION_JSON)
                                          .content(asJsonString(oldCustomerDTO)))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.firstName", equalTo("Test")))
           .andExpect(jsonPath("$.lastName", equalTo("Patch Last")))
           .andExpect(jsonPath("$.customerUrl", equalTo("/api/v1/customers/1")));
  }

  @Test
  public void testDeleteCustomerById() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/customers/1").accept(MediaType.APPLICATION_JSON)
                                          .contentType(MediaType.APPLICATION_JSON))
           .andExpect(status().isOk());
    verify(customerService, times(1)).deleteCustomerById(anyLong());
  }


}