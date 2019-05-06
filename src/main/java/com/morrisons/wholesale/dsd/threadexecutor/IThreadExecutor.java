package com.morrisons.wholesale.dsd.threadexecutor;

import java.util.List;
import java.util.concurrent.Callable;

@FunctionalInterface
public interface IThreadExecutor<E> {

	List<E> execute(List<Callable<E>> list);
}
