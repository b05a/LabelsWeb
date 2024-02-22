package com.example.labelsweb.Pages

import android.app.PendingIntent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.labelsweb.MainActivity
import com.example.labelsweb.R
import com.example.labelsweb.ViewModel.MainViewModel
import com.example.labelsweb.ui.theme.GrayLight
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainPageContent(
    vm: MainViewModel,
    drawerState: DrawerState,
    scope: CoroutineScope,
    mainActivity: MainActivity,
    permissionIntent: PendingIntent
) {
    Scaffold() {
        it.calculateBottomPadding()

        SideEffect {
            println("hello")
        }
        var modifier =Modifier.rotate(90f)

        Box(modifier = Modifier
            .fillMaxSize()
            .background(GrayLight)) {
            
            AndroidView(factory = {vm.webView}, modifier = Modifier.fillMaxSize(), update = {})


            // кнопка открытия drawer
            IconButton(
                onClick = {
                    scope.launch {
                        drawerState.open()
                    }
                }, modifier = Modifier.align(Alignment.TopStart)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_menu),
                    contentDescription = "image"
                )
            }



            // устанавливаем метки
            for (i in vm.listLabels) {
                if (!i.verticalVal && vm.displayStateVertical.value) continue
                if (i.verticalVal && !vm.displayStateVertical.value) continue
                LabelItem(
                    label = i,
                    displayXY = vm.getDisplaySize(),
                    vm.colorLabel.value.color,
                    if(i.verticalVal){Modifier} else{Modifier.rotate(90f)}
                )
            }



            // окно настроек метки
            if (vm.optionView.value) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomStart)
                ) {
                    OptionsItem(
                        mainActivity,
                        vm
                    )
                }
            }



            // диалоговое окно удаления метки
            if (vm.deleteDialogAlert.value) {
                DeleteAlertDialog(vm = vm)
            }

            // диалоговое окно изменения настроек WiFi
            if(vm.wifiOption.value){
                WiFiOption(vm, mainActivity, permissionIntent)
            }


            // кнопка изменения цвета меток
            Box(modifier = Modifier.align(Alignment.TopEnd)) {
                IconButton(
                    onClick = { vm.changeColorLabel() },
                    modifier = Modifier.defaultMinSize(60.dp)/*.width(60.dp)*/,
                    content = {
                        Text(
                            text = stringResource(id = R.string.colorLabel),
                            color = Color.White,
                            maxLines = 1
                        )
                    })


            }

            if (!vm.optionView.value) {
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(10.dp)
                ) {
                    Row() {
                        Button(onClick = {
                            vm.webView.loadData(vm.pageHtml.mainPage, "text/html", "en_US")
                        }) {
                            Text(modifier = Modifier,text = stringResource(id = R.string.refresh))
                        }
                    }

                }

                if (vm.manuallysynchronizeIP.value){
                    ManuallySynchronizeIPDialog(vm = vm)
                }

                Box(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(10.dp)
                ) {
                    Button(onClick = { vm.changeDisplayStateVertical() }) {
                        Text(modifier = Modifier,text = "Rotate")
                    }
                }
            }
        }

    }



}
