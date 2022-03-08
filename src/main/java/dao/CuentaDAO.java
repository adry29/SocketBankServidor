package dao;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceUnit;
import javax.persistence.TypedQuery;

import model.Cuenta;
import model.Usuario;

public class CuentaDAO {

	private EntityManager em = null;
	
	public CuentaDAO() {
		try {
			this.em = utils.PersistenceUnit.getEM();
		}catch (Exception ex){
			System.err.println("Error al acceder a la base de datos: /n"+ex);
		}
	}
	
        //Inserta la cuenta en la base de datos
	public void register(Cuenta cuenta) {
            UsuarioDAO dao = new UsuarioDAO();
            Usuario u = dao.getByName(cuenta.usuario.getNickname());
            List<Cuenta> ucuentas = u.getCuentas();
		try {
			em.getTransaction().begin();
			em.persist(cuenta);
                        ucuentas.add(cuenta);
                        u.setCuentas(ucuentas);
                        em.merge(u);
			em.getTransaction().commit();
			System.out.println("Cuenta registrada correctamente: "+cuenta.toString());
		}catch(Exception ex) {
			System.err.println("Error al registrar Cuenta: /n"+ex);
		}
	}
	
        //Modifica la cuenta de la BBDD, se usa para cambiar el saldo cuando
        //se realiza una transacción
	public void edit(Cuenta newCuenta) {
		try {
			em.getTransaction().begin();
			em.merge(newCuenta);
			em.getTransaction().commit();
			System.out.println("Cuenta editada correctamente: "+newCuenta.toString());
		}catch(Exception ex) {
			System.err.println("Error al editar cuenta: /n"+ex);
		}
	}
	
        //Devuelve una cuenta según su id
	public Cuenta getById(Long id) {
		Cuenta result = null;
		try {
			em.getTransaction().begin();
			TypedQuery<Cuenta> query = em.createNamedQuery("findCuentaById", Cuenta.class);
			query.setParameter("id", id);
			result = query.getSingleResult();
			em.getTransaction().commit();
			System.out.println("Cliente encontrado: "+result.toString());
			return result;
		}catch(Exception ex) {
			System.err.println("Error al buscar cuenta: /n"+ex);
			return result;
		}
	}
        
        //Devuelve todas las cuentas asociadas a un usuario concreto
        public List<Cuenta> getByUser(Usuario u){
            List<Cuenta> result = new ArrayList();
            try {
			if(u != null){
                            em.getTransaction().begin();
			TypedQuery<Cuenta> query = em.createNamedQuery("findCuentaByUsuario", Cuenta.class);
			query.setParameter("id", u.getId());
			result = query.getResultList();
			em.getTransaction().commit();
			System.out.println("Cuentas encontradas: "+result.toString()+" del cliente "+u.getNickname());
			return result;
                        }else{
                            return result;
                        }
		}catch(Exception ex) {
			System.err.println("Error al buscar cuentas: /n"+ex);
			return result;
		}
        }
        
        //Elimina una cuenta de la BBDD
        public boolean delete(Cuenta c){
            Usuario u = c.getUsuario();
            List<Cuenta> cuentas = u.getCuentas();
            try{
                em.getTransaction().begin();
                em.remove(c);
                cuentas.remove(c);
                u.setCuentas(cuentas);
                em.merge(u);
                em.getTransaction().commit();
                System.out.println("Se ha eliminado la cuenta: " + c.toString());
                return true;
                
            }catch(Exception e){
                System.err.println("Error al eliminar cuentas: /n"+e);
                return false;
            }
        }
	
	
}
