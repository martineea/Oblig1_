package Oblig2;


////////////////// class DobbeltLenketListe //////////////////////////////


import java.util.*;

import java.util.function.Predicate;



public class DobbeltLenketListe<T> implements Liste<T> {

    /**
     * Node class
     *
     * @param <T>
     */
    private static final class Node<T> {
        private T verdi;                   // nodens verdi
        private Node<T> forrige, neste;    // pekere

        private Node(T verdi, Node<T> forrige, Node<T> neste) {
            this.verdi = verdi;
            this.forrige = forrige;
            this.neste = neste;
        }

        private Node(T verdi) {
            this(verdi, null, null);
        }
    }

    // instansvariabler
    private Node<T> hode;          // peker til den første i listen
    private Node<T> hale;          // peker til den siste i listen
    private int antall;            // antall noder i listen
    private int endringer;         // antall endringer i listen


    public DobbeltLenketListe() { // konstruktør for tom liste
        hode = null;
        hale = null;
        antall = 0;
        endringer = 0;
    }

    /*
    Sjekkliste for konstrultøren DobbeltLenketListe(T[]):
    ● Stoppes en null-tabell? Kastes i så fall en ​NullPointerException​?
    ● Blir det korrekt hvis parametertabellen inneholder en eller flere null-verdier?
    ● Blir det korrekt hvis parametertabellen er tom (har lengde 0)?
    ● Blir det korrekt hvis parametertabellen kun har null-verdier?
    ● Blir det korrekt hvis parametertabellen har kun én verdi som ikke er null?
    ● Blir antallet satt korrekt?
    ● Får verdiene i listen samme rekkefølge som i tabellen?
     */

    // Oppgave 1
    public DobbeltLenketListe(T[] a) { // konstruktør
        // Skal lage en dobbeltlenket liste med verdiene fra tabellen a
        // Verdiene skal ligge i samme rekkefølge i listen som i tabellen

        // Hvis a er null skal det kastes en NullPointerException
        // Hvis a inneholder en eller flere null-verdier skal de ikke tas med
        // Dvs den skal returnere liste med verdiene fra a som ikke er null
        Objects.requireNonNull(a, "Tabellen a er null!"); // kaster nullPointerException om a == null

        // Passe på at hode peker til den første i listen og hale til den siste
        // Pass på at neste og forrige er satt riktig i alle noder.
        // hode.forrige og hale.neste skal være null
        // Hvis tabellen a kun har en verdi skal både hode og hale peke på samme node
        // Hvis a er tom skal det ikke opprettes noen noder og hode og hale er fortsatt null

        Node<T> p = new Node(null, null, null); // oppretter en ny node med ingen verdier eller forrige-/neste-pekere

        // Først settes hode - gir noden i p en verdi og peker hode på denne:
        int teller = 0; // hjelppevariabel

        for (int i = 0; i < a.length; i++) { // kjører igjennom tabellen a
            if (a[i] != null) { // og så lenge det finnes en verdi i a
                p.verdi = a[i]; // så setter vi den verdien lik p sin verdi
                hode = p; // og peker hode-pekeren på p for å si at det er første node i listen
                teller++; // og plusser på en
                antall++; // og legger på 1 i antall noder i antall-tellingen
                break;
            }
            else {
                teller++; // hvis det ikke finnes verdi i listen a, så legges det til en
            }
        }
        // Hvis det bare er 1 node i listen a, så settes både hale- og hode-pekerene på denne noden
        if (a.length == 1) {
            hale = hode;
        }
        else { // Hvis det er flere enn 1 node i listen, så må vi sette hale-pekeren:
            for (int i = teller; i < a.length; i++) { // kjører igjennom listen a med hjelpevariabelen (som er 1)
                if (a[i] != null) { // og sjekker at hvis det finnes verdi(er) i listen a
                    Node q = new Node(a[i]); // så oppretter vi en ny node q og setter verdien på denne
                    q.forrige = p; // setter forrige-pekeren til q til å peke på p
                    p.neste = q; // og neste-pekeren til p til å peke på q, slik at det blir en dobbelt lenket liste
                    p = q; // p peker på q
                    hale = q; //og setter noden q til å være hale - dvs den siste i listen
                    antall++;
                }
            }
        }
    }

