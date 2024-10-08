package com.product.server.koi_control_application.service;


import com.product.server.koi_control_application.custom_exception.AlreadyExistedException;
import com.product.server.koi_control_application.custom_exception.NotFoundException;
import com.product.server.koi_control_application.model.UserPackage;
import com.product.server.koi_control_application.model.UserRole;
import com.product.server.koi_control_application.model.Users;
import com.product.server.koi_control_application.pojo.request.UserRegister;
import com.product.server.koi_control_application.repository.UsersRepository;
import com.product.server.koi_control_application.service_interface.IImageService;
import com.product.server.koi_control_application.service_interface.IPackageService;
import com.product.server.koi_control_application.service_interface.IUserService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {
    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailServiceImpl emailService;
    private final IImageService imageService;
    private final IPackageService packageService;

    @Override
    public Users saveUser(UserRegister register) {
        try {

            Users user = Users.builder()
                    .username(register.getUsername())
                    .email(register.getEmail())
                    .roles(new HashSet<>())
                    .build();


            user.setAvatarUrl(imageService.getDefaultImage());
            //Create user
            user.setPassword(passwordEncoder.encode(register.getPassword()));

            user.getRoles().add(new UserRole(register.getRole().getValue()));
            Users savedUser = usersRepository.save(user);

            // Send email to user
            userRegisterMail(user.getEmail(), savedUser);

            savedUser.setAUserPackage(packageService.getPackageByDefault());
            return savedUser;
        } catch (DataIntegrityViolationException ex) {
            if (ex.getMessage().contains("users_email_unique")) {
                throw new AlreadyExistedException("Email already exists: " + register.getEmail());
            } else if (ex.getMessage().contains("users_username_unique")) {
                throw new AlreadyExistedException("Username already exists: " + register.getUsername());
            }
        }
        throw new DataIntegrityViolationException("Error occurred while saving user: user account already exists");
    }

    @Override
    public void userRegisterMail(String email, Users savedUser) {
        String verificationLink = "https://koi-controls-e5hxekcpd0cmgjg2.eastasia-01.azurewebsites.net/api/users/verify/email/" + email;
        String emailBody = "Your account has been created successfully. Please verify your email to activate your account by clicking the following link: " + verificationLink;
        emailService.sendMail(savedUser.getEmail(), "Welcome to KOI Control Application", emailBody);
    }

    @Override
    public Users getUser(int id) {
        return usersRepository.fetchUsersById(id).orElseThrow(() -> new NotFoundException("No user found with id: " + id));
    }

    @Override
    public Users getUsersByEmail(String email) {
        return usersRepository.findByEmail(email).orElseThrow(() -> new NotFoundException(email));
    }

    @Override
    public Users getUsersByUsername(String username) {
        return usersRepository.findByUsername(username).orElse(null);
    }

    @Override
    public Users userLogin(String email, String password) {
        return usersRepository.findByEmailAndPassword(email, password).orElseThrow(() -> new NotFoundException(email));
    }

    @Override
    public Page<Users> getUsers(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return usersRepository.findAll(pageable);
    }

    @Override
    public List<Users> getUsers() {
        return usersRepository.findAll();
    }

    @Override
    public void deleteUser(int id) {
        Users user = getUser(id);
        if (user == null) {
            throw new NotFoundException(String.valueOf(id));
        }
        user.removeRole();
        usersRepository.delete(user);
    }


    @Override
    public void resetPassword(String email) {
        Users user = usersRepository.findByEmail(email).orElseThrow(() -> new NotFoundException(email));
        usersRepository.save(user);
    }

    @Override
    public String generateNewPassword() {
        return RandomStringUtils.randomAlphanumeric(12);
    }

    @Override
    public void updatePassword(String email, String newPassword) {
        Users user = usersRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User not found with email: " + email));

        user.setPassword(passwordEncoder.encode(newPassword));
        usersRepository.save(user);
    }

    @Override
    public Users updateUser(int id, Users rUser, MultipartFile file) throws IOException {
        Users user = usersRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found with id: " + rUser.getUsername()));

        if (file != null) {
            String filename = imageService.updateImage(user.getAvatarUrl(), file);
            user.setAvatarUrl(filename);
        }

        if (rUser.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(rUser.getPassword()));
        }
        if (rUser.getUsername() != null) {
            user.setUsername(rUser.getUsername());
        }
        if (rUser.getEmail() != null) {
            user.setEmail(rUser.getEmail());
        }
        if (rUser.getAddress() != null) {
            user.setAddress(rUser.getAddress());
        }
        if (rUser.getPhoneNumber() != null) {
            user.setPhoneNumber(rUser.getPhoneNumber());
        }
        return usersRepository.save(user);
    }

    @Override
    public Users updateUser(Users rUser) {
        return usersRepository.save(rUser);
    }

    @Override
    public Users addPackage(int userId, UserPackage userPackage) {
        Users user = getUser(userId);
         user.setAUserPackage(userPackage);
        return usersRepository.save(user);
    }
}
