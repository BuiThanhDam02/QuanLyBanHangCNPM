package view;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        try {
            new ManageProductsView();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
}