    //oppgave 3b
    public Liste<T> subliste(int fra, int til) {
        //Denne kontrollmetoden kan da kalles med​ antall​,f​ra​ og​ til​ som argumenter.
       fraTilKontroll(antall,fra, til);
        //lager en dobbeltlenketListe
        DobbeltLenketListe <T> liste=new DobbeltLenketListe<>();

        //lager en ny node som angir verdien til fra:
        Node<T> nodeFra= finnNode(fra);

        //finne antall elementer i listen:
        int antall= til-fra;
        //husk at et tomt intervall er lovlig. Det betyr at vi får en tom liste.
        if(antall<1){
            return new DobbeltLenketListe<>();
        }

        while(antall>0){
            // Variabelen ​endringer​ skal være 0. Her kan alle metoder brukes - også ​leggInn​()​.

            liste.leggInn(nodeFra.verdi);
            nodeFra= nodeFra.neste;
            //Pass på at variablen ​antall ​ i den listen som metoden​ subliste(​)​returnerer,får korrekt verdi.
            antall--;

        }
        //Den skal returnere en liste (en instans av klassen ​DobbeltLenketListe​)
        // som inneholder verdiene fra intervallet [fra:til> i «vår» liste.
        return liste;

    }
    //oppgave 3b hentet fra kompendiet
    private static void fraTilKontroll(int antall, int fra, int til){

        //Her må det først sjekkes om indeksene ​fra​ og ​til​ er lovlige.
        //Hvis ikke, skal det kastes unntak slik som i metoden ​fratilKontroll​()​.
        //legg derfor den inn som en privat metode i klassen ​DobbeltLenketListe​
        // og bytt ut ArrayIndexOutOfBoundsException​ med ​IndexOutOfBoundsException siden vi ikke har noen tabell (array) her.
        //Bytt også ut ordet ​tablengde​ med ordet ​antall.​

        if (fra < 0)                                  // fra er negativ
        throw new IndexOutOfBoundsException
                ("fra(" + fra + ") er negativ!");

        if (til > antall)                          // til er utenfor tabellen
            throw new IndexOutOfBoundsException
                    ("til(" + til + ") > antall(" + antall + ")");

        if (fra > til)                                // fra er større enn til
            throw new IllegalArgumentException
                    ("fra(" + fra + ") > til(" + til + ") - illegalt intervall!");

    }

    @Override
    public int antall() {
        // Returnere antallet verdier i listen
        return antall;
    }

    @Override
    public boolean tom() {
        // Returnere true eller false avhengig av om listen er tom eller ikke
        if (antall == 0) {
            return true;
        }
        return false;
    }

    // Oppgave 2b
    @Override
    public boolean leggInn(T verdi) {

        // Null-verdier ikke tillatt - bruk en requireNonNull.metode fra klassen Objects
        Objects.requireNonNull(verdi,"Null-verdier ikke tillatt");

        // innleggingsmetoden: legge en ny node med oppgitt verdi bakerst i listen og returnere true
        /*
        Skille mellom to tilfeller:
        1. at listen på forhånd er tom
        2. at den ikke er tom
        - I en tom liste skal både hode og hale være null (og antall lik 0)
        - I tilfelle 1 skal både hode og hale etter innleggingen peke på den nye noden
            (både forrige-peker og neste-peker i noden skal da være null
        - I tilfelle 2 er det kun hale-pekeren som skal endres etter innleggingen.
            Pass da på at forrige-peker og neste-peker i den nye noden og i den noden
            som opprinnelig lå bakerst, får korrekte verdier.
        -  Husk at antallet må økes etter en innlegging.
        - Det samme med variabelen endringer.
        - Metoden skal returnere true
         */

        Node<T> p = new Node(verdi); // lager en ny node, kalt p

        // 1. Hvis listen på forhånd er tom
        if (antall == 0 && hode == null && hale == null) { // ssjekke om antall er lik 0 og hode og hale er null
            hode = p; // både hode og hale skal peke på den nye noden p
            hale = p;
            p.neste = p.forrige = null;
            antall++;
            endringer++;
        }
        // 2. Hvis listen ikke er tom
        else {
            hale.neste = p; // setter inn p bakerst: p lik hale sin neste node - dvs den bakerste noden
            p.forrige = hale; // setter så hale lik p sin forrige - dvs neste bakerste noden
            hale = p; // setter så p lik hale, så den nye hale-pekeren peker på den nye noden å
            antall++;
            endringer++;
        }
        return true;
    }


