package org.example.parser;

import jakarta.json.*;
import org.example.modelo.Direccion;
import org.example.modelo.Empleado;
import org.example.modelo.Telefono;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ParserJSON {

    private static final String ARCHIVO_JSON = "empleados.json";

    public List<Empleado> obtenerEmpleados() {
        List<Empleado> empleados = new ArrayList<>();

        try {
            File file = new File(ARCHIVO_JSON);
            if (!file.exists() || file.length() == 0) {
                return empleados; 
            }

            JsonReader reader = Json.createReader(new FileInputStream(file));
            JsonStructure jsonStructure = reader.read();
            reader.close();

            // Verificar si es un objeto JSON válido
            if (jsonStructure.getValueType() == JsonValue.ValueType.OBJECT) {
                JsonObject jsonObject = (JsonObject) jsonStructure;

                // Buscar la clave "empleados" (array)
                if (jsonObject.containsKey("empleados")) {
                    JsonArray empleadosArray = jsonObject.getJsonArray("empleados");
                    for (JsonValue empleadoValue : empleadosArray) {
                        if (empleadoValue.getValueType() == JsonValue.ValueType.OBJECT) {
                            JsonObject empleadoObj = (JsonObject) empleadoValue;
                            Empleado empleado = parsearEmpleado(empleadoObj);
                            empleados.add(empleado);
                        }
                    }
                }
                // Buscar la clave "datos" 
                else if (jsonObject.containsKey("datos")) {
                    JsonObject datosObj = jsonObject.getJsonObject("datos");
                    Empleado empleado = parsearEmpleado(datosObj);
                    empleados.add(empleado);
                }
                // Si el objeto raíz tiene directamente los campos de empleado
                else if (jsonObject.containsKey("firstName") || jsonObject.containsKey("nombre")) {
                    Empleado empleado = parsearEmpleado(jsonObject);
                    empleados.add(empleado);
                }
            }

        } catch (Exception e) {
            System.out.println("Error al leer el archivo JSON: " + e.getMessage());
            e.printStackTrace();
        }

        return empleados;
    }

    private Empleado parsearEmpleado(JsonObject empleadoObj) {
        Empleado empleado = new Empleado();

        // Datos básicos del empleado 
        if (empleadoObj.containsKey("firstName")) {
            empleado.setNombre(empleadoObj.getString("firstName"));
        } else if (empleadoObj.containsKey("nombre")) {
            empleado.setNombre(empleadoObj.getString("nombre"));
        }

        if (empleadoObj.containsKey("lastName")) {
            empleado.setApellido(empleadoObj.getString("lastName"));
        } else if (empleadoObj.containsKey("apellido")) {
            empleado.setApellido(empleadoObj.getString("apellido"));
        }

        if (empleadoObj.containsKey("age")) {
            empleado.setEdad(empleadoObj.getInt("age"));
        } else if (empleadoObj.containsKey("edad")) {
            empleado.setEdad(empleadoObj.getInt("edad"));
        }

        // Dirección
        if (empleadoObj.containsKey("address")) {
            JsonObject dirObj = empleadoObj.getJsonObject("address");
            Direccion direccion = new Direccion(
                    dirObj.getString("streetAddress", ""),
                    dirObj.getString("city", ""),
                    dirObj.getString("state", ""),
                    dirObj.getInt("postalCode", 0)
            );
            empleado.setDireccion(direccion);
        } else if (empleadoObj.containsKey("direccion")) {
            JsonObject dirObj = empleadoObj.getJsonObject("direccion");
            Direccion direccion = new Direccion(
                    dirObj.getString("calle", ""),
                    dirObj.getString("ciudad", ""),
                    dirObj.getString("estado", ""),
                    dirObj.getInt("numero", 0)
            );
            empleado.setDireccion(direccion);
        }

        // Teléfonos
        if (empleadoObj.containsKey("phoneNumbers")) {
            JsonArray telefonosArray = empleadoObj.getJsonArray("phoneNumbers");
            ArrayList<Telefono> telefonos = new ArrayList<>();

            for (JsonValue telefonoValue : telefonosArray) {
                if (telefonoValue.getValueType() == JsonValue.ValueType.OBJECT) {
                    JsonObject telObj = (JsonObject) telefonoValue;
                    Telefono telefono = new Telefono(
                            telObj.getString("type", ""),
                            telObj.getString("number", "")
                    );
                    telefonos.add(telefono);
                }
            }
            empleado.setTelefonos(telefonos);
        } else if (empleadoObj.containsKey("telefonos")) {
            JsonArray telefonosArray = empleadoObj.getJsonArray("telefonos");
            ArrayList<Telefono> telefonos = new ArrayList<>();

            for (JsonValue telefonoValue : telefonosArray) {
                if (telefonoValue.getValueType() == JsonValue.ValueType.OBJECT) {
                    JsonObject telObj = (JsonObject) telefonoValue;
                    Telefono telefono = new Telefono(
                            telObj.getString("tipo", ""),
                            telObj.getString("numero", "")
                    );
                    telefonos.add(telefono);
                }
            }
            empleado.setTelefonos(telefonos);
        }

        return empleado;
    }

    public void agregarEmpleado(Empleado nuevoEmpleado) {
        try {
            System.out.println("Leyendo empleados existentes...");
            List<Empleado> empleadosExistentes = obtenerEmpleados();
            System.out.println("Empleados existentes: " + empleadosExistentes.size());

            // Agregar el nuevo empleado a la lista existente
            empleadosExistentes.add(nuevoEmpleado);
            System.out.println("Total de empleados después de agregar: " + empleadosExistentes.size());

            // Guardar todos los empleados (existentes + nuevo)
            guardarEmpleados(empleadosExistentes);
            System.out.println("Empleado agregado exitosamente.");

        } catch (Exception e) {
            System.out.println("Error al agregar empleado: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void guardarEmpleados(List<Empleado> empleados) {
        try (JsonWriter writer = Json.createWriter(new FileOutputStream(ARCHIVO_JSON))) {

            JsonArrayBuilder empleadosArrayBuilder = Json.createArrayBuilder();

            for (Empleado empleado : empleados) {
                JsonObjectBuilder empleadoBuilder = Json.createObjectBuilder()
                        .add("firstName", empleado.getNombre() != null ? empleado.getNombre() : "")
                        .add("lastName", empleado.getApellido() != null ? empleado.getApellido() : "")
                        .add("age", empleado.getEdad());

                // Construir dirección
                if (empleado.getDireccion() != null) {
                    JsonObjectBuilder direccionBuilder = Json.createObjectBuilder()
                            .add("streetAddress", empleado.getDireccion().getCalle() != null ? empleado.getDireccion().getCalle() : "")
                            .add("city", empleado.getDireccion().getCiudad() != null ? empleado.getDireccion().getCiudad() : "")
                            .add("state", empleado.getDireccion().getEstado() != null ? empleado.getDireccion().getEstado() : "")
                            .add("postalCode", empleado.getDireccion().getCp());
                    empleadoBuilder.add("address", direccionBuilder);
                }

                // Construir teléfonos
                if (empleado.getTelefonos() != null && !empleado.getTelefonos().isEmpty()) {
                    JsonArrayBuilder telefonosArrayBuilder = Json.createArrayBuilder();
                    for (Telefono telefono : empleado.getTelefonos()) {
                        JsonObjectBuilder telefonoBuilder = Json.createObjectBuilder()
                                .add("type", telefono.getTipo() != null ? telefono.getTipo() : "")
                                .add("number", telefono.getNumero() != null ? telefono.getNumero() : "");
                        telefonosArrayBuilder.add(telefonoBuilder);
                    }
                    empleadoBuilder.add("phoneNumbers", telefonosArrayBuilder);
                }

                empleadosArrayBuilder.add(empleadoBuilder);
            }

            // Estructura con array "empleados" para múltiples empleados
            JsonObject estructuraCompleta = Json.createObjectBuilder()
                    .add("empleados", empleadosArrayBuilder)
                    .build();

            writer.write(estructuraCompleta);
            System.out.println("Guardados " + empleados.size() + " empleados en el archivo.");

        } catch (Exception e) {
            System.out.println("Error al guardar empleados: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void mostrarEmpleados() {
        List<Empleado> empleados = obtenerEmpleados();
        if (empleados.isEmpty()) {
            System.out.println("No hay empleados registrados.");
            return;
        }

        System.out.println("=== LISTA DE EMPLEADOS ===");
        for (int i = 0; i < empleados.size(); i++) {
            Empleado emp = empleados.get(i);
            System.out.println("Empleado #" + (i + 1));
            System.out.println("Nombre: " + emp.getNombre() + " " + emp.getApellido());
            System.out.println("Edad: " + emp.getEdad());

            if (emp.getDireccion() != null) {
                System.out.println("Dirección: " + emp.getDireccion().getCalle() + ", " +
                        emp.getDireccion().getCiudad() + ", " + emp.getDireccion().getEstado() +
                        " CP: " + emp.getDireccion().getCp());
            }

            if (emp.getTelefonos() != null && !emp.getTelefonos().isEmpty()) {
                System.out.println("Teléfonos:");
                for (Telefono tel : emp.getTelefonos()) {
                    System.out.println("  " + tel.getTipo() + ": " + tel.getNumero());
                }
            }
            System.out.println("-----------------------------");
        }
    }
}