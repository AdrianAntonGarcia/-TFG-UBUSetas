package basedatossql;

import java.sql.*;
import org.apache.log4j.Logger;

/**
 * Clase que contiene los Métodos necesarios para acceder a una base de datos
 * SQL y manejarla
 * 
 * @name BDsql
 * @author Adrian Anton Garcia
 * @category class
 * 
 */

public class BDsql {

	// logger del BDsql

	private final static Logger logger = Logger.getLogger(BDsql.class);
	// Conexion con la base de datos
	private Connection conexion;

	// id de las filas que se insertan en la tablaSetas
	private int tamTablaDescripciones = 1;
	private int tamTablaGeneros = 1;
	private int tamTablaEnlaces = 1;
	private int tamTablaComestible = 1;
	private int tamTablaClaves = 1;

	/**
	 * Constructor que inicializa la clase BDsql
	 * 
	 * @name BDsql
	 * @author Adrian Anton Garcia
	 * @category constructor
	 * 
	 */

	public BDsql() {

	}

	/**
	 * Método que devuele el tamaño de la tabla descripciones
	 * 
	 * @name getTamTablaDescripciones
	 * @author Adrian Anton Garcia
	 * @category Método
	 * @return int, el tamaño de la tabla
	 */

	public int getTamTablaDescripciones() {
		return tamTablaDescripciones;
	}

	/**
	 * Método que devuele el tamaño de la tabla Generos
	 * 
	 * @name getTamTablaGeneros
	 * @author Adrian Anton Garcia
	 * @category Método
	 *
	 * @return int, el tamaño de la tabla
	 */

	public int getTamTablaGeneros() {
		return tamTablaGeneros;
	}

	/**
	 * Método que devuele el tamaño de la tabla Enlaces
	 * 
	 * @name getTamTablaEnlaces
	 * @author Adrian Anton Garcia
	 * @category Método
	 * 
	 * @return int, el tamaño de la tabla
	 */

	public int getTamTablaEnlaces() {
		return tamTablaEnlaces;
	}

	/**
	 * Método que devuele el tamaño de la tabla Claves
	 * 
	 * @name getTamTablaClaves
	 * @author Adrian Anton Garcia
	 * @category Método
	 * @return int, el tamaño de la tabla
	 */

	public int getTamTablaClaves() {
		return tamTablaClaves;
	}

	/**
	 * Procedimiento que borra la tabla pasada por parametro de la base de datos
	 * 
	 * @name borrarTablaSetas
	 * @author Adrian Anton Garcia
	 * @category procedimiento
	 * @param String,
	 *            nombre de la tabla a borrar
	 */

	public void borrarTablaSetas(String nombre) {
		Statement borrarTabla = null;
		try {
			borrarTabla = conexion.createStatement();
			String borrar = "DROP TABLE IF EXISTS " + nombre + ";";
			borrarTabla.execute(borrar);
			logger.info("Tabla " + nombre + " borrada con exito");
		} catch (SQLException e) {
			logger.info("Error SQL al borrar la tabla " + nombre + ":" + e.getMessage());
		} catch (Exception ex) {
			logger.error("Error al borrar la tabla: " + nombre + ":" + ex.getMessage());
		} finally {
			try {
				borrarTabla.close();
			} catch (Exception ex) {
				logger.error("Error al cerrar la tabla: " + nombre + ":" + ex.getMessage());
			}
		}
	}

	/**
	 * Procedimiento que crea la tabla pasada por parametro de la base de datos
	 * 
	 * @name crearTablaDescripciones
	 * @author Adrian Anton Garcia
	 * @category procedimiento
	 * 
	 * @param String,
	 *            nombre de la tabla a crear
	 */

