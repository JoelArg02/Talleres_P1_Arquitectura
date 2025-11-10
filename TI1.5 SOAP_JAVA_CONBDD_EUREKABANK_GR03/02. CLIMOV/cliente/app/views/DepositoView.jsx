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
  Pressable
} from 'react-native';
import { regDeposito } from '../controllers/OperacionController';
import { useNavigation } from '@react-navigation/native';
import { XMLParser } from 'fast-xml-parser';

export default function DepositoView() {
  const [cuenta, setCuenta] = useState('');
  const [importe, setImporte] = useState('');
  const [modalVisible, setModalVisible] = useState(false);
  const [mensaje, setMensaje] = useState('');
  const navigation = useNavigation();

  const validarCampos = () => {
    if (!cuenta.trim() || cuenta.trim().length !== 8) {
      setMensaje('La cuenta debe tener exactamente 8 dígitos.');
      setModalVisible(true);
      return false;
    }

    const monto = parseFloat(importe);
    if (!importe.trim() || isNaN(monto) || monto <= 0) {
      setMensaje('El importe debe ser un número mayor que cero.');
      setModalVisible(true);
      return false;
    }

    return true;
  };

  const handleDeposito = async () => {
    if (!validarCampos()) return;

    try {
      const res = await regDeposito(cuenta.trim(), importe.trim());
      const parser = new XMLParser();
      const parsed = parser.parse(res);

      const body = parsed['S:Envelope']?.['S:Body'];
      let resultado = null;

      if (body) {
        for (const key in body) {
          const nodo = body[key];
          if (typeof nodo === 'object' && ('estado' in nodo || 'return' in nodo)) {
            resultado = nodo.estado ?? nodo.return;
            break;
          }
        }
      }

      if (String(resultado).toLowerCase() === 'true' || String(resultado) === '1') {
        setMensaje(`✅ Depósito exitoso\n\nCuenta: ${cuenta}\nMonto: $${parseFloat(importe).toFixed(2)}`);
        setCuenta('');
        setImporte('');
      } else {
        setMensaje('❌ Depósito fallido. Verifica los datos.');
      }

      setModalVisible(true);
    } catch (error) {
      setMensaje('⚠️ Error de conexión o datos inválidos.');
      setModalVisible(true);
    }
  };

  return (
    <KeyboardAvoidingView
      style={styles.screen}
      behavior={Platform.OS === 'ios' ? 'padding' : undefined}
    >
      <ScrollView contentContainerStyle={styles.container}>
        <Text style={styles.title}>Depósito</Text>

        <TextInput
          style={styles.input}
          placeholder="Número de cuenta (8 dígitos)"
          value={cuenta}
          onChangeText={setCuenta}
          maxLength={8}
          keyboardType="number-pad"
          autoCapitalize="none"
        />

        <TextInput
          style={styles.input}
          placeholder="Importe a depositar"
          value={importe}
          onChangeText={setImporte}
          keyboardType="decimal-pad"
        />

        <View style={styles.buttonWrapper}>
          <Button title="Confirmar depósito" color="#5fb4a2" onPress={handleDeposito} />
        </View>

        <View style={styles.buttonWrapper}>
          <Button
            title="Volver al menú"
            color="#4e88a9"
            onPress={() => navigation.navigate('views/MenuView')}
          />
        </View>

        {/* ✅ Modal de comprobante */}
        <Modal
          animationType="fade"
          transparent={true}
          visible={modalVisible}
        >
          <View style={styles.modalOverlay}>
            <View style={styles.modalBox}>
              <Text style={styles.modalText}>{mensaje}</Text>
              <Pressable style={styles.modalButton} onPress={() => setModalVisible(false)}>
                <Text style={styles.modalButtonText}>Cerrar</Text>
              </Pressable>
            </View>
          </View>
        </Modal>
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
    backgroundColor: 'white'
  },
  title: {
    fontSize: 26,
    marginBottom: 20,
    color: '#35798e',
    fontWeight: 'bold',
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
    marginVertical: 10
  },

  // 🧾 Estilos para el Modal (popup)
  modalOverlay: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: 'rgba(0,0,0,0.4)'
  },
  modalBox: {
    backgroundColor: 'white',
    borderRadius: 16,
    padding: 24,
    width: 300,
    alignItems: 'center',
    shadowColor: '#000',
    shadowOpacity: 0.3,
    shadowOffset: { width: 0, height: 2 },
    shadowRadius: 6,
    elevation: 6
  },
  modalText: {
    fontSize: 16,
    textAlign: 'center',
    marginBottom: 20
  },
  modalButton: {
    backgroundColor: '#4e88a9',
    paddingVertical: 10,
    paddingHorizontal: 20,
    borderRadius: 8
  },
  modalButtonText: {
    color: 'white',
    fontWeight: 'bold'
  }
});
