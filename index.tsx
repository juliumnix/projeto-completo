import React from 'react';
import {AppRegistry} from 'react-native';

import { App } from './react/Screens/App';


export interface NativeProps {
  message_from_native: string
}


AppRegistry.registerComponent(
  'MyReactNativeApp',
  () => App,
);