package users.rest.config;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import users.rest.model.dto.UserDTO;
import users.rest.model.entity.UserEntity;

@Configuration
public class AppConfig {
    @Bean
    public ModelMapper modelMapper() {

        final ModelMapper modelMapper = new ModelMapper();

        Converter<UserEntity, UserDTO> userEntityUserDTOConverter = new Converter<UserEntity, UserDTO>() {
            @Override
            public UserDTO convert(MappingContext<UserEntity, UserDTO> context) {
                UserEntity source = context.getSource();
                UserDTO destination = context.getDestination();

                return destination;
            }
        };

        return modelMapper;
    }
}
