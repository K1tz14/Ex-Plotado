
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/* * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license 
* Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template */ /**
 * * * @author Kitzia
 */

public class Juego extends javax.swing.JFrame implements Runnable {

    /**
     * * Creates new form Juego
     */
    // variables de control del sistema
    boolean enPausa = false;
    boolean enEjecucion = false;
    boolean cambio = false;
    Thread juego;

    // variables de estado de objetos
    boolean cafeActivo = true;
    boolean gatoActivo = true;

    // variables de alertas de computadoras
    boolean alerta1Activa = false, alerta2Activa = false, alerta3Activa = false;
    boolean alerta4Activa = false, alerta5Activa = false, alerta6Activa = false;
    boolean alerta7Activa = false, alerta8Activa = false, alerta9Activa = false;

    // variables de impresoras
    boolean impresora1Activa = false;
    boolean impresora2Activa = false;
    boolean impresora3Activa = false;

    // variables de estadisticas y progreso
    int dia = 1;
    int puntuacionTotal = 0;
    int puntuacion = 0;
    int energia = 100;
    int estres = 0;

    // variables de tiempo
    int minutos = 3;
    int segundos = 0;

    // variables de movimiento y contadores
    int posX, posY, direccion;
    int contadorCafe = 0;
    int contadorGato = 0;

    public Juego() {
        initComponents();
        setLocationRelativeTo(null);
        setFocusable(true);
        minutos = 3;
        segundos = 0;
        LMinutos.setText("03");
        LSegundos.setText("00");
        enEjecucion = true;
        juego = new Thread(this);
        juego.start();
        posX = 420;
        posY = 70;
        Jugador.setLocation(posX, posY);
        Alerta1.setVisible(false);
        Alerta2.setVisible(false);
        Alerta3.setVisible(false);
        Alerta4.setVisible(false);
        Alerta5.setVisible(false);
        Alerta6.setVisible(false);
        Alerta7.setVisible(false);
        Alerta8.setVisible(false);
        Alerta9.setVisible(false);
        energia = 100;
        estres = 0;

        PBEnergia.setValue(energia);
        PBEstres.setValue(estres);

    }

    public void run() {

        int contadorTiempo = 0;
        int contadorEventos = 0;
        int contadorImpresora = 0;
        int contadorEnergia = 0;
        int contadorEstres = 0;

        while (enEjecucion) {

            if (!enPausa) {

                // movimiento del jugador
                dirigir();

                // revision de eventos activos
                revisarEventos();

                // incrementa contadores de control
                contadorTiempo++;
                contadorEventos++;
                contadorImpresora++;
                contadorEnergia++;
                contadorEstres++;

                //parte del cronometro
                if (contadorTiempo >= 25) {
                    contadorTiempo = 0;
                    segundos--;

                    if (segundos < 0) {
                        segundos = 59;
                        minutos--;
                    }

                    LMinutos.setText(String.format("%02d", minutos));
                    LSegundos.setText(String.format("%02d", segundos));

                    if (minutos == 0 && segundos == 0) {
                        puntuacionTotal += puntuacion;
                        JOptionPane.showMessageDialog(null, "Terminó el día " + dia + "\n Puntuación del día: " + puntuacion);

                        if (dia < 3) {
                            dia++;
                            reiniciarDia();

                        } else {
                            finalizarJuego();
                            enEjecucion = false;
                        }
                    }
                }

                // generacion de eventos randoms
                if (contadorEventos >= 40) {
                    activarEvento();
                    contadorEventos = 0;
                }

                // activacion de impresoras
                if (contadorImpresora >= 400) {
                    activarImpresora();
                    contadorImpresora = 0;
                }

                // sistema de energia
                if (contadorEnergia >= 25) {

                    contadorEnergia = 0;
                    energia--;

                    if (energia < 0) {
                        energia = 0;
                        enEjecucion = false;

                        JOptionPane.showMessageDialog(null, "te sientes muy cansado y caes desmayado");
                        System.exit(0);
                    }

                    PBEnergia.setValue(energia);
                }

                // sistema de estres
                if (contadorEstres >= 25) {

                    contadorEstres = 0;
                    estres++;

                    if (estres > 100) {
                        estres = 100;
                        enEjecucion = false;

                        JOptionPane.showMessageDialog(null, "el estrés es demasiado alto, colapsas" );
                        System.exit(0);
                    }

                    PBEstres.setValue(estres);
                }
            }

            dormirHilo(40);
        }
    }

