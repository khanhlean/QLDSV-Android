package com.example.Objects;

public class Semester {
    private int term, stSchoolYear, ndSchoolYear;

    public Semester(int term, int stSchoolYear, int ndSchoolYear) {
        this.term = term;
        this.stSchoolYear = stSchoolYear;
        this.ndSchoolYear = ndSchoolYear;
    }

    public Semester() {
    }

    public int getTerm() {
        return term;
    }

    public void setTerm(int term) {
        this.term = term;
    }

    public int getStSchoolYear() {
        return stSchoolYear;
    }

    public void setStSchoolYear(int stSchoolYear) {
        this.stSchoolYear = stSchoolYear;
    }

    public int getNdSchoolYear() {
        return ndSchoolYear;
    }

    public void setNdSchoolYear(int ndSchoolYear) {
        this.ndSchoolYear = ndSchoolYear;
    }
}
