package com.example.labelsweb.Pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import com.example.labelsweb.R
import com.example.labelsweb.ViewModel.MainViewModel
import com.example.labelsweb.ui.theme.GrayDark
import com.example.labelsweb.ui.theme.GrayLight

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManuallySynchronizeIPDialog(vm:MainViewModel){
    Dialog(onDismissRequest = { vm.manuallysynchronizeIP.value = !vm.manuallysynchronizeIP.value }) {
        Box(modifier = Modifier.clip(RoundedCornerShape(10.dp))) {
            Column(
                modifier = Modifier
                    .background(GrayLight)
                    .padding(15.dp)
            ) {
                Row(modifier = Modifier.padding(5.dp)) {
                    Text(text = stringResource(id = R.string.ipItem), fontSize = 16.sp)
                }
                TextField(
                    value = vm.ipItem.value,
                    onValueChange = { vm.ipItem.value = it },
                    modifier = Modifier.padding(5.dp),
                    singleLine = true
                )
                Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.padding(5.dp)) {
                    Button(
                        onClick = { vm.manuallySynchronizeIPFun() },
                        colors = ButtonDefaults.buttonColors(GrayDark),
                        shape = RoundedCornerShape(15.dp)
                    ) {
                        Text(text = stringResource(id = R.string.synchronize))
                    }
                    Button(
                        onClick = { vm.manuallysynchronizeIP.value = false }, modifier = Modifier.padding(start = 10.dp),
                        colors = ButtonDefaults.buttonColors(GrayDark),
                        shape = RoundedCornerShape(15.dp)
                    ) {
                        Text(text = stringResource(id = R.string.close))
                    }
                }
            }
        }
    }
}