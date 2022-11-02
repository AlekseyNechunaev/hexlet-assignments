package exercise.servlet;

import exercise.Data;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static exercise.Data.getCompanies;

@WebServlet(name = "CompaniesServlet", urlPatterns = "/companies")
public class CompaniesServlet extends HttpServlet {

    private List<String> companies;

    @Override
    public void init() throws ServletException {
        super.init();
        companies = Data.getCompanies();
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String queryString = request.getQueryString();
        String parameterValue = request.getParameter("search");
        PrintWriter writer = response.getWriter();
        List<String> resultCompanyList;
        if (queryString == null || parameterValue.isEmpty()) {
            resultCompanyList = this.showAllCompanies();
        } else {
            resultCompanyList = this.showCompaniesWhereNameContains(parameterValue);
        }
        String result = printCompanies(resultCompanyList);
        writer.write(result);
        writer.close();
    }

    private List<String> showAllCompanies() {
        return companies;
    }

    private List<String> showCompaniesWhereNameContains(String subText) {
        return this.companies.stream()
                .filter(c -> c.contains(subText))
                .collect(Collectors.toList());
    }

    private String printCompanies(List<String> companiesList) {
        return companiesList.isEmpty() ? "Companies not found" : String.join("\n", companiesList);
    }
}