    public void reiniciarDia() {
        
        // reiniciar tiempo
        minutos = 3;
        segundos = 0;

        LMinutos.setText("03");
        LSegundos.setText("00");

        // reiniciar la posicion del juagdor
        posX = 420;
        posY = 70;

        Jugador.setLocation(posX, posY);

        // reiniciar los puntos obtenidos del dia anterior
        puntuacion = 0;

        // reiniciar energia y estres
        energia = 100;
        estres = 0;

        PBEnergia.setValue(energia);
        PBEstres.setValue(estres);

        // ocultar alertas
        Alerta1.setVisible(false);
        Alerta2.setVisible(false);
        Alerta3.setVisible(false);
        Alerta4.setVisible(false);
        Alerta5.setVisible(false);
        Alerta6.setVisible(false);
        Alerta7.setVisible(false);
        Alerta8.setVisible(false);
        Alerta9.setVisible(false);

        alerta1Activa = false;
        alerta2Activa = false;
        alerta3Activa = false;
        alerta4Activa = false;
        alerta5Activa = false;
        alerta6Activa = false;
        alerta7Activa = false;
        alerta8Activa = false;
        alerta9Activa = false;

        // reiniciar impresoras
        impresora1Activa = false;
        impresora2Activa = false;
        impresora3Activa = false;

        Impresora.setIcon( new javax.swing.ImageIcon(getClass().getResource("/img/ImpresoraNormal.png")));

        Impresora2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/ImpresoraNormal.png")));

        Impresora3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/ImpresoraNormal.png")));

        // reaparecer cafe y gato
        cafeActivo = true;
        gatoActivo = true;
        Cafe.setVisible(true);
        Mochi.setVisible(true);

        JOptionPane.showMessageDialog(null,"Comienza el día " + dia);
    }

    public void finalizarJuego() {

        if (puntuacionTotal >= 700) {
            JOptionPane.showMessageDialog(null,"Puntuación final: " + puntuacionTotal+ "\n\nTu esfuerzo fue excelente."+ "\nLa empresa decidió ascenderte a jefe del departamento.");
            System.exit(0);

        } else if (puntuacionTotal > 300) {
            JOptionPane.showMessageDialog(null,"Puntuación final: " + puntuacionTotal+ "\n\nTu rendimiento fue regular."+ "\nSeguirás trabajando en el mismo puesto.");
            System.exit(0);

        } else {
            JOptionPane.showMessageDialog(null,"Puntuación final: " + puntuacionTotal+ "\n\nTu rendimiento fue muy bajo."+ "\nLa empresa decidió despedirte.");
            System.exit(0);
        }
    }

    public void activarEvento() {

        int random = (int) (Math.random() * 9) + 1;

        switch (random) {

            case 1:
                alerta1Activa = true;
                Alerta1.setVisible(true);
                break;

            case 2:
                alerta2Activa = true;
                Alerta2.setVisible(true);
                break;

            case 3:
                alerta3Activa = true;
                Alerta3.setVisible(true);
                break;
            case 4:
                alerta4Activa = true;
                Alerta4.setVisible(true);
                break;

            case 5:
                alerta5Activa = true;
                Alerta5.setVisible(true);
                break;

            case 6:
                alerta6Activa = true;
                Alerta6.setVisible(true);
                break;

            case 7:
                alerta7Activa = true;
                Alerta7.setVisible(true);
                break;
            case 8:
                alerta8Activa = true;
                Alerta8.setVisible(true);
                break;

            case 9:
                alerta9Activa = true;
                Alerta9.setVisible(true);
                break;

        }
    }

