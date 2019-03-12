package cn.edu.llxy.dw.core.pojo;

public class UserVo {
    private String userId;
    private String userName;
    private String account;
    private String password;
    private String role;
    private String permission;

    public UserVo() {
    }

    public UserVo(String userId, String userName, String account, String password, String role, String permission) {
        this.userId = userId;
        this.userName = userName;
        this.account = account;
        this.password = password;
        this.role = role;
        this.permission = permission;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    @Override
    public String toString() {
        return "UserVo{" +
                "userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", account='" + account + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                ", permission='" + permission + '\'' +
                '}';
    }
}
