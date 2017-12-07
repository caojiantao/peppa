package com.cjt.entity.demo;

public class User {

  private Long id;

  private String account;

  private String password;

  private String name;

  private Integer age;

  private Boolean sex;

  public Boolean getSex() {
    return sex;
  }

  public void setSex(Boolean sex) {
    this.sex = sex;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Integer getAge() {
    return age;
  }

  public void setAge(Integer age) {
    this.age = age;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
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

  @Override
  public String toString() {
    return "User{" +
            "id=" + id +
            ", account='" + account + '\'' +
            ", password='" + password + '\'' +
            ", name='" + name + '\'' +
            ", age=" + age +
            ", sex=" + sex +
            '}';
  }
}
