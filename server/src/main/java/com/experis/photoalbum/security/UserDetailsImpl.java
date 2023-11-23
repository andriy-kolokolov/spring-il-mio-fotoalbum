package com.experis.photoalbum.security;

import com.experis.photoalbum.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Data
@AllArgsConstructor
public class UserDetailsImpl implements UserDetails {

    private Long id;
    private String username;
    private String password;

    public static UserDetailsImpl build(User user) {
        return new UserDetailsImpl(
                user.getId(),
                user.getUsername(),
                user.getPassword());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // In this example, we're returning a single role "ROLE_USER".
        // You can adjust this based on your actual user roles.
        return null;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        // Return true if the user account is not expired.
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // Return true if the user account is not locked.
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // Return true if the user's credentials are not expired.
        return true;
    }

    @Override
    public boolean isEnabled() {
        // Return true if the user account is enabled.
        return true;
    }
}
