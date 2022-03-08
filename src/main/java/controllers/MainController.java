package controllers;

import java.net.ServerSocket;

import dao.CuentaDAO;
import dao.UsuarioDAO;
import java.util.List;
import model.Cuenta;
import model.Data;
import model.Usuario;
import utils.GesConection;

public class MainController implements Runnable {

    ServerSocket serv;
    GesConection gc = new GesConection(9640);
    UsuarioController ucontroller = new UsuarioController();
    UsuarioDAO udao = new UsuarioDAO();
    CuentaController ccontroller = new CuentaController();
    CuentaDAO cdao = new CuentaDAO();

    public void MainControllerActions(Object dataObject) {
        Data<?> data = (Data<?>) dataObject;

        switch (data.getOpt()) {

            //Caso de registro de usuario
            case 1:
                //Recibimos el usuario desde el cliente
                //Lo registramos en la BBDD y lo enviamos de vuelta al cliente
                Data<Usuario> registerData = (Data<Usuario>) data;
                try {

                    Usuario registered = ucontroller.register(registerData.getObject());
                    if (registered != null) {
                        registerData.setResult(true);
                        registerData.setObject(registered);
                    } else {
                        registerData.setResult(false);
                    }
                    gc.sendObject(registerData);
                } catch (Exception e) {
                    System.err.println("Error en la comunicación de registro: " + e);
                }
                break;

            //Caso de inicio de sesión
            case 2:
                //Recibe los datos del usuario
                Data<Usuario> loginData = (Data<Usuario>) data;
                Usuario logged;
                //Devuelve el usuario que coincida con los datos recibidos
                //Si no encuentra ningún usuario, devuelve null
                logged = ucontroller.dao.getByName(loginData.getObject().getNickname());
                System.out.println(logged);
                loginData.setObject(logged);
                gc.sendObject(loginData);

            //Ver cuentas del cliente
            case 3:
                //Recibe el usuario del que se quieran obtener las cuentas y devuelve sus cuentas asociadas en una
                //lista
                Data<Usuario> usuario = (Data<Usuario>) data;
                try {
                    List<Cuenta> listacuentas = ccontroller.getByUser(usuario.getObject());
                    Data<List<Cuenta>> listSent = new Data<>();
                    listSent.setResult(true);
                    listSent.setObject(listacuentas);
                    gc.sendObject(listSent);

                } catch (Exception e) {
                    System.out.println("Error en la comunicación al revisar cuentas: " + e);
                }
                break;

            //Añadir dinero
            case 4:
                //Realiza un ingreso en una cuenta determinada
                Data<Cuenta> addData = (Data<Cuenta>) data;
                try {
                    addData.setObject(ccontroller.getByID(addData.getObject().getId()));
                    System.out.println(addData.getObject().toString() + " " + data.getChange());
                    Cuenta result2 = ccontroller.addSaldo(addData.getChange(), addData.getObject());
                    if (result2 != null) {
                        addData.setResult(true);
                        addData.setObject(result2);
                    } else {
                        addData.setResult(false);
                    }
                    gc.sendObject(addData);
                } catch (Exception e) {
                    System.err.println("Error en la comunicación al añadir saldo: " + e);
                }
                break;

            //Retirar dinero
            case 5:
                //Realiza una extracción de la cuenta elegida
                //Si el valor a extraer recibido es mayor que el que se encuentra
                //en la BBDD, entonces no realiza la transacción, para evitar valores negativos
                Data<Cuenta> takeData = (Data<Cuenta>) data;
                try {
                    takeData.setObject(ccontroller.getByID(takeData.getObject().getId()));
                    Cuenta result3 = ccontroller.takeSaldo(takeData.getChange(), takeData.getObject());
                    if (result3 != null) {
                        takeData.setResult(true);
                        takeData.setObject(result3);
                    } else {
                        takeData.setResult(false);
                    }
                    gc.sendObject(takeData);
                } catch (Exception e) {
                    System.err.println("Error en la comunicación al extraer saldo: " + e);
                }
                break;

            //Ver todos los usuarios
            case 6:
                //Devuelve al cliente una lista con todos los usuarios registrados
                //Recibe la información del usuario que la solicita, pero
                //no hace nada con dicha información
                Data<Usuario> usuario2 = (Data<Usuario>) data;
                try {
                    List<Usuario> listausuarios = ucontroller.getAll();
                    Data<List<Usuario>> listSent = new Data<>();
                    listSent.setResult(true);
                    listSent.setObject(listausuarios);
                    gc.sendObject(listSent);

                } catch (Exception e) {
                    System.out.println("Error en la comunicación al revisar usuarios: " + e);
                }
                break;

            //Eliminar cuenta
            case 7:
                Data<Long> cuenta = (Data<Long>) data;
                Data<Cuenta> bool = new Data<>();
                Cuenta c = ccontroller.getByID(cuenta.getObject());
                System.out.println(c);
                if (c != null) {
                    ccontroller.delete(c);
                    gc.sendObject(bool);
                } else {
                    bool.setObject(null);
                    gc.sendObject(bool);
                }
                break;

            //Crear cuenta
            case 8:
                //Recibe el nombre de un cliente. Si dicho cliente existe en la BBDD,
                //crea una nueva cuenta en dicho cliente y la devuelve al cliente. En caso contrario,
                //devuelve null
                Data<String> dueño = (Data<String>) data;
                Cuenta result = null;
                try {
                    Data<Cuenta> cuentacreada = new Data<Cuenta>();
                    Usuario aux = udao.getByName(dueño.getObject());
                    System.out.println(aux.toString());
                    if (aux != null) {
                        result = ccontroller.register(new Cuenta(aux));
                    } else {

                        cuentacreada.setObject(null);
                    }
                    cuentacreada.setResult(true);
                    cuentacreada.setObject(result);
                    gc.sendObject(cuentacreada);
                } catch (Exception e) {
                    System.out.println("Error en la comunicación al crear cuenta" + e);
                }
        }
    }

    public void run() {
        try {
            System.out.println("Servidor iniciado");
            while (true) {
                System.out.println("Esperando peticiones:");
                Object obj = gc.getObjectFromClient();
                MainControllerActions(obj);
            }
        } catch (Exception e) {
            System.err.println("Error en la ejecución del servidor: " + e);
        }
    }
}
