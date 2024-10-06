package cz.lastaapps.carouselcrashreport

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.carousel.HorizontalUncontainedCarousel
import androidx.compose.material3.carousel.rememberCarouselState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cz.lastaapps.carouselcrashreport.ui.theme.CarouselCrashReportTheme
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CarouselCrashReportTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(modifier = Modifier.padding(innerPadding)) {
                        var list by remember { mutableStateOf(persistentListOf("0", "1", "2")) }
                        BugReport(
                            list,
                            {
                                list = list.add(list.size.toString())
                            },
                            {
                                list.takeUnless { it.isEmpty() }
                                    ?.let { list = it.removeAt(it.size - 1) }
                            }
                        )
                    }
                }
            }
        }
    }
}

@Suppress("UnusedReceiverParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ColumnScope.BugReport(
    list: PersistentList<String>,
    onAdd: () -> Unit,
    onRemove: () -> Unit,
) {
    val state = rememberCarouselState { list.size }
    HorizontalUncontainedCarousel(state, itemWidth = 100.dp) { index ->
        Box(
            modifier = Modifier.size(100.dp),
            contentAlignment = Alignment.Center,
        ) {
            Text(list[index])
        }
    }

    Button(onAdd) {
        Text("Add item")
    }
    Button(onRemove) {
        Text("Drop last item")
    }
}
