package guru.springfamework.services;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import guru.springfamework.api.v1.mapper.CustomerMapper;
import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.domain.Customer;
import guru.springfamework.repositories.CustomerRepository;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class CustomerServiceImplTest {

  @Mock
  CustomerRepository customerRepository;
  CustomerService customerService;

  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);
    customerService = new CustomerServiceImpl(customerRepository, CustomerMapper.INSTANCE);
  }

  @Test
  public void testFindAllCustomers() {
    List<Customer> customers = Arrays.asList(new Customer(), new Customer());

    when(customerRepository.findAll()).thenReturn(customers);

    List<CustomerDTO> customerDTOS = customerService.findAllCustomers();

    assertEquals(customerDTOS.size(), 2);

  }

  @Test
  public void testFindCustomerById() {
    Customer customer = new Customer();
    customer.setFirstName("First");
    when(customerRepository.findById(anyLong())).thenReturn(Optional.of(customer));

    CustomerDTO customerDTO = customerService.findCustomerById(anyLong());

    assertEquals(customerDTO.getFirstName(), "First");

  }

  @Test
  public void testCreateCustomer() {
    CustomerDTO customerDTO = new CustomerDTO();
    customerDTO.setFirstName("Test");
    customerDTO.setLastName("Test Last");

    Customer customer = new Customer();
    customer.setId(customerDTO.getId());
    customer.setFirstName(customerDTO.getFirstName());
    customer.setLastName(customerDTO.getLastName());

    when(customerRepository.save(any())).thenReturn(customer);

    CustomerDTO createdCustomerDTO = customerService.createCustomer(customerDTO);

    assertEquals(createdCustomerDTO.getFirstName(), "Test");
    assertEquals(createdCustomerDTO.getCustomerUrl(), "/api/v1/customers/" + createdCustomerDTO.getId());


  }

  @Test
  public void testUpdateCustomer() {

    CustomerDTO customerDTO = new CustomerDTO();
    customerDTO.setFirstName("Test");

    Customer savedCustomer = new Customer();
    savedCustomer.setFirstName(customerDTO.getFirstName());
    savedCustomer.setLastName(customerDTO.getLastName());
    savedCustomer.setId(1L);

    when(customerRepository.save(any())).thenReturn(savedCustomer);

    CustomerDTO updatedCustomer = customerService.updateCustomer(customerDTO, 1L);

    assertEquals(updatedCustomer.getId(), Long.valueOf(1));
    assertEquals(updatedCustomer.getFirstName(), savedCustomer.getFirstName());
    assertEquals(updatedCustomer.getCustomerUrl(), "/api/v1/customers/1");

  }

  @Test
  public void testDeleteCustomerById() {
    Long id = 1L;
    customerService.deleteCustomerById(id);
    verify(customerRepository,times(1)).deleteById(anyLong());
  }
}