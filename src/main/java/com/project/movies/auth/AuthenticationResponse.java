package com.project.movies.auth;

import com.project.movies.role.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class AuthenticationResponse {
    private String token;
    private String fullname;
    private String email;
    private String role;
}
