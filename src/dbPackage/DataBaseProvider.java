package dbPackage;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;


public class DataBaseProvider{
	
	private static Connection Connect = null;
	public static Statement Statement;
	private static DataBaseProvider _instance = null;
	
	private String uri = null;
	private String login = null;
	private String pass = null;
	
	//constructor
	private DataBaseProvider(){
		InitConnection();
	}
	
	public static Statement GetNewStatement() throws SQLException	{
		return Connect.createStatement();
	}
	
	private void InitConnection()	{
		try{
			LoadDbConfig();
			
			try {
				Class.forName("com.mysql.jdbc.Driver");
				System.out.println("Driver loading success!");
	        }
	        catch (ClassNotFoundException e) {
	           System.err.println (e) ;
	           System.exit (-1) ;
	        }
            
			//String url = "jdbc:mysql://localhost/";
			Connect = DriverManager.getConnection(uri,login,pass);
			// create a statement
			Statement = Connect.createStatement();
			boolean res = Statement.execute("Select * from fmdat.customer");
			if (res)
				System.out.println("Connected to fmdat");
			else
				System.out.println("Something wrong! Check SQL connect");
		}
		catch(java.sql.SQLException e)
		{
			e.printStackTrace();
			System.out.println("********\nConnect to MySql failed!\n**********");
			System.exit (-1) ;
		}
	}
	
	public static synchronized DataBaseProvider GetInstance()
    {			
		if (_instance == null)
			_instance = new DataBaseProvider();
		return _instance;		
    }
	
	private void LoadDbConfig()	{
		FileInputStream fis;
        Properties property = new Properties();
 
        try {
            fis = new FileInputStream("src/config.cfg");
            // load config.sys from stream
            property.load(fis);
 
            uri = property.getProperty("db.uri");
            login = property.getProperty("db.login");
            pass = property.getProperty("db.password");
 
            System.out.println("CHECK->URI: " + uri
                            + ", LOGIN: " + login
                            + ", PASSWORD: " + pass);
 
        } catch (IOException e) {
            System.err.println("ОШИБКА: Файл config.sys отсуствует или его невозможно открыть!");
        }
	}
	
	@Override
    protected void finalize() throws Throwable 
    {
        //close connection when exit application
		Connect.close();
        super.finalize();
    }
}