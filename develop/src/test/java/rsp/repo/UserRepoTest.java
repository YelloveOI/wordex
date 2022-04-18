package rsp.repo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import rsp.environment.Generator;
import rsp.model.User;


@SpringBootTest
public class UserRepoTest {

    private final UserRepo repo;


    @Autowired
    public UserRepoTest(UserRepo repo) {
        this.repo = repo;
    }

    @Test
    public void getAllUsers_getsOneUser_Test() {  // required a bean named 'entityManagerFactory'
        final User user = Generator.generateRandomUser();
        repo.save(user);

        Integer result = repo.getAllUsers().size();

        Assertions.assertEquals(1, result);
    }
}
