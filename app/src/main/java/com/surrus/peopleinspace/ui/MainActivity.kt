package com.surrus.peopleinspace.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.*
import androidx.lifecycle.lifecycleScope
import androidx.ui.core.Modifier
import androidx.ui.core.setContent
import androidx.ui.foundation.AdapterList
import androidx.ui.foundation.Text
import androidx.ui.layout.Column
import androidx.ui.layout.LayoutPadding
import androidx.ui.layout.padding
import androidx.ui.livedata.observeAsState
import androidx.ui.material.MaterialTheme
import androidx.ui.material.TopAppBar
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.dp
import com.surrus.common.remote.Assignment
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel


@ExperimentalCoroutinesApi
class MainActivity : AppCompatActivity() {
    private val peopleInSpaceViewModel: PeopleInSpaceViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            peopleInSpaceViewModel.peopleInSpace.collect {
                setContent { mainLayout(it) }
            }
        }
    }
}

@Composable
fun mainLayout(peopleInSpace: List<Assignment>) {
    MaterialTheme {
        Column {
            TopAppBar(
                title = {
                    Text("People In Space")
                }
            )
            AdapterList(data = peopleInSpace) { person ->
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