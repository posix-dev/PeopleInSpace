import com.surrus.common.remote.Assignment
import react.*
import react.dom.*
import kotlinx.coroutines.*



val App = functionalComponent<RProps> { _ ->
    val appDependencies = useContext(AppDependenciesContext)
    val peopleInSpaceApi = appDependencies.peopleInSpaceApi

    val (people, setPeople) = useState(emptyList<Assignment>())

    useEffectWithCleanup(dependencies = listOf()) {
        val mainScope = MainScope()

        mainScope.launch {
            setPeople(peopleInSpaceApi.fetchPeople().people)
        }
        return@useEffectWithCleanup { mainScope.cancel() }
    }

    h1 {
        +"People In Space"
    }
    ul {
        people.forEach { item ->
            li {
                +"${item.name} (${item.craft})"
            }
        }
    }
}