    public void activarImpresora() {

        int random = (int) (Math.random() * 3) + 1;

        switch (random) {

            case 1:
                if (!impresora1Activa) {
                    impresora1Activa = true;
                    Impresora.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/ImpresoraFuego.gif")));
                }
                break;

            case 2:
                if (!impresora2Activa) {
                    impresora2Activa = true;
                    Impresora2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/ImpresoraFuego.gif")));
                }
                break;

            case 3:
                if (!impresora3Activa) {
                    impresora3Activa = true;
                    Impresora3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/ImpresoraFuego.gif")));
                }
                break;
        }
    }

    public void revisarEventos() {
        if (alerta1Activa && posX >= 210 && posX <= 230 && posY >= 90 && posY <= 110) {

            alerta1Activa = false;
            Alerta1.setVisible(false);
            puntuacion += 5;
            System.out.println("Tu puntuacion es de:" + puntuacion);
        }

        if (alerta2Activa && posX >= 190 && posX <= 230 && posY >= 200 && posY <= 220) {

            alerta2Activa = false;
            Alerta2.setVisible(false);
            puntuacion += 5;
            System.out.println("Tu puntuacion es de:" + puntuacion);
        }

        if (alerta3Activa  && posX >= 190 && posX <= 230 && posY >= 300 && posY <= 320) {

            alerta3Activa = false;
            Alerta3.setVisible(false);
            puntuacion += 5;
            System.out.println("Tu puntuacion es de:" + puntuacion);
        }

        if (alerta4Activa && posX >= 430 && posX <= 450 && posY >= 90 && posY <= 110) {

            alerta4Activa = false;
            Alerta4.setVisible(false);
            puntuacion += 5;
            System.out.println("Tu puntuacion es de:" + puntuacion);
        }

        if (alerta5Activa && posX >= 430 && posX <= 450 && posY >= 200 && posY <= 220) {

            alerta5Activa = false;
            Alerta5.setVisible(false);
            puntuacion += 5;
            System.out.println("Tu puntuacion es de:" + puntuacion);
        }

        if (alerta6Activa && posX >= 440 && posX <= 460 && posY >= 300 && posY <= 320) {

            alerta6Activa = false;
            Alerta6.setVisible(false);
            puntuacion += 5;
            System.out.println("Tu puntuacion es de:" + puntuacion);
        }

        if (alerta7Activa && posX >= 660 && posX <= 680 && posY >= 90 && posY <= 110) {

            alerta7Activa = false;
            Alerta7.setVisible(false);
            puntuacion += 5;
            System.out.println("Tu puntuacion es de:" + puntuacion);
        }

        if (alerta8Activa && posX >= 660 && posX <= 680 && posY >= 190 && posY <= 210) {

            alerta8Activa = false;
            Alerta8.setVisible(false);
            puntuacion += 5;
            System.out.println("Tu puntuacion es de:" + puntuacion);
        }

        if (alerta9Activa && posX >= 660 && posX <= 680 && posY >= 300 && posY <= 320) {

            alerta9Activa = false;
            Alerta9.setVisible(false);
            puntuacion += 5;
            System.out.println("Tu puntuacion es de:" + puntuacion);
        }

        //Impresoras 
        if (impresora1Activa && posX >= 130 && posX <= 170 && posY >= 420 && posY <= 440) {

            impresora1Activa = false;
            Impresora.setIcon( new javax.swing.ImageIcon(getClass().getResource("/img/ImpresoraNormal.png")));
            puntuacion += 15;
            System.out.println("Tu puntuacion es de: " + puntuacion);
        }

        if (impresora2Activa && posX >= 550 && posX <= 560 && posY >= 60 && posY <= 130) {

            impresora2Activa = false;
            Impresora2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/ImpresoraNormal.png")));
            puntuacion += 15;
            System.out.println("Tu puntuacion es de: " + puntuacion);
        }

        if (impresora3Activa && posX >= 710 && posX <= 760&& posY >= 420 && posY <= 440) {

            impresora3Activa = false;
            Impresora3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/ImpresoraNormal.png")));
            puntuacion += 15;
            System.out.println("Tu puntuacion es de: " + puntuacion);
        }

        if (cafeActivo && posX >= 850 && posX <= 870 && posY >= 400 && posY <= 420) {

            cafeActivo = false;
            Cafe.setVisible(false);
            energia += 15;
            System.out.println("Tu energía aumento 15 puntos con ese café, tu energía actual es de: " + energia);

            if (energia > 100) {
                energia = 100;
            }
            
            
            PBEnergia.setValue(energia);
            contadorCafe = 0;
        }

// Cafe
        if (!cafeActivo) {

            contadorCafe++;

            if (contadorCafe >= 150) {
                cafeActivo = true;
                Cafe.setVisible(true);
                System.out.println("Una nueva taza de café apareció!");
            }
        }

// Mochi (el gatito)
        if (gatoActivo && posX >= 190 && posX <= 210 && posY >= 40 && posY <= 60) {

            gatoActivo = false;
            Mochi.setVisible(false);
            estres -= 20;
            System.out.println("Acariciar a Mochi hizo que tu estrés disminuyera 20 puntos, tu estrés actual es de: " + estres);

            if (estres < 0) {
                estres = 0;
            }

            PBEstres.setValue(estres);
            contadorGato = 0;
        }

        if (!gatoActivo) {
            contadorGato++;
            if (contadorGato >= 250) {
                gatoActivo = true;
                Mochi.setVisible(true);
                System.out.println("Mochi ha regresado!");
            }
        }
    }

