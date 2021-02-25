
import 'dart:async';

import 'package:flutter/services.dart';

class EmhScan {
  static const MethodChannel _channel =
      const MethodChannel('emh_scan');

  static Future<String> get emhScanInit async {
    final String code = await _channel.invokeMethod('init');
    return code;
  }
}
