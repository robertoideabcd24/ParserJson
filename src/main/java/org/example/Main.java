package org.example;

import org.example.modelo.Direccion;
import org.example.modelo.Empleado;
import org.example.modelo.Telefono;
import org.example.parser.ParserJSON;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        ParserJSON parser = new ParserJSON();
        boolean salir = false;
        int opcion;

        System.out.println("Gestion de mepleados");
        while(!salir){
            System.out.println("MENÚ");
            System.out.println("1. Agregar empleado");
            System.out.println("2. Eliminar empeldao");
            System.out.println("3. Modificar empleado");
            System.out.println("4. Buscar empleado");
            System.out.println("5. Mostrar todos los empleados");
            System.out.println("6. Salir");
            opcion = leerEntero();

            switch (opcion){
                case 1:
                    agregarEmpleado(parser);
                case 2:
                    System.out.println("Función ELIMINAR aún no implementada.");
                    break;
                case 3:
                    System.out.println("Función MODIFICAR aún no implementada.");
                    break;
                case 4:
                    System.out.println("Función BUSCAR aún no implementada.");
                    break;
                case 5:
                    parser.mostrarEmpleados();
                    break;
                case 6:
                    salir = true;
                    System.out.println("Saliendo del sistema...");
                    break;
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
                    break;
            }//fin case
        }
    }

    private static void agregarEmpleado(ParserJSON parser) {
        Empleado empleado = new Empleado();
        Direccion direccion;
        ArrayList<Telefono> telefonos = new ArrayList<>();

        String calle, ciudad, estado, tipo, numero, respuesta;
        Long cp;

        System.out.println("Agregando nuevo empleado...");

        System.out.print("Nombre: ");
        empleado.setNombre(scanner.nextLine());

        System.out.print("Apellido: ");
        empleado.setApellido(scanner.nextLine());

        System.out.print("Edad: ");
        empleado.setEdad(leerEntero());

        System.out.println("Direccion");
        System.out.print("Calle: ");
        calle = scanner.nextLine();

        System.out.print("Ciudad: ");
        ciudad = scanner.nextLine();

        System.out.print("Estado: ");
        estado = scanner.nextLine();

        System.out.print("Código Postal: ");
        cp = leerLong();

        direccion = new Direccion(calle, ciudad, estado, cp);
        empleado.setDireccion(direccion);

        System.out.println("Telefonos");

        while(true){
            System.out.println("Tipo de telefono (home, mobile, work, etc): ");
            tipo = scanner.nextLine();

            System.out.print("Numero: ");
            numero = scanner.nextLine();
            telefonos.add(new Telefono(tipo, numero));

            System.out.println("¿Deseas agregar otro telefono (s/n): ");
            respuesta = scanner.nextLine();

            if(!respuesta.equalsIgnoreCase("s")){
                break;
            }
        }// fin while

        empleado.setTelefonos(telefonos);
        System.out.println("Guardando empleado...");
        parser.agregarEmpleado(empleado);
    }

    private static int leerEntero(){
        String entrada;
        while(true){
            try{
                entrada = scanner.nextLine();
                return Integer.parseInt(entrada);
            }catch (NumberFormatException e){
                System.out.println("Ingresar un número valido: ");
            }
        }
    }//fin del metodo leer entero

    private static long leerLong(){
        String entrada;
        while(true){
            try{
                entrada = scanner.nextLine();
                return Long.parseLong(entrada);
            }catch (NumberFormatException e){
                System.out.println("Ingresar un número valido: ");
            }
        }
    }


}