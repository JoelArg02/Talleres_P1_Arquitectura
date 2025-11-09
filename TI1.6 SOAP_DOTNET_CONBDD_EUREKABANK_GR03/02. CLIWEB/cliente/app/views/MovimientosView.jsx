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
  Alert,
  useWindowDimensions
} from 'react-native';
import { traerMovimientos } from '../controllers/CuentaController';
import { useNavigation } from '@react-navigation/native';

export default function MovimientosView() {
  const [cuenta, setCuenta] = useState('');
  const [movimientos, setMovimientos] = useState([]);
  const [saldoTotal, setSaldoTotal] = useState(null);
  const navigation = useNavigation();
  const { width } = useWindowDimensions();

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
      const data = await traerMovimientos(cuenta.trim());

      if (!Array.isArray(data) || data.length === 0) {
        setMovimientos([]);
        setSaldoTotal(null);
        Alert.alert('Sin movimientos', 'No se encontraron movimientos para esta cuenta');
        return;
      }

      const parsed = data
        .map((mov) => ({
          cuenta: mov['a:Cuenta'],
          fecha: new Date(mov['a:Fecha']).toLocaleString(),
          importe: parseFloat(mov['a:Importe']),
          tipo: mov['a:Tipo'],
          accion: mov['a:Accion'],
          nromov: parseInt(mov['a:NroMov'])
        }))
        .sort((a, b) => b.nromov - a.nromov);

      setMovimientos(parsed);

      const total = parsed.reduce((acc, mov) => {
        return mov.accion === 'INGRESO' ? acc + mov.importe : acc - mov.importe;
      }, 0);
      setSaldoTotal(total);
    } catch (err) {
      console.error(err);
      setMovimientos([]);
      setSaldoTotal(null);
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

        {saldoTotal !== null && (
          <View style={styles.saldoBox}>
            <Text style={styles.saldoTexto}>Saldo actual:</Text>
            <Text style={styles.saldoCantidad}>${saldoTotal.toFixed(2)}</Text>
          </View>
        )}

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
          Platform.OS === 'web' && width >= 768 ? (
            <View style={styles.table}>
              <View style={[styles.row, styles.header]}>
                <Text style={styles.cell}>N°</Text>
                <Text style={styles.cell}>Fecha</Text>
                <Text style={styles.cell}>Tipo</Text>
                <Text style={styles.cell}>Acción</Text>
                <Text style={styles.cell}>Importe</Text>
                <Text style={styles.cell}>Cuenta</Text>
              </View>
              {movimientos.map((mov, i) => (
                <View key={i} style={styles.row}>
                  <Text style={styles.cell}>{mov.nromov}</Text>
                  <Text style={styles.cell}>{mov.fecha}</Text>
                  <Text style={styles.cell}>{mov.tipo}</Text>
                  <Text style={styles.cell}>{mov.accion}</Text>
                  <Text style={styles.cell}>${mov.importe.toFixed(2)}</Text>
                  <Text style={styles.cell}>{mov.cuenta}</Text>
                </View>
              ))}
            </View>
          ) : (
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
                <Text style={styles.codigo}>Cuenta: {mov.cuenta}</Text>
                <Text style={styles.codigo}>Movimiento N°: {mov.nromov}</Text>
              </View>
            ))
          )
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
  screen: { flex: 1, backgroundColor: '#e0f7fa' },
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
  tipo: { fontSize: 16, fontWeight: '600', marginBottom: 4 },
  fecha: { fontSize: 14, color: '#666' },
  monto: { fontSize: 15, fontWeight: 'bold', color: '#333' },
  codigo: { fontSize: 13, color: '#555' },
  noData: {
    marginTop: 20,
    fontStyle: 'italic',
    color: '#999',
    textAlign: 'center'
  },
  table: { width: '100%', marginTop: 20 },
  header: { backgroundColor: '#4e88a9' },
  row: {
    flexDirection: 'row',
    justifyContent: 'space-around',
    paddingVertical: 8,
    borderBottomWidth: 1,
    borderColor: '#ccc'
  },
  cell: { flex: 1, textAlign: 'center', fontSize: 14, color: '#333' },
  saldoBox: {
    marginBottom: 16,
    padding: 16,
    backgroundColor: '#e8f5e9',
    borderRadius: 12,
    alignItems: 'center',
    borderColor: '#81c784',
    borderWidth: 1,
    width: '100%',
    maxWidth: 400
  },
  saldoTexto: {
    fontSize: 16,
    color: '#388e3c',
    fontWeight: '600',
    marginBottom: 4
  },
  saldoCantidad: {
    fontSize: 22,
    color: '#2e7d32',
    fontWeight: 'bold'
  }
});
