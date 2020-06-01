package com.library.businessModels;

public interface ISalt {
public void setSaltedPassword(String passwordValue,String salt);
public String getSaltedPassword();
}
