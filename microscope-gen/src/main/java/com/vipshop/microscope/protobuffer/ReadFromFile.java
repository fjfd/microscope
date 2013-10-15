package com.vipshop.microscope.protobuffer;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import com.vipshop.microscope.protobuffer.HtmlThemeProtos.HtmlTheme;

public class ReadFromFile {
	public static void main(String[] args) throws IOException {
		String file = "G:/corpus/protobuf/TIME.txt";
		FileInputStream inputStream = new FileInputStream(new File(file));
		HtmlTheme htmlTheme;
		int count = 0;
		while ((htmlTheme = HtmlTheme.parseDelimitedFrom(inputStream)) != null) {
			System.out.println(htmlTheme.getId() + "\t\t" + htmlTheme.getTitle());
			count++;
		}
		
		System.out.println("===============================");
		System.out.println(count);
		System.out.println("===============================");
		
	}
}

