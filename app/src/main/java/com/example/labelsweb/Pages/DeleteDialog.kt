package com.example.labelsweb.Pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.labelsweb.R
import com.example.labelsweb.ViewModel.MainViewModel
import com.example.labelsweb.ui.theme.GrayMid
import com.example.labelsweb.ui.theme.RedLight

@Composable
fun DeleteAlertDialog(vm: MainViewModel) {
//    AlertDialog(
//        onDismissRequest = { vm.deleteDialogAlert.value = !vm.deleteDialogAlert.value },
//        title = { Text(text = "Удалить метку?") },
//        backgroundColor = Color.Gray,
//        modifier = Modifier.clip(RoundedCornerShape(10.dp)),
//        buttons = {
//            Row(modifier = Modifier.padding(10.dp), horizontalArrangement = Arrangement.Center) {
//                Button(
//                    onClick = {
//                        vm.delLabel()
//                    },
//                    colors = ButtonDefaults.buttonColors(backgroundColor = RedLight),
//                    shape = RoundedCornerShape(15.dp),
//                    modifier = Modifier.padding(5.dp)
//                ) {
//                    Text(text = "Удалить", fontSize = 20.sp)
//                }
//                Button(
//                    onClick = { vm.deleteDialogAlert.value = !vm.deleteDialogAlert.value },
//                    colors = ButtonDefaults.buttonColors(backgroundColor = GrayMid),
//                    shape = RoundedCornerShape(15.dp),
//                    modifier = Modifier.padding(5.dp)
//
//                ) {
//                    Text(text = "Отмена", fontSize = 20.sp)
//                }
//            }
//        })
    AlertDialog(
        containerColor = Color.Gray,
        onDismissRequest = { vm.deleteDialogAlert.value = !vm.deleteDialogAlert.value },
        title = {
            Text(
                text = stringResource(id = R.string.removeLabel)
            )
        },
        modifier = Modifier
            .clip(RoundedCornerShape(10.dp)),
        confirmButton = {
            Button(
                onClick = {
                    vm.delLabel()
                },
                colors = ButtonDefaults.buttonColors(containerColor = RedLight),
                shape = RoundedCornerShape(15.dp),
                modifier = Modifier.padding(5.dp)
            ) {
                Text(text = stringResource(id = R.string.DeleteLabel), fontSize = 20.sp)
            }
        },
        dismissButton = {
            Button(
                onClick = { vm.deleteDialogAlert.value = !vm.deleteDialogAlert.value },
                colors = ButtonDefaults.buttonColors(containerColor = GrayMid),
                shape = RoundedCornerShape(15.dp),
                modifier = Modifier.padding(5.dp)
            ) {
                Text(text = stringResource(id = R.string.Cancel), fontSize = 20.sp)
            }
        }
    )
}