package federicopignatelli.crowdfundingplatform_backend.cloudinaryconfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.cloudinary.Cloudinary;

import java.util.HashMap;
import java.util.Map;



    @Configuration
    public class Cloudinaryconfig {
        @Bean
        public Cloudinary cloudinaryUploader(
                @Value("${cloudinary.name}") String name,
                @Value("${cloudinary.apikey}") String apikey,
                @Value("${cloudinary.secret}") String secret
        ) {
            Map<String, String> config = new HashMap<>();
            config.put("cloud_name", name);
            config.put("api_key", apikey);
            config.put("api_secret", secret);
            return new Cloudinary(config);
        }
    }

