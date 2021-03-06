package webscraping;

import java.util.*;

import org.apache.log4j.PropertyConfigurator;

import com.jaunt.*;

import basedatossql.CreadorBD;
import traductor.Translator;

/**
 * Clase que guarda la estructura de una clave dicotómica y tiene los métodos
 * para cargarla de la url proporcionada y acceder a sus elementos. Esta
 * preparada para funcionar con las claves de la página web
 * http://www.avelinosetas.info
 * 
 * @name ClaveDicotomica
 * @author Adrian Anton Garcia
 * @category class
 */

public class ClaveDicotomica {

	// User agent para hacer uso de las funciones de web scraping de jaunt.
	private UserAgent userAgent;
	// Mapa que contiene la estructura de padre hijos de los nodos.
	private Map<String, ArrayList<String>> arbolNodos;
	// Mapa que contiene el cotnenido de los nodos.
	private Map<String, ArrayList<String>> contenidoNodos;
	// Mapa que relaciona el genero con el nodo que lo contiene.
	private Map<String, String> generosNodos;
	// Traductor para traducir las claves
	private Translator traductor;

	public static void main(String[] args) {
		// Generación de las claves dicotómicas
		PropertyConfigurator.configure("log4j.properties");
		CreadorClaves generador = new CreadorClaves();
		generador.generarClavesFicheroEs();
		generador.generarClavesFicheroEn();
	}
	
	/**
	 * Constructor que inicializa todos los mapas de la clave, la url, el
	 * userAgent e inicializa el primer nodo de la estructura.
	 * 
	 * @name ClaveDicotomica
	 * @author Adrian Anton Garcia
	 * @category constructor
	 * @param String,
	 *            la url de la clave a cargar.
	 */

	public ClaveDicotomica(String url, String nodoInicial) {
		userAgent = new UserAgent();
		arbolNodos = new TreeMap<String, ArrayList<String>>();
		contenidoNodos = new TreeMap<String, ArrayList<String>>();
		generosNodos = new TreeMap<String, String>();
		traductor = new Translator();
		// Inicializo el primer nodo
		ArrayList<String> contenidoNodoUno = new ArrayList<String>();
		contenidoNodoUno.add("Nodo inicial");
		contenidoNodoUno.add(url);
		contenidoNodoUno.add(nodoInicial);
		contenidoNodos.put(nodoInicial, contenidoNodoUno);
	}

	/**
	 * Método que devuelve el mapa con la estructura de la clave dicotómica
	 * 
	 * @name getArbolNodos
	 * @author Adrian Anton Garcia
	 * @category Método
	 * @return Map<String, ArrayList<String>>, mapa con la estructura de la
	 *         clave dicotómica
	 * 
	 */

	public Map<String, ArrayList<String>> getArbolNodos() {
		return this.arbolNodos;
	}

	/**
	 * Método que devuelve el mapa con el contenido de los nodos de la clave
	 * 
	 * @name getContenidoNodos
	 * @author Adrian Anton Garcia
	 * @category Método
	 * @return Map<String, ArrayList<String>>, mapa con el contenido de los
	 *         nodos de la clave
	 */

	public Map<String, ArrayList<String>> getContenidoNodos() {
		return this.contenidoNodos;
	}

	/**
	 * Método que devuelve el mapa con los nodos que contienen los generos
	 * 
	 * @name getGenerosNodos
	 * @author Adrian Anton Garcia
	 * @category Método
	 * @return Map<String, String>, mapa con los nodos que contienen los generos
	 */

	public Map<String, String> getGenerosNodos() {
		this.generosNodos.remove("&nbsp;");
		return this.generosNodos;
	}

	/**
	 * Método devuelve el html de una página web.
	 * 
	 * @name getInnetHtml
	 * @author Adrian Anton Garcia
	 * @category Método
	 */

	public String getInnetHtml(String url) {
		try {
			userAgent.visit(url);
		} catch (JauntException e) {
			System.err.println(e);
		}
		return userAgent.doc.innerHTML();
	}

	/**
	 * Procedimiento que explora de forma recursiva el html de la url
	 * proporcionada por el nodo padre que se introduce, añade un elemento a
	 * cada mapa de la clave con los nuevos nodos de los hijos del padre. De
	 * forma recursiva llama de nuevo al procedimiento pasando como nodos padres
	 * los hijos.
	 * 
	 * @name cargarClaveDicotomica
	 * @author Adrian Anton Garcia
	 * @category procedimiento
	 * @param String,
	 *            El nodo padre del que se van a cargar los hijos
	 */

