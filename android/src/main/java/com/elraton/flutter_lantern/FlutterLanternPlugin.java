package com.elraton.flutter_lantern;

import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Build;

import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry.Registrar;

import static android.content.Context.CAMERA_SERVICE;

/** FlutterLanternPlugin */
public class FlutterLanternPlugin implements MethodCallHandler {
  /** Plugin registration. */
  private FlutterLanternPlugin(Registrar registrar) {
    this._registrar = registrar;
    this.mCameraManager = this.getCamera();
    this.isTorchOn = false;
  }

  public static void registerWith(Registrar registrar) {
    final MethodChannel channel = new MethodChannel(registrar.messenger(), "github.com/elratonmaton/flutter_lantern");
    channel.setMethodCallHandler(new FlutterLanternPlugin(registrar));
  }

  private Registrar _registrar;

  private CameraManager mCameraManager;
  private String mCameraId;
  private Boolean isTorchOn;

  @Override
  public void onMethodCall(MethodCall call, Result result) {
    switch (call.method) {
      case "getPlatformVersion": {
        result.success("Android " + android.os.Build.VERSION.RELEASE);
      }
      case "hasLamp": {
        result.success(this.hasLamp());
        break;
      }
      case "turnOn": {
        this.turn(true);
        result.success(null);
        break;
      }
      case "turnOff": {
        this.turn(false);
        result.success(null);
        break;
      }
      default: {
        result.notImplemented();
        break;
      }
    }

    /*if (call.method.equals("getPlatformVersion")) {
      result.success("Android " + android.os.Build.VERSION.RELEASE);
    } else {
      result.notImplemented();
    }*/
  }

  private CameraManager getCamera() {
    mCameraManager = (CameraManager) _registrar.context().getSystemService(CAMERA_SERVICE);
    try {
      mCameraId = mCameraManager.getCameraIdList()[0];
    } catch (CameraAccessException e) {
      e.printStackTrace();
    }
    return  mCameraManager;
  }

  private void turn(boolean on) {
    if (on) {
      isTorchOn = true;
      try {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
          mCameraManager.setTorchMode(mCameraId, true);
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    } else {
      isTorchOn = false;
      try {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
          mCameraManager.setTorchMode(mCameraId, false);
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  private boolean hasLamp() {
    return _registrar.context().getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
  }
}
