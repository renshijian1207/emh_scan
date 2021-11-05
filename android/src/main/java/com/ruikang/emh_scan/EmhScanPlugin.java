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
  private EventChannel eventChannel;
  private Context applicationContext;

  private MethodChannel jy_channel;
  private EventChannel jy_eventChannel;
  private Context jy_applicationContext;

  private MethodChannel idata_channel;
  private EventChannel idata_eventChannel;
  private Context idata_applicationContext;

  private MethodChannel sl_channel;
  private EventChannel sl_eventChannel;
  private Context sl_applicationContext;

  private MethodChannel xmg_channel;
  private EventChannel xmg_eventChannel;
  private Context xmg_applicationContext;

  private MethodChannel lx_channel;
  private EventChannel lx_eventChannel;
  private Context lx_applicationContext;

  //易迈海
  private static final String EMH_SCAN_ACTION = "com.ge.action.barscan";
  private static final String CHARGING_CHANNEL = "emh_flutter";

  //京颐
  private static final String JY_SCAN_ACTION = "com.kyee.action.scanner";
  private static final String JY_CHARGING_CHANNEL = "jy_flutter";

  //iData
  private static final String iData_SCAN_ACTION = "android.intent.action.SCANRESULT";
  private static final String iData_CHARGING_CHANNEL = "idata_flutter";

  //识凌
  private static final String sl_SCAN_ACTION = "SYSTEM_BAR_READ";
  private static final String sl_CHARGING_CHANNEL = "sl_flutter";

  //东集小码哥
  private static final String xmg_SCAN_ACTION = "com.android.server.scannerservice.broadcastJX";
  private static final String xmg_CHARGING_CHANNEL = "xmg_flutter";

  //联新
  private static final String lx_SCAN_ACTION = "lachesis_barcode_value_notice_broadcast";
  private static final String lx_CHARGING_CHANNEL = "lx_flutter";

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


      private BroadcastReceiver chargingStateChangeReceiver1;

      @Override
      public void onListen(Object arguments, EventChannel.EventSink events) {
        chargingStateChangeReceiver1 = createChargingStateChangeReceiver(events);
        IntentFilter filter = new IntentFilter();
        filter.addAction(JY_SCAN_ACTION);
        jy_applicationContext.registerReceiver(
                chargingStateChangeReceiver1, filter);
      }

      @Override
      public void onCancel(Object arguments) {
        jy_applicationContext.unregisterReceiver(chargingStateChangeReceiver1);
        chargingStateChangeReceiver1 = null;
      }
    });


    idata_channel = new MethodChannel(flutterPluginBinding.getBinaryMessenger(), "idata_scan");
    idata_channel.setMethodCallHandler(this);

    idata_eventChannel = new EventChannel(flutterPluginBinding.getBinaryMessenger(), iData_CHARGING_CHANNEL);
    idata_eventChannel.setStreamHandler(new EventChannel.StreamHandler() {


      private BroadcastReceiver chargingStateChangeReceiver2;

      @Override
      public void onListen(Object arguments, EventChannel.EventSink events) {
        chargingStateChangeReceiver2 = createChargingStateChangeReceiver(events);
        IntentFilter filter = new IntentFilter();
        filter.addAction(iData_SCAN_ACTION);
        idata_applicationContext.registerReceiver(
                chargingStateChangeReceiver2, filter);
      }

      @Override
      public void onCancel(Object arguments) {
        idata_applicationContext.unregisterReceiver(chargingStateChangeReceiver2);
        chargingStateChangeReceiver2 = null;
      }
    });

    sl_channel = new MethodChannel(flutterPluginBinding.getBinaryMessenger(), "sl_scan");
    sl_channel.setMethodCallHandler(this);

    sl_eventChannel = new EventChannel(flutterPluginBinding.getBinaryMessenger(), sl_CHARGING_CHANNEL);
    sl_eventChannel.setStreamHandler(new EventChannel.StreamHandler() {


      private BroadcastReceiver chargingStateChangeReceiver3;

      @Override
      public void onListen(Object arguments, EventChannel.EventSink events) {
        chargingStateChangeReceiver3 = createChargingStateChangeReceiver(events);
        IntentFilter filter = new IntentFilter();
        filter.addAction(sl_SCAN_ACTION);
        sl_applicationContext.registerReceiver(
                chargingStateChangeReceiver3, filter);
      }

      @Override
      public void onCancel(Object arguments) {
        sl_applicationContext.unregisterReceiver(chargingStateChangeReceiver3);
        chargingStateChangeReceiver3 = null;
      }
    });

    xmg_channel = new MethodChannel(flutterPluginBinding.getBinaryMessenger(), "xmg_scan");
    xmg_channel.setMethodCallHandler(this);

    xmg_eventChannel = new EventChannel(flutterPluginBinding.getBinaryMessenger(), xmg_CHARGING_CHANNEL);
    xmg_eventChannel.setStreamHandler(new EventChannel.StreamHandler() {


      private BroadcastReceiver chargingStateChangeReceiver4;

      @Override
      public void onListen(Object arguments, EventChannel.EventSink events) {
        chargingStateChangeReceiver4 = createChargingStateChangeReceiver(events);
        IntentFilter filter = new IntentFilter();
        filter.addAction(xmg_SCAN_ACTION);
        idata_applicationContext.registerReceiver(
                chargingStateChangeReceiver4, filter);
      }

      @Override
      public void onCancel(Object arguments) {
        idata_applicationContext.unregisterReceiver(chargingStateChangeReceiver4);
        chargingStateChangeReceiver4 = null;
      }
    });



    lx_channel = new MethodChannel(flutterPluginBinding.getBinaryMessenger(), "lx_scan");
    lx_channel.setMethodCallHandler(this);

    lx_eventChannel = new EventChannel(flutterPluginBinding.getBinaryMessenger(), lx_CHARGING_CHANNEL);
    lx_eventChannel.setStreamHandler(new EventChannel.StreamHandler() {


      private BroadcastReceiver chargingStateChangeReceiver5;

      @Override
      public void onListen(Object arguments, EventChannel.EventSink events) {
        chargingStateChangeReceiver5 = createChargingStateChangeReceiver(events);
        IntentFilter filter = new IntentFilter();
        filter.addAction(lx_SCAN_ACTION);
        lx_applicationContext.registerReceiver(
                chargingStateChangeReceiver5, filter);
      }

      @Override
      public void onCancel(Object arguments) {
        lx_applicationContext.unregisterReceiver(chargingStateChangeReceiver5);
        chargingStateChangeReceiver5 = null;
      }
    });


    applicationContext = flutterPluginBinding.getApplicationContext();
    jy_applicationContext = flutterPluginBinding.getApplicationContext();
    idata_applicationContext = flutterPluginBinding.getApplicationContext();
    sl_applicationContext = flutterPluginBinding.getApplicationContext();
    xmg_applicationContext = flutterPluginBinding.getApplicationContext();
    lx_applicationContext = flutterPluginBinding.getApplicationContext();
  }

  public static void registerWith(Registrar registrar) {
    final MethodChannel channel = new MethodChannel(registrar.messenger(), "emh_scan");
    channel.setMethodCallHandler(new EmhScanPlugin());

    final MethodChannel channel1 = new MethodChannel(registrar.messenger(), "jy_scan");
    channel1.setMethodCallHandler(new EmhScanPlugin());

    final MethodChannel channel2 = new MethodChannel(registrar.messenger(), "idata_scan");
    channel2.setMethodCallHandler(new EmhScanPlugin());

    final MethodChannel channel3 = new MethodChannel(registrar.messenger(), "sl_scan");
    channel3.setMethodCallHandler(new EmhScanPlugin());

    final MethodChannel channel4 = new MethodChannel(registrar.messenger(), "xmg_scan");
    channel4.setMethodCallHandler(new EmhScanPlugin());

    final MethodChannel channel5 = new MethodChannel(registrar.messenger(), "lx_scan");
    channel5.setMethodCallHandler(new EmhScanPlugin());
  }


  @Override
  public void onMethodCall(@NonNull MethodCall call, @NonNull Result result) {

  }

  @Override
  public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
    channel.setMethodCallHandler(null);
    jy_channel.setMethodCallHandler(null);
    idata_channel.setMethodCallHandler(null);
    sl_channel.setMethodCallHandler(null);
    xmg_channel.setMethodCallHandler(null);
    lx_channel.setMethodCallHandler(null);
  }

  private BroadcastReceiver createChargingStateChangeReceiver(final EventChannel.EventSink events) {
    return new BroadcastReceiver() {
      @Override
      public void onReceive(Context context, Intent intent) {
        String code = intent.getStringExtra("value");
        String content = intent.getStringExtra("content");
        String sl = intent.getStringExtra("BAR_VALUE");
        String xmg = intent.getStringExtra("scannerdata");
        String lx = intent.getStringExtra("lachesis_barcode_value_notice_broadcast_data_string");
        if (code != null && !code.isEmpty()) {
//          System.out.println("手机接收到广播数据>>>>>>>>>>>>>>>>>>>>>>>>>"+code);
          events.success(code);
        }

        if (content != null && !content.isEmpty()) {

          events.success(content);
        }

        if(sl != null && !sl.isEmpty()) {
          events.success(sl);
        }

        if(xmg != null && !xmg.isEmpty()) {
          events.success(xmg);
        }

        if(lx != null && !lx.isEmpty()) {
          events.success(lx);
        }

//        System.out.println("手机接收到广播数据>>>>>>>>>>>>>>>>>>>>>>>>>"+content+"----"+staffid);
      }
    };
  }
}