    //oppgave5
    @Override
    public void leggInn(int indeks, T verdi) {
        // Den skal legge ​verdi​ inn i listen slik at den får indeks/posisjon ​indeks​.
        //Husk at negative indekser og indekser større enn antall
        // er ulovlige (indekser fra og med 0 til og med antall er lovlige).

        //kaster et avvik hvis verdi er null
        Objects.requireNonNull(verdi, "Det er ikke tillatt med null.");
        Node <T> forandretNode= new Node<T>(verdi,null, null);

        if(indeks<0 || indeks>antall){
            throw new IndexOutOfBoundsException("indeks: "+ indeks+ " må være mer enn 0 og må være mindre enn antall" );
        }
        else if (hode == null && hale == null)
        {
            hode = forandretNode;
            hale = forandretNode;
        }
        else if (indeks == 0)
        {
            forandretNode.neste = hode;
            hode.forrige = forandretNode;
            hode = forandretNode;
        }
        else if (indeks == antall) {
            forandretNode.forrige = hale;
            hale.neste = forandretNode;
            hale = forandretNode;
        }
        else
        {
            Node<T> høyreNode = finnNode(indeks);
            forandretNode.forrige = høyreNode.forrige;
            forandretNode.neste = høyreNode;
            høyreNode.forrige = forandretNode;
            forandretNode.forrige.neste = forandretNode;
        }
        // Her må du passe på de fire tilfellene
        // 1) listen er tom,
        // 2) verdien skal legges først,
        // 3) verdien skal legges bakerst og
        // 4) verdien skal legges mellom to andre verdier.
        //Sørg for at neste- og forrige-pekere får
        // korrekte verdier i alle noder
        //Spesielt skal forrige-peker i den første noden være null og neste-peker i den siste noden være null.

        antall++;
        endringer++;




    }

    //oppgave 4
    @Override
    public boolean inneholder(T verdi) {


        //Den skal returnere true hvis listen inneholder ​verdi​ og returnere false ellers.
        // Her lønner det seg å bruke et kall på metoden ​indeksTil​ som en del av koden.
         if(indeksTil(verdi) != -1){
            return true;
         }
         else{
             return false;
         }


    }

    @Override
    //oppgave 3a
    public T hent(int indeks) {
        //skal lages ved ved å bruke ​finnNode(​)​

        indeksKontroll(indeks, false);
        return finnNode(indeks).verdi;

        //Pass på at ​indeks ​sjekkes.

        //Bruk metoden ​indeksKontroll(​)​ som arves fra​ Liste​(bruk ​false​ som parameter).

    }
    //oppgave 4
    @Override
    public int indeksTil(T verdi) {

        // og returnere -1 hvis den ikke finnes.
        //Her skal det ikke kastes unntak hvis​ verdi​ er n​ull​. men -1

        Node<T> node=hode;

            for(int indeks=0; indeks<antall; indeks++, node=node.neste ){

                if(node.verdi.equals(verdi)){
                    //Den skal returnere indeksen/posisjonen til ​verdi​ hvis den finnes i listen

                    //Hvis ​verdi​ forekommer flere ganger, skal indeksen til den første av dem (fra venstre) returneres.
                    return indeks;
                }
            }

        return -1;

    }

    @Override
    //oppgave 3a
    public T oppdater(int indeks, T nyverdi) {
        //Husk at indeks må sjekkes,
        indeksKontroll(indeks, false);

        // at null-verdier ikke skal kunne legges inn og at variabelen ​endringer​ skal økes.

        if(nyverdi==null){
            throw new NullPointerException("Null-verdier er ikke lov.");
        }

        //skal erstatte verdien på plass ​indeks med ​nyverdi​ og returnere det som lå der fra før.
        //lager en ny Node og finner noden til den gitte indeksen.
        Node<T> nodeEksempel= finnNode(indeks);
        //lagrer den gamle verdien
        T gammelVerdi = nodeEksempel.verdi;
        //lager ny verdi
        nodeEksempel.verdi=nyverdi;

        //skal returnere verdien til verdien som var der før.
        return gammelVerdi;

    }

