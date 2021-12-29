package guru.springfamework.bootstrap;

import guru.springfamework.domain.Category;
import guru.springfamework.domain.Customer;
import guru.springfamework.domain.Vendor;
import guru.springfamework.repositories.CategoryRepository;
import guru.springfamework.repositories.CustomerRepository;
import guru.springfamework.repositories.VendorRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Bootstrap implements CommandLineRunner {

  private final CategoryRepository categoryRepository;
  private final CustomerRepository customerRepository;
  private final VendorRepository vendorRepository;

  public Bootstrap(CategoryRepository categoryRepository, CustomerRepository customerRepository, VendorRepository vendorRepository) {
    this.categoryRepository = categoryRepository;
    this.customerRepository = customerRepository;
    this.vendorRepository = vendorRepository;
  }

  @Override
  public void run(String... args) throws Exception {
    loadCategories();
    loadCustomers();
    loadVendors();

    log.debug("Data Loaded= " + categoryRepository.count());
  }

  private void loadCategories() {
    Category fruits = new Category();
    fruits.setName("Fruits");

    Category dried = new Category();
    dried.setName("Dried");

    Category fresh = new Category();
    fresh.setName("Fresh");

    Category nuts = new Category();
    nuts.setName("Nuts");

    Category exotic = new Category();
    exotic.setName("Exotic");

    categoryRepository.save(fruits);
    categoryRepository.save(dried);
    categoryRepository.save(fresh);
    categoryRepository.save(exotic);
    categoryRepository.save(nuts);
  }

  private void loadCustomers() {
    Customer fahad = new Customer();
    fahad.setFirstName("Fahad");
    fahad.setLastName("A");

    Customer ahmed = new Customer();
    ahmed.setFirstName("Ahmed");
    ahmed.setLastName("F");

    Customer khalid = new Customer();
    khalid.setFirstName("Khalid");
    khalid.setLastName("K");

    customerRepository.save(fahad);
    customerRepository.save(khalid);
    customerRepository.save(ahmed);
  }

  private void loadVendors() {
    Vendor vendorA = new Vendor();
    vendorA.setName("Vendor Ahmed");

    Vendor vendorH = new Vendor();
    vendorH.setName("Vendor Hussam");

    vendorRepository.save(vendorA);
    vendorRepository.save(vendorH);
  }
}
