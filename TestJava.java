import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FiFty {
    public static void main(String[] pArgs) throws IOException {
        // Chemin du fichier texte
        String fileName = "text.txt";
        File file = new File(fileName);
        ArrayList<String> arrdate = new ArrayList<String>();
        // itération à travers le fichier et lecture ligne par ligne
        try (Stream linesStream = Files.lines(file.toPath())) {
            linesStream.forEach(line -> {
                // Remplacement du nom du mois par son numéro (ex : août sera changé à 08)
                String stlin = line.toString()
                        .replaceAll("(?:)January(?:)", "01")
                        .replaceAll("(?:)February(?:)", "02")
                        .replaceAll("(?:)March(?:)", "03")
                        .replaceAll("(?:)April(?:)", "04")
                        .replaceAll("(?:)May(?:)", "05")
                        .replaceAll("(?:)June(?:)", "06")
                        .replaceAll("(?:)July(?:)", "07")
                        .replaceAll("(?:)August(?:)", "08")
                        .replaceAll("(?:)September(?:)", "09")
                        .replaceAll("(?:)October(?:)", "10")
                        .replaceAll("(?:)November(?:)", "11")
                        .replaceAll("(?:)December(?:)", "12");

                //  Créer des motifs de date

                // Pattern rx1 ex: 2000 (Year) - 02 (Month) - 01 (Day) YYYY(-,/,etc..) MM (-,/,etc..) DD
                Pattern  rx1 = Pattern.compile("([0-9]{4}).([0-9]{1,2}).([0-9]{1,2})");
                // Pattern rx2 ex: 01 (Day) 02 (Month) 2000 (Year) DD MM YYYY
                Pattern  rx2 = Pattern.compile("([0-9]{1,2}).([0-9]{1,2}).([0-9]{4})");
                // Pattern rx3 ex: 02 (Month) 01 (Day), 2000 (Year) MM DD, YYYY
                Pattern  rx3 = Pattern.compile("([0-9]{1,2})\\s([0-9]{1,2}),\\s([0-9]{4})");

                // Trouver des correspondances pour chaque motif

                Matcher matcher1 = rx1.matcher(stlin);

                while (matcher1.find()) {
                    String exp1;
                    if(Integer.parseInt(matcher1.group(2))>12){
                        exp1 = matcher1.group(1)+"-"+ matcher1.group(3)+"-"+ matcher1.group(2);

                    }else if (Integer.parseInt(matcher1.group(3))>12) {
                        exp1 = matcher1.group(1)+"-"+ matcher1.group(2)+"-"+ matcher1.group(3);

                    }else{
                        // J'utilise le format de date standard AAAA MM JJ quand on ne sait pas lequel est MM
                        exp1 = matcher1.group(1)+"-"+ matcher1.group(2)+"-"+ matcher1.group(3);
                    }
                    //Ajout de la date à la ArrayList
                    arrdate.add(exp1);
                }

                Matcher matcher2 = rx2.matcher(stlin);
                while (matcher2.find()) {
                    String exp2;
                    if(Integer.parseInt(matcher2.group(2))>12){
                        exp2 = matcher2.group(3)+"-"+ matcher2.group(1)+"-"+ matcher2.group(2);

                    }else if (Integer.parseInt(matcher2.group(1))>12) {
                        exp2 = matcher2.group(3)+"-"+ matcher2.group(2)+"-"+ matcher2.group(1);

                    }else{
                        // J'utilise le format de date standard JJ MM MM AAAA quand on ne sait pas lequel est MM
                        exp2 = matcher2.group(3)+"-"+ matcher2.group(1)+"-"+ matcher2.group(2);

                    }
                    //Ajout de la date à la ArrayList
                    arrdate.add(exp2);
                }

                Matcher matcher3 = rx3.matcher(stlin);
                while (matcher3.find()) {
                    String exp3;
                    if(Integer.parseInt(matcher3.group(1))>12){
                        exp3 = matcher3.group(3)+"-"+ matcher3.group(2)+","+ matcher3.group(1);

                    }else if (Integer.parseInt(matcher3.group(3))>12) {
                        exp3 = matcher3.group(3)+"-"+ matcher3.group(1)+"-"+ matcher3.group(2);

                    }else{arraydate
                        // J'utilise le format de date standard MM JJ JJ AAAA quand on ne sait pas lequel est MM
                        exp3 = matcher3.group(3)+"-"+ matcher3.group(1)+"-"+ matcher3.group(2);
                    }
                    //Ajout de la date à la ArrayList
                    arrdate.add(exp3);
                }
            });countFrequencies(arrdate);
        }
    }
    // Fréquences de comptage de chaque élément de la ArrayList
    public static void countFrequencies(ArrayList<String> list)
    {

        TreeMap<String, Integer> tmap = new TreeMap<String, Integer>();
        for (String t : list) {
            Integer c = tmap.get(t);
            tmap.put(t, (c == null) ? 1 : c + 1);
        }

        for (Map.Entry m : tmap.entrySet())
            System.out.println("Frequency of " + m.getKey() + " is " + m.getValue());
    }

}
