package com.thephoenix.rockpaperscissors.composable


import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.thephoenix.rockpaperscissors.ui.theme.Oswald
import com.thephoenix.rockpaperscissors.ui.theme.appColor


@Composable
fun BarGraph(
    values: List<Float>,
    name: List<String>,
    modifier: Modifier = Modifier,
    barPadding: Dp = 64.dp,
    height: Int,
    title: String
) {

    Column (
        modifier = modifier
            .background(appColor.secondaryContainer)

    ){

        Text(
            modifier = Modifier.padding(16.dp),
            text = title,
            fontSize = 24.sp,
            fontFamily = Oswald,
            fontWeight = FontWeight.Bold,
            color = appColor.onBackground
        )
        Box(
            modifier = Modifier
                .height(height.dp)
                .padding(start = 16.dp, end = 16.dp, top = 16.dp)
        ) {

            val color: Color = appColor.background
            Canvas(modifier = Modifier.fillMaxSize()) {
                val barCount = values.size
                val totalPadding = barPadding * (barCount - 1)
                val barWidth = (size.width - totalPadding.value) / barCount

                for (i in values.indices) {
                    val barHeight = size.height * (values[i] / values.maxOrNull()!!)
                    val startX = i * (barWidth + barPadding.value)
                    val startY = size.height - barHeight
                    val endX = startX + barWidth
                    val endY = size.height

                    drawRoundRect(
                        color = color,
                        size = Size(endX - startX, endY - startY),
                        topLeft = Offset(startX, startY),
                        cornerRadius = CornerRadius(4.dp.toPx())
                    )
                }
            }
            BoxWithConstraints(
                modifier = Modifier.fillMaxSize()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    values.forEachIndexed { index, value ->
                        Box(
                            modifier = Modifier
                                .fillMaxHeight()
                                .padding(
                                    start = if (index == 0) 0.dp else barPadding / 4,
                                    end = if (index == (values.size - 1)) 0.dp else barPadding / 4
                                )
                                .weight(1f)
                        ) {
                            Text(
                                text = value.toInt().toString(),
                                color = appColor.onBackground,
                                modifier = Modifier
                                    .align(Alignment.BottomCenter),
                                fontFamily = Oswald
                            )
                        }
                    }
                }
            }
        }

        BoxWithConstraints(
            modifier = Modifier.fillMaxSize()
                .padding(start = 16.dp, end = 16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                name.forEachIndexed { index, value ->
                    Box(
                        modifier = Modifier
                            .fillMaxHeight()
                            .padding(
                                start = if (index == 0) 0.dp else barPadding / 4,
                                end = if (index == (values.size - 1)) 0.dp else barPadding / 4
                            )
                            .weight(1f)
                    ) {
                        Text(
                            text = value,
                            color = appColor.onBackground,
                            modifier = Modifier
                                .align(Alignment.TopCenter),
                            fontFamily = Oswald,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
        }
    }
}