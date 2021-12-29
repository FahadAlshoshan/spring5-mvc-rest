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

import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.services.VendorService;
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

public class VendorControllerTest {

  @Mock
  VendorService vendorService;
  @InjectMocks
  VendorController vendorController;
  MockMvc mockMvc;

  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);
    mockMvc = MockMvcBuilders.standaloneSetup(vendorController)
                             .setControllerAdvice(new RestResponseEntityExceptionHandler())
                             .build();
  }

  @Test
  public void testGetAllCustomers() throws Exception {
    List<VendorDTO> vendorDTOList = Arrays.asList(new VendorDTO(), new VendorDTO());
    when(vendorService.getAllVendors()).thenReturn(vendorDTOList);

    mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/vendors/")
                                          .accept(MediaType.APPLICATION_JSON)
                                          .contentType(MediaType.APPLICATION_JSON))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.vendors", hasSize(2)));
  }

  @Test
  public void testGetCustomerById() throws Exception {
    VendorDTO vendorDTO = new VendorDTO();
    vendorDTO.setId(1L);
    when(vendorService.getVendorById(anyLong())).thenReturn(vendorDTO);

    mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/vendors/1")
                                          .accept(MediaType.APPLICATION_JSON)
                                          .contentType(MediaType.APPLICATION_JSON))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.id", equalTo(1)));
  }

  @Test
  public void testCreateNewCustomer() throws Exception {
    VendorDTO vendorDTO = new VendorDTO();
    vendorDTO.setName("Test");

    VendorDTO savedVendorDTO = new VendorDTO();
    savedVendorDTO.setName(vendorDTO.getName());
    savedVendorDTO.setVendorUrl("/api/v1/vendors/1");

    when(vendorService.createNewVendor(vendorDTO)).thenReturn(savedVendorDTO);

    mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/vendors/")
                                          .accept(MediaType.APPLICATION_JSON)
                                          .contentType(MediaType.APPLICATION_JSON)
                                          .content(asJsonString(vendorDTO)))
           .andExpect(status().isCreated())
           .andExpect(jsonPath("$.name", equalTo("Test")))
           .andExpect(jsonPath("$.vendorUrl", equalTo("/api/v1/vendors/1")));
  }

  @Test
  public void testUpdateCustomer() throws Exception {
    VendorDTO oldVendorDTO = new VendorDTO();
    oldVendorDTO.setName("Test");

    VendorDTO newVendorDTO = new VendorDTO();
    newVendorDTO.setName(oldVendorDTO.getName());
    newVendorDTO.setVendorUrl("/api/v1/vendors/1");

    when(vendorService.updateVendor(any(), any())).thenReturn(newVendorDTO);

    mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/vendors/1")
                                          .accept(MediaType.APPLICATION_JSON)
                                          .contentType(MediaType.APPLICATION_JSON)
                                          .content(asJsonString(oldVendorDTO)))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.name", equalTo("Test")))
           .andExpect(jsonPath("$.vendorUrl", equalTo("/api/v1/vendors/1")));
  }

  @Test
  public void testPatchCustomer() throws Exception {
    VendorDTO oldVendorDTO = new VendorDTO();
    oldVendorDTO.setName("Test");

    VendorDTO newVendorDTO = new VendorDTO();
    newVendorDTO.setName(oldVendorDTO.getName());
    newVendorDTO.setVendorUrl("/api/v1/vendors/1");

    when(vendorService.patchVendor(any(), any())).thenReturn(newVendorDTO);

    mockMvc.perform(MockMvcRequestBuilders.patch("/api/v1/vendors/1")
                                          .accept(MediaType.APPLICATION_JSON)
                                          .contentType(MediaType.APPLICATION_JSON)
                                          .content(asJsonString(oldVendorDTO)))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.name", equalTo("Test")))
           .andExpect(jsonPath("$.vendorUrl", equalTo("/api/v1/vendors/1")));
  }

  @Test
  public void testDeleteCustomerById() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/vendors/1")
                                          .accept(MediaType.APPLICATION_JSON)
                                          .contentType(MediaType.APPLICATION_JSON))
           .andExpect(status().isOk());
    verify(vendorService, times(1)).deleteVendor(anyLong());
  }


}