package com.example.demo;

import javax.sql.DataSource;
import javax.swing.text.html.parser.Entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.common.contexts.DatabaseContext;
import com.example.demo.common.utils.DatabaseUtil;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@SpringBootApplication
public class GameServerApplication {

    @RestController
    class HelloworldController {
        @GetMapping("/")
        String hello() {
            return DatabaseContext.getDbType().getKeyword();
        }
    }

	public static void main(String[] args) {
		SpringApplication.run(GameServerApplication.class, args);
	}

}
