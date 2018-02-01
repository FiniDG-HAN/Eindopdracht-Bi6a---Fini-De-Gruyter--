/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package owe6eindopdracht;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.JOptionPane;

/**
 *
 * @author fini
 */
public class VirusAnalyse {

    BufferedReader inFile;

    public String bestandLezen(String Path) {
        try {
            inFile = new BufferedReader(new FileReader(Path));
            String line;
            inFile.readLine();

            while ((line = inFile.readLine()) != null) {
                String[] lines = line.split("\t");
                testmethode(lines);
            }

            inFile.close();

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Bestand kan niet gelezen worden");
        } catch (ArrayIndexOutOfBoundsException a) {
            JOptionPane.showMessageDialog(null, "Bestand kan niet gelezen worden");
        } catch (Exception e) {
            System.out.println("Onbekende fout: raadpleeg uw systeembeheerder");
        }
        return "hallo";
    }
    public String[] testmethode(String[] iets){
        return iets;
    }

}
