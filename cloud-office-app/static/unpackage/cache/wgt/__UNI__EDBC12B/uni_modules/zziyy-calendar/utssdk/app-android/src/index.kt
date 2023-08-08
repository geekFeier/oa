package uts.sdk.modules.zziyyCalendar;
import kotlinx.coroutines.async;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Deferred;
import kotlinx.coroutines.Dispatchers;
import io.dcloud.uts.Map;
import io.dcloud.uts.*;
import io.dcloud.uts.UTSAndroid;
import androidx.core.app.ActivityCompat;
import android.content.Context;
import android.content.ContentUris;
import android.content.ContentValues;
import android.graphics.Color;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CalendarContract;
import java.util.Calendar;
import java.util.TimeZone;
import java.lang.Long;
import kotlin.Long as kotlinLong;
import android.Manifest;
import android.content.pm.PackageManager;
import android.text.TextUtils;
data class AddParams (
    var name: String,
    var title: String,
    var location: String? = null,
    var description: String? = null,
    var dtstart: Number,
    var dtend: Number,
    var reminder: Number,
)
data class DeleteParams (
    var title: String,
)
data class Results (
    var code: Number,
    var data: String? = null,
    var msg: String,
)
data class AddOptions (
    var params: AddParams,
    var success: (res: Results) -> Unit,
    var fail: (err: Results) -> Unit,
    var complete: () -> Unit,
)
data class DeleteOptions (
    var params: DeleteParams,
    var success: (res: Results) -> Unit,
    var fail: (err: Results) -> Unit,
    var complete: () -> Unit,
)
val CALENDER_URL: String = "content://com.android.calendar/calendars";
val CALENDER_EVENT_URL: String = "content://com.android.calendar/events";
val CALENDER_REMINDER_URL: String = "content://com.android.calendar/reminders";
val CALENDARS_NAME: String = "test";
val CALENDARS_ACCOUNT_NAME: String = "test@qq.com";
val CALENDARS_ACCOUNT_TYPE: String = "com.android.test";
val CALENDARS_DISPLAY_NAME: String = "test";
open class CalendarUtils {
    constructor(){}
    open fun checkAndAddAccount(context: Context): Number {
        var oldId = this.checkAccount(context);
        if (oldId >= 0) return oldId;
        else {
            var addId = this.addAccount(context);
            if (addId >= 0) return this.checkAccount(context);
            else return -1;
        }
    }
    open fun checkAccount(context: Context): Number {
        var userCursor = context.getContentResolver().query(Uri.parse(CALENDER_URL), null, null, null, null);
        try {
            if (userCursor == null) return -1;
            var count = userCursor.getCount();
            if (count > 0) {
                userCursor.moveToFirst();
                return userCursor.getInt(userCursor.getColumnIndex(CalendarContract.Calendars._ID));
            } else return -1;
        }
         finally{
            if (userCursor != null) userCursor.close();
        }
    }
    open fun addAccount(context: Context): Number {
        var timeZone = TimeZone.getDefault();
        var value = ContentValues();
        value.put(CalendarContract.Calendars.NAME, CALENDARS_NAME);
        value.put(CalendarContract.Calendars.ACCOUNT_NAME, CALENDARS_ACCOUNT_NAME);
        value.put(CalendarContract.Calendars.ACCOUNT_TYPE, CALENDARS_ACCOUNT_TYPE);
        value.put(CalendarContract.Calendars.CALENDAR_DISPLAY_NAME, CALENDARS_DISPLAY_NAME);
        value.put(CalendarContract.Calendars.VISIBLE, "1");
        value.put(CalendarContract.Calendars.CALENDAR_COLOR, "" + Color.BLUE);
        value.put(CalendarContract.Calendars.CALENDAR_ACCESS_LEVEL, CalendarContract.Calendars.CAL_ACCESS_OWNER);
        value.put(CalendarContract.Calendars.SYNC_EVENTS, "1");
        value.put(CalendarContract.Calendars.CALENDAR_TIME_ZONE, timeZone.getID());
        value.put(CalendarContract.Calendars.OWNER_ACCOUNT, CALENDARS_ACCOUNT_NAME);
        value.put(CalendarContract.Calendars.CAN_ORGANIZER_RESPOND, "0");
        var calendarUri = Uri.parse(CALENDER_URL);
        calendarUri = calendarUri.buildUpon().appendQueryParameter(CalendarContract.CALLER_IS_SYNCADAPTER, "true").appendQueryParameter(CalendarContract.Calendars.ACCOUNT_NAME, CALENDARS_ACCOUNT_NAME).appendQueryParameter(CalendarContract.Calendars.ACCOUNT_TYPE, CALENDARS_ACCOUNT_TYPE).build();
        var result = context.getContentResolver().insert(calendarUri, value);
        var id = if (result == null) {
            -1;
        } else {
            ContentUris.parseId(result);
        }
        ;
        return id;
    }
    open fun addEvent(params: AddParams) {
        var title: String = params.title;
        var description: String = params.description + "";
        var dtstart: String = params.dtstart + "";
        var reminder: Number = params.reminder;
        var location: String = params.location + "";
        var context: Context = UTSAndroid.getUniActivity()!!.getApplicationContext();
        if (context === null) return;
        console.log("addEvent", " at uni_modules/zziyy-calendar/utssdk/app-android/index.uts:125");
        var calId: Number = this.checkAndAddAccount(context);
        if (calId < 0) return;
        var mCalendar = Calendar.getInstance();
        mCalendar.setTimeInMillis(Long.parseLong(dtstart));
        var start = mCalendar.getTime().getTime();
        mCalendar.setTimeInMillis(start + 600000);
        var end = mCalendar.getTime().getTime();
        var event = ContentValues();
        event.put(CalendarContract.Events.TITLE, title);
        event.put(CalendarContract.Events.EVENT_LOCATION, location);
        event.put(CalendarContract.Events.DESCRIPTION, description);
        event.put(CalendarContract.Events.CALENDAR_ID, "" + calId);
        event.put(CalendarContract.Events.DTSTART, "" + start);
        event.put(CalendarContract.Events.DTEND, "" + end);
        event.put(CalendarContract.Events.HAS_ALARM, "1");
        event.put(CalendarContract.Events.EVENT_TIMEZONE, "Asia/Shanghai");
        var newEvent = context.getContentResolver().insert(Uri.parse(CALENDER_EVENT_URL), event);
        if (newEvent == null) return;
        var values = ContentValues();
        values.put(CalendarContract.Reminders.EVENT_ID, "" + ContentUris.parseId(newEvent));
        values.put(CalendarContract.Reminders.MINUTES, "" + reminder);
        values.put(CalendarContract.Reminders.METHOD, CalendarContract.Reminders.METHOD_ALERT);
        var uri = context.getContentResolver().insert(Uri.parse(CALENDER_REMINDER_URL), values);
        if (uri == null) return;
    }
    open fun deleteEvent(title: String) {
        var context: Context = UTSAndroid.getUniActivity()!!.getApplicationContext();
        if (context === null) return;
        var eventCursor = context.getContentResolver().query(Uri.parse(CALENDER_EVENT_URL), null, null, null, null);
        try {
            if (eventCursor == null) return;
            if (eventCursor.getCount() > 0) run {
                eventCursor.moveToFirst();
                while(!eventCursor.isAfterLast()){
                    var eventTitle: String = eventCursor.getString(eventCursor.getColumnIndex("title"));
                    if (!TextUtils.isEmpty(title) && title.equals(eventTitle)) {
                        var id = eventCursor.getLong(eventCursor.getColumnIndex(CalendarContract.Calendars._ID));
                        var deleteUri = ContentUris.withAppendedId(Uri.parse(CALENDER_EVENT_URL), id);
                        var rows: Int = context.getContentResolver().delete(deleteUri, null, null);
                        if (rows == -1) return;
                    }
                    eventCursor.moveToNext();
                }
            }
        }
         finally{
            if (eventCursor != null) eventCursor.close();
        }
    }
}
fun calendarAdd(opts: AddOptions) {
    console.log("---------------", " at uni_modules/zziyy-calendar/utssdk/app-android/index.uts:199");
    if (ActivityCompat.checkSelfPermission(UTSAndroid.getUniActivity()!!, Manifest.permission.WRITE_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
        ActivityCompat.requestPermissions(UTSAndroid.getUniActivity()!!, arrayOf(Manifest.permission.WRITE_CALENDAR), 1001);
        val ret = Results(code = -1, data = "权限", msg = "不具备权限，请申请权限");
        opts.fail(ret);
    } else {
        if (ActivityCompat.checkSelfPermission(UTSAndroid.getUniActivity()!!, Manifest.permission.READ_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(UTSAndroid.getUniActivity()!!, arrayOf(Manifest.permission.READ_CALENDAR), 1001);
            val ret = Results(code = -1, data = "权限", msg = "不具备权限，请申请权限");
            opts.fail(ret);
        }
        var calendar_utils: CalendarUtils = CalendarUtils();
        var params: AddParams = opts.params;
        calendar_utils.addEvent(params);
        val ret = Results(code = 1, data = "成功", msg = "添加提醒成功");
        opts.success(ret);
    }
    opts.complete();
}
fun calendarDelete(opts: DeleteOptions) {
    if (ActivityCompat.checkSelfPermission(UTSAndroid.getUniActivity()!!, Manifest.permission.WRITE_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
        ActivityCompat.requestPermissions(UTSAndroid.getUniActivity()!!, arrayOf(Manifest.permission.WRITE_CALENDAR), 1001);
        val ret = Results(code = -1, data = "权限", msg = "不具备权限，请申请权限");
        opts.fail(ret);
    } else {
        var calendar_utils: CalendarUtils = CalendarUtils();
        var params: DeleteParams = opts.params;
        calendar_utils.deleteEvent(params.title);
        val ret = Results(code = 1, data = "成功", msg = "删除提醒成功");
        opts.success(ret);
    }
    opts.complete();
}
open class AddOptionsJSONObject : UTSJSONObject() {
    open lateinit var params: AddParams;
    open lateinit var success: UTSCallback;
    open lateinit var fail: UTSCallback;
    open lateinit var complete: UTSCallback;
}
open class DeleteOptionsJSONObject : UTSJSONObject() {
    open lateinit var params: DeleteParams;
    open lateinit var success: UTSCallback;
    open lateinit var fail: UTSCallback;
    open lateinit var complete: UTSCallback;
}
fun calendarAddByJs(opts: AddOptionsJSONObject) {
    return calendarAdd(AddOptions(params = opts.params, success = fun(res: Results): Unit {
        opts.success(res);
    }
    , fail = fun(err: Results): Unit {
        opts.fail(err);
    }
    , complete = fun(): Unit {
        opts.complete();
    }
    ));
}
fun calendarDeleteByJs(opts: DeleteOptionsJSONObject) {
    return calendarDelete(DeleteOptions(params = opts.params, success = fun(res: Results): Unit {
        opts.success(res);
    }
    , fail = fun(err: Results): Unit {
        opts.fail(err);
    }
    , complete = fun(): Unit {
        opts.complete();
    }
    ));
}
