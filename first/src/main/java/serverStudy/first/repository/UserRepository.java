package serverStudy.first.repository;

import org.springframework.stereotype.Repository;
import serverStudy.first.domain.User;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository  // Spring Bean으로 등록
public class UserRepository {

    private static Map<Long, User> store = new HashMap<>();
    private static long sequence = 0;

    @PostConstruct
    public void init() {
        save(User.createUser("김찬희", "몰라용", 20));
        save(User.createUser("김대성", "몰라용", 20));
        save(User.createUser("박재권", "몰라용", 20));

    }

    // 회원 저장
    public User save(User user) {
        user.setId(++sequence);
        store.put(user.getId(), user);
        return user;
    }

    // 회원 한개 조회
    public User findById(Long id) {
        User user = store.get(id);
        return user;
    }

    // 회원 전체 조회
    public List<User> findAll() {
        List<User> users = new ArrayList<>(store.values());
        return users;
    }

    // 회원 삭제
    public void deleteById(Long id) {
        store.remove(id);
    }
}