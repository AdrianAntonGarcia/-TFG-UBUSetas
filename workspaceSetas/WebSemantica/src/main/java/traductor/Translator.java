package traductor;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.apache.log4j.Logger;
import org.json.JSONArray;

import dbpedia.DBpedia;

//http://archana-testing.blogspot.com.es/2016/02/calling-google-translation-api-in-java.html
public class Translator {

	@SuppressWarnings("unused")
	private final static Logger logger = Logger.getLogger(Translator.class);
	
	public static void main(String[] args) throws Exception {

		Translator http = new Translator();
		String word = http.callUrlAndParseResult("en", "es",
				"Agrocybe is a genus of mushrooms in the family Strophariaceae. Some species are poisonous. The genus has a widespread distribution, and contains about 100 species");

		System.out.println(word);
	}

	public String callUrlAndParseResult(String langFrom, String langTo, String word) throws Exception {

		String url = "https://translate.googleapis.com/translate_a/single?" + "client=gtx&" + "sl=" + langFrom + "&tl="
				+ langTo + "&dt=t&q=" + URLEncoder.encode(word, "US-ASCII");

		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestProperty("User-Agent", "Mozilla/5.0");

		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		return parseResult(response.toString());
	}
	
	public String parseResult(String inputJson){
		/*
		 * inputJson for word 'hello' translated to language Hindi from English-
		 * [[["नमस्ते","hello",,,1]],,"en"] We have to get 'नमस्ते ' from this
		 * json.
		 */

		JSONArray jsonArray = new JSONArray(inputJson);
		JSONArray jsonArray2 = (JSONArray) jsonArray.get(0);
		JSONArray jsonArray3 = (JSONArray) jsonArray2.get(0);

		return jsonArray3.get(0).toString();
	}
	/**
	 * Traduce el idioma del tipo de las setas.
	 * 
	 * @param tipo tipo de la especie.
	 * @return tipo de la especie traducido.
	 */
	public String translateType(String tipo) {
		tipo = tipo.replaceAll("choice", "Recomendada");
		tipo = tipo.replaceAll("not recommended", "No recomendada");
		tipo = tipo.replaceAll("inedible", "No comestible");
		tipo = tipo.replaceAll("edible", "Comestible");
		tipo = tipo.replaceAll("psychoactive", "Psicotropica");
		tipo = tipo.replaceAll("poisonous", "Venenosa");
		tipo = tipo.replaceAll("deadly", "Mortal");
		tipo = tipo.replaceAll("caution", "Peligrosa");
		tipo = tipo.replaceAll("unknown", "Desconocido");
		tipo = tipo.replaceAll("unpalatable", "Comestible");
		return tipo;
	}
}