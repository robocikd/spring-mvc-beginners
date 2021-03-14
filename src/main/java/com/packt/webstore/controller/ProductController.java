package com.packt.webstore.controller;

import com.packt.webstore.domain.Product;
import com.packt.webstore.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.MatrixVariable;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Controller
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @RequestMapping()
    public String list(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        return "products";
    }

    @RequestMapping("/all")
    public String allProducts(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        return "products";
    }

    @RequestMapping("/{category}")
    public String getProductsByCategory(Model model, @PathVariable("category") String productCategory) {
        model.addAttribute("products", productService.getProductsByCategory(productCategory));
        return "products";
    }

    @RequestMapping("/filter/{ByCriteria}")
    public String getProductByFilter(@MatrixVariable(pathVar = "ByCriteria") Map<String, List<String>> filterParams, Model model) {
        model.addAttribute("products", productService.getProductByFilter(filterParams));
        return "products";
    }

    @RequestMapping("/product")
    public String getProductById(@RequestParam("id") String porductId, Model model) {
        model.addAttribute("product", productService.getProductById(porductId));
        return "product";
    }

    @RequestMapping("/{tablet}/{price}")
    public String filterProducts(@PathVariable("tablet") String category, @MatrixVariable Map<String, List<String>> price, @RequestParam("manufacturer") String manufacturer, Model model) {
        List<Product> productsByCategory = productService.getProductsByCategory(category);
        Set<Product> productByPriceFilter = productService.getProductByPriceFilter(price);
        List<Product> productsByManufacturer = productService.getProductsByManufacturer(manufacturer);
        productsByCategory.retainAll(productByPriceFilter);
        productsByCategory.retainAll(productsByManufacturer);
        model.addAttribute("products", productsByCategory);
        return "products";
    }

//    @RequestMapping(value = "/add", method = RequestMethod.GET)
//    public String getAddNewProductForm(Model model) {
//        Product newProduct = new Product();
//        model.addAttribute("newProduct", newProduct);
//        return "addProduct";
//    }
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String getAddNewProductForm(@ModelAttribute("newProduct") Product newProduct){
        return "addProduct";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String processAddNewProductFrom(@ModelAttribute("newProduct") Product newProduct) {
        productService.addProduct(newProduct);
        return "redirect:/products";
    }
}
