import javax.swing.*;
import java.awt.*;

public class GuiManager extends JFrame {

    public GuiManager() {
        initUI();
    }

    private void addSearchButton() {
        Container pane = getContentPane();

        JButton searchButton = new JButton();

        searchButton.setBounds(350, 525, 200, 50);
        searchButton.setVisible(true);
        searchButton.setText("Search");

        pane.add(searchButton);
    }

    private void addResultsBox() {
        Container pane = getContentPane();

        JTextArea area = new JTextArea();

        area.setBounds(200, 5, 690, 510);
        area.setBackground(Color.blue);
        area.setVisible(true);
        area.setText("IpsumLorem IpsumLorem IpsumLorem IpsumLorem Ipsum");

        pane.add(area);
    }

    private void addLeftPanel() {
        Container pane = getContentPane();
        JPanel panel = new JPanel();
        JScrollPane scrollPane = new JScrollPane(panel);

        int height = 50;

        for(int i= 0; i < 20; i++) {

            JButton button = new JButton();

            button.setBounds(i * 50 + 2, 10, 100, height);
            button.setVisible(true);
            button.setText("Search " + i);

            panel.add(button);
        }

        panel.setBackground(Color.red);

        //scrollPane.setBackground(Color.GREEN);

        scrollPane.setBounds(10, 5, 180, 510);

        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);



/*

        JLabel fieldLabel = new JLabel();
        fieldLabel.setText("Field: ");

        DefaultListModel<String> model = new DefaultListModel<>();
        JList<String> list = new JList<>(model);

        model.addElement("date");
        model.addElement("time");
        model.addElement("jobId");
        model.addElement("message");
*/

        pane.add(scrollPane);
    }

    private void initUI() {
        Container pane = getContentPane();
        pane.setLayout(null);

        setTitle("LoGrep");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        addLeftPanel();
        addResultsBox();
        addSearchButton();
    }

    public void launchGui() {
        EventQueue.invokeLater(() -> {
            GuiManager ex = new GuiManager();
            ex.setVisible(true);
        });
    }
}
