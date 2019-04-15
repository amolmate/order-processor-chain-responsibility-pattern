package com.morrisons.wholesale.dsd.threadexecutor;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.morrisons.wholesale.dsd.exception.ErrorCodes;
import com.morrisons.wholesale.dsd.exception.IWMMExceptionFactory;
import com.morrisons.wholesale.dsd.exception.WMMException;

public class ThreadExecutor<E> implements IThreadExecutor<E> {

	private static final Logger LOGGER = LoggerFactory.getLogger(ThreadExecutor.class);

	private final int threadPoolSize;

	private final IWMMExceptionFactory exceptionFactory;

	public ThreadExecutor(int threadPoolSize, IWMMExceptionFactory exceptionFactory) {

		this.threadPoolSize = threadPoolSize;
		this.exceptionFactory = exceptionFactory;
	}

	@Override
	public List<E> execute(List<Callable<E>> callables) {

		try {

			ExecutorService executorService = Executors.newFixedThreadPool(threadPoolSize);

			List<E> listResponse = executorService.invokeAll(callables).stream().map(this::getEntity)
					.collect(Collectors.toList());

			executorService.shutdown();

			executorService.awaitTermination(60, TimeUnit.MINUTES);

			return listResponse;
		} catch (Exception e) {

			WMMException we = exceptionFactory.createException(ErrorCodes.THREAD_EXECUTOR_ERR, e);
			LOGGER.error(we.getMessage(), we);
			throw we;
		}
	}

	private E getEntity(Future<E> future) {

		try {

			return future.get();
		} catch (WMMException ex) {

			throw ex;
		} catch (Exception e) {

			WMMException we = exceptionFactory.createException(ErrorCodes.THREAD_EXECUTOR_FUTURES_ERR, e);
			LOGGER.error(we.getMessage(), we);
			throw we;
		}
	}
}