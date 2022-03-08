package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Cuenta")
@NamedQueries({
	@NamedQuery(name="findCuentaById", query = "SELECT c FROM Cuenta c WHERE c.id=:id"),
        @NamedQuery(name="findCuentaByUsuario", query = "SELECT c FROM Cuenta c WHERE c.usuario.id=:id")
})
public class Cuenta implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "saldo")
	private int saldo;
        
        @ManyToOne()
        @JoinColumn(name="usuarioId")
        public Usuario usuario;
	
	public Cuenta() {
		this.id=-1L;
		this.saldo=0;
                this.usuario=null;
	}
	
	public Cuenta(Usuario usuario) {
		this.id=-1L;
                this.usuario=usuario;
		this.saldo=0;
	}
	
	public Long getId() {
		return id;
	}
	
	
	public int getSaldo() {
		return saldo;
	}
	
	public void setSaldo(int saldo) {
		this.saldo = saldo;
	}
        
          public void setId(Long id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }
          
          
          
	
	
	@Override
	public String toString() {
		return "Cuenta " + this.id +", dueño: " + usuario.getNickname() + ", saldo disponible: " + saldo;
	}

        
         
	
	
}
