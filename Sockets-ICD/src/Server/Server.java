package Server;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public final static int DEFAULT_PORT = 5025; 
    
    
    public static void main(String[] args) 
    {
        int port = DEFAULT_PORT; 

        if (args.length > 0) {
            try {
                port = Integer.parseInt(args[0]);
            }
            catch (NumberFormatException e) {
                System.err.println("Erro no porto indicado");
            }
        }
        
        ServerSocket serverSocket = null;

        try {
        	// Criar socket com uma determinada porta
            serverSocket = new ServerSocket(port);

            Socket newSock    = null;

            for( ; ; ) {
                System.out.println("Servidor TCP aguarda ligacao no porto: " + port + "..." );

                // Espera connect do cliente
                newSock = serverSocket.accept(); 
                
                Thread th = new HandleConnectionThread (newSock);
                th.start();
            }
        } 
        catch (IOException e) {
            System.err.println("Excep��o no servidor: " + e);
        }
    } // end main

} // end ServidorTCP



