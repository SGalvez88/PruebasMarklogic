package markapp;

import com.marklogic.client.DatabaseClient;
import com.marklogic.client.DatabaseClientFactory;
import com.marklogic.client.document.XMLDocumentManager;
import com.marklogic.client.io.DocumentMetadataHandle;
import com.marklogic.client.io.SearchHandle;
import com.marklogic.client.io.StringHandle;
import com.marklogic.client.query.MatchDocumentSummary;
import com.marklogic.client.query.QueryManager;
import com.marklogic.client.query.StructuredQueryBuilder;
import com.marklogic.client.query.StructuredQueryDefinition;

public class MarkAPP {

    private static final String HOST = "localhost";
    private static final int PORT = 8000;
    private static final String USERNAME = "admin";
    private static final String PASSWORD = "1234";

    public static void main(String[] args) {
        // Crea una conexión a la base de datos "prueba" de MarkLogic
        DatabaseClient client = DatabaseClientFactory.newClient(HOST, PORT, USERNAME, PASSWORD, DatabaseClientFactory.Authentication.DIGEST);

        // Crea un gestor de documentos XML
        XMLDocumentManager xmlManager = client.newXMLDocumentManager();

        // Define los datos de usuario en formato XML
        String xmlContent = "<user>"
                + "<name>Sergio</name>"
                + "<email>sergio@gmail.com</email>"
                + "</user>";

        String docId = "/users/user1.xml";

        // Define the metadata for the document
        DocumentMetadataHandle metadata = new DocumentMetadataHandle();
        metadata.getCollections().add("users");

        // Crea un handle para los datos XML
        StringHandle handle = new StringHandle(xmlContent);

        // Guarda los datos XML en la base de datos "prueba"
        xmlManager.write(docId, metadata, handle);
     

        // Cierra la conexión a la base de datos
        client.release();

    }
}
