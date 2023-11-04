package pro.mongocrud;

import java.sql.Connection;
import java.util.Scanner;
//import pro.conectarbanco.conectar;

public class Main {
    public static void main(String[] args) {
        conectaMongo conn = new conectaMongo();
        conn.getValues();        
        
        /*conectar obj = new conectar();
        Connection conexao = obj.connectionMySql();
        System.out.println("Digite o codigo a buscar no banco:");
        Scanner sc = new Scanner(System.in);
        int code = sc.nextInt();
        System.out.println("Cod: " + code);
        String x = obj.dataBaseSelect(code);
        System.out.println(x);
        obj.closeConnectionMySql(conexao);*/
    }
}

   
    //updateValues();
        // deleteValues();
