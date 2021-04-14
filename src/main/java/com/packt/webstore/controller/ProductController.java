package com.packt.webstore.controller;

import com.packt.webstore.domain.Product;
import com.packt.webstore.exception.NoProductFoundUnderCategoryException;
import com.packt.webstore.exception.ProductNoFoundException;
import com.packt.webstore.service.ProductService;
import com.packt.webstore.validator.UnitsInStockValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Controller
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private UnitsInStockValidator unitsInStockValidator;

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
        List<Product> products = productService.getProductsByCategory(productCategory);
        if (products == null || products.isEmpty()) {
            throw new NoProductFoundUnderCategoryException();
        }
        model.addAttribute("products", products);
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
    public String getAddNewProductForm(@ModelAttribute("newProduct") Product newProduct) {
        return "addProduct";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String processAddNewProductForm(@ModelAttribute("newProduct") @Valid Product productToBeAdded, BindingResult result, HttpServletRequest request) {
        String[] suppressedFields = result.getSuppressedFields();
        if (result.hasErrors()) {
            return "addProduct";
        }
        if (suppressedFields.length > 0) {
            throw new RuntimeException("Próba wiązania niedozwolonych pól: " + StringUtils.arrayToCommaDelimitedString(suppressedFields));
        }
        MultipartFile productImage = productToBeAdded.getProductImage();
        String rootDirectory = request.getSession().getServletContext().getRealPath("/");
        if (productImage != null && !productImage.isEmpty()) {
            try {
                productImage.transferTo(new File(rootDirectory + "resources" + File.separator + "images" + File.separator + productToBeAdded.getProductId() + ".jpg"));
            } catch (Exception e) {
                throw new RuntimeException("Niepowodzenie podczas próby zapisu obrazka produktu", e);
            }
        }

        productService.addProduct(productToBeAdded);
        return "redirect:/products";
    }

    @InitBinder
    public void initialiseBinder(WebDataBinder binder) {
        binder.setDisallowedFields("unitsInOrder", "discontinued");
        binder.setValidator(unitsInStockValidator);
    }

    @ExceptionHandler(ProductNoFoundException.class)
    public ModelAndView handleError(HttpServletRequest request, ProductNoFoundException exception) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("invalidProductId", exception.getProductId());
        mav.addObject("exception", exception);
        mav.addObject("url", request.getRequestURL() + "?" + request.getQueryString());
        mav.setViewName("productNotFound");
        return mav;
    }

    @RequestMapping("/invalidPromoCode")
    public String invaliPromoCode() {
        return "invalidPromoCode";
    }
}
