package dbpedia;

import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFormatter;

/**
 * @name DBpedia
 * @author Adrian Anton Garcia
 * @category class
 * @Description Clase que contiene los métodos necesarios realizar consultas a
 *              la web semantica dbpedia
 */

public class DBpedia {

	QueryExecution lanzadorQuery = null;

	/**
	 * @name DBpedia
	 * @author Adrian Anton Garcia
	 * @category constructor
	 * @Description Constructor que inicializa la clase DBpedia
	 */

	public DBpedia() {

	}

	/**
	 * @name lanzarConsulta
	 * @author Adrian Anton Garcia
	 * @category metodo
	 * @Description metodo que lanza la consulta pasada por parametro a la
	 *              dbpedia
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
	 * @name getDataQueryDescriptionEsp
	 * @author Adrian Anton Garcia
	 * @category metodo
	 * @Description metodo que consigue la descripcion en español de la seta
	 *              introducida como parametro
	 * @param String,
	 *            seta a consultar
	 * @return String, Descripcion conseguida de la web Semántica
	 */

	public String getDataQueryDescriptionEsp(String nombreEspecie) {
		return "PREFIX dbpediaResource: <http://es.dbpedia.org/resource/>" + "PREFIX dbo:<http://dbpedia.org/ontology/>"
				+ "SELECT ?descripcion " + "WHERE { " + "dbpediaResource:" + nombreEspecie
				+ " dbo:abstract ?descripcion." + "FILTER ( lang(?descripcion)=\"es\" )." // ||
																							// lang(?descripcion)=\"en\"
				+ "}";
	}

	/**
	 * @name getDataQueryDescriptionEn
	 * @author Adrian Anton Garcia
	 * @category metodo
	 * @Description metodo que consigue la descripcion en ingles de la seta
	 *              introducida como parametro
	 * @param String,
	 *            seta a consultar
	 * @return String, Descripcion conseguida de la web Semántica
	 */

	public String getDataQueryDescriptionEn(String nombreEspecie) {
		return "PREFIX dbpediaResource: <http://dbpedia.org/resource/>" + "PREFIX dbo:<http://dbpedia.org/ontology/>"
				+ "SELECT ?descripcion " + "WHERE { " + "dbpediaResource:" + nombreEspecie
				+ " dbo:abstract ?descripcion." + "FILTER ( lang(?descripcion)=\"en\" )." // ||
																							// lang(?descripcion)=\"es\"
				+ "}";
	}

	/**
	 * @name close
	 * @author Adrian Anton Garcia
	 * @category procedimiento
	 * @Description Procedimiento que cierra la conexion con la web semantica
	 */
	
	public void close() {
		lanzadorQuery.close();
	}
}
