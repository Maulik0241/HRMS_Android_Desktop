package components.userManagement

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import database.model.EmployeeItem

@Composable
fun EmployeeTable(employeeList: List<EmployeeItem>) {
    var isLoading by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxSize()
            .fillMaxWidth(0.7f)
            .padding(top = 10.dp, end = 10.dp, start = 40.dp),
        backgroundColor = Color.White,
        elevation = 7.dp
    ) {
        Text(
            text = "Employee List",
            fontSize = 24.sp,
            color = Color(0xFF11009E),
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Serif,
            modifier = Modifier.padding(vertical = 10.dp, horizontal = 50.dp),
        )

        Box(modifier = Modifier.fillMaxSize()) {
            Card(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(45.dp),
                backgroundColor = Color(0xFFF5F5F5),
                elevation = 7.dp
            ) {

                Column(modifier = Modifier.fillMaxSize()) {
                    Box(modifier = Modifier.fillMaxWidth()) {
                        HeaderRow()
                    }
                    Divider()

                    Box(
                        modifier = Modifier.weight(1f),
                        contentAlignment = Alignment.TopStart
                    ) {
                        if (isLoading) {
                            // Show loading indicator
                            CircularProgressIndicator(
                                modifier = Modifier.align(Alignment.Center)
                                    .padding(16.dp)
                            )
                        } else {
                            LazyColumn(
                                modifier = Modifier.fillMaxSize(),
                                contentPadding = PaddingValues(16.dp)
                            ) {
                                val sortedEmployeeList = employeeList.sortedBy { it.number }
                                items(sortedEmployeeList) { employee ->
                                    EmployeeRow(employee)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}





