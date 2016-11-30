package zakhse.algo.kdz;

import zakhse.Methods;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {

    public static void main(String[] args) {
        int numberOfCopies;
        if (args.length != 1 || !Methods.tryParseInt(args[0])) {
            System.out.println("Run program with the only positive integer argument - a number of copies of each type of test files");
            return;
        }
        numberOfCopies = Integer.parseInt(args[0]);

        for (int copy = 0; copy < numberOfCopies; copy++) {
            for (int type = 1; type <= 3; type++) {
                for (int size = 20; size <= 100; size += 20) {
                    List<String> temp = new ArrayList<>();
                    temp.add(GenerateFile(size * 1024, type));
                    try {
                        Files.write(new File(size + "Kb_" + type + "type_" + copy + "copy.txt").toPath(), temp, StandardCharsets.UTF_8, StandardOpenOption.CREATE);
                    } catch (IOException e) {
                        System.out.println("File with copy = " + copy + ", type = " + type + ", size = " + size + "Kb is failed");
                        System.out.println(e.getMessage());
                    }
                }

                for (int size = 1; size <= 3; size++) {
                    List<String> temp = new ArrayList<>();
                    temp.add(GenerateFile(size * 1024 * 1024, type));
                    try {
                        Files.write(new File(size + "Mb_" + type + "type_" + copy + "copy.txt").toPath(), temp, StandardCharsets.UTF_8, StandardOpenOption.CREATE);
                    } catch (IOException e) {
                        System.out.println("File with copy = " + copy + ", type = " + type + ", size = " + size + "Mb is failed");
                        System.out.println(e.getMessage());
                    }
                }
            }
        }
        System.out.println("Done!");
    }

    private static Random rnd = new Random();

    private static String GenerateFile(int bytes, int type) {

        List<Character> characters = new ArrayList<>();

        // For 1st, 2nd and 3rd type of tests
        if (type >= 1) {
            for (int i = 'a'; i <= 'z'; i++)
                characters.add((char) i);
            characters.add(' ');
            for (int i = 'A'; i <= 'Z'; i++)
                characters.add((char) i);
        }

        // For 2nd and 3rd type of tests
        if (type >= 2) {
            for (int i = 'а'; i <= 'я'; i++)
                characters.add((char) i);
            for (int i = 'Я'; i <= 'Я'; i++)
                characters.add((char) i);
        }

        // For 3rd type of tests
        if (type == 3) {
            for (int i = '!'; i <= '&'; i++) // ! " # $ % &
                characters.add((char) i);
            for (int i = '('; i <= '/'; i++) // ( ) * + , - . /
                characters.add((char) i);
            for (int i = ':'; i <= '@'; i++) // : ; < = > ? @
                characters.add((char) i);
            for (int i = '“'; i <= '‟'; i++) // “ ” „ ‟
                characters.add((char) i);
            characters.add('[');
            characters.add(']');
            characters.add('{');
            characters.add('}');
            characters.add('~');
        }

        StringBuilder str = new StringBuilder();
        for (int i = 0; i < bytes; ) {
            Character newChar = characters.get(rnd.nextInt(characters.size()));
            str.append(newChar);
            i += newChar.toString().getBytes(StandardCharsets.UTF_8).length;
        }
        return str.toString();
    }
}
