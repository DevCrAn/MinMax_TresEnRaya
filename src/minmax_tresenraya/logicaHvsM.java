package minmax_tresenraya;

public class logicaHvsM {

    private int[][] tablero = new int[3][3];
    private static final int JUGADOR = -1;         // Representa al jugador ("X")
    private static final int COMPUTADORA = 1;      // Representa a la computadora ("O")
    private static final int VACIO = 0;            // Representa una casilla vacía en el tablero

    // Método que realiza el movimiento de la computadora usando el algoritmo MINIMAX
    public int[] realizarMovimientoComputadora() {
        return encontrarMejorMovimiento(); // Encuentra la mejor jugada usando MINIMAX
    }

    // Método que utiliza el algoritmo MINIMAX para encontrar la mejor jugada posible para la computadora
    private int[] encontrarMejorMovimiento() {
        int mejorPuntaje = Integer.MIN_VALUE;
        int[] mejorMovimiento = new int[]{-1, -1};

        // Recorremos todas las casillas del tablero
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (tablero[i][j] == VACIO) {
                    tablero[i][j] = COMPUTADORA;
                    int puntaje = minimax(tablero, 0, false);
                    tablero[i][j] = VACIO;

                    if (puntaje > mejorPuntaje) {
                        mejorPuntaje = puntaje;
                        mejorMovimiento[0] = i;
                        mejorMovimiento[1] = j;
                    }
                }
            }
        }
        tablero[mejorMovimiento[0]][mejorMovimiento[1]] = COMPUTADORA;  // Actualizamos el tablero
        return mejorMovimiento;
    }

    // Algoritmo MINIMAX
    private int minimax(int[][] tablero, int profundidad, boolean esMaximizando) {
        int puntaje = evaluarTablero(tablero);

        if (puntaje == 10 || puntaje == -10) {
            return puntaje;
        }

        if (!hayMovimientosDisponibles(tablero)) {
            return 0;
        }

        if (esMaximizando) {
            int mejorPuntaje = Integer.MIN_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (tablero[i][j] == VACIO) {
                        tablero[i][j] = COMPUTADORA;
                        mejorPuntaje = Math.max(mejorPuntaje, minimax(tablero, profundidad + 1, false));
                        tablero[i][j] = VACIO;
                    }
                }
            }
            return mejorPuntaje;
        } else {
            int mejorPuntaje = Integer.MAX_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (tablero[i][j] == VACIO) {
                        tablero[i][j] = JUGADOR;
                        mejorPuntaje = Math.min(mejorPuntaje, minimax(tablero, profundidad + 1, true));
                        tablero[i][j] = VACIO;
                    }
                }
            }
            return mejorPuntaje;
        }
    }

    // Evaluar el tablero
    public int evaluarTablero(int[][] tablero) {
        for (int i = 0; i < 3; i++) {
            if (tablero[i][0] == tablero[i][1] && tablero[i][1] == tablero[i][2]) {
                if (tablero[i][0] == COMPUTADORA) {
                    return 10;
                } else if (tablero[i][0] == JUGADOR) {
                    return -10;
                }
            }
            if (tablero[0][i] == tablero[1][i] && tablero[1][i] == tablero[2][i]) {
                if (tablero[0][i] == COMPUTADORA) {
                    return 10;
                } else if (tablero[0][i] == JUGADOR) {
                    return -10;
                }
            }
        }
        if (tablero[0][0] == tablero[1][1] && tablero[1][1] == tablero[2][2]) {
            if (tablero[0][0] == COMPUTADORA) {
                return 10;
            } else if (tablero[0][0] == JUGADOR) {
                return -10;
            }
        }
        if (tablero[0][2] == tablero[1][1] && tablero[1][1] == tablero[2][0]) {
            if (tablero[0][2] == COMPUTADORA) {
                return 10;
            } else if (tablero[0][2] == JUGADOR) {
                return -10;
            }
        }
        return 0;
    }

    // Verificar si hay movimientos disponibles
    public boolean hayMovimientosDisponibles(int[][] tablero) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (tablero[i][j] == VACIO) {
                    return true;
                }
            }
        }
        return false;
    }

    // Actualizar el tablero con el movimiento del jugador
    public void actualizarTablero(int fila, int columna, int jugador) {
        tablero[fila][columna] = jugador;
    }

    public int[][] getTablero() {
        return tablero;
    }
}
