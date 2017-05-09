package io.fastup;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;

public class SimpleServlet extends HttpServlet {
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        res.setContentType("application/json");
        PrintWriter out = res.getWriter();
        out.println("{'message':'hello world FROM API TIER','version':" + System.getenv("FastupVersion") + "}");
        URL customerApp = new URL("https://customer.spinci.fastup.io/customer-app-1.0-SNAPSHOT/service");
        BufferedReader in = new BufferedReader(new InputStreamReader(customerApp.openStream()));
        String inputLine;
        StringBuilder resString = new StringBuilder();

        while ((inputLine = in.readLine()) != null)
            resString.append(inputLine);
        out.println(
                "{'message':'hello world FROM API TIER','version':" + System.getenv("FastupVersion") + "," +
                        "'customer_message':'" + resString.toString() + "'}"
        );
        in.close();
        out.flush();
        out.close();
    }

    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        doGet(req, res);
    }
}
