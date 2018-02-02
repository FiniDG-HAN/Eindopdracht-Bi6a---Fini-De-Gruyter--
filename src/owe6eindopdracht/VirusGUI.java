/**
 * Created by Fini De Gruyter on 29-01-2018.
 */
package owe6eindopdracht;

/**
 * alle imports
 */
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JViewport;
import static owe6eindopdracht.VirusLogica.hosts;

/**
 * @function maakt de GUI voor het programma, zodat de gebruiker makkelijk en
 * met behulp van een interface de juiste informatie kan ophalen
 *
 * Dit is tevens de hoofdclass, waaruit het programma gestart moet worden
 * @author Fini De Gruyter
 * @version 1.0
 * @since 29 jan 2018 11.09
 */
public class VirusGUI extends JFrame implements ActionListener {

    String fileLoc1, regel, header;

    static JTextField textfieldBestand;
    private JButton buttonBrowse, buttonOpen, buttonActiveer;
    private JLabel labelFile, labelClass, labelHost;
    private BufferedReader inFile;
    static JComboBox comboBoxLinks, comboBoxRechts, comboBoxMidden;
    private JTextArea textAreaVirus1, textAreaVirus2, textAreaOverlap;
    private JScrollPane scrollPaneVirus1, scrollPaneVirus2, scrollPaneOverlap;
    JRadioButton radioID, radioClass, radioHost;
    private JPanel radioPanel, activeerPanel;

    /**
     * @function de main functie. laat ook de GUI starten en zet de eerste
     * frames op.
     *
     * @param args the command line arguments (zijn er in dit geval niet)
     */
    public static void main(String[] args) {
        VirusGUI frame = new VirusGUI();
        frame.setSize(900, 600);
        frame.setTitle("Virus applicatie");
        frame.createGUI();
        frame.setVisible(true);
    }

    /**
     * @function Maakt een GUI met labels, textarea, buttons, radiobuttons,
     * comboboxen en scrollable textareas Gebruik gemaakt van flowlayout,
     * waardoor alle GUI elementen achter Elkaar worden weergegeven, maar door
     * de vaste breedte van het frame in De main methode gaan ze op de juiste
     * plek staan.
     *
     */
    public void createGUI() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        Container window = getContentPane();
        window.setLayout(new FlowLayout());

        // alle elementen uit de GUI aanmaken en actionListeners toevoegen
        labelFile = new JLabel("File of URL:");
        labelClass = new JLabel("Viral classification");
        labelHost = new JLabel("Host ID");
        textfieldBestand = new JTextField();
        buttonBrowse = new JButton("Browse");
        buttonBrowse.addActionListener(this);
        buttonOpen = new JButton("Open");
        buttonOpen.addActionListener(this);
        comboBoxLinks = new JComboBox();
        comboBoxRechts = new JComboBox();
        comboBoxMidden = new JComboBox();
        textAreaVirus1 = new JTextArea();
        textAreaVirus2 = new JTextArea();
        textAreaOverlap = new JTextArea();
        scrollPaneVirus1 = new JScrollPane(textAreaVirus1);
        scrollPaneVirus2 = new JScrollPane(textAreaVirus2);
        scrollPaneOverlap = new JScrollPane(textAreaOverlap);
        radioID = new JRadioButton("ID");
        radioClass = new JRadioButton("classificatie");
        radioHost = new JRadioButton("aantal hosts");
        radioID.addActionListener(this);
        radioClass.addActionListener(this);
        radioHost.addActionListener(this);
        buttonActiveer = new JButton("Activeer");
        buttonActiveer.addActionListener(this);

        // alle elementen de juiste grootte geven, zodat het er netjes uitziet
        // vanwege de flowlayout
        textfieldBestand.setPreferredSize(new Dimension(600, 30));
        comboBoxMidden.setPreferredSize(new Dimension(852, 30));
        comboBoxLinks.setPreferredSize(new Dimension(396, 30));
        comboBoxRechts.setPreferredSize(new Dimension(396, 30));
        scrollPaneVirus1.setPreferredSize(new Dimension(200, 250));
        scrollPaneVirus2.setPreferredSize(new Dimension(200, 250));
        scrollPaneOverlap.setPreferredSize(new Dimension(200, 250));

