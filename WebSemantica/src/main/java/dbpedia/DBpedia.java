package dbpedia;



import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFormatter;

public class DBpedia {
	
	QueryExecution lanzadorQuery=null;
	public DBpedia(){
		
	}
	
	public static void main(String[] args){
		
		
		DBpedia dp= new DBpedia();
		
		String query = dp.getDataQuerySpecie("Amanitaregalis");
		
		ResultSet resultados=dp.lanzarConsulta(query);

		
		ResultSetFormatter.out(System.out,resultados);
		System.out.println(query);
		dp.close();
		
	}
	
	public ResultSet lanzarConsulta(String query){
		lanzadorQuery=QueryExecutionFactory.sparqlService("http://dbpedia.org/sparql", query);
		ResultSet resultados= lanzadorQuery.execSelect();
		
		return resultados;
	}
	
	public void close(){
		lanzadorQuery.close();
	}
	
	public String getDataQuerySpecie(String nombreEspecie) {
		return "PREFIX : <http://dbpedia.org/resource/>"
				+ "PREFIX dbpedia2: <http://dbpedia.org/property/>"
				+ "PREFIX dbo:<http://dbpedia.org/ontology/>"
				+ "SELECT ?genero ?especie ?descripcion "
				+ "WHERE { "
				+ ":"
				+ nombreEspecie
				+ " dbo:abstract ?descripcion."
				+ "FILTER ( lang(?descripcion)=\"es\" || lang(?descripcion)=\"en\")."
				+ ":" + nombreEspecie + " dbpedia2:genus ?genero ."
				+ "FILTER langMatches (lang(?genero),'en')" + ":"
				+ nombreEspecie + " dbpedia2:species ?especie." + 
				"}";
	}

}
