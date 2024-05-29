package com.example.Objects;

public class SubjectScore {
    private String subjectId, subjectName, finalScoreByChar;
    private int creditHours;
    private float attendRatio, attendScore, middleExamRatio, middleExamScore,
            practiceRatio, practiceScore, seRatio, seScore, fnlExamRatio,
            fstFnlExamScore, sndFnlExamScore, thrdFnlExamScore, finalScore;
    private Semester semester;

    public SubjectScore(String subjectId, String subjectName, String finalScoreByChar,
                        int creditHours, float attendRatio, float attendScore, float middleExamRatio,
                        float middleExamScore, float practiceRatio, float practiceScore, float seRatio,
                        float seScore, float fnlExamRatio, float fstFnlExamScore, float sndFnlExamScore,
                        float thrdFnlExamScore, float finalScore, Semester semester) {
        this.subjectId = subjectId;
        this.subjectName = subjectName;
        this.finalScoreByChar = finalScoreByChar;
        this.creditHours = creditHours;
        this.attendRatio = attendRatio;
        this.attendScore = attendScore;
        this.middleExamRatio = middleExamRatio;
        this.middleExamScore = middleExamScore;
        this.practiceRatio = practiceRatio;
        this.practiceScore = practiceScore;
        this.seRatio = seRatio;
        this.seScore = seScore;
        this.fnlExamRatio = fnlExamRatio;
        this.fstFnlExamScore = fstFnlExamScore;
        this.sndFnlExamScore = sndFnlExamScore;
        this.thrdFnlExamScore = thrdFnlExamScore;
        this.finalScore = finalScore;
        this.semester = semester;
    }

    @Override
    public boolean equals(Object obj) {
        Semester semesterObj = ((SubjectScore) obj).getSemester();
        return this.semester.getTerm() == semesterObj.getTerm() &&
                this.semester.getStSchoolYear() == semesterObj.getStSchoolYear() &&
                this.semester.getNdSchoolYear() == semesterObj.getNdSchoolYear();
    }

    @Override
    public String toString() {
        return this.semester.getTerm() + " - " + this.semester.getStSchoolYear()
                + "/" + this.semester.getNdSchoolYear();
    }

    public int compare(SubjectScore ss){
        if(this.semester.getStSchoolYear() != ss.getSemester().getStSchoolYear()){
            if(this.semester.getStSchoolYear() > ss.getSemester().getStSchoolYear()){
                return 1;
            }
        }
        else if(this.semester.getTerm() > ss.getSemester().getTerm()){
            return 1;
        }
        return -1;
    }
    public void setCalcFinalScore(){
        this.finalScore = this.attendRatio * this.attendScore + this.middleExamRatio * this.middleExamScore + this.fstFnlExamScore * this.fnlExamRatio;
        setCalcFinalScoreChar();
    }

    private void setCalcFinalScoreChar(){
        if(this.finalScore<(float)4.0) {
            this.finalScoreByChar = "F";
            return;
        }
        if(this.finalScore<(float)5) {
            this.finalScoreByChar = "D";
            return;
        }
        if(this.finalScore<(float)5.5) {
            this.finalScoreByChar = "D+";
            return;
        }
        if(this.finalScore<(float)6.5) {
            this.finalScoreByChar = "C";
            return;
        }
        if(this.finalScore<(float)7) {
            this.finalScoreByChar = "C+";
            return;
        }
        if(this.finalScore<(float)8) {
            this.finalScoreByChar = "B";
            return;
        }
        if(this.finalScore<(float)8.5) {
            this.finalScoreByChar = "B+";
            return;
        }
        if(this.finalScore<(float)9) {
            this.finalScoreByChar = "A";
            return;
        }
        this.finalScoreByChar = "A+";
    }

    public Semester getSemester() {
        return semester;
    }

    public void setSemester(Semester semester) {
        this.semester = semester;
    }

    public SubjectScore() {
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getFinalScoreByChar() {
        return finalScoreByChar;
    }

    public void setFinalScoreByChar(String finalScoreByChar) {
        this.finalScoreByChar = finalScoreByChar;
    }

    public int getCreditHours() {
        return creditHours;
    }

    public void setCreditHours(int creditHours) {
        this.creditHours = creditHours;
    }

    public float getAttendRatio() {
        return attendRatio;
    }

    public void setAttendRatio(float attendRatio) {
        this.attendRatio = attendRatio;
    }

    public float getAttendScore() {
        return attendScore;
    }

    public void setAttendScore(float attendScore) {
        this.attendScore = attendScore;
    }

    public float getMiddleExamRatio() {
        return middleExamRatio;
    }

    public void setMiddleExamRatio(float middleExamRatio) {
        this.middleExamRatio = middleExamRatio;
    }

    public float getMiddleExamScore() {
        return middleExamScore;
    }

    public void setMiddleExamScore(float middleExamScore) {
        this.middleExamScore = middleExamScore;
    }

    public float getPracticeRatio() {
        return practiceRatio;
    }

    public void setPracticeRatio(float practiceRatio) {
        this.practiceRatio = practiceRatio;
    }

    public float getPracticeScore() {
        return practiceScore;
    }

    public void setPracticeScore(float practiceScore) {
        this.practiceScore = practiceScore;
    }

    public float getSeRatio() {
        return seRatio;
    }

    public void setSeRatio(float seRatio) {
        this.seRatio = seRatio;
    }

    public float getSeScore() {
        return seScore;
    }

    public void setSeScore(float seScore) {
        this.seScore = seScore;
    }

    public float getFnlExamRatio() {
        return fnlExamRatio;
    }

    public void setFnlExamRatio(float fnlExamRatio) {
        this.fnlExamRatio = fnlExamRatio;
    }

    public float getFstFnlExamScore() {
        return fstFnlExamScore;
    }

    public void setFstFnlExamScore(float fstFnlExamScore) {
        this.fstFnlExamScore = fstFnlExamScore;
    }

    public float getSndFnlExamScore() {
        return sndFnlExamScore;
    }

    public void setSndFnlExamScore(float sndFnlExamScore) {
        this.sndFnlExamScore = sndFnlExamScore;
    }

    public float getThrdFnlExamScore() {
        return thrdFnlExamScore;
    }

    public void setThrdFnlExamScore(float thrdFnlExamScore) {
        this.thrdFnlExamScore = thrdFnlExamScore;
    }

    public float getFinalScore() {
        return finalScore;
    }

    public void setFinalScore(float finalScore) {
        this.finalScore = finalScore;
    }
}