        // de titels zetten boven de drie textareas, zodat er niet aparte
        // JLabels nodig zijn
        JViewport jv1 = new JViewport();
        jv1.setView(new JLabel("Viruslijst"));
        JViewport jv2 = new JViewport();
        jv2.setView(new JLabel("Viruslijst"));
        JViewport jv3 = new JViewport();
        jv3.setView(new JLabel("Overeenkomst"));
        scrollPaneVirus1.setColumnHeader(jv1);
        scrollPaneVirus2.setColumnHeader(jv2);
        scrollPaneOverlap.setColumnHeader(jv3);

        // de radiobuttons en de titel en onder elkaar zetten vereiste extra
        // code die hieronder staat weergegeven
        ButtonGroup group = new ButtonGroup();
        group.add(radioID);
        group.add(radioClass);
        group.add(radioHost);
        radioPanel = new JPanel();
        radioPanel.setLayout(new GridLayout(3, 1));
        radioPanel.add(radioID);
        radioPanel.add(radioClass);
        radioPanel.add(radioHost);
        radioPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Sortering"));

        // zorgen dat overeenkomstbutton en radiobuttons onder elkaar komen te staan.
        activeerPanel = new JPanel();
        activeerPanel.setLayout(new GridLayout(2, 1));
        activeerPanel.add(radioPanel);
        activeerPanel.add(buttonActiveer);

// alles toevoegen in de juiste volgorde
        window.add(labelFile);
        window.add(textfieldBestand);
        window.add(buttonBrowse);
        window.add(buttonOpen);
        window.add(labelClass);
        window.add(comboBoxMidden);
        window.add(labelHost);
        window.add(comboBoxLinks);
        window.add(comboBoxRechts);
        window.add(scrollPaneVirus1);
        window.add(scrollPaneVirus2);
        window.add(scrollPaneOverlap);
        //window.add(radioPanel);
        //window.add(buttonActiveer);
        window.add(activeerPanel);

    }

    @Override
    public void actionPerformed(ActionEvent event) {
        if (event.getSource() == buttonBrowse) {
            JFileChooser fileChooser = new JFileChooser();
            int reply = fileChooser.showOpenDialog(this);
            if (reply == JFileChooser.APPROVE_OPTION) {
                File selectedFile1 = fileChooser.getSelectedFile();
                textfieldBestand.setText(selectedFile1.getAbsolutePath());
                fileLoc1 = textfieldBestand.getText();
            }
        }
        if (event.getSource() == buttonOpen) {
            //HashMap hosts = VirusLogica.bestandLezen(textfieldBestand.getText());
            VirusLogica.bestandLezen(textfieldBestand.getText());
            comboBoxLinks.setModel(new DefaultComboBoxModel(VirusLogica.hosts.keySet().toArray()));
            comboBoxRechts.setModel(new DefaultComboBoxModel(VirusLogica.hosts.keySet().toArray()));
            comboBoxLinks.setEnabled(true);
            comboBoxRechts.setEnabled(true);

        }
        if (event.getSource() == buttonActiveer) {
            String host1 = comboBoxLinks.getSelectedItem().toString();
            String host2 = comboBoxRechts.getSelectedItem().toString();
            ArrayList<Virus> virus1Lijst = new ArrayList<>(VirusLogica.hosts.get(host1));
            ArrayList<Virus> virus2Lijst = new ArrayList<>(VirusLogica.hosts.get(host2));

            textAreaVirus1.setText("");
            virus1Lijst.forEach((Virus virus) -> {
                textAreaVirus1.append(virus.getVirusID() + "\n");
            });
            textAreaVirus2.setText("");
            virus2Lijst.forEach((Virus virus) -> {
                textAreaVirus2.append(virus.getVirusID() + "\n");
            });

        }
        if (event.getSource() == radioID) {
            Virus.sorteer = 1;
            System.out.println("hallo");
        }
        if (event.getSource() == radioClass) {
            Virus.sorteer = 2;
            System.out.println("hallo");
        }
        if (event.getSource() == radioHost) {
            Virus.sorteer = 3;
            System.out.println("hallo");
        }

    }

}
