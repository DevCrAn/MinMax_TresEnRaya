package minmax_tresenraya;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JOptionPane;

/**
 *
 * @author DevCrAn
 */
public class viewHvsM extends javax.swing.JFrame implements ActionListener {

    private logicaHvsM logica = new logicaHvsM();
    private JButton[][] botonesTablero = new JButton[3][3];
    private int[][] tablero = new int[3][3];

    private static final int JUGADOR = -1; // Representa al jugador ("X")
    private static final int COMPUTADORA = 1; // Representa a la computadora ("O")
    private static final int VACIO = 0; // Representa una casilla vacía en el tablero

    // Variable para saber quién inició el juego
    private boolean computadoraInicia = false;

    public viewHvsM(boolean computadoraInicia) {
        initComponents();
        this.computadoraInicia = computadoraInicia;
        inicializarBotones();

        // Si la computadora fue seleccionada para iniciar, realiza su primer movimiento
        if (computadoraInicia) {
            realizarMovimientoComputadora();
        }
    }

    private void inicializarBotones() {
        botonesTablero[0][0] = btn00;
        botonesTablero[0][1] = btn01;
        botonesTablero[0][2] = btn02;
        botonesTablero[1][0] = btn10;
        botonesTablero[1][1] = btn11;
        botonesTablero[1][2] = btn12;
        botonesTablero[2][0] = btn20;
        botonesTablero[2][1] = btn21;
        botonesTablero[2][2] = btn22;

        //Asigna eventos de click a cada botón
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                botonesTablero[i][j].setFont(new Font("Arial", Font.PLAIN, 24));
                botonesTablero[i][j].addActionListener((ActionListener) this); // Añadir listener para gestionar clicks
            }
        }
    }

    public void realizarMovimientoComputadora() {
        int[] mejorMovimiento = encontrarMejorMovimiento(); // Encuentra la mejor jugada usando MINIMAX
        tablero[mejorMovimiento[0]][mejorMovimiento[1]] = COMPUTADORA; // Realiza la jugada
        botonesTablero[mejorMovimiento[0]][mejorMovimiento[1]].setText("O"); // Muestra la "O" en la casilla
    }

    // Método que utiliza el algoritmo MINIMAX para encontrar la mejor jugada posible para la computadora
    private int[] encontrarMejorMovimiento() {
        int mejorPuntaje = Integer.MIN_VALUE; // Inicializamos el mejor puntaje muy bajo
        int[] mejorMovimiento = new int[]{-1, -1}; // Inicializamos el mejor movimiento como no válido

        // Recorremos todas las casillas del tablero
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (tablero[i][j] == VACIO) { // Si la casilla está vacía
                    tablero[i][j] = COMPUTADORA;
                    int puntaje = minimax(tablero, 0, false); // Calculamos el puntaje del movimiento
                    tablero[i][j] = VACIO; // Restauramos la casilla a vacía

                    // Si el puntaje de este movimiento es mejor que el anterior, lo guardamos
                    if (puntaje > mejorPuntaje) {
                        mejorPuntaje = puntaje;
                        mejorMovimiento[0] = i;
                        mejorMovimiento[1] = j;
                    }
                }
            }
        }
        return mejorMovimiento; // Devolvemos el mejor movimiento encontrado
    }

    // Algoritmo MINIMAX para evaluar todas las posibles jugadas
    private int minimax(int[][] tablero, int profundidad, boolean esMaximizando) {
        int puntaje = evaluarTablero(tablero); // Evaluamos si alguien ha ganado

        //Si la computadora gana, devolvemos un puntaje alto, si pierde uno bajo
        if (puntaje == 10 || puntaje == -10) {
            return puntaje;
        }

        //empate es un empate
        if (!hayMovimientosDisponibles(tablero)) {
            return 0;
        }

        // Si es el turno de la computadora (maximizador)
        if (esMaximizando) {
            int mejorPuntaje = Integer.MIN_VALUE; // Buscamos maximizar el puntaje
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (tablero[i][j] == VACIO) { // Simulamos jugada
                        tablero[i][j] = COMPUTADORA;
                        mejorPuntaje = Math.max(mejorPuntaje, minimax(tablero, profundidad + 1, false));
                        tablero[i][j] = VACIO; // Restauramos el tablero
                    }
                }
            }
            return mejorPuntaje; // Devolvemos el mejor puntaje encontrado
        } else { // Si es el turno del jugador (minimizador)
            int mejorPuntaje = Integer.MAX_VALUE; // Buscamos minimizar el puntaje
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (tablero[i][j] == VACIO) { // Simulamos jugada
                        tablero[i][j] = JUGADOR;
                        mejorPuntaje = Math.min(mejorPuntaje, minimax(tablero, profundidad + 1, true));
                        tablero[i][j] = VACIO; // Restauramos el tablero
                    }
                }
            }
            return mejorPuntaje; // Devolvemos el mejor puntaje encontrado
        }
    }

    // Método para verificar si hay movimientos disponibles
    private boolean hayMovimientosDisponibles(int[][] tablero) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (tablero[i][j] == VACIO) { // Si hay una casilla vacía, aún hay movimientos posibles
                    return true;
                }
            }
        }
        return false; // No hay más movimientos disponibles
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
        return 0; // Si no hay ganador, el puntaje es 0
    }

    // Método que verifica si el juego ha terminado
    private boolean esJuegoTerminado() {
        return evaluarTablero(tablero) != 0 || !hayMovimientosDisponibles(tablero);
    }

    // Método que verifica el estado del juego para mostrar mensajes al jugador
    private void verificarEstadoJuego() {
        int puntaje = evaluarTablero(tablero);
        if (puntaje == 10) {
            JOptionPane.showMessageDialog(this, "¡La computadora ha ganado!");
            reiniciarJuego();
        } else if (puntaje == -10) {
            JOptionPane.showMessageDialog(this, "¡Has ganado!");
            reiniciarJuego();
        } else if (!hayMovimientosDisponibles(tablero)) {
            JOptionPane.showMessageDialog(this, "¡Es un empate!");
            reiniciarJuego();
        }
    }

    // Método para reiniciar el tablero y los botones
    private void reiniciarJuego() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                tablero[i][j] = VACIO;
                botonesTablero[i][j].setText("");
            }
        }

        // Si la computadora comenzó en la partida anterior, vuelve a hacer su movimiento inicial
        if (computadoraInicia) {
            realizarMovimientoComputadora();
        }
    }

    public void actionPerformed(ActionEvent e) {
        // Recorremos todos los botones para encontrar cuál fue clicado
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (e.getSource() == botonesTablero[i][j]) {
                    if (tablero[i][j] == VACIO) { // Solo permite hacer la jugada si la casilla está vacía
                        tablero[i][j] = JUGADOR;
                        botonesTablero[i][j].setText("X"); // Marca la casilla con una "X"
                        verificarEstadoJuego(); // Verifica si el juego ha terminado
                        if (!esJuegoTerminado()) { // Si el juego no ha terminado, realiza el movimiento de la computadora
                            realizarMovimientoComputadora();
                            verificarEstadoJuego(); // Verifica si el juego ha terminado después del movimiento
                        }
                    }
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        returnMenu = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        btn02 = new javax.swing.JButton();
        btn00 = new javax.swing.JButton();
        btn01 = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        btn12 = new javax.swing.JButton();
        btn10 = new javax.swing.JButton();
        btn11 = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        btn22 = new javax.swing.JButton();
        btn20 = new javax.swing.JButton();
        btn21 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Tres en Raya HvsM");
        setResizable(false);

        jLabel1.setFont(new java.awt.Font("Serif", 1, 14)); // NOI18N
        jLabel1.setText("Humano vs Máquina");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(53, 53, 53)
                .addComponent(jLabel1)
                .addContainerGap(51, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        returnMenu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/back.png"))); // NOI18N
        returnMenu.setText("Menú");
        returnMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                returnMenuActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(returnMenu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(returnMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(btn00, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btn01, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btn02, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(31, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn02, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn01, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn00, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(btn10, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btn11, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btn12, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(18, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn10, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                    .addComponent(btn12, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                    .addComponent(btn11, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(btn20, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btn21, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btn22, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn20, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                    .addComponent(btn22, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                    .addComponent(btn21, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(27, 27, 27)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(47, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void returnMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_returnMenuActionPerformed
        gameMode windowgameMode = new gameMode();

        windowgameMode.setVisible(true);
        windowgameMode.setLocationRelativeTo(null);
        this.dispose();
    }//GEN-LAST:event_returnMenuActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn00;
    private javax.swing.JButton btn01;
    private javax.swing.JButton btn02;
    private javax.swing.JButton btn10;
    private javax.swing.JButton btn11;
    private javax.swing.JButton btn12;
    private javax.swing.JButton btn20;
    private javax.swing.JButton btn21;
    private javax.swing.JButton btn22;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JButton returnMenu;
    // End of variables declaration//GEN-END:variables
}
