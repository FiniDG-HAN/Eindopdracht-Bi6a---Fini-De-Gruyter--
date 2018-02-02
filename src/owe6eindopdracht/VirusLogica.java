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
import java.util.HashMap;
import java.util.HashSet;
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
    static HashMap<String, HashSet<Virus>> hosts = new HashMap<>();
    static HashSet<Virus> virusses = new HashSet();
    static HashSet<String> classificatieLijst = new HashSet<>();

    /**
     *
     * @param Path
     */
    public static void bestandLezen(String Path) {
        try {
            BufferedReader inFile;
            inFile = new BufferedReader(new FileReader(Path));
            String line;
            inFile.readLine();
            int teller = 0;
            while ((line = inFile.readLine()) != null && teller < 501) {
                teller++;
                String[] array = line.split("\t", -1); // -1 zorgt dat hij de lege tabs ook ziet
                if (array[7] != null && !"".equals(array[7])) {
                    int virusID = Integer.parseInt(array[0]);
                    String soort = array[1];
                    String lineage = array[2];
                    String[] lineageArray = lineage.split(";");
                    String classificatie = lineageArray[1];
                    int hostID = Integer.parseInt(array[7]);
                    String hostIDcompleet = array[7] + " (" + array[8] + ")";
                    classificatieLijst.add(classificatie);

                    // @reference Jonathan Feenstra
                    Virus dezeVirus = new Virus(virusID, soort, classificatie);
                    dezeVirus.addHost(hostID);
                    virusses.add(dezeVirus);

                    if (!hosts.containsKey(hostIDcompleet)) {
                        hosts.put(hostIDcompleet, (HashSet) virusses.clone());
                    } else {
                        hosts.get(hostIDcompleet).add(dezeVirus);
                        for (Virus virus : hosts.get(hostIDcompleet)) {
                            virus.addHost(Integer.parseInt(array[7]));
                        }
                    }
                    // @reference: einde code jonathan
                }
            }
            inFile.close();

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Bestand kan niet gelezen worden");
        } catch (ArrayIndexOutOfBoundsException a) {
            JOptionPane.showMessageDialog(null, "Bestand kan niet gelezen worden");
        } catch (Exception e) {
            System.out.println("Onbekende fout: raadpleeg uw systeembeheerder");
        }
    }

}
