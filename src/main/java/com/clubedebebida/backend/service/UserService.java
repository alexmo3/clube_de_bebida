package com.clubedebebida.backend.service;

import com.clubedebebida.backend.controller.exception.ControllerNotFoundException;
import com.clubedebebida.backend.model.User;
import org.springframework.stereotype.Service;
import com.clubedebebida.backend.dto.UserDTO;
import com.clubedebebida.backend.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Service
   //User getUserById(Long id);
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public Page<UserDTO> findAll(Pageable pageable){
        Page<User> users = userRepository.findAll(pageable);

        return users.map(this::toDTO);
    }

    public UserDTO findById(Long id){
        User user = userRepository.findById(id).orElseThrow(()-> new ControllerNotFoundException("Usuário não encontrado"));
        return toDTO(user);
    }

    public UserDTO save(UserDTO UserDTO){
        User User = toEntity(UserDTO);
        User = userRepository.save(User);

        return toDTO(User);
    }

    public UserDTO update(Long id, UserDTO UserDTO){
        try {
            User user = userRepository.getReferenceById(id);
            user.setName(UserDTO.name());
            user.setEmail(UserDTO.email());
            user.setPhone(UserDTO.phone());
            user.setPhoto(UserDTO.photo());
            user.setBirthday(UserDTO.birthday());
            user.setPassword(UserDTO.password());
            user.setCreatedAt(UserDTO.createdAt());
            user.setUpdatedAt(UserDTO.updatedAt());
            user = userRepository.save(user);
            return toDTO(user);

        } catch (EntityNotFoundException e) {
            throw new ControllerNotFoundException("Usuário não encontrado");
        }
    }

    public void delete(Long id){
        userRepository.deleteById(id);
    }

    private UserDTO toDTO(User user) {
        return new UserDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getPhone(),
                user.getPhoto(),
                user.getBirthday(),
                user.getPassword(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }

    private User toEntity(UserDTO userDTO){
        return new User(
                userDTO.id(),
                userDTO.name(),
                userDTO.email(),
                userDTO.phone(),
                userDTO.photo(),
                userDTO.birthday(),
                userDTO.password(),
                userDTO.createdAt(),
                userDTO.updatedAt()
        );
    }
}


