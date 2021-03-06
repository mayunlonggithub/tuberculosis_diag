package com.example.tuberculosis.domain.entity;

import com.example.tuberculosis.base.CreateModifyTime;

import javax.persistence.*;
import java.util.List;

/**
 * @author Ma
 * @version 1.0
 * @date 2019/12/29 16:55
 */
@Entity
@Table(name = "t_user")
public class User extends CreateModifyTime  {
    private int id;
    private String nickname;
    private String email;
    private String phone;
    private String account;
    private String password;
    private Integer delFlag;
    private List<Role> roles;
    private Integer roleType;
    private String employeeId;
    private Integer age;
    private Integer ifAccess;
    private String gender;


//    @Override
//    @Transient
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
//        for (Role role : roles) {
//            authorities.add(new SimpleGrantedAuthority(role.getName()));
//        }
//        return authorities;
//    }
//
//    @Override
//    public String getPassword() {
//        return password;
//    }
//
//    public void setPassword(String password){
//        this.password=password;
//    }
//    @Override
//    @Transient
//    public String getUsername() {
//        return account;
//    }
//    @Override
//    @Transient
//    public boolean isAccountNonExpired() {
//        return true;
//    }
//    @Override
//    @Transient
//    public boolean isAccountNonLocked() {
//        return true;
//    }
//    @Override
//    @Transient
//    public boolean isCredentialsNonExpired() {
//        return true;
//    }
//    @Override
//    @Transient
//    public boolean isEnabled() {
//        return true;
//    }
//    //省略getter/setter

    @Id
    @Column(name = "id")
    @TableGenerator(name = "idGenerator", table = "t_id_generator", pkColumnName = "id_key", pkColumnValue = "user", valueColumnName = "id_value")
    @GeneratedValue(generator = "idGenerator", strategy = GenerationType.TABLE)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "nickname")
    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @Basic
    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "phone")
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Basic
    @Column(name = "account")
    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }


    @Basic
    @Column(name = "del_flag")
    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    @Basic
    @Column(name = "role_type")
    public Integer getRoleType() {
        return roleType;
    }

    public void setRoleType(Integer roleType) {
        this.roleType = roleType;
    }

    @Basic
    @Column(name = "employee_id")
    public String getEmployeeId() {
        return employeeId;
    }
    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    @Transient
    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    @Basic
    @Column(name = "age")
    public Integer getAge() {
        return age;
    }
    public void setAge(Integer age) {
        this.age = age;
    }

    @Basic
    @Column(name = "if_access")
    public Integer getIfAccess() {
        return ifAccess;
    }
    public void setIfAccess(Integer ifAccess) {
        this.ifAccess = ifAccess;
    }

    @Basic
    @Column(name = "gender")
    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }

}