    public boolean hayColision() {
        if (Jugador.getBounds().intersects(Barrera.getBounds())) {
            return true;
        }
        if (Jugador.getBounds().intersects(Barrera3.getBounds())) {
            return true;
        }
        if (Jugador.getBounds().intersects(Barrera4.getBounds())) {
            return true;
        }
        if (Jugador.getBounds().intersects(Escritorio1.getBounds())) {
            return true;
        }
        if (Jugador.getBounds().intersects(Escritorio2.getBounds())) {
            return true;
        }
        if (Jugador.getBounds().intersects(Escritorio3.getBounds())) {
            return true;
        }
        if (Jugador.getBounds().intersects(Escritorio4.getBounds())) {
            return true;
        }
        if (Jugador.getBounds().intersects(Escritorio5.getBounds())) {
            return true;
        }
        if (Jugador.getBounds().intersects(Escritorio6.getBounds())) {
            return true;
        }
        if (Jugador.getBounds().intersects(Escritorio7.getBounds())) {
            return true;
        }
        if (Jugador.getBounds().intersects(Escritorio8.getBounds())) {
            return true;
        }
        if (Jugador.getBounds().intersects(Escritorio9.getBounds())) {
            return true;
        }
        if (Jugador.getBounds().intersects(Mesa.getBounds())) {
            return true;
        }
        if (Jugador.getBounds().intersects(OficinaBloq1.getBounds())) {
            return true;
        }
        if (Jugador.getBounds().intersects(OficinaBloq2.getBounds())) {
            return true;
        }
        if (Jugador.getBounds().intersects(OficinaBloq3.getBounds())) {
            return true;
        }
        if (Jugador.getBounds().intersects(Sala1.getBounds())) {
            return true;
        }
        if (Jugador.getBounds().intersects(Extra1.getBounds())) {
            return true;
        }
        if (Jugador.getBounds().intersects(Extra2.getBounds())) {
            return true;
        }
        if (Jugador.getBounds().intersects(Extra3.getBounds())) {
            return true;
        }
        if (Jugador.getBounds().intersects(Extra4.getBounds())) {
            return true;
        }
        if (Jugador.getBounds().intersects(Extra5.getBounds())) {
            return true;
        }
        if (Jugador.getBounds().intersects(Extra6.getBounds())) {
            return true;
        }
        if (Jugador.getBounds().intersects(Extra7.getBounds())) {
            return true;
        }
        if (Jugador.getBounds().intersects(Extra8.getBounds())) {
            return true;
        }
        if (Jugador.getBounds().intersects(Extra9.getBounds())) {
            return true;
        }
        if (Jugador.getBounds().intersects(Impresora.getBounds())) {
            return true;
        }
        if (Jugador.getBounds().intersects(Impresora3.getBounds())) {
            return true;
        }

        return false;
    }

