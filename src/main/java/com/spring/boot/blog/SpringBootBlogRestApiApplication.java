package com.spring.boot.blog;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
@OpenAPIDefinition(
        info = @Info(
                title = "Blog REST API",
                version = "v12.0",
                description = "Enjoy Blog REST API",
                contact = @Contact(
                        name = "Mahmoud Abdalla",
                        url = "https://www.blog.com",
                        email = "mahmoud-elbrer@example.com"
                ),
                license = @License(
                        name = "Apache 2.0",
                        url = "http://www.apache.org/licenses/LICENSE-2.0.html"
                )
        ),
        externalDocs = @ExternalDocumentation(
                description = "Find more info about our API",
                url = "https://www.blog.com/swagger-ui.html"
        )

)
public class SpringBootBlogRestApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootBlogRestApiApplication.class, args);
    }

}
