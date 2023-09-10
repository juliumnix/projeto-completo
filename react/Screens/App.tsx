import React from "react";
import { NativeProps } from "../..";
import { NativeModules } from "react-native";
import { NavigationContainer } from "@react-navigation/native";
import { createStackNavigator } from "@react-navigation/stack";
import ConfirmationScreen from "./ConfirmationScreen";
import InvestmentFunds from "./InvestmentFunds";
import SplashScreen from "./SplashScreen";

export type RootStackParamList = {
  SplashScreen: { infoFromNative: string };
  ConfirmationScreen: { pixCode: string };
  InvestmentFunds: undefined;
};

const Stack = createStackNavigator<RootStackParamList>();
export const App = (props: NativeProps) => {
  return (
    <NavigationContainer>
      <Stack.Navigator>
        <Stack.Screen
          name="SplashScreen"
          component={SplashScreen}
          initialParams={{ infoFromNative: props.message_from_native }}
          options={{
            headerShown: false, // Define a cor do texto da AppBar
          }}
        />
        <Stack.Screen
          name="ConfirmationScreen"
          component={ConfirmationScreen}
          options={{
            headerStyle: {
              backgroundColor: "#0099CC", // Define a cor de fundo da AppBar
            },
            headerTintColor: "white", // Define a cor do texto da AppBar
          }}
        />
        <Stack.Screen
          name="InvestmentFunds"
          component={InvestmentFunds}
          options={{
            headerStyle: {
              backgroundColor: "#0099CC", // Define a cor de fundo da AppBar
            },
            headerTintColor: "white", // Define a cor do texto da AppBar
          }}
        />
      </Stack.Navigator>
    </NavigationContainer>
  );
};
