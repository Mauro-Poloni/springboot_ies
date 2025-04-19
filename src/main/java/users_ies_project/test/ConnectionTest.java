package users_ies_project.test;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ConnectionTest implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        System.out.println("¡Conexión exitosa! La aplicación se ha iniciado correctamente.");
    }
} 