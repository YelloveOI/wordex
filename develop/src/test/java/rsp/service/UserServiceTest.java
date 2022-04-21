package rsp.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import rsp.environment.Generator;
import rsp.model.User;
import rsp.repo.UserRepo;
import rsp.service.interfaces.UserService;


@SpringBootTest
public class UserServiceTest {

    private final UserRepo repo;

    @Autowired
    private UserService sut;


    @Autowired
    public UserServiceTest(UserRepo repo) {
        this.repo = repo;
    }

    @Test
    public void getAllUsers_getsOneUser_Test() {  // Failed to resolve parameter [rsp.repo.UserRepo repo]
        final User user = Generator.generateRandomUser();
        repo.save(user);

        Integer result = sut.findAll().size();

        Assertions.assertEquals(1, result);
    }
}
