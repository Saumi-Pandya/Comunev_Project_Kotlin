# Comunev_Project_Kotlin
This is a simple Kotlin based Android App which fetches data from an API and displays the parsed data on the screen.
1. **RecyclerView** has been used to display the data.
2. Instead of using a third party library, a **custom function(getDatafromNewtork())** has been created to send an **HTTP request**. It returns the response to the calling function in string format.
3. The exceptions which could possibly be thrown when there is no internet connection have been handled to prevent the app crash. 