    // Oppgave 6
    @Override
    public boolean fjern(T verdi) {
        // Skal fjerne VERDI fra listen og så returnere true
        // Hvis det er flere forekomster av verdier er det den første av dem (fra venstre) som skal fjernes
        // Lag metoden så effektiv som mulig, må derfor kodes direkte og IKKE ved hjelp av indeksTil(T verdi) og fjern(int indeks)
        // Hvis VERDI ikke er i listen, skal metoden returnere false
        // Her skal det ikke kastes unntak hvis VERDI er null, metoden skal
        // i stedet for returnere false.
        if (verdi == null) {
            return false;
        }

        // Hjelpevariabel
        Node<T> p = hode; // Lager en ny node som er første node (hode)

        // Fjerne første
        if (p.verdi.equals(verdi) && p.neste != null) { // hvis peker sin verdi er lik verdien vi leter etter og det er en verdi etter neste
            hode = p.neste; // så skal neste node være nye hode - dvs den nye første noden i listen
            hode.forrige = null; // også setter vi den noden som tidligere var først til null - den tas dermed bort
            antall--; // tar bort 1 i antall, siden en node nå er fjernet
            endringer++;
            return true; // return true for VERDI er nå fjernet
        }
        else if (p.verdi.equals(verdi) && p.neste == null) { // hvis det kun er én node i listen, for det finnes ingen neste noder
            hode = null; // skal hode og hale settes lik null, tar dermed bort den eneste noden som er der
            hale = null;
        }

        // Fjerne mellomste
        p = hode.neste; // setter pekeren til å peke på noden etter den første noden (dvs node på indeks 1)
        for (; p != null; p = p.neste) { // kjører igjennom med forløkke: så lenge p ikke er null (dvs neste node eksisterer) så hopper vi til neste node
            if(p.verdi.equals(verdi)) { // og p-pekeren hopper bortover til den finner verdien vi leter etter (når p sin verdi er lik verdien vi leter etter)
                p.forrige.neste = p.neste; // da setter vi ny neste ved å ta p sin forrige sin neste til å være p sin neste (hopper over p)
                p.neste.forrige = p.forrige; // samme med forrige: tar p sin neste sin forrige til å være lik p sin forrige (hopper igjen over p)
                // nå har ikke p noen neste- eller forrige pekere på seg - den er fjernet
                antall--; //
                endringer++;
                return true;
            }
        }

        // Fjerne siste
        p = hale; // setter pekeren på siste node
        if (p.verdi.equals(verdi)) { // hvis verdien der p peker er lik verdien vi leter etter
            hale = p.forrige; // så skal vi sette hale lik p sin forrige (som blir den nye siste noden)
            hale.neste = null; // og hale sin neste til lik null - vi tar da bort siste noden
            antall--;
            endringer++;
            return true;
        }
        return false;
    }

    // Oppgave 6
    @Override
    public T fjern(int indeks) {
        // Sjekke indeks
        indeksKontroll(indeks, false);

        // Skal fjerne (og returnere) verdien på posisjon INDEKS
        // Indeks må først sjekkes
        // Lag metoden så effektiv som mulig

        Node<T> p = hode; // lager en node p som er hode (første node)
        T verdi; // verdien vi leter etter

        // Fjerne første (hvis kun 1 node + hvis flere noder)
        if (indeks == 0) { // hvis indeks er lik 0
            verdi = p.verdi; // setter så p noden sin verdi til å være lik verdien vi har funnet
            if (p.neste != null) { // og hvis p noden kan peke på en neste-node
                hode = p.neste; // da setter vi neste noden til p som hode
                hode.forrige = null; // og fjerner den første noden i listen
            }
            else { // hvis p ikke kan peke på noen neste node, så er det kun 1 node i listen
                hode = null; // og da setter vi både hode og hale til null - dvs den ene noden fjernes
                hale = null;
            }
            antall--;
            endringer++;
            return verdi;
        }

        // Fjerne siste
        else if (indeks == antall-1) { // finner indeks: som er antall noder - 1 (dvs den nest bakerste noden)
            p = hale; // og setter hale-pekeren til å være lik p-pekeren
            verdi = hale.verdi; // og hale sin verdi til å være lik verdien
            hale = p.forrige; // også tar man p sin forrige (altså nest siste noden) til å være nye halen
            hale.neste = null; // også tar man hale sin neste - dvs siste noden til å være lik null - dvs den fjernes
            antall--;
            endringer++;
            return verdi;
        }

        // Fjerne mellomste
        else {
            for (int i = 0; i < indeks; i++) { // kjører igjennom listen med en for-løkke
                p = p.neste; // setter i hver runde p sin neste node til å være p (der pekeren peker) - p-pekeren flytter seg dermed hele tiden bortover mot høyre
            }
            verdi = p.verdi; // når vi har funnet verdien vi leter etter setter vi p sin verdi til denne
            p.forrige.neste = p.neste; // så fltter vi neste-pekerene: p sin neste blir = p sin forrige sin neste
            p.neste.forrige = p.forrige; // og p sin forrige blir = p sin neste sin forrige. Har dermed tatt bort neste- og forrige-pekerene fra noden p peker på
            antall--;
            endringer++;
            return verdi;
        }
    }

