package com.example.ipmanager.IPAddress;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public interface IPAddressRepository extends JpaRepository<IPAddress, String> {

    @Query("SELECT ip FROM IPAddress ip WHERE ip.address = ?1")
    Optional<IPAddress> findExistingAddress(String address);

}
