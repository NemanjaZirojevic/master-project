package com.reactive.project.domain;



import javax.persistence.Id;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;
import javax.persistence.*;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class User implements UserDetails{

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private Long id;

        private String firstName;
        private String lastName;
        private String email;
        private String password;

        @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
        @JoinTable(
                name = "users_roles",
                joinColumns = @JoinColumn(
                        name = "user_id", referencedColumnName = "id"),
                inverseJoinColumns = @JoinColumn(
                        name = "role_id", referencedColumnName = "id"))
        private Collection<Role> roles;

        public User() {
        }

        public User(String firstName, String lastName, String email, String password, Collection<Role> roles) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.email = email;
            this.password = password;
            this.roles = roles;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }

    public String getPassword() {
            return password;
        }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setPassword(String password) {
            this.password = password;
        }

        public Collection<Role> getRoles() {
            return roles;
        }

        public void setRoles(Collection<Role> roles) {
            this.roles = roles;
        }

        @Override
        public String toString() {
            return "User{" +
                    "id=" + id +
                    ", firstName='" + firstName + '\'' +
                    ", lastName='" + lastName + '\'' +
                    ", email='" + email + '\'' +
                    ", password='" + "*********" + '\'' +
                    ", roles=" + roles +
                    '}';
        }

}
