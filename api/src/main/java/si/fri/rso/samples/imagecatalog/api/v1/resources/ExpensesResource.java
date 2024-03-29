package si.fri.rso.samples.imagecatalog.api.v1.resources;

import com.kumuluz.ee.logs.cdi.Log;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.headers.Header;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.json.JSONArray;
import si.fri.rso.samples.imagecatalog.lib.Expenses;
import si.fri.rso.samples.imagecatalog.services.beans.ExpensesBean;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpClient;
import java.io.IOException;
import org.json.JSONObject;



@Log
@ApplicationScoped
@Path("/expenses")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ExpensesResource {

    private Logger log = Logger.getLogger(ExpensesResource.class.getName());

    @Inject
    private ExpensesBean expensesBean;


    @Context
    protected UriInfo uriInfo;

    @Operation(description = "Get all expenses.", summary = "")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "List of expenses",
                    content = @Content(schema = @Schema(implementation = Expenses.class, type = SchemaType.ARRAY)),
                    headers = {@Header(name = "X-Total-Count", description = "Number of objects in list")}
            )})
    @GET
    public Response getExpenses() {

        log.log(Level.INFO, "get expenses");

        List<Expenses> expense = expensesBean.getExpensesFilter(uriInfo);

        return Response.status(Response.Status.OK).entity(expense).build();
    }


    @Operation(description = "Get expense.", summary = "")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "Expense",
                    content = @Content(
                            schema = @Schema(implementation = Expenses.class))
            )})
    @GET
    @Path("/{expenseId}")
    public Response getExpenses(@Parameter(description = "Expense ID.", required = true)
                                     @PathParam("expenseId") Integer expenseId) {

        log.log(Level.INFO, "get expense " + expenseId);
        Expenses expenses = expensesBean.getExpenses(expenseId);

        if (expenses == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.status(Response.Status.OK).entity(expenses).build();
    }

    @Operation(description = "Add expense.", summary = "")
    @APIResponses({
            @APIResponse(responseCode = "201",
                    description = "Expense successfully added."
            ),
            @APIResponse(responseCode = "405", description = "Validation error .")
    })
    @POST
    public Response createExpense(@RequestBody(
            description = "DTO object with expense.",
            required = true, content = @Content(
            schema = @Schema(implementation = Expenses.class))) Expenses expense) {

        log.log(Level.INFO, "add expense");

        if ((expense.getKind() == null || expense.getDescription() == null || expense.getDate_occurrence() == null)) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        else {
            String text = expense.getKind();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://grammar-and-spellcheck.p.rapidapi.com/grammarandspellcheck"))
                    .header("content-type", "application/x-www-form-urlencoded")
                    .header("X-RapidAPI-Key", "9124474929msh33f87c3c1527ffcp1b3bc2jsnc3117d7a449d")
                    .header("X-RapidAPI-Host", "grammar-and-spellcheck.p.rapidapi.com")
                    .method("POST",
                            HttpRequest.BodyPublishers.ofString("query=" + text))
                    .build();

            HttpResponse<String> response = null;
            try {
                response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
                JSONObject json = new JSONObject(response.body());

                //JSONObject json = new JSONObject("{\"identified_mistakes\":[]}");

                JSONArray ja = json.getJSONArray("identified_mistakes");
                //log.info(String.valueOf(ja.length()));

                // OK
                if (ja.isEmpty()) {
                    expense = expensesBean.createExpense(expense);
                    return Response.status(Response.Status.ACCEPTED).entity(expense).build();
                } else {
                    return Response.status(Response.Status.NOT_ACCEPTABLE).entity("misspelling").build();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return Response.status(Response.Status.CONFLICT).entity("error").build();

    }


    @Operation(description = "Update expense.", summary = "")
    @APIResponses({
            @APIResponse(
                    responseCode = "200",
                    description = "Expense successfully updated."
            )
    })
    @PUT
    @Path("{expenseId}")
    public Response putExpense(@Parameter(description = "Expense ID.", required = true)
                                     @PathParam("expenseId") Integer expenseId,
                                     @RequestBody(
                                             description = "DTO object with expense.",
                                             required = true, content = @Content(
                                             schema = @Schema(implementation = Expenses.class)))
                                     Expenses expense){

        expense = expensesBean.putExpense(expenseId, expense);

        if (expense == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.status(Response.Status.NOT_MODIFIED).build();

    }

    @Operation(description = "Delete expense.", summary = "")
    @APIResponses({
            @APIResponse(
                    responseCode = "200",
                    description = "Expense successfully deleted."
            ),
            @APIResponse(
                    responseCode = "404",
                    description = "Not found."
            )
    })
    @DELETE
    @Path("{expenseId}")
    public Response deleteExpense(@Parameter(description = "Expense ID.", required = true)
                                        @PathParam("expenseId") Integer expenseId){

        boolean deleted = expensesBean.deleteExpense(expenseId);

        if (deleted) {
            return Response.status(Response.Status.NO_CONTENT).build();
        }
        else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }





}
