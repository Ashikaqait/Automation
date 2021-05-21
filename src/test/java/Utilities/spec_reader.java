package Utilities;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class spec_reader {
	static String str;
	static String[] words;
	static WebElement element;

	public static WebElement locate(WebDriver driver, String str, String replacement)  {

		try {
			File file = new File("src/test/resources/locators.spec");
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line;

			outer:
			while ((line = br.readLine()) != null) {

				if (line.contains(str)) {

					words = line.split(":");
					if (!replacement.isEmpty())
						words[2] = words[2].replaceAll("\\$\\{.+?\\}", replacement);
					String str1 = "id";
					String str2 = "xpath";
					String str3 = "css";

					if (words[1].trim().equals(str1)) {

						element = driver.findElement(By.id(words[2].trim()));

						break outer;
					}
					if (words[1].trim().equals(str2)) {

						element = driver.findElement(By.xpath(words[2].trim()));

						break outer;

					}
					if (words[1].trim().equals(str3)) {
						element = driver.findElement(By.cssSelector(words[2].trim()));

						break outer;
					}
				}
			}
		}
	 catch (IOException ioe) {
		ioe.printStackTrace();
	}
		return element;

	}
}
