package model.pasapalabra;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Scanner;
import model.Juego;
import model.Jugador;

public class Pasapalabra extends Juego {

    private Rosco   rosco;
    private Jugador jugadorActual;
    private int     aciertos;
    private int     fallos;
    private String  rutaFichero;   // ← nueva: para saber qué banco cargar al reanudar

    // ── Constructores ────────────────────────────────────────────────────────

    public Pasapalabra(Jugador jugador) {
        super("Pasapalabra", 1, 1);
        this.jugadorActual = jugador;
        this.rutaFichero   = ".\\data\\RoscoDifícil.txt";
        this.rosco         = new Rosco();
    }

    public Pasapalabra(Jugador jugador, String rutaFichero) {
        super("Pasapalabra", 1, 1);
        this.jugadorActual = jugador;
        this.rutaFichero   = rutaFichero;
        this.rosco         = new Rosco(rutaFichero);
    }

    @Override
    public void inicializar() {
        this.rosco    = new Rosco(rutaFichero);
        this.aciertos = 0;
        this.fallos   = 0;
    }

    // ── Guardar / cargar / borrar progreso ───────────────────────────────────

    /**
     * Guarda el estado actual en Data\Progreso_<username>.txt
     * Formato de cada línea:
     *   ruta;<rutaFichero>
     *   indice;<indiceActual>
     *   aciertos;<n>
     *   fallos;<n>
     *   <Letra>;<ESTADO>      (una línea por letra)
     */
    public void guardarProgreso() {
        try {
            String ruta = ".\\Data\\Progreso_" + jugadorActual.getUsername() + ".txt"; //Crea el nombre del fichero con el progreso del usuario
            PrintWriter pw = new PrintWriter(new FileWriter(ruta, false)); //Crea el fichero y escribe en el
            pw.println("ruta;"     + rutaFichero);
            pw.println("indice;"   + rosco.getIndiceActual()); // Cuantas preguntas llevaba contestadas
            pw.println("aciertos;" + aciertos);
            pw.println("fallos;"   + fallos);
            for (Preguntas p : rosco.getPreguntas()) {
                if (p != null) {
                    pw.println(p.getLetra() + ";" + p.getEstado().name()); // escribe la letra de la pregunta y si la acerto fallo o paso
                }
            }
            pw.close();
        } catch (Exception e) {
            System.err.println("Error al guardar progreso: " + e.getMessage());
        }
    }

    /**
     * Carga los estados guardados sobre el rosco ya inicializado.
     * Debe llamarse justo después del constructor.
     */
    public void cargarProgreso() {
        try {
            String ruta = ".\\Data\\Progreso_" + jugadorActual.getUsername() + ".txt"; 
            Scanner sc = new Scanner(new File(ruta)); //Busca la ruta del progreso del usuario
            while (sc.hasNextLine()) {
                String   linea  = sc.nextLine().trim();
                String[] partes = linea.split(";", 2);
                if (partes.length < 2) continue; // Se asegura que no haya nada escrito imprevisto

                switch (partes[0]) { // la flecha "->" sirve para tenerlo mas organizado y no tener que usar el "break;"
                    case "ruta"     -> { /* ya cargada en constructor */ }
                    case "indice"   -> rosco.setIndiceActual(Integer.parseInt(partes[1]));
                    case "aciertos" -> this.aciertos = Integer.parseInt(partes[1]);
                    case "fallos"   -> this.fallos   = Integer.parseInt(partes[1]);
                    default -> { // línea de letra como: A;ACERTADA
                        if (partes[0].length() == 1) { //SE asegura que solo sea un caracter
                            char              letra  = partes[0].charAt(0);
                            EstadoPreguntas   estado = EstadoPreguntas.valueOf(partes[1]);
                            for (Preguntas p : rosco.getPreguntas()) {
                                if (p != null && p.getLetra() == letra) {
                                    p.setEstado(estado); //va metiendo los estados de las preguuntas ya contestadas
                                    break;
                                }
                            }
                        }
                    }
                }
            }
            sc.close();
        } catch (Exception e) {
            System.err.println("Error al cargar progreso: " + e.getMessage());
        }
    }

    /** Devuelve true si existe un fichero de progreso para ese usuario. */
    public static boolean hayProgresoGuardado(String username) {
        return new File(".\\Data\\Progreso_" + username + ".txt").exists();
    }

    /** Lee la ruta del banco de preguntas almacenada en el fichero de progreso. */
    public static String getRutaGuardada(String username) {
        try {
            Scanner sc = new Scanner(new File(".\\Data\\Progreso_" + username + ".txt"));
            while (sc.hasNextLine()) {
                String[] partes = sc.nextLine().split(";", 2);
                if (partes[0].equals("ruta")) { sc.close(); return partes[1]; }
            }
            sc.close();
        } catch (Exception e) { /* ignorar */ }
        return null;
    }

    /** Elimina el fichero de progreso (cuando la partida termina o el jugador descarta). */
    public static void eliminarProgreso(String username) {
        new File(".\\Data\\Progreso_" + username + ".txt").delete();
    }

    // ── Lógica de juego ──────────────────────────────────────────────────────

    public String procesarTurno(Jugador jugador, String respuesta) {
        Preguntas p = rosco.getSiguientePregunta();
        if (p == null) return "No quedan preguntas.";

        if (respuesta.equalsIgnoreCase("pasapalabra")) {
            p.setEstado(EstadoPreguntas.PASADA);
            rosco.avanzarIndice();
            return "Pasapalabra.";
        } else if (p.comprobar(respuesta)) {
            p.setEstado(EstadoPreguntas.ACERTADA);
            this.aciertos++;
            rosco.avanzarIndice();
            return "¡Respuesta correcta!";
        } else {
            p.setEstado(EstadoPreguntas.FALLADA);
            this.fallos++;
            rosco.avanzarIndice();
            return "¡Respuesta incorrecta!";
        }
    }

    @Override
    public boolean isPartidaTerminada() {
        for (Preguntas p : rosco.getPreguntas()) {
            if (p != null &&
               (p.getEstado() == EstadoPreguntas.PENDIENTE ||
                p.getEstado() == EstadoPreguntas.PASADA)) return false;
        }
        return true;
    }

    @Override public Jugador getGanador()             { return jugadorActual; }
    @Override public String  getEstadoVisible()       { return "Aciertos: " + aciertos + " | Fallos: " + fallos; }
    @Override public int     getPuntuacion(Jugador j) { return aciertos; }

    public Rosco getRosco()  { return rosco; }
    public int   getFallos() { return fallos; }

    // empezarJuego() (modo consola) — sin cambios relevantes
    public void empezarJuego() { /* igual que antes */ }
}