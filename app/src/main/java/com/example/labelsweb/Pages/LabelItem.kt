package com.example.labelsweb.Pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.labelsweb.Cases.DisplayXY
import com.example.labelsweb.Clases.Label
import com.example.labelsweb.R
import com.example.labelsweb.ui.theme.BlackLight
import com.example.labelsweb.ui.theme.GreenWhite

@Composable
fun LabelItem(label: Label, displayXY: DisplayXY, color: String, modifier: Modifier) {

    Box(modifier = Modifier.offset(x = label.horizontal.dp, y = label.vertical.dp)){
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
            /*modifier = Modifier.offset(x = label.horizontal.dp, y = label.vertical.dp)*/
        ) {

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                IconButton(
                    onClick = { /*TODO*/ },
                    modifier = Modifier
                        .padding(10.dp)
                        .height(60.dp)
                        .width(108.dp),
                    enabled = false
                ) {
                    Image(
                        painter = painterResource(
                            id =
                            when (color) {
                                "green" -> R.drawable.ic_label_green
                                "white" -> R.drawable.ic_label_white
                                "grey" -> R.drawable.ic_label_black
                                else -> R.drawable.ic_label_white
                            }
                        ),
                        contentDescription = "image"
                    )

                }
                if (displayXY.x * 0.5 < label.horizontal && label.verticalVal) {
                    Text(
                        text = label.name,
                        fontSize = 14.sp,
                        color =
                        when (color) {
                            "green" -> GreenWhite
                            "white" -> Color.White
                            "grey" -> BlackLight
                            else -> GreenWhite
                        },
                        textAlign = TextAlign.Center

                    )
                }
            }
            if (displayXY.x * 0.5 > label.horizontal || !label.verticalVal) {
                Text(
                    text = label.name,
                    fontSize = 18.sp,
                    color =
                    when (color) {
                        "green" -> GreenWhite
                        "white" -> Color.White
                        "grey" -> BlackLight
                        else -> GreenWhite
                    },
                    textAlign = TextAlign.Center
                )
            }
        }
    }

}