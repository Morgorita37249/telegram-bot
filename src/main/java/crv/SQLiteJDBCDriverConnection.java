package crv;

import java.sql.*;

public class SQLiteJDBCDriverConnection {
    String url="";
    private Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public void createNewTable() {
        String url = "";

        String sql = "CREATE TABLE IF NOT EXISTS tags (\n"
                + " id integer PRIMARY KEY,\n"
                + " tag text NOT NULL,\n"
                + " value text NOT NULL\n"
                + ");";

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void insertTag(Long id, String tag, String value) {
        String sql = "INSERT INTO tags(id,tag,value) VALUES(?,?,?)";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, id);
            pstmt.setString(2, tag);
            pstmt.setString(3, value);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public String selectTag(Long id, String tag){
        String sql = "SELECT value FROM tags WHERE id = ? AND tag = ?";
        String value = "";

        try (Connection conn = this.connect();
             PreparedStatement pstmt  = conn.prepareStatement(sql)){
            pstmt.setLong(1,id);
            pstmt.setString(2,tag);
            ResultSet rs  = pstmt.executeQuery();
            value = rs.getString("value");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return value;
    }
}