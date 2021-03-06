package org.communis.javawebintro.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.communis.javawebintro.entity.User;
import org.communis.javawebintro.enums.UserRole;
import org.communis.javawebintro.enums.UserStatus;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

@Data
public class UserWrapper implements ObjectWrapper<User>, Serializable {
    protected Long id;

    @NotEmpty
    @Size(max = 100)
    protected String firstName;

    @Size(max = 100)
    protected String lastName;

    @NotEmpty
    @Size(max = 50)
    private String login;

    @NotEmpty
    @Size(max = 150)
    private String email;

    @JsonIgnore
    @Size(min = 8, max = 32)
    private String password;

    @JsonIgnore
    @Size(min = 8, max = 32)
    private String confirmPassword;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private Date dateLastOnline;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private Date dateOpen;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private Date dateClose;
    private UserStatus status;
    private UserRole role;

    public UserWrapper() {

    }

    public UserWrapper(User user) {
        toWrapper(user);
    }

    @Override
    public void toWrapper(User item) {
        if (item != null) {
            id = item.getId();
            firstName = item.getFirstName();
            lastName = item.getLastName();
            login = item.getLogin();
            email = item.getEmail();
            password = item.getPassword();
            dateLastOnline = item.getDateLastOnline();
            dateOpen = item.getDateCreate();
            dateClose = item.getDateBlock();
            status = item.getStatus();
            role = item.getRole();
        }
    }

    @Override
    public void fromWrapper(User item) {
        if (item != null) {
            item.setLogin(login);
            item.setRole(role);
            item.setStatus(status);
            item.setEmail(email);
            item.setFirstName(firstName);
            item.setLastName(lastName);
        }
    }

    public String getFullName() {
        return firstName + (lastName != null ? " " + lastName : "");
    }

    @AssertTrue
    public boolean isPasswordValid() {
        return (password == null && confirmPassword == null) ||
                (password != null && confirmPassword != null && password.equals(confirmPassword));
    }


    public boolean isActive() {
        return status == UserStatus.ACTIVE;
    }
}