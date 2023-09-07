package com.example.labelsweb.DI

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import com.example.labelsweb.Bluetooth.BluetoothController
import com.example.labelsweb.Cases.ChangeColor
import com.example.labelsweb.Cases.DisplayType
import com.example.labelsweb.Cases.GenerationId
import com.example.labelsweb.Cases.ToastMessage
import com.example.labelsweb.Clases.AccPassManager
import com.example.labelsweb.Clases.AccPassValue
import com.example.labelsweb.Clases.IPManager
import com.example.labelsweb.Clases.PageHtml
import com.example.labelsweb.Clases.USBManager
import com.example.labelsweb.Repository.MainDb
import com.example.labelsweb.Repository.RepositoryRoom
import com.example.labelsweb.ViewModel.MainViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { MainViewModel(get(), get(), get(), get(), get(), get(), get(), get(), get(), get(), get(), get(), get()) }
    single<DisplayType> { DisplayType() }
    single<RepositoryRoom> { RepositoryRoom(get()) }
    single<MainDb> { MainDb.getDb(androidContext()) }
    single<ChangeColor> { ChangeColor() }
    single<Handler> { Handler(Looper.getMainLooper()) }
    single<ToastMessage> { ToastMessage(androidContext()) }
    single<GenerationId> { GenerationId() }
    single<WebView> {
        WebView(androidContext()).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            webViewClient = WebViewClient()
            settings.useWideViewPort = true;
            settings.loadWithOverviewMode = true;
        }
    }
    single<BluetoothAdapter>{ (androidContext().getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager).adapter }
    single<BluetoothController>{ BluetoothController(get()) }
    single<USBManager>{ USBManager(androidContext()) }
    single<PageHtml> { PageHtml() }
    single<AccPassValue> { AccPassValue() }
    single<IPManager>{ IPManager(androidContext()) }
    single<AccPassManager>{AccPassManager(androidContext())}

}