	public void crearTablaDescripciones(String nombre) {
		Statement crearTabla = null;
		try {
			crearTabla = conexion.createStatement();
			String crearTablaSql = "CREATE TABLE " + nombre + "(" + "IdSeta INT NOT NULL PRIMARY KEY, "
					+ "Nombre TEXT NOT NULL, " + "DescripcionEs TEXT, " + "DescripcionEn TEXT, " + "EsGenero INTEGER "
					+ ")";
			crearTabla.execute(crearTablaSql);
			logger.info("Tabla descripciones creada con exito");
		} catch (SQLException e) {
			logger.error("Error SQL al crear la tabla descriciones: " + e.getMessage());

		} catch (Exception ex) {
			logger.error("Error al crear la tabla descripciones: " + ex.getMessage());
		} finally {
			try {
				crearTabla.close();
			} catch (Exception ex) {
				logger.error("Error al cerrar la tabla descripciones: " + ex.getMessage());
			}
		}
	}

	/**
	 * Procedimiento que crea la tabla pasada por parametro de la base de datos
	 * 
	 * @name crearTablaDescripciones
	 * @author Adrian Anton Garcia
	 * @category procedimiento
	 * @param String,
	 *            nombre de la tabla a crear
	 */

	public void crearTablaEspecies(String nombre) {
		Statement crearTabla = null;
		try {
			crearTabla = conexion.createStatement();
			String crearTablaSql = "CREATE TABLE " + nombre + "(" + "IdSeta INT NOT NULL PRIMARY KEY, "
					+ "Nombre TEXT NOT NULL, " + "Especie TEXT " + ")";
			crearTabla.execute(crearTablaSql);
			logger.info("Tabla especies creada con exito");
		} catch (SQLException e) {
			logger.error("Error SQL al crear la tabla: " + e.getMessage());

		} catch (Exception ex) {
			logger.error("Error al crear la tabla: " + ex.getMessage());
		} finally {
			try {
				crearTabla.close();
			} catch (Exception ex) {
				logger.error("Error al cerrar la tabla: " + ex.getMessage());
			}
		}
	}

	/**
	 * Procedimiento que crea la tabla pasada por parametro de la base de datos
	 * 
	 * @name crearTablaComestible
	 * @author Adrian Anton Garcia
	 * @category procedimiento
	 * @param String,
	 *            nombre de la tabla a crear
	 */

	public void crearTablaComestible(String nombre) {
		Statement crearTabla = null;
		try {
			crearTabla = conexion.createStatement();
			String crearTablaSql = "CREATE TABLE " + nombre + "(" + "IdSeta INT NOT NULL PRIMARY KEY, "
					+ "Nombre TEXT NOT NULL, " + "ComestibleEn TEXT, " + "ComestibleEs TEXT " + ")";
			crearTabla.execute(crearTablaSql);
			logger.info("Tabla comestible creada con exito");
		} catch (SQLException e) {
			logger.error("Error SQL al crear la tabla comestible: " + e.getMessage());

		} catch (Exception ex) {
			logger.error("Error al crear la tabla comestible: " + ex.getMessage());
		} finally {
			try {
				crearTabla.close();
			} catch (Exception ex) {
				logger.error("Error al cerrar la tabla comestible: " + ex.getMessage());
			}
		}
	}

	/**
	 * Procedimiento que crea la tabla pasada por parametro de la base de datos
	 * 
	 * @name crearTablaEnlaces
	 * @author Adrian Anton Garcia
	 * @category procedimiento
	 * @param String,
	 *            nombre de la tabla a crear
	 */

	public void crearTablaEnlaces(String nombre) {
		Statement crearTabla = null;
		try {
			crearTabla = conexion.createStatement();
			String crearTablaSql = "CREATE TABLE " + nombre + "(" + "IdSeta INT NOT NULL PRIMARY KEY, "
					+ "Nombre TEXT NOT NULL, " + "Enlace TEXT " + ")";
			crearTabla.execute(crearTablaSql);
			logger.info("Tabla enlaces creada con exito");
		} catch (SQLException e) {
			logger.error("Error SQL al crear la tabla: " + e.getMessage());
		} catch (Exception ex) {
			logger.error("Error al crear la tabla: " + ex.getMessage());
		} finally {
			try {
				crearTabla.close();
			} catch (Exception ex) {
				logger.error("Error al cerrar la tabla: " + ex.getMessage());
			}
		}
	}

