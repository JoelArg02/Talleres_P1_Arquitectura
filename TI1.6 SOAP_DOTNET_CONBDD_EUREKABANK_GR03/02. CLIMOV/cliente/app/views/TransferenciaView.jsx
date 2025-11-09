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
  Modal,
  TouchableOpacity
} from 'react-native';
import { registrarTransferencia } from '../controllers/OperacionController';
import { useNavigation } from '@react-navigation/native';

export default function TransferenciaView() {
  const [origen, setOrigen] = useState('');
  const [destino, setDestino] = useState('');
  const [importe, setImporte] = useState('');
  const [modalVisible, setModalVisible] = useState(false);
  const [estadoTransaccion, setEstadoTransaccion] = useState(null);

  const navigation = useNavigation();

  const validarCampos = () => {
    if (!origen.trim() || !destino.trim() || !importe.trim()) {
      return 'Completa todos los campos';
    }

    if (origen.length !== 8 || destino.length !== 8) {
      return 'Las cuentas deben tener exactamente 8 dígitos';
    }

    if (origen === destino) {
      return 'La cuenta origen y destino deben ser diferentes';
    }

    const monto = parseFloat(importe);
    if (isNaN(monto) || monto <= 0) {
      return 'El importe debe ser un número mayor que cero';
    }

    return null;
  };

  const handleTransferencia = async () => {
    const error = validarCampos();
    if (error) {
      setEstadoTransaccion({ exito: false, mensaje: error });
      setModalVisible(true);
      return;
    }

    try {
      const result = await registrarTransferencia(origen.trim(), destino.trim(), importe.trim());

      if (String(result) === '1') {
        const fechaHora = new Date().toLocaleString();
        setEstadoTransaccion({
          exito: true,
          mensaje: `✅ Transferencia exitosa\n\nCuenta origen: ${origen}\nCuenta destino: ${destino}\nMonto: $${parseFloat(importe).toFixed(2)}\nFecha y hora: ${fechaHora}`
        });
        setOrigen('');
        setDestino('');
        setImporte('');
      } else {
        setEstadoTransaccion({
          exito: false,
          mensaje: 'No se pudo completar la transferencia. Verifica los datos.'
        });
      }

      setModalVisible(true);
    } catch (err) {
      setEstadoTransaccion({
        exito: false,
        mensaje: 'No se pudo realizar la transferencia. Revisa tu conexión o los datos.'
      });
      setModalVisible(true);
    }
  };

  return (
    <KeyboardAvoidingView
      style={styles.screen}
      behavior={Platform.OS === 'ios' ? 'padding' : undefined}
    >
      <ScrollView contentContainerStyle={styles.container}>
        <Text style={styles.title}>Transferencia</Text>

        <TextInput
          style={styles.input}
          placeholder="Cuenta origen (8 dígitos)"
          value={origen}
          onChangeText={setOrigen}
          maxLength={8}
          keyboardType="number-pad"
        />

        <TextInput
          style={styles.input}
          placeholder="Cuenta destino (8 dígitos)"
          value={destino}
          onChangeText={setDestino}
          maxLength={8}
          keyboardType="number-pad"
        />

        <TextInput
          style={styles.input}
          placeholder="Importe a transferir"
          value={importe}
          onChangeText={setImporte}
          keyboardType="decimal-pad"
        />

        <View style={styles.buttonWrapper}>
          <Button title="Transferir fondos" color="#f6a43f" onPress={handleTransferencia} />
        </View>

        <View style={styles.buttonWrapper}>
          <Button
            title="Volver al menú"
            color="#4e88a9"
            onPress={() => navigation.navigate('views/MenuView')}
          />
        </View>
      </ScrollView>

      {/* Modal tipo comprobante */}
      <Modal visible={modalVisible} animationType="slide" transparent>
        <View style={styles.modalBackground}>
          <View style={styles.modalBox}>
            <Text style={styles.modalTitle}>
              {estadoTransaccion?.exito ? '✅ Comprobante' : '❌ Error'}
            </Text>
            <Text style={styles.modalMessage}>{estadoTransaccion?.mensaje}</Text>
            <TouchableOpacity
              style={styles.modalButton}
              onPress={() => setModalVisible(false)}
            >
              <Text style={{ color: 'white', fontWeight: 'bold' }}>Aceptar</Text>
            </TouchableOpacity>
          </View>
        </View>
      </Modal>
    </KeyboardAvoidingView>
  );
}

const styles = StyleSheet.create({
  screen: {
    flex: 1,
    backgroundColor: '#fff3e0'
  },
  container: {
    flexGrow: 1,
    padding: 24,
    margin: 20,
    borderRadius: 16,
    alignItems: 'center',
    backgroundColor: 'white'
  },
  title: {
    fontSize: 26,
    marginBottom: 20,
    color: '#ce8325',
    fontWeight: 'bold',
    textAlign: 'center'
  },
  input: {
    width: '100%',
    maxWidth: 400,
    borderColor: '#ffe0b2',
    borderWidth: 1,
    borderRadius: 12,
    padding: 14,
    marginBottom: 16,
    backgroundColor: '#fff8ed',
    fontSize: 16
  },
  buttonWrapper: {
    width: '100%',
    maxWidth: 400,
    marginVertical: 10,
    borderRadius: 10,
    overflow: 'hidden'
  },
  modalBackground: {
    flex: 1,
    justifyContent: 'center',
    backgroundColor: 'rgba(0,0,0,0.5)',
    padding: 24
  },
  modalBox: {
    backgroundColor: 'white',
    padding: 24,
    borderRadius: 16,
    alignItems: 'center'
  },
  modalTitle: {
    fontSize: 22,
    fontWeight: 'bold',
    marginBottom: 12,
    color: '#35798e'
  },
  modalMessage: {
    fontSize: 16,
    textAlign: 'center',
    marginBottom: 20
  },
  modalButton: {
    backgroundColor: '#4e88a9',
    paddingVertical: 10,
    paddingHorizontal: 24,
    borderRadius: 10
  }
});
