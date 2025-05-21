package mx.edu.utez.controlaccess.model;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {
    private Long userId;
    private String email;
    private String password;
    private String state;
    private String role;

    public UserDto() {
    }

    public UserDto(Long userId, String email, String password, String state) {
        this.userId = userId;
        this.email = email;
        this.password = password;
        this.state = state;
    }
}
