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
import { registrarRetiro } from '../controllers/OperacionController';
import { useNavigation } from '@react-navigation/native';

export default function RetiroView() {
  const [cuenta, setCuenta] = useState('');
  const [importe, setImporte] = useState('');
  const [modalVisible, setModalVisible] = useState(false);
  const [estadoTransaccion, setEstadoTransaccion] = useState(null);

  const navigation = useNavigation();

  const validarCampos = () => {
    if (!cuenta.trim()) {
      return 'Por favor ingresa un número de cuenta';
    }

    if (cuenta.trim().length !== 8) {
      return 'El número de cuenta debe tener exactamente 8 dígitos';
    }

    const monto = parseFloat(importe);
    if (!importe.trim()) {
      return 'Por favor ingresa un importe';
    }

    if (isNaN(monto) || monto <= 0) {
      return 'El importe debe ser un número mayor que cero';
    }

    return null;
  };

  const handleRetiro = async () => {
    const error = validarCampos();
    if (error) {
      setEstadoTransaccion({ exito: false, mensaje: error });
      setModalVisible(true);
      return;
    }

    try {
      const result = await registrarRetiro(cuenta.trim(), importe.trim());

      if (String(result) === '1') {
        const fechaHora = new Date().toLocaleString();
        setEstadoTransaccion({
          exito: true,
          mensaje: `✅ Retiro exitoso\n\nCuenta: ${cuenta}\nMonto: $${parseFloat(importe).toFixed(2)}\nFecha y hora: ${fechaHora}`
        });
        setCuenta('');
        setImporte('');
      } else {
        setEstadoTransaccion({
          exito: false,
          mensaje: 'No se pudo completar el retiro. Verifica los datos.'
        });
      }

      setModalVisible(true);
    } catch (error) {
      setEstadoTransaccion({
        exito: false,
        mensaje: 'No se pudo realizar el retiro. Verifica tu conexión o los datos.'
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
        <Text style={styles.title}>Retiro</Text>

        <TextInput
          style={styles.input}
          placeholder="Número de cuenta (8 dígitos)"
          value={cuenta}
          onChangeText={setCuenta}
          keyboardType="number-pad"
          maxLength={8}
          autoCapitalize="none"
        />

        <TextInput
          style={styles.input}
          placeholder="Importe a retirar"
          value={importe}
          onChangeText={setImporte}
          keyboardType="decimal-pad"
        />

        <View style={styles.buttonWrapper}>
          <Button title="Confirmar retiro" color="#d27575" onPress={handleRetiro} />
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
    backgroundColor: '#fce4ec'
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
    color: '#a84848',
    fontWeight: 'bold',
    textAlign: 'center'
  },
  input: {
    width: '100%',
    maxWidth: 400,
    borderColor: '#f0d0d0',
    borderWidth: 1,
    borderRadius: 12,
    padding: 14,
    marginBottom: 16,
    backgroundColor: '#fceeee',
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
    color: '#a84848'
  },
  modalMessage: {
    fontSize: 16,
    textAlign: 'center',
    marginBottom: 20
  },
  modalButton: {
    backgroundColor: '#d27575',
    paddingVertical: 10,
    paddingHorizontal: 24,
    borderRadius: 10
  }
});