	public void cargarClaveDicotomica(String nodoPadre) {
		System.out.println("NODO PADRE: " + nodoPadre);
		String nodoHijo = "null";
		String pregunta = "null";
		String enlace = "null";
		String textoEnlace = "null";
		ArrayList<String> contenidos;
		ArrayList<String> nodosHijos = new ArrayList<String>();
		try {
			// recojo las filas de la página del nodo padre.
			String urlVisitar = contenidoNodos.get(nodoPadre).get(1);
			if (urlVisitar != "null") {
				userAgent.visit(urlVisitar);
				Elements filas = userAgent.doc.findEach("<tr>");
				System.out.println("Found " + filas.size() + " filas:");
				int i = 1;
				// recorro cada fila de la url.
				for (Element fila : filas) {
					// recojo las columnas de la fila.
					Elements columnas = fila.findEach("<td>");
					// recorro las 3 columnas
					if (columnas.size() >= 3) {
						contenidos = new ArrayList<String>();
						// NODO HIJO
						// Guardo el texto de la 1º columna como nombre del
						// nodo.
						System.out.println("Nodo hijo:");
						nodoHijo = columnas.getElement(0).findFirst("<p>").getText();
						System.out.println(nodoHijo);
						// Esta comprobacion es por si algun nodos esta repetido
						// en
						// la clave dicotomica.
						if (!nodosHijos.contains(nodoHijo)) {
							nodosHijos.add(nodoHijo.trim());
						} else {
							nodosHijos.add(nodoHijo + i);
							i++;
						}
						// PREGUNTA
						// Guardo el texto de la 2º columna como pregunta del
						// nodo.
						System.out.println("Pregunta:");
						try {
							pregunta = columnas.getElement(1).findFirst("<p>").getText();
						} catch (Exception ex) {
							pregunta = "null" + nodoPadre;
						}

						System.out.println(pregunta);
						contenidos.add(pregunta.trim());
						// ENLACE
						// Guardo el href de la 2º columna como el enlace.
						System.out.println("Enlace:");
						try {
							Element link = fila.findFirst("<a>");
							enlace = link.getAtString("href");
						} catch (Exception ex) {
							textoEnlace = "null" + nodoPadre;
						}
						System.out.println(enlace);
						contenidos.add(enlace.trim());
						contenidos.add(nodoPadre);
						// Agrego el nodo mas su pregunta y enlace
						contenidoNodos.put(nodoHijo, contenidos);
						// TEXTOENLACE
						// Con el texto del enlace de la segunda columna
						// compruebo
						// si es un nodo hoja o intermedio.
						System.out.println("TextoEnlace");
						// Esta comprobación es por si el enlace no esta en un
						// <a> y
						// lo esta en un <p>
						try {
							textoEnlace = columnas.getElement(2).findFirst("<a>").getText();
						} catch (Exception ex) {
							textoEnlace = columnas.getElement(2).findFirst("<p>").getText();
						}
						// Si es un nodo hoja guardo el genero, si no continuo
						// explorando la clave
						if (textoEnlace.length() > 4) {
							System.out.println(textoEnlace);
							System.out.println("------------------");
							textoEnlace = textoEnlace.replaceAll("género", "").trim();
							contenidos.add(textoEnlace);
							// Los nodos hojas tienen un campo más con el
							// género.
							contenidoNodos.put(nodoHijo, contenidos);
							generosNodos.put(textoEnlace.toLowerCase().trim(), nodoHijo);
						} else {
							System.out.println(textoEnlace);
							System.out.println("------------------");
							this.cargarClaveDicotomica(nodoHijo);
						}
					}
				}
			}
			arbolNodos.put(nodoPadre, nodosHijos);
		} catch (Exception e) {
			System.err.println(e);
		}
	}

	/**
	 * Procedimiento que explora de forma recursiva el html de la url
	 * proporcionada por el nodo padre que se introduce, añade un elemento a
	 * cada mapa de la clave con los nuevos nodos de los hijos del padre. De
	 * forma recursiva llama de nuevo al procedimiento pasando como nodos padres
	 * los hijos. Este procedimiento carga las claves dicotómicas traducidas al
	 * inglés.
	 * 
	 * @name cargarClaveDicotomicaEn
	 * @author Adrian Anton Garcia
	 * @category procedimiento
	 * @param String,
	 *            El nodo padre del que se van a cargar los hijos
	 */

