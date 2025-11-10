import React from 'react';
import {
  View,
  Text,
  StyleSheet,
  TouchableOpacity,
  Image,
  ScrollView
} from 'react-native';
import { useRouter } from 'expo-router';
import BackgroundWrapper from '../../screens/BackgroundWrapper'; // Ajusta si lo guardas en otra carpeta

export default function MenuView() {
  const router = useRouter();

  return (
    <BackgroundWrapper>
      <View style={styles.overlay}>
        <Text style={styles.title}>Bienvenido a Eureka Bank</Text>
        <Text style={styles.subtitle}>Tu banco. Tu mundo.</Text>

        <ScrollView contentContainerStyle={styles.buttonContainer}>
          <TouchableOpacity style={styles.option} onPress={() => router.push('/views/DepositoView')}>
            <Image source={require('../../assets/images/deposito.png')} style={styles.icon} />
            <Text style={styles.label}>Depósito</Text>
          </TouchableOpacity>

          <TouchableOpacity style={styles.option} onPress={() => router.push('/views/RetiroView')}>
            <Image source={require('../../assets/images/retiro.png')} style={styles.icon} />
            <Text style={styles.label}>Retiro</Text>
          </TouchableOpacity>

          <TouchableOpacity style={styles.option} onPress={() => router.push('/views/TransferenciaView')}>
            <Image source={require('../../assets/images/transferencia.png')} style={styles.icon} />
            <Text style={styles.label}>Transferencia</Text>
          </TouchableOpacity>

          <TouchableOpacity style={styles.option} onPress={() => router.push('/views/MovimientosView')}>
            <Image source={require('../../assets/images/movimientos.png')} style={styles.icon} />
            <Text style={styles.label}>Movimientos</Text>
          </TouchableOpacity>

          <TouchableOpacity
            style={[styles.option, { backgroundColor: '#ffecec', marginTop: 30 }]}
            onPress={() => router.replace('/')}
          >
            <Image
              source={require('../../assets/images/logout.png')}
              style={styles.icon}
            />
            <Text style={[styles.label, { color: '#d04848' }]}>Cerrar sesión</Text>
          </TouchableOpacity>
        </ScrollView>
      </View>
    </BackgroundWrapper>
  );
}

const styles = StyleSheet.create({
  overlay: {
    backgroundColor: 'rgba(255,255,255,0.9)',
    margin: 20,
    padding: 20,
    borderRadius: 20,
    alignItems: 'center',
    flex: 1
  },
  title: {
    fontSize: 26,
    fontWeight: 'bold',
    color: '#4e88a9',
    marginBottom: 8
  },
  subtitle: {
    fontSize: 16,
    color: '#5fb4a2',
    marginBottom: 20
  },
  buttonContainer: {
    flexGrow: 1,
    justifyContent: 'center',
    gap: 20
  },
  option: {
    backgroundColor: '#e6f7f7',
    padding: 16,
    borderRadius: 16,
    width: 260,
    alignItems: 'center',
    flexDirection: 'row',
    gap: 12,
    borderWidth: 1,
    borderColor: '#cfe0e8'
  },
  icon: {
    width: 32,
    height: 32,
    resizeMode: 'contain'
  },
  label: {
    fontSize: 16,
    fontWeight: '600',
    color: '#35798e'
  }
});
