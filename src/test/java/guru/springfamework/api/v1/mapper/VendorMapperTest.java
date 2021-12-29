package guru.springfamework.api.v1.mapper;

import static org.junit.Assert.assertEquals;

import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.domain.Vendor;
import org.junit.Test;

public class VendorMapperTest {

  VendorMapper vendorMapper = VendorMapper.INSTANCE;

  @Test
  public void testVendorToVendorDTO() {
    Vendor vendor = new Vendor();
    vendor.setName("Test");

    VendorDTO vendorDTO = vendorMapper.vendorToVendorDTO(vendor);

    assertEquals(vendorDTO.getName(), "Test");
  }

  @Test
  public void testVendorDTOToVendor() {
    VendorDTO vendorDTO = new VendorDTO();
    vendorDTO.setName("Test");

    Vendor vendor = vendorMapper.vendorDTOToVendor(vendorDTO);

    assertEquals(vendor.getName(), "Test");
  }
}