package controllers;

import dao.CuentaDAO;
import java.util.ArrayList;
import java.util.List;
import model.Cuenta;
import model.Usuario;

public class CuentaController {

    CuentaDAO dao = new CuentaDAO();

    public Cuenta register(Cuenta cuenta) {
        dao.register(cuenta);
        return (cuenta);

    }

    public Cuenta getByID(Long id) {
        Cuenta result = null;
        result = dao.getById((long) id);
        return result;
    }

    //Suma la cantidad @addition a la cuenta seleccionada
    public Cuenta addSaldo(int addition, Cuenta cuenta) {
        Cuenta result = null;
        result = dao.getById(cuenta.getId());
        int newSaldo = result.getSaldo() + addition;
        result.setSaldo(newSaldo);
        dao.edit(result);
        return result;
    }

    //Extrae la cantidad @sustraction a la cuenta seleccionada
    public Cuenta takeSaldo(int sustraction, Cuenta cuenta) {
        Cuenta result = null;
        result = dao.getById(cuenta.getId());
        int newSaldo = result.getSaldo() - sustraction;

        if (newSaldo < 0) {
            return result;
        } else {
            result.setSaldo(newSaldo);
            dao.edit(result);
            return result;
        }

    }

    public List<Cuenta> getByUser(Usuario u){
        List<Cuenta> result = new ArrayList<>();
        result = dao.getByUser(u);
        return result;
    }
    
    public boolean delete(Cuenta c){
        return dao.delete(c);
    }

}
