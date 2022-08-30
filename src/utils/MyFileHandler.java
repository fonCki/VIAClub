package utils;

import java.io.*;

/**
 * This class allows to interact with binary files, and text files.
 */

public class MyFileHandler {

   /**
    * It opens a new text file and rewrites the information. if the file does not exist, it is created.
    * @param fileName the name of the file
    * @param str the content in a text string
    * @throws FileNotFoundException file not found
    */
   public static void writeToTextFile(String fileName, String str) throws FileNotFoundException {
      PrintWriter writeToFile = null;
      try {
         FileOutputStream fileOutStream = new FileOutputStream(fileName, false);
         writeToFile = new PrintWriter(fileOutStream);
         writeToFile.println(str);
      } finally {
         if (writeToFile != null) {
            writeToFile.close();
         }
      }
   }

   /**
    * It opens a new binary file and rewrites the information. if the file does not exist, it is created.
    * @param fileName the name of the file.
    * @param obj the object to write.
    * @throws FileNotFoundException file not found
    */

   public static void writeToBinaryFile(String fileName, Object obj) throws FileNotFoundException, IOException {
      ObjectOutputStream writeToFile = null;

      try {
         FileOutputStream fileOutStream = new FileOutputStream(fileName);
         writeToFile = new ObjectOutputStream(fileOutStream);

         writeToFile.writeObject(obj);
      } finally {
         if (writeToFile != null) {
            try {
               writeToFile.close();
            } catch (IOException e) {
               System.out.println("IO Error closing file " + fileName);
            }
         }
      }
   }

   /**
    * Read the information from a Binary file, and return the object read.
    * @param fileName the name of the file
    * @return the information of the file, in a object.
    * @throws FileNotFoundException file not found
    * @throws IOException In Out Error
    * @throws ClassNotFoundException class not found.
    */
   public static Object readFromBinaryFile(String fileName) throws FileNotFoundException, IOException, ClassNotFoundException {
      Object obj = null;
      ObjectInputStream readFromFile = null;
      try {
         FileInputStream fileInStream = new FileInputStream(fileName);
         readFromFile = new ObjectInputStream(fileInStream);
         try {
            obj = readFromFile.readObject();
         } catch (EOFException eof) {
         }
      } finally {
         if (readFromFile != null) {
            try {
               readFromFile.close();
            } catch (IOException e) {
               System.out.println("IO Error closing file " + fileName);
            }
         }
      }
      return obj;
   }
}