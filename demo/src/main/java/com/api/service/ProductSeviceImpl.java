package com.api.service;

import com.api.Exception.HnDBankException;
import com.api.dto.ProductDTO;
import com.api.entity.Product;
import com.api.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRespository;

    @Override
    public int addProduct(ProductDTO productDto) throws HnDBankException {
        Optional<Product> optional = productRespository.findById(productDto.getProductId());
        if (optional.isPresent())
            throw new HnDBankException("Service.CUSTOMER_FOUND");
        Product product = new Product();
        product.setDateOfBirth(productDto.getDateOfBirth());
        product.setEmailId(productDto.getEmailId());
        product.setName(productDto.getName());
        product.setProductId(productDto.getProductId());
        product.setProductType(productDto.getProductType());
        Product s = productRespository.save(product);
        return s.getProductId();
    }
    @Override
    public ProductDTO getProduct(Integer productId) throws HnDBankException {
        Optional<Product> optional = productRespository.findById(productId);
        Product product = optional.orElseThrow(() -> new HnDBankException("Service.CUSTOMER_NOT_FOUND"));
//        Product product = new Product();
        ProductDTO productDto = new ProductDTO();
        productDto.setProductId(product.getProductId());
        productDto.setDateOfBirth(product.getDateOfBirth());
        productDto.setEmailId(product.getEmailId());
        productDto.setName(product.getName());
        productDto.setProductType(product.getProductType());
        return productDto;
    }
    @Override
    public List<ProductDTO> findAll() throws HnDBankException {
        Iterable<Product> products = productRespository.findAll();
        List<ProductDTO> productDTOs = new ArrayList<>();
        products.forEach(product -> {
            ProductDTO productDto = new ProductDTO();
            productDto.setProductId(product.getProductId());
            productDto.setDateOfBirth(product.getDateOfBirth());
            productDto.setEmailId(product.getEmailId());
            productDto.setProductType(product.getProductType());
            productDto.setName(product.getName());
            productDTOs.add(productDto);
        });
        if (productDTOs.isEmpty())
            throw new HnDBankException("Service.CUSTOMERS_NOT_FOUND");
        return productDTOs;
    }

    @Override
    public void updateProduct(Integer productId, ProductDTO productDTO) throws HnDBankException {
        Optional<Product> optional = productRespository.findById(productId);
        Product product = optional.orElseThrow(() -> new HnDBankException("Service.CUSTOMER_NOT_FOUND"));
        product.setEmailId(productDTO.getEmailId());
        product.setName(productDTO.getName());
        product.setProductType(productDTO.getProductType());
    }

    @Override
    public void deleteProduct(Integer productId) throws HnDBankException {
        Optional<Product> optional = productRespository.findById(productId);
        optional.orElseThrow(() -> new HnDBankException("Service.CUSTOMER_NOT_FOUND"));
        productRespository.deleteById(productId);
    }

//    @Override
//    public void findBy(String emailId, int custId) throws HnDBankException {
//        List<Product> custlist = productRespository.findByEmailId(emailId);
//        System.out.println(custlist);
//        List<String> name = productRespository.findNameByEmailId(emailId);
//        System.out.println(name);
//
//        productRespository.updateProductEmailId("h1234@gmail.com", custId);
//
////        optional.orElseThrow(() -> new HnDBankException("Service.CUSTOMER_NOT_FOUND"));
//
//    }


}
