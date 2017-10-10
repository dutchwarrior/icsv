package com.eisc.football;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.servlet.http.Part;


@Named
@SessionScoped
public class ConversationBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Logger log = Logger.getLogger(ConversationBean.class.getName());

	private Part file;
	private String fileContent;

	private List<String> list = new ArrayList<String>();

	public void upload() {
		Scanner scanner = null;
		try {

			scanner = new Scanner(file.getInputStream());

			while (scanner.hasNext()) {
				String line = scanner.nextLine();
				list.add(line);
			}

		} catch (IOException e) {
			log.log(Level.SEVERE, "Error during read file");
		} finally {
			if (scanner != null) {
				scanner.close();
			}
		}
	}

	public Part getFile() {
		return file;
	}

	public void setFile(Part file) {
		this.file = file;
	}

	public void findResult() {
		if (!list.isEmpty()) {

			int i = 0;
			Map<String, Number> clubGool = new HashMap<String, Number>();
			for (String line : list) {
				if (i != 0 && line.length() > 53) {
					String teamName = line.substring(8, 23).trim();
					String num1s = line.substring(40, 46).trim();
					String num2s = line.substring(49, 53).trim();

					if (!Util.isNumeric(num1s) || !Util.isNumeric(num2s)) {
						log.log(Level.WARNING, "line don't contains number charapters " + line);
						log.log(Level.WARNING, "this line is skipped!");
						continue;
					}

					int num1 = Integer.parseInt(num1s.trim());
					int num2 = Integer.parseInt(num2s.trim());

					clubGool.put(teamName, Math.abs(num1 - num2));

				}
				i = 1;
			}

			if (!clubGool.isEmpty()) {
				Entry<String, Number> min = null;
				for (Entry<String, Number> entry : clubGool.entrySet()) {
					if (min == null || min.getValue().intValue() > entry.getValue().intValue()) {
						min = entry;
					}
				}

				fileContent = min.getKey();

			}
		}
	}

	public String getFileContent() {
		return fileContent;
	}

	public void setFileContent(String fileContent) {
		this.fileContent = fileContent;
	}

}
