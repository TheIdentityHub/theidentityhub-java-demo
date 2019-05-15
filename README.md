# theidentityhub-java-demo
Demo JAVA Application for The Identity Hub. The Identity Hub makes it easy to connect your app to all major identity providers like Microsoft, Facebook, Google, Twitter, Linked In and more. For more information see https://www.theidentityhub.com

# Getting Started

Download or Clone the repository. 

Find the MainApp.java file in the lib theidentityhub folder and locate the following fragment:
````js
final String BASE_URL = "https://www.theidentityhub.com/[Your URL segment]"; 
final String CLIENT_ID = "[Your Application Client Id]"; 
````

Change the configuration as follows

1. Replace [Your Application Client Id] with the client id from your App configured in The Identity Hub.

2. Replace [Your URL segment] with the url of your tenant on The Identity Hub.

If you do not have already created an App see https://docs.theidentityhub.com/doc/Apps/Create-an-App.html.
