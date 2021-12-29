package guru.springfamework.controllers.v1;

import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.api.v1.model.CustomerListDTO;
import guru.springfamework.services.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(CustomerController.BASE_URL)
@RestController
public class CustomerController {

  public static final String BASE_URL = "/api/v1/customers";
  private final CustomerService customerService;

  public CustomerController(CustomerService customerService) {
    this.customerService = customerService;
  }

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public CustomerListDTO getAllCustomers() {
    return new CustomerListDTO(customerService.findAllCustomers());
  }

  @GetMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public CustomerDTO getCustomerById(
      @PathVariable
          Long id) {
    return customerService.findCustomerById(id);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public CustomerDTO createNewCustomer(@RequestBody CustomerDTO customerDTO) {
    return customerService.createCustomer(customerDTO);
  }

  @PutMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public CustomerDTO updateCustomer(@RequestBody CustomerDTO customerDTO, @PathVariable Long id) {
    return customerService.updateCustomer(customerDTO, id);
  }

  @PatchMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public CustomerDTO patchCustomer(@RequestBody CustomerDTO customerDTO, @PathVariable Long id) {
    return customerService.patchCustomer(customerDTO, id);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public void deleteCustomerById(@PathVariable Long id) {
    customerService.deleteCustomerById(id);
  }

}
