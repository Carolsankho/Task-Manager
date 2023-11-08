package com.manage.task.model;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

@Entity
@Table(name="users")
public class User implements UserDetails{

    private static final long serialVersionUID = 428441650422214977L;
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;
	
    @Email(message = "{user.email.not.valid}")
    @NotEmpty(message = "{user.email.not.empty}")
    @Column(unique = true)
    private String email;
    
    @NotEmpty(message = "{user.name.not.empty}")
    private String name;
    
    @NotEmpty(message = "{user.password.not.empty}")
    @Length(min = 5, message = "{user.password.length}")
    private String password;
    
    @Column(columnDefinition = "VARCHAR(255) DEFAULT 'images/user.png'")
    private String photo;
    
    @OneToMany(mappedBy = "owner", cascade = CascadeType.PERSIST)
    private List<Task> tasksOwned;

    @OneToOne(cascade = CascadeType.MERGE)
    private Role role;

    public List<Task> getTasksCompleted() {
        return tasksOwned.stream()
                .filter(Task::isCompleted)
                .collect(Collectors.toList());
    }

    public List<Task> getTasksInProgress() {
        return tasksOwned.stream()
                .filter(task -> !task.isCompleted())
                .collect(Collectors.toList());
    }

//    public boolean isAdmin() {
//        String roleName = "ADMIN";
//        return role.stream().map(Role::getRole).anyMatch(roleName::equals);
//    }

    public User() {
    }

    public User(@Email @NotEmpty String email,
                @NotEmpty String name,
                @NotEmpty @Length(min = 5) String password,
                String photo) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.photo = photo;
    }

    public User(@Email @NotEmpty String email,
                @NotEmpty String name,
                @NotEmpty @Length(min = 5) String password,
                String photo,
                List<Task> tasksOwned,
                Role role) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.photo = photo;
        this.tasksOwned = tasksOwned;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public List<Task> getTasksOwned() {
        return tasksOwned;
    }

    public void setTasksOwned(List<Task> tasksOwned) {
        this.tasksOwned = tasksOwned;
    }

    public Role getRoles() {
        return role;
    }

    public void setRoles(Role role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) &&
                email.equals(user.email) &&
                name.equals(user.name) &&
                password.equals(user.password) &&
                Objects.equals(photo, user.photo) &&
                Objects.equals(tasksOwned, user.tasksOwned) &&
                Objects.equals(role, user.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, name, password, photo, tasksOwned, role);
    }

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority(role.getAuthority()));
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

	@Override
	public String toString() {
		return "User [id=" + id + ", email=" + email + ", name=" + name + ", password=" + password + ", photo=" + photo
				+ ", tasksOwned=" + tasksOwned + ", role=" + role + "]";
	}
	
	
}
