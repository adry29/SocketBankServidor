/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import dao.UsuarioDAO;
import java.util.ArrayList;
import java.util.List;
import model.Cuenta;
import model.Usuario;

/**
 *
 * @author adryc
 */
public class UsuarioController {
    
    UsuarioDAO dao = new UsuarioDAO();
    
    public Usuario register(Usuario u){
        dao.register(u);
        return dao.getByName(u.getNickname());
    }
    
    public Usuario login(Usuario u){
        Usuario result = dao.getByName(u.getNickname());
        if(u.getNickname().equals(result.getNickname())
                && u.getPassword().equals(result.getPassword())){
            return result;
        }else{
            return null;
        }
    }
    
    
    
    public List<Usuario> getAll(){
        List<Usuario> result;
        result = dao.getAll();
        return result;
    }
    
    
}
