/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author adryc
 */
@Entity
@Table(name = "Usuario")
@NamedQueries({
	@NamedQuery(name="findUsuarioByNickname", query = "SELECT u FROM Usuario u WHERE u.nickname=:nickname"),
        @NamedQuery(name="findAll", query = "SELECT u FROM Usuario u")
})
public class Usuario implements Serializable{
    
    private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "nickname")
	private String nickname;
	
	@Column(name = "password")
	private String password;
        
        @Column(name = "admin")
        private boolean admin;
        
        @OneToMany(mappedBy = "usuario", fetch = FetchType.EAGER)
        private List<Cuenta> cuentas;

    public Usuario() {
        this.id = -1L;
        this.nickname = "";
        this.password = "";
        this.admin = false;
        this.cuentas = new ArrayList<Cuenta>();
    }

    public Usuario(Long id, String nickname, String password, boolean admin, List<Cuenta> cuentas) {
        this.id = id;
        this.nickname = nickname;
        this.password = password;
        this.admin = admin;
        this.cuentas = cuentas;
    }
    
    public Usuario(String nickname, String password) {
        this.id = id;
        this.nickname = nickname;
        this.password = password;
        this.admin = false;
        this.cuentas = new ArrayList<>();
    }

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the nickname
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * @param nickname the nickname to set
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the admin
     */
    public boolean isAdmin() {
        return admin;
    }

    /**
     * @param admin the admin to set
     */
    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    /**
     * @return the cuentas
     */
    public List<Cuenta> getCuentas() {
        return cuentas;
    }

    /**
     * @param cuentas the cuentas to set
     */
    public void setCuentas(List<Cuenta> cuentas) {
        this.cuentas = cuentas;
    }

    @Override
    public String toString() {
        return ("Usuario " + this.id + ": " + nickname + ", cuentas asociadas: " + this.cuentas.toString());
    }
    
    
        
        
}
