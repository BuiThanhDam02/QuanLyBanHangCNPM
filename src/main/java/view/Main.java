package view;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        try {
             new MenuFrame();
//					frame.setVisible(true);

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
}
