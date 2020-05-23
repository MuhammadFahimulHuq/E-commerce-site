package com.system.security.Service;


import com.system.security.Model.ProductSpecs;
import com.system.security.Model.Products;
import com.system.security.Model.ProductsCategory;
import com.system.security.Model.Review;
import com.system.security.Repository.ProductCategoryRepository;
import com.system.security.Repository.ProductRepository;
import com.system.security.Repository.ReviewRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class ProductsService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductCategoryRepository productCategoryRepository;
    @Autowired
    private ReviewRepository reviewRepository;

    private final Map<Products,Integer> addToCart = new HashMap<>();

    private Collection<Products> productsCollection = new ArrayList<>();

    public Products findByProductName(String ProductName){
        return productRepository.findByProductName(ProductName);
    }

    public Products findByProductId(int id){
        return productRepository.findById(id).orElse(null);
    }

    public List<Products> getAllProducts(){
        return   productRepository.findAll();
    }

    public Optional<Products> findById(int id){return productRepository.findById(id);}

    public void saveImage(MultipartFile imageFile) throws Exception {
        Path currentPath = Paths.get(".");
        byte[] bytes =  imageFile.getBytes();
        Path path = Paths.get(currentPath.toAbsolutePath()+"/src/main/resources/static/photos/"+imageFile.getOriginalFilename());
        Files.write(path,bytes);
    }
    /**addToCart  add function**/
    public void addProduct(Products product){
        if(addToCart.containsKey(product)){
            addToCart.replace(product,addToCart.get(product)+1);
        }
        else{
            addToCart.put(product,1);
        }
    }
    public void addProductCollection(Products products){
        productsCollection.add(products);
    }

    /**addToCart  remove function**/
    public void removeProduct(Products product){
        if ( addToCart.containsKey(product)) {
            if ( addToCart.get(product) > 1)
                addToCart.replace(product,  addToCart.get(product) - 1);
            else if ( addToCart.get(product) == 1) {
                addToCart.remove(product);
            }
        }
    }
    public void removeProductCollection(int id) {
    if(productsCollection != null){
        productsCollection.removeIf(entry->entry.getId()==id);
    }
    else System.out.println("no product id:"+id);
    }




    /**addToCart  get function**/
    public Map<Products,Integer> getProductsInCart(){
        return Collections.unmodifiableMap( addToCart);
    }

    public Collection<Products> getProductsCollection(){
return productsCollection.stream().collect(Collectors.toList());
    }





/**addToCart size of the list**/
public int sizeOfList (){return getProductsInCart().size();}

public int sizeOfListCollection(){return  getProductsCollection().size();}

/**addToCart get Total**/
public BigDecimal getTotal(){
    return addToCart.entrySet().stream()
            .map(entry-> entry.getKey().getPrice().multiply(BigDecimal.valueOf(entry.getValue())))
            .reduce(BigDecimal::add)
            .orElse(BigDecimal.ZERO);
}
public BigDecimal getTotalCollection(){
    return productsCollection.stream()
            .map(entry->entry.getPrice())
            .reduce(BigDecimal::add)
            .orElse(BigDecimal.ZERO);
}

public List<Products> findByCollectionId(Collection<Integer> id){
    return productRepository.findAllById(id);
}


public void removeAllProductFromCollection(){
    productsCollection.removeAll(getProductsCollection());
}

public Products findProduct(String productname){return productRepository.findByProductName(productname);}



//categories

    public List<ProductsCategory> getAllProductCategories (){
    return productCategoryRepository.findAll();
    }
    public ProductsCategory getCategories(int id){
    return productCategoryRepository.findById(id).orElse(null);
    }


    public ProductsCategory getProductId(int id){
    return productCategoryRepository.findById(id).orElse(null);
    }
//ProductSpec
    public ProductSpecs getProductSpecs(int id){
    return findByProductId(id).getProductSpecs();
    }

   //ProductReview


    public Collection<Review> getReviewByProductId(int id){
   return findByProductId(id).getReviews();
    }
public Double getAverageInteger(int id){
    IntSummaryStatistics stat = getReviewByProductId(id).stream().mapToInt(entry->entry.getRating())
            .summaryStatistics();

 return stat.getAverage();
}


}




