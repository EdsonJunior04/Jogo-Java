package br.com.edson.snake.game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;
import pro.mongocrud.conectaMongo;

public class TelaJogo extends JPanel implements ActionListener {

    private String nomeCadastro;
    private static final int LARGURA_TELA = 1300;
    private static final int ALTURA_TELA = 750;
    private static final int TAMANHO_BLOCO = 50;
    private static final int UNIDADES = LARGURA_TELA * ALTURA_TELA / (TAMANHO_BLOCO * TAMANHO_BLOCO);
    private static final int INTERVALO = 80;
    private static final String NOME_FONTE = "Ink Free";
    private final int[] eixoX = new int[UNIDADES];
    private final int[] eixoY = new int[UNIDADES];
    private final int[] eixoX2 = new int[UNIDADES];
    private final int[] eixoY2 = new int[UNIDADES];
    private int corpoCobra = 3;
    private int corpoCobra2 = 3;
    int blocosComidos = 0;
    private int blocoX;
    private int blocoY;
    private int blocoX2;
    private int blocoY2;
    private char direcao = 'D'; // C - Cima, B - Baixo, E - Esquerda, D - Direita
    private boolean estaRodando = false;
    Timer timer;
    Random random;

    public TelaJogo(String nome) {
        this.nomeCadastro = nome;
        random = new Random();
        setPreferredSize(new Dimension(LARGURA_TELA, ALTURA_TELA));
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(new LeitorDeTeclasAdapter());
        iniciarJogo();
    }

    public void iniciarJogo() {
        criarBloco();
        criarBloco2();
        estaRodando = true;
        timer = new Timer(INTERVALO, this);
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        desenharTela(g);
    }

    public void desenharTela(Graphics g) {

        if (estaRodando) {
            g.setColor(Color.BLUE);
            g.fillOval(blocoX, blocoY, TAMANHO_BLOCO, TAMANHO_BLOCO);
            g.fillOval(blocoX2, blocoY2, TAMANHO_BLOCO, TAMANHO_BLOCO);

            for (int i = 0; i < corpoCobra; i++) {
                if (i == 0) {
                    g.setColor(Color.WHITE);
                    g.fillRect(eixoX[0], eixoY[0], TAMANHO_BLOCO, TAMANHO_BLOCO);
                } else {
                    g.setColor(new Color(238, 232, 170));
                    g.fillRect(eixoX[i], eixoY[i], TAMANHO_BLOCO, TAMANHO_BLOCO);
                }
            }

            for (int i = 0; i < corpoCobra2; i++) {
                if (i == 0) {
                    g.setColor(Color.WHITE);
                    g.fillRect(eixoX2[0], eixoY2[0], TAMANHO_BLOCO, TAMANHO_BLOCO);
                } else {
                    g.setColor(new Color(238, 232, 170));
                    g.fillRect(eixoX2[i], eixoY2[i], TAMANHO_BLOCO, TAMANHO_BLOCO);
                }
            }
            g.setColor(Color.BLUE);
            g.setFont(new Font(NOME_FONTE, Font.BOLD, 40));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("Pontos: " + blocosComidos, (LARGURA_TELA - metrics.stringWidth("Pontos: " + blocosComidos)) / 2, g.getFont().getSize());
        } else {
            fimDeJogo(g);
            conectaMongo conn = new conectaMongo();
            int score = blocosComidos;
            conn.insertValues(nomeCadastro, score);
            conn.getValues();
        }
    }

    private void criarBloco() {
        blocoX = random.nextInt(LARGURA_TELA / TAMANHO_BLOCO) * TAMANHO_BLOCO;
        blocoY = random.nextInt(ALTURA_TELA / TAMANHO_BLOCO) * TAMANHO_BLOCO;
    }

    private void criarBloco2() {
        blocoX2 = random.nextInt(LARGURA_TELA / TAMANHO_BLOCO) * TAMANHO_BLOCO;
        blocoY2 = random.nextInt(ALTURA_TELA / TAMANHO_BLOCO) * TAMANHO_BLOCO;
    }

