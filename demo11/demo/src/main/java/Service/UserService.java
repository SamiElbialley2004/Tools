package Service;

import Entity.User;
import jakarta.ejb.Stateless;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Stateless
public class UserService {

    private static final Map<String, User> users = new HashMap<>();

    public void register(String email, String password, String fullName) throws Exception {
        if (email == null || password == null || fullName == null) {
            throw new Exception("Email, password, and full name are required.");
        }
        if (users.containsKey(email)) {
            throw new Exception("Email already registered.");
        }
        String hashed = hashPassword(password);
        User u = new User();
        u.setEmail(email);
        u.setPasswordHash(hashed);
        u.setFullName(fullName);
        u.setRole("USER");
        users.put(email, u);
    }

    public boolean login(String email, String password) {
        User u = users.get(email);
        if (u == null) return false;
        return u.getPasswordHash().equals(hashPassword(password));
    }

    public User getUser(String email) {
        return users.get(email);
    }

    public void updateProfile(
            String email,
            String newEmail,
            String newPassword,
            String newFullName,
            String newBio,
            String newRole
    ) throws Exception {
        User u = users.get(email);
        if (u == null) throw new Exception("User not found.");

        // Change email
        if (newEmail != null && !newEmail.equals(email)) {
            if (users.containsKey(newEmail)) throw new Exception("Email already in use.");
            users.remove(email);
            u.setEmail(newEmail);
            users.put(newEmail, u);
        }
        // Change password
        if (newPassword != null) {
            u.setPasswordHash(hashPassword(newPassword));
        }
        // Change full name
        if (newFullName != null) {
            u.setFullName(newFullName);
        }
        // Change bio
        if (newBio != null) {
            u.setBio(newBio);
        }
        // Change role
        if (newRole != null) {
            if (!newRole.equals("USER") && !newRole.equals("ADMIN")) {
                throw new Exception("Role must be USER or ADMIN.");
            }
            u.setRole(newRole);
        }
    }

    private String hashPassword(String pwd) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] h = md.digest(pwd.getBytes());
            return Base64.getEncoder().encodeToString(h);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Hashing error", e);
        }
    }
}
