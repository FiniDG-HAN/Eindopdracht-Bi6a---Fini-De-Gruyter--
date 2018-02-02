/**
 * Created by Fini De Gruyter on 29-01-2018.
 * 
 * 
 * Dit programma leest het bestand met virussen en de hosts die ze kunnen besmetten
 * van ftp://ftp.genome.jp/pub/db/virushostdb/ . Dit bestand veranderd (dagelijks)
 * van inhoud, omdat het publiek toegangelijk is.
 * 
 * Het laat de gebruiker kiezen tussen twee hosts en toont van elke host de virussen 
 * (VirusID's) die deze host kunnen besmetten, op basis van een bepaalde categorie 
 * van virussen (classificatie, zie http://www.genome.jp/virushostdb/ ). Vervolgens 
 * wordt in het vak "overeenkomst" de overeenkomstige VirusID's getoond, oftewel
 * de virusID's die beide hosts kunnen besmetten. Deze drie rijen van gegevens
 * kunnen worden gesorteerd op basis van virusID nummer, soortnaam van het virus
 * of het aantal hosts dat dit virusID kan besmetten. 
 * 
 * Werking van programma:
 * 1. Klik op "browse" om het bestand van bovengenoemde website in te laden.
 * Het bestandspad wordt vervolgens getoond in de balk.
 * 2. Klik op "open" om het bestand in de laden en de drop-down menus te voorzien
 * van de keuzemogelijkheden.
 * 3. Klik de gewenste virussen aan bij classificatie, klik de gewenste hosts aan
 * en Klik eventueel al op een bepaalde sortering (kan later ook).
 * 4. Klik op "activeer" om de juiste informatie te laten tonen. Ook nu kan er
 * door de klikken op de keuzemogelijkheden een sortering plaatsvinden.
 * 5. Herhaal indien gewenst stap 3 en 4 om nieuwe informatie te tonen.
 * 
 * 
 * @author Fini De Gruyter
 * @version 2.0
 * @since 29 jan 2018 11.09
 * 
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
import java.util.Collections;
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

/**
 * @function maakt de GUI voor het programma, zodat de gebruiker makkelijk en
 * met behulp van een interface de juiste informatie kan ophalen
 *
 * Dit is tevens de hoofdclass, waaruit het programma gestart moet worden
 * @author Fini De Gruyter
 * @version 2.0
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

    ArrayList<Virus> virus1Lijst = new ArrayList<>();
    ArrayList<Virus> virus2Lijst = new ArrayList<>();

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
        labelFile = new JLabel("File:");
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
        radioClass = new JRadioButton("Soortnaam");
        radioHost = new JRadioButton("Aantal hosts");
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

        // zorgen dat activeerbutton en radiobuttons onder elkaar komen te staan.
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
            VirusLogica.bestandLezen(textfieldBestand.getText());
            comboBoxLinks.setModel(new DefaultComboBoxModel(VirusLogica.hosts.keySet().toArray()));
            comboBoxRechts.setModel(new DefaultComboBoxModel(VirusLogica.hosts.keySet().toArray()));
            comboBoxMidden.setModel(new DefaultComboBoxModel(VirusLogica.classificatieLijst.toArray()));
            comboBoxLinks.setEnabled(true);
            comboBoxRechts.setEnabled(true);
            comboBoxMidden.setEnabled(true);
        }

        if (event.getSource() == buttonActiveer) {

            String host1 = comboBoxLinks.getSelectedItem().toString();
            String host2 = comboBoxRechts.getSelectedItem().toString();
            virus1Lijst = new ArrayList<>(VirusLogica.hosts.get(host1));
            virus2Lijst = new ArrayList<>(VirusLogica.hosts.get(host2));
            virus1Lijst.removeIf(virus -> !virus.getClassificatie().equals((String) comboBoxMidden.getSelectedItem()));
            virus2Lijst.removeIf(virus -> !virus.getClassificatie().equals((String) comboBoxMidden.getSelectedItem()));

            textAreaVirus1.setText("");
            ArrayList<Integer> dubbelChecker1 = new ArrayList<>();
            virus1Lijst.forEach((Virus virus) -> {
                if (!dubbelChecker1.contains(virus.getVirusID())) {
                    dubbelChecker1.add(virus.getVirusID());
                    textAreaVirus1.append(virus.getVirusID() + "\n");
                }

            });
            textAreaVirus2.setText("");
            ArrayList<Integer> dubbelChecker2 = new ArrayList<>();
            virus2Lijst.forEach((Virus virus) -> {
                if (!dubbelChecker2.contains(virus.getVirusID())) {
                    dubbelChecker2.add(virus.getVirusID());
                    textAreaVirus2.append(virus.getVirusID() + "\n");
                }
            });

            textAreaOverlap.setText("");
            ArrayList<Integer> dubbelChecker3 = new ArrayList<>();
            virus1Lijst.forEach((Virus virus) -> {
                if (!dubbelChecker3.contains(virus.getVirusID())) {
                    dubbelChecker3.add(virus.getVirusID());
                    textAreaOverlap.append(virus.getVirusID() + "\n");
                }
            });
        }

        if (event.getSource() == radioID) {
            Virus.sorteer = 1;

            Collections.sort(virus1Lijst);
            Collections.sort(virus2Lijst);
            HashSet overlap = new HashSet(virus1Lijst);
            overlap.retainAll(virus2Lijst);
            ArrayList<Virus> overlapList = new ArrayList(overlap);

            textAreaVirus1.setText("");
            ArrayList<Integer> dubbelChecker1 = new ArrayList<>();
            virus1Lijst.forEach((Virus virus) -> {
                if (!dubbelChecker1.contains(virus.getVirusID())) {
                    dubbelChecker1.add(virus.getVirusID());
                    textAreaVirus1.append(virus.getVirusID() + "\n");
                }
            });

            textAreaVirus2.setText("");
            ArrayList<Integer> dubbelChecker2 = new ArrayList<>();
            virus2Lijst.forEach((Virus virus) -> {
                if (!dubbelChecker2.contains(virus.getVirusID())) {
                    dubbelChecker2.add(virus.getVirusID());
                    textAreaVirus2.append(virus.getVirusID() + "\n");
                }
            });

            textAreaOverlap.setText("");
            ArrayList<Integer> dubbelChecker3 = new ArrayList<>();
            virus1Lijst.forEach((Virus virus) -> {
                if (!dubbelChecker3.contains(virus.getVirusID())) {
                    dubbelChecker3.add(virus.getVirusID());
                    textAreaOverlap.append(virus.getVirusID() + "\n");
                }
            });
        }

        if (event.getSource() == radioClass) {
            Virus.sorteer = 2;

            Collections.sort(virus1Lijst);
            Collections.sort(virus2Lijst);
            HashSet overlap = new HashSet(virus1Lijst);
            overlap.retainAll(virus2Lijst);
            ArrayList<Virus> overlapList = new ArrayList(overlap);

            textAreaVirus1.setText("");
            ArrayList<Integer> dubbelChecker1 = new ArrayList<>();
            virus1Lijst.forEach((Virus virus) -> {
                if (!dubbelChecker1.contains(virus.getVirusID())) {
                    dubbelChecker1.add(virus.getVirusID());
                    textAreaVirus1.append(virus.getVirusID() + "\n");
                }
            });

            textAreaVirus2.setText("");
            ArrayList<Integer> dubbelChecker2 = new ArrayList<>();
            virus2Lijst.forEach((Virus virus) -> {
                if (!dubbelChecker2.contains(virus.getVirusID())) {
                    dubbelChecker2.add(virus.getVirusID());
                    textAreaVirus2.append(virus.getVirusID() + "\n");
                }
            });

            textAreaOverlap.setText("");
            ArrayList<Integer> dubbelChecker3 = new ArrayList<>();
            virus1Lijst.forEach((Virus virus) -> {
                if (!dubbelChecker3.contains(virus.getVirusID())) {
                    dubbelChecker3.add(virus.getVirusID());
                    textAreaOverlap.append(virus.getVirusID() + "\n");
                }
            });

        }

        if (event.getSource() == radioHost) {
            Virus.sorteer = 3;

            Collections.sort(virus1Lijst);
            Collections.sort(virus2Lijst);
            HashSet overlap = new HashSet(virus1Lijst);
            overlap.retainAll(virus2Lijst);
            ArrayList<Virus> overlapList = new ArrayList(overlap);

            textAreaVirus1.setText("");
            ArrayList<Integer> dubbelChecker1 = new ArrayList<>();
            virus1Lijst.forEach((Virus virus) -> {
                if (!dubbelChecker1.contains(virus.getVirusID())) {
                    dubbelChecker1.add(virus.getVirusID());
                    textAreaVirus1.append(virus.getVirusID() + "\n");
                }
            });

            textAreaVirus2.setText("");
            ArrayList<Integer> dubbelChecker2 = new ArrayList<>();
            virus2Lijst.forEach((Virus virus) -> {
                if (!dubbelChecker2.contains(virus.getVirusID())) {
                    dubbelChecker2.add(virus.getVirusID());
                    textAreaVirus2.append(virus.getVirusID() + "\n");
                }
            });

            textAreaOverlap.setText("");
            ArrayList<Integer> dubbelChecker3 = new ArrayList<>();
            virus1Lijst.forEach((Virus virus) -> {
                if (!dubbelChecker3.contains(virus.getVirusID())) {
                    dubbelChecker3.add(virus.getVirusID());
                    textAreaOverlap.append(virus.getVirusID() + "\n");
                }
            });

        }

    }

}
