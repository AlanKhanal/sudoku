package sudoku;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class connect {
	private static final String jdbcUrl = "jdbc:mysql://localhost:3306/sudoku";
    private static final String username = "root";
    private static final String password = "";
    
	public static Connection getConnection() throws SQLException{
		return DriverManager.getConnection(jdbcUrl,username,password);
	}
}
