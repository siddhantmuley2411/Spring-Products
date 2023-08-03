package com.api.controller;

import com.api.Exception.HnDBankException;
import com.api.dto.ProductDTO;
import com.api.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/hndbank")
public class ProductController {
    @Autowired
    private ProductService ProductService;
    @Autowired
    private Environment environment;
    @GetMapping(value = "/products")
    public ResponseEntity<List<ProductDTO>> getAllProducts() throws HnDBankException {
        List<ProductDTO> productList = ProductService.findAll();
        return new ResponseEntity<>(productList, HttpStatus.OK);
    }
    @GetMapping(value = "/products/{productId}")
    public ProductDTO getProduct(@PathVariable Integer ProductId) throws HnDBankException {
        ProductDTO product = ProductService.getProduct(ProductId);
        return product;
    }
    @GetMapping(value = "/product")
    public ResponseEntity<ProductDTO> getProductParam(@RequestParam Integer productId) throws HnDBankException {
        ProductDTO product = ProductService.getProduct(productId);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }
    @PostMapping(value = "/products")
    public ResponseEntity<String> addProduct(@RequestBody  ProductDTO product) throws HnDBankException {
        Integer productId = ProductService.addProduct(product);
        String successMessage = environment.getProperty("API.INSERT_SUCCESS") + productId;
        return new ResponseEntity<>(successMessage, HttpStatus.CREATED);
    }
    @PutMapping(value = "/products/{productId}")
    public ResponseEntity<String> updateProduct(@PathVariable Integer productId, @RequestBody ProductDTO product)
            throws HnDBankException {
        ProductService.updateProduct(productId,product);
        String successMessage = environment.getProperty("API.UPDATE_SUCCESS");
        return new ResponseEntity<>(successMessage, HttpStatus.OK);
    }
    @DeleteMapping(value = "/products/{productId}")
    public ResponseEntity<String> deleteProduct(@PathVariable Integer productId) throws HnDBankException {
        ProductService.deleteProduct(productId);
        String successMessage = environment.getProperty("API.DELETE_SUCCESS");
        return new ResponseEntity<>(successMessage, HttpStatus.OK);
    }

}
