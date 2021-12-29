package guru.springframework.controllers.v1;

import guru.springframework.model.CustomerDTO;
import guru.springframework.model.CustomerListDTO;
import guru.springframework.services.CustomerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@Api(description = "This is my Customer Controller")
@RequestMapping(CustomerController.BASE_URL)
@RestController
public class CustomerController {

  public static final String BASE_URL = "/api/v1/customers";
  private final CustomerService customerService;

  public CustomerController(CustomerService customerService) {
    this.customerService = customerService;
  }

  @ApiOperation(value = "This will get a list of customers.", notes = "These are some notes about the API.")
  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public CustomerListDTO getAllCustomers() {
    CustomerListDTO customerListDTO = new CustomerListDTO();
    customerListDTO.getCustomers()
                   .addAll(customerService.findAllCustomers());
    return customerListDTO;
  }

  @GetMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public CustomerDTO getCustomerById(@PathVariable Long id) {
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
