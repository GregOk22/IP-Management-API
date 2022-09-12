package com.example.ipmanager.IPAddress;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class IPAddressService {
    private final IPAddressRepository ipAddressRepository;
    private static final String IPv4CIDRBlock =
            "^(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." +
            "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." +
            "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." +
            "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)" +
                    "($|/(24|25|26|27|28|29|30))$";
    private static final Pattern IPv4Pattern = Pattern.compile(IPv4CIDRBlock);

    @Autowired
    public IPAddressService(IPAddressRepository ipAddressRepository) {
        this.ipAddressRepository = ipAddressRepository;
    }

    //-- GET ALL ADDRESSES
    public List<IPAddress> getIPAddresses() {
        return ipAddressRepository.findAll();
    }

    //-- CREATE ADDRESSES IN CIDR BLOCK
    public void createCIDRBlock(String fullAddress) {

        // 1. Validate CIDR block format
        System.out.println("NEW Request to create IP block: " + fullAddress + "\n");
        if (isValidCIDRBlockAddress(fullAddress)) {
            // Clean up
            fullAddress = fullAddress.replaceAll("(?<=^|\\.)0+(?!\\.|/|$)","");
            System.out.println("Valid CIDR Block! \n");
            // break down the address
            String[] ipSections = fullAddress.split("[./]");
            String networkAddress = ipSections[0] + "." + ipSections[1] + "." + ipSections[2] + "." + ipSections[3];
            int cidrBlock = Integer.parseInt(ipSections[4]);
            System.out.println("Network Address = " + networkAddress);
            System.out.println("CIDR block size = " + cidrBlock + "\n");


            // 2. Check if all addresses in block are available [more logic can go into what CIDR blocks are avail if wanted]
            int numOfBlockAddresses = (int) Math.pow(2, (32 - cidrBlock));
            if (isAvailableCIDRBlock(ipSections, numOfBlockAddresses)) {

                // 3. Create all IP Address's in the new block
                int currentHostAddress = Integer.parseInt(ipSections[3]);
                for (int i = 0; i < numOfBlockAddresses; i++) {
                    if (currentHostAddress == 256) break;

                    // we can also set the network & broadcast addresses (first and last) to 'acquired' here if wanted

                    String newAddress = ipSections[0] + "." + ipSections[1] + "." + ipSections[2] + "." + currentHostAddress;
                    IPAddress newIPAddress = new IPAddress(newAddress, "available");
                    ipAddressRepository.save(newIPAddress);
                    System.out.println("Created new IP Address: " + newAddress);

                    currentHostAddress++;
                }
                System.out.println();
            }

        } else System.out.println("Invalid CIDR block! \n");
    }

    //-- UPDATE ADDRESS TO ACQUIRED
    @Transactional
    public void acquireAddress(String address) {
        IPAddress ipAddress = ipAddressRepository.findById(address)
                .orElseThrow(() -> new IllegalStateException((
                        "This IP Address does not exist in the system: " + address
                        ))
                );
        ipAddress.setStatus("acquired");
    }

    //-- UPDATE ADDRESS TO AVAILABLE
    @Transactional
    public void releaseAddress(String address) {
        IPAddress ipAddress = ipAddressRepository.findById(address)
                .orElseThrow(() -> new IllegalStateException((
                                "This IP Address does not exist in the system: " + address
                        ))
                );
        ipAddress.setStatus("available");
    }


    // Helper Functions
    private static boolean isValidCIDRBlockAddress(String cidrBlock) {
        if (cidrBlock == null) return false;
        Matcher matcher = IPv4Pattern.matcher(cidrBlock);
        return matcher.matches();
    }
    public boolean isAvailableCIDRBlock(String[] ipSections, int numOfBlockAddresses) {
        int currentHostAddress = Integer.parseInt(ipSections[3]);
        for (int i = 0; i < numOfBlockAddresses; i++) {
            String address = ipSections[0] + "." + ipSections[1] + "." + ipSections[2] + "." + currentHostAddress;
            Optional<IPAddress> addressOptional = ipAddressRepository.findExistingAddress(address);
            if (addressOptional.isPresent()) {
                System.out.println("IP Address inside the incoming block already exists!: " + addressOptional);
                throw new IllegalStateException("An IP Address inside the incoming block already exists!");
            }
            currentHostAddress++;
        }
        return true;
    }
}