    public void moverDerecha() {
        if (cambio) {
            Jugador.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/PrincipalDer1.png")));
        } else {
            Jugador.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/PrincipalDer2.png")));
        }
        cambio = !cambio;
        posX += 5;
        Jugador.setLocation(posX, posY);
        if (hayColision()) {
            posX -= 5;
            Jugador.setLocation(posX, posY);
        }
        direccion = 0;
    }

    public void moverIzquierda() {
        if (cambio) {
            Jugador.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/PrincipalIzq1.png")));
        } else {
            Jugador.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/PrincipalIzq2.png")));
        }
        cambio = !cambio;
        posX -= 5;
        Jugador.setLocation(posX, posY);
        if (hayColision()) {
            posX += 5;
            Jugador.setLocation(posX, posY);
        }
        direccion = 0;
    }

    public void moverArriba() {
        if (cambio) {
            Jugador.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/PrincipalAtras1.png")));
        } else {
            Jugador.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/PrincipalAtras2.png")));
        }
        cambio = !cambio;
        posY -= 5;
        Jugador.setLocation(posX, posY);
        if (hayColision()) {
            posY += 5;
            Jugador.setLocation(posX, posY);
        }
        direccion = 0;
    }

    public void moverAbajo() {
        if (cambio) {
            Jugador.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/PrincipalCaminar1.png")));
        } else {
            Jugador.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/PrincipalCaminar2.png")));
        }
        cambio = !cambio;
        posY += 5;
        Jugador.setLocation(posX, posY);
        if (hayColision()) {
            posY -= 5;
            Jugador.setLocation(posX, posY);
        }
        direccion = 0;
    }

    public void dirigir() {
        switch (direccion) {
            case 1:
                moverIzquierda();
                break;
            case 2:
                moverDerecha();
                break;
            case 3:
                moverArriba();
                break;
            case 4:
                moverAbajo();
                break;
        }
    }

