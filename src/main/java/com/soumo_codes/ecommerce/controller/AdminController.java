package com.soumo_codes.ecommerce.controller;

import com.soumo_codes.ecommerce.dto.ProductDTO;
import com.soumo_codes.ecommerce.model.Category;
import com.soumo_codes.ecommerce.model.Product;
import com.soumo_codes.ecommerce.service.CategoryService;
import com.soumo_codes.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@Controller
public class AdminController {

    public static String uploadDir= System.getProperty("user.dir")+"/src/main/resources/static/productImages"; //here user.dir is root means exactly before the src folder;

    @Autowired
    CategoryService categoryService;
    @GetMapping("/admin")
    public String adminHome(){
        return "adminHome";
    }

    @GetMapping("/admin/categories")
    public String getCategories(Model model){
        model.addAttribute("categories", categoryService.getAllCategory()); //these categories in HTML belong to th:each="category, iStat : ${categories}">
        // and value will be categoryService.getAllCategory()
        return "categories";
    }

    @GetMapping("/admin/categories/add")
    public String getAddCategories(Model model){ //this function will redirect to add category page to provide the values, there should be a parameter of model type which will add a object of type category
        model.addAttribute("category", new Category()); //this "category" should be same with HTML value "th:object="${category}" and will create an object here
        return "categoriesAdd";
    }

    @PostMapping("/admin/categories/add") //this route will do the post mapping
    public String postAddCategories(@ModelAttribute("category") Category category ){ // this function will perform when we will click on save, there should be a parameter of modelAttribute and
                                                                    // this "category" should be same with HTML value "th:object="${category}" and will create an object here
//  will now save the category
        categoryService.addCategory(category);
        return "redirect:/admin/categories"; //after saving this will redirect to category
    }

    @GetMapping("/admin/categories/delete/{id}")
    public String deleteCategory(@PathVariable int id){
        categoryService.removeCategoryByID(id);
        return "redirect:/admin/categories"; //after delete this will redirect to category
    }

    @GetMapping("/admin/categories/update/{id}")
    public String updateCategory(@PathVariable int id, Model model){
        Optional<Category> category = categoryService.getCategoryById(id);
        if (category.isPresent()) {
            model.addAttribute("category", category.get()); //category.get() will get the object
            return "categoriesAdd";
        }
        else return "404";
    }


    /////////////////////////Product Section////////////////////////////////////

    @Autowired
    ProductService productService;
    @GetMapping("/admin/products")
    public String getProducts(Model model){
        model.addAttribute("products", productService.getAllProduct());
        return "products";
    }


    @GetMapping("/admin/products/add")
    public String productAddGet(Model model){
        model.addAttribute("productDTO", new ProductDTO());
        model.addAttribute("categories", categoryService.getAllCategory());
        return "productsAdd";
    }

    @PostMapping("/admin/products/add") //this route will do the post mapping
    public String postAddProducts(@ModelAttribute("productDTO") ProductDTO productDTO,// using productDTO as we are sending as an object rather than individually
                                  @RequestParam("productImage")MultipartFile file, //this productImage will be same as name="productImage" as in HTML page
                                  @RequestParam("imgName") String imgName)throws IOException { // this function will perform when we will click on save, there should be a parameter of modelAttribute and

        Product product = new Product();

        product.setId(productDTO.getId());
        product.setName(productDTO.getName());
        //first we fetch the getCategoryById and into this use productDTO.getCategoryId() and as it optional we will use get()
        // productDTO.getCategoryId() have the id for t-shirt and based on this id we will fetch the original object
        product.setCategory(categoryService.getCategoryById(productDTO.getCategoryId()).get());
        product.setPrice(productDTO.getPrice());
        product.setWeight(productDTO.getWeight());
        product.setDescription(productDTO.getDescription());
        String imageUUID;
        if(!file.isEmpty()){
            imageUUID = file.getOriginalFilename();
            Path fileNameAndPath = Paths.get(uploadDir, imageUUID);
            Files.write(fileNameAndPath,file.getBytes());
        }
        else {
            imageUUID=imgName;
        }
        product.setImageName(imageUUID);
        productService.addProduct(product);

        return "redirect:/admin/products";
    }

    @GetMapping("/admin/product/delete/{id}")
    public String deleteProduct(@PathVariable long id){

        productService.removeProductById(id);
        return "redirect:/admin/products"; //after delete this will redirect to category
    }

    @GetMapping("/admin/product/update/{id}")
    public String updateProductGet(@PathVariable long id, Model model){ //here will convert product to ProductDTO
        Product product=productService.getProductById(id).get(); // first will fetch te product by id
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(product.getId());
        productDTO.setName(product.getName());
        productDTO.setCategoryId(product.getCategory().getId()); // get the id of category of the product
        productDTO.setPrice(product.getPrice());
        productDTO.setWeight(product.getWeight());
        productDTO.setDescription(product.getDescription());
        productDTO.setImageName(product.getImageName());

        model.addAttribute("categories", categoryService.getAllCategory());
        model.addAttribute("productDTO", productDTO);

        return "productsAdd";
    }


}
