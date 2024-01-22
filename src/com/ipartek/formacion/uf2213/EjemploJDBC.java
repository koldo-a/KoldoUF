package com.ipartek.formacion.uf2213;

import static com.ipartek.formacion.bibliotecask.Consolak.*;

//-------------------------------------
//-------------------------------------

import java.sql.*;
import java.time.*;
import java.util.*;

public class EjemploJDBC {
	private static final String URL = "jdbc:mysql://localhost:3306/manana_tienda";
	private static final String USER = "root";
	private static final String PASS = "1234";

	private static final String SQL_CAMPOS = "dni, dni_diferencial, nombre, apellidos, fecha_nacimiento";

	private static final String SQL_SELECT = "SELECT id," + SQL_CAMPOS + " FROM clientes";
	private static final String SQL_SELECT_ID = "SELECT id," + SQL_CAMPOS + " FROM clientes WHERE id=?";

	private static final String SQL_INSERT = "INSERT INTO clientes (" + SQL_CAMPOS + ") VALUES (?,?,?,?,?)";
	private static final String SQL_UPDATE = "UPDATE clientes SET dni=?, dni_diferencial=?, nombre=?, apellidos=?, fecha_nacimiento=? WHERE id=?";
	private static final String SQL_DELETE = "DELETE FROM clientes WHERE id=?";
	private static final int BUSCAR = 0;
	private static final int INSERTAR = 1;
	private static final int ACTUALIZAR = 2;
	private static final int BORRAR = 3;
	private static final int LISTADO = 4;
	private static final int SALIR = 5;

	private static Connection con;
	
	private static Scanner sc = new Scanner(System.in);

