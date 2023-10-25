package com.clubedebebida.backend.service;

import com.clubedebebida.backend.controller.exception.ControllerNotFoundException;
import com.clubedebebida.backend.dto.UserLoginRequestDTO;
import com.clubedebebida.backend.dto.UserPasswordDTO;
import com.clubedebebida.backend.model.User;
import org.springframework.stereotype.Service;
import com.clubedebebida.backend.dto.UserDTO;
import com.clubedebebida.backend.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Page<UserDTO> findAll(Pageable pageable) {
        Page<User> users = userRepository.findAll(pageable);

        return users.map(this::toDTO);
    }

    public UserDTO findById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ControllerNotFoundException("Usuário não encontrado"));
        return toDTO(user);
    }

    public UserDTO save(UserDTO userDTO) {
        User user = toEntity(userDTO);
        user = userRepository.save(user);

        return toDTO(user);
    }

    public UserLoginRequestDTO login(String email, String password) {
        try {
            User user = userRepository.findByEmail(email);
            if (user != null && user.getPassword().equals(password)) {
                return new UserLoginRequestDTO(user.getId(), user.getName(), user.getEmail());
            } else {
                return null; // Credenciais inválidas
            }
        } catch (EntityNotFoundException e) {
            throw new ControllerNotFoundException("Usuário não encontrado");
        }
    }

    public UserPasswordDTO changePassword(Long id, UserPasswordDTO userPasswordDTO) {
        try {
            User user = userRepository.findById(id).orElseThrow(() -> new ControllerNotFoundException("Usuário não encontrado"));
            if (user != null && user.getPassword().equals(userPasswordDTO.password())) {
                user.setPassword(userPasswordDTO.newPassword());
                user = userRepository.save(user);
                return new UserPasswordDTO(user.getId(), user.getEmail(), user.getPassword(),"");
            } else {
                return null; // Credenciais inválidas
            }
        } catch (EntityNotFoundException e) {
            throw new ControllerNotFoundException("Usuário não encontrado");
        }
    }

    public UserDTO update(Long id, UserDTO UserDTO) {
        try {
            User user = userRepository.getReferenceById(id);
            user.setName(UserDTO.name());
            user.setEmail(UserDTO.email());
            user.setPhone(UserDTO.phone());
            user.setPhoto(UserDTO.photo());
            user.setBirthday(UserDTO.birthday());
            user.setPassword(UserDTO.password());
            user.setUpdatedAt(LocalDateTime.now());
            user = userRepository.save(user);
            return toDTO(user);

        } catch (EntityNotFoundException e) {
            throw new ControllerNotFoundException("Usuário não encontrado");
        }
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    private UserDTO toDTO(User user) {
        return new UserDTO(
                user.getId(),
                user.getType(),
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

    private User toEntity(UserDTO userDTO) {
        return new User(
                userDTO.id(),
                userDTO.type(),
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


