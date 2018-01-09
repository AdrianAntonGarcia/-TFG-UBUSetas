package dbpedia;

import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.ResultSet;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import basedatossql.CreadorBD;
import webscraping.CreadorClaves;

/**
 * Clase que contiene los métodos necesarios realizar consultas a la web
 * semantica dbpedia
 * 
 * @name DBpedia
 * @author Adrian Anton Garcia
 * @category class
 */

public class DBpedia {

	// logger del BDsql
	@SuppressWarnings("unused")
	private final static Logger logger = Logger.getLogger(DBpedia.class);

	QueryExecution lanzadorQuery = null;

	public static void main(String[] args) {
		PropertyConfigurator.configure("log4j.properties");
		// Creación de la Base de datos SQlite
		CreadorBD creador = new CreadorBD();
		creador.crearBaseDatos();

	}
	
	/**
	 * Constructor que inicializa la clase DBpedia
	 * 
	 * @name DBpedia
	 * @author Adrian Anton Garcia
	 * @category constructor
	 */

	public DBpedia() {

	}

	/**
	 * Método que lanza la consulta pasada por parametro a la dbpedia
	 * 
	 * @name lanzarConsulta
	 * @author Adrian Anton Garcia
	 * @category Método
	 * @param String,
	 *            consulta a lanzar
	 * @return ResultSet, resultset que contiene el resultado de la consulta
	 */

	public ResultSet lanzarConsulta(String query) {
		lanzadorQuery = QueryExecutionFactory.sparqlService("http://dbpedia.org/sparql", query);
		ResultSet resultados = lanzadorQuery.execSelect();
		return resultados;

	}

	/**
	 * Método que obtiene la consulta para conseguir el genero de una seta
	 * llamando a la dbpedia con su nombre completo
	 * 
	 * @name getDataQueryGenero
	 * @author Adrian Anton Garcia
	 * @category Método
	 * @param String,
	 *            seta a consultar
	 * @return String, Consulta para obtener el genero de la web Semántica
	 */

	public String getDataQueryGeneroSeta(String nombreEspecie) {
		return "PREFIX dbpediaResource: <http://dbpedia.org/resource/>" + "PREFIX dbo:<http://dbpedia.org/ontology/>"
				+ "PREFIX dbp:<http://dbpedia.org/property/>" + "SELECT ?genero ?gen " + "WHERE { " + "dbpediaResource:"
				+ nombreEspecie + " dbo:genus ?genero." + "?genero dbp:genus ?gen" + "}";
	}

	/**
	 * Método que obtiene la consulta para conseguir el genero de una seta
	 * llamando a la debepedia solo con la primeta parte del nombre
	 * 
	 * @name getDataQueryGenero
	 * @author Adrian Anton Garcia
	 * @category Método
	 * @param String,
	 *            seta a consultar
	 * @return String, Genero conseguido de la web Semántica
	 */

	public String getDataQueryGenero(String nombreGenero) {
		return "PREFIX dbpediaResource:<http://dbpedia.org/resource/>" + "PREFIX dbo:<http://dbpedia.org/ontology/>"
				+ "PREFIX dbp:<http://dbpedia.org/property/>" + "SELECT ?genero " + "WHERE { " + "dbpediaResource:"
				+ nombreGenero + " dbp:genus ?genero." + "}";
	}

	/**
	 * Método que obtiene la consulta para conseguir el genero de una seta
	 * 
	 * @name getDataQueryEnlace
	 * @author Adrian Anton Garcia
	 * @category Método
	 * @param String,
	 *            seta a consultar
	 * @return String, Consulta para obtener el genero de la seta
	 */

	public String getDataQueryEnlace(String nombreEspecie) {
		return "PREFIX dbpediaResource:<http://dbpedia.org/resource/>" + "PREFIX dbo:<http://dbpedia.org/ontology/>"
				+ "PREFIX dbp:<http://dbpedia.org/property/>" + "SELECT ?enlace " + "WHERE { " + "dbpediaResource:"
				+ nombreEspecie + " dbo:wikiPageID ?enlace." + "}";
	}

	/**
	 * Método obtiene la consulta para conseguir la comestibilidad de una seta
	 * 
	 * @name getDataQueryComestible
	 * @author Adrian Anton Garcia
	 * @category Método
	 * @param String,
	 *            seta a consultar
	 * @return String, consulta de la comestibilidad de la seta
	 */

	public String getDataQueryComestible(String nombreEspecie) {
		return "PREFIX dbpediaResource:<http://dbpedia.org/resource/>" + "PREFIX dbp:<http://dbpedia.org/property/>"
				+ "SELECT  ?especie " + "WHERE { " + "dbpediaResource:" + nombreEspecie + " dbp:howedible ?especie."
				+ "}";
	}

	/**
	 * Método que consigue la descripcion en español de la seta introducida como
	 * parametro
	 * 
	 * @name getDataQueryDescriptionEsp
	 * @author Adrian Anton Garcia
	 * @category Método
	 * @param String,
	 *            seta a consultar
	 * @return String, Consulta de la web Semántica
	 */

	public String getDataQueryDescriptionEsp(String nombreEspecie) {
		return "PREFIX dbpediaResource: <http://dbpedia.org/resource/>" + "PREFIX dbo:<http://dbpedia.org/ontology/>"
				+ "SELECT ?descripcion " + "WHERE { " + "dbpediaResource:" + nombreEspecie
				+ " dbo:abstract ?descripcion." + "FILTER ( lang(?descripcion)=\"es\" )." // ||
				// lang(?descripcion)=\"en\"
				+ "}";
	}

	/**
	 * Método que obtiene la consulta para conseguir la descripcion en ingles de
	 * la seta introducida como parametro
	 * 
	 * @name getDataQueryDescriptionEn
	 * @author Adrian Anton Garcia
	 * @category Método
	 * @param String,
	 *            seta a consultar
	 * @return String, Consulta de la web Semántica
	 */

	public String getDataQueryDescriptionEn(String nombreEspecie) {
		return "PREFIX dbpediaResource: <http://dbpedia.org/resource/>" + "PREFIX dbo:<http://dbpedia.org/ontology/>"
				+ "SELECT ?descripcion " + "WHERE { " + "dbpediaResource:" + nombreEspecie
				+ " dbo:abstract ?descripcion." + "FILTER ( lang(?descripcion)=\"en\" )." // ||
																							// lang(?descripcion)=\"es\"
				+ "}";
	}

	/**
	 * Procedimiento que cierra la conexion con la web semantica
	 * 
	 * @name close
	 * @author Adrian Anton Garcia
	 * @category procedimiento
	 */

	public void close() {
		lanzadorQuery.close();
	}
}