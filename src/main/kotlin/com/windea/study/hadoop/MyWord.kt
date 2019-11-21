package com.windea.study.hadoop

import org.apache.hadoop.conf.*
import org.apache.hadoop.fs.*
import org.apache.hadoop.io.*
import org.apache.hadoop.mapreduce.*
import org.apache.hadoop.mapreduce.lib.input.*
import org.apache.hadoop.mapreduce.lib.output.*
import java.net.*
import kotlin.system.*

//仅作模版，不测试

object MyWord {
	@JvmStatic
	fun main(args: Array<String>) {
		val inputFold = "hdfs://192.168.46.134:9000/input"
		val outFold = "hdfs://192.168.46.134:9000/output"
		val configuration = Configuration()
		val fs = FileSystem.get(URI("hdfs://192.168.46.134:9000"), configuration, "shicheng")
		if(fs.exists(Path(outFold))) fs.delete(Path(outFold), true)
		
		val job = Job.getInstance(configuration, "MyWordCountApp")
		
		job.setJarByClass(MyWord::class.java) //运行的主类
		job.mapperClass = myMapper::class.java
		job.mapOutputKeyClass = Text::class.java
		job.mapOutputValueClass = IntWritable::class.java
		job.reducerClass = myReduce::class.java
		job.outputKeyClass = Text::class.java
		job.outputValueClass = LongWritable::class.java
		
		FileInputFormat.addInputPath(job, Path(inputFold))
		FileOutputFormat.setOutputPath(job, Path(outFold))
		exitProcess(if(job.waitForCompletion(true)) 0 else 1)
	}
}

class myMapper : Mapper<LongWritable?, Text, Text?, IntWritable?>() {
	override fun map(key: LongWritable?, value: Text, context: Context) {
		val words = value.toString().split(" ").toTypedArray()
		for(word in words) {
			context.write(Text(word), IntWritable(1))
		}
	}
}

class myReduce : Reducer<Text?, IntWritable?, Text?, LongWritable?>() {
	override fun reduce(key: Text?, value: Iterable<IntWritable?>, context: Context) {
		context.write(key, LongWritable(value.count().toLong()))
	}
}
