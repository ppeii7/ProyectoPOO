package control;

import java.io.File;
import java.util.Scanner;

/**
 * Controlador del panel de administración.
 * Lee el fichero HistorialTresEnRaya.txt y calcula estadísticas.
 * Formato de cada línea: nPartida;jugador1;jugador2;ganador;fecha
 * Usa únicamente arrays básicos, sin colecciones.
 */
public class ControlAdmin {

    private static final int    MAX  = 500;
    private static final String RUTA = ".\\Data\\HistorialTresEnRaya.txt";

    // ── Arrays paralelos con los datos de cada partida ───────────────────────
    private int[]    nPartidas = new int[MAX];
    private String[] jugador1s = new String[MAX];
    private String[] jugador2s = new String[MAX];
    private String[] ganadores = new String[MAX];
    private String[] fechas    = new String[MAX];
    private int      total     = 0;   // número de partidas leídas

    // ────────────────────────────────────────────────────────────────────────
    public ControlAdmin() {
        cargarHistorial();
    }

    // ── Lee el fichero línea a línea y rellena los arrays ────────────────────
    private void cargarHistorial() {
        try {
            File f = new File(RUTA);
            if (!f.exists()) return;

            Scanner sc = new Scanner(f);
            while (sc.hasNextLine() && total < MAX) {
                String linea = sc.nextLine().trim();
                if (linea.isEmpty()) continue;

                String[] campos = linea.split(";");
                if (campos.length >= 5) {
                    nPartidas[total] = Integer.parseInt(campos[0].trim());
                    jugador1s[total] = campos[1].trim();
                    jugador2s[total] = campos[2].trim();
                    ganadores[total] = campos[3].trim();
                    fechas[total]    = campos[4].trim();
                    total++;
                }
            }
            sc.close();

        } catch (Exception e) {
            System.out.println("ControlAdmin – error al cargar historial: " + e.getMessage());
        }
    }

    // ── Ranking: devuelve jugadores únicos ordenados por victorias desc ───────
    // Retorna un Object[] de dos elementos:
    //   [0] → String[]  con los nombres de jugador
    //   [1] → int[]     con sus victorias (mismo orden)
    public Object[] getRanking() {
        String[] jugadores = new String[MAX];
        int[]    victorias = new int[MAX];
        int      numJug    = 0;

        for (int i = 0; i < total; i++) {
            String g = ganadores[i];
            // ignorar empates (null o "null")
            if (g == null || g.equalsIgnoreCase("null") || g.isEmpty()) continue;

            // buscar si el jugador ya está en el ranking
            int idx = -1;
            for (int j = 0; j < numJug; j++) {
                if (jugadores[j].equalsIgnoreCase(g)) { idx = j; break; }
            }
            if (idx == -1) {
                jugadores[numJug] = g;
                victorias[numJug] = 1;
                numJug++;
            } else {
                victorias[idx]++;
            }
        }

        // Ordenar de mayor a menor victorias (burbuja)
        for (int i = 0; i < numJug - 1; i++) {
            for (int j = 0; j < numJug - i - 1; j++) {
                if (victorias[j] < victorias[j + 1]) {
                    int    tmpI = victorias[j];  victorias[j] = victorias[j+1];  victorias[j+1] = tmpI;
                    String tmpS = jugadores[j];  jugadores[j] = jugadores[j+1];  jugadores[j+1] = tmpS;
                }
            }
        }

        // Recortar arrays al tamaño real
        String[] jTrim = new String[numJug];
        int[]    vTrim = new int[numJug];
        for (int i = 0; i < numJug; i++) { jTrim[i] = jugadores[i]; vTrim[i] = victorias[i]; }

        return new Object[]{jTrim, vTrim};
    }

    // ── Cuenta cuántas partidas terminaron en empate ─────────────────────────
    public int getNumEmpates() {
        int empates = 0;
        for (int i = 0; i < total; i++) {
            String g = ganadores[i];
            if (g == null || g.equalsIgnoreCase("null") || g.isEmpty()) empates++;
        }
        return empates;
    }

    // ── Getters simples ──────────────────────────────────────────────────────
    public int      getTotal()      { return total;     }
    public int[]    getNPartidas()  { return nPartidas; }
    public String[] getJugador1s() { return jugador1s; }
    public String[] getJugador2s() { return jugador2s; }
    public String[] getGanadores() { return ganadores; }
    public String[] getFechas()    { return fechas;    }
}