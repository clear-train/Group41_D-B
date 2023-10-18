package com.example.robot1.user.domain;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class User {
    private Integer user_id;
    private String username;
    private boolean appointed_manager;
    private String department;
    private String password;
    private String account;
    private String phone;
    private String email;
    private LocalDateTime create_time;
    private LocalDateTime update_time;

/*
    public Integer getUserid() {
        return userid;
    }
    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public boolean getAppointed_manager(){return appointed_manager;}
    public void setAppointed_manager(boolean appointed_manager){this.appointed_manager=appointed_manager;}

    public String getDepartment(){return department;}
    public void setDepartment(String department){this.department=department;}

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {this.password = password;}

    public String getAccount(){return account;}
    public void setAccount(String account){this.account=account;}

    public String getPhone(){return phone;}
    public void setPhone(String phone){this.phone=phone;}

    public String getEmail(){return email;}
    public void setEmail(String email){this.email=email;}

    public LocalDateTime getCreate_time() {return create_time;}

    public void setCreate_time(LocalDateTime create_time) {this.create_time = create_time;}

    public LocalDateTime getUpdate_time() {return update_time;}

    public void setUpdate_time(LocalDateTime update_time) {this.update_time = update_time;}

 */

}
