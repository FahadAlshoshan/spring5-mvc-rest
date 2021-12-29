package guru.springfamework.services;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import guru.springfamework.api.v1.mapper.VendorMapper;
import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.domain.Vendor;
import guru.springfamework.repositories.VendorRepository;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class VendorServiceImplTest {

  @Mock
  VendorRepository vendorRepository;
  VendorService vendorService;

  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);
    vendorService = new VendorServiceImpl(vendorRepository, VendorMapper.INSTANCE);
  }

  @Test
  public void testGetAllVendors() {
    List<Vendor> vendors = Arrays.asList(new Vendor(), new Vendor());

    when(vendorRepository.findAll()).thenReturn(vendors);

    List<VendorDTO> list = vendorService.getAllVendors();

    assertEquals(list.size(), 2);
  }

  @Test
  public void testGetVendorById() {
    Vendor vendor = new Vendor();
    vendor.setName("Test");
    vendor.setId(1L);

    when(vendorRepository.findById(anyLong())).thenReturn(Optional.of(vendor));

    VendorDTO vendorDTO = vendorService.getVendorById(1L);

    assertEquals(vendorDTO.getId(), Long.valueOf(1));
    assertEquals(vendorDTO.getVendorUrl(), "/api/v1/vendors/1");
  }

  @Test
  public void testCreateNewVendor() {
    Vendor vendor = new Vendor();
    vendor.setName("Test");
    vendor.setId(1L);

    VendorDTO vendorDTO = new VendorDTO();
    vendorDTO.setName(vendor.getName());
    vendorDTO.setId(vendorDTO.getId());

    when(vendorRepository.save(any())).thenReturn(vendor);

    VendorDTO vendorDTOreturned = vendorService.createNewVendor(vendorDTO);

    assertEquals(vendorDTOreturned.getId(), Long.valueOf(1));
    assertEquals(vendorDTOreturned.getVendorUrl(), "/api/v1/vendors/1");
  }


  @Test
  public void testUpdateVendor() {

    VendorDTO vendorDTO = new VendorDTO();
    vendorDTO.setName("Test");
    vendorDTO.setId(1L);

    Vendor vendor = new Vendor();
    vendor.setName(vendorDTO.getName());
    vendor.setId(vendorDTO.getId());

    when(vendorRepository.save(any())).thenReturn(vendor);

    VendorDTO vendorDTOreturned = vendorService.updateVendor(vendorDTO, 1L);

    assertEquals(vendorDTOreturned.getId(), Long.valueOf(1));
    assertEquals(vendorDTOreturned.getName(), vendor.getName());
    assertEquals(vendorDTOreturned.getVendorUrl(), "/api/v1/vendors/1");
  }

  @Test
  public void testDeleteVendor() {
    Long id = 1L;

    vendorService.deleteVendor(id);

    verify(vendorRepository,times(1)).deleteById(anyLong());
  }
}