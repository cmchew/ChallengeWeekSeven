package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CommandLineRunnerBean implements CommandLineRunner {
    @Autowired
    RoleRepository roleRepository;

    @Autowired
    LibroRepository libroRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CategoryRepository categoryRepository;


    public void run(String...args){
        User user = new User("mc", "mc@domain.com", "mc", "MC", "Raptor", true);
        Role userRole = new Role("mc", "ROLE_USER");
        userRepository.save(user);
        roleRepository.save(userRole);

        User user1 = new User("cassie", "cassie@domain", "books", "Cassie", "Chew", true);
        User admin = new User("admin", "librarian@domain.com", "admin", "KC", "Flournoy", true);
        Role adminRole1 = new Role ("admin", "ROLE_ADMIN");
        Role adminRole2 = new Role("admin", "ROLE_USER");

        userRepository.save(admin);
        roleRepository.save(adminRole1);
        roleRepository.save(adminRole2);

        Libro libro1 = new Libro();
        libro1.setIbsn("1234");
        libro1.setTitle("Java");
        libro1.setAuthor("Cassie");
        libro1.setQuantity("2");
        libro1.setYear("2020");
        libroRepository.save(libro1);//save book

//        user1.setBorrow(libro1);//assign book to user
        userRepository.save(user1);//save user

        Category category1 = new Category();
        category1.setName("Information Technology");
        categoryRepository.save(category1);//save category1

    }
}
