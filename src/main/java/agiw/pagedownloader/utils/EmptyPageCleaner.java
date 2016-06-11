package agiw.pagedownloader.utils;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import utils.PropertyFactor;

public class EmptyPageCleaner {

	public static void main(String[] args) throws IOException {
		PropertyFactor pf = new PropertyFactor();
		System.out.println("Cleaning pages...");
		deleteEmptyFile(pf.getStoragePath());
	}

	public static void deleteEmptyFile(String storagePath) throws UnsupportedEncodingException {
		File dir = new File(storagePath);
		File[] directoryListing = dir.listFiles();
		if (directoryListing != null) {
			for (File child : directoryListing) {
				// Do something with child
				String fileName = child.getName();
				if (child.length() == 0) {
					System.out.println("vuoto: " + fileName);
					child.delete();
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

}