	/**
	 * Procedimiento que inserta una fila en la tabla descripciones
	 * 
	 * @name insertarFilaDescripciones
	 * @author Adrian Anton Garcia
	 * @category procedimiento
	 * @param String,
	 *            nombre de la tabla a insertar
	 * @param String,
	 *            nombre de la seta a insertar
	 * @param String,
	 *            descripcion en español
	 * @param String,
	 *            descripcion en ingles
	 */

	public void insertarFilaDescripciones(String nombreTabla, String nombre, String descripcionEs, String descripcionEn,
			int esGenero) {

		PreparedStatement preparedStatementInsertarFila = null;
		try {
			Statement insertarFila = conexion.createStatement();
			String insertarFilaSql = "INSERT INTO " + nombreTabla
					+ " (IdSeta, Nombre, DescripcionEs, DescripcionEn,EsGenero) VALUES" + "(?,?,?,?,?)";
			preparedStatementInsertarFila = conexion.prepareStatement(insertarFilaSql);
			preparedStatementInsertarFila.setInt(1, tamTablaDescripciones);
			preparedStatementInsertarFila.setString(2, nombre);
			preparedStatementInsertarFila.setString(3, descripcionEs);
			preparedStatementInsertarFila.setString(4, descripcionEn);
			preparedStatementInsertarFila.setInt(5, esGenero);
			preparedStatementInsertarFila.execute();
			insertarFila.close();
			tamTablaDescripciones++;
			logger.info("Fila descripciones insertada con exito");
		} catch (SQLException e) {
			logger.error("Error SQL al insertar fila descripciones: " + e.getMessage());
		} catch (Exception ex) {
			logger.error("Error al insertar fila descripciones: " + ex.getMessage());
		} finally {
			try {
				preparedStatementInsertarFila.close();
			} catch (Exception ex) {
				logger.error("Error al cerrar la fila descripciones: " + ex.getMessage());
			}
		}
	}

	/**
	 * Procedimiento que inserta una fila en la tabla comestible
	 * 
	 * @name insertarFilaComestible
	 * @author Adrian Anton Garcia
	 * @category procedimiento
	 * @param String,
	 *            nombre de la tabla a insertar
	 * @param String,
	 *            nombre de la seta a insertar
	 * @param String,
	 *            comestibilidad en español
	 * @param String,
	 *            comestibilidad en ingles
	 */

	public void insertarFilaComestible(String nombreTabla, String nombre, String comestibleEn, String comestibleEs) {
		PreparedStatement preparedStatementInsertarFila = null;
		try {
			Statement insertarFila = conexion.createStatement();
			String insertarFilaSql = "INSERT INTO " + nombreTabla
					+ " (IdSeta, Nombre, ComestibleEn, ComestibleEs) VALUES" + "(?,?,?,?)";
			preparedStatementInsertarFila = conexion.prepareStatement(insertarFilaSql);
			preparedStatementInsertarFila.setInt(1, tamTablaComestible);
			preparedStatementInsertarFila.setString(2, nombre);
			preparedStatementInsertarFila.setString(3, comestibleEn);
			preparedStatementInsertarFila.setString(4, comestibleEs);
			preparedStatementInsertarFila.execute();
			insertarFila.close();
			tamTablaComestible++;
			logger.info("Fila comestible insertada con exito");
		} catch (SQLException e) {
			logger.error("Error SQL al insertar fila comestible: " + e.getMessage());
		} catch (Exception ex) {
			logger.error("Error al insertar fila comestible: " + ex.getMessage());
		} finally {
			try {
				preparedStatementInsertarFila.close();
			} catch (Exception ex) {
				logger.error("Error al cerrar la fila comestible: " + ex.getMessage());
			}
		}
	}

	/**
	 * Procedimiento que inserta una fila en la tabla de especies
	 * 
	 * @name insertarFilaEspecies
	 * @author Adrian Anton Garcia
	 * @category procedimiento
	 * 
	 * @param String,
	 *            nombre de la tabla a insertar
	 * @param String,
	 *            nombre de la seta a insertar
	 * @param String,
	 *            genero de la seta
	 * 
	 */

