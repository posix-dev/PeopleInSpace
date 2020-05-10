package com.surrus.peopleinspace.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.*
import androidx.ui.core.Modifier
import androidx.ui.core.setContent
import androidx.ui.foundation.AdapterList
import androidx.ui.foundation.Text
import androidx.ui.layout.Column
import androidx.ui.layout.padding
import androidx.ui.material.MaterialTheme
import androidx.ui.material.TopAppBar
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.dp
import com.surrus.common.remote.Assignment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel
import kotlin.coroutines.CoroutineContext


@ExperimentalCoroutinesApi
class MainActivity : AppCompatActivity() {
    private val peopleInSpaceViewModel: PeopleInSpaceViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            mainLayout(peopleInSpaceViewModel)
        }
    }
}

@Composable
fun mainLayout(peopleInSpaceViewModel: PeopleInSpaceViewModel) {
    val peopleState = peopleInSpaceViewModel.peopleInSpace.collectAsState(emptyList())

    MaterialTheme {
        Column {
            TopAppBar(
                title = {
                    Text("People In Space")
                }
            )
            AdapterList(data = peopleState.value) { person ->
                Row(person)
            }
        }
    }
}


@Composable
fun Row(person: Assignment) {
    Text(
        text = "${person.name} (${person.craft})",
        modifier = Modifier.padding(16.dp)
    )
}



@Preview
@Composable
fun DefaultPreview() {
    MaterialTheme {
        Row(Assignment("ISS", "John O'Reilly"))
    }
}


@Composable
fun <T> Flow<T>.collectAsState(
    initial: T,
    context: CoroutineContext = Dispatchers.Main
): MutableState<T> {
    val state = state { initial }
    onPreCommit(this, context) {
        val job = CoroutineScope(context).launch {
            collect {
                FrameManager.framed {
                    state.value = it
                }
            }
        }
        onDispose { job.cancel() }
    }
    return state
}