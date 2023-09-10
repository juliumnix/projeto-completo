import {
  View,
  Text,
  Button,
  StyleSheet,
  NativeModules,
  Pressable,
} from "react-native";
import React, { useEffect, useState } from "react";
import { useNavigation, RouteProp } from "@react-navigation/native";
import { StackNavigationProp } from "@react-navigation/stack";
import { RootStackParamList } from "./App";
import { HeaderBackButton } from "@react-navigation/elements";

type ConfirmationScreenRouteProp = RouteProp<
  RootStackParamList,
  "ConfirmationScreen"
>;

type ConfirmationScreenNavigationProp = StackNavigationProp<
  RootStackParamList,
  "ConfirmationScreen"
>;

type ConfirmationScreenProps = {
  route: ConfirmationScreenRouteProp;
  navigation: ConfirmationScreenNavigationProp;
};

const verifyIsReact = false;

const { NativeFunctions } = NativeModules;

export default function ConfirmationScreen({ route }: ConfirmationScreenProps) {
  const { pixCode } = route.params;
  const navigation = useNavigation<ConfirmationScreenNavigationProp>();
  const [pixCodeSplitted, setPixCodeSplitted] = useState("");

  const goBackNative = async () => {
    try {
      await NativeFunctions.goBackNative();
    } catch (error) {
      console.log(error);
    }
  };

  const autenticate = async () => {
    try {
      await NativeFunctions.validateBiometric();
    } catch (error) {
      console.log(error);
    }
  };

  useEffect(() => {
    navigation.setOptions({
      title: verifyIsReact ? "Tela React" : "Confirmação pagamento",
      headerLeft: () => (
        <HeaderBackButton
          onPress={() =>
            navigation.canGoBack() ? navigation.goBack() : goBackNative()
          }
        />
      ),
    });
    const parts = pixCode.split("pixCode/");
    setPixCodeSplitted(parts[1]);
  }, []);
  return (
    <View style={styles.container}>
      <Text style={{ fontSize: 20, color: "black" }}>Deseja pagar o PIX?</Text>
      <Text>Código: {pixCodeSplitted}</Text>

      <Pressable
        style={styles.containerButtonConfirmation}
        onPress={() => {
          autenticate();
        }}
      >
        <Text style={{ color: "white", fontSize: 22 }}>Sim</Text>
      </Pressable>

      <Pressable
        style={styles.containerButtonCancel}
        onPress={() => {
          navigation.canGoBack() ? navigation.goBack() : goBackNative();
        }}
      >
        <Text style={{ color: "white", fontSize: 22 }}>Não</Text>
      </Pressable>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    padding: 20,
    backgroundColor: "#D9D9D9",
  },
  containerButtonConfirmation: {
    justifyContent: "center",
    alignItems: "center",
    padding: 20,
    backgroundColor: "#0099CC",
    marginTop: 20,
    borderRadius: 10,
  },

  containerButtonCancel: {
    justifyContent: "center",
    alignItems: "center",
    padding: 20,
    backgroundColor: "red",
    marginTop: 20,
    borderRadius: 10,
  },
});
