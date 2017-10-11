package basedatossql;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFormatter;
import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dbpedia.DBpedia;

/**
 * @name BDsql
 * @author Adrian Anton Garcia
 * @category class
 * @Description Clase que contiene los métodos necesarios para acceder a una
 *              base de datos SQL y manejarla
 */

public class BDsql {

	// Conexion con la base de datos
	private Connection conexion;

	// logger para manejar los mensajes
	private static Logger logger = LoggerFactory.getLogger(BDsql.class);

	// id de las filas que se insertan en la tablaSetas
	private int tamTablaSetas = 1;

	/**
	 * @name BDsql
	 * @author Adrian Anton Garcia
	 * @category constructor
	 * @Description Constructor que inicializa la clase BDsql
	 */

	public BDsql() {

	}


	/**
	 * @name borrarTablaSetas
	 * @author Adrian Anton Garcia
	 * @category procedimiento
	 * @Description Procedimiento que borra la tabla pasada por parametro de la
	 *              base de datos
	 * @param String,
	 *            nombre de la tabla a borrar
	 */

	public void borrarTablaSetas(String nombre) {
		Statement borrarTabla = null;
		try {
			borrarTabla = conexion.createStatement();
			String borrar = "DROP TABLE IF EXISTS " + nombre + ";";
			borrarTabla.execute(borrar);
			logger.info("Tabla borrada con exito");
		} catch (SQLException e) {
			logger.info("Error SQL al borrar la tabla: ", e.getMessage());
			System.out.println(e.getMessage());
		} catch (Exception ex) {
			logger.info("Error al borrar la tabla: ", ex.getMessage());
		} finally {
			try {
				borrarTabla.close();
			} catch (Exception ex) {
				logger.info("Error al cerrar la tabla: ", ex.getMessage());
			}
		}
	}

	/**
	 * @name crearTablaSetas
	 * @author Adrian Anton Garcia
	 * @category procedimiento
	 * @Description Procedimiento que crea la tabla pasada por parametro de la
	 *              base de datos
	 * @param String,
	 *            nombre de la tabla a crear
	 */

	public void crearTablaSetas(String nombre) {
		Statement crearTabla = null;
		try {
			crearTabla = conexion.createStatement();
			String crearTablaSql = "CREATE TABLE " + nombre + "(" + "IdSeta INT NOT NULL PRIMARY KEY, "
					+ "Nombre TEXT NOT NULL, " + "DescripcionEsp TEXT, " + "DescripcionEn TEXT " + ")";
			crearTabla.execute(crearTablaSql);
			logger.info("Tabla creada con exito");
		} catch (SQLException e) {
			logger.info("Error SQL al crear la tabla: ", e.getMessage());
			System.out.println(e.getMessage());
		} catch (Exception ex) {
			logger.info("Error al crear la tabla: ", ex.getMessage());
		} finally {
			try {
				crearTabla.close();
			} catch (Exception ex) {
				logger.info("Error al cerrar la tabla: ", ex.getMessage());
			}
		}
	}

	/**
	 * @name insertarFila
	 * @author Adrian Anton Garcia
	 * @category procedimiento
	 * @Description Procedimiento que inserta una fila en la tabla
	 * @param String,
	 *            nombre de la tabla a insertar
	 * @param String,
	 *            nombre de la seta a insertar
	 * @param String,
	 *            descripcion en español
	 * @param String,
	 *            descripcion en ingles
	 */

	public void insertarFila(String nombreTabla, String nombre, String descripcionEsp, String descripcionEn) {

		PreparedStatement preparedStatementInsertarFila = null;
		try {
			Statement insertarFila = conexion.createStatement();
			String insertarFilaSql = "INSERT INTO " + nombreTabla
					+ " (IdSeta, Nombre, DescripcionEsp, DescripcionEn) VALUES" + "(?,?,?,?)";
			preparedStatementInsertarFila = conexion.prepareStatement(insertarFilaSql);
			preparedStatementInsertarFila.setInt(1, tamTablaSetas);
			preparedStatementInsertarFila.setString(2, nombre);
			preparedStatementInsertarFila.setString(3, descripcionEsp);
			preparedStatementInsertarFila.setString(4, descripcionEn);
			preparedStatementInsertarFila.execute();
			insertarFila.close();
			tamTablaSetas++;
			logger.info("Fila insertada con exito");
		} catch (SQLException e) {
			logger.info("Error SQL al insertar fila: ", e.getMessage());
			System.out.println("Error SQL al cerrar la fila:");
		} catch (Exception ex) {
			logger.info("Error al insertar fila: ", ex.getMessage());
		} finally {
			try {
				preparedStatementInsertarFila.close();
			} catch (Exception ex) {
				logger.info("Error al cerrar la fila: ", ex.getMessage());
			}
		}
	}

	/**
	 * @name conectarseBaseDatos
	 * @author Adrian Anton Garcia
	 * @category procedimiento
	 * @Description Procedimiento que conecta con la base de datos
	 * @param String,
	 *            url de la base de datos a conectarse
	 */

	public void conectarseBaseDatos(String url) {
		try {
			// Load SQL Server JDBC driver and establish connection.
			System.out.print("Connecting to SQL Server ... ");
			conexion = DriverManager.getConnection(url);
			if (conexion != null) {
				logger.info("Conexion realizada con exito");
			}

		} catch (Exception e) {
			System.out.println();
			logger.info("Error al crear la conexión", e.getMessage());
		}
	}

	/**
	 * @name close
	 * @author Adrian Anton Garcia
	 * @category procedimiento
	 * @Description Procedimiento que cierra la conexion con la base de datos
	 */

	public void close() {
		try {
			conexion.close();
		} catch (SQLException e) {
			logger.info("Error SQL al cerrar la conexion", e.getMessage());
		}
		logger.info("Conexion cerrada con exito");

	}
}
