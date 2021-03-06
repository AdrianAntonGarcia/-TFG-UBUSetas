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
import org.apache.log4j.PropertyConfigurator;

/**
 * Clase que contiene los Métodos necesarios para serializar las claves
 * dicotomicas.
 * 
 * @name GeneradorClaves
 * @author Adrian Anton Garcia
 * @category class
 */

public class CreadorClaves implements Serializable {

	private static final long serialVersionUID = 1L;

	private final static Logger logger = Logger.getLogger(CreadorClaves.class);

	//private String urlFichero = "C:\\Users\\adrit\\Dropbox\\Universidad5\\Proyecto Ubu\\Java\\workspaceSetas\\ExtraccionDatos\\src\\main\\java\\generosEnlaces.txt";

	private String urlFichero = "src\\main\\java\\generosEnlaces.txt";
	
	private String nombreFichero = "claves.dat";

	private String nombreFicheroEn = "clavesEn.dat";

	private String nodoInicial = "1";

	public TreeMap<String, ArrayList<Object>> claves;

	public TreeMap<String, ArrayList<Object>> clavesEn;
	
	public static void main(String[] args) {
		// Generación de las claves dicotómicas
		PropertyConfigurator.configure("log4j.properties");
		CreadorClaves generador = new CreadorClaves();
		generador.generarClavesFicheroEs();
		generador.generarClavesFicheroEn();
	}

	/**
	 * Constructor de generadorClaves, inicializa el mapa de claves a serializar
	 * 
	 * @name GeneradorClaves
	 * @author Adrian Anton Garcia
	 * @category constructor
	 */

	public CreadorClaves() {
		claves = new TreeMap<String, ArrayList<Object>>();
		clavesEn = new TreeMap<String, ArrayList<Object>>();
	}

	/**
	 * Procedimiento que serializa las claves dicotómicas en el fichero
	 * claves.dat
	 * 
	 * @name generarClavesFichero
	 * @author Adrian Anton Garcia
	 * @category procedimiento
	 */

	public void generarClavesFicheroEs() {
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
	 * Procedimiento que serializa las claves dicotómicas en inglés en el
	 * fichero clavesEn.dat
	 * 
	 * @name generarClavesFicheroEn
	 * @author Adrian Anton Garcia
	 * @category procedimiento
	 */

	public void generarClavesFicheroEn() {
		TreeMap<String, String> generosEnlaces = this.leerFichero();
		for (String genero : generosEnlaces.keySet()) {
			String enlace = generosEnlaces.get(genero).trim();
			// Por cada clave de la que dispongamos enlace
			if (enlace.trim().equals("vacio") == false) {
				// Creo la clave
				ClaveDicotomica clave = new ClaveDicotomica(enlace, nodoInicial);
				clave.cargarClaveDicotomicaEn(nodoInicial);
				ArrayList<Object> mapas = clave.devolverMapas();
				clavesEn.put(genero, mapas);
				System.out.println(generosEnlaces.get(genero));
			} else {
				ArrayList<Object> mapas = new ArrayList<Object>();
				mapas.add(enlace);
				clavesEn.put(genero, mapas);
			}
		}
		try {
			// serializo con todas las claves cargadas
			ObjectOutputStream ficheroSalida = new ObjectOutputStream(new FileOutputStream(nombreFicheroEn));
			ficheroSalida.writeObject(clavesEn);
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
	 * Método que lee el fichero de los generos con las claves y carga un
	 * treemap
	 * 
	 * @name leerFichero
	 * @author Adrian Anton Garcia
	 * @category Método
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
