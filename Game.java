//package Servidor;

//
// YodafyServidorIterativo
// (CC) jjramos, 2012
//
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

//
// Nota: si esta clase extendiera la clase Thread, y el procesamiento lo hiciera el método "run()",
// ¡Podríamos realizar un procesado concurrente! 
//

public class Game{
   public static Datos datos = Datos.getInstance();
    
   public Game(){
   }
   
   public void new_player(Socket socket){
       ProcesadorYodafy p = new ProcesadorYodafy(socket);
       p.start();
   }
   
    public class ProcesadorYodafy extends Thread {
    // Referencia a un socket para enviar/recibir las peticiones/respuestas
    private Socket socketServicio;
    // stream de lectura (por aquí se recibe lo que envía el cliente)
    private BufferedReader inReader;
    // stream de escritura (por aquí se envía los datos al cliente)
    private PrintWriter outPrinter;
    


    // Constructor que tiene como parámetro una referencia al socket abierto en por otra clase
    public ProcesadorYodafy(Socket socketServicio) {
        this.socketServicio = socketServicio;
    }

    

    // Aquí es donde se realiza el procesamiento realmente:
    public void run() {

        String cadena ="";
        String mensaje;
		int jugador=-1;
		boolean primera_vez=true;
		boolean findeljuego=false;
        

        try {
            // Obtiene los flujos de escritura/lectura
            inReader = new BufferedReader(new InputStreamReader(socketServicio.getInputStream()));
            outPrinter = new PrintWriter(socketServicio.getOutputStream(), true);

            do {

			    if(primera_vez){
                    primera_vez=false;
                    cadena = inReader.readLine();
                    System.out.println("Un usuario solicita: " + cadena);
                    
                    
                    if (cadena.contains("jugar")) {
                        //Hasta que no tengamos 2 jugadores
                        
                        if (datos.jugadores < 2) {
                        	mensaje = "true";
                            datos.jugadores++;
                            ///////////////////////////////////////j////////////////
                            outPrinter.println(mensaje);
                            ////////////////////////////////////////////////////////
                            mensaje = "J" + datos.jugadores;     // El servidor envía al usuario su nombre de jugador.
                            outPrinter.println(mensaje);
                            jugador = datos.jugadores;
                           
                            if (datos.jugadores != 2){ 
                                outPrinter.println("Espera mientras se conecta otro jugador..."); 
                                Thread.sleep(6000); 
                            }
                        } 

                        else {
                            mensaje = "false";          // Si el servidor está ocupado con 2 jugadores, hace esperar a los siguientes.
                            outPrinter.println(mensaje);
                        }

                        ////////////////////////////////////////////////////////
                        // El servidor indica la figura inicial
                        ////////////////////////////////////////////////////////
                        if (datos.get_numero() == 1) {
                            datos.set_turno('x');
                        } 
                        else {
                            datos.set_turno('o');
                        }
                        ////////////////////////////////////////////////////////
                        
                        if (datos.jugadores == 2){
                            //mensaje = "T" + datos.numero + datos.tablero;
                            //outPrinter.println(mensaje);
                            System.out.println("Sincronizando jugador... J" + jugador);
                        }
                    } 
				}
				else{
                    cadena="Todavía no ha sido tu turno";
					
                    while (jugador != datos.get_numero()&&!findeljuego){ 
					
                    	if(datos.get_fin()!='c'){
							findeljuego=true;
						}
						else{
                  	        Thread.sleep(500);
							System.out.print("\033[H\033[2J");  
   							System.out.flush();
							System.out.println("Esperando..., turno de: " + datos.get_numero() + " .Yo soy el: " + jugador );
						}
                	}

					if(!findeljuego){
						outPrinter.println("T" + datos.get_numero() + datos.get_tablero());

						cadena = inReader.readLine();
             		   	System.out.println("Un usuario solicita: " + cadena);
					}
					else{
						if(datos.get_fin()=='e')
							outPrinter.println("fine");
						else
							outPrinter.println("fing" + datos.get_fin());
					}	
               
                	if (cadena.charAt(0) == 'C') {

                        if(datos.get_turno()=='o')
                            datos.set_turno('x'); 
                        else datos.set_turno('o');
                            datos.set_tablero(
                               datos.tablero.replace( (cadena.charAt(1) ), 
                                                      datos.get_turno())
                            );
                        
                        System.out.println("El servidor modifica la casilla " + cadena.charAt(1) + " por " + datos.get_turno());
                         

                        
                        if ((datos.get_tablero().charAt(0) == 'o' || datos.get_tablero().charAt(0) == 'x') && (datos.get_tablero().charAt(0) == datos.get_tablero().charAt(1)) && (datos.get_tablero().charAt(0) == datos.get_tablero().charAt(2))) {
                            datos.set_fin( (char) (datos.get_numero() + '0'));
                        } 
                        else if ((datos.get_tablero().charAt(0) == 'o' || datos.get_tablero().charAt(0) == 'x') && (datos.get_tablero().charAt(0) == datos.get_tablero().charAt(3)) && (datos.get_tablero().charAt(0) == datos.get_tablero().charAt(6))) {
                            datos.set_fin( (char) (datos.get_numero() + '0'));
                        } 
                        else if ((datos.get_tablero().charAt(0) == 'o' || datos.get_tablero().charAt(0) == 'x') && (datos.get_tablero().charAt(0) == datos.get_tablero().charAt(4)) && (datos.get_tablero().charAt(0) == datos.get_tablero().charAt(8))) {
                                datos.set_fin( (char) (datos.get_numero() + '0'));
                        } 
                        else if ((datos.get_tablero().charAt(3) == 'o' || datos.get_tablero().charAt(3) == 'x') && (datos.get_tablero().charAt(3) == datos.get_tablero().charAt(4)) && (datos.get_tablero().charAt(3) == datos.get_tablero().charAt(5))) {
                                datos.set_fin( (char) (datos.get_numero() + '0'));
                        } 
                        else if ((datos.get_tablero().charAt(6) == 'o' || datos.get_tablero().charAt(6) == 'x') && (datos.get_tablero().charAt(6) == datos.get_tablero().charAt(7)) && (datos.get_tablero().charAt(6) == datos.get_tablero().charAt(8))) {
                            datos.set_fin( (char) (datos.get_numero() + '0'));
                        } 
                        else if ((datos.get_tablero().charAt(1) == 'o' || datos.get_tablero().charAt(1) == 'x') && (datos.get_tablero().charAt(1) == datos.get_tablero().charAt(4)) && (datos.get_tablero().charAt(1) == datos.get_tablero().charAt(7))) {
                            datos.set_fin( (char) (datos.get_numero() + '0'));
                        } 
                        else if ((datos.get_tablero().charAt(2) == 'o' || datos.get_tablero().charAt(2) == 'x') && (datos.get_tablero().charAt(2) == datos.get_tablero().charAt(5)) && (datos.get_tablero().charAt(2) == datos.get_tablero().charAt(8))) {
                            datos.set_fin( (char) (datos.get_numero() + '0'));
                        } 
                        else if ((datos.get_tablero().charAt(6) == 'o' || datos.get_tablero().charAt(6) == 'x') && (datos.get_tablero().charAt(6) == datos.get_tablero().charAt(4)) && (datos.get_tablero().charAt(6) == datos.get_tablero().charAt(2))) {
                            datos.set_fin( (char) (datos.get_numero() + '0'));
                        } 
     					else if ((datos.get_tablero().charAt(0) == 'o' || datos.get_tablero().charAt(0) == 'x') && (datos.get_tablero().charAt(1) == 'o' || datos.get_tablero().charAt(1) == 'x') && (datos.get_tablero().charAt(2) == 'o' || datos.get_tablero().charAt(2) == 'x') && (datos.get_tablero().charAt(3) == 'o' || datos.get_tablero().charAt(3) == 'x') && (datos.get_tablero().charAt(4) == 'o' || datos.get_tablero().charAt(4) == 'x') && (datos.get_tablero().charAt(5) == 'o' || datos.get_tablero().charAt(5) == 'x') && (datos.get_tablero().charAt(6) == 'o' || datos.get_tablero().charAt(6) == 'x') && (datos.get_tablero().charAt(7) == 'o' || datos.get_tablero().charAt(7) == 'x') && (datos.get_tablero().charAt(8) == 'o' || datos.get_tablero().charAt(8) == 'x')) {
                            datos.set_fin( 'e');
                        }
                        else {
                            datos.set_fin('c');
    						System.out.println("Continua el juego chavales");
                        }

                        if (datos.get_fin() == 'c') {
                            if (datos.get_numero() == 1){
                                datos.set_numero(2);
                                System.out.println("El servidor cede el turno a T2" );
    							Thread.sleep(500);
                            }
    										

                            else if (datos.get_numero() == 2){
                                datos.set_numero(1);
                                System.out.println("El servidor cede el turno a T1" );
    							Thread.sleep(500);
                            }
                        } 

                        else if (datos.get_fin() == 'e') {
                            outPrinter.println("fine");
    						System.out.println("Hay un empate");
                        } 

                        else {
    						Thread.sleep(3000);
                            outPrinter.println("fing" + datos.get_fin());
    						System.out.println("El ganador es J" + datos.get_fin());
                        }

                    } 
                    
                    else if (cadena.contains("fin")) {

                    mensaje = "finTironDelCable";
                    outPrinter.println(mensaje);

                    }

                }
			} while (datos.get_fin() == 'c');
				
        } catch (IOException e) {
            System.err.println("Error al obtener los flujos de entrada/salida.");

        } catch (InterruptedException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    }
}