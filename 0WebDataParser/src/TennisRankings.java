import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import javax.swing.JOptionPane;

public class TennisRankings {
	/*
	 * Goes on to www.tennis.com/rankings/ATP and gives the ranking of the input player. 
	 */
	public static void main(String[] args) throws IOException {
		URL link = new URL("http://www.tennis.com/rankings/ATP/");
		URLConnection connection = link.openConnection();
		BufferedReader  reader= new BufferedReader(new InputStreamReader(connection.getInputStream()));
		String line;
		StringBuffer pageContent = new StringBuffer();
		while((line = reader.readLine()) != null) {
			pageContent.append(line + "\n");
		}
		while(true) {
			String name = JOptionPane.showInputDialog("Player Name?");
			int ranking = pageContent.indexOf(name);
			if (ranking == -1) {
				System.out.println("NOT FOUND");
			} else {
				int pointValueStart = pageContent.indexOf("s\">",ranking) + 3;
				int pointValueEnd = pageContent.indexOf("<",pointValueStart);
				String points = pageContent.substring(pointValueStart, pointValueEnd);
				
				int crvalueStartIndex = pageContent.indexOf("t-rank\">", ranking) + 8;
				int crvalueEndIndex = pageContent.indexOf("<", crvalueStartIndex);
				String currentRank = pageContent.substring(crvalueStartIndex, crvalueEndIndex);
				
				int prvalueStart = pageContent.indexOf("v-rank\">",ranking) + 8;
				int prvalueEnd = pageContent.indexOf("<", prvalueStart);
				String prevRank = pageContent.substring(prvalueStart, prvalueEnd);
				System.out.println("Player Name: " + name + "\n" + "Points: " + points +"\n" + "Current Rank: " + currentRank + "\n" 
				+ "Prev Rank: " + prevRank);
				break;
			}
		}
	}
}
