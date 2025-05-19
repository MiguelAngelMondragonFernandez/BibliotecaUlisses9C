package mx.edu.utez.controlaccess.model;


public class UserDto {
    private Long userId;
    private String email;
    private String password;
    private String state;

    public UserDto() {
    }

    public UserDto(Long userId, String email, String password, String state) {
        this.userId = userId;
        this.email = email;
        this.password = password;
        this.state = state;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
