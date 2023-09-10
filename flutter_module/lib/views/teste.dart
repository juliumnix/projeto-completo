import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:flutter_module/views/help_page.dart';
import 'package:flutter_module/mymodule.dart';

class Teste extends StatefulWidget {
  final String fundDetail;
  const Teste({super.key, required this.fundDetail});

  @override
  State<Teste> createState() => _TesteState();
}

class _TesteState extends State<Teste> {
  final bool verifyIsFlutter = false;
  static var module = MyModule();

  void _navigateToHelp() {
    Navigator.push(
        context,
        MaterialPageRoute(
          builder: (context) => const HelpPage(),
        ));
  }

  @override
  Widget build(BuildContext context) {
    return WillPopScope(
      onWillPop: () async {
        if (Navigator.canPop(context)) {
          SystemChannels.platform.invokeMethod<void>('SystemNavigator.pop');
          return false;
        } else {
          return true;
        }
      },
      child: Scaffold(
        appBar: AppBar(
          backgroundColor: const Color(0xff0099cc),
          systemOverlayStyle: const SystemUiOverlayStyle(
            statusBarColor: Color(0xff0099cc),
          ),
          title:
              Text(verifyIsFlutter ? "Tela Flutter" : "Resumo do Investimento"),
        ),
        body: Padding(
          padding: const EdgeInsets.all(8.0),
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              Row(
                children: [
                  Text(widget.fundDetail),
                  const Spacer(),
                  ElevatedButton.icon(
                      onPressed: () {
                        _navigateToHelp();
                      },
                      icon: const Icon(Icons.help),
                      label: const Text("Ajuda"))
                ],
              ),
              const Text("Valor: 10,00"),
              const Padding(padding: EdgeInsets.all(8.0)),
              const Center(
                child: Text("Quer mesmo Investir?"),
              ),
              Center(
                child: SizedBox(
                  width: 200,
                  child: ElevatedButton(
                    onPressed: () {
                      module.navigateToNative();
                    },
                    child: const Text('Sim'),
                  ),
                ),
              ),
              const Padding(padding: EdgeInsets.all(8)),
              Center(
                child: SizedBox(
                  width: 200,
                  child: ElevatedButton(
                    style:
                        ElevatedButton.styleFrom(backgroundColor: Colors.red),
                    onPressed: () {
                      SystemChannels.platform
                          .invokeMethod<void>('SystemNavigator.pop');
                    },
                    child: const Text('Não'),
                  ),
                ),
              ),
            ],
          ),
        ),
      ),
    );
  }
}
