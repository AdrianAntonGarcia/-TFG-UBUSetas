package webscraping;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.TreeMap;

import org.apache.log4j.Logger;

/**
 * @name GeneradorClaves
 * @author Adrian Anton Garcia
 * @category class
 * @Description Clase que contiene los metodos necesarios para serializar las claves dicotomicas
 */

public class GeneradorClaves implements Serializable {

	private static final long serialVersionUID = 1L;

	private final static Logger logger = Logger.getLogger(GeneradorClaves.class);

	private String urlFichero = "C:\\Users\\adrit\\Dropbox\\Universidad5\\Proyecto Ubu\\Java\\workspaceSetas\\WebSemantica\\src\\main\\java\\generosEnlaces.txt";

	private String nombreFichero = "claves.dat";

	private String nodoInicial = "1";

	public TreeMap<String, ArrayList<Object>> claves;

	/**
	 * @name GeneradorClaves
	 * @author Adrian Anton Garcia
	 * @category constructor
	 * @Description Constructor de generadorClaves, inicializa el mapa de claves a serialozar
	 */
	
	public GeneradorClaves() {
		claves = new TreeMap<String, ArrayList<Object>>();
	}

	public static void main(String[] args) {
		GeneradorClaves generador = new GeneradorClaves();
		generador.generarClavesFichero();
		System.out.println(generador.claves.size());
	}

	/**
	 * @name generarClavesFichero
	 * @author Adrian Anton Garcia
	 * @category procedimiento
	 * @Description Procedimiento que serializa las claves dicotómicas en el
	 *              fichero claves.dat
	 */

	public void generarClavesFichero() {
		TreeMap<String, String> generosEnlaces = this.leerFichero();
		for (String genero : generosEnlaces.keySet()) {
			String enlace = generosEnlaces.get(genero).trim();
			// Por cada clave de la que dispongamos enlace
			if (enlace.trim().equals("vacio") == false) {
				// Creo la clave
				ClaveDicotomica clave = new ClaveDicotomica(enlace, nodoInicial);
				clave.cargarClaveDicotomica(nodoInicial);
				ArrayList<Object> mapas = clave.devolverMapas();
				claves.put(genero, mapas);
				System.out.println(generosEnlaces.get(genero));
			} else {
				ArrayList<Object> mapas = new ArrayList<Object>();
				mapas.add(enlace);
				claves.put(genero, mapas);
			}
		}
		try {
			// serializo con todas las claves cargadas
			ObjectOutputStream ficheroSalida = new ObjectOutputStream(new FileOutputStream(nombreFichero));
			ficheroSalida.writeObject(claves);
			ficheroSalida.flush();
			ficheroSalida.close();
			System.out.println("Claves guardadas correctamente...");

		} catch (FileNotFoundException fnfe) {
			System.out.println("Error: El fichero no existe. ");
		} catch (IOException ioe) {
			System.out.println("Error: Fallo en la escritura en el fichero. ");
		}

	}

	/**
	 * @name leerFichero
	 * @author Adrian Anton Garcia
	 * @category metodo
	 * @Description Metodo que lee el fichero de los generos con las claves y
	 *              carga un treemap
	 */

	public TreeMap<String, String> leerFichero() {
		ArrayList<String> generosEnlaces = null;
		try {
			generosEnlaces = new ArrayList<String>();
			String cadena;
			FileReader f = new FileReader(urlFichero);
			BufferedReader b = new BufferedReader(f);
			while ((cadena = b.readLine()) != null) {
				generosEnlaces.add(cadena.trim());
			}
			b.close();
		} catch (FileNotFoundException e) {
			logger.error(e.getMessage());
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
		TreeMap<String, String> mapa = new TreeMap<String, String>();
		for (String e : generosEnlaces) {
			mapa.put(e.split(" ")[0], e.split(" ")[1]);
		}
		return mapa;
	}
}
