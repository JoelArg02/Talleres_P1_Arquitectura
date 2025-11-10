import { Drawer } from 'expo-router/drawer';

export default function Layout() {
  return (
    <Drawer
      screenOptions={{
        headerStyle: { backgroundColor: '#eaf6f6' },
        headerTintColor: '#35798e',
        drawerStyle: { backgroundColor: '#f0f4f8' },
      }}
    >
      {/* LOGIN OCULTO */}
      <Drawer.Screen
        name="index"
        options={{
          drawerItemStyle: { display: 'none' }, // oculta del menú
          headerShown: false                    // oculta la barra superior
        }}
      />

      {/* VISTAS NORMALES */}
      <Drawer.Screen name="views/MenuView" options={{ title: 'Menú Principal' }} />
      <Drawer.Screen name="views/DepositoView" options={{ title: 'Depósito' }} />
      <Drawer.Screen name="views/RetiroView" options={{ title: 'Retiro' }} />
      <Drawer.Screen name="views/TransferenciaView" options={{ title: 'Transferencia' }} />
      <Drawer.Screen name="views/MovimientosView" options={{ title: 'Movimientos' }} />
    </Drawer>
  );
}
