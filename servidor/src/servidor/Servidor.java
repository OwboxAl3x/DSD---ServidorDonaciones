/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

// servidor.java = Programa servidor
package servidor;
import donador.Donador;
import java.net.MalformedURLException;
import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.*;

public class Servidor {
    public static void main(String[] args) throws NotBoundException, AlreadyBoundException {
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());}
        try {
            // Crea una instancia de Donador
            Registry reg=LocateRegistry.createRegistry(1099);
            Donador miservidor = new Donador(1);
            reg.bind("mmiservidor1", miservidor);
            System.out.println("Servidor RemoteException | MalformedURLExceptiondor preparado");
        }catch (RemoteException e) {
            System.out.println("Exception: " + e.getMessage());
        }
    }
}