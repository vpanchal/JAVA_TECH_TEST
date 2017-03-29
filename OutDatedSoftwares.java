/*You are in Section 6: Out of Date Software, question 1 of 1. This is the last section.

Programming question: Find out of Date Servers from the list of the servers.

Take input file as serverList.txt. There are multiple servers in data center. All the software information installed on multiple servers is saved in this file.

For example, software information saved in the file is as follows.

Server1, Database, MySQL, 5.5
Server2, Database, MySQL, 5.1
Server3, OS, Ubuntu, 10.04
Server1, OS, Ubuntu, 12.04
Server2, OS, Ubuntu, 12.04
Server3, Language, Python, 2.6.3

This file indicates that Server1, has version 5.5 of MySQL installed, and Server2 has version 5.1 installed, and Server3 has version 10.04 of Ubuntu installed. For this program that all version numbers are of the form X.Y or X.Y.Z where X, Y, and Z are made up of only digits.

Write a program that reads this file, and prints a list of server names that have at least one software installation that is not the latest version.

Thus, in this case, the output of your program should be:

Server2
Server3

Priority will be given to the correct program. The program should be efficient and should be handling various corner cases.

Note: While writing a program, consider input file is saved in the current directory where actual program code is saved and running.

Don't print anything else other than required output.*/

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class OutDatedSoftwares {
	public static void main(String [] args) {

        // The name of the file to open.
        String fileName = "input.txt";
        
        // This will reference one line at a time
        String line = null;

        try {
        	//This is used to check the current directory file.
            File f = new File(fileName);
            // FileReader reads text files in the default encoding.
            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader = 
                new BufferedReader(new FileReader(f));

            Map<String,Integer> mapObje = new HashMap<String,Integer>();
            Map<String, List<String>> mapObject = new HashMap<>();
            
            while((line = bufferedReader.readLine()) != null) {
               String[] strArray = line.split(",");
               if(mapObje.containsKey(strArray[2])) {
            	   mapObje.put(strArray[2], mapObje.get(strArray[2]) +1);
            	   List<String> strList = mapObject.get(strArray[2]);
            	   strList.add(line);
            	   mapObject.put(strArray[2], strList);
            	   strList = null;
               }else {
            	   mapObje.put(strArray[2],1);
            	   List<String> strList = new ArrayList<>();
            	   strList.add(line);
            	   mapObject.put(strArray[2], strList);
            	   strList = null;
               }
               
            }
            // Always close files.
            bufferedReader.close();      
            
            List<String> listOfServerName = new ArrayList<>();
            for (Map.Entry<String, Integer> entry : mapObje.entrySet()) {
            	if(entry.getValue() > 2) {
            		List<String> strList = mapObject.get(entry.getKey());
            		listOfServerName.add(getServerName(strList));
            	}
            }
            
            //Write File
            // Assume default encoding.
            FileWriter fileWriter =
                new FileWriter("output.txt");

            // Always wrap FileWriter in BufferedWriter.
            BufferedWriter bufferedWriter =
                new BufferedWriter(fileWriter);

            // Note that write() does not automatically
            // append a newline character.
            for(String str:listOfServerName) {
            	 bufferedWriter.write(str);
            }
            // Always close files.
            bufferedWriter.close();
        }
        catch(FileNotFoundException ex) {
            System.out.println(
                "Unable to open file '" + 
                fileName + "'");                
        }
        catch(IOException ex) {
            System.out.println(
                "Error reading file '" 
                + fileName + "'");                  
            // Or we could just do this: 
            // ex.printStackTrace();
        }
    }
	
	/**
	 * Gets the server name.
	 *
	 * @param strList the str list
	 * @return the server name
	 */
	private static String getServerName(List<String> strList) {
		
		List<String> listOfServerName = new ArrayList<String>();
        List<String> listOfSoftwareNames = new ArrayList<String>();
        List<Double> listOfSoftwareVersions = new ArrayList<Double>();
		
		for (Iterator iterator = strList.iterator(); iterator.hasNext();) {
			String string = (String) iterator.next();
			String[] strArray = string.split(",");
			listOfServerName.add(strArray[0].trim());
            listOfSoftwareNames.add(strArray[2].trim());
            listOfSoftwareVersions.add(Double.valueOf(strArray[3].trim().substring(0, 2)));
		}
        int minIndex = listOfSoftwareVersions.indexOf(Collections.min(listOfSoftwareVersions));
        System.out.println(minIndex + " Server Name :: " + listOfSoftwareNames.get(minIndex));
		return listOfSoftwareNames.get(minIndex);
	}
}
