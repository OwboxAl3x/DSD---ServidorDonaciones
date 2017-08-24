/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
// cliente.java
package cliente;

import java.rmi.registry.LocateRegistry;
import java.rmi.*;
import java.rmi.registry.Registry;
import java.util.Scanner;
import idonador.iDonador;


public class Cliente {
    public static void main(String[] args) {
        String host = "", opcion = "", user = "", donacion = "";
        int numServidores = 2, serv = 0;
        String server1 = "mmiservidor1", server2 = "mmiservidor2";
        boolean cerrar = false, haDonado = false;
        Scanner teclado = new Scanner (System.in);
        // Crea e instala el gestor de seguridad
        if (System.getSecurityManager() == null) {System.setSecurityManager(new SecurityManager());}
        try {
        // Crea el stub para el cliente especificando el nombre del servidor1
        Registry mireg1 = LocateRegistry.getRegistry("localhost", 1099);
        iDonador miservidor1 = (iDonador)mireg1.lookup(server1);
        
        while(!cerrar){
            
            System.out.println("Menu del sistema:\n\n    1.Registrarse.\n    2.Donar.\n    3.Consultar el total donado.\n    4.Cerrar.\n\nEleccion:");
            opcion = teclado.nextLine();
            if(opcion.equals("1") && user.equals("")){
                
                System.out.println("\nElige tu seudonimo:");
                user = teclado.nextLine();
                
                serv = miservidor1.registrarUsuario(user, true);
                if(serv==1 || serv==2){
                    System.out.println("\nUsuario registrado con exito.");
                    if(serv==2){
                        // Crea el stub para el cliente especificando el nombre del servidor2
                        Registry mireg2 = LocateRegistry.getRegistry("localhost", 1100);
                        iDonador miservidor2 = (iDonador)mireg2.lookup(server2);
                        miservidor1 = (iDonador)mireg2.lookup(server2);
                        miservidor2 = (iDonador)mireg1.lookup(server1);
                    }                    
                }else{ System.out.println("\nFallo registrando el usuario."); user=""; }
                
            }else if(opcion.equals("2")){
                
                if(miservidor1.buscarUsuario(user)){
                    System.out.println("\nCuanto va a donar?:");
                    donacion = teclado.nextLine();
                    miservidor1.donar(Integer.parseInt(donacion));
                    System.out.println("\nHa donado " + donacion + "€.");
                    haDonado = true;
                }else System.out.println("\nNo estas registrado y es un requisito para donar.");
                
            }else if(opcion.equals("3")){
                
                if(miservidor1.buscarUsuario(user) && haDonado){
                    System.out.println("\nSe ha donado un total de " + miservidor1.consultaTotal() + "€.");
                }else System.out.println("\nNo estas registrado o no ha donado aun y son requisitos para consultar el total donado.");
                
            }else if(opcion.equals("4")){
                
                cerrar=true;
                
            }
            
        }
        
        } catch(NotBoundException | RemoteException e) {
        System.err.println("Exception del sistema: " + e);
        }
        System.exit(0);
    }
}