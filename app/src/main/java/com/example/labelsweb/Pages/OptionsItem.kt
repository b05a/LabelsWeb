package com.example.labelsweb.Pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.labelsweb.MainActivity
import com.example.labelsweb.R
import com.example.labelsweb.ViewModel.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OptionsItem(mainActivity: MainActivity, vm: MainViewModel) {
    val focus = LocalFocusManager.current
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .background(
                Color.Gray
            )
    ) {
        // ряд текст, сохранить, удалить
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            // текстовое поле названия
            TextField(
                value = vm.listLabels[vm.changeListNumber].name,
                onValueChange = { vm.changeNameLabel(it) },
                Modifier.weight(2.2f),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = { focus.clearFocus() }),
                singleLine = true
            )
            // кнопка сохранить
            IconButton(
                onClick = { vm.optionView.value = false; vm.saveChange() },
                Modifier.weight(1f)
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_save),
                        contentDescription = "icon"
                    )
                    Text(text = stringResource(id = R.string.save), textAlign = TextAlign.Center)
                }
            }
            // кнопка удалить
            IconButton(onClick = { vm.deleteDialogAlert.value = true }, Modifier.weight(1f)) {
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_delete),
                        contentDescription = "icon2"
                    )
                    Text(text = stringResource(id = R.string.Delete), textAlign = TextAlign.Center)
                }
            }
        }
        // ряд стрелочек вверх, вниз, влево, вправо
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            // стрелочка вверх
            IconButton(
                onClick = { vm.changeUp() }, modifier = Modifier
                    .weight(1f)
                    .height(45.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_up),
                    contentDescription = "icon"
                )
            }
            // стрелочка вниз
            IconButton(
                onClick = { vm.changeDown() }, modifier = Modifier
                    .weight(1f)
                    .height(45.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_down),
                    contentDescription = "icon"
                )
            }
            // стрелочка влево
            IconButton(
                onClick = { vm.changeLeft() }, modifier = Modifier
                    .weight(1f)
                    .height(45.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_left),
                    contentDescription = "icon"
                )
            }
            // стрелочка вправо
            IconButton(
                onClick = { vm.changeRight() }, modifier = Modifier
                    .weight(1f)
                    .height(45.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_right),
                    contentDescription = "icon"
                )
            }
        }
        // ряд стрелочек вверх 10, вниз 10, влево 10, вправо 10
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            // стрелочка вверх 10
            IconButton(
                onClick = { vm.changeUp10() }, modifier = Modifier
                    .weight(1f)
                    .height(45.dp)
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_up),
                        contentDescription = "icon"
                    )
                    Text(text = "10", color = Color.White)
                }

            }
            // стрелочка вниз 10
            IconButton(
                onClick = { vm.changeDown10() }, modifier = Modifier
                    .weight(1f)
                    .height(45.dp)
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_down),
                        contentDescription = "icon"
                    )
                    Text(text = "10", color = Color.White)
                }

            }
            // стрелочка влево 10
            IconButton(
                onClick = { vm.changeLeft10() }, modifier = Modifier
                    .weight(1f)
                    .height(45.dp)
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_left),
                        contentDescription = "icon"
                    )
                    Text(text = "10", color = Color.White)
                }

            }
            // стрелочка вправо 10
            IconButton(
                onClick = { vm.changeRight10() }, modifier = Modifier
                    .weight(1f)
                    .height(45.dp)
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_right),
                        contentDescription = "icon"
                    )
                    Text(text = "10", color = Color.White)
                }

            }
        }
    }
}