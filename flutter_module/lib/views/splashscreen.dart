import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:flutter_module/views/camera_pix_detector.dart';
import 'package:flutter_module/mymodule.dart';
import 'package:flutter_module/views/teste.dart';

class SplashScreen extends StatefulWidget {
  const SplashScreen({
    super.key,
  });

  @override
  State<SplashScreen> createState() => _SplashScreenState();
}

class _SplashScreenState extends State<SplashScreen>
    with WidgetsBindingObserver {
  var module = MyModule();

  Future<void> _getNativeParams() async {
    try {
      String navigate = await module.getNavigate();

      if (navigate == "/") {
        _navigateTo(const CameraPixDetector());
      }

      if (navigate == "/teste") {
        String dataFund = await module.getMessageFromNative();
        _navigateTo(Teste(
          fundDetail: dataFund,
        ));
      }
    } on PlatformException catch (e) {
      // print(e.message);
    }
  }

  void _navigateTo(StatefulWidget page) {
    Navigator.push(
        context,
        MaterialPageRoute(
          builder: (context) => page,
        ));
  }

  @override
  void initState() {
    super.initState();
    _getNativeParams();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Container(
        color: const Color(0xFF41B3D3),
        child: Center(
          child: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            children: <Widget>[
              Image.asset(
                'assets/images/logo.png', // Caminho para a imagem local
                width: 200, // Largura da imagem (ajuste conforme necessário)
                height: 200, // Altura da imagem (ajuste conforme necessário)
              ),
            ],
          ),
        ),
      ),
    );
  }
}
