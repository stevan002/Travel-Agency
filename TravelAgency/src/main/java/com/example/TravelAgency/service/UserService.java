package com.example.TravelAgency.service;

import com.example.TravelAgency.model.User;
import com.example.TravelAgency.model.dto.user.ChangePasswordDTO;
import com.example.TravelAgency.model.dto.user.UpdateUserDTO;
import com.example.TravelAgency.model.dto.user.UserProfileDTO;
import com.example.TravelAgency.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserProfileDTO getUserProfile(String username){
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found."));

        return new UserProfileDTO(
                user.getFirstName(),
                user.getLastName(),
                user.getUsername(),
                user.getAddress(),
                user.getDateOfBirth(),
                user.getJMBG(),
                user.getPhoneNumber(),
                user.getDateOfRegistration(),
                user.getRole()
        );
    }

    public void changePassword(String username, ChangePasswordDTO changePasswordDTO){
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found."));

        if (!passwordEncoder.matches(changePasswordDTO.getOldPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Old password is incorrect.");
        }

        if (!changePasswordDTO.getNewPassword().equals(changePasswordDTO.getNewPassword1())) {
            throw new IllegalArgumentException("New passwords don't match.");
        }

        String hashedNewPassword = passwordEncoder.encode(changePasswordDTO.getNewPassword());
        user.setPassword(hashedNewPassword);
        userRepository.save(user);
    }

    public void updateProfile(String username, UpdateUserDTO updateUserDTO){
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found."));

        if (updateUserDTO.getFirstName() != null) {
            user.setFirstName(updateUserDTO.getFirstName());
        }
        if (updateUserDTO.getLastName() != null) {
            user.setLastName(updateUserDTO.getLastName());
        }
        if (updateUserDTO.getUsername() != null) {
            user.setUsername(updateUserDTO.getUsername());
        }
        if (updateUserDTO.getAddress() != null) {
            user.setAddress(updateUserDTO.getAddress());
        }
        if (updateUserDTO.getDateOfBirth() != null) {
            user.setDateOfBirth(updateUserDTO.getDateOfBirth());
        }
        if (updateUserDTO.getJMBG() != null) {
            user.setJMBG(updateUserDTO.getJMBG());
        }
        if (updateUserDTO.getPhoneNumber() != null) {
            user.setPhoneNumber(updateUserDTO.getPhoneNumber());
        }

        userRepository.save(user);
    }
}
