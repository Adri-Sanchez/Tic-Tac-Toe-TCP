/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//package Servidor;

import java.util.concurrent.atomic.*;

/**
 *
 * @author adri_
 */
public class Datos {

    public static int jugadores;
    public static char turno;
    public static String tablero;
   	public static String chat;
    public static int numero;
    public static char fin;

   	private static Datos instance = null;
   	protected Datos() {
   	}
   	public static Datos getInstance() {
      if(instance == null) {
         instance = new Datos();
				 jugadores=0;
				 tablero="123456789";
				 chat="";
				 numero=((int) (Math.random() * 2) + 1);
				 fin = 'c';
      }
      return instance;
   } 

    public static int get_numero(){
        return numero;
    }

		public static void set_numero(int i){

				numero=i;
		}
    public static void incrementar_jugadores() {
        jugadores = jugadores + 1;
    }

    public  static void decrementar_jugadores() {
        jugadores = jugadores - 1;
    }

    public  static int get_jugadores() {
        return jugadores;
    }

    public  static char get_turno() {
        return turno;
    }

    public  static void set_turno(char t) {
        turno = t;
    }

    public   static String get_tablero() {
        return tablero;
    }

    public   static void set_tablero(String t) {
        tablero = t;
    }

    public   static String get_chat() {
        return chat;
    }

    public   static void set_chat(String mensaje) {
        chat = chat + mensaje;
    }

    public   static void set_fin(char f) {
        fin = f;
    }

    public static char get_fin() {
        return fin;
    }
}
