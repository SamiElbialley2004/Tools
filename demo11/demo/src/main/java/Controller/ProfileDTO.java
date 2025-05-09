package Controller;

public class ProfileDTO {
    private String email;       // current email (to identify the user)
    private String newEmail;    // if the user wants to change email
    private String password;    // new password (if changing)
    private String fullName;    // new full name
    private String bio;         // new bio
    private String role;        // "USER" or "ADMIN"

    // Getters & setters
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getNewEmail() { return newEmail; }
    public void setNewEmail(String newEmail) { this.newEmail = newEmail; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getBio() { return bio; }
    public void setBio(String bio) { this.bio = bio; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
}
