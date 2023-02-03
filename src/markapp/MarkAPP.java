package markapp;

import com.marklogic.client.DatabaseClient;
import com.marklogic.client.DatabaseClientFactory;
import com.marklogic.client.document.XMLDocumentManager;
import com.marklogic.client.io.DocumentMetadataHandle;
import com.marklogic.client.io.SearchHandle;
import com.marklogic.client.io.StringHandle;
import com.marklogic.client.query.MatchDocumentSummary;
import com.marklogic.client.query.QueryManager;
import com.marklogic.client.query.QueryManager.QueryView;
import com.marklogic.client.query.StructuredQueryBuilder;
import com.marklogic.client.query.StructuredQueryDefinition;

public class MarkAPP {

    private static final String HOST = "localhost";
    private static final int PORT = 8000;
    private static final String USERNAME = "admin";
    private static final String PASSWORD = "1234";

    public static void main(String[] args) {

        DatabaseClient client = DatabaseClientFactory.newClient(HOST, PORT, USERNAME, PASSWORD, DatabaseClientFactory.Authentication.DIGEST);
         // Crea un gestor de documentos XML
        XMLDocumentManager xmlManager = client.newXMLDocumentManager();
                
//        crearUsuarios(client,xmlManager);
        
//        queryComprobarSiExiste(client);
        
        
        getAll(client);
       
    }
    
    
    public static void crearUsuarios(DatabaseClient client, XMLDocumentManager xmlManager){

        // Define los datos de usuario en formato XML
        String xmlContent = "<user>"
                + "<name>Jairo</name>"
                + "<email>Jairo@gmail.com</email>"
                + "</user>";

        String docId = "/users/user4.xml";

        // Define the metadata for the document
        DocumentMetadataHandle metadata = new DocumentMetadataHandle();
        metadata.getCollections().add("users");

        // Crea un handle para los datos XML
        StringHandle handle = new StringHandle(xmlContent);

        // Guarda los datos XML en la base de datos "prueba"
        xmlManager.write(docId, metadata, handle);
    }
    
    public static void getAll(DatabaseClient client){
        
         // Create a query manager
      QueryManager queryMgr = client.newQueryManager();

      // Define a query to retrieve all documents in the "users" collection
      StructuredQueryBuilder qb = queryMgr.newStructuredQueryBuilder();
      StructuredQueryDefinition query = qb.collection("users");

      // Search for all matching documents
      StringHandle results = new StringHandle();
      queryMgr.search(query, results);

      // Display the results
      System.out.println(results.get());

      // Release the client
      client.release();
    
    }
    
    
    public static void queryComprobarSiExiste(DatabaseClient client) {

        //Este metodo devuelve si hay un usuario segun la query, en este caso devuelve que existe 1
        // Crear un gestor de consultas
        QueryManager queryMgr = client.newQueryManager();

        // Crear una definición de consulta estructurada
        StructuredQueryBuilder qb = queryMgr.newStructuredQueryBuilder();
        StructuredQueryDefinition query = qb.and(qb.term("username", "Jairo"));

        // Ejecutar la consulta y obtener el resultado
        SearchHandle results = queryMgr.search(query, new SearchHandle());

        // Mostrar los resultados
        System.out.println("Results: " + results.getTotalResults());

        // Cerrar la conexión
        client.release();

    }
    
    
    
}
