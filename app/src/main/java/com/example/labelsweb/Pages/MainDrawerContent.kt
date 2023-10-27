package com.example.labelsweb.Pages

import android.app.PendingIntent
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DrawerDefaults
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.labelsweb.MainActivity
import com.example.labelsweb.R
import com.example.labelsweb.ViewModel.MainViewModel
import com.example.labelsweb.ui.theme.GrayDark
import com.example.labelsweb.ui.theme.GrayLight
import com.example.labelsweb.ui.theme.GrayMid
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainDrawerContent(
    vm: MainViewModel,
    scope: CoroutineScope,
    drawerState: DrawerState,
    mainActivity: MainActivity,
    permissionIntent: PendingIntent
) {
    ModalDrawerSheet(
        drawerShape = DrawerDefaults.shape,
        modifier = Modifier,
        drawerContainerColor = Color.Gray,
        drawerTonalElevation = 700.dp
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(GrayLight)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize()
            ) {
                // Кнопка добавления новой метки
                Button(
                    onClick = {
                        vm.addLabel(mainActivity.resources.getString(R.string.newLabel))
                        scope.launch { drawerState.close() }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = GrayDark, contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(15.dp),
                    modifier = Modifier.padding(5.dp)
                ) {
                    Text(text = stringResource(id = R.string.AddaLabel), fontSize = 20.sp)
                }


                // текст "Вертикальный экран"
                Card(
                    colors = CardDefaults.cardColors(containerColor = GrayMid),
                    modifier = Modifier
                        .padding(5.dp)
                        .clip(RoundedCornerShape(15.dp))
                ) {
                    Text(
                        text = stringResource(id = R.string.VerticalScreen),
                        fontSize = 20.sp,
                        modifier = Modifier.padding(5.dp)
                    )
                }

                // если вертикальных меток нет, то паказываем текст "Нет меток"
                if (vm.listLabelsCreate.value) {
                    Log.d("hello", "${vm.listLabelsCreate.value} hello")
                    if (vm.verticalIsEmpty()) {
                        Card(
                            colors = CardDefaults.cardColors(containerColor = GrayLight),
                            modifier = Modifier
                                .padding(5.dp)
                                .clip(RoundedCornerShape(15.dp))
                        ) {
                            Text(
                                text = stringResource(id = R.string.NoLabels),
                                fontSize = 20.sp,
                                fontStyle = FontStyle.Italic,
                                modifier = Modifier.padding(5.dp)
                            )
                        }
                    } else {


                        // кноки вертикальных меток
                        for (i in vm.listLabels) {
                            if (!i.verticalVal) continue
                            Button(
                                onClick = {
                                    if (!vm.editLabelVertical(i)) return@Button

                                    println(vm.changeListNumber)
                                    scope.launch { drawerState.close() }
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = GrayDark,
                                    contentColor = Color.White
                                ),
                                shape = RoundedCornerShape(15.dp),
                                modifier = Modifier.padding(5.dp)
                            ) {
                                Text(text = i.name, fontSize = 20.sp)
                            }
                        }
                    }
                }

                // текст "Горизонтальный экран"
                Card(
                    colors = CardDefaults.cardColors(containerColor = GrayMid),
                    modifier = Modifier
                        .clip(RoundedCornerShape(15.dp))
                ) {
                    Text(
                        text = stringResource(id = R.string.HorizontalScreen),
                        fontSize = 20.sp,
                        modifier = Modifier.padding(5.dp)
                    )
                }

                if (vm.listLabelsCreate.value) {
                    // если горизонтальных меток нет, но показываем текст "Нет меток"
                    if (vm.horizontalIsEmpty()) {
                        Card(
                            colors = CardDefaults.cardColors(containerColor = GrayLight),
                            modifier = Modifier
                                .padding(5.dp)
                                .clip(RoundedCornerShape(15.dp))
                        ) {
                            Text(
                                text = stringResource(id = R.string.NoLabels),
                                fontSize = 20.sp,
                                fontStyle = FontStyle.Italic,
                                modifier = Modifier.padding(5.dp)
                            )
                        }
                    } else {


                        // кнопки горизонтальных меток
                        for (i in vm.listLabels) {
                            if (i.verticalVal) continue
                            Button(
                                onClick = {
                                    if (!vm.editLabelHorizontal(i)) return@Button
                                    scope.launch {
                                        drawerState.close()
                                    }
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = GrayDark,
                                    contentColor = Color.White
                                ),
                                shape = RoundedCornerShape(15.dp),
                                modifier = Modifier.padding(5.dp)
                            ) {
                                Text(text = i.name, fontSize = 20.sp)
                            }
                        }
                    }
                }

            }


            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row() {
                        Button(onClick = {
                            vm.wifiOption.value = true
                            val i = ""
                            Toast.makeText(mainActivity, i, Toast.LENGTH_LONG).show()
                        }) {
                            Text(text = stringResource(id = R.string.WiFiSettings))
                        }
                        Button(onClick = {
//                            val i = mainActivity.getIP(permissionIntent)
                            vm.getIp(permissionIntent)
                        }) {
                            Text(text = stringResource(id = R.string.synchronize))
                        }
                    }

                }

            }
        }


    }
}