package com.example.ipmanager;

import com.example.ipmanager.IPAddress.IPAddress;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class IPManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(IPManagerApplication.class, args);
	}

}
