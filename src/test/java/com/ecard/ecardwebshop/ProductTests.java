package com.ecard.ecardwebshop;

import com.ecard.ecardwebshop.category.Category;
import com.ecard.ecardwebshop.product.Product;
import com.ecard.ecardwebshop.product.ProductController;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
@Sql(scripts = "/init.sql")
public class ProductTests {

	@Autowired
	private ProductController productController;


	@Test
	public void testGetProduct(){
		// When
		List<Product> products = productController.getProducts();
		//Then
		assertEquals(22, products.size());
	}

	@Test
	public void testSaveProductAndGetId() {
		//Given
		List<Product> products = productController.getProducts();
		// When
		productController.saveProductAndGetId(new Product(21, "25KA14", "balaton_shark", "Balaton Shark", "tester", 200000, "ACTIVE", new Category(1, "no category", 1)));
		//Then
		List<Product> products2 = productController.getProducts();
		assertEquals(22, products.size());
		assertEquals(23, products2.size());
		assertEquals("balaton_shark", products2.get(0).getAddress());
	}

	@Test
	public void testUpdateProducts(){
		// When
		productController.updateProducts(1, new Product(0, "ST001", "trainer", "Trainer", "Blue Sea", 11_111, "ACTIVE", new Category(1, "no category", 1)));
		// Then
		assertEquals("Trainer", productController.getProductById(1).getName());
		assertEquals("ST001", productController.getProductById(1).getCode());
		assertEquals("trainer", productController.getProductById(1).getAddress());
		assertEquals("Blue Sea", productController.getProductById(1).getManufacture());
		assertEquals(11_111, productController.getProductById(1).getPrice());
		assertEquals("ACTIVE", productController.getProductById(1).getProductStatus());
	}

	@Test
	public void testGetProducts(){
		// When
		List<Product> productListForAdmin = productController.getProducts();
		List<Product> productListForUser = productController.getProducts();
		// Then
		assertEquals(22, productListForAdmin.size());
		assertEquals(22, productListForUser.size());
	}

	@Test
	public void testGetProductsWithStartAndSize(){
		// When
		List<Product> productsPart = productController.getProductsWithStartAndSize(1,10, null);
		// Then
		assertEquals(10, productsPart.size());
	}

	@Test
	public void testDeleteProduct(){
		// When
		List<Product> products = productController.getProducts();
		productController.deleteProduct(7);
		List<Product> products2 = productController.getProducts();

		//Then
		assertEquals(products2.size(), products.size()-1);
	}

	@Test
	public void getProductById(){
		// When
		Product searchedProduct = productController.getProductById(14);
		//Then
		assertEquals(14, searchedProduct.getId());
		assertEquals("Educational Insights", searchedProduct.getManufacture());
		assertEquals("Science Quiz Flash Cards", searchedProduct.getName());
	}

	@Test
	public void testListAdviceProductsSizeOfListIsThree(){
		// When
		List<Product> productList = productController.listAdvicedProducts();
		//Then
		assertEquals(productList.size(),3 );
	}

	@Test
	public void testOfferedProductsStatusIsActive(){
		// When
		List<Product> productList = productController.listAdvicedProducts();
		//Then
		int sizeOfACTIVEProductList = productList.stream().filter(p -> p.getProductStatus().equals("ACTIVE")).collect(Collectors.toList()).size();
		assertEquals(productList.size(), sizeOfACTIVEProductList);
	}

	@Test
	public void testCategoryOrder(){
		//Given
		//When
		List<Product> products = productController.getProducts();
		for(Product product : products){
			System.out.println(product);
		}

		//Then
		Assert.assertEquals("Társasjáték kártyák",products.get(0).getCategory().getName());
		Assert.assertEquals("Oktató kártyajátékok", products.get(10).getCategory().getName());
	}


}
