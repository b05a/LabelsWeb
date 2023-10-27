package com.example.labelsweb.Pages

import android.app.PendingIntent
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.labelsweb.MainActivity
import com.example.labelsweb.R
import com.example.labelsweb.ViewModel.MainViewModel
import com.example.labelsweb.ui.theme.GrayLight

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WiFiOption(vm: MainViewModel, mainActivity: MainActivity, permissionIntent: PendingIntent) {
    Dialog(onDismissRequest = { vm.wifiOption.value = !vm.wifiOption.value }) {
        Box(modifier = Modifier.clip(RoundedCornerShape(10.dp))) {
            Column(
                modifier = Modifier
                    .background(GrayLight)
                    .padding(15.dp)
            ) {
                Row(modifier = Modifier.padding(5.dp)) {
                    Text(text = stringResource(id = R.string.WiFilogin), fontSize = 16.sp)
                    Text(
                        text = stringResource(id = R.string.Now) + if (vm.accValue.value == "") stringResource(
                            id = R.string.noLogin
                        ) else vm.accValue.value,
                        fontSize = 16.sp,
                        modifier = Modifier.padding(start = 10.dp)
                    )
                }
                TextField(
                    value = vm.acc.value,
                    onValueChange = { vm.acc.value = it },
                    modifier = Modifier.padding(5.dp),
                    singleLine = true
                )
                Row(modifier = Modifier.padding(5.dp)) {
                    Text(text = stringResource(id = R.string.wifiPassword), fontSize = 16.sp)
                    Text(
                        text = stringResource(id = R.string.Now) + if (vm.passValue.value == "") stringResource(
                            id = R.string.noPassword
                        ) else vm.passValue.value,
                        fontSize = 16.sp,
                        modifier = Modifier.padding(start = 10.dp)
                    )
                }
                TextField(
                    value = vm.pass.value,
                    onValueChange = { vm.pass.value = it },
                    modifier = Modifier.padding(5.dp),
                    singleLine = true
                )
                Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.padding(5.dp)) {
                    Button(onClick = {
                        val i = vm.setAccPass(permissionIntent)
//                        val i = mainActivity.setAccPass(permissionIntent)
                    }) {
                        Text(text = stringResource(id = R.string.synchronize))
                    }
                }

            }
        }
    }
}