	public static void main(String[] args) {
		
		try {
			con = DriverManager.getConnection(URL, USER, PASS);
			
			int opcion;
			
			do {
			mostrarMenu();
            opcion = pedirOpcion();
            ejecutarOpcion(opcion);          
            } while(opcion != SALIR);
			
		} catch (SQLException e) {
			System.err.println("Error al conectar a la base de datos");
			System.err.println(e.getMessage());
//			e.printStackTrace();
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					System.err.println("Error al cerrar la conexión");
					e.printStackTrace();
				}
			}
		}
	}

	private static void ejecutarOpcion(int opcion) {
		plnAzul("Ejecutando opción " + opcion);
		switch (opcion) {
		case BUSCAR:
			buscar();
			break;
		case INSERTAR:
			insertar();
		    break;
		case ACTUALIZAR:
		    actualizar();
		    break;
		case BORRAR:
		    borrar();
		    break;
		case LISTADO:
			listar();
			break;
		case SALIR:
			plnAmarillo("Gracias por utilizar esta aplicación");
			break;
		default:
		    System.err.println("Opción no válida");
			}
	}
	
	private static int pedirOpcion() {
		return leerInt("\u001B[34mIntroduce la opción elegida\u001b[0m");
	}
	
	private static void listar() {
		plnAmarillo("Hola soy la consola");
		plnAzul("Ahí va tu listado: ");
		listado();
		plnVerdeNegr("Listado generado con éxito"); 
	}

	private static void borrar() {
		plnAzul("Ingrese el ID para borrar:");
		long idBorrar = sc.nextLong();
		borrar(idBorrar);
		plnVerdeNegr("Borrado registro con éxito");
	}

	private static void buscar() {

		long id = leerLong("Ingrese el ID a Buscar:");
		obtenerPorId(id);
	}

	private static void actualizar() {
		plnAzul("Ingrese el ID para actualizar:");
		long idUpdate = sc.nextLong();
		obtenerPorId(idUpdate);
		plnAzulNegr("Insertando...");// METER OTRO SWITCH 
		pln("DNI: ");
		String dniUpdate = sc.next();
		pln("DNI Diferencial: ");
		int dniDiferencialUpdate = sc.nextInt();
		pln("Nombre: ");
		String nombreUpdate = sc.next();
		pln("Apellidos: ");
		String apellidosUpdate = sc.next();
		pln("Fecha de Nacimiento");
		LocalDate fechaNacimientoUpdate = LocalDate.parse(sc.next());
		
		modificar(idUpdate, dniUpdate, dniDiferencialUpdate, nombreUpdate, apellidosUpdate, fechaNacimientoUpdate);
		listado();
		obtenerPorId(idUpdate);
		pln("--------------------------------");
		plnVerdeNegr("Actualizado registro con éxito: " + idUpdate + " - " + dniUpdate + " - " + dniDiferencialUpdate + " - " + nombreUpdate + " - " + apellidosUpdate + " - " + fechaNacimientoUpdate);
	}

	private static void insertar() {
		plnAzul("Insertando...");
		String dni = leerString("DNI");
		Integer dniDiferencial = leerInt("DNI diferencial");
		String nombre = leerString("Nombre");
		String apellidos = leerString("Apellidos");
		LocalDate fechaNacimiento = leerFecha("Fecha de nacimiento");

		insertar(dni, dniDiferencial, nombre, apellidos, fechaNacimiento);
		
		listado();
		
		plnRojoNegr("Agregado registro con éxito");
	}

	private static void mostrarMenu() {

		pln(""" 
				-----------------------------
				
				Seleccione una opción:
				
				0. Buscar
				1. Insertar
				2. Actualizar
				3. Borrar
				4. Listado
				5. Salir
				
				-----------------------------    
						"""
		);
	}

	private static void listado() {
		try (Statement st = con.createStatement(); ResultSet rs = st.executeQuery(SQL_SELECT)) {
			while (rs.next()) {
				// System.out.printf("%s;%s;%s;%s;%s;%s\n",
				System.out.printf("%2s %s %3s %-10s %-20s %s\n", rs.getString("id"), rs.getString("dni"),
						rs.getString("dni_diferencial"), rs.getString("nombre"), rs.getString("apellidos"),
						rs.getString("fecha_nacimiento"));
			}
		} catch (SQLException e) {
			System.err.println("Error en el listado");
			System.err.println(e.getMessage());
//			e.printStackTrace();
		}
	}

	private static void obtenerPorId(long id) {
		try (PreparedStatement pst = con.prepareStatement(SQL_SELECT_ID)) {
			pst.setLong(1, id);

			try (ResultSet rs = pst.executeQuery()) {
				if (rs.next()) {
					System.out.printf("""
							Id:                  %s
							DNI:                 %s%s
							Nombre:              %s
							Apellidos:           %s
							Fecha de nacimiento: %s\n
							""",
							// System.out.printf("%2s %s %3s %-3s %-20s %s\n",
							rs.getString("id"), rs.getString("dni"), rs.getString("dni_diferencial"),
							rs.getString("nombre"), rs.getString("apellidos"), rs.getString("fecha_nacimiento"));
				}
			}
		} catch (SQLException e) {
			System.err.println("Error al obtener por el id " + id);
			System.err.println(e.getMessage());
//			e.printStackTrace();
		}
	}

	private static void insertar(String dni, int dniDiferencial, String nombre, String apellidos,
			LocalDate fechaNacimiento) {
		try (PreparedStatement pst = con.prepareStatement(SQL_INSERT)) {
			pst.setString(1, dni);
			pst.setInt(2, dniDiferencial);
			pst.setString(3, nombre);
			pst.setString(4, apellidos);
			pst.setDate(5, java.sql.Date.valueOf(fechaNacimiento));

			int numeroRegistrosModificados = pst.executeUpdate();

			System.out.println(numeroRegistrosModificados);
		} catch (SQLException e) {
			System.err.println("Error en la inserción");
			System.err.println(e.getMessage());
//			e.printStackTrace();
		}
	}

	private static void modificar(long id, String dni, int dniDiferencial, String nombre, String apellidos,
			LocalDate fechaNacimiento) {
		try (PreparedStatement pst = con.prepareStatement(SQL_UPDATE)) {
			pst.setString(1, dni);
			pst.setInt(2, dniDiferencial);
			pst.setString(3, nombre);
			pst.setString(4, apellidos);
			pst.setDate(5, java.sql.Date.valueOf(fechaNacimiento));

			pst.setLong(6, id);

			int numeroRegistrosModificados = pst.executeUpdate();

			System.out.println(numeroRegistrosModificados);
		} catch (SQLException e) {
			System.err.println("Error en la modificación");
			System.err.println(e.getMessage());
//			e.printStackTrace();
		}
	}

	private static void borrar(long id) {
		try (PreparedStatement pst = con.prepareStatement(SQL_DELETE)) {
			pst.setLong(1, id);

			int numeroRegistrosModificados = pst.executeUpdate();

			System.out.println(numeroRegistrosModificados);
			
		} catch (SQLException e) {
			System.err.println("Error en el borrado");
			System.err.println(e.getMessage());
//			e.printStackTrace();
		}
	}
}