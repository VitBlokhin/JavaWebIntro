package org.communis.javawebintro.dto;

import lombok.Data;
import org.communis.javawebintro.entity.User;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
public class AuthorWrapper implements ObjectWrapper<User>, Serializable {
    protected Long id;

    @NotEmpty
    @Size(max = 100)
    protected String firstName;

    @Size(max = 100)
    protected String lastName;

    public AuthorWrapper() {
    }

    public AuthorWrapper(User user) {
        toWrapper(user);
    }

    @Override
    public void toWrapper(User user) {
        if (user != null) {
            id = user.getId();
            firstName = user.getFirstName();
            lastName = user.getLastName();
        }
    }

    @Override
    public void fromWrapper(User user) {
        if (user != null) {
            user.setFirstName(firstName);
            user.setLastName(lastName);
        }
    }

    public String getFullName() {
        return firstName + (lastName != null ? " " + lastName : "");
    }
}
