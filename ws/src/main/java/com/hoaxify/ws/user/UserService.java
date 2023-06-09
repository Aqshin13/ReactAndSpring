package com.hoaxify.ws.user;

import com.hoaxify.ws.error.NotFoundException;
import com.hoaxify.ws.file.FileService;
import com.hoaxify.ws.hoax.HoaxService;
import com.hoaxify.ws.user.vm.UserUpdateVM;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Base64;

@Service
public class UserService {


    private UserRepository userRepository;

//    HoaxService hoaxService;

    private PasswordEncoder passwordEncoder;
    private FileService fileService;


    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, FileService fileService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.fileService = fileService;

    }
//    @Autowired
//    public void setHoaxService(HoaxService hoaxService) {
//        this.hoaxService = hoaxService;
//    }
    public void save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public void deleteUser(String username) {
//        hoaxService.deleteHoaxesOfUser(username);
//        User inDB = userRepository.findByUsername(username);
//        userRepository.delete(inDB);
//        userRepository.deleteByUsername(username);

        User inDB = userRepository.findByUsername(username);
        fileService.deleteAllStoredFilesForUser(inDB);
        userRepository.delete(inDB);
    }

    public Page<User> getUsers(Pageable page, User user) {
        if (user != null) {
            return userRepository.findByUsernameNot(user.getUsername(), page);
//            username olmayan datalari cekir
        }
        return userRepository.findAll(page);
    }


    public User getByUsername(String username) {
        User inDB = userRepository.findByUsername(username);
        if (inDB == null) {
            throw new NotFoundException();
        }
        return inDB;
    }

    @SneakyThrows
    public User updateUser(String username, UserUpdateVM updatedUser) {
        User inDB = getByUsername(username);
        inDB.setDisplayName(updatedUser.getDisplayName());
        if (updatedUser.getImage() != null) {
            String oldImageName = inDB.getImage();

            try {
                String storedFileName = fileService.writeBase64EncodedStringToFile(updatedUser.getImage());
                inDB.setImage(storedFileName);
            } catch (IOException e) {
                e.printStackTrace();
            }
            fileService.deleteProfileImage(oldImageName);
        }

        return userRepository.save(inDB);
    }
}





