/**
 * Created by Fini De Gruyter on 29-01-2018.
 */
package owe6eindopdracht;

/**
 * alle imports
 */
import java.util.ArrayList;

/**
 * @function Hier wordt de class Virus gemaakt, wat ervoor zorgt dat elk VirusID
 * in het bestand een apart object wordt met de relevante informatie erin
 * @author Fini De Gruyter
 * @version 1.0
 * @since 29 jan 2018 11.10
 */
public class Virus implements Comparable<Virus> {

    int virusID;
    String soort;
    String classificatie;
    ArrayList<Integer> hostList = new ArrayList<>();

    /**
     * @function de constructor van Virus
     * @param ID VirusID
     * @param classification Welk type virus is het? zoals ssRNA, dsDNA ect.
     * @param species De naam van het virus
     */
    public Virus(int ID, String species, String classification) {
        this.virusID = ID;
        this.soort = species;
        this.classificatie = classification;
        this.hostList = new ArrayList<>();
    }

    /**
     * @reference Jonathan Feenstra
     * @function: is nodig om meerdere Hosts bij 1 Virus te zetten, zonder dat
     * de big O zo groot wordt. Anders moest je steeds search() doen in het
     * grote bestand. Zie ook commentaar in bestandLezen() bij VirusLogica
     * @param hostID
     */
    public void addHost(int hostID) {
        this.hostList.add(hostID);
    }

    /**
     * @function: getters en setters van de virus class
     * @param a
     */
    public void setVirusID(int a) {
        virusID = a;
    }

    /**
     * @function: getters en setters van de virus class
     * @return virusID
     */
    public int getVirusID() {
        return virusID;
    }

    /**
     * @function: getters en setters van de virus class
     * @param b
     */
    public void setSoort(String b) {
        soort = b;
    }

    /**
     * @function: getters en setters van de virus class
     * @return soort
     */
    public String getSoort() {
        return soort;
    }

    /**
     * @function: getters en setters van de virus class
     * @param c
     */
    public void setClassificatie(String c) {
        classificatie = c;
    }

    /**
     * @function: getters en setters van de virus class
     * @return classificatie
     */
    public String getClassificatie() {
        return classificatie;
    }

    /**
     * @function: getters en setters van de virus class
     * @param d
     */
    public void setHost(ArrayList<Integer> d) {
        if (hostList == null) {
            throw new NullPointerException("hostList mag niet leeg zijn");
        } else {
            hostList = d;
        }
    }

    /**
     * @function: getters en setters van de virus class
     * @return ArrayList van de hostList
     */
    public ArrayList<Integer> getHost() {
        return new ArrayList<>(hostList);
    }

    /**
     * @reference: Jonathan Feenstra, zie de compareTo methode
     * @function: zorgt dat de sorteer variable voor de compareTo methode in de
     * class Virus is. hiermee kun je dus op meerdere verschillende manieren 
     * sorteren
     */
    public static int sorteer;

    /**
     * @reference Jonathan Feenstra. heeft geholpen om ervoor te zorgen dat je
     * op drie verschillende manieren kan sorteren. 
     *
     * @param o Is het Virusobject die gegeven wordt aan de comparable methode
     * @function het kunnen sorteren van de virusobjecten op basis van de
     * eigenschappen van het virus
     * @return 0, 1 of -1. 0 wordt teruggegeven als de waardes gelijk zijn aan
     * elkaar 1 wordt teruggegeven als de vergelijking tussen de objecten zegt
     * dat het ene object "groter" is dan het andere. -1 wordt teruggegeven als
     * de vergelijking tussen de objecten zegt dat het ene object "kleiner" is
     * dan het andere. Default is return 0
     */
    @Override
    public int compareTo(Virus o) {
        Virus v = (Virus) o;
        switch (sorteer) {
            case 1:
                return v.virusID - this.virusID;
            case 2:
                return v.soort.compareTo(this.soort);
            case 3:
                return v.hostList.size() - this.hostList.size();
            default:
                return 0;
        }
    }

}
