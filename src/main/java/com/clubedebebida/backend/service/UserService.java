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
    private final UserRepository UserRepository;

    @Autowired
    public UserService(UserRepository UserRepository){
        this.UserRepository = UserRepository;
    }

    public Page<UserDTO> findAll(Pageable pageable){
        Page<User> Users = UserRepository.findAll(pageable);

        return Users.map(this::toDTO);
    }

    public UserDTO findById(Long id){
        User User = UserRepository.findById(id).orElseThrow(()-> new ControllerNotFoundException("Usuário não encontrado"));
        return toDTO(User);
    }

    public UserDTO save(UserDTO UserDTO){
        User User = toEntity(UserDTO);
        User = UserRepository.save(User);

        return toDTO(User);
    }

    public UserDTO update(Long id, UserDTO UserDTO){
        try {
            User User = UserRepository.getReferenceById(id);
            User.setName(UserDTO.name());
            User.setEmail(UserDTO.email());
            User.setPhone(UserDTO.phone());
            User.setPhoto(UserDTO.photo());
            User.setBirthday(UserDTO.birthday());
            User.setPassword(UserDTO.password());
            User.setCreatedAt(UserDTO.createdAt());
            User.setUpdatedAt(UserDTO.updatedAt());
            User = UserRepository.save(User);
            return toDTO(User);

        } catch (EntityNotFoundException e) {
            throw new ControllerNotFoundException("Usuário não encontrado");
        }
    }

    public void delete(Long id){
        UserRepository.deleteById(id);
    }

    private UserDTO toDTO(User User) {
        return new UserDTO(
                User.getId(),
                User.getName(),
                User.getEmail(),
                User.getPhone(),
                User.getPhoto(),
                User.getBirthday(),
                User.getPassword(),
                User.getCreatedAt(),
                User.getUpdatedAt()
        );
    }

    private User toEntity(UserDTO UserDTO){
        return new User(
                UserDTO.id(),
                UserDTO.name(),
                UserDTO.email(),
                UserDTO.phone(),
                UserDTO.photo(),
                UserDTO.birthday(),
                UserDTO.password(),
                UserDTO.createdAt(),
                UserDTO.updatedAt()
        );
    }
}


