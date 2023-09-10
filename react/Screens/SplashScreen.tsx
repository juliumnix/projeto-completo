import { View, Text } from "react-native";
import React, { useEffect } from "react";
import {
  useNavigation,
  RouteProp,
  StackActions,
} from "@react-navigation/native";
import { RootStackParamList } from "./App";

type SplashScreenRouteProp = RouteProp<RootStackParamList, "SplashScreen">;

interface SplashScreenProps {
  route: SplashScreenRouteProp;
}

export default function SplashScreen({ route }: SplashScreenProps) {
  const { infoFromNative } = route.params;
  const navigation = useNavigation();
  useEffect(() => {
    if (infoFromNative.includes("pixCode/")) {
      navigation.dispatch(
        StackActions.replace("ConfirmationScreen", { pixCode: infoFromNative })
      );
    } else {
      navigation.dispatch(StackActions.replace("InvestmentFunds"));
    }
  }, []);

  return <View></View>;
}
