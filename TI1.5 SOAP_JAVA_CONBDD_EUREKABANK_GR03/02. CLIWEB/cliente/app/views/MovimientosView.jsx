import React, { useState } from 'react';
import {
  View,
  Text,
  TextInput,
  Button,
  StyleSheet,
  ScrollView,
  KeyboardAvoidingView,
  Platform,
  Alert
} from 'react-native';
import { traerMovimientos } from '../controllers/CuentaController';
import { XMLParser } from 'fast-xml-parser';
import { useNavigation } from '@react-navigation/native';

export default function MovimientosView() {
  const [cuenta, setCuenta] = useState('');
  const [movimientos, setMovimientos] = useState([]);
  const navigation = useNavigation();

  const validarCuenta = () => {
    if (!cuenta.trim()) {
      Alert.alert('Validación', 'Por favor ingresa un número de cuenta');
      return false;
    }

    if (cuenta.trim().length !== 8) {
      Alert.alert('Validación', 'El número de cuenta debe tener exactamente 8 dígitos');
      return false;
    }

    return true;
  };

  const handleConsulta = async () => {
    if (!validarCuenta()) return;

    try {
      const response = await traerMovimientos(cuenta.trim());
      const parser = new XMLParser();
      const result = parser.parse(response);

      const items = result['S:Envelope']?.['S:Body']?.['ns2:traerMovimientosResponse']?.movimiento;

      if (!items || !Array.isArray(items)) {
        setMovimientos([]);
        Alert.alert('Sin movimientos', 'No se encontraron movimientos para esta cuenta');
        return;
      }

      const parsed = items.map((mov) => ({
        cuenta: mov.cuenta,
        fecha: mov.fecha.split('T')[0],
        importe: parseFloat(mov.importe),
        tipo: mov.tipo,
        accion: mov.accion,
        nromov: mov.nromov
      }));

      setMovimientos(parsed);
    } catch (err) {
      setMovimientos([]);
      Alert.alert('Error', 'No se pudo consultar los movimientos. Verifica tu conexión o el número de cuenta');
    }
  };

  return (
    <KeyboardAvoidingView
      style={styles.screen}
      behavior={Platform.OS === 'ios' ? 'padding' : undefined}
    >
      <ScrollView contentContainerStyle={styles.container}>
        <Text style={styles.title}>Movimientos de Cuenta</Text>

        <TextInput
          style={styles.input}
          placeholder="Número de cuenta (8 dígitos)"
          value={cuenta}
          onChangeText={setCuenta}
          maxLength={8}
          keyboardType="number-pad"
        />
        <View style={styles.buttonWrapper}>
          <Button title="Consultar" color="#4e88a9" onPress={handleConsulta} />
        </View>

        {movimientos.length > 0 ? (
          movimientos.map((mov, index) => (
            <View
              key={index}
              style={[
                styles.card,
                { borderLeftColor: mov.accion === 'INGRESO' ? '#4caf50' : '#f44336' }
              ]}
            >
              <Text style={styles.tipo}>{mov.tipo} ({mov.accion})</Text>
              <Text style={styles.fecha}>Fecha: {mov.fecha}</Text>
              <Text style={styles.monto}>Monto: ${mov.importe.toFixed(2)}</Text>
              <Text style={styles.codigo}>N° Movimiento: {mov.nromov}</Text>
            </View>
          ))
        ) : (
          <Text style={styles.noData}>No hay movimientos o cuenta inválida.</Text>
        )}

        <View style={{ marginTop: 20 }}>
          <Button
            title="Volver al menú"
            color="#4e88a9"
            onPress={() => navigation.navigate('views/MenuView')}
          />
        </View>
      </ScrollView>
    </KeyboardAvoidingView>
  );
}

const styles = StyleSheet.create({
  screen: {
    flex: 1,
    backgroundColor: '#e0f7fa'
  },
  container: {
    flexGrow: 1,
    padding: 24,
    margin: 20,
    borderRadius: 16,
    alignItems: 'center',
    backgroundColor: 'white',
    shadowColor: '#000',
    shadowOffset: { width: 0, height: 1 },
    shadowOpacity: 0.1,
    shadowRadius: 3,
    elevation: 2
  },
  title: {
    fontSize: 24,
    color: '#35798e',
    fontWeight: 'bold',
    marginBottom: 16,
    textAlign: 'center'
  },
  input: {
    width: '100%',
    maxWidth: 400,
    borderColor: '#cfe0e8',
    borderWidth: 1,
    borderRadius: 12,
    padding: 14,
    marginBottom: 16,
    backgroundColor: '#f0f4f8',
    fontSize: 16
  },
  buttonWrapper: {
    width: '100%',
    maxWidth: 400,
    marginBottom: 20,
    borderRadius: 10,
    overflow: 'hidden'
  },
  card: {
    width: '100%',
    backgroundColor: '#ffffff',
    padding: 16,
    marginBottom: 12,
    borderRadius: 10,
    borderLeftWidth: 6,
    elevation: 2,
    shadowColor: '#00000033',
    shadowOpacity: 0.2,
    shadowRadius: 4
  },
  tipo: {
    fontSize: 16,
    fontWeight: '600',
    marginBottom: 4
  },
  fecha: {
    fontSize: 14,
    color: '#666'
  },
  monto: {
    fontSize: 15,
    fontWeight: 'bold',
    color: '#333'
  },
  codigo: {
    fontSize: 12,
    color: '#999'
  },
  noData: {
    marginTop: 20,
    fontStyle: 'italic',
    color: '#999',
    textAlign: 'center'
  }
});
