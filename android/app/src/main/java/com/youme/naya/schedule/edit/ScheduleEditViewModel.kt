package com.youme.naya.schedule.edit

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.youme.naya.schedule.main.ScheduleMainState
import com.youme.naya.schedule.useCases.DeleteSchedule
import com.youme.naya.schedule.useCases.GetSchedule
import com.youme.naya.schedule.useCases.InsertSchedule
import kotlinx.coroutines.launch
import javax.inject.Inject

class ScheduleEditViewModel @Inject constructor(
    private val deleteSchedule: DeleteSchedule,
    private val getSchedule: GetSchedule,
    private val insertSchedule: InsertSchedule,
    private val savedStateHandle: SavedStateHandle
): ViewModel() {

    private val _title = mutableStateOf(TextFieldState())
    val title: State<TextFieldState> = _title

    private val _content = mutableStateOf(TextFieldState())
    val content: State<TextFieldState> = _content

    private val _scheduleDate = mutableStateOf(TextFieldState())
    val scheduleDate: State<TextFieldState> = _scheduleDate


    private var currentUserId: Int? = null

//    init {
//        savedStateHandle.get<Int>("userId")?.let { userId ->
//            if (userId != -1) {
//                viewModelScope.launch {
//                    getUser(userId)?.also { user ->
//                        currentUserId = user.id
//                        _userName.value = userName.value.copy(
//                            text = user.name
//                        )
//                        _userLastName.value = userLastName.value.copy(
//                            text = user.lastName
//                        )
//                        _userAge.value = userAge.value.copy(
//                            text = user.age.toString()
//                        )
//                    }
//                }
//            }
//        }
//    }

//
//    fun onEvent(event: ScheduleEditEvent) {
//        when (event) {
//            is ScheduleEditEvent.DeleteSchedule -> {
//                viewModelScope.launch {
//                    deleteSchedule(event.schedule)
//                }
//            }
//            is ScheduleEditEvent.EnteredName -> {
//                _userName.value = userName.value.copy(
//                    text = event.value
//                )
//            }
//            is ScheduleEditEvent.EnteredLastName -> {
//                _userLastName.value = userLastName.value.copy(
//                    text = event.value
//                )
//            }
//            is ScheduleEditEvent.EnteredAge -> {
//                _userAge.value = userAge.value.copy(
//                    text = event.value
//                )
//            }
//            ScheduleEditEvent.InsertSchedule -> {
//                viewModelScope.launch {
//                    insertUser(
//                        User(
//                            name = userName.value.text,
//                            lastName = userLastName.value.text,
//                            age = userAge.value.text.toInt(),
//                            id = currentUserId
//                        )
//                    )
//                    _eventFlow.emit(UiEvent.SaveUser)
//                }
//            }
//        }
//        }
//    }


    sealed class UiEvent {
        object SaveSchedule: UiEvent()
    }


}