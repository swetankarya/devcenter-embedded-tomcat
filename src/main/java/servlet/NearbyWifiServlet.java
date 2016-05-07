package servlet;

import helper.CommunicationManager;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;

@WebServlet(
        name = "MyServlet", 
        urlPatterns = {"/getNearbyWifi"}
    )
public class NearbyWifiServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
    	String localityName = req.getParameter("localityName");
    	String subAdminArea = req.getParameter("subAdminArea");
    	String countryName = req.getParameter("countryName");
    	List<DBObject> countryList = CommunicationManager.getNearByWifi(localityName, subAdminArea, countryName);
        ServletOutputStream out = resp.getOutputStream();
        out.write(JSON.serialize(countryList).getBytes());
        out.flush();
        out.close();
    }
    
}
