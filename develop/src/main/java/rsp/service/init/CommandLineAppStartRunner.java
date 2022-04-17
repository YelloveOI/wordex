package rsp.service.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import rsp.enums.Role;
import rsp.model.Deck;
import rsp.model.Statistics;
import rsp.model.User;
import rsp.repo.UserRepo;
import rsp.service.interfaces.UserService;

import java.util.LinkedList;

@Component
class CommandLineAppStartupRunner implements CommandLineRunner {

    @Autowired
    UserService userService;


    @Override
    public void run(String...args) throws Exception {

        try {
            userService.findByUsername("admin");

        }catch (Exception e){
            final User admin = new User();

            admin.setUsername("admin");
            admin.setPassword("1234");
            admin.addRole(Role.ADMIN);
            admin.setEmail("email@com.cz");
            admin.setDecks(new LinkedList<Deck>());
            Statistics statistics = new Statistics();
            statistics.setLearnedCards(0);
            admin.setStatistics(statistics);

            userService.createAdmin(admin);
        }


    }
}