package com.login.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class userDto {
	
	  private String userId;
	  private String  username;
	  private String accessToken;
	 
	private String role;
	

}
