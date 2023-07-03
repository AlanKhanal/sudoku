package sudoku;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class connect {
	public static final String jdbcUrl = "jdbc:mysql://localhost:3306/sudoku";
    public static final String username = "root";
    public static final String password = "";
    
	public static Connection getConnection() throws SQLException{
		return DriverManager.getConnection(jdbcUrl,username,password);
	}
}
