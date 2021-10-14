package scanner;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Scanner;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Erick Capilla
 */
public class Main {
    public static void main(String[] args) {
    ArrayList<String> lines = new ArrayList<>();

    Scanner sc = new Scanner(System.in);
    System.out.print("Ruta/arcivo: ");
    String source = sc.nextLine();

    System.out.print("Salida (sin extension): ");
    String out = sc.nextLine();

    readFile(source, lines);

    String txt = "";
    int i = 1;
    
    for(String line : lines) {        
        
        File file = new File("file.txt");
        PrintWriter write;
        try {
            write = new PrintWriter(file);
            write.print(line);
            write.close();
            
            Reader reader = new BufferedReader(new FileReader("file.txt"));
          
            Lexer lexer = new Lexer(reader);
            txt += "Linea " + i + ":\n";
            while(true) {
                Tokens tokens = lexer.yylex();
                if(tokens == null) {
                    writeFile(out, txt);
                    file.delete();
                    break;
                }
                switch(tokens) {
                    case ERROR:
                        txt += "Simbolo no definido\n";
                        break;
                    case Identificador: case Reservada: case Signo: 
                    case Simbolo: case Operador: case Numero:
                        txt += "\t" + tokens + ": " + lexer.lexeme + "\n";
                        break;
                    default:
                        txt += "Token: " + tokens + "\n";
                        break;
                }
            }
            i++;           
        } catch(Exception e) { e.printStackTrace(); }
    }
    
    System.out.println(txt);
  }

  public static void readFile(String source, ArrayList<String> lines) {
    String line;

    try {
      FileReader file = new FileReader(source);
      BufferedReader reader = new BufferedReader(file);

      while ((line = reader.readLine()) != null) {
        if(!line.equalsIgnoreCase("")) lines.add(line.trim());
      }

      reader.close();
    } catch (Exception e) { e.printStackTrace(); }
  }
  
  public static void writeFile(String out, String txt) {
      try {
          FileWriter file = new FileWriter(out + ".lex");
          PrintWriter writer = new PrintWriter(file);
          
          writer.println(txt);
          file.close();
      } catch (Exception e) { e.printStackTrace(); }
  }
}
