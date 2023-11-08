package com.manage.task.model;

import java.util.List;
import java.util.Objects;

import org.springframework.security.core.GrantedAuthority;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="roles")
public class Role implements GrantedAuthority{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Integer id;
    @Column(name = "role")
    private String role;
    
    @OneToMany(mappedBy = "role")
    private List<User> user;

    public Role() {
    }

    public Role(String role) {
        this.role = role;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role1 = (Role) o;
        return Objects.equals(id, role1.id) &&
                Objects.equals(role, role1.role);
        }

    @Override
    public int hashCode() {
        return Objects.hash(id, role);
    }

	@Override
	public String getAuthority() {
		return role;
	}

	@Override
	public String toString() {
		return "Role [id=" + id + ", role=" + role + "]";
	}
	
	
}


