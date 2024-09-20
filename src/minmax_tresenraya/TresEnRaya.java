package minmax_tresenraya;


import javax.swing.*;
import java.awt.event.*;
import java.awt.Font;

public class TresEnRaya extends JFrame implements ActionListener {

    /*
    arreglo de 3x3 para los botones del tablero (representación gráfica)
     */
    private JButton[][] botonesTablero = new JButton[3][3];
    /*
    arreglo de 3x3 para almacenar el estado del tablero
     */
    private int[][] tablero = new int[3][3];

    // Constantes para representar a los jugadores
    private static final int JUGADOR = -1;         // Representa al jugador ("X")
    private static final int COMPUTADORA = 1;      // Representa a la computadora ("O")
    private static final int VACIO = 0;            // Representa una casilla vacía en el tablero

    // Constructor que inicializa la ventana del juego
    public TresEnRaya() {
        setTitle("Tres en Raya");
        setSize(480, 505);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setLocationRelativeTo(null);

        // Inicializamos los botones del tablero y los colocamos en la ventana (mostrar tablero)
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                botonesTablero[i][j] = new JButton("");  // Cada botón representa una casilla vacía
                botonesTablero[i][j].setFont(new Font("Arial", Font.PLAIN, 24));  // Tamaño de fuente
                botonesTablero[i][j].setBounds(j * 155, i * 155, 155, 155);  // Posición y tamaño
                botonesTablero[i][j].addActionListener(this);  // Añadimos el listener para gestionar clicks
                add(botonesTablero[i][j]);  // Añadimos los botones a la ventana
            }
        }

        // La computadora realiza el primer movimiento automáticamente
        realizarMovimientoComputadora();
    }

    // Método que realiza el movimiento de la computadora usando el algoritmo MINIMAX
    private void realizarMovimientoComputadora() {
        int[] mejorMovimiento = encontrarMejorMovimiento(); // Encuentra la mejor jugada usando MINIMAX
        tablero[mejorMovimiento[0]][mejorMovimiento[1]] = COMPUTADORA; // Realiza la jugada
        botonesTablero[mejorMovimiento[0]][mejorMovimiento[1]].setText("O");  // Muestra la "O" en la casilla
    }

    // Método que utiliza el algoritmo MINIMAX para encontrar la mejor jugada posible para la computadora
    private int[] encontrarMejorMovimiento() {
        int mejorPuntaje = Integer.MIN_VALUE;               // Inicializamos el mejor puntaje muy bajo
        int[] mejorMovimiento = new int[]{-1, -1};          // Inicializamos el mejor movimiento como no válido

        // Recorremos todas las casillas del tablero
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (tablero[i][j] == VACIO) {               // Si la casilla está vacía
                    tablero[i][j] = COMPUTADORA;            // Simulamos el movimiento de la computadora
                    int puntaje = minimax(tablero, 0, false);  // Calculamos el puntaje del movimiento
                    tablero[i][j] = VACIO;                 // Restauramos la casilla a vacía

                    // Si el puntaje de este movimiento es mejor que el anterior, lo guardamos
                    if (puntaje > mejorPuntaje) {
                        mejorPuntaje = puntaje;
                        mejorMovimiento[0] = i;
                        mejorMovimiento[1] = j;
                    }
                }
            }
        }
        return mejorMovimiento;  // Devolvemos el mejor movimiento encontrado
    }

    // Algoritmo MINIMAX para evaluar todas las posibles jugadas
    private int minimax(int[][] tablero, int profundidad, boolean esMaximizando) {
        int puntaje = evaluarTablero(tablero);  // Evaluamos si alguien ha ganado

        // Si la computadora gana, devolvemos un puntaje alto, si pierde uno bajo
        if (puntaje == 10 || puntaje == -10) {
            return puntaje;
        }

        // Si no hay más movimientos disponibles, es un empate
        if (!hayMovimientosDisponibles(tablero)) {
            return 0;
        }

        // Si es el turno de la computadora (maximizador)
        if (esMaximizando) {
            int mejorPuntaje = Integer.MIN_VALUE;  // Buscamos maximizar el puntaje
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (tablero[i][j] == VACIO) {  // Simulamos jugada
                        tablero[i][j] = COMPUTADORA;
                        mejorPuntaje = Math.max(mejorPuntaje, minimax(tablero, profundidad + 1, false));
                        tablero[i][j] = VACIO;  // Restauramos el tablero
                    }
                }
            }
            return mejorPuntaje;  // Devolvemos el mejor puntaje encontrado
        } else {  // Si es el turno del jugador (minimizador)
            int mejorPuntaje = Integer.MAX_VALUE;  // Buscamos minimizar el puntaje
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (tablero[i][j] == VACIO) {  // Simulamos jugada
                        tablero[i][j] = JUGADOR;
                        mejorPuntaje = Math.min(mejorPuntaje, minimax(tablero, profundidad + 1, true));
                        tablero[i][j] = VACIO;  // Restauramos el tablero
                    }
                }
            }
            return mejorPuntaje;  // Devolvemos el mejor puntaje encontrado
        }
    }

    // Método para verificar si hay movimientos disponibles
    private boolean hayMovimientosDisponibles(int[][] tablero) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (tablero[i][j] == VACIO) {  // Si hay una casilla vacía, aún hay movimientos posibles
                    return true;
                }
            }
        }
        return false;  // No hay más movimientos disponibles
    }

    // Método para evaluar el estado del tablero y determinar el puntaje del juego
    private int evaluarTablero(int[][] tablero) {
        // Verificamos filas, columnas y diagonales
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
        return 0;  // Si no hay ganador, el puntaje es 0
    }

    // Método que verifica si el juego ha terminado
    private boolean esJuegoTerminado() {
        return evaluarTablero(tablero) != 0 || !hayMovimientosDisponibles(tablero);
    }

    // Método que verifica el estado del juego para mostrar mensajes al jugador
    private void verificarEstadoJuego() {
        int puntaje = evaluarTablero(tablero);
        if (puntaje == 10) {
            JOptionPane.showMessageDialog(this, "¡La computadora ha ganado!");  // La computadora ganó
            System.exit(0);  // Cerrar el juego
        } else if (puntaje == -10) {
            JOptionPane.showMessageDialog(this, "¡Has ganado!");  // El jugador ganó
            System.exit(0);  // Cerrar el juego
        } else if (!hayMovimientosDisponibles(tablero)) {
            JOptionPane.showMessageDialog(this, "¡Empate!");  // No hay movimientos disponibles, es empate
            System.exit(0);  // Cerrar el juego
        }
    }

    // Método que maneja los eventos de los botones (cuando el jugador hace clic en ellos)
    public void actionPerformed(ActionEvent e) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (e.getSource() == botonesTablero[i][j] && tablero[i][j] == VACIO) {
                    tablero[i][j] = JUGADOR;                // Marcamos la jugada del jugador
                    botonesTablero[i][j].setText("X");      // Mostramos la "X" en el botón
                    if (!esJuegoTerminado()) {
                        realizarMovimientoComputadora();    // Si el juego no ha terminado, la computadora juega
                    }
                    verificarEstadoJuego();                // Verificamos si el juego ha terminado
                }
            }
        }
    }

    // Método principal para lanzar el juego
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new TresEnRaya().setVisible(true);  // Crear y mostrar la ventana del juego
        });
    }
}