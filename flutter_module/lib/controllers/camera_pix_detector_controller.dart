import 'package:flutter/material.dart';
import 'package:flutter_barcode_scanner/flutter_barcode_scanner.dart';
import 'package:flutter_module/mymodule.dart';

class CameraPixDetectorController {
  final bool verifyIsFlutter = false;
  final TextEditingController _textController = TextEditingController();
  CameraPixDetectorController();

  readQRCode() async {
    String code = await FlutterBarcodeScanner.scanBarcode(
      "#FFFFFF",
      "Cancelar",
      false,
      ScanMode.QR,
    );
    if (code != "-1") {
      navigateToPix(code);
    }
  }

  navigateToPix(String pixCode) {
    MyModule module = MyModule();
    _textController.clear();
    module.navigateToReact(pixCode);
  }
}