	public void cargarClaveDicotomicaEn(String nodoPadre) {
		System.out.println("CLAVES EN INGLÉS");
		System.out.println("NODO PADRE: " + nodoPadre);
		String nodoHijo = "null";
		String preguntaEs = "null";
		String preguntaEn = "null";
		String enlace = "null";
		String textoEnlace = "null";
		ArrayList<String> contenidos;
		ArrayList<String> nodosHijos = new ArrayList<String>();

		try {
			// recojo las filas de la página del nodo padre.
			String urlVisitar = contenidoNodos.get(nodoPadre).get(1);
			if (urlVisitar != "null") {
				userAgent.visit(urlVisitar);
				Elements filas = userAgent.doc.findEach("<tr>");
				System.out.println("Found " + filas.size() + " filas:");
				int i = 1;
				// recorro cada fila de la url.
				for (Element fila : filas) {
					// recojo las columnas de la fila.
					Elements columnas = fila.findEach("<td>");
					// recorro las 3 columnas
					if (columnas.size() >= 3) {
						contenidos = new ArrayList<String>();
						// NODO HIJO
						// Guardo el texto de la 1º columna como nombre del
						// nodo.
						System.out.println("Nodo hijo:");
						nodoHijo = columnas.getElement(0).findFirst("<p>").getText();
						System.out.println(nodoHijo);
						// Esta comprobacion es por si algun nodos esta repetido
						// en
						// la clave dicotomica.
						if (!nodosHijos.contains(nodoHijo)) {
							nodosHijos.add(nodoHijo.trim());
						} else {
							nodosHijos.add(nodoHijo + i);
							i++;
						}
						// PREGUNTA
						// Guardo el texto de la 2º columna como pregunta del
						// nodo.
						System.out.println("Pregunta:");
						try {
							preguntaEs = columnas.getElement(1).findFirst("<p>").getText();
							preguntaEn = traductor.callUrlAndParseResult("es", "en", preguntaEs);
						} catch (NotFound ex) {
							preguntaEn = "null" + nodoPadre;
						} catch (Exception ex) {
							System.err.println("Error al traducir");
						}
						System.out.println("Pregunta en inglés:");
						System.out.println(preguntaEn);
						contenidos.add(preguntaEn.trim());
						// ENLACE
						// Guardo el href de la 2º columna como el enlace.
						System.out.println("Enlace:");
						try {
							Element link = fila.findFirst("<a>");
							enlace = link.getAtString("href");
						} catch (Exception ex) {
							textoEnlace = "null" + nodoPadre;
						}
						System.out.println(enlace);
						contenidos.add(enlace.trim());
						contenidos.add(nodoPadre);
						// Agrego el nodo mas su pregunta y enlace
						contenidoNodos.put(nodoHijo, contenidos);
						// TEXTOENLACE
						// Con el texto del enlace de la segunda columna
						// compruebo
						// si es un nodo hoja o intermedio.
						System.out.println("TextoEnlace");
						// Esta comprobación es por si el enlace no esta en un
						// <a> y
						// lo esta en un <p>
						try {
							textoEnlace = columnas.getElement(2).findFirst("<a>").getText();
						} catch (Exception ex) {
							textoEnlace = columnas.getElement(2).findFirst("<p>").getText();
						}
						// Si es un nodo hoja guardo el genero, si no continuo
						// explorando la clave
						if (textoEnlace.length() > 4) {
							System.out.println(textoEnlace);
							System.out.println("------------------");
							textoEnlace = textoEnlace.replaceAll("género", "").trim();
							contenidos.add(textoEnlace);
							// Los nodos hojas tienen un campo más con el
							// género.
							contenidoNodos.put(nodoHijo, contenidos);
							generosNodos.put(textoEnlace.toLowerCase().trim(), nodoHijo);
						} else {
							System.out.println(textoEnlace);
							System.out.println("------------------");
							this.cargarClaveDicotomicaEn(nodoHijo);
						}
					}
				}
			}
			arbolNodos.put(nodoPadre, nodosHijos);
		} catch (Exception e) {
			System.err.println(e);
		}
	}

	/**
	 * Método que devuelve los mapas de la clave en un array de objetos
	 * 
	 * @name devolverMapas
	 * @author Adrian Anton Garcia
	 * @category Método
	 * @return ArrayList<Object>, array list con los mapas de la clave
	 */

	public ArrayList<Object> devolverMapas() {
		ArrayList<Object> mapas = new ArrayList<Object>();
		mapas.add(this.arbolNodos);
		mapas.add(this.contenidoNodos);
		mapas.add(this.generosNodos);
		return mapas;
	}


	/*
	 * public void leerFichero(){ ArrayList<Object> claves = new
	 * ArrayList<Object>(); try { FileInputStream fis = new
	 * FileInputStream(nombreFichero); ObjectInputStream is = new
	 * ObjectInputStream(fis); claves = (ArrayList<Object>) is.readObject();
	 * is.close(); fis.close(); }catch(Exception ex){
	 * System.out.println(ex.getMessage()); } Map<String, ArrayList<String>>
	 * arbolNodos2 = (Map<String, ArrayList<String>>) claves.get(0); Map<String,
	 * ArrayList<String>> contenidoNodos2= (Map<String, ArrayList<String>>)
	 * claves.get(1); Map<String, String> generosNodos2= (Map<String, String>)
	 * claves.get(2); System.out.println(arbolNodos2.toString());
	 * System.out.println(contenidoNodos2.toString());
	 * System.out.println(generosNodos2.toString()); }
	 */
}
