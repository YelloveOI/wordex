package rsp.service.init;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import rsp.enums.Role;
import rsp.exception.NotFoundException;
import rsp.model.Deck;
import rsp.model.Statistics;
import rsp.model.User;
import rsp.service.UserServiceImpl;
import rsp.service.interfaces.UserService;

import javax.annotation.PostConstruct;
import java.util.LinkedList;

@Component
public class SystemInitializer {

    private static final Logger LOG = LoggerFactory.getLogger(SystemInitializer.class);

    /**
     * Default admin username
     */
    private static final String ADMIN_USERNAME = "admin";

    private final UserServiceImpl userService;

    private final PlatformTransactionManager txManager;

    @Autowired
    public SystemInitializer(UserServiceImpl userService,
                             PlatformTransactionManager txManager) {
        this.userService = userService;
        this.txManager = txManager;
    }

    @PostConstruct
    private void initSystem() {
        //I dont know if is it right, but it seems to work
        generateAdmin();

        /*
        TransactionTemplate txTemplate = new TransactionTemplate(txManager);
        txTemplate.execute((status) -> {
            return null;
        });
        */
    }

    /**
     * Generates an admin account if it does not already exist.
     */
    private void generateAdmin() {
        try {
            userService.findByUsername(ADMIN_USERNAME);
        }catch (NotFoundException e){
            return;
        }

        final User admin = new User();
        admin.setUsername(ADMIN_USERNAME);
        admin.setPassword("1234");
        admin.addRole(Role.ADMIN);
        admin.setEmail("email@com.cz");
        admin.setDecks(new LinkedList<Deck>());
        admin.setStatistics(new Statistics());
        LOG.info("Generated admin user with credentials " + admin.getUsername() + "/" + admin.getPassword());
        userService.createAdmin(admin);
    }
}
