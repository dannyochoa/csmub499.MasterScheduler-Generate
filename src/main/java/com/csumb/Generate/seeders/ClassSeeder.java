package com.csumb.Generate.seeders;

import com.csumb.Administrative.repositotries.IClassRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ClassSeeder implements CommandLineRunner {
    @Autowired
    private IClassRepository classRepository;

    @Override
    public void run(String... args){
        List<Class> classes = new ArrayList<>();

        classRepository.deleteAll();
        classRepository.saveAll(classes);
    }

}