package com.adil.blog.payloads;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class UserDto {

    private int id;

    @NotEmpty
    private String name;

    @Email(message = "Email address is not valid.", regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
    private String email;

    @NotEmpty(message = "Password cannot be empty")
    @Size(min = 4, message = "Password must be atleast 4 characters long.")
    private String password;

    @NotEmpty
    private String about;

}
