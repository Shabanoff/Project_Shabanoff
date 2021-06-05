package controller.schedulers;

import org.apache.logging.log4j.LogManager;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import static org.quartz.CronScheduleBuilder.dailyAtHourAndMinute;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

public class SchedulerInit {

    private SchedulerInit() {
        init();
    }

    private static class Singleton {
        private static final SchedulerInit INSTANCE = new SchedulerInit();
    }

    public static SchedulerInit getInstance() {
        return Singleton.INSTANCE;
    }

    private static void init() {

        SchedulerFactory schedulerFactory = new StdSchedulerFactory();

        Scheduler scheduler;
        try {
            scheduler = schedulerFactory.getScheduler();
            scheduler.start();
            LogManager.getLogger(Scheduler.class).info("Scheduler started correctly!");
        } catch (SchedulerException e) {
            LogManager.getLogger(Scheduler.class).error(e);
            return;
        }

        Trigger debitTrigger = newTrigger()
                .withIdentity("debitTrigger", "mainGroup")
                .withSchedule(dailyAtHourAndMinute(1, 00))
                .build();


        JobDetail creditJob = newJob(DebitJob.class)
                .withIdentity("debitJob", "mainGroup")
                .build();


        try {
            scheduler.scheduleJob(creditJob, debitTrigger);
        } catch (SchedulerException e) {
            LogManager.getLogger(Scheduler.class).error(e);
        }
    }
}
