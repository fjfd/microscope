package com.vipshop.microscope.stats;
import com.codahale.metrics.health.HealthCheck;
import com.codahale.metrics.health.HealthCheckRegistry;

import java.util.Map;
import java.util.Random;

/**
 * User: hzwangxx
 * Date: 14-2-18
 * Time: 9:57
 */
public class DatabaseHealthCheck extends HealthCheck{
    private final Database database;

    public DatabaseHealthCheck(Database database) {
        this.database = database;
    }

    @Override
    protected Result check() throws Exception {
        if (database.ping()) {
            return Result.healthy();
        }
        return Result.unhealthy("Can't ping database.");
    }

    /**
     * 模拟Database对象
     */
    static class Database {
        /**
         * 模拟database的ping方法
         * @return 随机返回boolean值
         */
        public boolean ping() {
            Random random = new Random();
            return random.nextBoolean();
        }
    }

    public static void main(String[] args) {
//        MetricRegistry metrics = new MetricRegistry();
//        ConsoleReporter reporter = ConsoleReporter.forRegistry(metrics).build();
        HealthCheckRegistry registry = new HealthCheckRegistry();
        registry.register("database1", new DatabaseHealthCheck(new Database()));
        registry.register("database2", new DatabaseHealthCheck(new Database()));
        while (true) {
            for (Map.Entry<String, Result> entry : registry.runHealthChecks().entrySet()) {
                if (entry.getValue().isHealthy()) {
                    System.out.println(entry.getKey() + ": OK");
                } else {
                    System.err.println(entry.getKey() + ": FAIL, error message: " + entry.getValue().getMessage());
                    final Throwable e = entry.getValue().getError();
                    if (e != null) {
                        e.printStackTrace();
                    }
                }
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {

            }
        }
    }
}