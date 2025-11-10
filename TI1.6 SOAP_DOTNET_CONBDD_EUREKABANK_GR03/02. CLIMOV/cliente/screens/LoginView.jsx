import React, { useState } from 'react';
import {
  View,
  TextInput,
  Button,
  Text,
  StyleSheet,
  Image,
  Alert,
  TouchableOpacity,
  Platform,
  KeyboardAvoidingView
} from 'react-native';
import { login } from '../app/controllers/AuthController';
import { useRouter } from 'expo-router';

export default function LoginView() {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [secure, setSecure] = useState(true);
  const router = useRouter();

  const handleLogin = async () => {
    if (!username.trim() || !password) {
      Alert.alert('Validación', 'Por favor, completa todos los campos');
      return;
    }

    try {
      const success = await login(username.trim(), password);
      if (success) {
        router.replace('/views/MenuView');
      } else {
        Alert.alert('Error', 'Credenciales incorrectas');
      }
    } catch {
      Alert.alert('Error', 'No se pudo conectar al servidor');
    }
  };

  return (
    <KeyboardAvoidingView
      style={styles.container}
      behavior={Platform.OS === 'ios' ? 'padding' : undefined}
    >
      <Image source={require('../assets/images/logo_monster.png')} style={styles.logo} />
      <Text style={styles.title}>Eureka Bank</Text>

      <View style={styles.inputWrapper}>
        <TextInput
          style={styles.input}
          placeholder="Usuario"
          value={username}
          onChangeText={setUsername}
          placeholderTextColor="#888"
          autoCapitalize="none"
        />
      </View>

      <View style={styles.inputWrapper}>
        <TextInput
          style={styles.input}
          placeholder="Contraseña"
          secureTextEntry={secure}
          value={password}
          onChangeText={setPassword}
          placeholderTextColor="#888"
          autoCapitalize="none"
        />
        <TouchableOpacity onPress={() => setSecure(!secure)} style={styles.toggle}>
          <Text style={styles.toggleText}>{secure ? 'Mostrar' : 'Ocultar'}</Text>
        </TouchableOpacity>
      </View>

      <View style={styles.buttonContainer}>
        <Button title="INGRESAR" color="#5fb4a2" onPress={handleLogin} />
      </View>
    </KeyboardAvoidingView>
  );
}

const styles = StyleSheet.create({
  container: {
    padding: 24,
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: '#fff'
  },
  logo: {
    width: 150,
    height: 60,
    resizeMode: 'contain',
    marginBottom: 10
  },
  title: {
    fontSize: 26,
    fontWeight: '600',
    color: '#4e88a9',
    textAlign: 'center',
    marginBottom: 24
  },
  inputWrapper: {
    width: '90%',
    maxWidth: 400,
    marginBottom: 16,
    position: 'relative'
  },
  input: {
    backgroundColor: '#f0f4f8',
    padding: 14,
    borderRadius: 12,
    borderColor: '#cfe0e8',
    borderWidth: 1,
    color: '#333',
    paddingRight: 80
  },
  toggle: {
    position: 'absolute',
    right: 14,
    top: '25%'
  },
  toggleText: {
    color: '#4e88a9',
    fontWeight: '500'
  },
  buttonContainer: {
    width: '90%',
    maxWidth: 400,
    marginTop: 8,
    borderRadius: 10,
    overflow: 'hidden'
  }
});
