package guru.springframework.services;

import guru.springframework.api.v1.mapper.CustomerMapper;
import guru.springframework.controllers.v1.CustomerController;
import guru.springframework.domain.Customer;
import guru.springframework.model.CustomerDTO;
import guru.springframework.repositories.CustomerRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService {

  private final CustomerRepository customerRepository;
  private final CustomerMapper customerMapper;

  public CustomerServiceImpl(CustomerRepository customerRepository, CustomerMapper customerMapper) {
    this.customerRepository = customerRepository;
    this.customerMapper = customerMapper;
  }

  @Override
  public List<CustomerDTO> findAllCustomers() {
    return customerRepository.findAll()
                             .stream()
                             .map(customer -> {
                               CustomerDTO customerDTO = customerMapper.customerToCustomerDTO(customer);
                               customerDTO.setCustomerUrl(getCustomerUrl(customer.getId()));
                               return customerDTO;
                             })
                             .collect(Collectors.toList());
  }

  @Override
  public CustomerDTO findCustomerById(Long id) {
    return customerRepository.findById(id)
                             .map(customerMapper::customerToCustomerDTO)
                             .map(customerDTO -> {
                               //set API URL
                               customerDTO.setCustomerUrl(getCustomerUrl(id));
                               return customerDTO;
                             })
                             .orElseThrow(ResourceNotFoundException::new);
  }

  @Override
  public CustomerDTO createCustomer(CustomerDTO customerDTO) {
//    Customer customer = customerMapper.customerDTOToCustomer(customerDTO);
//    Customer savedCustomer = customerRepository.save(customer);
//    CustomerDTO returnedCustomerDTO = customerMapper.customerToCustomerDTO(savedCustomer);
//    returnedCustomerDTO.setCustomerUrl("/api/v1/customers/"+customer.getId());
    return saveHelper(customerMapper.customerDTOToCustomer(customerDTO));
  }


  @Override
  public CustomerDTO updateCustomer(CustomerDTO customerDTO, Long id) {
    Customer customer = customerMapper.customerDTOToCustomer(customerDTO);
    customer.setId(id);
    return saveHelper(customer);
  }

  @Override
  public CustomerDTO patchCustomer(CustomerDTO customerDTO, Long id) {
    return customerRepository.findById(id)
                             .map(customer -> {

                               if (customerDTO.getFirstName() != null) {
                                 customer.setFirstName(customerDTO.getFirstName());
                               }

                               if (customerDTO.getLastName() != null) {
                                 customer.setLastName(customerDTO.getLastName());
                               }

                               CustomerDTO returnDto = customerMapper.customerToCustomerDTO(customerRepository.save(customer));

                               returnDto.setCustomerUrl(getCustomerUrl(id));

                               return returnDto;

                             })
                             .orElseThrow(ResourceNotFoundException::new);
  }

  @Override
  public void deleteCustomerById(Long id) {
    customerRepository.deleteById(id);
  }

  private String getCustomerUrl(Long id) {
    return CustomerController.BASE_URL + "/" + id;
  }

  private CustomerDTO saveHelper(Customer customer) {
    Customer savedCustomer = customerRepository.save(customer);

    CustomerDTO returnDto = customerMapper.customerToCustomerDTO(savedCustomer);

    returnDto.setCustomerUrl(getCustomerUrl(savedCustomer.getId()));

    return returnDto;

  }
}