    // Oppgave 7
    @Override
    public void nullstill() {
        // Skal "tømme" listen og nulle alt slik at "søppeltømmeren" kan hente alt som ikke lenger brukes
        // Kod den på to måter - velg den som er mest effektiv (gjør tidsmålinger):
        /*
        1. måte: Start i hode og gå mot hale vha pekeren neste. For hver node "nulles"
            nodeverdien og alle nodens pekere. Til slutt settes både hode og hale til null,
            antall til 0 og endringer økes. Hvis du er i tvil om hva som det bes om her, kan
            du slå opp i kildekoden for metoden clear() i klassen LinkedList i Java.
        2. måte: Lag en løkke som inneholder metodekallet fjern(0) (den første noden fjernes)
            og som går inntil listen er tom
         */

        // Måte 1:
        Node<T> p = hode; // lager en ny node p som peker på hode-peker (første node)
        for (; p != null; p = p.neste) { // kjører gjennom listen, så lenge p (hode) ikke er null, hopper den til neste
            p = hode.neste; // setter hode sin neste til å være p
            p.neste = null; // og neste blir satt til null
            p.forrige = null; // og forrige blir satt til null
            antall = 0;
            endringer++;
        }
        hode = hale = null; // og setter både hode og hale til null når det kun er hode- og hale-pekere igjen

        // Måte 2:
        Node<T> q = hode;
        for (; q != null; q = q.neste) { // kjører igjennom listen, og så lenge q (lik hode) ikke er null så hopper den til neste
            fjern(0); // og bruker fjern-metoden til å fjerne den første noden inntill hele listen er tom
            endringer++;
        }
    }

    // Oppgave 2a
    @Override
    public String toString() {
        /*
        - Skal bruke StringBuilder (eller StringJoiner) til å bygge opp tegnstrengen
            og verdiene i listen finner du ved å travesere fra hode til hale vha
            neste-pekere
        - Skal returnere en tegnstreng med listens verdier
        - Hvis listen feks inneholder tallene 1,2 og 3, skal metoden returenre strengen:
            [1,2,3] og kun [] hvis liten er tom
        */

        // Lager en node som er hode (første node):
        Node<T> p = hode;

        StringBuilder tegnStreng = new StringBuilder(); // Begynner å bygge tegnstrengen
        tegnStreng.append("["); // lager første del av strengen med [

        if (antall == 0) {
            tegnStreng.append("]"); // hvis den er tom returnerer den bare []
            return tegnStreng.toString(); // og returnerer toString som er []
        }
        else { // hvis den ikke er 0
            tegnStreng.append(p.verdi); // legger den til p sin verdi
            p = hode.neste; // og p på neste node
            while (p != null) { // og hvis p ikke er null - altså hvis det er flere noder
                tegnStreng.append(", "); // setter den inn et komma for å skille flere verdier
                tegnStreng.append(p.verdi); // og setter inn neste node sin verdi, der pekeren p nå
                p = p.neste; // også p den på neste og sjekker om denne er null eller om der er flere noder i listen
            }
        }
        tegnStreng.append("]"); // avslutter tegnstrengen med ]
        return tegnStreng.toString();
    }

