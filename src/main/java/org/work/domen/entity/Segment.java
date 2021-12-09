package org.work.domen.entity;

public class Segment extends AbstractEntity{

    private String name;

    public Segment() {
    }

    public Segment(String id) {
        super(id);
    }

    public Segment(String id, String name) {
        this(id);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }




}
