package dao;

import model.Shelf;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class ShelfDAO extends DAO {

    public ShelfDAO() {
        super();
    }

    public ArrayList<Shelf> getAllShelves() {
        ArrayList<Shelf> result = new ArrayList<>();
        String sql = "SELECT shelfID, room, `row` FROM tblShelf ORDER BY shelfID";
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                Shelf shelf = new Shelf();
                shelf.setShelfID(rs.getString("shelfID"));
                shelf.setRoom(rs.getString("room"));
                shelf.setRow(rs.getString("row"));
                result.add(shelf);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
