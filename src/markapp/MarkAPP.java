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

        //crearUsuarios(client,xmlManager);
//        queryComprobarSiExiste(client);
        getAll(client, xmlManager);
        //findOneBy(xmlManager);
        // modify(xmlManager);
        // Delete the document
        
        
        //xmlManager.delete(docId);

    }

    public static void crearUsuarios(DatabaseClient client, XMLDocumentManager xmlManager) {

        // Define los datos de usuario en formato XML
        String xmlContent = "<user>"
                + "<name>marta</name>"
                + "<email>marta@gmail.com</email>"
                + "</user>";

        String docId = "/users/user5.xml";

        // Define the metadata for the document
        DocumentMetadataHandle metadata = new DocumentMetadataHandle();
        metadata.getCollections().add("users");

        // Crea un handle para los datos XML
        StringHandle handle = new StringHandle(xmlContent);

        // Guarda los datos XML en la base de datos "prueba"
        xmlManager.write(docId, metadata, handle);
    }

    public static void getAll(DatabaseClient client, XMLDocumentManager xmlManager) {

        QueryManager queryManager = client.newQueryManager();
        StructuredQueryBuilder qb = queryManager.newStructuredQueryBuilder();
        StructuredQueryDefinition query = qb.collection("users");

        // Execute the search
        SearchHandle results = queryManager.search(query, new SearchHandle());

        // Iterate through the search results
        MatchDocumentSummary[] summaries = results.getMatchResults();
        for (MatchDocumentSummary summary : summaries) {
            // Read the contents of each document
            StringHandle handle = new StringHandle();
            xmlManager.read(summary.getUri(), handle);

            // Display the contents of the document
            System.out.println(handle.get());
        }
        // Release the client
        client.release();

    }

    public static void queryComprobarSiExiste(DatabaseClient client) {

        //Este metodo devuelve si hay un usuario segun la query, en este caso devuelve que existe 1
        // Crear un gestor de consultas
        QueryManager queryMgr = client.newQueryManager();

        // Crear una definici??n de consulta estructurada
        StructuredQueryBuilder qb = queryMgr.newStructuredQueryBuilder();
        StructuredQueryDefinition query = qb.and(qb.term("username", "Jairo"));

        // Ejecutar la consulta y obtener el resultado
        SearchHandle results = queryMgr.search(query, new SearchHandle());

        // Mostrar los resultados
        System.out.println("Results: " + results.getTotalResults());

        // Cerrar la conexi??n
        client.release();
    }

    public static void findOneBy(XMLDocumentManager xmlManager) {

        String docId = "/users/user2.xml";

        // Crea un handle para los datos XML
        StringHandle handle = new StringHandle();

        // Lee los datos XML de la base de datos "prueba"
        xmlManager.read(docId, handle);

        // Obtiene el contenido del documento
        String xmlContent = handle.get();

        // Muestra el contenido del documento en la consola
        System.out.println(xmlContent);

    }

    public static void modify(XMLDocumentManager xmlManager) {
        String docId = "/users/user5.xml";

        // Crea un handle para los datos XML
        StringHandle handle = new StringHandle();

        // Lee los datos XML de la base de datos "prueba"
        xmlManager.read(docId, handle);

        // Obtiene el contenido del documento
        String xmlContent = handle.get();

        // Agrega el nuevo usuario al contenido XML
        xmlContent = xmlContent.replace("<name>Pedro</name>", "<name>chester</name>");
        xmlContent = xmlContent.replace("<email>chester@gmail.com</email>", "<email>perrete@gmail.com</email>");

        // Crea un handle para los nuevos datos XML
        handle = new StringHandle(xmlContent);

        // Guarda los nuevos datos XML en la base de datos "prueba"
        xmlManager.write(docId, handle);
    }
}
