package guru.springfamework.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import guru.springfamework.api.v1.mapper.VendorMapper;
import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.bootstrap.Bootstrap;
import guru.springfamework.domain.Vendor;
import guru.springfamework.repositories.CategoryRepository;
import guru.springfamework.repositories.CustomerRepository;
import guru.springfamework.repositories.VendorRepository;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class VendorServiceImplIT {

  @Autowired
  VendorRepository vendorRepository;

  @Autowired
  CategoryRepository categoryRepository;

  @Autowired
  CustomerRepository customerRepository;

  VendorService vendorService;

  @Before
  public void setUp() throws Exception {
    Bootstrap bootstrap = new Bootstrap(categoryRepository, customerRepository, vendorRepository);
    bootstrap.run();

    vendorService = new VendorServiceImpl(vendorRepository, VendorMapper.INSTANCE);
  }

  @Test
  public void testPatchVendorName() {
    String updateName = "Update";
    Long id = getVendorIdValue();

    Vendor originalVendor = vendorRepository.getOne(id);
    assertNotNull(originalVendor);

    String originalName = originalVendor.getName();

    VendorDTO vendorDTO = new VendorDTO();
    vendorDTO.setName(updateName);

    vendorService.patchVendor(vendorDTO, id);

    Vendor updateVendor = vendorRepository.findById(id)
                                          .get();

    assertNotNull(updateVendor);
    assertEquals(updateVendor.getName(), updateName);
    assertNotEquals(updateVendor.getName(), originalName);

  }

  private Long getVendorIdValue() {
    List<Vendor> vendors = vendorRepository.findAll();

    System.out.println("Vendors Found: " + vendors.size());

    //return first id
    return vendors.get(0)
                  .getId();
  }
}
