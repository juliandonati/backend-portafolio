package com.juliandonati.backendPortafolio.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.cloudinary.*;
import com.cloudinary.utils.ObjectUtils;
import java.util.Map;

@Configuration
public class CloudinaryConfig {
    @Bean
    public Cloudinary cloudinary(
            @Value("${CLOUDINARY_URL}")
            String cloudinaryUrl
    ){
        return new Cloudinary(cloudinaryUrl);
    }
}
