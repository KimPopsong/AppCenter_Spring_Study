package serverStudy.first.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import serverStudy.first.domain.User;
import serverStudy.first.model.UserSaveRequest;
import serverStudy.first.repository.UserRepository;

import java.util.List;

@RestController                 // restful api 만들때 사용하는 annotation
@RequiredArgsConstructor        // 필요한 인자를 넣어주는 생성자
public class UserController {

    private final UserRepository userRepository;

    @PostMapping("/users")
    public User save(@RequestBody UserSaveRequest userSaveRequest) {        //JSON 타입을 받을 때 @RequestBody를 사용(유의)

        // User 객체를 메서드에서 생성하고 리턴받는다.
        User user = User.createUser(userSaveRequest.getName(), userSaveRequest.getEmail(),
                userSaveRequest.getAge());

        User savedUser = userRepository.save(user);

        return savedUser;
    }

    // 회원 전체 조회
    @GetMapping("/users")
    public List<User> findAll() {
        List<User> users = userRepository.findAll();
        return users;
    }

    // 회원 한명 조회
    @GetMapping("/users/{userId}")
    public User findById(@PathVariable Long userId) {
        User user = userRepository.findById(userId);
        return user;
    }

    // 회원 삭제
    @DeleteMapping("/users/{userId}")
    public void deleteById(@PathVariable Long userId) {
        userRepository.deleteById(userId);
    }

}