import { View, Text, StyleSheet, FlatList, Pressable } from "react-native";
import React, { useEffect, useState } from "react";
import { NativeModules } from "react-native";
import { Button } from "react-native";
import { HeaderBackButton } from "@react-navigation/elements";
import { RouteProp, useNavigation } from "@react-navigation/native";
import { RootStackParamList } from "./App";
import { StackNavigationProp } from "@react-navigation/stack";

const { NativeFunctions } = NativeModules;

interface Investment {
  id: string;
  name: string;
}
const verifyIsReact = false;

export default function InvestmentFunds() {
  const [investments, setInvestments] = useState<Investment[]>([]);
  const navigation = useNavigation();

  const goBackNative = async () => {
    try {
      await NativeFunctions.goBackNative();
    } catch (error) {
      console.log(error);
    }
  };

  useEffect(() => {
    getDataFromAPI();
    navigation.setOptions({
      title: verifyIsReact ? "Tela React" : "Fundos de Investimentos",
      headerLeft: () => (
        <HeaderBackButton
          onPress={() =>
            navigation.canGoBack() ? navigation.goBack() : goBackNative()
          }
        />
      ),
    });
  }, []);

  const getDataFromAPI = async () => {
    try {
      const response = await NativeFunctions.getAPIData();
      setInvestments(JSON.parse(response));
    } catch (error) {
      console.log(error);
    }
  };

  const navigateToFlutter = async (fundDetail: String) => {
    await NativeFunctions.navigateToFlutter(fundDetail);
  };

  const renderItemList = (item: Investment) => {
    return (
      <View style={styles.containerItem}>
        <Pressable
          onPress={() => {
            navigateToFlutter(item.name);
          }}
        >
          <Text style={styles.textItem}>Fundo: {item.name}</Text>
        </Pressable>
      </View>
    );
  };

  return (
    <View style={styles.container}>
      <FlatList
        data={investments}
        keyExtractor={(item) => item.id}
        renderItem={({ item }) => renderItemList(item)}
      />
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    padding: 20,
    backgroundColor: "#D9D9D9",
  },
  containerItem: {
    backgroundColor: "white",
    borderRadius: 10,
    padding: 20,
    margin: 10,
  },
  textItem: {
    color: "black",
  },
});
