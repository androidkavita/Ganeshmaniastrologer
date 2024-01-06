package com.callastro.ui.activities

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.ContentValues.TAG
import android.icu.text.SimpleDateFormat
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import com.callastro.R
import com.callastro.adapters.CalenderScheduleAdapter
import com.callastro.adapters.CalenderScheduleNewAppAdapter
import com.callastro.databinding.ActivityCalenderScheduleBinding
import com.callastro.model.CalenderListData
import com.callastro.model.CreateCalendarSchedule
import com.callastro.viewModels.CalenderViewModel
import com.maxtra.astrorahiastrologer.util.DateFormat
import com.maxtra.callastro.baseClass.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.header.view.*
import org.json.JSONArray
import org.json.JSONObject
import java.text.ParseException
import java.util.*

@AndroidEntryPoint
class Calender_Schedule : BaseActivity(), CalenderScheduleNewAppAdapter.OnClick,
    CalenderScheduleAdapter.OnClick {

    //lateinit var binding: ActivityCalenderScheduleBinding
    private var _binding: ActivityCalenderScheduleBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CalenderViewModel by viewModels()
    var Listdata: ArrayList<CalenderListData> = ArrayList()
    lateinit var getistAdapter: CalenderScheduleAdapter
    var time1_24 = ""
    var time1_12 = ""
    val mcurrentTime1 = Calendar.getInstance()
    var hour1 = mcurrentTime1[Calendar.HOUR_OF_DAY]
    val minute1 = mcurrentTime1[Calendar.MINUTE]
    val zone1 = mcurrentTime1[Calendar.AM_PM]
    var time2_24 = ""
    var time2_12 = ""
    val mcurrentTime2 = Calendar.getInstance()
    var hour2 = mcurrentTime2[Calendar.HOUR_OF_DAY]
    val minute2 = mcurrentTime2[Calendar.MINUTE]
    val zone2 = mcurrentTime2[Calendar.AM_PM]

    // var newListSchedule :ArrayList<String> = ArrayList()
    var newListSchedule: ArrayList<CreateCalendarSchedule> = ArrayList()
    var newListStringSchedule: ArrayList<String> = ArrayList()
    lateinit var newAdapter: CalenderScheduleNewAppAdapter
    var itemsjsonArray = JSONArray()
    var savedDate = ""

    var strFromDate: String? = ""
    private var listFromDate: ArrayList<String> = ArrayList()
    var strTodate: String? = ""
    private var listToDate: ArrayList<String> = ArrayList()

    var idFrom = ""
    var idTo = ""


    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        _binding = DataBindingUtil.setContentView(this, R.layout.activity_calender_schedule)

        binding.header.backArrow.setOnClickListener { finish() }
        binding.header.tvHeadName.text = "Calender Schedule"

        binding.llDateFilter.setOnClickListener {
            chooseDataPicker()
        }

        binding.layoutprivioustime.setOnClickListener {
            clickTimePicker1()
        }

        binding.tvAfterTime.setOnClickListener {
            clickTimePicker2(30, time1_12)
        }

        binding.tvAfterTime60.setOnClickListener {
            clickTimePicker2(60, time1_12)
        }

        getistAdapter = CalenderScheduleAdapter(this, Listdata, this)
        getistAdapter.notifyDataSetChanged()

        newAdapter = CalenderScheduleNewAppAdapter(this, newListSchedule, this)
        newAdapter.notifyDataSetChanged()


        viewModel.progressBarStatus.observe(this) {
            if (it) {
                showProgressDialog()
            } else {
                hideProgressDialog()
            }
        }

        val currentTime = Calendar.getInstance().time
        //binding.tvDate.text =currentTime.toString()

        @RequiresApi(Build.VERSION_CODES.O)
        @SuppressLint("SetTextI18n")
        binding.tvDate.text = DateFormat.getCurrentDateformat(currentTime)
        savedDate = DateFormat.getCurrentDateformat(currentTime)
        viewModel.CalenderList(
            "Bearer " + userPref.getToken().toString(),
            binding.tvDate.text.toString()
        )
        Log.d(TAG, "onCreate:1__ " + savedDate)


        viewModel.manageCalendarResponse.observe(this) {
            if (it?.status == 1) {
                snackbar(it.message!!)
                newListSchedule.clear()
                listFromDate.clear()
                listToDate.clear()
                newAdapter.notifyDataSetChanged()
                strFromDate = ""
                strTodate = ""
                binding.btnSubmit.visibility = View.GONE

             /*   viewModel.CalenderList(
                    "Bearer " + userPref.getToken().toString(),
                    binding.tvDate.text.toString()
                )*/

            } else if (it.status == 0){
                snackbar(it?.message!!)
            }
        }


        binding.btnSubmit.setOnClickListener {
            viewModel.manage_calendar_scheduleApi(
                "Bearer " + userPref.getToken().toString(),
                savedDate,
                time1_12,
                time2_12
            )
            Log.d(
                TAG,
                "onCreate:2__  " + savedDate + "__from__" + strFromDate.toString() + "__to__" + strTodate.toString()
            )
            newListSchedule.clear()
            viewModel.CalenderList(
                "Bearer " + userPref.getToken().toString(),
                binding.tvDate.text.toString()
            )
        }


        viewModel.CalenderListResponse.observe(this) {
            if (it?.status == 1) {
                binding.tvDate.text = it.date.toString()
                Listdata.clear()
                Listdata.addAll(it.data)
                getistAdapter = CalenderScheduleAdapter(this, Listdata, this)
                binding.rvTimeSlotsApi.adapter = getistAdapter
                binding.rvTimeSlotsApi.visibility = View.VISIBLE
                binding.idNouser.visibility = View.GONE

            } else if(it.status == 0) {
                Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                //binding.rvTimeSlots.visibility = View.GONE
//                toast(this@Calender_Schedule,it.message.toString())
                binding.rvTimeSlotsApi.visibility = View.GONE
                binding.idNouser.visibility = View.VISIBLE

            }
        }

        viewModel.manageDeleteResponse.observe(this) {
            if (it.status == 1) {
                viewModel.CalenderList(
                    "Bearer " + userPref.getToken().toString(),
                    binding.tvDate.text.toString()
                )
            } else {
                snackbar(it.message!!)
            }
        }
    }


    var cal1 = Calendar.getInstance()

    @SuppressLint("SimpleDateFormat")
    @RequiresApi(Build.VERSION_CODES.N)
    val timeSetListener1 = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
        cal1.set(Calendar.HOUR_OF_DAY, hour)
        cal1.set(Calendar.MINUTE, minute)
        time1_24 = SimpleDateFormat("HH:mm").format(cal1.time)
        time1_12 = SimpleDateFormat("hh:mm a").format(cal1.time)
        binding.tvPriviousTime.text = time1_12
    }


    @RequiresApi(Build.VERSION_CODES.N)
    fun clickTimePicker1() {
        TimePickerDialog(
            this,
            R.style.timePicker,
            timeSetListener1,
            cal1.get(Calendar.HOUR_OF_DAY),
            cal1.get(Calendar.MINUTE),
            false
        ).show()
    }




    @SuppressLint("SetTextI18n", "SimpleDateFormat")
    @RequiresApi(Build.VERSION_CODES.N)
    fun clickTimePicker2(minutes: Int, startTime: String) {
        val cal1 = Calendar.getInstance()
        val sdf = SimpleDateFormat("hh:mm a", Locale.US)

        try {
            val date = sdf.parse(startTime)
            if (date != null) {
                cal1.time = date

                if (minutes == 30) {
                    cal1.add(Calendar.MINUTE, 30) // Add 30 minutes
                } else {
                    cal1.add(Calendar.MINUTE, 60) // Add 60 minutes
                }

                 time2_12 = sdf.format(cal1.time)

                val newSchedule = CreateCalendarSchedule(
                    time1_24,
                    time2_24,
                    savedDate,
                    this.time1_12,
                    time2_12
                )

                newListSchedule.add(newSchedule)
                newAdapter = CalenderScheduleNewAppAdapter(this, newListSchedule, this)
                binding.rvTimeSlotsSys.adapter = newAdapter

                if (newListSchedule.isNotEmpty()) {
                    val jsonArray2 = JSONArray()
                    for (i in newListSchedule.indices) {
                        val jsonObject2 = JSONObject()
                        jsonObject2.put("date", savedDate)
                        jsonObject2.put("from_time", newListSchedule[i].slottime1)
                        jsonObject2.put("to_time", newListSchedule[i].slottime2)
                        jsonArray2.put(jsonObject2)
                        itemsjsonArray.put(jsonObject2)
                        binding.idNouser.visibility = View.GONE
                        idFrom = newListSchedule[i].slottime1
                        idTo = newListSchedule[i].slottime2
                    }
                    binding.btnSubmit.visibility = View.VISIBLE
                    binding.rvTimeSlotsSys.visibility = View.VISIBLE
                } else {
                    binding.btnSubmit.visibility = View.GONE
                    binding.rvTimeSlotsSys.visibility = View.GONE
                }

                listFromDate.add(idFrom.toString())
                listToDate.add(idTo.toString())

                strFromDate = TextUtils.join(",", listFromDate)
                strTodate = TextUtils.join(",", listToDate)
            }
        } catch (e: ParseException) {
            e.printStackTrace()
            // Handle the parsing error
        }
    }



    @RequiresApi(Build.VERSION_CODES.N)
    private fun chooseDataPicker() {
        val cal = Calendar.getInstance()
        val simpleDateFormat = SimpleDateFormat("dd-MM-yyyy")
        cal.timeZone = TimeZone.getTimeZone("UTC")

        val datePickerDialog = DatePickerDialog(
            this, R.style.DatePickerTheme, { view, year, monthOfYear, dayOfMonth ->
                cal.set(year, monthOfYear, dayOfMonth)
                binding.tvDate.text =
                    DateFormat.addServiceDealsDate(simpleDateFormat.format(cal.time))

                savedDate = binding.tvDate.text.toString()
                Log.d(TAG, "onCreate:9__ " + savedDate)

                viewModel.CalenderList(
                    "Bearer " + userPref.getToken().toString(),
                    binding.tvDate.text.toString()
                )
            },
            cal.get(Calendar.YEAR),
            cal.get(Calendar.MONTH),
            cal.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.datePicker.minDate = System.currentTimeMillis() - 1000
        datePickerDialog.show()
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onRemoveClicked(createCalendarSchedule: CreateCalendarSchedule) {

        newListSchedule.remove(createCalendarSchedule)
        // listFromDate.remove(createCalendarSchedule.slottime1)
        // listToDate.remove(createCalendarSchedule.slottime2)
        listFromDate.remove(createCalendarSchedule.slottime1)
        listToDate.remove(createCalendarSchedule.slottime2)

        strFromDate = android.text.TextUtils.join(",", listFromDate)
        strTodate = android.text.TextUtils.join(",", listToDate)


        newAdapter.notifyDataSetChanged()
        // getistAdapter.notifyDataSetChanged()
        if (newListSchedule.isEmpty()) {
            binding.btnSubmit.visibility = View.GONE
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onRemovedFromApiClicked(calenderListData: CalenderListData) {
        viewModel.calendar_schedule_deleteApi(
            "Bearer " + userPref.getToken(),
            calenderListData.id.toString()
        )
        Log.d(TAG, "onCreate:10__ " + "12")
        getistAdapter.notifyDataSetChanged()
        // newAdapter.notifyDataSetChanged()
    }


}