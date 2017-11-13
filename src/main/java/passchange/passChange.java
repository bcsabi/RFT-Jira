package passchange;

import hu.unideb.rft.jira.jira_springboot_mvc.entity.User;

public class passChange {
    private String new_password;
    private String confirm_password;
    private String current_password;
    private User user;

    public String getNew_password() {
        return new_password;
    }

    public void setNew_password(String new_password) {
        this.new_password = new_password;
    }

    public String getConfirm_password() {
        return confirm_password;
    }

    public void setConfirm_password(String confirm_password) {
        this.confirm_password = confirm_password;
    }

    public String getCurrent_password() {
        return current_password;
    }

    public void setCurrent_password(String current_password) {
        this.current_password = current_password;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
