# Flight Gear Remote Joystick


Welcome aboard! The Flight Gear Remote Joystick Android app allows you to connect from your mobile device to computer running the Flight Gear application and control and fly the aircraft.

## Features

- Connect to Flight Gear application running on PC
- Control the aileron and elevator values using the joystick
- Control the throttle and rudder values using the sliders
- Lots and lots of fun

Moving the joystick up and down control the elevator value, while moving the joystick left and right control the aileron value. Moving the left slider control the throttel value, while moving the bottom slider control the rudder value.

As [John Gruber] writes on the [Markdown site][df1]

> The overriding design goal for Markdown's
> formatting syntax is to make it as readable
> as possible. The idea is that a
> Markdown-formatted document should be
> publishable as-is, as plain text, without
> looking like it's been marked up with tags
> or formatting instructions.

This text you see here is *actually- written in Markdown! To get a feel
for Markdown's syntax, type some text into the left window and
watch the results in the right.

## Gettting Started

The Flight Gear Remote Joystick app requires the following components:

- [Flight Gear] - desktop application the simulates flights and aircrafts
- [Android Studio] - IDE for developing android applications
- [Flight Gear Remote Joystick] - The Flight Gear Remote Joystick application itself

## Flight Gear Installation

Download the Flight Gear desktop application from the [Flight Gear website] and install it on the desiered computer using the instuctions on their website and the installation wizard.
After the installation completed, run the Flight Gear application and enter the settings tab. In the "Additional Settings" text box, enter the following:
```sh
--telnet=socket,in,10,127.0.0.1,6400,tcp
```

## Android Studio Installation
Download the Android Studio IDE from the [Android Studio website] and install it on the desiered computer using the instuctions on their website and the installation wizard.
If you wish to run the application on emulated android device on your PC, you'll need to download and install a device that using Android API level 22 or above (Pixel 2 is recommended).
If you wish to run the application on your android device, connect the desired device to your computer using a USB cable, click the "Troubleshoot Device Connections" in the top of the IDE and follow the instructions.

## Fly!
After tou completed the previeus steps, it's time for some fun!
Launch the Flight Gear application on your PC, and click the "Fly!" tab. Wait for the simulator to load, and then, on the top toolbar press on your aircraft's name and click "Autostart". Then, launch the Android Studio IDE, open the project from this repository (using "git clone"), and click "Run". If your'e using your Android device, make sure that the device connected properly to the PC using a USB cabel. When the application starts on the device (Physical or emulated), fill the local IP address (if your'e running the application using the emulator or a physical device thats in the same local network as your PC) of the PC running the Flight Gear application. To check your IP address, you can launch the command prompt, type "ipconfig" and hit "enter". The IP address you'll need is the "IPv4 Address". Enter port "6400" in the Flight Gear Remote Joystick application and press "connect". Now your'e ready to fly! You can use the left slider to control the throttle, and the bottom one to control the rudder. Use the joystick to control the aileron and elevator.

## Development Information

The Flight Gear Remote Joystick application developed in Java using the Android Studio IDE. The application is based on the MVVM architecure and uses also the Active Object design pattern and the dependency injection principle using the Strategy design Pattern.

## MVVM architecure
### View
The View contains two components:
- MainActivity.java - contains the view-logic behind the XML
- activity_main.xml - contains the design of the application in XML format

The text fields and the sliders are binded to the appropriate fields in the viewModel using Data Binding. The "connect" button sends command to the ViewModel (calles the connectToSimulator method thats in the ViewModel class).

### ViewModel
The ViewModel contains the ViewModel.java - the bridge between the View and the Model, in order that the View won't know about the Model and the Model won't know about the View.

### Model
The Model contains three components:
- Model.java - contains the business logic of the program, using the Object Adapter design pattern to run the task one by one by order in a seperate thread than the main thread
- Joystick.java - contains the logic behind the movement of the joystick
- IOnChange.java - an functional inteface, in order to set the action that will be made on Joystick move using the dependency injection principle and the Strategy design Pattern. There's lambda expression injected to the "onChange" field (of type IOnChange) of the Joystick in the MainActivity.java


## Want to use our joystick in your project?
Your'e welcome to do so! All you need to do is:
- Copy the Joystick.java class to your project
- Copy the IOnChange.java interface to your project
- Copy the following code to your View's XML file to insert the joystick's view to your View, and adjust the parameters based on your needs:
```sh
<com.example.myapplication1.Joystick
    android:id="@+id/myJoystick"
    android:layout_width="320dp"
    android:layout_height="320dp"
    android:layout_alignParentEnd="true"
    android:layout_alignParentBottom="true"
    android:layout_marginEnd="50dp"
    android:layout_marginBottom="91dp" />
```
- From your MainActivity's onCreate function, enter a lambda expression into the onChange field of your joystick, for example:
```sh
Joystick coolJoystick = findViewById(R.id.myJoystick);
js.onChange = (x, y) -> {
    myViewModel.setX(x);
    myViewModel.setY(y);
};
```

## UML

[![UML](https://github.com/Ellakh/FlightGearJoystickApp/blob/master/app/src/main/java/com/example/myapplication1/UML.png)]

## Video and presentaion


## Credits



[//]: # (These are reference links used in the body of this note and get stripped out when the markdown processor does its job. There is no need to format nicely because it shouldn't be seen. Thanks SO - http://stackoverflow.com/questions/4823468/store-comments-in-markdown-syntax)

   [dill]: <https://github.com/joemccann/dillinger>
   [git-repo-url]: <https://github.com/joemccann/dillinger.git>
   [john gruber]: <http://daringfireball.net>
   [df1]: <http://daringfireball.net/projects/markdown/>
   [markdown-it]: <https://github.com/markdown-it/markdown-it>
   [Ace Editor]: <http://ace.ajax.org>
   [node.js]: <http://nodejs.org>
   [Twitter Bootstrap]: <http://twitter.github.com/bootstrap/>
   [jQuery]: <http://jquery.com>
   [@tjholowaychuk]: <http://twitter.com/tjholowaychuk>
   [express]: <http://expressjs.com>
   [AngularJS]: <http://angularjs.org>
   [Gulp]: <http://gulpjs.com>

   [PlDb]: <https://github.com/joemccann/dillinger/tree/master/plugins/dropbox/README.md>
   [PlGh]: <https://github.com/joemccann/dillinger/tree/master/plugins/github/README.md>
   [PlGd]: <https://github.com/joemccann/dillinger/tree/master/plugins/googledrive/README.md>
   [PlOd]: <https://github.com/joemccann/dillinger/tree/master/plugins/onedrive/README.md>
   [PlMe]: <https://github.com/joemccann/dillinger/tree/master/plugins/medium/README.md>
   [PlGa]: <https://github.com/RahulHP/dillinger/blob/master/plugins/googleanalytics/README.md>
