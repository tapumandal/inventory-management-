package com.tapumandal.ims.service.implementation;

import com.tapumandal.ims.entity.Company;
import com.tapumandal.ims.entity.User;
import com.tapumandal.ims.entity.User;
import com.tapumandal.ims.entity.dto.UserDto;
import com.tapumandal.ims.repository.UserRepository;
import com.tapumandal.ims.repository.UserRepository;
import com.tapumandal.ims.service.CompanyService;
import com.tapumandal.ims.service.UserService;
import com.tapumandal.ims.util.MyPagenation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    UserRepository userRepository;

    @Autowired
    CompanyService companyService;

    private User user;

    public UserServiceImpl(){}

    public UserServiceImpl(User user){
        this.user = user;
    }


    @Override
    public User createUser(UserDto userDto) {

        User u;

        if(userDto.getCompany().getId() == 0){
            Company company = new Company();
            company.setId(companyService.create(userDto.getCompany()).getId());
            u = new User(userDto);
            u.setCompany(company);
            u.setRole("ADMIN");
        }else{
            u = new User(userDto);
        }

        Optional<User> user;
        try{
            int userId = userRepository.create(u);
            user = Optional.ofNullable(userRepository.getById(userId));
        }catch (Exception e){
            return null;
        }

        if(user.isPresent()){
            return user.get();
        }else{
            return null;
        }
    }


    @Override
    public User create(UserDto u) {
        return null;
    }

    @Override
    public User update(UserDto userDto) {

        User u = new User(userDto);

        Optional<User> user;
        try{
            int userId = userRepository.update(u);
            user = Optional.ofNullable(userRepository.getById(userId));
        }catch (Exception e){
            return null;
        }

        if(user.isPresent()){
            return user.get();
        }else{
            return null;
        }

    }

    @Override
    public List<User> getAll(Pageable pageable) {
        Optional<List<User>> products = Optional.ofNullable(userRepository.getAll(pageable));

        if(products.isPresent()){
            return products.get();
        }else{
            return null;
        }
    }

    @Override
    public User getById(int id) {

        Optional<User> user = Optional.ofNullable(userRepository.getById(id));

        if(user.isPresent()){
            return user.get();
        }else{
            return null;
        }
    }

    @Override
    public boolean deleteById(int id) {
        try {
            return userRepository.delete(id);
        }catch (Exception ex){
            return false;
        }
    }

    @Override
    public User getByValue(String kye, String value) {
        return null;
    }

    @Override
    public List<User> getAllByValue(String kye, String value) {
        return null;
    }

    @Override
    public boolean isActive(int id) {
        return user.isActive();
    }

    @Override
    public boolean isDeleted(int id) {
        return user.isDeleted();
    }

    @Override
    public MyPagenation getPageable(Pageable pageable) {
        return userRepository.getPageable(pageable);
    }

    public boolean isUserExist(String userName){
        return userRepository.isUserExist(userName);
    }



}
