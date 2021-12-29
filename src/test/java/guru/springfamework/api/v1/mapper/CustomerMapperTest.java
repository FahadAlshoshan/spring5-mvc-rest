package guru.springfamework.api.v1.mapper;

import static org.junit.Assert.assertEquals;

import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.domain.Customer;
import org.junit.Test;

public class CustomerMapperTest {

  CustomerMapper customerMapper = CustomerMapper.INSTANCE;

  @Test
  public void testCusToCusDTO() {
    Customer customer = new Customer();
    customer.setFirstName("First");
    customer.setLastName("Last");

    CustomerDTO customerDTO = customerMapper.customerToCustomerDTO(customer);

    assertEquals("First", customerDTO.getFirstName());
    assertEquals("Last", customerDTO.getLastName());
  }
  @Test
  public void testCusDTOPToCus() {
    CustomerDTO customerDTO = new CustomerDTO();
    customerDTO.setFirstName("First");
    customerDTO.setLastName("Last");

    Customer customer = customerMapper.customerDTOToCustomer(customerDTO);

    assertEquals("First", customer.getFirstName());
    assertEquals("Last", customer.getLastName());
  }
}