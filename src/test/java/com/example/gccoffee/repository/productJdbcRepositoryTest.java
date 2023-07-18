package com.example.gccoffee.repository;

import static com.wix.mysql.EmbeddedMysql.*;
import static com.wix.mysql.ScriptResolver.*;
import static com.wix.mysql.config.MysqldConfig.*;
import static com.wix.mysql.distribution.Version.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;

import com.example.gccoffee.model.Category;
import com.example.gccoffee.model.Product;
import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.config.Charset;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
class productJdbcRepositoryTest {

	@Autowired
	ProductRepository repository;

	private Product newProduct = new Product(UUID.randomUUID(), "new-product", Category.COFFEE_BEAN_PACKAGE, 1000);

	@Test
	@Order(0)
	@DisplayName("상품을 모두 제거 할 수 있다.")
	void deleteAll() {
		repository.deleteAll();
		List<Product> all = repository.findAll();
		assertThat(all.isEmpty(), is(true));
	}

	@Test
	@Order(1)
	@DisplayName("상품을 추가할 수 있다.")
	void testInsert() {
		repository.insert(newProduct);
		List<Product> all = repository.findAll();
		assertThat(all.isEmpty(), is(false));
	}

	@Test
	@Order(2)
	@DisplayName("상품을 이름으로 조회할 수 있다.")
	void testFindByName() {
		Optional<Product> product = repository.findByName(newProduct.getProductName());
		assertThat(product.isEmpty(), is(false));
	}

	@Test
	@Order(3)
	@DisplayName("상품을 아이디로 조회할 수 있다.")
	void testFindById() {
		Optional<Product> product = repository.findById(newProduct.getProductId());
		assertThat(product.isEmpty(), is(false));
	}

	@Test
	@Order(4)
	@DisplayName("상품을 카테고리로 조회할 수 있다.")
	void testFindByCategory() {
		List<Product> product = repository.findByCategory(Category.COFFEE_BEAN_PACKAGE);
		assertThat(product.isEmpty(), is(false));
	}

	@Test
	@Order(5)
	@DisplayName("상품을 수정할 수 있다.")
	void testUpdate() {
		//given
		newProduct.setProductName("updated-product");

		//when
		repository.update(newProduct);

		//then
		var foundedProductById = repository.findById(newProduct.getProductId());
		var foundedProdcutByName = repository.findByName("updated-product");
		assertThat(foundedProductById.isEmpty(), is(false));
		assertThat(foundedProductById.get(), samePropertyValuesAs(foundedProdcutByName.get()));
	}
}