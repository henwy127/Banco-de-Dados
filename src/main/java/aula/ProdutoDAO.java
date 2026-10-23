package aula;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDAO {

    public void criarTabela() {
        String sql = "CREATE TABLE IF NOT EXISTS produto(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "descricao VARCHAR(50) NOT NULL, " +
                "preco REAL NOT NULL)";

        try (Connection conexao = Conexao.abrir();
             Statement comando = conexao.createStatement()) {

            comando.execute(sql);
            System.out.println("Tabela criada com sucesso!");

        } catch (SQLException e) {
            System.out.println("Erro ao criar tabela: " + e.getMessage());
        }
    }

    public int inserir(String descricao, double preco) throws SQLException {
        String sql = "INSERT INTO produto (descricao, preco) VALUES (?, ?)";

        try (Connection conexao = Conexao.abrir();
             PreparedStatement comando = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            comando.setString(1, descricao);
            comando.setDouble(2, preco);
            comando.executeUpdate();

            try (ResultSet chaves = comando.getGeneratedKeys()) {
                if (chaves.next()) {
                    return chaves.getInt(1);
                } else {
                    throw new SQLException("Nenhum ID foi gerado.");
                }
            }
        }
    }

    public List<Produto> listar() throws  SQLException{
        List<Produto> listaProdutos = new ArrayList<Produto>();

        String sql = "SELECT id, descricao, preco FROM produto ORDER BY id;";

        try(Connection conexao = Conexao.abrir();
        PreparedStatement comando = conexao.prepareStatement(sql);
        ResultSet resultado = comando.executeQuery();){

            while(resultado.next()){
                int id = resultado.getInt("id");
                String descricao = resultado.getString("descricao");
                double preco = resultado.getDouble("preco");

                listaProdutos.add(new Produto(id, descricao, preco));
            }
        }

        return listaProdutos;
    }
}