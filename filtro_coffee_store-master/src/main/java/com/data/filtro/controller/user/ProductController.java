package com.data.filtro.controller.user;

import com.data.filtro.model.Feedback;
import com.data.filtro.model.Product;
import com.data.filtro.model.User;
import com.data.filtro.service.FeedbackService;
import com.data.filtro.service.InputService;
import com.data.filtro.service.ProductService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static com.data.filtro.service.InputService.containsAllowedCharacters;
import static com.data.filtro.service.InputService.isStringLengthLessThan50;

@Controller
@RequestMapping("/product")
public class ProductController {

    @Autowired
    ProductService productService;

    @Autowired
    FeedbackService feedbackService;

    @Autowired
    InputService inputService;

    private String errorMessage;
    private String csrfToken;


    @GetMapping
    public String product(Model model) {
        String _csrfToken = generateRandomString();
        csrfToken = _csrfToken;
        System.out.println("csrfToken:" + _csrfToken);
        model.addAttribute("_csrfToken", _csrfToken);
        return "user/product";
    }

    @GetMapping("/{id}")
    public String product(@PathVariable Integer id, HttpSession session, Model model) {
        String _csrfToken = generateRandomString();
        csrfToken = _csrfToken;
        System.out.println("csrfToken:" + _csrfToken);
        model.addAttribute("_csrfToken", _csrfToken);
        int currentProductId = id;
        long maxProductId = productService.countAll();
        List<Feedback> feedbackList = feedbackService.getAllFeedBackByProductId(id);
        int t1 = 13;
        long t2 = 24;
        Product product = productService.getProductById(id);
        List<Product> productList = productService.getTop4ProductsByFlavor(product.getFlavor().getId(), currentProductId);
        model.addAttribute("product", product);
        model.addAttribute("products", productList);
        model.addAttribute("currentProductId", currentProductId);
        model.addAttribute("maxProductId", maxProductId);
        model.addAttribute("feedbackList", feedbackList);
        model.addAttribute("_csrfToken", _csrfToken);
        productList.forEach(product1 -> System.out.println(product1.getProductName()));
        if (errorMessage != null){
            model.addAttribute("errorMessage", errorMessage);
            System.out.println(errorMessage);
        }
        errorMessage = null;
        return "user/product";
    }


    @PostMapping("/{id}/feedback")
    public String feedback(@ModelAttribute Feedback feedback, @PathVariable Integer id, @RequestParam("_csrfParameterName") String csrfTokenForm, HttpSession session, Model model) {
        feedback.setUser(null);
//        if(!inputService.isValidComment(feedback.getContent())){
//            String message = "Bình luận chỉ được chứa các ký tự thường và dấu chấm, dấu phẩy, (), @, ! và " +
//                    "độ dài dưới 50 ký tự";
//            model.addAttribute("errorMessage", message);
//            errorMessage = message;
//            return "redirect:/product/" + id;
//        }
        System.out.println("Sau khi nhan nut dang ky thi csrf token la: " + csrfToken);
        if (!csrfTokenForm.equals(csrfToken)) {
            String message = "Mã token không đúng";
            model.addAttribute("errorMessage", message);
            return "redirect:/product/" + id;
        }
        User user = (User) session.getAttribute("user");
        feedback.setUser(user);
        feedback.setProduct(productService.getProductById(id));
        feedbackService.createFeedback(feedback);
        return "redirect:/product/" + id;
    }
    public String generateRandomString() {
        return UUID.randomUUID().toString();
    }
}
