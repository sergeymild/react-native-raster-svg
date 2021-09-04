import * as React from 'react';

import { StyleSheet, View } from 'react-native';
import { RasterSvgView } from 'react-native-raster-svg';

export default function App() {
  return (
    <View style={styles.container}>
      <RasterSvgView
        style={{ width: 100, height: 40 }}
        params={{ source: require('./assets/dd.svg'), width: 200, height: 40 }}
      />
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
  },
  box: {
    width: 60,
    height: 60,
    marginVertical: 20,
  },
});
