package com.example.userregistrationandlogin.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
public class InMemoryCrud {

    private Map<String, TempUser> userMap = new HashMap<>();

    public InMemoryCrud(){
//    Map<String, TempUser> userMap = new HashMap<String,TempUser>();
    userMap.put("vishek", new TempUser("vishek","vishekpass", "vishekpatel033@gmail.com","nice man!"));
    userMap.put("virat", new TempUser("virat","viratpass", "viratkohli033@gmail.com","king kohli!"));
    userMap.put("dhoni", new TempUser("dhoni","dhonipass", "dhonibhai033@gmail.com","captain cool!"));
    userMap.put("rohit", new TempUser("rohit","rohitpass", "rohitpass@gmail.com","hitman sharma!"));
    }

    @PostMapping("/register")
    public ResponseEntity<TempUser> registerUser(@RequestBody TempUser user){
        TempUser newUser = new TempUser(user.getUser(), user.getPassword(),user.getEmail(),user.getBio());
        userMap.put(user.getUser(),newUser);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED );
    }

    @GetMapping("/getAllUser")
    public ResponseEntity<List<TempUser>> getAllUser(){
//        List<TempUser> users = Arrays.asList(userMap.values());
        List<TempUser> users = new ArrayList<>(userMap.values().stream().collect(Collectors.toList()));
        return new ResponseEntity<>(users,HttpStatus.OK);
    }

    @PutMapping("updateUser")
    public ResponseEntity<String> updateUser(@RequestBody TempUser user, @RequestParam("userName") String userName){
        if(!userMap.containsKey(userName)){
            return new ResponseEntity<>("User not found " , HttpStatus.NOT_FOUND);
        }
        userMap.put(userName, new TempUser(user.getUser(), user.getPassword(), user.getBio(), user.getEmail()));
        return new ResponseEntity<>("User updated ", HttpStatus.CREATED);
    }

    @DeleteMapping("deleteUser")
    public ResponseEntity<String> deleteUser(@RequestParam("userName") String userName){
        if(!userMap.containsKey(userName)){
            return new ResponseEntity<>("User not found!", HttpStatus.NOT_FOUND);
        }
        userMap.remove(userName);
        return new ResponseEntity<>("User deleted successfully!", HttpStatus.ACCEPTED);
    }

}

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
 class TempUser{
    private String user;
    private String password;
    private String email;
    private String bio;
}
