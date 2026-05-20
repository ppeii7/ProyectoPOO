package model;

import java.util.Random;

public class ModeloSnake extends Juego{
    
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
    	super("Snake", 1, 1);
        // Posición inicial
        snakeRow[0] = 4;
        snakeCol[0] = 3;
    }

    public void reiniciar() {
        CELL_SIZE = 60;
        ptos = 0;
        crash = false;
        snakeLength = 1;
        dirRow = 0;   dirCol = 0;
        nextDirRow = 0; nextDirCol = 0;

        mapa = new MapaSnake(); // Reiniciamos el mapa
        snakeRow[0] = 4;
        snakeCol[0] = 3;
        tiempoZoom = 0;
    }

    public void sincronizarSerpiente() { //para cuando se gira el mapa
        int n = 10;
        for (int i = 0; i < snakeLength; i++) { //gira el cuerpo de la serpiente
            int viejaFila = snakeRow[i];
            int viejaCol = snakeCol[i];
            
            snakeRow[i] = viejaCol; //la nueva fila pasa a ser la columna de antes
            snakeCol[i] = n - 1 - viejaFila; // y la nueva columna pasa a ser el tamaño del mapa menos 1(pared) menos la fila de antes
        }
        int viejaDirRow = dirRow; //Cambio la direccion a la que va la serpiente
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
            snakeLength = Math.min(snakeLength + 1, MAX_LENGTH);  //Aumentamos el tamaño de la serpiente
            mapa.nuevaManzana(); 
        } else {
            mapa.mapa[snakeRow[snakeLength - 1]][snakeCol[snakeLength - 1]] = 0; //Si no comio manzana quitamos la ultima parte de la serpiente
        }

        if (cell == 6) { //Si come una manzana de zoom se cambia el tamaño de las casillas aleatoriamente por 5 segundos
            Random rand = new Random();
            CELL_SIZE = rand.nextInt(30,60);
            tiempoZoom = 33;   // Definí que se actualize el mapa cada 150ms 
            // y quiero hacer que el zoom dure 5 seg --> 5000ms/150ms = 33,3 --> 33
        }

        for (int i = snakeLength - 1; i > 0; i--) { // La parte de la serpiente pasa a la posicion donde estaba la siguiente parte
            snakeRow[i] = snakeRow[i - 1];
            snakeCol[i] = snakeCol[i - 1];
        } 

        snakeRow[0] = newRow;// La nueva parte de la serpiente es la cabeza
        snakeCol[0] = newCol;

        //Actualizo el mapa
        if (snakeLength > 1) {
            mapa.mapa[snakeRow[1]][snakeCol[1]] = 3;
        }
        mapa.mapa[snakeRow[0]][snakeCol[0]] = 2; 

        if (cell == 5) {  // Si come una manzana de giro
            mapa.giroMapa();
            sincronizarSerpiente();
        }

        if (tiempoZoom == 0) {
            CELL_SIZE = 60; //cuando se acaba el efecto del zoom vuelve al tamaño normal
        }
        if (tiempoZoom > 0) {
            tiempoZoom--;
        }
    }

	@Override
	public void inicializar() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String procesarTurno(Jugador jugador, String entrada) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isPartidaTerminada() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getPuntuacion(Jugador jugador) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Jugador getGanador() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getEstadoVisible() {
		// TODO Auto-generated method stub
		return null;
	}
}