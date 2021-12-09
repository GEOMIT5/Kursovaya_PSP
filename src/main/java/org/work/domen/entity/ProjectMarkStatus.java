package org.work.domen.entity;

public enum ProjectMarkStatus {

    LONG(1),
    MID(2),
    SHORT(3),
    NOL(4);


    private int id;

    ProjectMarkStatus(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }



}
