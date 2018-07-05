//package Cliente;

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
import java.net.UnknownHostException;
import java.util.*;

public class YodafyClienteTCP {

    public static void main(String[] args) throws InterruptedException {
        boolean play;
        int eleccion;
        Scanner cin;
        String cadena, jugador, mensaje;
        // Nombre del host donde se ejecuta el servidor:
        String host = "localhost";
        // Puerto en el que espera el servidor:
        int port = 8989;

        // Socket para la conexión TCP
        Socket socketServicio = null;
        // Menú inicial
        /////////////////////////////////////////////////////
        System.out.println("Tres en Raya (Online Game of the Year edition)\n"
                + "1.- Jugar \n2.- Salir \n\nIntroduce una opción: ");
        /////////////////////////////////////////////////////

        // El usuario elige la opción
        /////////////////////////////////////////////////////
        cin = new Scanner(System.in);
        eleccion = cin.nextInt();
        /////////////////////////////////////////////////////

		final String ANSI_RESET = "\u001B[0m";
		final String ANSI_BLACK = "\u001B[30m";
		final String ANSI_RED = "\u001B[31m";
		final String ANSI_GREEN = "\u001B[32m";
		final String ANSI_YELLOW = "\u001B[33m";
		final String ANSI_BLUE = "\u001B[34m";
		final String ANSI_PURPLE = "\u001B[35m";
		final String ANSI_CYAN = "\u001B[36m";
		final String ANSI_WHITE = "\u001B[37m";

        if (eleccion == 1) {
            try {
                // Creamos un socket que se conecte a "hst" y "port":
                //////////////////////////////////////////////////////
                socketServicio = new Socket(host, port);
                //////////////////////////////////////////////////////			

                PrintWriter outPrinter = new PrintWriter(socketServicio.getOutputStream(), true);
                BufferedReader inReader = new BufferedReader(new InputStreamReader(socketServicio.getInputStream()));

                // El servidor nos indica si podemos jugar
                //////////////////////////////////////////////////////
                outPrinter.println("jugar");
                play = Boolean.parseBoolean(inReader.readLine());

                while (!play) {
                    System.out.println("\nEl servidor está ocupado en estos momentos... ");
                    Thread.sleep(5000);
                    outPrinter.println("jugar");
                    play = Boolean.parseBoolean(inReader.readLine());

                }
                //////////////////////////////////////////////////////

                // El servidor nos indica nuestro número de jugador
                //////////////////////////////////////////////////////
                jugador = inReader.readLine();
                System.out.println("\nEres el jugador:  " + jugador);
                //////////////////////////////////////////////////////

                do {
////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////
										cadena="nada";
										System.out.println("Esperando al otro jugador");
                    cadena = inReader.readLine();
                    System.out.println("El servidor envía: " + cadena);

                    if (cadena.charAt(0) == 'T' && cadena.charAt(1) == jugador.charAt(1)) {        // Cadena se gestiona por CX y jugador por JX

												System.out.print("\033[H\033[2J");  
												System.out.flush();
												System.out.println(ANSI_WHITE + "- - - - - - - -" + ANSI_RESET);
												System.out.print(ANSI_WHITE + "|" + ANSI_RESET);
												int j=0;
												for (int i = 0; i < 9; i++) {
																if(cadena.charAt(i+2)=='o')
																		System.out.print(ANSI_GREEN + " " + cadena.charAt(i + 2) + ANSI_RESET + ANSI_WHITE + " | " + ANSI_RESET);
																else if(cadena.charAt(i+2)=='x')
																		System.out.print(ANSI_RED + " " + cadena.charAt(i + 2) + ANSI_RESET + ANSI_WHITE + " | " + ANSI_RESET);
																else
																		System.out.print(ANSI_WHITE + " " + cadena.charAt(i + 2) + " | " + ANSI_RESET);

																j++;
                                if (j==3) {
																		j=0;                 
                                    System.out.println(ANSI_WHITE + "\n- - - - - - - -" + ANSI_RESET);
																		System.out.print(ANSI_WHITE + "|" + ANSI_RESET);
                                }
                            }
                        System.out.print("\n\nTres en Raya (Online Game of the Year edition)\n"
                                + "1.- Introduce casilla (Número). \n2.- Salir \n\nIntroduce una opción: ");

                        eleccion = cin.nextInt();

                        if (eleccion == 1) {
														System.out.print("\033[H\033[2J");  
    												System.out.flush();

                            System.out.flush();
	
														System.out.println(ANSI_WHITE + "- - - - - - - -" + ANSI_RESET);
														System.out.print(ANSI_WHITE + "|" + ANSI_RESET);
                            
														j=0;
                            for (int i = 0; i < 9; i++) {
																if(cadena.charAt(i+2)=='o')
																		System.out.print(ANSI_GREEN + " " + cadena.charAt(i + 2) + ANSI_RESET + ANSI_WHITE + " | " + ANSI_RESET);
																else if(cadena.charAt(i+2)=='x')
																		System.out.print(ANSI_RED + " " + cadena.charAt(i + 2) + ANSI_RESET + ANSI_WHITE + " | " + ANSI_RESET);
																else
																		System.out.print(ANSI_WHITE + " " + cadena.charAt(i + 2) + " | " + ANSI_RESET);

																j++;
                                if (j==3) {
																		j=0;                 
                                    System.out.println(ANSI_WHITE + "\n- - - - - - - -" + ANSI_RESET);
																		System.out.print(ANSI_WHITE + "|" + ANSI_RESET);
                                }
                            }
														System.out.println("\nIntroduce el número de casilla: ");
                            eleccion = cin.nextInt();

														while(cadena.charAt(eleccion+1)=='o' || cadena.charAt(eleccion+1)=='x'){
                           		System.out.println("\nCasilla ya ocupada: ");
                            	eleccion = cin.nextInt();
														}
	                          System.out.println("\nHas elegido la casilla: " + eleccion);
                            outPrinter.println("C" + eleccion);
														
														Thread.sleep(1000);

                        }
                        
                        else if (eleccion == 2) {
                            outPrinter.println("fin");                      // FIN
                        }	
											
                    }             
////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////
                } while (!cadena.contains("fin"));

                if (cadena.contains("fine")) {
                    System.out.println(ANSI_CYAN + "\n******************************Empate************************************\n" + ANSI_RESET);
										Thread.sleep(5000);
                } 
                else if (cadena.contains("fing")) {

                    System.out.println(ANSI_BLUE + "\n******************************Gana J" + cadena.charAt(4) + "************************************\n" + ANSI_RESET);
										Thread.sleep(5000);
                } 
                else {

                    System.out.println(ANSI_BLACK + "\n******************************Ragequit xddd*****************************\n" + ANSI_RESET);
										Thread.sleep(5000);
                }

                // Una vez terminado el servicio, cerramos el socket (automáticamente se cierran
                // el inpuStream  y el outputStream)
                //////////////////////////////////////////////////////
                socketServicio.close();
                //////////////////////////////////////////////////////

                // Excepciones:
            } catch (UnknownHostException e) {
                System.err.println("Error: Nombre de host no encontrado.");
            } catch (IOException e) {
                System.err.println("Error de entrada/salida al abrir el socket.");
            }
        } else {
            System.out.println("Adios. :)");
        }
    }
}
