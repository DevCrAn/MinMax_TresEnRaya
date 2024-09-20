package minmax_tresenraya;

import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener; 

public class logicaHvsH implements ActionListener {

    private javax.swing.JButton btnRestaurar;
    private javax.swing.JButton btnSalir;
    private javax.swing.JButton txtbtn1;
    private javax.swing.JButton txtbtn2;
    private javax.swing.JButton txtbtn3;
    private javax.swing.JButton txtbtn4;
    private javax.swing.JButton txtbtn5;
    private javax.swing.JButton txtbtn6;
    private javax.swing.JButton txtbtn7;
    private javax.swing.JButton txtbtn8;
    private javax.swing.JButton txtbtn9;
    private viewHvsH ventanaHH = new  viewHvsH();

    private String turno = "X";  // El primer turno comenzara con X
    private boolean juegoTerminado = false;

    public logicaHvsH(viewHvsH ventanaHH) {
    this.ventanaHH = ventanaHH;  // Usa la ventana que ya existe
    this.btnRestaurar = ventanaHH.btnRestaurar;
    this.btnSalir = ventanaHH.btnSalir;
    this.txtbtn1 = ventanaHH.txtbtn1;
    this.txtbtn2 = ventanaHH.txtbtn2;
    this.txtbtn3 = ventanaHH.txtbtn3;
    this.txtbtn4 = ventanaHH.txtbtn4;
    this.txtbtn5 = ventanaHH.txtbtn5;
    this.txtbtn6 = ventanaHH.txtbtn6;
    this.txtbtn7 = ventanaHH.txtbtn7;
    this.txtbtn8 = ventanaHH.txtbtn8;
    this.txtbtn9 = ventanaHH.txtbtn9;

    // Asociar los botones a ActionListener
    txtbtn1.addActionListener(this);
    txtbtn2.addActionListener(this);
    txtbtn3.addActionListener(this);
    txtbtn4.addActionListener(this);
    txtbtn5.addActionListener(this);
    txtbtn6.addActionListener(this);
    txtbtn7.addActionListener(this);
    txtbtn8.addActionListener(this);
    txtbtn9.addActionListener(this);
    btnRestaurar.addActionListener(this);
    btnSalir.addActionListener(this);
}


    private void cambiarTurno() {
        turno = turno.equals("X") ? "O" : "X";  // Alternar entre X y O
    }

    private void verificarEstado() {
        // Comprobar si hay un ganador o si es empate
        if (hayGanador()) {
            juegoTerminado = true;
            JOptionPane.showMessageDialog(ventanaHH, "¡El jugador " + (turno.equals("X") ? "O" : "X") + " ha ganado!");
        } else if (esEmpate()) {
            juegoTerminado = true;
            JOptionPane.showMessageDialog(ventanaHH, "¡Es un empate!");
        }
    }

    private boolean hayGanador() {
        // Combinaciones ganadoras con los botones correspondientes
        JButton[][] combinaciones = {
            {txtbtn1, txtbtn2, txtbtn3}, // Primera fila
            {txtbtn4, txtbtn5, txtbtn6}, // Segunda fila
            {txtbtn7, txtbtn8, txtbtn9}, // Tercera fila
            {txtbtn1, txtbtn4, txtbtn7}, // Primera columna
            {txtbtn2, txtbtn5, txtbtn8}, // Segunda columna
            {txtbtn3, txtbtn6, txtbtn9}, // Tercera columna
            {txtbtn1, txtbtn5, txtbtn9}, // Diagonal principal
            {txtbtn3, txtbtn5, txtbtn7} // Diagonal inversa
        };

        for (JButton[] combinacion : combinaciones) {
            if (!combinacion[0].getText().equals("")
                    && combinacion[0].getText().equals(combinacion[1].getText())
                    && combinacion[1].getText().equals(combinacion[2].getText())) {
                // Pintar los botones ganadores
                for (JButton boton : combinacion) {
                    boton.setBackground(Color.GREEN);  // Cambiar color a verde para los botones ganadores
                }
                return true;
            }
        }
        return false;
    }

    private boolean esEmpate() {
        // Verificar si todas las casillas están llenas y no hay un ganador
        return !txtbtn1.getText().equals("") && !txtbtn2.getText().equals("") && !txtbtn3.getText().equals("")
                && !txtbtn4.getText().equals("") && !txtbtn5.getText().equals("") && !txtbtn6.getText().equals("")
                && !txtbtn7.getText().equals("") && !txtbtn8.getText().equals("") && !txtbtn9.getText().equals("");
    }

    private void reiniciarJuego() {
        // Reiniciar todas las casillas y el estado del juego
        txtbtn1.setText("");
        txtbtn2.setText("");
        txtbtn3.setText("");
        txtbtn4.setText("");
        txtbtn5.setText("");
        txtbtn6.setText("");
        txtbtn7.setText("");
        txtbtn8.setText("");
        txtbtn9.setText("");

        // Restaurar el color original de los botones
        txtbtn1.setBackground(null);
        txtbtn2.setBackground(null);
        txtbtn3.setBackground(null);
        txtbtn4.setBackground(null);
        txtbtn5.setBackground(null);
        txtbtn6.setBackground(null);
        txtbtn7.setBackground(null);
        txtbtn8.setBackground(null);
        txtbtn9.setBackground(null);

        turno = "X";  // El juego comienza con X
        juegoTerminado = false;  // Reiniciar el estado del juego
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton botonPresionado = (JButton) e.getSource();

        // cerrar el juego
        if (botonPresionado == btnSalir) {
            System.exit(0);
        }

        // reiniciar el juego
        if (botonPresionado == btnRestaurar) {
            reiniciarJuego();
            return;
        }

        // Si ya terminó el juego, no hacer nada (unica forma(btnrestaurar))
        if (juegoTerminado) {
            return;
        }

        if (botonPresionado.getText().equals("")) { //si la casilla está vacía entra
            botonPresionado.setText(turno);  // Asignar el turno actual a la casilla X o O
            cambiarTurno();  // Alternar el turno
            verificarEstado();  // Comprobar si alguien ha ganado o si es empate
        }
    }
}
