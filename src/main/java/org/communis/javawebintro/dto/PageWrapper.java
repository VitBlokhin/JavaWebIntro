package org.communis.javawebintro.dto;

import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class PageWrapper<T extends ObjectWrapper> {
    private List<T> items;
    private int total = 0;
    private int number = 0;

    public PageWrapper(Object obj, Supplier<T> supplier) {
        if (obj instanceof Page) {
            Page page = (Page) obj;
            total = page.getTotalPages();
            number = page.getNumber() + 1;

            items = new ArrayList<>(page.getSize());
            for (Object elem : page.getContent()) {
                try {
                    T objectWrapper = supplier.get();
                    objectWrapper.toWrapper(elem);
                    items.add(objectWrapper);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        } else if (obj instanceof List) {
            List source = (List) obj;
            items = new ArrayList<>(source.size());
            for (Object elem : source) {
                try {
                    T objectWrapper = supplier.get();
                    objectWrapper.toWrapper(elem);
                    items.add(objectWrapper);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        } else {
            items = new ArrayList<>(0);
        }
    }

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
