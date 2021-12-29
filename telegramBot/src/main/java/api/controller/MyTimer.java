package api.controller;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;

public class MyTimer {
    private TelegramImplementations bot;
    private SendNotif notifInstance;

    public MyTimer(TelegramImplementations bot) {
        this.bot = bot;
        notifInstance = new SendNotif(bot);
    }

    public void sendNotify() {

        ZonedDateTime startTime = ZonedDateTime.now();
        startTime = startTime.withHour(startTime.getHour() + 1).withMinute(0).withSecond(0).withNano(0);
        Date startDate = Date.from(startTime.toInstant());
        TimeZone timeZoneUa = TimeZone.getTimeZone("Europe/Kiev");

        Timer timer = new Timer(true);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                ZonedDateTime currentTime = ZonedDateTime.now(timeZoneUa.toZoneId());
                int hour = currentTime.getHour();
                if (hour >= 9 && hour <= 18) {

                    try {
                        notifInstance.sendNotifications(hour);
                    } catch (IOException | InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, startDate, 3600000L);
    }
}
