package com.bitcoinfraud;

import java.io.IOException;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/AdminServlet")
public class AdminServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");

        try {

            Connection con = DBConnection.initializeDatabase();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM fraud_reports ORDER BY report_date DESC");

            response.getWriter().println("<html><body>");
            response.getWriter().println("<h2>Fraud Reports</h2>");
            response.getWriter().println("<table border='1'>");
            response.getWriter().println("<tr><th>ID</th><th>Category</th><th>Description</th><th>BTC</th><th>Amount</th><th>Name</th><th>Email</th><th>Status</th><th>Date</th></tr>");

            while (rs.next()) {

                response.getWriter().println("<tr>");
                response.getWriter().println("<td>" + rs.getInt("id") + "</td>");
                response.getWriter().println("<td>" + rs.getString("category") + "</td>");
                response.getWriter().println("<td>" + rs.getString("description") + "</td>");
                response.getWriter().println("<td>" + rs.getString("btc_address") + "</td>");
                response.getWriter().println("<td>" + rs.getBigDecimal("amount_lost") + "</td>");
                response.getWriter().println("<td>" + rs.getString("reporter_name") + "</td>");
                response.getWriter().println("<td>" + rs.getString("reporter_email") + "</td>");
                response.getWriter().println("<td>" + rs.getString("status") + "</td>");
                response.getWriter().println("<td>" + rs.getTimestamp("report_date") + "</td>");
                response.getWriter().println("</tr>");
            }

            response.getWriter().println("</table>");
            response.getWriter().println("</body></html>");

            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