    public void fimDeJogo(Graphics g) {
        g.setColor(Color.RED);
        g.setFont(new Font(NOME_FONTE, Font.BOLD, 40));
        FontMetrics fontePontuacao = getFontMetrics(g.getFont());
        g.drawString("Sua Pontuação: " + blocosComidos, (LARGURA_TELA - fontePontuacao.stringWidth("Pontos: " + blocosComidos)) / 2, g.getFont().getSize());
        g.setColor(Color.RED);
        g.setFont(new Font(NOME_FONTE, Font.BOLD, 75));
        FontMetrics fonteFinal = getFontMetrics(g.getFont());
        g.drawString("\uD83D\uDE1D Fim do Jogo.", (LARGURA_TELA - fonteFinal.stringWidth("Fim do Jogo")) / 2, ALTURA_TELA / 2);
    }

    public void actionPerformed(ActionEvent e) {
        if (estaRodando) {
            andar();
            andar2();
            alcancarBloco();
            validarLimites();
        }
        repaint();
    }

    private void andar() {
        for (int i = corpoCobra; i > 0; i--) {
            eixoX[i] = eixoX[i - 1];
            eixoY[i] = eixoY[i - 1];
        }

        switch (direcao) {
            case 'C':
                eixoY[0] = eixoY[0] - TAMANHO_BLOCO;
                break;
            case 'B':
                eixoY[0] = eixoY[0] + TAMANHO_BLOCO;
                break;
            case 'E':
                eixoX[0] = eixoX[0] - TAMANHO_BLOCO;
                break;
            case 'D':
                eixoX[0] = eixoX[0] + TAMANHO_BLOCO;
                break;
            default:
                break;
        }
    }

    private void andar2() {
        for (int i = corpoCobra2; i > 0; i--) {
            eixoX2[i] = eixoX2[i - 1];
            eixoY2[i] = eixoY2[i - 1];
        }

        switch (direcao) {
            case 'C':
                eixoY2[0] = eixoY2[0] - TAMANHO_BLOCO;
                break;
            case 'B':
                eixoY2[0] = eixoY2[0] + TAMANHO_BLOCO;
                break;
            case 'E':
                eixoX2[0] = eixoX2[0] - TAMANHO_BLOCO;
                break;
            case 'D':
                eixoX2[0] = eixoX2[0] + TAMANHO_BLOCO;
                break;
            default:
                break;
        }
    }

    private void alcancarBloco() {
        if (eixoX[0] == blocoX && eixoY[0] == blocoY) {
            corpoCobra++;
            blocosComidos++;
            criarBloco();
        }

        if (eixoX2[0] == blocoX2 && eixoY2[0] == blocoY2) {
            corpoCobra2++;
            blocosComidos++;
            criarBloco2();
        }
    }

    private void validarLimites() {
        //A cabeça bateu no corpo?
        for (int i = corpoCobra; i > 0; i--) {
            if (eixoX[0] == eixoX[i] && eixoY[0] == eixoY[i]) {
                estaRodando = false;
                break;
            }
        }

        for (int i = corpoCobra2; i > 0; i--) {
            if (eixoX2[0] == eixoX2[i] && eixoY2[0] == eixoY2[i]) {
                estaRodando = false;
                break;
            }
        }

        //A cabeça tocou uma das bordas Direita ou esquerda?
        if (eixoX[0] < 0 || eixoX[0] > LARGURA_TELA || eixoX2[0] < 0 || eixoX2[0] > LARGURA_TELA) {
            estaRodando = false;
        }

        //A cabeça tocou o piso ou o teto?
        if (eixoY[0] < 0 || eixoY[0] > ALTURA_TELA || eixoY2[0] < 0 || eixoY2[0] > ALTURA_TELA) {
            estaRodando = false;
        }

        if (!estaRodando) {
            timer.stop();
        }
    }

    public class LeitorDeTeclasAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    if (direcao != 'D') {
                        direcao = 'E';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if (direcao != 'E') {
                        direcao = 'D';
                    }
                    break;
                case KeyEvent.VK_UP:
                    if (direcao != 'B') {
                        direcao = 'C';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if (direcao != 'C') {
                        direcao = 'B';
                    }
                    break;
                case KeyEvent.VK_A:
                    if (direcao != 'D') {
                        direcao = 'E';
                    }
                    break;
                case KeyEvent.VK_D:
                    if (direcao != 'E') {
                        direcao = 'D';
                    }
                    break;
                case KeyEvent.VK_W:
                    if (direcao != 'S') {
                        direcao = 'C';
                    }
                    break;
                case KeyEvent.VK_S:
                    if (direcao != 'C') {
                        direcao = 'B';
                    }
                    break;
                default:
                    break;
            }
        }
    }
}
