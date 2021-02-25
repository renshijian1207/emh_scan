import 'package:flutter/services.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:emh_scan/emh_scan.dart';

void main() {
  const MethodChannel channel = MethodChannel('emh_scan');

  TestWidgetsFlutterBinding.ensureInitialized();

  setUp(() {
    channel.setMockMethodCallHandler((MethodCall methodCall) async {
      return '42';
    });
  });

  tearDown(() {
    channel.setMockMethodCallHandler(null);
  });


}
