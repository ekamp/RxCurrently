# RxCurrently
Simple weather application to prove the viability of RxAndroid.

##Application Purpose
This application was originally created to flush out the viability of using RxAndroid in a complete application. While developing
I realized that I was using the application more and more to check the weather as it was faster than opening Google Now and searching for the weather section.

##Application Structure
This application is written in loosely based MVP, utilizing Retrofit to handle network requests and RxAndroid for application communication / subscriptions.

##Libraries / APIs utilized
- <b>RxAndroid</b> : Generalized application structure and communication.
- <b>Butterknife</b> : View and resource binding.
- <b>Retrofit</b> : Network requests.
- <b>Picasso</b> : Image request and image loading.
- <b>Joda-Time</b> : Time zone conversion and date formatting.
- <b>OpenWeatherMap</b> : API for weather information.

##Screenshots
![Main Screenshot](https://github.com/ekamp/RxCurrently/blob/master/ScreenShotMain.png "Opening Screenshot")
</br>
![Secondary Screenshot](https://github.com/ekamp/RxCurrently/blob/master/ScreenShotNext.png "Secondary Screenshot")
</br>
