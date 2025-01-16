package users.rest.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import users.rest.repository.UserRepository;
import users.rest.service.UserService;
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }


}
