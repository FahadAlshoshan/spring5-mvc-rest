package guru.springframework.services;

import guru.springframework.api.v1.model.VendorDTO;
import java.util.List;

public interface VendorService {

  List<VendorDTO> getAllVendors();

  VendorDTO getVendorById(Long id);

  VendorDTO createNewVendor(VendorDTO vendorDTO);

  VendorDTO updateVendor(VendorDTO vendorDTO, Long id);

  VendorDTO patchVendor(VendorDTO vendorDTO, Long id);

  void deleteVendor(Long id);

}
