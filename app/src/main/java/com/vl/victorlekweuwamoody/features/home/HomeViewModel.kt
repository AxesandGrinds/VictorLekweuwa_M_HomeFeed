package com.vl.victorlekweuwamoody.features.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vl.victorlekweuwamoody.api.models.HomeResponse
import com.vl.victorlekweuwamoody.data.HomeRepository
import com.vl.victorlekweuwamoody.util.HomeState
import com.vl.victorlekweuwamoody.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: HomeRepository
) : ViewModel() {

    /*
    This loading variables tells the Splash screen how long it should wait before moving to the
    Home Screen
    */
    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    // This state is used to time when the Splash Screen should disappear.
    init {
        viewModelScope.launch {
            delay(1000)
            _isLoading.value = false
        }
    }

    /*
    This home data variable is used to store the home page response from the T-Mobile Test
    */
    var _homeData = MutableLiveData<HomeResponse?>()
    var homeData: LiveData<HomeResponse?> = _homeData

    /*
    This state variable is a simple way used to update the state of the view model.
    There are three states:
    HomeState.Loading: The network call is still loading information
    HomeState.Error: An error occurred while fetching the information
    HomeState.Success: The network call was successful and we have received the data
    */
    var _state = MutableLiveData<HomeState>(HomeState.Initial)
    var state: LiveData<HomeState?> = _state

    /*
    * This is the error message from the serve incase there is an error.
    * */
    var _networkErrorMessage = MutableLiveData<String>()
    var networkErrorMessage: LiveData<String> = _networkErrorMessage

    /*
    This function fetches the home feed data from the API.
    */
    fun getHomeFeed() = viewModelScope.launch {

        println("ATTENTION ATTENTION: From Get Home Feed - Function have started network call.")

        repository.getHomeFeed().collectLatest {

            when (it) {
                is Resource.Loading -> {
                    _state.value = HomeState.Loading
                }
                is Resource.Error -> {
                    println("ATTENTION ATTENTION: Get Error Home Response: ${it.error?.message}")
                    _networkErrorMessage.value = it.error?.message
                    _state.value = HomeState.Error
                    if (it.data != null) {
                        _homeData.value = it.data
                        _state.value = HomeState.Success
                    }
                }
                is Resource.Success -> {
                    println("ATTENTION ATTENTION: Get Successful Home Response: ${it.data}")
                    _homeData.value = it.data
                    _state.value = HomeState.Success
                }
            }

        }

    }

    /*
    * This function resets the state of the home feed to the initial state.
    * The use of this is so that the view model is not in a loading or error state
    * when the user navigates back to the Home Screen and the app is reopened.
    * In a future rendition, if the user clicks on a card, and returns to the home screen
    * then this function would be used to reset the data after the user comes back
    * to the home screen.
    */
    fun resetState() {
        _state.value = HomeState.Initial
    }

}