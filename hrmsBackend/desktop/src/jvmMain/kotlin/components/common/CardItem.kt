package components.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import presentation.Colors

@Composable
fun CardItem(text: String, icon: ImageVector, count: Int = 100, modifier: Modifier = Modifier,onClick:()->Unit) {
    Card(
        modifier = modifier.padding(8.dp).size(200.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = 10.dp
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(8.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = Color.Black,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = text,
                style = MaterialTheme.typography.body1,
                color = Colors.bgSideMenu

            )
            Spacer(modifier = Modifier.height(8.dp))
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .background(Color.LightGray, RoundedCornerShape(45.dp))
                    .clickable { onClick() },
                contentAlignment = Alignment.Center,
            ) {
                    Text(
                        text = count.toString(),
                        style = MaterialTheme.typography.h6,
                        color = Color.Black
                    )
                }
            }
        }
    }
