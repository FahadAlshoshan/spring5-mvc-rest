package guru.springframework.services;


import guru.springframework.model.CustomerDTO;
import java.util.List;

public interface CustomerService {

  List<CustomerDTO> findAllCustomers();

  CustomerDTO findCustomerById(Long id);

  CustomerDTO createCustomer(CustomerDTO customerDTO);

  CustomerDTO updateCustomer(CustomerDTO customerDTO, Long id);

  CustomerDTO patchCustomer(CustomerDTO customerDTO, Long id);

  void deleteCustomerById(Long id);


}
