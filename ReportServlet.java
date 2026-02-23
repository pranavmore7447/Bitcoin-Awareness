package com.bitcoinfraud;

import java.io.IOException;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/SaveReportServlet")
public class ReportServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String category = request.getParameter("fraud_type");
        String description = request.getParameter("description");
        String btc = request.getParameter("btc_address");
        String amount = request.getParameter("amount_lost");
        String name = request.getParameter("name");
        String email = request.getParameter("email");

        try {

            Connection con = DBConnection.initializeDatabase();

            String query = "INSERT INTO fraud_reports "
                    + "(category, description, btc_address, amount_lost, reporter_name, reporter_email) "
                    + "VALUES (?, ?, ?, ?, ?, ?)";

            PreparedStatement pst = con.prepareStatement(query);

            pst.setString(1, category);
            pst.setString(2, description);
            pst.setString(3, btc);

            if (amount == null || amount.isEmpty())
                pst.setNull(4, java.sql.Types.NUMERIC);
            else
                pst.setBigDecimal(4, new java.math.BigDecimal(amount));

            pst.setString(5, name);
            pst.setString(6, email);

            pst.executeUpdate();

            pst.close();
            con.close();

            response.sendRedirect("index.html");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
