package creador;

import org.apache.log4j.PropertyConfigurator;

import basedatossql.CreadorBD;
import webscraping.CreadorClaves;

/**
 * Clase que lanza los metodos necesarios para crear la base de datos y extraer
 * las claves dicotómicas
 * 
 * @name CreadorBDyClaves
 * @author Adrian Anton Garcia
 * @category class
 */

public class CreadorBDyClaves {

	public static void main(String[] args) {
		// Generación de las claves dicotómicas
		PropertyConfigurator.configure("log4j.properties");
		CreadorClaves generador = new CreadorClaves();
		generador.generarClavesFicheroEs();
		generador.generarClavesFicheroEn();

		// Creación de la Base de datos SQlite

		CreadorBD creador = new CreadorBD();
		creador.crearBaseDatos();

	}

}
