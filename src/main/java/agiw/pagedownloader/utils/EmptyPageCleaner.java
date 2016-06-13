package agiw.pagedownloader.utils;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import utils.PropertyFactor;

public class EmptyPageCleaner {

	public static void main(String[] args) throws IOException {
		EmptyPageCleaner epc = new EmptyPageCleaner();
		System.out.println("Cleaning pages...");
		epc.deleteEmptyFiles();
		System.out.println("Pages cleaned.");
	}
	
	private String storagePath;
	
	public EmptyPageCleaner() {
		PropertyFactor pf = new PropertyFactor();
		this.storagePath = pf.getStoragePath();
	}

	public void deleteEmptyFiles() throws UnsupportedEncodingException {
		File dir = new File(this.storagePath);
		File[] directoryListing = dir.listFiles();
		if (directoryListing != null) {
			for (File child : directoryListing) {
				// Do something with child
				String fileName = child.getName();
				if (child.length() == 0) {
					System.out.println("empty: " + fileName);
					child.delete();
				}
				if (child.getName().contains(".pdf")||
						child.getName().contains(".ppt")||
						child.getName().contains(".pptx")) {
					System.out.println("formato pdf, ppt, pptx: " + fileName);
					child.delete();
				}
			}
		} else {
			System.err.println("Storage Path not setted properly, it should be a directory!");
		}
	}

}
