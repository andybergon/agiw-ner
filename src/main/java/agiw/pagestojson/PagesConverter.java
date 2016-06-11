package agiw.pagestojson;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import utils.PropertyFactor;

public class PagesConverter {

	public static void main(String[] args) throws IOException {
		System.out.println("Start converting pages to JSON...");
		convertPagesToJson();
	}

	public static void convertPagesToJson() {
		PropertyFactor pf = new PropertyFactor();
		File dir = new File(pf.getStoragePath());
		File[] directoryListing = dir.listFiles();
		Arrays.sort(directoryListing);

		File groupFolder = new File(pf.getJsonPagesPath());
		String groupPath = groupFolder.getAbsolutePath();

		if (directoryListing != null) {
			for (File child : directoryListing) {
				String lastName, firstName;

				String jsonFileName = child.getName();

				int httpIndex = jsonFileName.indexOf("http");
				String completeName = jsonFileName.substring(0, httpIndex);

				if (completeName.contains(" ")) { //double surname
					lastName = completeName.split(" ")[0].toLowerCase().replace("_", "_");
					firstName = completeName.split(" ")[1].toLowerCase().replace("_", "");

				} else {
					lastName = completeName.split("_")[0].toLowerCase();
					firstName = completeName.split("_")[1].toLowerCase();
				}

				File personDirectory = new File(groupPath, firstName + "_" + lastName);

				if (!personDirectory.exists()) {
					personDirectory.mkdir();
				}

				jsonFileName = changeFileExtension(jsonFileName, "json");
				String jsonFileAbsolutePath = personDirectory.getPath() + "/" + jsonFileName;
				File jsonFile;

				if (jsonFileName.length() > 255) {
					System.out.println("file name too long, renaming: " + jsonFileAbsolutePath);
					continue;
					//					jsonFileName = jsonFileName.substring(0, 250).concat(".json");
					//					jsonFile = new File(personDirectory.getPath(), jsonFileName);
				} else {
					jsonFile = new File(personDirectory.getPath(), jsonFileName);
				}

				String url = jsonFileName.substring(httpIndex).replace(".json", "");

				try {
					String urlOriginal = URLDecoder.decode(url, "UTF-8");
					String html = readFile(child.getPath(), StandardCharsets.UTF_8);

					writeToJsonFile(jsonFile, urlOriginal, html);

				} catch (UnsupportedEncodingException e) {
					System.out.println("skipping line for exception");
					e.printStackTrace();
				} catch (IOException e) {
					System.out.println("skipping line for exception");
					e.printStackTrace();
				}

			}
		} else {
			System.out.println("Storage Path not setted properly, it should be a directory!");
			// Handle the case where dir is not really a directory.
			// Checking dir.isDirectory() above would not be sufficient
			// to avoid race conditions with another process that deletes
			// directories.
		}
	}

	@SuppressWarnings("unused")
	private static boolean hasThreePartName(String jsonFileName) {
		int httpIndex = jsonFileName.indexOf("http");
		String completeName = jsonFileName.substring(0, httpIndex);
		int underscoreCount = completeName.length() - completeName.replace("_", "").length();

		return underscoreCount >= 3;
	}

	private static void writeToJsonFile(File jsonFile, String url, String html) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode node = mapper.createObjectNode();
		node.put("url", url);
		node.put("html", html);
		mapper.writer().writeValue(jsonFile, node);
	}

	private static String readFile(String path, Charset encoding) throws IOException {
		byte[] encoded = Files.readAllBytes(Paths.get(path));
		return new String(encoded, encoding);
	}

	private static String changeFileExtension(String sourceFile, String newExtension) {
		String target;
		String currentExtension = getFileExtension(sourceFile);

		if (currentExtension.equals("")) {
			target = sourceFile + "." + newExtension;
		} else {
			target = sourceFile.replaceFirst(Pattern.quote("." + currentExtension) + "$",
					Matcher.quoteReplacement("." + newExtension));

		}
		return target;
	}

	private static String getFileExtension(String f) {
		String ext = "";
		int i = f.lastIndexOf('.');
		if (i > 0 && i < f.length() - 1) {
			ext = f.substring(i + 1);
		}
		return ext;
	}

}
