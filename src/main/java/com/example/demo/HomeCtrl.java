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
        return "addLibro";
    }

    //Users

    @RequestMapping("/listUser")
    public String listUser(Model model){
        model.addAttribute("users", userRepository.findAll());
        return "listUser";
    }

    @RequestMapping("/updateUsers/{id}")
    public String updateUser(@PathVariable("id") long id, Model model){
        model.addAttribute("user", userRepository.findById(id));
        model.addAttribute("libros", libroRepository.findAll());
        return "addUsers";
    }
    @GetMapping("/addUsers")
    public String addUser(Model model){
        model.addAttribute("user", new User());
        model.addAttribute("libros", libroRepository.findAll());
        return "addUser";
    }
    @PostMapping("/processUser")
    public String processUser(@ModelAttribute User user,
                                  @RequestParam("file")MultipartFile file) {
        if(file.isEmpty()){
            return "addEmployee";
        }
        try {
            Map uploadResult = cloudc.upload(file.getBytes(),
                    ObjectUtils.asMap("resourcetype", "auto"));
            user.setHeadshot(uploadResult.get("url").toString());
            userRepository.save(user);

            } catch (IOException e) {
            e.printStackTrace();
            return "redirect:/addUser";
        }
//        userRepository.save(user);
        return "redirect:/";
    }
    @RequestMapping("/detailUser/{id}")
    public String detailUser(@PathVariable("id") long id, Model model) {
        User user = userRepository.findById(id).get();
        model.addAttribute("user", user);
        return "detaiUser";
    }




//    @RequestMapping("/deleteEmployee/{id}")
//    public String deleteEmployee(@PathVariable("id") long id){
//        employeeRepository.deleteById(id);
//        return "redirect:/";
//
//    }
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

//    @RequestMapping("/")
//    public String index() {
//
//        return "index";
//    }
    @RequestMapping("/login")
    public String login() {
        return "login";
    }
//    @RequestMapping("/logout")
//    public String logout() {
//        return "redirect:/login?logout=true";
//    }
    @RequestMapping("/admin")
    public String admin() {
        return "admin";
    }


}
