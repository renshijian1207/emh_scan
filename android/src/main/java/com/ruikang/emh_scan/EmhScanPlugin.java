package com.ruikang.emh_scan;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.BatteryManager;
import android.os.IBinder;

import io.flutter.app.FlutterActivity;

import androidx.annotation.NonNull;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.EventChannel;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry.Registrar;
import io.flutter.view.FlutterView;


/** EmhScanPlugin */
public class EmhScanPlugin implements FlutterPlugin, MethodCallHandler {
  /// The MethodChannel that will the communication between Flutter and native Android
  ///
  /// This local reference serves to register the plugin with the Flutter Engine and unregister it
  /// when the Flutter Engine is detached from the Activity
  private MethodChannel channel;
  private MethodChannel jy_channel;

  private EventChannel eventChannel;
  private EventChannel jy_eventChannel;
  private Context applicationContext;

  //易迈海
  private static final String EMH_SCAN_ACTION = "com.ge.action.barscan";
//  private static final String EMH_SCAN_ACTION = "com.kyee.action.scanner";
  private static final String CHARGING_CHANNEL = "emh_flutter";

  //京颐
  private static final String JY_SCAN_ACTION = "com.kyee.action.scanner";
  private static final String JY_CHARGING_CHANNEL = "jy_flutter";

  @Override
  public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
    channel = new MethodChannel(flutterPluginBinding.getBinaryMessenger(), "emh_scan");
    channel.setMethodCallHandler(this);

    eventChannel = new EventChannel(flutterPluginBinding.getBinaryMessenger(), CHARGING_CHANNEL);
    eventChannel.setStreamHandler(new EventChannel.StreamHandler() {

      private BroadcastReceiver chargingStateChangeReceiver;

      @Override
      public void onListen(Object arguments, EventChannel.EventSink events) {
        chargingStateChangeReceiver = createChargingStateChangeReceiver(events);
        IntentFilter filter = new IntentFilter();
        filter.addAction(EMH_SCAN_ACTION);
        applicationContext.registerReceiver(
                chargingStateChangeReceiver, filter);
      }

      @Override
      public void onCancel(Object arguments) {
        applicationContext.unregisterReceiver(chargingStateChangeReceiver);
        chargingStateChangeReceiver = null;
      }
    });

    jy_channel = new MethodChannel(flutterPluginBinding.getBinaryMessenger(), "jy_scan");
    jy_channel.setMethodCallHandler(this);

    jy_eventChannel = new EventChannel(flutterPluginBinding.getBinaryMessenger(), JY_CHARGING_CHANNEL);
    jy_eventChannel.setStreamHandler(new EventChannel.StreamHandler() {
      private BroadcastReceiver chargingStateChangeReceiver;

      @Override
      public void onListen(Object arguments, EventChannel.EventSink events) {
        chargingStateChangeReceiver = createChargingStateChangeReceiver(events);
        IntentFilter filter = new IntentFilter();
        filter.addAction(JY_SCAN_ACTION);
        applicationContext.registerReceiver(
                chargingStateChangeReceiver, filter);
      }

      @Override
      public void onCancel(Object arguments) {
        applicationContext.unregisterReceiver(chargingStateChangeReceiver);
        chargingStateChangeReceiver = null;
      }
    });


    applicationContext = flutterPluginBinding.getApplicationContext();
  }

  public static void registerWith(Registrar registrar) {
    final MethodChannel channel = new MethodChannel(registrar.messenger(), "emh_scan");
    channel.setMethodCallHandler(new EmhScanPlugin());

    final MethodChannel channel1 = new MethodChannel(registrar.messenger(), "jy_scan");
    channel1.setMethodCallHandler(new EmhScanPlugin());
  }


  @Override
  public void onMethodCall(@NonNull MethodCall call, @NonNull Result result) {

  }

  @Override
  public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
    channel.setMethodCallHandler(null);
  }

  private BroadcastReceiver createChargingStateChangeReceiver(final EventChannel.EventSink events) {
    return new BroadcastReceiver() {
      @Override
      public void onReceive(Context context, Intent intent) {
        String code = intent.getStringExtra("value");
        String content = intent.getStringExtra("content");
        String staffid = intent.getStringExtra("staffId");
        if (code != null && !code.isEmpty()) {
//          System.out.println("手机接收到广播数据>>>>>>>>>>>>>>>>>>>>>>>>>"+code);
          events.success(code);
        }

        if (content != null && !content.isEmpty()) {
          events.success(content);
        }

//        System.out.println("手机接收到广播数据>>>>>>>>>>>>>>>>>>>>>>>>>"+content+"----"+staffid);
      }
    };
  }
}
