package com.example.ipmanager.IPAddress;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1")
public class IPAddressController {
    private final IPAddressService ipAddressService;

    @Autowired
    public IPAddressController(IPAddressService ipAddressService) {
        this.ipAddressService = ipAddressService;
    }

    @GetMapping(path = "GetAllAddresses")
    public List<IPAddress> getIPAddresses() {
        return ipAddressService.getIPAddresses();
    }

    @PostMapping(path = "CreateCIDRBlock")
    public void createCIDRBlock(@RequestParam String fullAddress) {
        ipAddressService.createCIDRBlock(fullAddress);
    }

    @PutMapping(path = "AcquireAddress")
    public void acquireAddress(@RequestParam String address) {
        ipAddressService.acquireAddress(address);
    }

    @PutMapping(path = "ReleaseAddress")
    public void releaseAddress(@RequestParam String address) {
        ipAddressService.releaseAddress(address);
    }
}
