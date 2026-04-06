package ru.ezhak.users.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ezhak.users.model.User;
import ru.ezhak.users.repository.UserRepository;

import java.util.List;

@Service
public class UserService {


   private final UserRepository userRepository;

   @Autowired
    public UserService(UserRepository userRepository) {
       this.userRepository = userRepository;
   }

   public List<User> findUsers() {
       return userRepository.findAll();
   }

   public User findUserById(long id) {
       return userRepository.findById(id).orElseGet(User::new);
   }

   public void save(User user) {
       userRepository.save(user);
   }

   public void delete(long id) {
       userRepository.deleteById(id);
   }

   public void update(long id, User user) {
       user.setId(id);
       userRepository.save(user);
   }
}
