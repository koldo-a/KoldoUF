package com.ipartek.formacion.uf2213;

import java.sql.*;
import java.time.*;
import java.util.*;

//-------------------------------------
//-------------------------------------

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

	private static Connection con;
	
	private static Scanner sc = new Scanner(System.in);

	public static void main(String[] args) {
		
		try {
			con = DriverManager.getConnection(URL, USER, PASS);
           
			while(true) {
			mostrarMenu();
            System.out.print("Ingrese el número de la opción: ");
            int opcion = sc.nextInt();
            
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
            default:
                System.err.println("Opción no válida");
            	}          
			}
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

	private static void listar() {
		System.out.println("Ahí va tu listado: ");
		listado();
		System.out.println("\u001B[132mListado generado con éxito\u001b[0m "); //rojo
	}

	private static void borrar() {
		System.out.print("\u001B[34mIngrese el ID para borrar: \u001B[0m");
		long idBorrar = sc.nextLong();
		borrar(idBorrar);
		System.out.println("\u001B[132mBorrado registro con éxito\u001b[0m");
	}

	private static void buscar() {
		System.out.println("\u001B[34mIngrese el ID a Buscar: \u001B[0m"); //azul
		long id = sc.nextLong();
		obtenerPorId(id);
	}

	private static void actualizar() {
		System.out.println("\\u001B[34mIngrese el ID para actualizar: \u001B[0m");
		long idUpdate = sc.nextLong();
		obtenerPorId(idUpdate);
		System.out.println("\u001B[34mInsertando...\u001B[0m");// METER OTRO SWITCH 
		System.out.println("DNI: ");
		String dniUpdate = sc.next();
		System.out.println("DNI Diferencial: ");
		int dniDiferencialUpdate = sc.nextInt();
		System.out.println("Nombre: ");
		String nombreUpdate = sc.next();
		System.out.println("Apellidos: ");
		String apellidosUpdate = sc.next();
		System.out.println("Fecha de Nacimiento: (AAAA-MM-DD) ");
		LocalDate fechaNacimientoUpdate = LocalDate.parse(sc.next());
		
		modificar(idUpdate, dniUpdate, dniDiferencialUpdate, nombreUpdate, apellidosUpdate, fechaNacimientoUpdate);
		listado();
		obtenerPorId(idUpdate);
		System.out.println("--------------------------------");
		System.out.println("Actualizado registro con éxito: " + idUpdate + " - " + dniUpdate + " - " + dniDiferencialUpdate + " - " + nombreUpdate + " - " + apellidosUpdate + " - " + fechaNacimientoUpdate);
	}

	private static void insertar() {
		System.out.println("\u001b[4;31mInsertando...\u001B[0m");
		System.out.println("DNI: ");
		String dni = sc.next();
		System.out.println("DNI Diferencial: ");
		int dniDiferencial = sc.nextInt();
		System.out.println("Nombre: ");
		String nombre = sc.next();
		System.out.println("Apellidos: ");
		String apellidos = sc.next();
		System.out.println("Fecha de Nacimiento: (AAAA-MM-DD) ");
		LocalDate fechaNacimiento = LocalDate.parse(sc.next());
		insertar(dni, dniDiferencial, nombre, apellidos, fechaNacimiento);
		listado();
		System.out.println("\u001b[4;31mAgregado registro con éxito\u001b[0m "); //rojo
	}

	private static void mostrarMenu() {
		System.out.println(""" 
				
				-----------------------------
				
				Seleccione una opción:
				
				0. Buscar
				1. Insertar
				2. Actualizar
				3. Borrar
				4. Listado
				
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