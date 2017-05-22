package io.fastup;

import org.apache.log4j.Logger;

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
        Logger.getLogger(this.getClass()).debug("Starting GET");
        res.setContentType("application/json");
        PrintWriter out = res.getWriter();
        out.println("{'message':'hello world SpinSci API','version':" + System.getenv("FastupVersion") + "}");
        StringBuilder resString = getRemoteResponse();
        out.println(
                "{'message':'hello SpinSci world SpinSci API','version':" + System.getenv("FastupVersion") + "," +
                        "'customer_message':'" + resString.toString() + "'}"
        );
        out.flush();
        out.close();
        Logger.getLogger(this.getClass()).debug("Finishing GET");
    }

    private StringBuilder getRemoteResponse() throws IOException {
        Logger.getLogger(this.getClass()).debug("Attempting to get response from customer app");
        URL customerApp = new URL("https://customer.spinscicloud.fastup.io/customer-app-1.0-SNAPSHOT/service");
        BufferedReader in = new BufferedReader(new InputStreamReader(customerApp.openStream()));
        String inputLine;
        StringBuilder resString = new StringBuilder();
        while ((inputLine = in.readLine()) != null)
            resString.append(inputLine);
        in.close();
        Logger.getLogger(this.getClass()).debug("Received response: " + resString.toString());
        return resString;
    }

    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        doGet(req, res);
    }
}
