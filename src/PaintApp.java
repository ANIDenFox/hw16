import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class PaintApp extends JFrame {
    private Color currentColor = Color.BLACK;
    private int brushSize = 5;
    private boolean eraseMode = false;
    private JPanel canvas;

    public PaintApp() {
        setTitle("Paint");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        canvas = new JPanel();
        canvas.setBackground(Color.WHITE);

        MouseAdapter mousePressed = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (!eraseMode) {
                    drawPoint(e.getPoint());
                } else {
                    erasePoint(e.getPoint());
                }
            }
        };
        canvas.addMouseListener(mousePressed);

        MouseMotionAdapter mouseDragged = new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (!eraseMode) {
                    drawPoint(e.getPoint());
                } else {
                    erasePoint(e.getPoint());
                }
            }
        };
        canvas.addMouseMotionListener(mouseDragged);

        add(canvas, BorderLayout.CENTER);

        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        JButton colorButton = createButton("Оберіть колір", this::chooseColor);
        JButton eraseButton = createButton("Ластик", this::toggleEraseMode);
        JButton clearButton = createButton("Очистити", this::clearCanvas);

        for (int i = 1; i <= 5; i++) {
            JButton sizeButton = createButton(String.valueOf(i), e -> setBrushSize(Integer.parseInt(e.getActionCommand())));
            sizeButton.setMargin(new Insets(5, 5, 5, 5));
            controlPanel.add(sizeButton);
        }

        controlPanel.add(Box.createHorizontalGlue());

        controlPanel.add(colorButton);
        controlPanel.add(Box.createRigidArea(new Dimension(5, 0)));
        controlPanel.add(eraseButton);
        controlPanel.add(Box.createRigidArea(new Dimension(5, 0)));
        controlPanel.add(clearButton);

        add(controlPanel, BorderLayout.NORTH);

        setVisible(true);
    }

    private void setBrushSize(int size) {
        brushSize = size;
    }

    private void clearCanvas(ActionEvent actionEvent) {
        Graphics g = canvas.getGraphics();
        g.setColor(canvas.getBackground());
        g.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }

    private void drawPoint(Point point) {
        Graphics g = canvas.getGraphics();
        g.setColor(currentColor);
        g.fillOval(point.x - brushSize / 2, point.y - brushSize / 2, brushSize, brushSize);
    }

    private void erasePoint(Point point) {
        Graphics g = canvas.getGraphics();
        g.setColor(canvas.getBackground());
        g.fillOval(point.x - brushSize / 2, point.y - brushSize / 2, brushSize, brushSize);
    }

    private void chooseColor(ActionEvent e) {
        currentColor = JColorChooser.showDialog(this, "Оберіть колір", currentColor);
    }

    private void toggleEraseMode(ActionEvent e) {
        eraseMode = !eraseMode;
        JButton eraseButton = (JButton) e.getSource();
        eraseButton.setText(eraseMode ? "Кисть" : "Ластик");
    }

    private JButton createButton(String label, ActionListener listener) {
        JButton button = new JButton(label);
        button.addActionListener(listener);
        return button;
    }
}
