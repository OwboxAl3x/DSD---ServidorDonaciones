/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

// iDonador.java
package idonador;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface iDonador extends Remote {
    int consultaTotal() throws RemoteException;
    void donar (int valor) throws RemoteException;
    public int registrarUsuario(String usuario, boolean primera) throws RemoteException;
    public boolean buscarUsuario(String usuario) throws RemoteException;
    
    int preguntarSubTotal() throws RemoteException;
    int preguntarRegistrados () throws RemoteException;
    int preguntarServidor () throws RemoteException;
}