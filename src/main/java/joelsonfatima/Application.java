package joelsonfatima;

import joelsonfatima.dto.ROLES;
import joelsonfatima.entity.Driver;
import joelsonfatima.entity.Role;
import joelsonfatima.entity.User;
import joelsonfatima.exception.ResourceNotFoundException;
import joelsonfatima.repository.DriverRepository;
import joelsonfatima.repository.RoleRepository;
import joelsonfatima.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@SpringBootApplication
@EnableAspectJAutoProxy
public class Application implements CommandLineRunner {
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @Autowired
    private DriverRepository driverRepository;
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        addRoles();
        addUsers();
        addDates();
        addDriver();
    }
    public void addRoles() {
        if(!roleRepository.findAll().isEmpty())
            return;

        for(ROLES roleEnum : ROLES.values()) {
            Role role = new Role();
            role.setName(roleEnum.name());
            roleRepository.save(role);
        }
    }

    public void addUsers() {
        if(!userRepository.findAll().isEmpty())
            return;

        // Get role admin
        Role roleAdmin = roleRepository.findByName(ROLES.ROLE_ADMIN.name()).orElseThrow(
                () -> new ResourceNotFoundException("Role", "name", ROLES.ROLE_ADMIN.name())
        );

        User user = new User();
        User user1 = new User();
        User user2 = new User();
        User user3 = new User();
        User user4 = new User();
        User user5 = new User();
        User user6 = new User();
        User user7 = new User();

        user.setUsername("admin");
        user.setPassword(passwordEncoder.encode("admin"));
        user.setRole(roleAdmin);

        user1.setUsername("joelson");
        user1.setPassword(passwordEncoder.encode("joelson"));
        user1.setRole(roleAdmin);

        user2.setUsername("fatima");
        user2.setPassword(passwordEncoder.encode("fatima"));
        user2.setRole(roleAdmin);

        user3.setUsername("emile");
        user3.setPassword(passwordEncoder.encode("emile"));
        user3.setRole(roleAdmin);

        user4.setUsername("haja");
        user4.setPassword(passwordEncoder.encode("haja"));
        user4.setRole(roleAdmin);

        user5.setUsername("nirina");
        user5.setPassword(passwordEncoder.encode("nirina"));
        user5.setRole(roleAdmin);

        user6.setUsername("mimi");
        user6.setPassword(passwordEncoder.encode("mimi"));
        user6.setRole(roleAdmin);

        user7.setUsername("nana");
        user7.setPassword(passwordEncoder.encode("nana"));
        user7.setRole(roleAdmin);

        userRepository.save(user);
        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);
        userRepository.save(user4);
        userRepository.save(user5);
        userRepository.save(user6);
        userRepository.save(user7);

    }

    public void addDates(){
        LocalDate d1= LocalDate.now();
        System.out.println("La date d'aujourd'hui est "+d1);
    }

    public  void addDriver(){
        Driver d1= new Driver();
        //"A18X30422",2021-02-20,"Firmain","Gezard","1"
        d1.setCin("A18X30422");
        d1.setDateOfBirth(LocalDate.ofEpochDay(2002-01-20));
        d1.setFirstName("Firmain");
        d1.setLastName("Gezard");

        driverRepository.save(d1);
        ;
    }
}
