package dis.ufv.OrdinariaAPI.dis.ufv.OrdinariaAPI.service;

import dis.ufv.OrdinariaAPI.dis.ufv.OrdinariaAPI.model.User;
import dis.ufv.OrdinariaAPI.dis.ufv.OrdinariaAPI.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService() {
        this.userRepository = new UserRepository(); // o usa @Autowired si lo registras como bean
    }

    public List<User> getAllUsers() {
        return userRepository.getAllUsuarios();
    }
    public void addUser(User user) {
        userRepository.addUsuario(user);
    }
    public void updateUser(User user) {
        userRepository.updateUsuario(user);
    }
    public byte[] generatePdf() {
        return userRepository.generatePdfFromUsuarios(); // suponiendo que devuelve un PDF en bytes
    }

}