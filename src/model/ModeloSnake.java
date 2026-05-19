package model;

import java.util.Random;

public class ModeloSnake {
    
    public int CELL_SIZE = 60;  
    public final int MAX_LENGTH = 100; 

    public MapaSnake mapa = new MapaSnake();
    public int ptos = 0;
    public boolean crash = false;

    public int[] snakeRow = new int[MAX_LENGTH];
    public int[] snakeCol = new int[MAX_LENGTH];
    public int snakeLength = 1;
    public int tiempoZoom = 0;

    public int dirRow = 0, dirCol = 0;
    public int nextDirRow = 0, nextDirCol = 0;

    public ModeloSnake() {
        // Posición inicial
        snakeRow[0] = 6;
        snakeCol[0] = 5;
    }

    public void reiniciar() {
        CELL_SIZE = 60;
        ptos = 0;
        crash = false;
        snakeLength = 1;
        dirRow = 0;   dirCol = 0;
        nextDirRow = 0; nextDirCol = 0;

        mapa = new MapaSnake(); // Reiniciamos el mapa
        snakeRow[0] = 6;
        snakeCol[0] = 5;
        tiempoZoom = 0;
    }

    public void sincronizarSerpiente() {
        int n = 10; 
        for (int i = 0; i < snakeLength; i++) {
            int viejaFila = snakeRow[i];
            int viejaCol = snakeCol[i];
            
            snakeRow[i] = viejaCol;          
            snakeCol[i] = n - 1 - viejaFila; 
        }
        int viejaDirRow = dirRow;
        dirRow = dirCol;
        dirCol = -viejaDirRow;

        int viejaNextDirRow = nextDirRow;
        nextDirRow = nextDirCol;
        nextDirCol = -viejaNextDirRow;
    }

    public void actualizarLogica() {
        // Si no se ha pulsado nada o hemos chocado, no calculamos nada
        if (nextDirRow == 0 && nextDirCol == 0) return;
        if (crash) return;

        dirRow = nextDirRow;
        dirCol = nextDirCol;

        int newRow = snakeRow[0] + dirRow;
        int newCol = snakeCol[0] + dirCol;

        int cell = mapa.mapa[newRow][newCol];
        if (cell == 1 || cell == 2 || cell == 3) { 
            crash = true;
            return;
        }

        boolean ateApple = (cell == 4 || cell == 5 || cell == 6);

        if (ateApple) {
            ptos++;
            snakeLength = Math.min(snakeLength + 1, MAX_LENGTH); 
            mapa.nuevaManzana(); 
        } else {
            mapa.mapa[snakeRow[snakeLength - 1]][snakeCol[snakeLength - 1]] = 0;
        }

        if (cell == 6) {
            Random rand = new Random();
            CELL_SIZE = rand.nextInt(30,60);
            tiempoZoom = 33; 
        }

        for (int i = snakeLength - 1; i > 0; i--) {
            snakeRow[i] = snakeRow[i - 1];
            snakeCol[i] = snakeCol[i - 1];
        }

        snakeRow[0] = newRow;
        snakeCol[0] = newCol;

        if (snakeLength > 1) {
            mapa.mapa[snakeRow[1]][snakeCol[1]] = 3;
        }
        mapa.mapa[snakeRow[0]][snakeCol[0]] = 2;

        if (cell == 5) { 
            mapa.giroMapa();
            sincronizarSerpiente();
        }

        if (tiempoZoom == 0) {
            CELL_SIZE = 60;
        }
        if (tiempoZoom > 0) {
            tiempoZoom--;
        }
    }
}