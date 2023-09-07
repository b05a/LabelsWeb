package com.example.labelsweb.Pages

import android.app.PendingIntent
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.runtime.Composable
import com.example.labelsweb.MainActivity
import com.example.labelsweb.ViewModel.MainViewModel
import kotlinx.coroutines.CoroutineScope

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainPage(
    vm: MainViewModel,
    mainActivity: MainActivity,
    drawerState: DrawerState,
    scope: CoroutineScope,
    permissionIntent: PendingIntent
) {
    ModalNavigationDrawer(
        drawerState = drawerState,
        // кнопки дравер лейаут
        drawerContent = { MainDrawerContent(vm, scope, drawerState,mainActivity, permissionIntent) }

    ) {
        // кнопки главного экрана и метки
        MainPageContent(vm,drawerState,scope,mainActivity, permissionIntent)
    }
}