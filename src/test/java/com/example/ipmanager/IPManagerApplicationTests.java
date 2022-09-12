package com.example.ipmanager;

import com.example.ipmanager.IPAddress.IPAddress;
import com.example.ipmanager.IPAddress.IPAddressController;
import com.example.ipmanager.IPAddress.IPAddressRepository;
import com.example.ipmanager.IPAddress.IPAddressService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.Assert;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@ContextConfiguration(classes = IPManagerApplication.class)
@WebMvcTest(IPAddressController.class)
class IPManagerApplicationTests {

	@Test
	void contextLoads() {
	}

	public IPManagerApplicationTests() {

	}

	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private IPAddressService ipAddressService;
	@MockBean
	private IPAddressRepository ipAddressRepository;

	IPAddress address1 = new IPAddress("15.0.0.1", "available");
	IPAddress address2 = new IPAddress("15.0.0.2", "available");
	IPAddress address3 = new IPAddress("15.0.0.3", "acquired");

	@Test
	public void getAllAddresses_success() throws Exception {
		List<IPAddress> addressList = new ArrayList<>(Arrays.asList(address1, address2, address3));

		Mockito.when(ipAddressService.getIPAddresses()).thenReturn(addressList);

		mockMvc.perform(MockMvcRequestBuilders
				.get("/api/v1/GetAllAddresses"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(3)));
	}

	@Test
	public void createCIDRBlock_success() throws Exception {
		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
				.post("/api/v1/CreateCIDRBlock")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.param("fullAddress", "25.0.0.0/24");
		mockMvc.perform(mockRequest)
				.andExpect(status().isOk());

	}

	/*


	@Test
	public void acquireAddresses_success() throws Exception {
		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
				.put("/api/v1/AcquireAddress")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.param("address", "10.0.0.1");
		mockMvc.perform(mockRequest)
				.andExpect(status().isOk());
	}

	@Test
	public void releaseAddresses_success() throws Exception {
		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
				.put("/api/v1/ReleaseAddress")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.param("address", "10.0.0.3");
		mockMvc.perform(mockRequest)
				.andExpect(status().isOk());
	}

	 */
}
