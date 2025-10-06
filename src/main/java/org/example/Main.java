package org.example;

import org.example.modelo.Direccion;
import org.example.modelo.Empleado;
import org.example.modelo.Telefono;
import org.example.parser.ParserJSON;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ParserJSON parser = new ParserJSON();

        System.out.println("=== SISTEMA DE GESTIÓN DE EMPLEADOS ===");

        // Mostrar empleados existentes 
        System.out.println("\n--- EMPLEADOS EXISTENTES ---");
        parser.mostrarEmpleados();

        // Agregar un nuevo empleado directamente 
        System.out.println("\n--- AGREGANDO NUEVO EMPLEADO ---");
        agregarEmpleadoPrueba(parser);

        // Mostrar empleados después de agregar
        System.out.println("\n--- EMPLEADOS DESPUÉS DE AGREGAR ---");
        parser.mostrarEmpleados();
    }

    private static void agregarEmpleadoPrueba(ParserJSON parser) {
        // Primer empleado
        Empleado empleado1 = new Empleado();
        empleado1.setNombre("Maria");
        empleado1.setApellido("Gonzalez");
        empleado1.setEdad(30);

        Direccion direccion1 = new Direccion("Av. Reforma 123", "Ciudad de México", "CDMX", 06600L);
        empleado1.setDireccion(direccion1);

        ArrayList<Telefono> telefonos1 = new ArrayList<>();
        telefonos1.add(new Telefono("mobile", "55 1234 5678"));
        telefonos1.add(new Telefono("work", "55 8765 4321"));
        empleado1.setTelefonos(telefonos1);

        System.out.println("Agregando empleado 1: " + empleado1.getNombre() + " " + empleado1.getApellido());
        parser.agregarEmpleado(empleado1);

        // Segundo empleado
        Empleado empleado2 = new Empleado();
        empleado2.setNombre("Carlos");
        empleado2.setApellido("Lopez");
        empleado2.setEdad(28);

        Direccion direccion2 = new Direccion("Calle Juarez 456", "Guadalajara", "Jalisco", 44100L);
        empleado2.setDireccion(direccion2);

        ArrayList<Telefono> telefonos2 = new ArrayList<>();
        telefonos2.add(new Telefono("home", "33 5555 8888"));
        telefonos2.add(new Telefono("mobile", "33 9999 1111"));
        empleado2.setTelefonos(telefonos2);

        System.out.println("Agregando empleado 2: " + empleado2.getNombre() + " " + empleado2.getApellido());
        parser.agregarEmpleado(empleado2);
    }


}