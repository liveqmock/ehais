package org.ehais.model;

import org.ehais.utils.Password;

public class ExtendEntity {

	  @Password
	  private String password;
	  
	  public String getPassword() {
	    return password;
	  }

	  public void setPassword(String password) {
	    this.password = password;
	  }

	}
