# AppliApp
This is a solution to the Applitools Hackathon.

Steps:
1. Clone the repo
2. Import as a maven project to IDE of your choice
3. Run either traditional or ai visual test suite from command line by typing: "mvn -Dtest=TraditionalTests test" or "mvn -Dtest=VisualAITets test"
4. Check the generated test results of Traditional tests under "src/target/surefire-reports" or on Applitools dashboard for Visual AI tests.

Note:
The app was tested using OS: Linux, Chrome: version 78 and selenium 3.11.0. To use the tests for a different combination of OS and browser, go to the URL: "https://chromedriver.chromium.org/downloads" and choose a right one for your combination. Afterwards, change the path to the driver in setUp method in TraditionalTests and VisualAITests.
