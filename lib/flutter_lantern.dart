import 'dart:async';

import 'package:flutter/services.dart';

class Lantern {
  static const MethodChannel _channel = const MethodChannel('github.com/elratonmaton/flutter_lantern');
  /*static Future<bool> get hasLamp async {
    final bool haslamp = await _channel.invokeMethod('hasLamp');
    return haslamp;
  }*/

  static Future<String> get platformVersion async {
    final String version = await _channel.invokeMethod('getPlatformVersion');
    return version;
  }

  static Future<bool> get hasLamp async {
    final bool haslamp = await _channel.invokeMethod('hasLamp');
    return haslamp;
  }

  static Future turnOn({double intensity = 1.0}) => _channel.invokeMethod('turnOn', {'intensity' : intensity.clamp(0.01, 1.0)});
  static Future turnOff() => _channel.invokeMethod('turnOff');
  static Future flash(Duration duration) => turnOn().whenComplete(() => new Future.delayed(duration, () => turnOff()));
}
