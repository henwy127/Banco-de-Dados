package aula;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class App
{
    public static void main( String[] args ) throws SQLException
    {
        ProdutoDAO dao = new ProdutoDAO();
        try {
            for(Produto p : dao.listar()){
                System.out.println(p.getId());
                System.out.println(p.getDescricao());
                System.out.println(p.getPreco());
                System.out.println("-----------");
            }
        }catch (Exception e){

        }


    }
 }

