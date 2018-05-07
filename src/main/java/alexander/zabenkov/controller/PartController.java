package alexander.zabenkov.controller;

import alexander.zabenkov.business.BusinessContext;
import alexander.zabenkov.business.entity.Part;
import alexander.zabenkov.business.service.PartService;
import alexander.zabenkov.business.specification.PartSpecificationWithAllFieldsAndSorting;
import alexander.zabenkov.business.utils.SqlSortingOrder;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Servlet for managing root Get and Post http-requests.
 */
public class PartController extends HttpServlet {
    Logger logger = Logger.getLogger(this.getClass().getName());

    /** Path to view */
    private static final String PART_INDEX = "view/part.jsp";

    /** Part service */
    private PartService partService;

    public PartController() {
        super();
        partService = new PartService();
    }

    /**
     * Runs database schema migration.
     * Called when servlet is upped.
     */
    @Override
    public void init() {
        BusinessContext.getSchemaMigrator().migrateSchema();
    }

    /**
     * Closes database connection.
     * Called when servlet is downs.
     */
    @Override
    public void destroy() {
        try {
            BusinessContext.getConnection().close();
        } catch (SQLException e) {
            logger.severe("[SQL Error] Unable to close database connection: " + e.getMessage());
        }
    }

    /**
     * Responds with collection of all Part data and part.jsp view by Get http-request.
     * @param req http-request
     * @param resp http-response with collection of parts
     */
    @Override
    protected void doGet(HttpServletRequest req,
                         HttpServletResponse resp) throws ServletException, IOException {

        req.setAttribute("partsList", partService.getAllParts());

        RequestDispatcher view = req.getRequestDispatcher(PART_INDEX);
        view.forward(req, resp);
    }

    /**
     * Responds with collection of filtered Part data in JSON by Post http-request.
     * @param req http-request
     * @param resp http-response with collection of parts
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, String> filters = new Gson().fromJson(req.getParameter("filters"), Map.class);

        List<Part> parts = partService.getParts(filters);

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        resp.getWriter().write(gson.toJson(parts));
    }
}