    public void dormirHilo(int vel) {
        try {
            Thread.sleep(vel);
        } catch (InterruptedException ex) {
            Logger.getLogger(Juego.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        PBEnergia = new javax.swing.JProgressBar();
        PBEstres = new javax.swing.JProgressBar();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jlabel = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        LMinutos = new javax.swing.JLabel();
        LSegundos = new javax.swing.JLabel();
        Alerta1 = new javax.swing.JLabel();
        Alerta2 = new javax.swing.JLabel();
        Alerta3 = new javax.swing.JLabel();
        Alerta4 = new javax.swing.JLabel();
        Alerta5 = new javax.swing.JLabel();
        Alerta6 = new javax.swing.JLabel();
        Alerta7 = new javax.swing.JLabel();
        Alerta8 = new javax.swing.JLabel();
        Alerta9 = new javax.swing.JLabel();
        Escritorio1 = new javax.swing.JLabel();
        Escritorio2 = new javax.swing.JLabel();
        Escritorio3 = new javax.swing.JLabel();
        Escritorio4 = new javax.swing.JLabel();
        Escritorio5 = new javax.swing.JLabel();
        Escritorio6 = new javax.swing.JLabel();
        Escritorio7 = new javax.swing.JLabel();
        Escritorio8 = new javax.swing.JLabel();
        Escritorio9 = new javax.swing.JLabel();
        Jugador = new javax.swing.JLabel();
        Barrera = new javax.swing.JLabel();
        Barrera3 = new javax.swing.JLabel();
        Barrera4 = new javax.swing.JLabel();
        Barrera7 = new javax.swing.JLabel();
        Impresora = new javax.swing.JLabel();
        OficinaBloq = new javax.swing.JLabel();
        OficinaBloq1 = new javax.swing.JLabel();
        OficinaBloq2 = new javax.swing.JLabel();
        OficinaBloq3 = new javax.swing.JLabel();
        Sala1 = new javax.swing.JLabel();
        Extra1 = new javax.swing.JLabel();
        Extra2 = new javax.swing.JLabel();
        Extra3 = new javax.swing.JLabel();
        Extra4 = new javax.swing.JLabel();
        Extra5 = new javax.swing.JLabel();
        Extra6 = new javax.swing.JLabel();
        Extra7 = new javax.swing.JLabel();
        Extra8 = new javax.swing.JLabel();
        Extra9 = new javax.swing.JLabel();
        Mesa = new javax.swing.JLabel();
        Impresora3 = new javax.swing.JLabel();
        Extra10 = new javax.swing.JLabel();
        Extra11 = new javax.swing.JLabel();
        Impresora2 = new javax.swing.JLabel();
        Mochi = new javax.swing.JLabel();
        Cafe = new javax.swing.JLabel();
        FondoOficina = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(0, 0, 0));
        setMinimumSize(new java.awt.Dimension(1200, 610));
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
        });
        getContentPane().setLayout(null);

        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        PBEnergia.setForeground(new java.awt.Color(0, 204, 0));
        PBEnergia.setOrientation(1);

        PBEstres.setForeground(new java.awt.Color(255, 51, 0));
        PBEstres.setOrientation(1);

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setText("ENERGIA");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setText("ESTRÉS");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(PBEnergia, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(PBEstres, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 22, Short.MAX_VALUE)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 6, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(PBEnergia, javax.swing.GroupLayout.DEFAULT_SIZE, 420, Short.MAX_VALUE)
                    .addComponent(PBEstres, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addContainerGap(9, Short.MAX_VALUE))
        );

        PBEstres.getAccessibleContext().setAccessibleName("");

        getContentPane().add(jPanel1);
        jPanel1.setBounds(1040, 10, 130, 470);

        jPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jlabel.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jlabel.setText(":");
        jPanel2.add(jlabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 0, -1, -1));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        jLabel5.setText("TIEMPO");
        jPanel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 50, -1, -1));

        LMinutos.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        LMinutos.setText("00");
        jPanel2.add(LMinutos, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 50, 40));

        LSegundos.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        LSegundos.setText("00");
        jPanel2.add(LSegundos, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 10, 50, 40));

        getContentPane().add(jPanel2);
        jPanel2.setBounds(1040, 490, 130, 70);

        Alerta1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/BurbujaAlerta.gif"))); // NOI18N
        getContentPane().add(Alerta1);
        Alerta1.setBounds(230, 140, 24, 30);

        Alerta2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/BurbujaAlerta.gif"))); // NOI18N
        getContentPane().add(Alerta2);
        Alerta2.setBounds(230, 250, 24, 20);

        Alerta3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/BurbujaAlerta.gif"))); // NOI18N
        getContentPane().add(Alerta3);
        Alerta3.setBounds(230, 360, 24, 20);

        Alerta4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/BurbujaAlerta.gif"))); // NOI18N
        getContentPane().add(Alerta4);
        Alerta4.setBounds(460, 140, 24, 20);

        Alerta5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/BurbujaAlerta.gif"))); // NOI18N
        getContentPane().add(Alerta5);
        Alerta5.setBounds(460, 250, 24, 20);

        Alerta6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/BurbujaAlerta.gif"))); // NOI18N
        getContentPane().add(Alerta6);
        Alerta6.setBounds(450, 350, 24, 20);

        Alerta7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/BurbujaAlerta.gif"))); // NOI18N
        getContentPane().add(Alerta7);
        Alerta7.setBounds(690, 140, 24, 20);

        Alerta8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/BurbujaAlerta.gif"))); // NOI18N
        getContentPane().add(Alerta8);
        Alerta8.setBounds(690, 250, 24, 20);

        Alerta9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/BurbujaAlerta.gif"))); // NOI18N
        getContentPane().add(Alerta9);
        Alerta9.setBounds(690, 350, 24, 20);
        getContentPane().add(Escritorio1);
        Escritorio1.setBounds(660, 170, 70, 30);
        getContentPane().add(Escritorio2);
        Escritorio2.setBounds(660, 270, 70, 30);
        getContentPane().add(Escritorio3);
        Escritorio3.setBounds(650, 380, 80, 30);
        getContentPane().add(Escritorio4);
        Escritorio4.setBounds(430, 380, 80, 30);
        getContentPane().add(Escritorio5);
        Escritorio5.setBounds(430, 270, 80, 30);
        getContentPane().add(Escritorio6);
        Escritorio6.setBounds(430, 170, 80, 20);
        getContentPane().add(Escritorio7);
        Escritorio7.setBounds(210, 170, 70, 30);
        getContentPane().add(Escritorio8);
        Escritorio8.setBounds(200, 270, 90, 30);
        getContentPane().add(Escritorio9);
        Escritorio9.setBounds(220, 380, 50, 30);

        Jugador.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/PrincipalEst.png"))); // NOI18N
        getContentPane().add(Jugador);
        Jugador.setBounds(420, 80, 50, 60);
        getContentPane().add(Barrera);
        Barrera.setBounds(0, 560, 1020, 10);
        getContentPane().add(Barrera3);
        Barrera3.setBounds(1010, 10, 10, 550);
        getContentPane().add(Barrera4);
        Barrera4.setBounds(0, 10, 10, 550);
        getContentPane().add(Barrera7);
        Barrera7.setBounds(10, 0, 1020, 10);

        Impresora.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/ImpresoraNormal.png"))); // NOI18N
        getContentPane().add(Impresora);
        Impresora.setBounds(150, 490, 80, 70);
        getContentPane().add(OficinaBloq);
        OficinaBloq.setBounds(10, 160, 80, 170);
        getContentPane().add(OficinaBloq1);
        OficinaBloq1.setBounds(890, 10, 120, 190);
        getContentPane().add(OficinaBloq2);
        OficinaBloq2.setBounds(830, 200, 180, 190);
        getContentPane().add(OficinaBloq3);
        OficinaBloq3.setBounds(950, 420, 60, 140);
        getContentPane().add(Sala1);
        Sala1.setBounds(30, 450, 80, 90);
        getContentPane().add(Extra1);
        Extra1.setBounds(260, 500, 220, 50);
        getContentPane().add(Extra2);
        Extra2.setBounds(550, 500, 160, 50);
        getContentPane().add(Extra3);
        Extra3.setBounds(10, 310, 20, 50);
        getContentPane().add(Extra4);
        Extra4.setBounds(100, 230, 10, 50);
        getContentPane().add(Extra5);
        Extra5.setBounds(30, 50, 50, 30);
        getContentPane().add(Extra6);
        Extra6.setBounds(30, 20, 50, 30);
        getContentPane().add(Extra7);
        Extra7.setBounds(10, 20, 140, 50);
        getContentPane().add(Extra8);
        Extra8.setBounds(490, -20, 400, 100);
        getContentPane().add(Extra9);
        Extra9.setBounds(150, -20, 740, 70);
        getContentPane().add(Mesa);
        Mesa.setBounds(850, 480, 90, 10);

        Impresora3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/ImpresoraNormal.png"))); // NOI18N
        getContentPane().add(Impresora3);
        Impresora3.setBounds(740, 490, 80, 70);
        getContentPane().add(Extra10);
        Extra10.setBounds(810, 50, 70, 50);
        getContentPane().add(Extra11);
        Extra11.setBounds(640, 50, 40, 60);

        Impresora2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/ImpresoraNormal.png"))); // NOI18N
        getContentPane().add(Impresora2);
        Impresora2.setBounds(540, 50, 80, 70);

        Mochi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Mochi.png"))); // NOI18N
        getContentPane().add(Mochi);
        Mochi.setBounds(210, 60, 30, 20);

        Cafe.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Taza.gif"))); // NOI18N
        getContentPane().add(Cafe);
        Cafe.setBounds(880, 440, 30, 30);

        FondoOficina.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/FondoOficina.png"))); // NOI18N
        getContentPane().add(FondoOficina);
        FondoOficina.setBounds(0, 0, 1024, 570);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed
        if (evt.getKeyCode() == evt.VK_A) {
            direccion = 1;
        } else if (evt.getKeyCode() == evt.VK_D) {
            direccion = 2;
        } else if (evt.getKeyCode() == evt.VK_W) {
            direccion = 3;
        } else if (evt.getKeyCode() == evt.VK_S) {
            direccion = 4;
        } else if (evt.getKeyCode() == evt.VK_ESCAPE) {
            enPausa = true;

            int respuesta = JOptionPane.showConfirmDialog(null,"El juego está en pausa, ¿Deseas seguir jugando?","Pausa",JOptionPane.YES_NO_OPTION);
            if (respuesta == JOptionPane.YES_OPTION) {
                enPausa = false;
            } else {
                System.exit(0);
            }
        }
    }//GEN-LAST:event_formKeyPressed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Juego.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Juego.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Juego.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Juego.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Juego().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Alerta1;
    private javax.swing.JLabel Alerta2;
    private javax.swing.JLabel Alerta3;
    private javax.swing.JLabel Alerta4;
    private javax.swing.JLabel Alerta5;
    private javax.swing.JLabel Alerta6;
    private javax.swing.JLabel Alerta7;
    private javax.swing.JLabel Alerta8;
    private javax.swing.JLabel Alerta9;
    private javax.swing.JLabel Barrera;
    private javax.swing.JLabel Barrera3;
    private javax.swing.JLabel Barrera4;
    private javax.swing.JLabel Barrera7;
    private javax.swing.JLabel Cafe;
    private javax.swing.JLabel Escritorio1;
    private javax.swing.JLabel Escritorio2;
    private javax.swing.JLabel Escritorio3;
    private javax.swing.JLabel Escritorio4;
    private javax.swing.JLabel Escritorio5;
    private javax.swing.JLabel Escritorio6;
    private javax.swing.JLabel Escritorio7;
    private javax.swing.JLabel Escritorio8;
    private javax.swing.JLabel Escritorio9;
    private javax.swing.JLabel Extra1;
    private javax.swing.JLabel Extra10;
    private javax.swing.JLabel Extra11;
    private javax.swing.JLabel Extra2;
    private javax.swing.JLabel Extra3;
    private javax.swing.JLabel Extra4;
    private javax.swing.JLabel Extra5;
    private javax.swing.JLabel Extra6;
    private javax.swing.JLabel Extra7;
    private javax.swing.JLabel Extra8;
    private javax.swing.JLabel Extra9;
    private javax.swing.JLabel FondoOficina;
    private javax.swing.JLabel Impresora;
    private javax.swing.JLabel Impresora2;
    private javax.swing.JLabel Impresora3;
    private javax.swing.JLabel Jugador;
    private javax.swing.JLabel LMinutos;
    private javax.swing.JLabel LSegundos;
    private javax.swing.JLabel Mesa;
    private javax.swing.JLabel Mochi;
    private javax.swing.JLabel OficinaBloq;
    private javax.swing.JLabel OficinaBloq1;
    private javax.swing.JLabel OficinaBloq2;
    private javax.swing.JLabel OficinaBloq3;
    private javax.swing.JProgressBar PBEnergia;
    private javax.swing.JProgressBar PBEstres;
    private javax.swing.JLabel Sala1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel jlabel;
    // End of variables declaration//GEN-END:variables
}
