package com.jsp;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

@DataJpaTest
//@AutoConfigureTestDatabase
public class ProductsTests {

	@Autowired
	private ProductRepository repo;

	@Test
	@Rollback(false)
	public void testCreateProduct() {
		Product product = new Product("iphone 10", 789);
		Product saveProduct = repo.save(product);

		assertNotNull(saveProduct);
	}

	@Test
	public void testFindProductByNameExist() {
		String name = "iphone 11";
		Product product = repo.findByName(name);
		assertThat(product.getName()).isEqualTo(name);

	}

	@Test
	public void testFindProductByNameNotExist() {
		String name = "Iphone 10";
		Product product = repo.findByName(name);
		assertNull(product);	
	}

	@Test
	@Rollback(false)
	public void testUpdateProduct() {
		String productName = "Android phone";
		Product product =new  Product(productName, 199);
		product.setId(3);
		
		repo.save(product);
		Product updateProduct = repo.findByName(productName);
		assertThat(updateProduct.getName()).isEqualTo(productName);
	}			
	
	@Test
	public void testListProducts() {
		List<Product>products = (List<Product>) repo.findAll();
		
		for(Product product : products) {
			System.out.println(product);
		}
		
		assertThat(products).size().isGreaterThan(0);
	}
	
	@Test
	@Rollback(false)
	public void testDeleteProduct() {
		Integer id = 2;
		
		boolean isExistsBeforeDelete = repo.findById(id).isPresent();
		
		repo.deleteById(id);
		
		boolean notExistsAfterDelete = repo.findById(id).isPresent();
		
		assertTrue(isExistsBeforeDelete);
		assertThat(notExistsAfterDelete);

	}
}
