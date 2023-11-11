package com.backend.domain.model;

public enum TypeSport {
    FUTEVOEI(1),
    VOLLEYBALL(2),
    BEACH_TENNIS(3),;

	private int sportType;

    private TypeSport(int sportType) {
        this.sportType = sportType;
    }

    public int getPriority() {
        return sportType;
    }
}
