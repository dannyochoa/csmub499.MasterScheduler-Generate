package com.csumb.Generate.seeders;

import com.csumb.Administrative.entities.Section;
import com.csumb.Administrative.repositotries.ISectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SectionSeeder implements CommandLineRunner {
    @Autowired
    private ISectionRepository sectionRepository;

    @Override
    public void run(String... args){
        List<Section> sections = new ArrayList<>();

        sectionRepository.deleteAll();
        sectionRepository.saveAll(sections);
    }
}
