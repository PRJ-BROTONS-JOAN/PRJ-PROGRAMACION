package es.progcipfpbatoi.classwork.controllers.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.springframework.stereotype.Service;

@Service
public class MariaDBConnection {
	private static Connection connection;
    private String ip;
    private String database;
    private String userName;
    private String password;

    public MariaDBConnection() {
        this.ip = "192.168.56.101";
        this.database = "batoiflix";
        this.userName = "batoi";
        this.password = "1234";
    }

    public Connection getConnection(){        
            try {
            	if (connection == null || connection.isClosed()) {
                String dbURL = "jdbc:mariadb://" + ip + "/" + database;
                connection = DriverManager.getConnection(dbURL, userName, password);
            	}
         } catch (SQLException exception) {
                throw new RuntimeException(exception.getMessage());
         }
            	
        return connection;
    }
    
    public void closeConnection() {
    	try {
    		connection.close();
    	 } catch (SQLException exception) {
             throw new RuntimeException(exception.getMessage());
    	 }
     
    	
    }

}
