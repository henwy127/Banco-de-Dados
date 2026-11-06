package aula;

import net.bytebuddy.dynamic.scaffold.MethodRegistry;

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

    public Produto buscarPorId(int id) throws SQLException{
        String sql = "SELECT id, descricao, preco FROM produto Where id = ?";

        try(Connection conexao = Conexao.abrir();
            PreparedStatement comando = conexao.prepareStatement(sql)){

            comando.setInt(1, id);

            ResultSet resultado = comando.executeQuery();

            if(resultado.next()){
                return new Produto(resultado.getInt("id"),
                        resultado.getString("descricao"),
                        resultado.getDouble("preco"));
            }
        }
        return null;
    }


    public List<Produto> buscarPorPreco(double preco) throws SQLException {
        String sql = "SELECT id, descricao, preco FROM produto WHERE preco <= ?";
        List<Produto> listaProdutos = new ArrayList<>();

        try (Connection conexao = Conexao.abrir();
             PreparedStatement comando = conexao.prepareStatement(sql)) {

            comando.setDouble(1, preco);

            try (ResultSet resultado = comando.executeQuery()) {
                while (resultado.next()) {
                    listaProdutos.add(new Produto(
                            resultado.getInt("id"),
                            resultado.getString("descricao"),
                            resultado.getDouble("preco")
                    ));
                }
            }
        }
        return listaProdutos;
    }

    public List<Produto> buscarPorDescricao(String descricao) throws SQLException {
        String sql = "SELECT id, descricao, preco FROM produto WHERE descricao LIKE ?";
        List<Produto> listaProdutos = new ArrayList<>();

        try (Connection conexao = Conexao.abrir();
             PreparedStatement comando = conexao.prepareStatement(sql)) {

            comando.setString(1, "%" + descricao + "%");

            try (ResultSet resultado = comando.executeQuery()) {
                while (resultado.next()) {
                    listaProdutos.add(new Produto(
                            resultado.getInt("id"),
                            resultado.getString("descricao"),
                            resultado.getDouble("preco")
                    ));
                }
            }
        }
        return listaProdutos;
    }
}