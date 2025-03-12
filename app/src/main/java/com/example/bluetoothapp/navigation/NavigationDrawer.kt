package com.example.bluetoothapp.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.bluetoothapp.R
import com.example.bluetoothapp.ui.theme.Red40
import com.example.bluetoothapp.viewmodels.CrashAlertViewModel

enum class NavigationItem(
    @StringRes val titleResId: Int,
    @DrawableRes val iconResId: Int,
    val route: String
) {
    DASHBOARD(R.string.dashboard, R.drawable.baseline_motorcycle_24, "dashboard"),
    DEVICES(R.string.devices_title, R.drawable.baseline_devices_other_24, "devices"),
    BLUETOOTH(R.string.bluetooth, R.drawable.baseline_bluetooth_24, "bluetooth")
}

@Composable
fun AppNavigationDrawer(
    drawerState: DrawerState,
    currentRoute: State<String>,
    unacknowledgedAlertsCount: Int,
    onNavigate: (String) -> Unit,
    content: @Composable () -> Unit
) {
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                modifier = Modifier.fillMaxWidth(0.8f),
                drawerContainerColor = MaterialTheme.colorScheme.surface
            ) {
                // App header in drawer
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .padding(bottom = 8.dp)
                            .clip(RoundedCornerShape(50))
                            .background(Red40)
                            .padding(8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_motorcycle_24),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onPrimary,
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                    
                    Text(
                        text = "Motorcycle Crash Detection",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                    
                    Text(
                        text = "Safety Monitoring System",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
                
                Divider(modifier = Modifier.padding(horizontal = 16.dp))
                Spacer(modifier = Modifier.height(8.dp))
                
                // Navigation items
                NavigationItem.values().forEach { item ->
                    val selected = currentRoute.value == item.route
                    
                    if (item == NavigationItem.DASHBOARD && unacknowledgedAlertsCount > 0) {
                        // Dashboard with badge for unacknowledged alerts
                        NavigationDrawerItem(
                            label = { Text(text = stringResource(id = item.titleResId)) },
                            selected = selected,
                            onClick = { onNavigate(item.route) },
                            icon = {
                                BadgedBox(
                                    badge = {
                                        Badge { 
                                            Text(text = unacknowledgedAlertsCount.toString())
                                        }
                                    }
                                ) {
                                    Icon(
                                        painter = painterResource(id = item.iconResId),
                                        contentDescription = stringResource(id = item.titleResId)
                                    )
                                }
                            },
                            colors = NavigationDrawerItemDefaults.colors(
                                selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                                unselectedContainerColor = MaterialTheme.colorScheme.surface
                            ),
                            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                        )
                    } else {
                        // Regular navigation item without badge
                        NavigationDrawerItem(
                            label = { Text(text = stringResource(id = item.titleResId)) },
                            selected = selected,
                            onClick = { onNavigate(item.route) },
                            icon = {
                                Icon(
                                    painter = painterResource(id = item.iconResId),
                                    contentDescription = stringResource(id = item.titleResId)
                                )
                            },
                            colors = NavigationDrawerItemDefaults.colors(
                                selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                                unselectedContainerColor = MaterialTheme.colorScheme.surface
                            ),
                            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                        )
                    }
                }
            }
        },
        content = content
    )
}