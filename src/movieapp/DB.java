package movieapp;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DB {
    final String JDBC_DRIVER = "org.apache.derby.jdbc.EmbeddedDriver";
    final String URL = "jdbc:derby:sampleDB;create=true";
    final String USERNAME = "";
    final String PASSWORD = "";
    
    Connection conn = null;
    Statement crstmt = null;
    DatabaseMetaData dbmd = null;
    
    public DB () {
        try {
            conn = DriverManager.getConnection(URL);
        } catch (SQLException ex) {
            System.out.println("Problem while establishing connection: " + ex);
        }
        
        if (conn != null) {
            try {
                crstmt = conn.createStatement();
            } catch (SQLException ex) {
                System.out.println("Problem while creating the statement: " + ex);
            }
        }
        
        try {
            dbmd = conn.getMetaData();
        } catch (SQLException ex) {
            System.out.println("" + ex);
        }
        
        try {
            ResultSet rs = dbmd.getTables(null, "APP", "USERS", null);
            if (!rs.next()) {
//			crstmt.execute("create table movies (id INT not null primary key GENERATED ALWAYS AS IDENTITY(START WITH 1, INCREMENT BY 1), title varchar(20), length varchar(8), language varchar(15), date varchar(10))");
            }
        } catch (SQLException ex) {
                System.out.println("Problem while creating the table: " + ex);
        }
    
    }
    
    public void addMovie(Movie movie) {
        try {
            String sql = "insert into movies (title, length, language, date) values (?, ?, ?, ?)";
            PreparedStatement prpstmt = conn.prepareStatement(sql);
            prpstmt.setString(1, movie.getTitle());
            prpstmt.setString(2, movie.getLength());
            prpstmt.setString(3, movie.getLanguage());
            prpstmt.setString(4, movie.getDate());
            prpstmt.execute();
                    } catch (SQLException ex) {
            System.out.println("Error adding movie: " + ex);
        }
                
    }
    
    public void updateMovie(Movie movie) {
        try {
            String sql = "update movies set title = ?, length = ?, language = ?, date = ?";
            PreparedStatement prpstmt = conn.prepareStatement(sql);
            prpstmt.setString(1, movie.getTitle());
            prpstmt.setString(1, movie.getLength());
            prpstmt.setString(1, movie.getLanguage());
            prpstmt.setString(1, movie.getDate());
            prpstmt.execute();
        } catch (SQLException e) {
            System.out.println("Error updating movies: " + e);
        }
    }
    
    public void removeMovie(Movie movie) {
        try {
            String sql = "delete from movies where id = ?";
            PreparedStatement prpstmt = conn.prepareStatement(sql);
            prpstmt.setInt(1, Integer.parseInt(movie.getId()));
            prpstmt.execute();
        } catch (SQLException ex) {
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public ArrayList<Movie> getAllMovies() {
        String sql = "select * from movies";
        ArrayList<Movie> movies = null;
        
        try {
            ResultSet rs = crstmt.executeQuery(sql);
            movies = new ArrayList<>();
            
            while (rs.next()) {
                Movie actMovie = new Movie(rs.getInt("id"), rs.getString("title"), rs.getString("length"), rs.getString("language"), rs.getString("date"));
                movies.add(actMovie);
            }
        } catch (SQLException e) {
            System.out.println("Error getting the movies: " + e);
        }
        return movies;
    }
}
