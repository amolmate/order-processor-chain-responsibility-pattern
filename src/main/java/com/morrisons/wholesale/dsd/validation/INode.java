package com.morrisons.wholesale.dsd.validation;

@FunctionalInterface
public interface INode<T> {

	void processNode(T t);
}