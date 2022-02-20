/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.iris.ConexaoBanco;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class dataBase {

    public static Connection conectar() throws SQLException {

        String user = "iris";
        String password = "SenMy@do";
        String url = "jdbc:mysql://localhost:3306/milton";

        try {

            Connection conexao = DriverManager.getConnection(url, user, password);
            return conexao;
        } catch (SQLException ex) {
            ex.printStackTrace();//mostrar o erro por n ter conectado
            System.out.println("Conexão Falhou!!");

        }
        return null;

    }

}
