package creador;

import org.apache.log4j.PropertyConfigurator;

import basedatossql.CreadorBD;
import webscraping.GeneradorClaves;

/**
 * @name CreadorBDyClaves
 * @author Adrian Anton Garcia
 * @category class
 * @Description Clase que lanza los metodos necesarios para 
 * crear la base de datos y extraer las claves dicotómicas
 */

public class CreadorBDyClaves {

	public static void main(String[] args) {
		//Generación de las claves dicotómicas
		PropertyConfigurator.configure("log4j.properties");
		GeneradorClaves generador = new GeneradorClaves();
		generador.generarClavesFicheroEs();
		generador.generarClavesFicheroEn();
		
		//Creación de la Base de datos SQlite
		
		CreadorBD creador = new CreadorBD();
		creador.crearBaseDatos();

	}

}
