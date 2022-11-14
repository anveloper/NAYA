package com.youme.naya.schedule

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.youme.naya.database.entity.Member
import com.youme.naya.database.entity.Schedule
import com.youme.naya.database.entity.relations.ScheduleWithMembers
import com.youme.naya.database.repository.ScheduleRepository
import com.youme.naya.ui.theme.SecondarySystemBlue
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayIn
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class ScheduleMainViewModel @Inject constructor(
    private val repository: ScheduleRepository,
    savedStateHandle: SavedStateHandle,
): ViewModel() {

    private val _schedules = mutableStateOf(emptyList<Schedule>())
    val schedules: State<List<Schedule>> = _schedules

    private val _schedulesAll = mutableStateOf(emptyList<Schedule>())
    val schedulesAll: State<List<Schedule>> = _schedulesAll

    private val _schedulesWithMembers = mutableStateOf(emptyList<ScheduleWithMembers>())
    val schedulesWithMembers: State<List<ScheduleWithMembers>> = _schedulesWithMembers


    private val _selectedDate = mutableStateOf(
        Clock.System.todayIn(TimeZone.currentSystemDefault()).toString())
    val selectedDate: State<String> = _selectedDate

    private var currentScheduleId: Int? = null

    private var recentlyDeletedSchedule: Schedule? = null

    private val _title = mutableStateOf(TextFieldState())
    var title: State<TextFieldState> = _title

    private val _description = mutableStateOf(TextFieldState())
    var description: State<TextFieldState> = _description

    private val _address = mutableStateOf(TextFieldState())
    var address: State<TextFieldState> = _address

    private val _color = mutableStateOf(SecondarySystemBlue.toArgb())
    var color: State<Int> = _color

    private val _isOnAlarm = mutableStateOf(false)
    var isOnAlarm: State<Boolean> = _isOnAlarm

    private val _startTime = mutableStateOf("01 : 00 PM")
    var startTime: State<String> = _startTime

    private val _endTime = mutableStateOf("12 : 00 PM")
    var endTime: State<String> = _endTime

    private val _alarmTime = mutableStateOf("시작 시간")
    var alarmTime: State<String> = _alarmTime

    private val _isDone = mutableStateOf(false)
    val isDone: State<Boolean> = _isDone

    private val _memberName = mutableStateOf(TextFieldState())
    var memberName: State<TextFieldState> = _memberName

    private val _memberType = mutableStateOf(-1)
    var memberType: State<Int> = _memberType

    private val _memberPhone = mutableStateOf(TextFieldState())
    var memberPhone: State<TextFieldState> = _memberPhone

    private val _memberEmail = mutableStateOf(TextFieldState())
    var memberEmail: State<TextFieldState> = _memberEmail

    private val _memberEtcInfo = mutableStateOf(TextFieldState())
    var memberEtcInfo: State<TextFieldState> = _memberEtcInfo

    private val _memberList = mutableStateOf(emptyList<Member>())
    val memberList: State<List<Member>> = _memberList

    private val _memberListForRepo = mutableStateOf(emptyList<Member>())
    val memberListForRepo: State<List<Member>> = _memberListForRepo

    private val _membersForDelete = mutableStateOf(emptyList<Member>())
    val membersForDelete : State<List<Member>> = _membersForDelete


    init {
        viewModelScope.launch {
        repository.getSchedulesByDate(selectedDate.value)
            .collect { schedules ->
                _schedules.value = schedules
            }
            _schedules.value = schedules.value.sortedBy { it.startTime }
        }

        viewModelScope.launch {
            repository.getSchedulesWithMembersByDate(selectedDate.value)
                .collect { schedulesWithMembers ->
                    _schedulesWithMembers.value = schedulesWithMembers
                }
        }
        viewModelScope.launch {
            repository.getSchedules()
                .collect { schedules ->
                    _schedulesAll.value = schedules
                }

        }
        viewModelScope.launch {
            repository.getMembers()
                .collect { member ->
                    _memberListForRepo.value = member
                }

        }

        savedStateHandle.get<Int>("scheduleId")?.let { scheduleId ->
            if (scheduleId != -1) {
                viewModelScope.launch {
                    repository.getScheduleWithMembersById(scheduleId)?.also { schedule ->
                        currentScheduleId = schedule.schedule.scheduleId
                        _title.value = title.value.copy(
                            text = schedule.schedule.title
                        )
                        _description.value = description.value.copy(
                            text = schedule.schedule.description
                        )
                        _address.value = schedule.schedule.address?.let {
                            address.value.copy(
                                text = it
                            )
                        }!!
                        _color.value = schedule.schedule.color
                        _isDone.value = schedule.schedule.isDone
                        _isOnAlarm.value = schedule.schedule.isOnAlarm
                        _startTime.value = schedule.schedule.startTime.toString()
                        _endTime.value = schedule.schedule.endTime.toString()
                        _alarmTime.value = schedule.schedule.alarmTime
                        _selectedDate.value = schedule.schedule.scheduleDate
                        _memberList.value = schedule.members
                    }
                }
            }
        }

    }

    fun getSelectedDate(
        date: String
    ) {
        _selectedDate.value = date
    }


    fun getScheduleList(date: String) {
        viewModelScope.launch {
            repository.getSchedulesByDate(date)
                .collect { schedules ->
                    _schedules.value = schedules
                }
            _schedules.value = schedules.value.sortedBy { it.startTime }
        }
    }

    fun getScheduleWithMembers(date: String) {
        viewModelScope.launch {
            repository.getSchedulesWithMembersByDate(date)
                .collect { schedulesWithMembers ->
                    _schedulesWithMembers.value = schedulesWithMembers
                }
            _schedulesWithMembers.value = schedulesWithMembers.value.sortedBy { it.schedule.startTime }
        }
    }

    fun onDoneChange(scheduleId: Int, isDone: Boolean) {
        viewModelScope.launch {
            repository.getScheduleById(scheduleId)?.also { schedule ->
            repository.insertSchedule(
                schedule.copy(
                    isDone = isDone
                )
            )}
        }
    }

    fun onMemNameChange(EnteredName: String) {
        _memberName.value = memberName.value.copy(
            text = EnteredName
        )
    }

    fun onMemPhoneChange(EnteredPhone: String) {
        _memberPhone.value = memberPhone.value.copy(
            text = EnteredPhone
        )
    }

    fun onMemEmailChange(EnteredEmail: String) {
        _memberEmail.value = memberEmail.value.copy(
            text = EnteredEmail
        )
    }

    fun onMemEtcChange(EnteredEtc: String) {
        _memberEtcInfo.value = memberEtcInfo.value.copy(
            text = EnteredEtc
        )
    }

    fun onTitleChange(EnteredTitle: String) {
        _title.value = title.value.copy(
            text = EnteredTitle
        )
    }

    fun onDescriptionChange(EnteredDescription: String) {
        _description.value = description.value.copy(
            text = EnteredDescription
        )
    }

    fun onAddressChange(EnteredAddress: String) {
        _address.value = address.value.copy(
            text = EnteredAddress
        )
    }

    fun onColorChange(EnteredColor: Int) {
        _color.value = EnteredColor
    }

    fun onAlarmChange() {
        _isOnAlarm.value = !isOnAlarm.value
    }

    fun alarmTimeChange(alarmTime: String) {
        _alarmTime.value = alarmTime
    }

    fun onStartTimeChange(time: String) {
        _startTime.value = time
    }

    fun onEndTimeChange(time: String) {
        _endTime.value = time
    }


    fun deleteSchedule(scheduleId: Int?) {
        viewModelScope.launch {
            if (scheduleId != null) {
                val schedule =  repository.getScheduleById(scheduleId)
                if (schedule != null) {
                    recentlyDeletedSchedule = schedule
                    if (memberList.value != null) {
                        repository.deleteScheduleWithMembers(schedule, memberList.value)
                    } else {
                        repository.deleteSchedule(schedule)
                    }
                }
            }
        }
    }

    fun restoreSchedule() {
        viewModelScope.launch {
            repository.insertSchedule(
                recentlyDeletedSchedule ?: return@launch)
            recentlyDeletedSchedule = null
        }
    }


    fun insertSchedule(schedule: Schedule? = null, selectedDate: String) {
        viewModelScope.launch {
            repository.insertScheduleWithMembers(Schedule(
                title = title.value.text,
                description = description.value.text,
                isDone = isDone.value,
                scheduleId = currentScheduleId,
                address = address.value.text,
                scheduleDate = selectedDate,
                color = color.value,
                isOnAlarm = isOnAlarm.value,
                startTime = startTime.value,
                endTime = endTime.value,
            ),  memberList.value)

        }
    }


    fun insertTemporaryMember(memberType: Int, memberNum: Int, scheduleId: Int) {
        viewModelScope.launch {
            _memberList.value +=
                Member(
                    memberId = null,
                    scheduleId = scheduleId,
                    type = memberType,
                    name = memberName.value.text,
                    phoneNum = memberPhone.value.text,
                    email = memberEmail.value.text,
                    etcInfo = memberEtcInfo.value.text,
                    memberIcon = memberNum
                )
            }
        _memberName.value = memberName.value.copy(
            text = ""
        )
        _memberPhone.value = memberPhone.value.copy(
            text = ""
        )
        _memberEmail.value = memberEmail.value.copy(
            text = ""
        )
        _memberEtcInfo.value = memberEtcInfo.value.copy(
            text = ""
        )
    }

    fun deleteTemporaryMember(memberId: Int) {
        viewModelScope.launch {
            var list = emptyList<Member>()
            for (index in memberList.value.indices) {
                if (index != memberId) list += memberList.value[index]
            }
            _memberList.value = list
        }
    }

    fun deleteMember(member: Member) {
        viewModelScope.launch {
            repository.deleteMember(member)
        }
    }

}