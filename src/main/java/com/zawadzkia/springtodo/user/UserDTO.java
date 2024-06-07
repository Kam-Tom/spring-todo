package com.zawadzkia.springtodo.user;

import java.io.Serializable;

import com.zawadzkia.springtodo.validator.FieldsMatch;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldsMatch(firstField = "password", secondField = "confirmPassword", message = "Passwords do not match")
public class UserDTO implements Serializable {

    @NotNull(message = "Username is required")
    @NotEmpty(message = "Username cannot be empty")
    @Size(min = 3, message = "Name should contain at least 3 characters.")
    private String username;

    @NotNull(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String password;

    private String confirmPassword;
}
