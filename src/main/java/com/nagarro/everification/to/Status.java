package com.nagarro.everification.to;

public enum Status {
    UN_ASSIGNED("Unassigned"),
    PROCEED("Proceed"),
    REJECT("Reject");

    private final String status;

    Status(String status) {
        this.status = status;
    }

    public String toString() {
        return this.status;
    }
}
