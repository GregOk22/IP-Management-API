package com.example.ipmanager.IPAddress;

import javax.persistence.*;

@Entity
@Table
public class IPAddress {
    @Id

    private String address;
    private String status;

    public IPAddress() {
    }

    public IPAddress(String address, String status) {
        this.address = address;
        this.status = status;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "IPAddress{" +
                "address='" + address + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}


