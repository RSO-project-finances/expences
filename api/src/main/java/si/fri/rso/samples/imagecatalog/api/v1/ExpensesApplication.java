package si.fri.rso.samples.imagecatalog.api.v1;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;


import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Contact;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.info.License;
import org.eclipse.microprofile.openapi.annotations.servers.Server;

@OpenAPIDefinition(info = @Info(title = "Expenses API", version = "v1",
        contact = @Contact(email = "tomazpoljansek12@gmail.com"),
        license = @License(name = "dev"), description = "API for managing expenses."),
        servers = @Server(url = "http://20.228.195.151:8080"))
@ApplicationPath("/v1")
public class ExpensesApplication extends Application {

}
