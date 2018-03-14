

# GUI Development 


## Main Functionality 
The Main functions include being able to add a movie, view, search for a movie by movie title, delete a movie and edit movie details.
![ScreenShot](/openingScreen.jpg)
![ScreenShot](/addScreen.jpg)



## Extra Functionality 
The extra functionality I included was the datePicker to pick the date when the movie is playing. I used SQLiteDatabase to save movie details. 
I created a filter by function which allows users to filter by movie Title, movie ID, movie time and cinema this filter is done by using SQLiteDatabase and the ORDER BY function. 

![ScreenShot](/listScreen.jpg)

I also implemented a Youtube player functionally so users can view the trailer of the movie the entered. I did this by getting the name of the movie the user entered and searches it using the API and retrieves the video which is then used to play the video.

![ScreenShot](/trailerScreen.jpg)


## References
[http://developer.android.com/guide/topics/ui/dialogs.html]

[http://www.codeproject.com/Articles/783073/A-Simple-Android-SQLite-Example]

[http://stackoverflow.com/questions/23762231/how-to-disable-past-dates-in-android-date-picker]

[http://stackoverflow.com/questions/2808535/round-a-double-to-2-decimal-places]

[http://www.joe0.com/2016/03/05/youtube-data-api-v3-how-to-search-youtube-using-java-and-extract-video-id-of-the-most-relevant-result/]

[https://ddrohan.gitbooks.io/android-101-labs-as/content/session4/lab/md/step04.html]

[https://developers.google.com/youtube/v3/docs/search/list#parameters]

[https://www.youtube.com/watch?v=a4NT5iBFuZs]

[http://stackoverflow.com/questions/13136539/caused-by-android-os-networkonmainthreadexception]
