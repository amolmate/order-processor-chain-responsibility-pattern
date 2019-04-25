package com.morrisons.wholesale.dsd;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;

import com.morrisons.wholesale.dsd.processor.IWholesaleDSDOrderProcessor;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@EnableRetry
@Slf4j
public class WholesaleDSDOrderProcessorTask implements CommandLineRunner {

	@Autowired
	private IWholesaleDSDOrderProcessor wholesaleDSDOrderProcessor;
	
	public static void main(String[] args) {

		SpringApplication springApplication = new SpringApplication(WholesaleDSDOrderProcessorTask.class);
		springApplication.setWebApplicationType(WebApplicationType.NONE);
		springApplication.run(args);
	}

	@Override
	public void run(String... args) throws Exception {

		log.debug("WholesaleDSDOrderProcessorTask started");
		wholesaleDSDOrderProcessor.processTask();
	}
	
	private class TestRunnable implements Runnable{

		private int number;
		
		public TestRunnable(int number) {

			this.number = number;
		}
		
		@Override
		public void run() {

			System.out.println("Print task start");
			System.out.println("print number " + number);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("Print task end");
		}
		
	}
}