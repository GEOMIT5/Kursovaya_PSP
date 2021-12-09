package org.work.domen.entity;

import java.io.Serializable;
import java.util.Objects;

abstract class AbstractEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;

    AbstractEntity() {
    }

    AbstractEntity(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractEntity abstractEntity = (AbstractEntity) o;
        return Objects.equals(id, abstractEntity.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return
                "id='" + id + '\'' +
                        '}';
    }

}
