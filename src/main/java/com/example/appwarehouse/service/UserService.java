package com.example.appwarehouse.service;

import com.example.appwarehouse.entity.User;
import com.example.appwarehouse.entity.WareHouse;
import com.example.appwarehouse.repository.SupplierRepository;
import com.example.appwarehouse.repository.UserRepository;
import com.example.appwarehouse.repository.WareHouseRepository;
import com.example.appwarehouse.transfer.Result;
import com.example.appwarehouse.transfer.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    SupplierRepository supplierRepository;

    @Autowired
    WareHouseRepository wareHouseRepository;

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User getUser(Integer id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            return optionalUser.get();
        }
        return null;
    }

    public Result addingUser(UserDTO userDTO, boolean edit, Integer id) {
        User user = new User();

        if (!edit && userRepository.existsByPhoneNumber(userDTO.getPhoneNumber()) ||
                edit && userRepository.existsByIdIsNotAndPhoneNumber(id, userDTO.getPhoneNumber())) {
            return new Result("Bunday telefon nomer bor", false);
        }

        if (!edit && userRepository.existsByPhoneNumberAndUserName(userDTO.getPhoneNumber(), user.getUserName()) ||
                edit && userRepository.existsByIdIsNotAndPhoneNumberAndUserName(id, userDTO.getPhoneNumber(), userDTO.getUserName())) {
            return new Result("Bunday telefon nomerli user bor", false);
        }

        if (!edit && userRepository.existsByUserName(userDTO.getUserName()) ||
                edit && userRepository.existsByIdIsNotAndUserName(id, userDTO.getUserName())) {
            return new Result("Bunday username bor", false);
        }

        Set<WareHouse> wareHouses = new HashSet<>();
        for (Integer wareHousesId : userDTO.getWareHousesIds()) {
            Optional<WareHouse> optionalWareHouse = wareHouseRepository.findById(wareHousesId);

            if (optionalWareHouse.isPresent()) {
                //Ombor active bo'lsa userga qo'shiladi
                if (optionalWareHouse.get().isActive()) {
                    wareHouses.add(optionalWareHouse.get());
                } else
                    return new Result("Active bo'lmagan ombor kiritildi", false);
            } else
                return new Result("Bunday ombor yo'q", false);
        }

        user.setActive(userDTO.isActive());
        user.setUserName(userDTO.getUserName());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setPassword(userDTO.getPassword());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        user.setWareHouses(wareHouses);
        return new Result(true, user);
    }

    public Result addUser(UserDTO userDTO) {
        Result result = addingUser(userDTO, false, null);
        if (result.isSuccess()) {
            User user = (User) result.getObject();
            user.setCode(generateUserCode());
            userRepository.save(user);
            return new Result("User qo'shildi", true);
        }
        return result;
    }

    public Result editUser(Integer id, UserDTO userDTO) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            Result result = addingUser(userDTO, true, id);
            if (result.isSuccess()) {
                User user = (User) result.getObject();
                User editUser = optionalUser.get();
                editUser.setActive(user.isActive());
                editUser.setUserName(user.getUserName());
                editUser.setFirstName(user.getFirstName());
                editUser.setLastName(user.getLastName());
                editUser.setPassword(user.getPassword());
                editUser.setPhoneNumber(user.getPhoneNumber());
                editUser.setWareHouses(user.getWareHouses());
                userRepository.save(editUser);
                return new Result("User o'zgartirildi", true);
            }
            return result;
        }
        return new Result("User topilmadi", false);
    }

    public String generateUserCode() {
        List<User> users = getUsers();
        int index = users.size();
        if (index == 0)
            return "1";
        User user = users.get(index - 1);
        index = user.getId() + 1;
        return Integer.toString(index);
    }

    public Result deleteUser(Integer id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            userRepository.delete(optionalUser.get());
            return new Result("User o'chirildi", true);
        }
        return new Result("User topilmadi", false);
    }
}
