/*
 * Copyright (c) 2005, 2014, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */

package com.study.jvm;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.concurrent.TimeUnit;


// @Warmup:当前方法需要预热的次数，此时表示1秒钟内预热5轮
@Warmup(iterations = 5, time = 1)
// @Fork:启动多少个进程进行测试，此处表示只启动一个进程进行测试，并且启动时追加了堆内存相关的虚拟机参数
@Fork(value = 1, jvmArgsAppend = {"-Xms1g", "-Xmx1g"})
// @BenchmarkMode:指定当前的显示结果，可以指定吞吐量、耗时时间等，此处表示打印平均耗时时间
@BenchmarkMode(Mode.AverageTime)
// @OutputTimeUnit:指定显示结果的单位，此处指定时间单位为纳秒
@OutputTimeUnit(TimeUnit.NANOSECONDS)
// @State:变量共享范围，变量是在整个测试过程中共享，还是在单个线程中共享，此处表示在整个测试过程中共享（如果想在单个线程中共享，使用Scope.Thread）
@State(Scope.Benchmark)
public class DateBenchmark {

    // 定义变量
    private Date date = new Date();
    private LocalDateTime localDateTime = LocalDateTime.now();
    private static String format = "yyyy-MM-dd HH:mm:ss";

    @Benchmark // 方法上标注了 @Benchmark 注解，表示此方法为测试方法
    public void testMethod(Blackhole blackhole) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        String str = simpleDateFormat.format(date);
        blackhole.consume(str);
    }

    @Benchmark // 方法上标注了 @Benchmark 注解，表示此方法为测试方法
    public void testMethod2(Blackhole blackhole) {
        String str = localDateTime.format(DateTimeFormatter.ofPattern(DateBenchmark.format));
        blackhole.consume(str);
    }


    // 通过main方法来跑jmh的测试方法
    public static void main(String[] args) throws RunnerException {
        Options options = new OptionsBuilder()
                .include(DateBenchmark.class.getSimpleName()) // 手动指定测试的类对应的类名
                .forks(1) // 指定进程号，这里的fork是给main方法来使用的，如果打成jar包最终还是会使用类上面的Fork注解
                .resultFormat(ResultFormatType.JSON) // 生成JSON格式的报告文档
                .build();
        // 通过Runner来启动测试方法
        new Runner(options).run();
    }

}
