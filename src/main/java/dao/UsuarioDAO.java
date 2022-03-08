/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import model.Cuenta;
import model.Usuario;

/**
 *
 * @author adryc
 */
public class UsuarioDAO {

    private EntityManager em = null;

    public UsuarioDAO() {
        try {
            this.em = utils.PersistenceUnit.getEM();
        } catch (Exception ex) {
            System.err.println("Error al acceder a la base de datos: /n" + ex);
        }
    }
    
    //Inserta un nuevo usuario en la BBDD
    public Usuario register(Usuario u){
        try{
            em.getTransaction().begin();
            em.persist(u);
            em.getTransaction().commit();
            System.out.println("Usuario registrado: " + u.toString());
            return u;
        }catch(Exception e){
            System.out.println("Error al registrar usuario: "+e);
            return null;
        }
    }

    //Devuelve el usuario según su nombre
    public Usuario getByName(String name) {
        Usuario result = null;
        if(name!=null){
            try {
            em.getTransaction().begin();
            TypedQuery<Usuario> query = em.createNamedQuery("findUsuarioByNickname", Usuario.class);
            query.setParameter("nickname", name);
            result = query.getSingleResult();
            em.getTransaction().commit();
            System.out.println("Usuario encontrado: " + result.toString());
            return result;
        } catch (Exception e) {
            System.out.println("Error al buscar usuario: " + e);
            em.getTransaction().commit();
            return null;
        }
        }else{
            return null;
        }
    }
    
    //Devuelve una lista con todos los usuarios registrados
    public List<Usuario> getAll(){
        List<Usuario> result = new ArrayList<>();
        try {
            em.getTransaction().begin();
            TypedQuery<Usuario> query = em.createNamedQuery("findAll", Usuario.class);
            result = query.getResultList();
            em.getTransaction().commit();
            System.out.println("Usuarios encontrados: " + result.toString());
            return result;
        } catch (Exception e) {
            System.out.println("Error al buscar usuarios: " + e);
            return null;
        }
    }
    
    

}
