import 'package:flutter/material.dart';
import 'dart:async';

import 'package:flutter/services.dart';
import 'package:emh_scan/emh_scan.dart';

void main() {
  runApp(MyApp());
}

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  String _platformVersion = 'Unknown';

  static const EventChannel _eventChannel = EventChannel('emh_flutter');

  static const EventChannel _jy_eventChannel = EventChannel('jy_flutter');

  static const EventChannel _idata_eventChannel = EventChannel('idata_flutter');

  static const EventChannel sl_eventChannel = EventChannel('sl_flutter');

  @override
  void initState() {
    super.initState();

    _eventChannel.receiveBroadcastStream().listen((value) {
      print("获取到扫描头数据>>>>>>>>>>$value");

    });

    _jy_eventChannel.receiveBroadcastStream().listen((value) {
      print("获取到京颐数据>>>>>>>>>>$value");

    });

    _idata_eventChannel.receiveBroadcastStream().listen((value) {
      print("获取到iData数据>>>>>>>>>>$value");
    });

    sl_eventChannel.receiveBroadcastStream().listen((value) {
      print("获取到iData数据>>>>>>>>>>$value");
    });
  }

  // Platform messages are asynchronous, so we initialize in an async method.
  Future<void> initPlatformState() async {
    String platformVersion;
    // Platform messages may fail, so we use a try/catch PlatformException.
    try {
      platformVersion = await EmhScan.emhScanInit;
    } on PlatformException {
      platformVersion = 'Failed to get platform version.';
    }

    // If the widget was removed from the tree while the asynchronous platform
    // message was in flight, we want to discard the reply rather than calling
    // setState to update our non-existent appearance.
    if (!mounted) return;

    setState(() {
      _platformVersion = platformVersion;
    });
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Plugin example app'),
        ),
        body: Center(
          child: Text('Running on: $_platformVersion\n'),
        ),
      ),
    );
  }
}
