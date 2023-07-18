package com.example.gccoffee.repository;

import static com.example.gccoffee.Utils.*;

import java.nio.ByteBuffer;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.gccoffee.model.Category;
import com.example.gccoffee.model.Product;

@Repository
public class productJdbcRepository implements ProductRepository{
	private final NamedParameterJdbcTemplate jdbcTemplate;

	public productJdbcRepository(NamedParameterJdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public List<Product> findAll() {
		return jdbcTemplate.query("select * from products", productRowMapper);
	}

	@Override
	public Product insert(Product product) {
		int updatedProductNum = jdbcTemplate.update(
			"INSERT INTO products(product_id, product_name, category, price, created_at, updated_at) "
				+ "VALUES (UNHEX(REPLACE(:productId, '-', '')), :productName, :category, :price, :createdAt, :updatedAt)"
			, toParamMap(product)
		);

		if (updatedProductNum != 1) {
			throw new RuntimeException("Nothing was inserted");
		}

		return product;
	}

	@Override
	public Product update(Product product) {
		return null;
	}

	@Override
	public Optional<Product> findById(UUID productId) {
		return Optional.empty();
	}

	@Override
	public Optional<Product> findByName(String productName) {
		return Optional.empty();
	}

	@Override
	public List<Product> findByCategory(Category category) {
		return null;
	}

	@Override
	public void deleteAll() {

	}

	private static final RowMapper<Product> productRowMapper = (resultSet, i) ->
	{
		var productId = toUUID(resultSet.getBytes("product_id"));
		var productName = resultSet.getString("product_name");
		var category = Category.valueOf(resultSet.getString("category"));
		var price = resultSet.getLong("price");
		var description =resultSet.getString("description");
		var createdAt = toLocalDateTime(resultSet.getTimestamp("created_at"));
		var updatedAt = toLocalDateTime(resultSet.getTimestamp("updated_at"));
		return new Product(
			productId,
			productName,
			category,
			price,
			description,
			createdAt,
			updatedAt
		);
	};

	private Map<String, Object> toParamMap(Product product) {
		var paramMap = new HashMap<String, Object>();
		paramMap.put("productId", product.getProductId().toString());
		paramMap.put("productName", product.getProductName());
		paramMap.put("category", product.getCategory().toString());
		paramMap.put("price", product.getPrice());
		paramMap.put("description", product.getDescription());
		paramMap.put("createdAt", product.getCreatedAt());
		paramMap.put("updatedAt", product.getUpdatedAt());
		return paramMap;
	}
}
