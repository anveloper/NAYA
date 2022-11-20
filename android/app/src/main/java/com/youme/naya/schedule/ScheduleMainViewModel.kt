package com.youme.naya.schedule

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.toArgb
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.*
import com.youme.naya.database.entity.Alarm
import com.youme.naya.database.entity.Member
import com.youme.naya.database.entity.Schedule
import com.youme.naya.database.entity.relations.ScheduleWithMembers
import com.youme.naya.database.repository.ScheduleRepository
import com.youme.naya.ui.theme.SecondarySystemBlue
import com.youme.naya.utils.AppModule
import com.youme.naya.utils.NotificationWorker
import com.youme.naya.widgets.home.ViewCard
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayIn
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.*
import java.util.Optional.empty
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class ScheduleMainViewModel @Inject constructor(
    private val repository: ScheduleRepository,
    savedStateHandle: SavedStateHandle,
): ViewModel() {

    // 해당 시간의 스케줄
    private val _schedules = mutableStateOf(emptyList<Schedule>())
    val schedules: State<List<Schedule>> = _schedules

    // 해당 시간의 스케줄
    private val _schedulesAll = mutableStateOf(emptyList<Schedule>())
    val schedulesAll: State<List<Schedule>> = _schedulesAll

    private val _schedulesWithMembers = mutableStateOf(emptyList<ScheduleWithMembers>())
    val schedulesWithMembers: State<List<ScheduleWithMembers>> = _schedulesWithMembers


    private val _selectedDate = mutableStateOf(
        Clock.System.todayIn(TimeZone.currentSystemDefault()).toString())
    val selectedDate: State<String> = _selectedDate

    var currentScheduleId: Int? = null

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

    private val _cardUri = mutableStateOf("")
    var cardUri: MutableState<String> = _cardUri

    private val _nuyaType = mutableStateOf(-1)
    var nuyaType: MutableState<Int> = _nuyaType


    init {
        createNotificationChannel(context = AppModule.appContext)
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
                        _startTime.value = schedule.schedule.startTime
                        _endTime.value = schedule.schedule.endTime
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
            repository.getScheduleWithMembersById(scheduleId)?.also {
                repository.insertScheduleWithMembers(
                    it.schedule.copy(
                        isDone = isDone
                    ),
                    it.members
                )
            }
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

    fun onUriChange(uri: String) {
        cardUri.value = uri
    }

    fun onNuyaTypeChange(type: Int) {
        nuyaType.value = type
    }

    // 스케줄 삭제

    // 복구
    fun restoreSchedule() {
        viewModelScope.launch {
            repository.insertSchedule(
                recentlyDeletedSchedule ?: return@launch)
            recentlyDeletedSchedule = null
        }
    }

    fun deleteSchedule(scheduleId: Int?) {
        viewModelScope.launch {
            if (scheduleId != null) {
                val schedule =  repository.getScheduleById(scheduleId)
                if (schedule != null) {
                    recentlyDeletedSchedule = schedule
                    repository.deleteSchedule(schedule)
                }
            }
        }
    }

    // 스케줄 추가/수정

    // Day2 받은 날짜 (알람을 위한 변환)
    private fun compareTime(Day: String) : Long {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm")
        val startDate =  Calendar.getInstance().apply {
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }.time.time
        val endDate = dateFormat.parse(Day).time
        val compareDate = (endDate - startDate) / (60 * 1000)
        return compareDate
    }

    fun insertSchedule(schedule: Schedule? = null, selectedDate: String, scheduleId: Int?) {
        viewModelScope.launch {
            if (scheduleId == null) {
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
                    alarmTime = alarmTime.value,
                    endTime = endTime.value,
                ),  memberList.value)
            } else {
                createScheduleMadeNotification(selectedDate, startTime.value, title.value.text)
                repository.insertScheduleWithMembers(Schedule(
                    title = title.value.text,
                    description = description.value.text,
                    isDone = isDone.value,
                    scheduleId = scheduleId,
                    address = address.value.text,
                    scheduleDate = selectedDate,
                    color = color.value,
                    isOnAlarm = isOnAlarm.value,
                    startTime = startTime.value,
                    alarmTime = alarmTime.value,
                    endTime = endTime.value,
                ),  memberList.value)
            }
            if (isOnAlarm.value) {
                if (alarmTime.value == "종료 시간") {
                    val reservationHour =
                        if (endTime.value.substring(8,10) == "PM" && endTime.value.substring(0,2).toInt() != 12) {
                            (endTime.value.substring(0,2).toInt() + 12).toString()
                        }
                        else if (endTime.value.substring(8,10) == "AM" && endTime.value.substring(0,2).toInt() == 12) {
                            "00"
                        } else {
                            endTime.value.substring(0,2).toInt().toString()
                        }
                    val reservationMinute = endTime.value.substring(5,7)
                    val reservationTime = selectedDate + " ${reservationHour}:${reservationMinute}"
                    var compareTimeResult = compareTime(reservationTime)
                    if (compareTimeResult < 0) {
                        createEndErrorMadeNotification(selectedDate, compareTimeResult.toString(),title.value.text)
                    } else {
                        if (scheduleId == null) {
                            currentScheduleId?.let {
                                setOneTimeNotification(
                                    it,
                                    compareTimeResult,selectedDate,
                                    compareTimeResult.toString(),title.value.text,
                                    0,  alarmTime.value, color.value)
                            }
                        }  else {
                            setOneTimeNotification(scheduleId,
                                compareTimeResult,selectedDate,
                                compareTimeResult.toString(),title.value.text, 0,
                                alarmTime.value, color.value)
                        }

                    }
                } else {
                    val reservationHour =
                        if (startTime.value.substring(8,10) == "PM" && startTime.value.substring(0,2).toInt() != 12) {
                            (startTime.value.substring(0,2).toInt() + 12).toString()
                        } else if (startTime.value.substring(8,10) == "AM" && startTime.value.substring(0,2).toInt() == 12) {
                            "00"
                        } else {
                            startTime.value.substring(0,2).toInt().toString()
                        }
                    val reservationMinute = startTime.value.substring(5,7)
                    val reservationTime = selectedDate + " ${reservationHour}:${reservationMinute}"
                    var compareTimeResult = compareTime(reservationTime)
                    when {
                        (alarmTime.value == "1시간 전") -> compareTimeResult -= 60
                        (alarmTime.value == "3시간 전") -> compareTimeResult -= 180
                    }
                    if (compareTimeResult < 0) {
                        createStartErrorMadeNotification(selectedDate, compareTimeResult.toString(),title.value.text)
                    } else {
                        if (scheduleId == null) {
                            currentScheduleId?.let {
                                setOneTimeNotification(
                                    it,
                                    compareTimeResult,
                                    selectedDate,
                                    compareTimeResult.toString(),title.value.text, 1,
                                    alarmTime.value,
                                    color.value
                                )
                            }
                        } else {
                            setOneTimeNotification(scheduleId,
                                compareTimeResult,selectedDate, compareTimeResult.toString(),
                                title.value.text,
                                1,
                                alarmTime.value,
                                color.value
                            )
                        }
                    }
                }

            }
        }

    }

    fun insertTemporaryMember(
        memberType: Int,
        memberNum: Int,
        scheduleId: Int,
    ) {
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
                    memberIcon = memberNum,
                    cardUri = cardUri?.value,
                    nuyaType = nuyaType.value
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
        _cardUri.value = ""
        _nuyaType.value = -1
    }

    fun insertAlarm(title: String, content: String, date: String, color: Int) {
        val current = java.time.LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH시 mm분 ss초")
        val formatted = current.format(formatter)
        viewModelScope.launch {
            repository.insertAlarm(Alarm(
                alarmId = null,
                title = title,
                content = content,
                date = date,
                color = color,
                alarmTime = formatted
            )
            )
        }

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


    private fun createScheduleMadeNotification(selectedDate: String, startTime: String, title: String) {
        val notificationId = 2
        val builder = NotificationCompat.Builder(AppModule.appContext, "CHANNEL_ID")
            .setSmallIcon(com.youme.naya.R.drawable.ic_launcher_foreground)
            .setContentTitle("일정 생성 완료")
            .setContentText("$selectedDate  ${startTime},\n" +
                    "${title}이 시작됩니다." )
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        with(NotificationManagerCompat.from(AppModule.appContext)) {
            notify(notificationId, builder.build())
        }
    }

    private fun createEndErrorMadeNotification(selectedDate: String, startTime: String, title: String) {
        val notificationId = 2
        val builder = NotificationCompat.Builder(AppModule.appContext, "CHANNEL_ID")
            .setSmallIcon(com.youme.naya.R.drawable.ic_launcher_foreground)
            .setContentTitle("이미 종료된 일정")
            .setContentText("$selectedDate  ${startTime},\n" +
                    "${title}이 이미 종료되었습니다." )
            .setPriority(NotificationCompat.PRIORITY_HIGH)
        with(NotificationManagerCompat.from(AppModule.appContext)) {
            notify(notificationId, builder.build())
        }
    }

    private fun createStartErrorMadeNotification(selectedDate: String, startTime: String, title: String) {
        val notificationId = 2
        val builder = NotificationCompat.Builder(AppModule.appContext, "CHANNEL_ID")
            .setSmallIcon(com.youme.naya.R.drawable.ic_launcher_foreground)
            .setContentTitle("알림 시간이 지난 일정")
            .setContentText("${title} 알림 시간이 이미 지났습니다." )
            .setPriority(NotificationCompat.PRIORITY_HIGH)
        with(NotificationManagerCompat.from(AppModule.appContext)) {
            notify(notificationId, builder.build())
        }
    }

    private fun setOneTimeNotification(scheduleId: Int,
                                       time: Long,
                                       selectedDate: String,
                                       startTime: String, title: String,
                                       type: Int, alarmTIme: String,
                                       color: Int
    ) {
        val workManager = WorkManager.getInstance(AppModule.appContext)
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.NOT_REQUIRED)
            .build()

        val notificationWorker = OneTimeWorkRequestBuilder<NotificationWorker>()
            .setInitialDelay(time, TimeUnit.MINUTES)
            .setConstraints(constraints)
            .build()

        workManager.enqueue(notificationWorker)

        //Monitoring for state of work
        workManager.getWorkInfoByIdLiveData(notificationWorker.id)
            .observeForever { workInfo ->
                if (workInfo.state == WorkInfo.State.SUCCEEDED) {
                    when {
                        (type == 0) ->  createEndNotification(scheduleId, selectedDate, startTime, title, color)
                        (type == 1 && alarmTIme == "시작 시간") ->
                            createStartNotification(scheduleId, selectedDate, startTime, title, alarmTIme, color)
                        (type == 1 && alarmTIme == "1시간 전") ->
                            createStartNotification(scheduleId, selectedDate, startTime, title, alarmTIme, color)
                        (type == 1 && alarmTIme == "3시간 전") ->
                            createStartNotification(scheduleId, selectedDate, startTime, title, alarmTIme, color)
                    }

                }
            }
    }

    private fun createNotificationChannel(context: Context) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "일정 알림"
            val descriptionText = "일정에 대한 정보를 제공합니다."
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("CHANNEL_ID", name, importance).apply {
                description = descriptionText
            }
            // register the channel with the system
            val notificationManager: NotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun createStartNotification(scheduleId: Int,
                                        selectedDate: String,
                                        startTime: String,
                                        title: String,
                                        alarmTime: String,
                                        color : Int
        ) {

        val builder = NotificationCompat.Builder(AppModule.appContext, "CHANNEL_ID")
            .setSmallIcon(com.youme.naya.R.drawable.ic_launcher_foreground)
            .setContentTitle("일정 알림")
            .setContentText( when (alarmTime) {
                "시작 시간" -> "${title}이 곧 시작됩니다."
                "1시간 전" -> "1시간 후 ${title}이 시작됩니다."
                "3시간 전" -> "3시간 후 ${title}이 시작됩니다."
                else -> ""
            })
            .setPriority(NotificationCompat.PRIORITY_HIGH)
        with(NotificationManagerCompat.from(AppModule.appContext)) {
            //notificationId is unique for each notification that you define
            notify(scheduleId, builder.build())
        }
        insertAlarm(
            title = "일정 알림",
            content =  when (alarmTime) {
                "시작 시간" -> "${title}이 곧 시작됩니다."
                "1시간 전" -> "1시간 후 ${title}이 시작됩니다."
                "3시간 전" -> "3시간 후 ${title}이 시작됩니다."
                else -> ""
            },
            date = selectedDate,
            color = color
        )

    }

    private fun createEndNotification(scheduleId: Int, selectedDate: String,
                                      startTime: String, title: String, color : Int) {
        val builder = NotificationCompat.Builder(AppModule.appContext, "CHANNEL_ID")
            .setSmallIcon(com.youme.naya.R.drawable.ic_launcher_foreground)
            .setContentTitle("일정 알림")
            .setContentText("${title}이 종료되었습니다." )
            .setPriority(NotificationCompat.PRIORITY_HIGH)
        with(NotificationManagerCompat.from(AppModule.appContext)) {
            //notificationId is unique for each notification that you define
            notify(scheduleId, builder.build())
        }
        insertAlarm(
            title = "일정 알림",
            content = "${title}이 종료되었습니다.",
            date = selectedDate,
            color = color
        )
    }
}

