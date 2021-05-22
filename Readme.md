Automation Assignment - Selenium Automation
System Requirement:
JDK 1.8 or above
Maven 3.1 or above
Intellij or IDE of choice in case there is need to update the script. (optional)
Android Studio with ANDROID_HOME variable and path setting for platform-tools, build-tools, tools.
Appium desktop client.

For execution of scripts on Chrome you need to have executable files for both drivers respectively and add them as as environment variable.
You can download these executable files from below links
Chrome: http://chromedriver.chromium.org/downloads
For execution of scripts on Mobile you need Real android device with Debugging mode On or android emulator.
Config1.properties file should be updated with device data.
APK file is place at src>test>resources>Apps , Take absolute path and add in Config1.properties file.

Execution Steps
Please follow the instructions to execute the tests on local:

Checkout the code from github
Open terminal and navigate to the checkout directory
git clone https://github.com/Ashikaqait/Automation.git
Checkout the Master branch

At src>test>resources>testngxmls 2 TestNG files are stored for Assignment 1 and 2
Assignment1: WeatherCompareTest.xml
Assignment2: MobileTest.xml

Result Files:
Test Results - _Users_ashikasrivastava_Desktop_BlueStacks_Demo_src_test_resources_testngxmls_MobileTest_xml.html
Test Results - _Users_ashikasrivastava_Desktop_BlueStacks_Demo_src_test_resources_testngxmls_WeatherCompareTest_xml.html