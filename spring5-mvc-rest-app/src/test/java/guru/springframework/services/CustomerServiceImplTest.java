package guru.springframework.services;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import guru.springframework.api.v1.mapper.CustomerMapper;
import guru.springframework.model.CustomerDTO;
import guru.springframework.domain.Customer;
import guru.springframework.repositories.CustomerRepository;
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
    //given
    CustomerDTO customerDTO = new CustomerDTO();
    customerDTO.setFirstName("Jim");

    Customer savedCustomer = new Customer();
    savedCustomer.setFirstName(customerDTO.getFirstName());
    savedCustomer.setLastName(customerDTO.getLastName());
    savedCustomer.setId(1L);

    when(customerRepository.save(any(Customer.class))).thenReturn(savedCustomer);

    //when
    CustomerDTO savedDto = customerService.createCustomer(customerDTO);

    //then
    assertEquals(customerDTO.getFirstName(), savedDto.getFirstName());
    assertEquals("/api/v1/customers/1", savedDto.getCustomerUrl());

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