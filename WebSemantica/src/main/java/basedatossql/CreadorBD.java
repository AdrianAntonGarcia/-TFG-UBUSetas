package basedatossql;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFormatter;
import org.apache.log4j.BasicConfigurator;

import dbpedia.DBpedia;

/**
 * @name CreadorBD
 * @author Adrian Anton Garcia
 * @category class
 * @Description Clase que crea la base de datos a partir de BDsql y DBpedia
 */

public class CreadorBD {
	//clase de la web semantica
	DBpedia dp = null;
	//clase de la base de datos
	BDsql bd = null;

	/**
	 * @name CreadorBD
	 * @author Adrian Anton Garcia
	 * @category constructor
	 * @Description Constructor que inicializa la clase CreadorBD
	 */

	public CreadorBD() {
		dp = new DBpedia();
		bd = new BDsql();
	}

	/**
	 * @name getBaseDatos
	 * @author Adrian Anton Garcia
	 * @category metodo
	 * @Description metodo que devuelve la instancia de la base de datos usada
	 * @return BDsql, instancia de la base de datos
	 */
	
	public BDsql getBaseDatos() {
		return bd;
	}

	/**
	 * @name getWebSemantica
	 * @author Adrian Anton Garcia
	 * @category metodo
	 * @Description metodo que devuelve la instancia de la web semantica usada
	 * @return BDsql, instancia de la web semantica
	 */
	
	public DBpedia getWebSemantica() {
		return dp;
	}

	public static void main(String[] args) {
		BasicConfigurator.configure();

		CreadorBD lanzador = new CreadorBD();
		BDsql baseDatos = lanzador.getBaseDatos();
		String nombreTabla = "TablaSetas";
		List<String> resultados;
		resultados = lanzador.leerFichero(
				"C:\\Users\\adrit\\Dropbox\\Universidad5\\Proyecto Ubu\\Java\\workspaceSetas\\WebSemantica\\src\\main\\java\\nombresSetas.txt");
		// String connectionUrl =
		// "jdbc:sqlserver://localhost:1433;databaseName=DBsetas;user=sa;password=adrian1";
		String connectionUrl = "jdbc:sqlite:C:\\sqlite\\DBsetas\\DBsetas.db";
		baseDatos.conectarseBaseDatos(connectionUrl);
		baseDatos.borrarTablaSetas(nombreTabla);
		baseDatos.crearTablaSetas(nombreTabla);
		for (String nombreSeta : resultados) {
			lanzador.insertarDescripciones(nombreSeta, nombreTabla);
		}
		baseDatos.close();

	}

	/**
	 * @name insertarDescripciones
	 * @author Adrian Anton Garcia
	 * @category procedimiento
	 * @Description Procedimiento que hace uso de la web semántica para
	 *              consultar las descripciones de la seta que se pasa por
	 *              parametro e inserta una fila de esa seta con la descripción.
	 * @param String,
	 *            nombre de la seta a insertar las descripciones
	 */

	public void insertarDescripciones(String nombreSeta, String nombreTabla) {

		// Descripcion en español
		String queryEsp = dp.getDataQueryDescriptionEsp(nombreSeta);
		ResultSet ResultSetresultados = dp.lanzarConsulta(queryEsp);
		String descripcionEsp = ResultSetFormatter.asText(ResultSetresultados);
		descripcionEsp = descripcionEsp.replaceAll("-", "").replaceAll("=", "").replaceAll("\\|", "").substring(14)
				.trim();
		// Descripcion en ingles
		String queryEng = dp.getDataQueryDescriptionEn(nombreSeta);
		ResultSetresultados = dp.lanzarConsulta(queryEng);
		String descripcionEng = ResultSetFormatter.asText(ResultSetresultados);
		descripcionEng = descripcionEng.replaceAll("-", "").replaceAll("=", "").replaceAll("\\|", "").substring(14)
				.trim();
		// inserto la fila
		bd.insertarFila(nombreTabla, nombreSeta, descripcionEsp, descripcionEng);
	}

	/**
	 * @name leeFichero
	 * @author Adrian Anton Garcia
	 * @category metodo
	 * @Description Metodo que lee las setas del fichero y devuelve una lista
	 *              con todos los nombres de las setas contenidas en ese fichero
	 * @param String,
	 *            nombre del fichero a leer
	 * @return List<String>, lista de nombres de las setas leidas del fichero
	 */

	public List<String> leerFichero(String nombre) {
		List<String> nombreSetas = null;

		try {
			nombreSetas = new ArrayList<String>();
			String cadena;
			FileReader f = new FileReader(nombre);
			BufferedReader b = new BufferedReader(f);
			while ((cadena = b.readLine()) != null) {
				nombreSetas.add(cadena);
			}
			b.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return nombreSetas;
	}
}
