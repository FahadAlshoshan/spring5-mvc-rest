package guru.springframework.services;

import guru.springframework.api.v1.mapper.VendorMapper;
import guru.springframework.api.v1.model.VendorDTO;
import guru.springframework.controllers.v1.VendorController;
import guru.springframework.domain.Vendor;
import guru.springframework.repositories.VendorRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class VendorServiceImpl implements VendorService {

  private final VendorRepository vendorRepository;
  private final VendorMapper vendorMapper;

  public VendorServiceImpl(VendorRepository vendorRepository, VendorMapper vendorMapper) {
    this.vendorRepository = vendorRepository;
    this.vendorMapper = vendorMapper;
  }

  @Override
  public List<VendorDTO> getAllVendors() {
    return vendorRepository.findAll()
                           .stream()
                           .map(vendor -> {
                                 VendorDTO vendorDTO = vendorMapper.vendorToVendorDTO(vendor);
                                 vendorDTO.setVendorUrl(getVendorUrl(vendor.getId()));
                                 return vendorDTO;
                               }
                           )
                           .collect(Collectors.toList());
  }

  @Override
  public VendorDTO getVendorById(Long id) {
    return vendorRepository.findById(id)
                           .map(vendorMapper::vendorToVendorDTO)
                           .map(vendorDTO -> {
                             vendorDTO.setVendorUrl(getVendorUrl(id));
                             return vendorDTO;
                           })
                           .orElseThrow(ResourceNotFoundException::new);
  }

  @Override
  public VendorDTO createNewVendor(VendorDTO vendorDTO) {
    return saveHelper(vendorMapper.vendorDTOToVendor(vendorDTO));
  }

  @Override
  public VendorDTO updateVendor(VendorDTO vendorDTO, Long id) {
    Vendor vendor = vendorMapper.vendorDTOToVendor(vendorDTO);
    vendor.setId(id);
    return saveHelper(vendor);
  }

  @Override
  public VendorDTO patchVendor(VendorDTO vendorDTO, Long id) {
    return vendorRepository.findById(id)
                           .map(vendor -> {
                             if (vendorDTO.getName() != null) {
                               vendor.setName(vendorDTO.getName());
                             }
                             VendorDTO returnedVendor = vendorMapper.vendorToVendorDTO(vendorRepository.save(vendor));
                             returnedVendor.setVendorUrl(getVendorUrl(id));
                             return returnedVendor;
                           })
                           .orElseThrow(ResourceNotFoundException::new);
  }

  @Override
  public void deleteVendor(Long id) {
    vendorRepository.deleteById(id);
  }

  private String getVendorUrl(Long id) {
    return VendorController.BASE_URL + "/" + id;
  }

  private VendorDTO saveHelper(Vendor vendor) {
    VendorDTO vendorDTO = vendorMapper.vendorToVendorDTO(vendorRepository.save(vendor));
    vendorDTO.setVendorUrl(getVendorUrl(vendorDTO.getId()));
    return vendorDTO;

  }
}
