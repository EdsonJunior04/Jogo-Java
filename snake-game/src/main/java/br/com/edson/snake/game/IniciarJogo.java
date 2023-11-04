package br.com.edson.snake.game;

import javax.swing.*;

public class IniciarJogo extends JFrame {

    private String nomeCadastro;


    IniciarJogo(String nomeCadastro) {
        
        add(new TelaJogo(nomeCadastro));
        setTitle("Jogo da Cobrinha - Snake game");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        pack();
        setVisible(true);
        setLocationRelativeTo(null);
    }
}
