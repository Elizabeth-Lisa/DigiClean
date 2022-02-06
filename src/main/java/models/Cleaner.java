package models;

public class Cleaner {
    private int id;
    private String cleanerName;
    private String cleanerBio;
    private int cleanerPassword;
    private int cleanerIdNo;
    private int cleanerPhone;
    private boolean status;
    private int cleanerRating;


    public Cleaner(String cleanerName, int cleanerPassword, int cleanerIdNo, int cleanerPhone) {
        this.cleanerName = cleanerName;
        this.cleanerPassword = cleanerPassword;
        this.cleanerIdNo = cleanerIdNo;
        this.cleanerPhone = cleanerPhone;
        this.status = false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCleanerName() {
        return cleanerName;
    }

    public void setCleanerName(String cleanerName) {
        this.cleanerName = cleanerName;
    }

    public String getCleanerBio() {
        return cleanerBio;
    }

    public void setCleanerBio(String cleanerBio) {
        this.cleanerBio = cleanerBio;
    }

    public int getCleanerPassword() {
        return cleanerPassword;
    }

    public void setCleanerPassword(int cleanerPassword) {
        this.cleanerPassword = cleanerPassword;
    }

    public int getCleanerIdNo() {
        return cleanerIdNo;
    }

    public void setCleanerIdNo(int cleanerIdNo) {
        this.cleanerIdNo = cleanerIdNo;
    }

    public int getCleanerPhone() {
        return cleanerPhone;
    }

    public void setCleanerPhone(int cleanerPhone) {
        this.cleanerPhone = cleanerPhone;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getCleanerRating() {
        return cleanerRating;
    }

    public void setCleanerRating(int cleanerRating) {
        this.cleanerRating = cleanerRating;
    }
}