	public void insertarFilaGeneros(String nombreTabla, String nombre, String especie) {

		PreparedStatement preparedStatementInsertarFila = null;
		try {
			Statement insertarFila = conexion.createStatement();
			String insertarFilaSql = "INSERT INTO " + nombreTabla + " (IdSeta, Nombre, Especie) VALUES" + "(?,?,?)";
			preparedStatementInsertarFila = conexion.prepareStatement(insertarFilaSql);
			preparedStatementInsertarFila.setInt(1, tamTablaGeneros);
			preparedStatementInsertarFila.setString(2, nombre);
			preparedStatementInsertarFila.setString(3, especie);

			preparedStatementInsertarFila.execute();
			insertarFila.close();
			tamTablaGeneros++;
			logger.info("Fila generos insertada con exito");
		} catch (SQLException e) {
			logger.error("Error SQL al insertar fila generos: " + e.getMessage());
		} catch (Exception ex) {
			logger.error("Error al insertar fila generos: " + ex.getMessage());
		} finally {
			try {
				preparedStatementInsertarFila.close();
			} catch (Exception ex) {
				logger.error("Error al cerrar la fila generos: " + ex.getMessage());
			}
		}
	}

	/**
	 * Procedimiento que inserta una fila en la tabla de enlaces
	 * 
	 * @name insertarFilaEnlaces
	 * @author Adrian Anton Garcia
	 * @category procedimiento
	 * @param String,
	 *            nombre de la tabla a insertar
	 * @param String,
	 *            nombre de la seta a insertar
	 * @param String,
	 *            enlace de la seta
	 * 
	 */

	public void insertarFilaEnlace(String nombreTabla, String nombre, String enlace) {

		PreparedStatement preparedStatementInsertarFila = null;
		try {
			Statement insertarFila = conexion.createStatement();
			String insertarFilaSql = "INSERT INTO " + nombreTabla + " (IdSeta, Nombre, Enlace) VALUES" + "(?,?,?)";
			preparedStatementInsertarFila = conexion.prepareStatement(insertarFilaSql);
			preparedStatementInsertarFila.setInt(1, tamTablaEnlaces);
			preparedStatementInsertarFila.setString(2, nombre);
			preparedStatementInsertarFila.setString(3, enlace);

			preparedStatementInsertarFila.execute();
			insertarFila.close();
			tamTablaEnlaces++;
			logger.info("Fila enlaces insertada con exito");
		} catch (SQLException e) {
			logger.error("Error SQL al insertar fila enlaces: " + e.getMessage());
		} catch (Exception ex) {
			logger.error("Error al insertar fila: " + ex.getMessage());
		} finally {
			try {
				preparedStatementInsertarFila.close();
			} catch (Exception ex) {
				logger.error("Error al cerrar la fila enlaces: " + ex.getMessage());
			}
		}
	}

	/**
	 * Procedimiento que conecta con la base de datos
	 * 
	 * @name conectarseBaseDatos
	 * @author Adrian Anton Garcia
	 * @category procedimiento
	 * @param String,
	 *            url de la base de datos a conectarse
	 */

	public void conectarseBaseDatos(String url) {
		try {
			// Load SQL Server JDBC driver and establish connection.
			logger.info("Connecting to SQL Server ... ");
			conexion = DriverManager.getConnection(url);
			if (conexion != null) {
				logger.info("Conexion realizada con exito");
			}

		} catch (Exception e) {
			logger.error("Error al crear la conexión" + e.getMessage());
		}
	}

	/**
	 * Procedimiento que cierra la conexion con la base de datos
	 * 
	 * @name close
	 * @author Adrian Anton Garcia
	 * @category procedimiento
	 * 
	 */

	public void close() {
		try {
			conexion.close();
		} catch (SQLException e) {
			logger.error("Error SQL al cerrar la conexion" + e.getMessage());
		}
		logger.info("Conexion cerrada con exito");

	}
}
