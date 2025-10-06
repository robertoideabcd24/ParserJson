package org.example.modelo;

import java.util.List;

public class Empleado {
    private String nombre;
    private String apellido;
    private int edad;
    private Direccion direccion;
    private List<Telefono> telefonos;

    public Empleado() {
    }

    public Empleado(String nombre, String apellido, int edad, Direccion direccion, List<Telefono> telefonos) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.edad = edad;
        this.direccion = direccion;
        this.telefonos = telefonos;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public Direccion getDireccion() {
        return direccion;
    }

    public void setDireccion(Direccion direccion) {
        this.direccion = direccion;
    }

    public List<Telefono> getTelefonos() {
        return telefonos;
    }

    public void setTelefonos(List<Telefono> telefonos) {
        this.telefonos = telefonos;
    }

    @Override
    public String toString() {
        return "Empleado{" +
                "nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", edad=" + edad +
                ", direccion=" + direccion +
                ", telefonos=" + telefonos +
                '}';
    }


}
