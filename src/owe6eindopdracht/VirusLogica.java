/**
 * Created by Fini De Gruyter on 29-01-2018.
 */
package owe6eindopdracht;

/**
 * alle imports
 */
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;

/**
 * @function Hier zit alle Logica van het programma. hier worden de juiste
 * berekeningen uitgevoerd en de gegevens opgehaald uit het bestand
 * @author Fini De Gruyter
 * @version 1.0
 * @since 29 jan 2018 11.12
 */
public class VirusLogica {

    /**
     *
     * @reference Jonathan Feenstra
     * @function: bestand inlezen en de juiste waardes eruit halen om in de
     * class Virus te kunnen zetten. Leest het bestand regel voor regel en split
     * op tabs.
     *
     * Er was een lastig stuk waar ik niet uit kon komen (hoe zet je de meerdere
     * Hosts in 1 Virus object, zonder steeds te moeten zoeken in het bestand
     * naar de rest van alle mogelijke hosts met hetzelfde VirusID. Hierbij
     * heeft Jonathan geholpen. Dit zorgt dat de Big O niet zo groot wordt (n^n)
     * @param Path het bestandspad, zodat deze getoond wordt in de GUI
     * @return hosts en null
     */
    public static HashSet bestandLezen(String Path) {
        try {
            BufferedReader inFile;
            inFile = new BufferedReader(new FileReader(Path));
            String line;
            inFile.readLine();
            HashMap<String, HashSet<Virus>> hosts = new HashMap<>();
            HashSet<Virus> virusses = new HashSet();
            while ((line = inFile.readLine()) != null) {
                
                String[] array = line.split("\t", -1); // -1 zorgt dat hij de lege tabs ook ziet
                if (array[7] != null && !"".equals(array[7])) {
                    int virusID = Integer.parseInt(array[0]);
                    String soort = array[1];
                    String lineage = array[2];
                    String[] lineageArray = lineage.split(";");
                    String classificatie = lineageArray[1];
                    //getClassificatie(classificatie);
                    int hostID = Integer.parseInt(array[7]);
                    String hostIDcompleet = array[7] + " (" + array[8] + ")";

                    // @reference Jonathan Veenstra
                    Virus dezeVirus = new Virus(virusID, soort, classificatie);
                    dezeVirus.addHost(hostID);
                    virusses.add(dezeVirus);

                    if (hosts.containsKey(hostIDcompleet)) {

                        hosts.get(hostIDcompleet).add(dezeVirus);
                        for (Virus virus : hosts.get(hostIDcompleet)) {
                            virus.addHost(Integer.parseInt(array[7]));
                        }
                    } else {
                        hosts.put(hostIDcompleet, (HashSet<Virus>) virusses.clone());
                    }
                    virusses.clear();
                    // @reference: einde code jonathan
                }
                
            }
            inFile.close();
            return virusses;
            
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Bestand kan niet gelezen worden");
        } catch (ArrayIndexOutOfBoundsException a) {
            JOptionPane.showMessageDialog(null, "Bestand kan niet gelezen worden");
        } catch (Exception e) {
            System.out.println("Onbekende fout: raadpleeg uw systeembeheerder");
        }
        return null;
    }
    
    
    public static void probeersel() {
        VirusGUI.comboBoxLinks.setModel(new DefaultComboBoxModel(hosts.keySet().toArray()));
        VirusGUI.comboBoxRechts.setModel(new DefaultComboBoxModel(hosts.keySet().toArray()));
        VirusGUI.comboBoxLinks.setEnabled(true);
        VirusGUI.comboBoxRechts.setEnabled(true);
        
        ArrayList virusList1 = new ArrayList<>(hosts.get(VirusGUI.comboBoxLinks.getSelectedItem().toString()));
        ArrayList virusList2 = new ArrayList<>(hosts.get(VirusGUI.comboBoxRechts.getSelectedItem().toString()));
        
    }