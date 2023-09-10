import 'package:flutter/services.dart';

class MyModule {
  static const platform = MethodChannel('samples.flutter.dev/battery');

  static const platformNavigate = MethodChannel('navigate/flutter');

  static const plataformNavigateToReact = MethodChannel("navigate/react");

  static const plataformNavigateToNative = MethodChannel("investmentFunds");

  Future<String> getNavigate() async {
    try {
      final String message =
          await platformNavigate.invokeMethod('getNavigationRoute');
      return message;
    } on PlatformException catch (e) {
      return '';
    }
  }

  Future<String> getMessageFromNative() async {
    try {
      final String message = await platform.invokeMethod('getMessage');
      return message;
    } on PlatformException catch (e) {
      return '';
    }
  }

  Future<void> navigateToReact(String code) async {
    try {
      await plataformNavigateToReact
          .invokeMethod("getNavigateToReact", {"key": code});
    } catch (e) {}
  }

  Future<void> navigateToNative() async {
    try {
      await plataformNavigateToNative.invokeMethod("navigateToNative");
    } catch (e) {}
  }
}
