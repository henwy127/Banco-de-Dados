package aula;

import java.sql.SQLException;
import java.util.List;

public class App {
    public static void main(String[] args) {
        ProdutoDAO dao = new ProdutoDAO();

        try {
            // 1. Cria a tabela se não existir
            dao.criarTabela();

            // 2. Realiza a busca por descrição
            List<Produto> produtos = dao.buscarPorDescricao("Monitor");

            if (produtos != null && !produtos.isEmpty()) {
                System.out.println("Produtos encontrados: " + produtos.size());

                for (Produto p : produtos) {
                    System.out.println("-------------------------");
                    System.out.println("ID: " + p.getId());
                    System.out.println("Descrição: " + p.getDescricao());
                    System.out.println("Preço: " + p.getPreco());
                }
            } else {
                System.out.println("Nenhum produto encontrado com essa descrição.");
            }

        } catch (Exception e) {
            System.out.println("Erro ao buscar produto: " + e.getMessage());
        }
    }
}