package app.restfulapi.servlet;

import app.restfulapi.domain.PersonDTO;
import app.restfulapi.service.PersonService;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.HttpMethodConstraint;
import javax.servlet.annotation.ServletSecurity;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(loadOnStartup = 0, urlPatterns = "/persons")
@ServletSecurity(
        httpMethodConstraints = {
                @HttpMethodConstraint(value = "PUT"),
                @HttpMethodConstraint(value = "POST"),
                @HttpMethodConstraint(value = "GET"),
                @HttpMethodConstraint(value = "DELETE"),
        }
)
public class PersonServletController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        ObjectMapper mapper = new ObjectMapper();

        String personId = req.getParameter("id");
        String jsonString = "";

        if(personId != null) {
            PersonDTO person = PersonService.getInstance().getPersonById(Integer.parseInt(personId));
            jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(person);
        }
        else {
            List<PersonDTO> persons = PersonService.getInstance().getPersons();
            jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(persons);
        }
        out.print(jsonString);
        out.flush();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        ObjectMapper mapper = new ObjectMapper();

        PersonService personService = PersonService.getInstance();
        int personId = personService.getPersons().size() + 1;
        String personFirstName = req.getParameter("first_name");
        String personLastName = req.getParameter("last_name");
        boolean personActive = Boolean.getBoolean(req.getParameter("active"));

        PersonDTO person = new PersonDTO(personId, personFirstName, personLastName, personActive);
        personService.addPerson(person);

        out.print("");
        out.flush();
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        ObjectMapper mapper = new ObjectMapper();


        String personId = req.getParameter("id");
        String jsonString = "";

        if(personId != null) {
            PersonDTO person = PersonService.getInstance().getPersonById(Integer.parseInt(personId));
            person.setLastName(req.getParameter("last_name"));
            person.setFirstName(req.getParameter("first_name"));
            person.setActive(Boolean.getBoolean(req.getParameter("active")));
            PersonService personService = PersonService.getInstance();
            personService.updatePerson(person);
        }
        out.print("");
        out.flush();
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        ObjectMapper mapper = new ObjectMapper();
        String personId = req.getParameter("id");

        if(personId != null) {
            PersonService personService = PersonService.getInstance();
            personService.deletePersonById(Integer.parseInt(personId));
        }
        out.print("");
        out.flush();
    }


}
