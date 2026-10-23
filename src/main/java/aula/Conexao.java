package aula;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
    private static final String URL = "jdbc:sqlite:projeto_loja.db";

    public static Connection abrir() throws SQLException {
        return DriverManager.getConnection(URL);
    }
}
