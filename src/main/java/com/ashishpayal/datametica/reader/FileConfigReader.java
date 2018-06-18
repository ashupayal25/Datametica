package com.ashishpayal.datametica.reader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class FileConfigReader implements IConfigReader {
	private String fileName;

	public FileConfigReader(String fileName) {
		this.fileName = fileName;
	}
	
	public Map<Integer,Integer> read() throws IOException {

		Map<Integer,Integer> denom = new HashMap<Integer, Integer>();
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(fileName));
			String line = reader.readLine();
			while (line != null) {
				int denomination = Integer.parseInt(line.split(" ")[0]);
				int availDenom = Integer.parseInt(line.split(" ")[1]);
				denom.put(denomination,availDenom);
				line = reader.readLine();
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		}
		return denom;
	}
	
}
