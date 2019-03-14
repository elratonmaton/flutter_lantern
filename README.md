# Lantern
[![pub package](https://img.shields.io/pub/v/lamp.svg)](https://pub.dartlang.org/packages/flutter_lantern)
A Flutter plugin to access the device's lantern/torch on Android and iOS.

## Usage
To use this plugin, add `lantern` as a [dependency in your pubspec.yaml file](https://flutter.io/platform-plugins/).

It works on Android Pie !!

For android, add the following to your manifest

``` 
<uses-permission android:name="android.permission.CAMERA" />
<uses-permission android:name="android.permission.FLASHLIGHT" />
<uses-feature android:name="android.hardware.camera" />
<uses-feature android:name="android.hardware.camera.flash" />
``` 

and change the minSDK in the app build.graddle

``` 
minSdkVersion 21
``` 

## Example
``` dart
// Import package
import 'package:flutter_lantern/flutter_lantern.dart';

// Turn the lamp on:
Lantern.turnOn();

// Turn the lamp off:
Lantern.turnOff();

// Turn the lamp with a specific intensity (only affects iOS as of now):
Lantern.turnOn(intensity: 0.4);

// Check if the device has a lamp:
bool hasLamp = await Lantern.hasLamp;

```