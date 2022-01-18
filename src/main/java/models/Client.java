package models;

public class Client {
    private int id;
    private String clientName;
    private int clientPassword;
    private int clientIdNo;
    private int clientPhone;
    private String clientLocation;

    public Client(String clientName, int clientPassword, int clientIdNo, int clientPhone, String clientLocation) {
        this.clientName = clientName;
        this.clientPassword = clientPassword;
        this.clientIdNo = clientIdNo;
        this.clientPhone = clientPhone;
        this.clientLocation = clientLocation;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public int getClientPassword() {
        return clientPassword;
    }

    public void setClientPassword(int clientPassword) {
        this.clientPassword = clientPassword;
    }

    public int getClientIdNo() {
        return clientIdNo;
    }

    public void setClientIdNo(int clientIdNo) {
        this.clientIdNo = clientIdNo;
    }

    public int getClientPhone() {
        return clientPhone;
    }

    public void setClientPhone(int clientPhone) {
        this.clientPhone = clientPhone;
    }

    public String getClientLocation() {
        return clientLocation;
    }

    public void setClientLocation(String clientLocation) {
        this.clientLocation = clientLocation;
    }
}
