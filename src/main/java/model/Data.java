package model;

import java.io.Serializable;

//Modelo utilizado para poder transferir cualquier tipo de objeto entre cliente
//y servidor bajo un mismo tipo de instancia, actúa como un paquete de datos
public class Data<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    //La opción que determina la acción a realizar por el servidor
    Integer opt;
    //El objeto que se envía en la comunicación entre servidor y cliente
    T object;
    //Cambio de valor seleccionado al extraer e ingresar dinero por un cliente
    int change;
    Boolean result;

    public Integer getOpt() {
        return opt;
    }

    public T getObject() {
        return object;
    }

    public int getChange() {
        return change;
    }

    public Boolean getResult() {
        return result;
    }

    public void setOpt(Integer opt) {
        this.opt = opt;
    }

    public void setObject(T object) {
        this.object = object;
    }

    public void setBalance(int change) {
        this.change = change;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }
}
