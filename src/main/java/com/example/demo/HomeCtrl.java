package com.example.demo;

import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;
import java.util.Map;


@Controller
public class HomeCtrl {

    @Autowired
    UserRepository userRepository;

    @Autowired
    LibroRepository libroRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    CloudinaryConfig cloudc;

    @RequestMapping("/")
    public String listAll(Model model){
        model.addAttribute("libros", libroRepository.findAll());
        return "index";
    }
    @RequestMapping("/listLibro")
    public String listLibro(Model model){
        model.addAttribute("libros", libroRepository.findAll());//has to match what is called in the html file
        return "listLibros";
    }
    @RequestMapping("/detailLibro/{id}")
    public String detailLibro(@PathVariable("id") long id, Model model) {
        Libro libro = libroRepository.findById(id).get();
        model.addAttribute("libro", libro);
        return "detailLibro";
    }
    @GetMapping("/addLibro")
    public String addLibro(Model model){
        model.addAttribute("libro", new Libro());
        model.addAttribute("categorys", categoryRepository.findAll());
        return "addLibro";
    }
    @PostMapping("/processLibro")
    public String processLibro(@ModelAttribute Libro libro,
                               @RequestParam("file")MultipartFile file) {
        if (file.isEmpty()) {
            return "redirect:/addLibro";
        }
        try {
            Map uploadResult = cloudc.upload(file.getBytes(),
                    ObjectUtils.asMap("resourcetype", "auto"));
            libro.setImage(uploadResult.get("url").toString());
            libroRepository.save(libro);
        } catch (IOException e) {
            e.printStackTrace();
            return "redirect:/addLibro";
        }
        return "redirect:/";
    }
    @RequestMapping("/updateLibro/{id}")
    public String updateLibro(@PathVariable("id") long id, Model model){
        Libro libro = libroRepository.findById(id).get();
        model.addAttribute("libro", libro);
        model.addAttribute("categorys", categoryRepository.findAll());
        return "addLibro";
    }

    //Category Table

    @RequestMapping("/listCategory")
    public String listCategory(Model model){
        model.addAttribute("categorys", categoryRepository.findAll());
        return "listCategory";
    }

    @RequestMapping("/updateCategory/{id}")
    public String updateCategory(@PathVariable("id") long id, Model model){
        model.addAttribute("category", categoryRepository.findById(id));
        return "addCategory";
    }
    @GetMapping("/addCategory")
    public String addCategory(Model model){
        model.addAttribute("category", new Category());
        return "addCategory";
    }
    @PostMapping("/processCategory")
    public String processCategory(@ModelAttribute Category category){
        categoryRepository.save(category);
        return "redirect:/";
    }
    @RequestMapping("/detailCategory/{id}")
    public String detailUser(@PathVariable("id") long id, Model model) {
        Category category = categoryRepository.findById(id).get();
        model.addAttribute("category", category);
        return "detailCategory";
    }
    //Add registration
    @GetMapping("/register")
    public String showRegistrationPage(Model model){
        model.addAttribute("user", new User());
        return "register";
    }
    @PostMapping("/processregister")
    public String processRegistrationPage(
            @Valid @ModelAttribute("user") User user,
            BindingResult result, Model model){
        if (result.hasErrors()){
            user.clearPasswords();
            model.addAttribute("user", user);
            return "register";
        }
        else {
            model.addAttribute("user", user);
            model.addAttribute("message","New user account created");

            user.setEnabled(true);
            userRepository.save(user);

            Role role = new Role(user.getUsername(), "ROLE_USER");
            roleRepository.save(role);
        }
        return "redirect:/";
    }
    @RequestMapping("/secure")
    public String secure(Principal principal, Model model){
        String username = principal.getName();
        model.addAttribute("users", userRepository.findAll());
        return "secure";
    }
    //Add login
    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping("/admin")
    public String admin() {
        return "admin";
    }

}
