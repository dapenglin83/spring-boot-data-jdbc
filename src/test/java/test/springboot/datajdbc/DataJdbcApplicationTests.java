package test.springboot.datajdbc;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import test.springboot.datajdbc.data.Customer;

import java.util.Random;

import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class DataJdbcApplicationTests {

	@Autowired
	MockMvc mockMvc;

	@Autowired
	ObjectMapper objectMapper;

	Customer getCustomer(Long id) throws Exception {
		MvcResult result = this.mockMvc.perform(get("/customer/{id}", id))
				.andExpect(status().isOk())
				.andReturn();
		return objectMapper.readValue(result.getResponse().getContentAsString(), Customer.class);
	}

	@Test
	void contextLoads() {
	}

	@Test
	@Order(1)
	public void testGetCustomerNotFound() throws Exception {
		long id = 1L;
		this.mockMvc.perform(get("/customer/{id}", id)).andDo(print())
				.andExpect(status().isNotFound());
	}

	@Test
	@Order(2)
	public void testCreateCustomerOK() throws Exception {
		Customer customer = new Customer();
		String firstName = "abcd";
		String lastName = "dcba";
		customer.setFirstName(firstName);
		customer.setLastName(lastName);
		String content = objectMapper.writeValueAsString(customer);
		this.mockMvc.perform(post("/customer").contentType(MediaType.APPLICATION_JSON).content(content))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.firstName").value(firstName))
				.andExpect(jsonPath("$.lastName").value(lastName));
	}

	@Test
	@Order(3)
	public void testGetCustomerFound() throws Exception {
		long id = 1L;
		Customer customer = getCustomer(id);
		this.mockMvc.perform(get("/customer/{id}", 1)).andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id", is(1)))
				.andExpect(jsonPath("$.firstName").value(customer.getFirstName()))
				.andExpect(jsonPath("$.lastName").value(customer.getLastName()));
	}

	@Test
	@Order(4)
	public void testUpdateCustomerOk() throws Exception {
		long id = 1L;
		Random random = new Random();
		Customer customer = getCustomer(id);
		String firstName = customer.getFirstName() + random.nextInt(100);
		String lastName = customer.getLastName() + random.nextInt(100);
		customer.setFirstName(firstName);
		customer.setLastName(lastName);
		this.mockMvc.perform(put("/customer/{id}", id).contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(customer)))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.firstName").value(firstName))
				.andExpect(jsonPath("$.lastName").value(lastName));
	}


	@Test
	@Order(5)
	public void testUpdateCustomerFailedWithVersionMismatch() throws Exception {
		long id = 1L;
		Random random = new Random();
		Customer customer = getCustomer(id);
		String firstName = customer.getFirstName() + random.nextInt(100);
		String lastName = customer.getLastName() + random.nextInt(100);
		customer.setFirstName(firstName);
		customer.setLastName(lastName);
		customer.setVersion(customer.getVersion() + 10);
		this.mockMvc.perform(put("/customer/{id}", id).contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(customer)))
				.andDo(print())
				.andExpect(status().isConflict());
	}

	@Test
	@Order(6)
	public void testDeleteOk() throws Exception {
		long id = 1L;
		Customer customer = getCustomer(id);
		this.mockMvc.perform(delete("/customer/{id}", id))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().json(objectMapper.writeValueAsString(customer)));
	}

	@Test
	@Order(7)
	public void testDeleteNotFound() throws Exception {
		long id = 1L;
		this.mockMvc.perform(delete("/customer/{id}", id))
				.andDo(print())
				.andExpect(status().isNotFound());
	}
}