    // Oppgave 2a
    public String omvendtString() {
        /*
        - Skal returnere en tegnstreng på samme form som den toString() gir,
            men verdiene skal komme i omvendt rekkefølge
        - Skal finne verdiene i omvendt rekkefølge ved å travesere fra hale til hode vha
            forrige-pekere
        - hensikten ved omvendtString er å sjekke at forrige-pekerne er satt riktig
         */

        Node<T> p = hale;

        StringBuilder tegnStreng = new StringBuilder();
        tegnStreng.append("[");

        if (antall == 0) {
            tegnStreng.append("]");
            return tegnStreng.toString();
        }
        else {
            tegnStreng.append(p.verdi);
            p = p.forrige;
            while (p != null) {
                tegnStreng.append(", ");
                tegnStreng.append(p.verdi);
                p = p.forrige;
            }
        }
        tegnStreng.append("]");
        return tegnStreng.toString();
    }

    //oppgave 8b
    @Override
    public Iterator<T> iterator() {

        //Den skal returnere en instans av iteratorklassen.
        return new DobbeltLenketListeIterator();

    }

    //oppgave 8d
    public Iterator<T> iterator(int indeks) {

        //Det må først sjekkes at indeksen er lovlig.
        //Bruk metoden ​indeksKontroll​()​.
        indeksKontroll(indeks, false);

        //Deretter skal den ved hjelp av konstruktøren i punkt c) returnere en instans av iteratorklassen.
        return new DobbeltLenketListeIterator(indeks);

    }

    private class DobbeltLenketListeIterator implements Iterator<T> {
        private Node<T> denne;
        private boolean fjernOK;
        private int iteratorendringer;

        private DobbeltLenketListeIterator() {
            denne = hode;     // p starter på den første i listen
            fjernOK = false;  // blir sann når next() kalles
            iteratorendringer = endringer;  // teller endringer
        }
        //oppgave 8c
        private DobbeltLenketListeIterator(int indeks) {
            //en skal sette pekeren ​denne​ til den noden som hører til den oppgitte indeksen.
            //Resten skal være som i den konstruktøren som er ferdigkodet.

            //setter denne til den oppgitte indeksen
           denne = finnNode(indeks);

            //setter fjernOk til være false
            fjernOK = false;

            //setter iteratorendringer itl endringer
            iteratorendringer = endringer;
        }

        @Override
        public boolean hasNext() {
            return denne != null;
        }

        //oppgava 8a
        @Override
        public T next() {

            if (iteratorendringer != endringer) {
                //Hvis ikke, kastes en ​ConcurrentModificationException​.
                throw new ConcurrentModificationException("Det er ikke lovlig at endringer og iteratorendringer ikke er like");
            }
            if (!hasNext()) {
                ////Så en ​NoSuchElementException hvis det ikke er flere igjen i listen (dvs. hvis ​hasNext​()​ ikke er sann/true).
                throw new NoSuchElementException(" Det er ikke flere elementer i listen");
            }
            //Deretter settes ​fjernOK​ til sann/true, verdien til ​denne​ returneres og ​denne​ flyttes til den neste node.
            fjernOK = true;
            T verdi = denne.verdi;
            denne = denne.neste;
            return verdi;
            
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

    } // class DobbeltLenketListeIterator

    public static <T> void sorter(Liste<T> liste, Comparator<? super T> c) {
        throw new UnsupportedOperationException();
    }

    //Oppgave 3.
    private Node<T> finnNode(int indeks) {

        Node forerstCurrent=hode;
        Node bakerstCurrrent=hale;

        //Hvis indeks er mindre enn ​antall​/2,
        if (indeks <= antall / 2) {

            for (int i = 0; i < indeks; i++) {
                forerstCurrent = forerstCurrent.neste;
            }
            // så ​skal letingen etter noden starte fra hode og gå mot høyre ved hjelp av neste-pekere.
            return forerstCurrent;
            //hvis ikke
        } else {


            for (int i = antall - 1; i > indeks; i--) {
                bakerstCurrrent = bakerstCurrrent.forrige;
                // Hvis ikke, ​skal​ letingen starte fra halen og gå mot venstre ved hjelp av forrige-pekere.
            }

            return bakerstCurrrent;
            //legger til returnstatement.

        }
    }

}// class DobbeltLenketListe


