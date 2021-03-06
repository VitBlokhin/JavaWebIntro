package org.communis.javawebintro.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.communis.javawebintro.entity.Category;

import org.hibernate.validator.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

@Data
public class CategoryWrapper implements ObjectWrapper<Category>, Serializable {
    private Long id;

    @NotEmpty
    @Size(max = 100)
    private String name;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private Date dateOpen;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private Date dateClose;

    public CategoryWrapper() {
    }

    public CategoryWrapper(Category item) {
        toWrapper(item);
    }

    @Override
    public void toWrapper(Category item) {
        if (item != null) {
            id = item.getId();
            name = item.getName();
            dateOpen = item.getDateCreate();
            dateClose = item.getDateClose();
        }
    }

    @Override
    public void fromWrapper(Category item) {
        if (item != null) {
            item.setName(name);
        }
    }
}
