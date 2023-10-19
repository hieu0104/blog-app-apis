package com.hieu.blog.payloads;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor

public class JwtAuthRequest {

	private String username;
	private String password;
}
