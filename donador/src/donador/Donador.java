/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

// servidor.java
package donador;

import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import idonador.iDonador;

public class Donador extends UnicastRemoteObject implements iDonador {
    private int suma;
    private final int numServidor;
    private final String[] usuarios;
    
    public Donador(int servidor) throws RemoteException{
        this.usuarios = new String[10];
        numServidor = servidor;
        suma = 0;
    }
    
    @Override
    public int consultaTotal() throws RemoteException {
        
        int subTotal = 0;

        try{
            if(numServidor == 1){
                Registry mireg = LocateRegistry.getRegistry("localhost", 1100);
                iDonador servidor2 = (iDonador)mireg.lookup("mmiservidor2");
                subTotal = this.preguntarSubTotal() + servidor2.preguntarSubTotal();
            } else if(numServidor == 2){                        
                Registry mireg = LocateRegistry.getRegistry("localhost", 1099);
                iDonador servidor1 = (iDonador)mireg.lookup("mmiservidor1");
                subTotal = this.preguntarSubTotal() + servidor1.preguntarSubTotal();
            }
        }catch (NotBoundException e){
        }

        return subTotal;
        
    }
    
    @Override
    public void donar(int valor) throws RemoteException {
        suma = suma + valor;
    }
    
    @Override
    public int registrarUsuario(String usuario, boolean primera) throws RemoteException {
        
        int regisOtro = 0;
        boolean esta = false;
        int efectivo = 0;
        
        try{
            if(numServidor == 1){
                Registry mireg = LocateRegistry.getRegistry("localhost", 1100);
                iDonador servidor2 = (iDonador)mireg.lookup("mmiservidor2");
                regisOtro = servidor2.preguntarRegistrados ();
                esta = servidor2.buscarUsuario(usuario);
            } else if(numServidor == 2){                        
                Registry mireg = LocateRegistry.getRegistry("localhost", 1099);
                iDonador servidor1 = (iDonador)mireg.lookup("mmiservidor1");
                regisOtro = servidor1.preguntarRegistrados();
                esta = servidor1.buscarUsuario(usuario);
            }
        }catch (NotBoundException e){
        }
        if(!esta){
            if(this.preguntarRegistrados() > regisOtro && primera){
                try{
                    if(numServidor == 1){
                        Registry mireg = LocateRegistry.getRegistry("localhost", 1100);
                        iDonador servidor2 = (iDonador)mireg.lookup("mmiservidor2");
                        efectivo = servidor2.registrarUsuario(usuario, false);
                    } else if(numServidor == 2){                        
                        Registry mireg = LocateRegistry.getRegistry("localhost", 1099);
                        iDonador servidor1 = (iDonador)mireg.lookup("mmiservidor1");
                        efectivo = servidor1.registrarUsuario(usuario, false);
                    }
                }catch (NotBoundException e){
                }
            }else{

                int i;
                for(i=0;i<10;i++){
                    if(usuarios[i]==null || usuarios[i].equals(usuario)){
                        break;
                    }
                }
                if(i>9){
                    efectivo = 0;
                }else if(usuario.equals(usuarios[i])){ 
                    efectivo = 0;
                }else{ 
                    usuarios[i] = usuario;
                    efectivo = numServidor;
                }

            }
        }
        
        return efectivo;
        
    }
    
    @Override
    public boolean buscarUsuario(String usuario) throws RemoteException {
        int i;
        for(i=0; i<10; i++){
            if(usuarios[i]==null || usuarios[i].equals(usuario)){
                break;
            }
        }
        return i<10 && usuario.equals(usuarios[i]);
    }
    
    @Override
    public int preguntarSubTotal() throws RemoteException, AccessException {
                return suma;
    }
    
    @Override
    public int preguntarRegistrados() throws RemoteException {
        int i;
        for(i=0;i<10;i++){
            if(usuarios[i]==null){
                break;
            }
        }
        return i;
    }
    
    @Override
    public int preguntarServidor() throws RemoteException{
        return numServidor;
    }
}



