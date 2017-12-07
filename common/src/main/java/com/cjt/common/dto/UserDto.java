package com.cjt.common.dto;

public class UserDto {

  private long userId;

  private String account;

  private String password;

  public UserDto(long userId, String account, String password) {
    this.userId = userId;
    this.account = account;
    this.password = password;
  }

  public long getUserId() {
    return userId;
  }

  public void setUserId(long userId) {
    this.userId = userId;
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

  /**
   * 构造器模式，方便拓展参数
   */
  public static class Builder{

    private long userId;

    private String account;

    private String password;

    public Builder userId(long userId){
      this.userId = userId;
      return this;
    }

    public Builder account(String account){
      this.account = account;
      return this;
    }

    public Builder password(String password){
      this.password = password;
      return this;
    }

    public UserDto create(){
      return new UserDto(userId, account, password);
    }
  }
}
